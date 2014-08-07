package com.powerdata.openpa.pwrflow;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;
import com.powerdata.openpa.BusList;
import com.powerdata.openpa.FixedShunt;
import com.powerdata.openpa.FixedShuntList;
import com.powerdata.openpa.FixedShuntListIfc;
import com.powerdata.openpa.PAModel;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.PflowModelBuilder;
import com.powerdata.openpa.tools.PAMath;

public class FixedShuntCalc extends CalcBase
{
	PAModel _m;
	int[] _busndx;
	float[] _b, _q;
	BusList _buses;
	FixedShuntListIfc<? extends FixedShunt> _shunts;
	
	public FixedShuntCalc(PAModel m,
			FixedShuntListIfc<? extends FixedShunt> shunts)
			throws PAModelException
	{
		_m = m;
		_buses = m.getBuses();
		_shunts = shunts;
		setup(BusRefIndex.CreateFromConnectivityBus(m));
	}

	public FixedShuntCalc(PAModel m, BusRefIndex bndx,
			FixedShuntListIfc<? extends FixedShunt> fshunts)
			throws PAModelException
	{
		_m = m;
		_buses = bndx.getBuses();
		_shunts = fshunts;
		setup(bndx);
	}
	private void setup(BusRefIndex bref) throws PAModelException
	{
		_busndx = bref.get1TBus(_shunts);
		_b = _shunts.getB();
	}

	public FixedShuntCalc calc(float[] vmpu) throws PAModelException
	{
		int nfsh = _shunts.size();
		_q = new float[nfsh];
		int[] insvc = getInSvc(_shunts);
		int nsvc = insvc.length;
		for(int in=0; in < nsvc; ++in)
		{
			int i = insvc[in];
			float v = vmpu[_busndx[i]];
			_q[i] = _b[i] * v * v; 
		}
		return this;
	}
	
	public FixedShuntCalc calc() throws PAModelException
	{
		return calc(PAMath.vmpu(_buses));
	}

	public float[] getQ()
	{
		return _q;
	}
	
	public FixedShuntListIfc<? extends FixedShunt> getList() {return _shunts;}

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
		PAModel m = bldr.load();
		
		BusRefIndex bri = BusRefIndex.CreateFromConnectivityBus(m);
		Set<FixedShuntCalc> fsc = new HashSet<>();
		for(FixedShuntList s : m.getFixedShunts())
			fsc.add(new FixedShuntCalc(m, bri, s));
		
		final float[] vm = PAMath.vmpu(m.getBuses());
		
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(new File(outdir,"fixedshuntq.csv"))));
		pw.println("ID,Bus,MW");
		for(FixedShuntCalc c : fsc)
		{
			c.calc(vm);
			float[] q = c.getQ();
			int n = q.length;
			for (int i = 0; i < n; ++i)
			{
				pw.format("%s,%s,%f\n", c.getList().getID(i), bri.getBuses()
						.getByBus(c.getList().getBus(i)).getName(), q[i]);
			}
		}
		pw.close();

		int niter = 1000;
		long ts = System.currentTimeMillis();
		for(int i=0; i < niter; ++i)
		{
			for(FixedShuntCalc c : fsc) c.calc(vm);
		}
		long te = System.currentTimeMillis() - ts;
		System.out.format("single-threaded %d iter in %d ms\n", niter, te);

		ts = System.currentTimeMillis();
		for(int i=0; i < niter; ++i)
		{
			fsc.parallelStream().forEach(c -> {try{c.calc(vm);}catch(PAModelException e){}});
		}
		te = System.currentTimeMillis() - ts;
		System.out.format("multi-threaded %d iter in %d ms\n", niter, te);

	}

}
