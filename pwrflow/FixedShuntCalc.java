package com.powerdata.openpa.pwrflow;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import com.powerdata.openpa.BusList;
import com.powerdata.openpa.FixedShuntList;
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
	FixedShuntList _shunts;
	
	public FixedShuntCalc(PAModel m) throws PAModelException
	{
		_m = m;
		_buses = m.getBuses();
		_shunts = _m.getFixedShunts();
		setup(BusRefIndex.CreateFromConnectivityBus(m));
	}

	public FixedShuntCalc(PAModel m, BusRefIndex bndx, FixedShuntList fshunts) throws PAModelException
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
		FixedShuntList fshunts = m.getFixedShunts();
		BusRefIndex bri = BusRefIndex.CreateFromConnectivityBus(m);
		FixedShuntCalc fsc = new FixedShuntCalc(m, bri, fshunts);
		fsc.calc();
		
		float[] q = fsc.getQ();
		int n = q.length;
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(new File(outdir,"fixedshuntq.csv"))));
		pw.println("ID,Bus,MW");
		
		for(int i=0; i < n; ++i)
		{
			pw.format("%s,%s,%f\n", fshunts.getID(i), 
				bri.getBuses().getByBus(fshunts.getBus(i)).getName(),
				q[i]);
		}
		pw.close();


	}

}
