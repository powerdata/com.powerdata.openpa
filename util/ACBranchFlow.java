package com.powerdata.openpa.util;

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

public class ACBranchFlow
{
	PAModel _model;
	BusList _buses;
	ACBranchList _branches;
	int[] _f, _t;
	float[] _fp, _fq, _tp, _tq;
	float[] _fbch, _tbch, _bmag;
	ComplexList _z;
	
	public ACBranchFlow(PAModel m) throws PAModelException
	{
		_model = m;
		_buses = m.getBuses();
		_branches = m.getACBranches();
		setup();
	}
	
	public ACBranchFlow(PAModel m, BusList buses) throws PAModelException
	{
		_model = m;
		_buses = buses;
		_branches = m.getACBranches();
		setup();
	}
	
	public ACBranchFlow(PAModel m, BusList buses, ACBranchList branches) throws PAModelException
	{
		_model = m;
		_buses = buses;
		_branches = branches;
		setup();
	}

	void setup() throws PAModelException
	{
		int n = _branches.size();
		_f = new int[n];
		_t = new int[n];
		for(int i=0; i < n; ++i)
		{
			_f[i] = _buses.getByBus(_branches.getFromBus(i)).getIndex();
			_t[i] = _buses.getByBus(_branches.getToBus(i)).getIndex();
		}
		
		_z = new ComplexList(_branches.getR(), _branches.getX());
		_fbch = _branches.getFromBchg();
		_tbch = _branches.getToBchg();
		_bmag = _branches.getBmag();
	}

	public void calc() throws PAModelException
	{
		calc(PAMath.vmpu(_buses), PAMath.deg2rad(_buses.getVA()));
	}

	public void calc(float[] vmpu, float[] varad) throws PAModelException
	{
		int n = _branches.size();
		_fp = new float[n];
		_tp = new float[n];
		_fq = new float[n];
		_tq = new float[n];
		float[] ftap = _branches.getFromTap(), ttap = _branches.getToTap();
		float[] shift = _branches.getShift();
		boolean[] oos = _branches.isOutOfSvc();
		
		for(int i=0; i < n; ++i)
		{
			int f = _f[i], t = _t[i];
			if(!oos[i])
			{
				float fvm = vmpu[f], tvm = vmpu[t],
						fva = varad[f], tva = varad[t];
				float sh = fva - tva - shift[i];
				float ft = ftap[i], tt = ttap[i];
				float tvmpq = fvm * tvm / (ft * tt);
				float tvmp2 = fvm * fvm / (ft * ft);
				float tvmq2 = tvm * tvm / (tt * tt);
				float ctvmpq = tvmpq * (float) Math.cos(sh);
				float stvmpq = tvmpq * (float) Math.sin(sh);
				Complex y = _z.get(i).inv();
				float gcos = ctvmpq * y.re();
				float bcos = ctvmpq * y.im();
				float gsin = stvmpq * y.re();
				float bsin = stvmpq * y.im();
				float bmag = _bmag[i];

				_fp[i] = -gcos - bsin + tvmp2 * y.re();
				_fq[i] = -gsin + bcos - tvmp2 * (y.im() + bmag + _fbch[i]);
				_tp[i] = -gcos + bsin + tvmq2 * y.re();
				_tq[i] = gsin + bcos - tvmq2 * (y.im() + bmag + _tbch[i]);
			}
		}
		
	}
	
	public float[] getFromP() {return _fp;}
	public float[] getFromQ() {return _fq;}
	public float[] getToP() {return _tp;}
	public float[] getToQ() {return _tq;}

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
		
		PAModel m = PflowModelBuilder.Create(uri).load();
		ACBranchList branches = m.getACBranches();
		ACBranchFlow bc = new ACBranchFlow(m, m.getBuses(), branches);
		bc.calc();
		
		float[] fp = bc.getFromP(), fq = bc.getFromQ(),
				tp = bc.getToP(), tq = bc.getToQ();
		
		int n = fp.length;
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(new File(outdir,"acbranchflow.csv"))));
		pw.println("ID,FromMW,FromMVAr,ToMW,ToMVAr");
		for(int i=0; i < n; ++i)
		{
			pw.format("%s,%f,%f,%f,%f\n",
				branches.getID(i), fp[i]*100f, fq[i]*100f, tp[i]*100f,
				tq[i]*100f);
		}
		pw.close();
	}
}
