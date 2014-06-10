package com.powerdata.openpa.psse.powerflow;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import com.powerdata.openpa.psse.PsseModel;

public class InitialFlow
{
	public interface FlowWriter
	{
		void write(String p, int px, String q, int qx, float mw, float mvar);
	}
	
	public InitialFlow(PsseModel model, FlowWriter writer)
	{
		
	}
	
	public void write()
	{
		
	}

	
	public static void main(String[] args) throws Exception
	{
		String uri = null;
		PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

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
					pw = new PrintWriter(new BufferedWriter(new FileWriter(args[i++])));
					break;
			}
		}
		
		if (uri == null)
		{
			System.err.format("Usage: -uri model_uri [ -output path_to_output_file ]");
			System.exit(1);
		}
		
		InitialFlow ifl = new InitialFlow(PsseModel.Open(uri), new FlowWriter()
		{
			@Override
			public void write(String p, int px, String q, int qx, float mw, float mvar)
			{
				// TODO Auto-generated method stub
				
			}
		});
		
		ifl.write();
		
		pw.close();
	}

}
