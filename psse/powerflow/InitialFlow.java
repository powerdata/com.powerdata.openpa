package com.powerdata.openpa.psse.powerflow;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import com.powerdata.openpa.psse.ACBranch;
import com.powerdata.openpa.psse.ACBranchList;
import com.powerdata.openpa.psse.Bus;
import com.powerdata.openpa.psse.PsseModel;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.psse.util.BusGroup2TDevList;
import com.powerdata.openpa.psse.util.ImpedanceFilter;
import com.powerdata.openpa.psse.util.MinZMagFilter;

public class InitialFlow
{
	public interface FlowWriter
	{
		void write(String p, int px, String q, int qx, String name, float mw, float mvar);
	}
	
	PsseModel _model;
	FlowWriter _wr;
	
	public InitialFlow(PsseModel model, FlowWriter writer)
	{
		_model = model;
		_wr = writer;
	}
	
	public void write() throws PsseModelException
	{
		ACBranchList branches = _model.getBranches();
		PowerCalculator pc = new PowerCalculator(_model, 
			new MinZMagFilter(branches, 0.001f), 
			new BusGroup2TDevList(_model));
		float[][] v = pc.getRTVoltages();
		float[][] flows = pc.calcACBranchFlows(branches, v[0], v[1], pc.getImpedanceFilter());
		int n = branches.size();
		for(int i=0; i < n; ++i)
		{
			ACBranch br = branches.get(i);
			Bus fb = br.getFromBus(), tb = br.getToBus();
			_wr.write(fb.getObjectID(), fb.getIndex(), tb.getObjectID(),
				tb.getIndex(), br.getObjectID(), flows[0][i] * 100f, flows[1][i] * 100f);
			_wr.write(tb.getObjectID(), tb.getIndex(), fb.getObjectID(),
				fb.getIndex(), br.getObjectID(), flows[2][i] * 100f, flows[3][i] * 100f);
		}
	}

	
	public static void main(String[] args) throws Exception
	{
		String uri = null;
		PrintWriter pws = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

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
				case "output":
					pws = new PrintWriter(new BufferedWriter(new FileWriter(args[i++])));
					break;
			}
		}
		
		final PrintWriter pw = pws;
		
		if (uri == null)
		{
			System.err.format("Usage: -uri model_uri [ -output path_to_output_file ]");
			System.exit(1);
		}
		
		pw.println("FromBus,FBusNdx,ToBus,TBusNdx,ID,MW,MVAr");
		
		InitialFlow ifl = new InitialFlow(PsseModel.Open(uri), new FlowWriter()
		{
			@Override
			public void write(String p, int px, String q, int qx, String name, float mw, float mvar)
			{
				pw.format("%s,%d,%s,%d,%s,%f,%f\n",
					p, px, q, qx, name, mw, mvar);
			}
		});
		
		ifl.write();
		
		pw.close();
	}

}
