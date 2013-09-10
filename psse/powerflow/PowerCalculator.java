package com.powerdata.openpa.psse.powerflow;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.List;

import com.powerdata.openpa.psse.ACBranch;
import com.powerdata.openpa.psse.ACBranchList;
import com.powerdata.openpa.psse.Bus;
import com.powerdata.openpa.psse.BusList;
import com.powerdata.openpa.psse.BusTypeCode;
import com.powerdata.openpa.psse.Gen;
import com.powerdata.openpa.psse.GenList;
import com.powerdata.openpa.psse.Load;
import com.powerdata.openpa.psse.LoadList;
import com.powerdata.openpa.psse.OneTermDev;
import com.powerdata.openpa.psse.PsseModel;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.psse.SVC;
import com.powerdata.openpa.psse.Shunt;
import com.powerdata.openpa.psse.ShuntList;
import com.powerdata.openpa.psse.SvcList;
import com.powerdata.openpa.psseraw.Psse2CSV;
import com.powerdata.openpa.psseraw.PsseHeader;
import com.powerdata.openpa.tools.Complex;

public class PowerCalculator
{
	PsseModel _model;
	MismatchReport _dbg;
	
	public PowerCalculator(PsseModel model)
	{
		_model = model;
	}

	public PowerCalculator(PsseModel model, MismatchReport dbg)
	{
		_model = model;
		_dbg = dbg;
	}
	
	public float[][] calcACBranchFlows(float[] vang, float[] vmag) throws PsseModelException
	{
		ACBranchList brlist = _model.getBranches();
		int nbr = brlist.size();
		float[] pfrm = new float[nbr], pto = new float[nbr],
				qfrm = new float[nbr], qto = new float[nbr];
		for (int i=0; i < nbr; ++i)
		{
			ACBranch br = brlist.get(i);
			if (br.isInSvc())
			{
				int fbndx = br.getFromBus().getIndex();
				int tbndx = br.getToBus().getIndex();
				float fvm = vmag[fbndx], tvm = vmag[tbndx],
						fva = vang[fbndx], tva = vang[tbndx];
				
				float shift = fva - tva - br.getPhaseShift();

				float tvmpq = fvm * tvm
						/ (br.getFromTap() * br.getToTap());
				float tvmp2 = fvm * fvm
						/ (br.getFromTap() * br.getFromTap());
				float tvmq2 = tvm * tvm / (br.getToTap() * br.getToTap());

				float ctvmpq = tvmpq * (float) Math.cos(shift);
				float stvmpq = tvmpq * (float) Math.sin(shift);

				Complex y = br.getY();
				float gcos = ctvmpq * y.re();
				float bcos = ctvmpq * y.im();
				float gsin = stvmpq * y.re();
				float bsin = stvmpq * y.im();

				pfrm[i] = -(-gcos - bsin + tvmp2 * y.re());
				qfrm[i] = -(-gsin + bcos - tvmp2 * (y.im() + br.getFromYcm().im()));
				pto[i] = -(-gcos + bsin + tvmq2 * y.re());
				qto[i] = -(gsin + bcos - tvmq2 * (y.im() + br.getToYcm().im()));
			}
		}
		if (_dbg != null) _dbg.setBranchFlows(pfrm, qfrm, pto, qto);
		return new float[][] {pfrm, qfrm, pto, qto};
	}
	
//	public static void dumpBranchesToCSV(File csv, ACBranchList branches)
//			throws IOException, PsseModelException
//	{
//		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(csv)));
//		pw.println("I,J,ObjectID,ObjectName,R,X,G,B,Bcm,FromVM,FromVA,ToVM,ToVA,Shift,a,pp,qp,pq,qq");
//		for(ACBranch branch: branches)
//		{
//			if (branch.isInSvc())
//			{
//				Bus frbus = branch.getFromBus();
//				Bus tobus = branch.getToBus();
//				Complex z = branch.getZ();
//				Complex y = branch.getY();
//				Complex ycm = branch.getFromYcm().add(branch.getToYcm());
//				PComplex frv = frbus.getVoltage(),
//						 tov = tobus.getVoltage();
//////				Complex frs = branch.getRTFromS(),
//////						tos = branch.getRTToS();
////				
////				pw.format("\"%s\",\"%s\",\"%s\",\"%s\",%f,%f,%f,%f,%f,%f,%f,%f,%f,%f,%f,%f,%f,%f,%f\n",
////						frbus.getObjectID(),
////						tobus.getObjectID(),
////						branch.getObjectID(),
////						branch.getObjectName(),
////						z.re(), z.im(),
////						y.re(),y.im(), ycm.im(),
////						frv.r(), PAMath.rad2deg(frv.theta()),
////						tov.r(), PAMath.rad2deg(tov.theta()),
////						branch.getPhaseShift(),
////						branch.getFromTap(),
////						frs.re(), frs.im(),
////						tos.re(), tos.im());
//			}
//		}
//		
//		pw.close();
//	}
//	
	public float[][] calculateMismatches(float[][] v) throws PsseModelException
	{
		return calculateMismatches(v[0], v[1]);
	}
	
	public float[][] calculateMismatches(float[] va, float[] vm)
			throws PsseModelException
	{
		BusList blist = _model.getBuses();
		int nbus = blist.size();
		float[] pmm = new float[nbus], qmm = new float[nbus];
		LoadList ldlist = _model.getLoads();
		GenList genlist = _model.getGenerators();
		
		applyBranches(pmm, qmm, calcACBranchFlows(va, vm));
		applyShunts(qmm, calcShunts(), _model.getShunts());
		applyShunts(qmm, calcSVCs(), _model.getSvcs());
		for(Load l : ldlist)
		{
			if (l.isInSvc())
			{
				int bndx = l.getBus().getIndex();
				pmm[bndx] -= l.getRTP();
				qmm[bndx] -= l.getRTQ();
			}
		}
		for(Gen g : genlist)
		{
			Bus b = g.getBus();
			BusTypeCode btc = b.getBusType();
			if (g.isInSvc()/* && btc != BusTypeCode.Slack*/)
			{
				int bndx = b.getIndex();
				pmm[bndx] += g.getRTP();
				if (btc == BusTypeCode.Load)
					qmm[bndx] += g.getRTQ();
			}
		}
		if (_dbg != null)
		{
			_dbg.setMismatches(pmm, qmm);
			_dbg.setVoltages(va, vm);
		}
		return new float[][] {pmm, qmm};
	}

	void applyBranches(float[] pmm, float[] qmm,
			float[][] brflows) throws PsseModelException
	{
		ACBranchList brlist = _model.getBranches();
		int nbr = brlist.size();
		for(int i=0; i < nbr; ++i)
		{
			ACBranch branch = brlist.get(i);
			int fbndx = branch.getFromBus().getIndex();
			int tbndx = branch.getToBus().getIndex();
			pmm[fbndx] += brflows[0][i];
			qmm[fbndx] += brflows[1][i];
			pmm[tbndx] += brflows[2][i];
			qmm[tbndx] += brflows[3][i];
		}
	}

	void applyShunts(float[] qmm, float[] qshunt,
			List<? extends OneTermDev> list) throws PsseModelException
	{
		for (int i = 0; i < list.size(); ++i)
		{
			int bndx = list.get(i).getBus().getIndex();
			qmm[bndx] += qshunt[i];
		}
	}

	public float[] calcShunts() throws PsseModelException
	{
		ShuntList shunts = _model.getShunts();
		int nsh = shunts.size();
		float[] q = new float[nsh];

		for (int i = 0; i < nsh; ++i)
		{
			Shunt shunt = shunts.get(i);
			float vm = shunt.getBus().getVoltage().r();
			q[i] = shunt.getBpu() * vm * vm;
		}
		if (_dbg != null) _dbg.setShunts(q);
		return q;
	}

	public float[] calcSVCs() throws PsseModelException
	{
		SvcList svcs = _model.getSvcs();
		int nsh = svcs.size();
		float[] q = new float[nsh];

		for (int i = 0; i < nsh; ++i)
		{
			SVC svc = svcs.get(i);
			float vm = svc.getBus().getVoltage().r();
			// TODO: Fix for SVC, only treating as a fixed shunt for now
			q[i] = svc.getBINIT() / 100f * vm * vm;
		}
		if (_dbg != null) _dbg.setSVCs(q);
		return q;
	}
	
	public static void main(String[] args) throws Exception
	{
		File cwd = new File(System.getProperty("user.dir"));
		File csvdir = null;
		File pssefile = null;
		File outdir = cwd;
		int narg = args.length;
		for (int i = 0; i < narg;)
		{
			String a = args[i++];
			if (a.startsWith("-"))
			{
				int idx = (a.charAt(1) == '-') ? 2 : 1;
				switch (a.substring(idx))
				{
					case "psse": pssefile = new File(args[i++]); break;
					case "csvdir": csvdir = new File(args[i++]); break;
					case "outdir": outdir = new File(args[i++]); break;
				}
			}
		}

		File tmpdir = new File(System.getProperty("java.io.tmpdir"));

		if (csvdir == null)
		{
			if (pssefile==null)
			{
				pssefile = cwd.listFiles(new FilenameFilter()
				{
					@Override
					public boolean accept(File arg0, String arg1)
					{
						return arg1.endsWith(".raw");
					}
				})[0];
			}
		
			String pssefn = pssefile.getName();
			File tmpsub = new File(tmpdir, pssefn.substring(0, pssefn.length()-4));
			if (!tmpsub.exists()) tmpsub.mkdirs();
			
			Reader rpsse = new BufferedReader(new FileReader(pssefile));
			Psse2CSV p2c = new Psse2CSV(rpsse, null, tmpsub);
			
			PsseHeader hdr = p2c.getHeader();
			System.out.println("Loading File: "+pssefile);
			System.out.println("Change Code: "+hdr.getChangeCode());
			System.out.println("System Base MVA: "+hdr.getSystemBaseMVA());
			System.out.format("Case Time: %tc\n", hdr.getCaseTime());
			System.out.format("Import Time: %tc\n", hdr.getImportTime());
			System.out.println("Heading 1: "+hdr.getHeading1());
			System.out.println("Heading 2: "+hdr.getHeading2());
			System.out.println("Version: "+hdr.getVersion());
			
			p2c.process();
			rpsse.close();
			p2c.cleanup();
			csvdir = tmpsub;
		}
		
		PsseModel model = PsseModel.OpenInput("pssecsv:path="+csvdir);
		PrintWriter mmout = new PrintWriter(new BufferedWriter(new FileWriter(new File(outdir, "mismatch.csv"))));
		MismatchReport mmr = new MismatchReport(model, mmout);
		PowerCalculator pcalc = new PowerCalculator(model, mmr);
		pcalc.calculateMismatches(pcalc.getRTVoltages());
		mmr.report();
		mmout.close();
	}

	public float[][] getRTVoltages() throws PsseModelException
	{
		BusList blist = _model.getBuses();
		int nbus = blist.size();
		float[] va = new float[nbus], vm = new float[nbus];
		for(int i=0; i < nbus; ++i)
		{
			va[i] = blist.getRTVAng(i);
			vm[i] = blist.getRTVMag(i);
		}
		return new float[][] {va, vm};
	}
	
}

