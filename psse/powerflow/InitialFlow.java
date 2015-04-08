package com.powerdata.openpa.psse.powerflow;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import com.powerdata.openpa.psse.ACBranch;
import com.powerdata.openpa.psse.ACBranchList;
import com.powerdata.openpa.psse.Bus;
import com.powerdata.openpa.psse.BusList;
import com.powerdata.openpa.psse.PsseModel;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.psse.TwoTermDCLine;
import com.powerdata.openpa.psse.TwoTermDCLineList;
import com.powerdata.openpa.psse.util.BusGroup2TDevList;
import com.powerdata.openpa.psse.util.MinZMagFilter;

public class InitialFlow
{
	public interface FlowWriter
	{
		void write(String p, int px, String q, int qx,
			String id, String name, float mw,
			float mvar) throws PsseModelException;
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
			new MinZMagFilter(branches, 0.0001f), 
			new BusGroup2TDevList(_model));
		float[][] v = pc.getRTVoltages();
		float[][] flows = pc.calcACBranchFlows(branches, v[0], v[1], pc.getImpedanceFilter());
		int n = branches.size();
		for(int i=0; i < n; ++i)
		{
			ACBranch br = branches.get(i);
			Bus fb = br.getFromBus(), tb = br.getToBus();
			_wr.write(fb.getObjectID(), fb.getIndex(), tb.getObjectID(),
				tb.getIndex(), br.getObjectID(), br.getFullName(), flows[0][i] * 100f, flows[1][i] * 100f);
			_wr.write(tb.getObjectID(), tb.getIndex(), fb.getObjectID(),
				fb.getIndex(), br.getObjectID(), br.getFullName(), flows[2][i] * 100f, flows[3][i] * 100f);
		}
		
		
		TwoTermDCLineList dclines = _model.getTwoTermDCLines();
		TwoTermDCLineResultList results = pc.calcTwoTermDCLines(dclines, v[1]);
		int ndc = dclines.size();
		for(int i=0; i < ndc; ++i)
		{
			TwoTermDCLine line = dclines.get(i);
			TwoTermDCLineResult lres = results.get(i);
			Bus fb = line.getFromBus(), tb = line.getToBus();
			_wr.write(fb.getObjectID(), fb.getIndex(), tb.getObjectID(),
				tb.getIndex(), line.getObjectID(), line.getFullName(),
				lres.getMWR()*100f, lres.getMVArR()*100f);
			_wr.write(tb.getObjectID(), tb.getIndex(), fb.getObjectID(),
				fb.getIndex(), line.getObjectID(), line.getFullName(),
				lres.getMWI()*100f, lres.getMVArI()*100f);
		}
	}

	
	public static void main(String[] args) throws Exception
	{
		String uri = null;
		PrintWriter pws = new PrintWriter(new BufferedWriter(
				new OutputStreamWriter(System.out)));
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
		
		pw.println("FromBus,FName,FBusNdx,ToBus,TName,TBusNdx,ID,Name,MW,MVAr");
		final PsseModel model = PsseModel.Open(uri);
		InitialFlow ifl = new InitialFlow(model, new FlowWriter()
		{
			@Override
			public void write(String p, int px, String q,
				int qx, String id, String name, float mw, float mvar) throws PsseModelException
			{
				BusList b = model.getBuses();
				pw.format("'%s','%s',%d,'%s','%s',%d,'%s','%s',%f,%f\n", p, b.get(px).getNAME(), px, q, b.get(qx).getNAME(),qx, id, name,
					mw, mvar);
			}
		});		
		ifl.write();
		
		pw.close();
	}

}
