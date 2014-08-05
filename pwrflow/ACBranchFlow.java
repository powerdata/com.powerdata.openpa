package com.powerdata.openpa.pwrflow;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import com.powerdata.openpa.ACBranchList;
import com.powerdata.openpa.BusList;
import com.powerdata.openpa.PAModel;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.PflowModelBuilder;
import com.powerdata.openpa.tools.Complex;
import com.powerdata.openpa.tools.ComplexList;
import com.powerdata.openpa.tools.PAMath;

public class ACBranchFlow extends CalcBase
{
	public static final float SBASE = 100f;
	PAModel _model;
	BusList _buses;
	ACBranchList _branches;
	int[] _f, _t;
	float[] _fp, _fq, _tp, _tq;
	float[] _fbch, _tbch, _bmag;
	float[] _yg, _yb;
	
	float[] _ftap, _ttap, _shift;
	
	public ACBranchFlow(PAModel m) throws PAModelException
	{
		_model = m;
		_buses = m.getBuses();
		_branches = m.getACBranches();
		setup(BusRefIndex.CreateFromConnectivityBus(m));
	}
	
	public ACBranchFlow(PAModel m, BusRefIndex bndx, ACBranchList branches)
			throws PAModelException
	{
		_model = m;
		_buses = bndx.getBuses();
		_branches = branches;
		setup(bndx);
	}
	
	void setup(BusRefIndex bndx) throws PAModelException
	{
		int n = _branches.size();
		int[][] nmap = bndx.get2TBus(_branches);
		_f = nmap[0];
		_t = nmap[1];
		
		ComplexList zlist = new ComplexList(_branches.getR(), _branches.getX());
		_yg = new float[n];
		_yb = new float[n];
		for(int i=0; i < n; ++i)
		{
			Complex y = zlist.get(i).inv();
			_yg[i] = y.re();
			_yb[i] = y.im();
		}
		
		_fbch = _branches.getFromBchg();
		_tbch = _branches.getToBchg();
		_bmag = _branches.getBmag();
		_ftap = _branches.getFromTap();
		_ttap = _branches.getToTap();
		_shift = _branches.getShift();
	}

	public ACBranchFlow calc() throws PAModelException
	{
		calc(PAMath.vmpu(_buses), PAMath.deg2rad(_buses.getVA()));
		return this;
	}

	public ACBranchFlow calc(float[] vmpu, float[] varad) throws PAModelException
	{
		int nbr = _branches.size();
		_fp = new float[nbr];
		_tp = new float[nbr];
		_fq = new float[nbr];
		_tq = new float[nbr];
		
		int[] insvc = getInSvc(_branches);
		int ninsvc = insvc.length;
		
		for (int in = 0; in < ninsvc; ++in)
		{
			int i = insvc[in];
			int f = _f[i], t = _t[i];
			float fvm = vmpu[f], tvm = vmpu[t], fva = varad[f], tva = varad[t];
			float sh = fva - tva - _shift[i];
			float ft = _ftap[i], tt = _ttap[i];
			float tvmpq = fvm * tvm / (ft * tt);
			float tvmp2 = fvm * fvm / (ft * ft);
			float tvmq2 = tvm * tvm / (tt * tt);
			float ctvmpq = tvmpq * (float) Math.cos(sh);
			float stvmpq = tvmpq * (float) Math.sin(sh);
			float yg = _yg[i]; 
			float yb = _yb[i];
			float gcos = ctvmpq * yg;
			float bcos = ctvmpq * yb;
			float gsin = stvmpq * yg;
			float bsin = stvmpq * yb;
			float bmag = _bmag[i];
			_fp[i] = (-gcos - bsin + tvmp2 * yg) * SBASE;
			_fq[i] = (-gsin + bcos - tvmp2 * (yb + bmag + _fbch[i]))
					* SBASE;
			_tp[i] = (-gcos + bsin + tvmq2 * yg) * SBASE;
			_tq[i] = (gsin + bcos - tvmq2 * (yb + bmag + _tbch[i])) * SBASE;
		}
		return this;
	}
	
	public float[] getFromP() {return _fp;}
	public float[] getFromQ() {return _fq;}
	public float[] getToP() {return _tp;}
	public float[] getToQ() {return _tq;}

	public void update() throws PAModelException
	{
		_branches.setFromP(_fp);
		_branches.setFromQ(_fq);
		_branches.setToP(_tp);
		_branches.setToQ(_tq);
	}
	
	public static void main(String[] args) throws Exception
	{
		String uri = null;
		File outdir = new File(System.getProperty("user.dir"));
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
					outdir = new File(args[i++]);
					break;
			}
		}
		if (uri == null)
		{
			System.err.format("Usage: -uri model_uri "
					+ "[ --outdir output_directory (deft to $CWD ]\n");
			System.exit(1);
		}
		
		PflowModelBuilder bldr = PflowModelBuilder.Create(uri);
		bldr.enableFlatVoltage(false);
		PAModel m = bldr.load();
		ACBranchList branches = m.getACBranches();
		BusRefIndex bri = BusRefIndex.CreateFromSingleBus(m);
		ACBranchFlow bc = new ACBranchFlow(m, bri, branches);
		BusList sbus = m.getSingleBus();
		float[] vm = PAMath.vmpu(sbus);
		float[] va = PAMath.deg2rad(sbus.getVA());
		bc.calc(vm, va);
		
		float[] fp = bc.getFromP(), fq = bc.getFromQ(),
				tp = bc.getToP(), tq = bc.getToQ();
		
		int n = fp.length;
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(new File(outdir,"acbranchflow.csv"))));
		pw.println("ID,FromBus,FromMW,FromMVAr,ToBus,ToMW,ToMVAr");
		
		for(int i=0; i < n; ++i)
		{
			pw.format("%s,%s,%f,%f,%s,%f,%f\n", branches.getID(i), 
				bri.getBuses().getByBus(branches.getFromBus(i)).getName(),
				fp[i], fq[i], 
				bri.getBuses().getByBus(branches.getToBus(i)).getName(),
				tp[i], tq[i]);
		}
		pw.close();
		
		long ts = System.currentTimeMillis();
		int niter = 1000;
		for(int i=0; i < niter; ++i)
		{
//			ACBranchFlow bc2 = new ACBranchFlow(m, bri, branches);
			bc.calc(vm, va);
		}
		long te = System.currentTimeMillis() - ts;
		System.out.format("%d iter in %d ms\n", niter, te);
	}
}
