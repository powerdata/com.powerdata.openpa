package com.powerdata.openpa.pwrflow;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import com.powerdata.openpa.ACBranch;
import com.powerdata.openpa.ACBranchList;
import com.powerdata.openpa.Bus;
import com.powerdata.openpa.BusList;
import com.powerdata.openpa.BusRefIndex;
import com.powerdata.openpa.FixedShunt;
import com.powerdata.openpa.FixedShuntListIfc;
import com.powerdata.openpa.Gen;
import com.powerdata.openpa.GenList;
import com.powerdata.openpa.LoadList;
import com.powerdata.openpa.PAModel;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.PflowModelBuilder;
import com.powerdata.openpa.SVCList;
import com.powerdata.openpa.SubLists;
import com.powerdata.openpa.TwoTermDev;
import com.powerdata.openpa.TwoTermDevList;
import com.powerdata.openpa.tools.PAMath;

/**
 * Calculate AC power and generate mismatches
 * 
 * Two separate models exist for SVC's. If the droop is defined in the model
 * (non-zero), then we follow that characteristic. If no droop is defined, then
 * we don't solve the SVC and assume it is caluculated within the power flow.
 * 
 * @author chris@powerdata.com
 *
 */
public class ACPowerCalc
{
	/** system MVA base */
	float _sbase = 100f;
	/** ac branch calculators for each type */
	List<ACBranchFlows> _brcalc = new ArrayList<>();
	/** SVC calculator */
	SVCCalcList _svc;
	/** keep the partitioned SVC results around for a reasonable amount of time */
	WeakReference<SVCList[]> _svcpart = new WeakReference<>(null);
	/** PA Model */
	PAModel _model;
	/** fixed shunt calculators */
	List<FixedShuntCalcList> _fshcalc = new ArrayList<>();
	/** active loads */
	Active1TData _actvld, _actvgen;

	/**
	 * Create a new calculator
	 * @param model Power System model
	 * @param bri Bus references to correctly translate connectivty nodes if desired
	 * @throws PAModelException 
	 */
	public ACPowerCalc(PAModel model, BusRefIndex bri, float sbase) throws PAModelException
	{
		this(model, bri, setupFixedShunts(model), setupActiveLoads(bri, model, sbase),
				setupActiveGens(bri, model, sbase));
	}
	public static Active1TData setupActiveLoads(BusRefIndex bri, PAModel model, float sbase)
			throws PAModelException
	{
		LoadList loadsub = SubLists.getLoadInsvc(model.getLoads());
		return new Active1TData(bri, loadsub, () -> loadsub.getP(),
				() -> loadsub.getQ(), sbase);
	}
	public static Active1TData setupActiveGens(BusRefIndex bri, PAModel model, float sbase)
			throws PAModelException
	{
		GenList gens = model.getGenerators();
		int[] ix = new int[gens.size()];
		int np = 0;
		for(Gen g : gens)
		{
			if (g.isGenerating())
				ix[np++] = g.getIndex();
		}
		GenList gensub = SubLists.getGenSublist(gens, Arrays.copyOf(ix, np));
		return new Active1TData(bri, gensub, () -> gensub.getP(), () -> gensub.getQ(), sbase);
	}

	public static Collection<FixedShuntListIfc<? extends FixedShunt>> setupFixedShunts(PAModel model)
			throws PAModelException
	{
		List<FixedShuntListIfc<? extends FixedShunt>> shInSvc = new ArrayList<>(2);
		shInSvc.add(SubLists.getShuntCapInsvc(model.getShuntCapacitors()));
		shInSvc.add(SubLists.getShuntReacInsvc(model.getShuntReactors()));
		return shInSvc;
	}
	
	public ACPowerCalc(PAModel model, BusRefIndex bri,
			Collection<FixedShuntListIfc<? extends FixedShunt>> shInSvc,
			Active1TData actvLoads, Active1TData actvGens)
			throws PAModelException
	{
		_model = model;
		
		/* build AC branch calculators (one for each dev type) */
		for(ACBranchList l : model.getACBranches())
		{
			_brcalc.add(new ACBranchFlowsSubList(new ACBranchFlowsI(l, bri),
				SubLists.getInServiceIndexes(l)));
		}

		/* partition SVC's into 0 and non-0 slope (0-slope will become PV buses) */
		SVCList[] psvc = partitionSVCs();
		SVCList nonPvSvc = psvc[1];
		_svc = new SVCCalcList(nonPvSvc, bri);
		_svcpart = new WeakReference<>(psvc);

		/* set up the fixed shunt calculators */
		for(FixedShuntListIfc<? extends FixedShunt> fsh : shInSvc)
			_fshcalc.add(new FixedShuntCalcList(fsh, bri));
		
		_actvld = actvLoads;
		_actvgen = actvGens;

	}

	/** return fixed shunt calculator (one for each list) */
	public List<FixedShuntCalcList> getFixedShuntCalc() {return _fshcalc;}
	
	/** return AC Branch calculator (one for each type of branch) */
	public List<ACBranchFlows> getBranchFlows() {return _brcalc;}

	/** return SVC calculator for SVC's modeled with positive slope */
	public SVCCalcList getSVCCalc() {return _svc;}

	/**
	 * Partition the SVC's into elements that should be treated as PV buses (0-slope)
	 * @return Index 0 has SVC's treated as PV buses, 1 has the rest
	 * @throws PAModelException
	 */
	SVCList[] partitionSVCs() throws PAModelException
	{
		SVCList svcs = _model.getSVCs();
		int[] insvc = SubLists.getInServiceIndexes(svcs);
		int nsvc = insvc.length;
		int[] pv = new int[nsvc], npv = new int[nsvc];
		int pvc=0, npvc=0;
		for(int i : insvc)
		{
			if(svcs.getSlope(i) == 0f)
				pv[pvc++] = i;
			else
				npv[npvc++] = i;
		}
		return new SVCList[] {SubLists.getSVCSublist(_model.getSVCs(), Arrays.copyOf(pv, pvc)), 
				SubLists.getSVCSublist(_model.getSVCs(), Arrays.copyOf(npv, npvc))};
	}

	/**
	 * Get the set of SVC's with no slope defined.
	 * @return SVC's with no slope (droop)
	 * @throws PAModelException 
	 */
	public SVCList getPvSvcList() throws PAModelException
	{
		SVCList[] l = _svcpart.get();
		if (l == null)
		{
			l = partitionSVCs();
			_svcpart = new WeakReference<>(l);
		}
		return l[0];
	}
	
	/**
	 * Calculate flows & shunts
	 * 
	 * @param vm
	 * @param va
	 * @throws PAModelException 
	 */
	public void calc(float[] vm, float[] va) throws PAModelException
	{
		for(ACBranchFlows flows : _brcalc)
			flows.calc(vm, va);
		
		for(FixedShuntCalcList fs : _fshcalc)
			fs.calc(vm);
		
		_svc.calc(vm);
	}
	
	/**
	 * Calculate all bus mismatches 
	 * @param pmm
	 * @param qmm
	 * @throws PAModelException
	 */
	public void applyMismatch(Mismatch pmm, Mismatch qmm) throws PAModelException
	{
		for(ACBranchFlows flows : _brcalc)
			flows.applyMismatches(pmm, qmm);

		for(FixedShuntCalcList fs : _fshcalc)
			fs.applyMismatches(qmm);
		
		_svc.applyMismatches(qmm);
		
		_actvld.applyMismatch(pmm, qmm);
		_actvgen.applyMismatch(pmm, qmm);
	
	}
	
	/** Update results back into the OpenPA lists */
	public void updateResults() throws PAModelException
	{
		for(ACBranchFlows flows : _brcalc)
			flows.update();

		for(FixedShuntCalcList fs : _fshcalc)
			fs.update();
		
		_svc.update();
	}
	
	/**
	 * Provide a main routine to report branch flows and mismatches
	 * @param args
	 * @throws Exception
	 */
	public static void main(String...args) throws Exception
	{
		String uri = null;
		File poutdir = new File(System.getProperty("user.dir"));
		Boolean singlebus = false, mismatches = false;
		for(int i=0; i < args.length;)
		{
			String s = args[i++].toLowerCase();
			int ssx = 1;
			if (s.startsWith("--")) ++ssx;
			switch(s.substring(ssx))
			{
				case "uri":
					uri = args[i++];
					break;
				case "outdir":
					poutdir = new File(args[i++]);
					break;
				case "singlebus":
					singlebus = true;
					break;
				case "mismatches":
					mismatches = true;
					singlebus = true;
					break;
			}
		}
		if (uri == null)
		{
			System.err.format("Usage: -uri model_uri \n" +
					"\t[ --singlebus ] (defaults to false unless --mismatches enabled \n"+
					"\t[ --mismatches ] (defaults to false, true forces single bus )\n"
					+ "\t[ --outdir output_directory (deft to $CWD ]\n");
			System.exit(1);
		}
		
		System.out.format("\nConfiguration\n\tUri: %s\n", uri);
		System.out.format("\tUse Single Bus: %s\n", singlebus.toString());
		System.out.format("\tGenerate Mismatches: %s\n",  mismatches.toString());
		System.out.format("\tOutputDirectory: %s\n", poutdir.toString());
		
		final File outdir = poutdir;
		if (!outdir.exists()) outdir.mkdirs();
		PflowModelBuilder bldr = PflowModelBuilder.Create(uri);
		bldr.setLeastX(0.0001f);
		bldr.enableFlatVoltage(false);
		PAModel m = bldr.load();
		float sbase = 100f;

		for(TwoTermDevList devlists : m.getTwoTermDevices())
		{
			for(TwoTermDev dev : devlists)
			{
				dev.setFromP(0);
				dev.setFromQ(0);
				dev.setToP(0);
				dev.setToQ(0);
			}
		}
		

		
		/* Create bus reference index according to configuration */
		BusRefIndex bri = singlebus ? BusRefIndex.CreateFromSingleBuses(m)
				: BusRefIndex.CreateFromConnectivityBuses(m);
		/* create AC network calculator object */
		ACPowerCalc calc = new ACPowerCalc(m, bri, sbase);
		/*
		 * Get system buses, and use current voltage magnitude & angle to
		 * calculate power flows
		 */
		BusList buses = bri.getBuses();
		float[] vm = PAMath.vmpu(buses), va = PAMath.deg2rad(buses.getVA());
		calc.calc(vm, va);

		
		/*
		 * Update the flows back to the model
		 */
		calc.updateResults();
		
		/*
		 * Output the flows
		 */
		reportFlows(m, bri, outdir);
		
		/*
		 * If enabled, output the mismatches
		 */
		if (mismatches)
		{
			Mismatch pmm = new Mismatch(bri, null, null);
			Mismatch qmm = new Mismatch(bri, null, null);
			calc.applyMismatch(pmm, qmm);
			
			MismatchReporter r = new DetailMismatchReporter(m, outdir, false).reportBegin(bri.getBuses());
			r.reportMismatch(PAMath.pu2mva(pmm.get(), sbase), PAMath.pu2mva(qmm.get(), sbase), vm, PAMath.rad2deg(va), null);
			r.reportEnd();
		}
	}
	
	private static void reportFlows(PAModel m, BusRefIndex bri, File outdir) throws PAModelException, IOException
	{
		boolean usesta = !m.getStations().isEmpty();
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
				new File(outdir, "branches.csv"))));
		pw.print("\"Type\",\"ID\",\"Name\",\"FromArea\",\"ToArea\",\"FromBus\",\"ToBus\",");
		if (usesta) pw.print("\"FromStation\",\"ToStation\",");
		pw.println("\"FromVL\",\"ToVL\",\"FromMW\",\"FromMVAr\",\"ToMW\",\"ToMVAr\"");
		BusList buses = bri.getBuses();
		for (ACBranchList brlist : m.getACBranches())
		{
			String type = brlist.getListMeta().toString();
			for (ACBranch br : brlist)
			{
				Bus fbus = buses.getByBus(br.getFromBus()), tbus = buses
						.getByBus(br.getToBus());
				pw.format("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",",
					type, br.getID(), br.getName(), fbus.getArea().getName(),
					tbus.getArea().getName(), fbus.getID(), tbus.getID());
				if (usesta)
				{
					pw.format("\"%s\",\"%s\",", fbus.getStation().getName(),
						tbus.getStation().getName());
				}
				pw.format("%f,%f,%f,%f,%f,%f\n", fbus.getVoltageLevel()
						.getBaseKV(), tbus.getVoltageLevel().getBaseKV(), br
						.getFromP(), br.getFromQ(), br.getToP(), br.getToQ());
			}
		}
		pw.close();
	}

}
