package com.powerdata.openpa.pwrflow;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashSet;
import com.powerdata.openpa.ACBranch;
import com.powerdata.openpa.ACBranchList;
import com.powerdata.openpa.ACBranchListIfc;
import com.powerdata.openpa.BusList;
import com.powerdata.openpa.PAModel;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.PflowModelBuilder;
import com.powerdata.openpa.tools.ComplexList;
import com.powerdata.openpa.tools.PAMath;

public class ACBranchFlow extends CalcBase
{
	public float _sbase;
	BusList _buses;
	ACBranchListIfc<? extends ACBranch> _branches;
	int[] _f, _t;
	float[] _fp, _fq, _tp, _tq;
	ComplexList _y;
	
	public ACBranchFlow(float sbase, BusRefIndex bndx,
			ACBranchListIfc<? extends ACBranch> branches, ComplexList y)
			throws PAModelException
	{
		super(branches);
		_sbase = sbase;
		_buses = bndx.getBuses();
		_branches = branches;
		_y = y;
		setup(bndx);
	}
	
	void setup(BusRefIndex bndx) throws PAModelException
	{
		int[][] nmap = bndx.get2TBus(_branches);
		_f = nmap[0];
		_t = nmap[1];
	}

	public ACBranchFlow calc() throws PAModelException
	{
		calc(PAMath.vmpu(_buses), PAMath.deg2rad(_buses.getVA()));
		return this;
	}

	@Override
	public void calc(float[] varad, float[] vmpu)
	{
		int n = _branches.size();
		_fp = new float[n];
		_tp = new float[n];
		_fq = new float[n];
		_tq = new float[n];
		float[] ygbr = _y.re(), ybbr = _y.im();
		try
		{
			int[] insvc = getInSvc();
			int ninsvc = insvc.length;
			float[] shift = PAMath.deg2rad(_branches.getShift()),
					ftap = _branches.getFromTap(),
					ttap = _branches.getToTap(),
					bmagl = _branches.getBmag(),
					fbch = _branches.getFromBchg(),
					tbch = _branches.getToBchg();
			for (int in = 0; in < ninsvc; ++in)
			{
				int i = insvc[in];
				int f = _f[i], t = _t[i];
				float fvm = vmpu[f], tvm = vmpu[t], fva = varad[f], tva = varad[t];
				float sh = fva - tva - shift[i];
				float ft = ftap[i], tt = ttap[i];
				float tvmpq = fvm * tvm / (ft * tt);
				float tvmp2 = fvm * fvm / (ft * ft);
				float tvmq2 = tvm * tvm / (tt * tt);
				float ctvmpq = tvmpq * (float) Math.cos(sh);
				float stvmpq = tvmpq * (float) Math.sin(sh);
				float yg = ygbr[i]; 
				float yb = ybbr[i];
				float gcos = ctvmpq * yg;
				float bcos = ctvmpq * yb;
				float gsin = stvmpq * yg;
				float bsin = stvmpq * yb;
				float bmag = bmagl[i];
				_fp[i] = -gcos - bsin + tvmp2 * yg;
				_fq[i] = -gsin + bcos - tvmp2 * (yb + bmag + fbch[i]);
				_tp[i] = -gcos + bsin + tvmq2 * yg;
				_tq[i] = gsin + bcos - tvmq2 * (yb + bmag + tbch[i]);
			}
		}
		catch(PAModelException e)
		{
			_e = e;
		}
	}
	
	public ACBranchListIfc<? extends ACBranch> getList() {return _branches;}
	
	public float[] getFromP() {return _fp;}
	public float[] getFromQ() {return _fq;}
	public float[] getToP() {return _tp;}
	public float[] getToQ() {return _tq;}

	public void update() throws PAModelException
	{
		_branches.setFromP(PAMath.pu2mva(_fp, _sbase));
		_branches.setFromQ(PAMath.pu2mva(_fq, _sbase));
		_branches.setToP(PAMath.pu2mva(_tp, _sbase));
		_branches.setToQ(PAMath.pu2mva(_tq, _sbase));
	}
	
	public int[] getFromBus() {return _f;}
	public int[] getToBus() {return _t;}
	
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
		BusRefIndex bri = BusRefIndex.CreateFromSingleBus(m);
		BusList sbus = m.getSingleBus();
		float[] vm = PAMath.vmpu(sbus);
		float[] va = PAMath.deg2rad(sbus.getVA());

		if (!outdir.exists()) outdir.mkdirs();
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
			new File(outdir, "acbranchflow.csv"))));
		pw.println("ID,FromBus,FromMW,FromMVAr,ToBus,ToMW,ToMVAr");

		HashSet<ACBranchFlow> flows = new HashSet<>();
		for(ACBranchList l : m.getACBranches())
			flows.add(new BranchComposite(m.getSBASE(), l, bri).getCalc());
		
		for(ACBranchFlow bc : flows)
		{
			bc.calc(va, vm);
			ACBranchListIfc<? extends ACBranch> branches = bc.getList();
			float[] fp = bc.getFromP(), fq = bc.getFromQ(), tp = bc.getToP(), tq = bc
					.getToQ();
			int n = fp.length;
			for (int i = 0; i < n; ++i)
			{
				pw.format("%s,%s,%f,%f,%s,%f,%f\n", branches.getID(i), bri
						.getBuses().getByBus(branches.getFromBus(i)).getName(),
					fp[i], fq[i], bri.getBuses().getByBus(branches.getToBus(i))
							.getName(), tp[i], tq[i]);
			}
		}
		pw.close();

		int niter = 1000;
		long ts = System.currentTimeMillis();
		
		for (int i = 0; i < niter; ++i)
		{
			for(ACBranchFlow f : flows) f.calc(va, vm);
		}

		long te = System.currentTimeMillis() - ts;
		System.out.format("single-threaded %d iter in %d ms\n", niter, te);

		
		ts = System.currentTimeMillis();
		
		for (int i = 0; i < niter; ++i)
		{
			flows.parallelStream().forEach(t -> t.calc(va, vm));
		}

		te = System.currentTimeMillis() - ts;
		System.out.format("multi-threaded %d iter in %d ms\n", niter, te);
		
	}

	@Override
	public void applyMismatches(float[] pmm, float[] qmm)
	{
		int n = _f.length;
		for(int i=0; i < n; ++i)
		{
			int fx = _f[i], tx = _t[i];
			pmm[fx] += _fp[i];
			pmm[tx] += _tp[i];
			qmm[fx] += _fq[i];
			qmm[tx] += _tq[i];
		}
	}

}
