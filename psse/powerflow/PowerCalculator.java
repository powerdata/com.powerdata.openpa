package com.powerdata.openpa.psse.powerflow;

import java.io.File;
import java.io.FilenameFilter;
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
import com.powerdata.openpa.tools.Complex;

public class PowerCalculator
{
	PsseModel _model;
	MismatchReport _dbg;
	
	public PowerCalculator(PsseModel model)
	{
		_model = model;
		_dbg = null;
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
				qfrm[i] = -(-gsin + bcos - tvmp2 * (y.im() + br.getBmag()+br.getFromBchg()));
				pto[i] = -(-gcos + bsin + tvmq2 * y.re());
				qto[i] = -(gsin + bcos - tvmq2 * (y.im() + br.getBmag()+br.getToBchg()));
			}
		}
		if (_dbg != null) _dbg.setBranchFlows(pfrm, qfrm, pto, qto);
		return new float[][] {pfrm, qfrm, pto, qto};
	}
	
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
		applyShunts(qmm, calcShunts(vm), _model.getShunts());
		applyShunts(qmm, calcSVCs(vm), _model.getSvcs());
		for(Load l : ldlist)
		{
			if (l.isInSvc())
			{
				int bndx = l.getBus().getIndex();
				pmm[bndx] -= l.getPpu();
				qmm[bndx] -= l.getQpu();
			}
		}
		for(Gen g : genlist)
		{
			Bus b = g.getBus();
			BusTypeCode btc = b.getBusType();
			if (g.isInSvc()/* && btc != BusTypeCode.Slack*/)
			{
				int bndx = b.getIndex();
				pmm[bndx] += g.getPpu();
				if (btc == BusTypeCode.Load)
					qmm[bndx] += g.getQpu();
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

	public float[] calcShunts(float[] vm) throws PsseModelException
	{
		ShuntList shunts = _model.getShunts();
		int nsh = shunts.size();
		float[] q = new float[nsh];

		for (int i = 0; i < nsh; ++i)
		{
			Shunt shunt = shunts.get(i);
			int bndx = shunt.getBus().getIndex();
			float bvm = vm[bndx];
			q[i] = shunt.getBpu() * bvm * bvm;
		}
		if (_dbg != null) _dbg.setShunts(q);
		return q;
	}

	public float[] calcSVCs(float[] vm) throws PsseModelException
	{
		SvcList svcs = _model.getSvcs();
		int nsh = svcs.size();
		float[] q = new float[nsh];

		for (int i = 0; i < nsh; ++i)
		{
			SVC svc = svcs.get(i);
			int bndx = svc.getBus().getIndex();
			float bvm = vm[bndx];
			q[i] = svc.getBINIT() / 100f * bvm * bvm;
		}
		if (_dbg != null) _dbg.setSVCs(q);
		return q;
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
	
	public static void main(String[] args) throws Exception
	{
		String uri = null;

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
			}
		}
		
		if (uri == null)
		{
			System.err.format("Usage: -uri model_uri");
			System.exit(1);
		}
		
		PsseModel model = PsseModel.Open(uri);

		File tdir = new File(System.getProperty("java.io.tmpdir"));
		File[] list = tdir.listFiles(new FilenameFilter()
		{
			@Override
			public boolean accept(File dir, String name)
			{
				return name.startsWith("mismatch") && name.endsWith(".csv");
			}
		});
		for (File f : list) f.delete();
		
		
		
		MismatchReport mmr = new MismatchReport(model, tdir);
		PowerCalculator pc = new PowerCalculator(model, mmr);
		pc.calculateMismatches(pc.getRTVoltages());
		mmr.report("");
	}
}

