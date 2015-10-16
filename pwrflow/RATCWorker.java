package com.powerdata.openpa.pwrflow;

import java.io.File;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import com.powerdata.openpa.ACBranch;
import com.powerdata.openpa.ACBranchListIfc;
import com.powerdata.openpa.BusRefIndex;
import com.powerdata.openpa.CloneModelBuilder;
import com.powerdata.openpa.ColumnMeta;
import com.powerdata.openpa.Gen;
import com.powerdata.openpa.LineList;
import com.powerdata.openpa.PAModel;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.PflowModelBuilder;
import com.powerdata.openpa.impl.GenListI;
import com.powerdata.openpa.impl.LoadListI;

/**
 * Remedial adaptive topology control (RATC)
 * 
 * Evaluate power system to recommend switching actions that will alleviate flow
 * on specified branch by calculating a set of Line Outage Distribution Factors (LODF)
 * and applying them to the solved flows
 * 
 * 
 * 
 * The algorithm works as follows:
 * 
 * <ol>
 * <li>Fabricate and place 100MW flow across selected branch</li>
 * <li>Solve flows for fabricated case</li>
 * <li>Calculate fraction of flow in target branch</li>
 * <li>Assign remaining fracction to network, NF</li>
 * <li>LODF for each Line (branch, actually), = flow / 100 / NF</li>
 * </ol>
 * 
 * @author chris@powerdata.com
 *
 */

public class RATCWorker
{
	protected PAModel _model;
	protected ACBranch _targ;
	protected BusRefIndex _bri;
	protected BusTypeUtil _btu;
	protected boolean _swapgenside = false;
	
	@SuppressWarnings("deprecation")
	public RATCWorker(PAModel model, ACBranch target) throws PAModelException
	{
		System.out.format("Target: %s\n", target.getName());
		_model = model;
		_targ = target;
		if (_targ.getFromP() < 0f) _swapgenside = true;
		_bri = BusRefIndex.CreateFromSingleBuses(model);
		FDPowerFlow pf = new FDPowerFlow(model, _bri);
		pf.runPF();
		pf.updateResults();
//		try
//		{
//			new ListDumper().dump(_model, new File("/run/shm/actest"));
//		}
//		catch (IOException | ReflectiveOperationException | RuntimeException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		//TODO:  make the bus type util creation external to the ac power flow
		_btu = pf.getBusTypes();
	}
	
	float[] _lodf, _ratc;
	ACBranchListIfc<? extends ACBranch> _branches;
	
	public void runRATC() throws PAModelException
	{
		RATCModelBuilder rmb = new RATCModelBuilder(_model);
		PAModel dcmodel = rmb.load();
		DCPowerFlow dcpf = new DCPowerFlow(dcmodel, _bri, _btu);
		dcpf.runPF().updateResults();

		int nlodf = _model.getLines().size();
		_lodf = new float[nlodf];
		_ratc = new float[nlodf];
		// TODO:  Update to support all AC branches
		LineList dclineflow = dcmodel.getLines(), aclineflow = _model.getLines();
		_branches = aclineflow;
		ACBranch dctarg = dclineflow.get(_targ.getIndex());
		/** fraction of artificial 100MW flow across target */
		float lnfact = Math.abs(dctarg.getFromP())/100f;
		_lodf[_targ.getIndex()] = lnfact;
		float netfrac = 1f-lnfact;
		float div = 1f/(100f*netfrac);
		for(int i=0; i < nlodf; ++i)
		{
			if (i != _targ.getIndex())
			{
				ACBranch dc = dclineflow.get(i), ac = aclineflow.get(i);
				_lodf[i] = dc.getFromP() * div;
				_ratc[i] = _lodf[i] * ac.getFromP();
			}
		}
		
	}
	
	public class Result implements Comparable<Result>
	{
		int _ndx;
		Result(int ndx) {_ndx = ndx;}
		public ACBranch getBranch() {return _branches.get(_ndx);}
		public float getLODF() {return _lodf[_ndx];}
		public float getOrder() {return _ratc[_ndx];}
		@Override
		public int compareTo(Result o)
		{
			return Float.compare(getOrder(), o.getOrder());
		}
		
	}
	
	public List<Result> getResults()
	{
		List<Result> rv = new AbstractList<Result>()
		{
			@Override
			public Result get(int index) {return new Result(index);}

			@Override
			public int size() {return _branches.size();}
		};
		return rv;
	}
	
	/**
	 * Create a model builder that can provide a model configured with only a
	 * single generate and load on either side of the target branch
	 * 
	 * @author chris@powerdata.com
	 *
	 * TODO:  Merge this and ShiftFactorModelBuilder
	 */
	@Deprecated
	class RATCModelBuilder extends CloneModelBuilder
	{
		
		@Override
		protected void loadPrep()
		{
			super.loadPrep();
			
			/* replace entries to return our fabricated data */
			DataLoader<String[]> gname = () -> new String[] {"RATC_SRC"};
			DataLoader<float[]> fzero = () -> new float[] {0f};
			DataLoader<float[]> f200 = () -> new float[] {200f};
			DataLoader<int[]> fbus = () -> new int[] {_targ.getFromBus().getIndex()};
			DataLoader<int[]> tbus = () -> new int[] {_targ.getToBus().getIndex()};
			DataLoader<boolean[]> insvc = () -> new boolean[] {true};
			
			DataLoader<int[]> genbus=fbus, loadbus=tbus;
			if (_swapgenside)
			{
				genbus = tbus;
				loadbus = fbus;
			}
			
			
			_col.put(ColumnMeta.GenID, gname);
			_col.put(ColumnMeta.GenNAME, gname);
			_col.put(ColumnMeta.GenBUS, genbus);
			_col.put(ColumnMeta.GenP, fzero);
			_col.put(ColumnMeta.GenQ, fzero);
			_col.put(ColumnMeta.GenINSVC, insvc); 
			_col.put(ColumnMeta.GenTYPE, () -> new Gen.Type[] {Gen.Type.Thermal});
			_col.put(ColumnMeta.GenMODE, () -> new Gen.Mode[] {Gen.Mode.MAN});
			_col.put(ColumnMeta.GenOPMINP, fzero);
			_col.put(ColumnMeta.GenOPMAXP, f200);
			_col.put(ColumnMeta.GenMINQ, () -> new float[] {-100f});
			_col.put(ColumnMeta.GenMAXQ, f200);
			_col.put(ColumnMeta.GenPS, () -> new float[] {100f});
			_col.put(ColumnMeta.GenQS, fzero);
			_col.put(ColumnMeta.GenAVR, () -> new boolean[] {true});
			_col.put(ColumnMeta.GenVS, () -> new float[] {1f});
			_col.put(ColumnMeta.GenREGBUS, genbus);
		
			DataLoader<String[]> lname = () -> new String[] {"RATC_LOAD"};
			DataLoader<float[]> pld = () -> new float[]{-100f};
			DataLoader<float[]> qld = () -> new float[]{-10f};
			
			_col.put(ColumnMeta.LoadID, lname);
			_col.put(ColumnMeta.LoadNAME, lname);
			_col.put(ColumnMeta.LoadBUS, loadbus);
			_col.put(ColumnMeta.LoadP, pld);
			_col.put(ColumnMeta.LoadQ, qld);
			_col.put(ColumnMeta.LoadINSVC, insvc);
			_col.put(ColumnMeta.LoadPMAX, pld);
			_col.put(ColumnMeta.LoadQMAX, qld);

		}

		@Override
		protected LoadListI loadLoads() throws PAModelException
		{
			return new LoadListI(_m, 1);
		}



		@Override
		protected GenListI loadGens() throws PAModelException
		{
			return new GenListI(_m, 1);
		}



		public RATCModelBuilder(PAModel srcmdl)
		{
			super(srcmdl, _localCols);
			// TODO Auto-generated constructor stub
		}
		
	}
	
	static Set<ColumnMeta> _localCols = EnumSet.copyOf(Arrays.asList(new ColumnMeta[]
	{
		ColumnMeta.BusVM,
		ColumnMeta.BusVA,
		
		ColumnMeta.ShcapQ,
		ColumnMeta.ShreacQ,
		
		ColumnMeta.SvcQ,
		
		ColumnMeta.LinePFROM,
		ColumnMeta.LinePTO,
		ColumnMeta.LineQFROM,
		ColumnMeta.LineQTO,
		
		ColumnMeta.SercapPFROM,
		ColumnMeta.SercapPTO,
		ColumnMeta.SercapQFROM,
		ColumnMeta.SercapQTO,
			
		ColumnMeta.SerreacPFROM,
		ColumnMeta.SerreacPTO,
		ColumnMeta.SerreacQFROM,
		ColumnMeta.SerreacQTO,
		
		ColumnMeta.PhashPFROM,
		ColumnMeta.PhashPTO,
		ColumnMeta.PhashQFROM,
		ColumnMeta.PhashQTO,
		
		ColumnMeta.TfmrPFROM,
		ColumnMeta.TfmrPTO,
		ColumnMeta.TfmrQFROM,
		ColumnMeta.TfmrQTO,
	}));

	public static void main(String[] args) throws Exception
	{
		String uri = null;
		File poutdir = new File(System.getProperty("user.dir"));
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
			}
		}
		if (uri == null)
		{
			System.err.format("Usage: -uri model_uri "
					+ "[ --outdir output_directory (deft to $CWD ]\n");
			System.exit(1);
		}
		final File outdir = poutdir;
		if (!outdir.exists()) outdir.mkdirs();
		PflowModelBuilder bldr = PflowModelBuilder.Create(uri);
		bldr.enableFlatVoltage(true);
		bldr.setLeastX(0.0001f);
		PAModel base = bldr.load();
		RATCWorker rw = new RATCWorker(base, base.getLines().getByKey(6512));
		rw.runRATC();
//		new ListDumper().dump(rmb.load(), outdir);
	}

}
