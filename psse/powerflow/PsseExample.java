package com.powerdata.openpa.psse.powerflow;

import java.io.File;
import java.io.PrintWriter;

import com.powerdata.openpa.psse.BusList;
import com.powerdata.openpa.psse.Line;
import com.powerdata.openpa.psse.LineList;
import com.powerdata.openpa.psse.PsseModel;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.tools.PAMath;

/**
 * This example shows how to load in a PSS/e file, and calculate branch flows for NontransformerBranch(Line)
 * 
 * This application takes a single parameter, a path to a psse raw file
 * 
 * @author chris@powerdata.com
 * 
 */
public class PsseExample
{
	PsseModel _model;
	float[] _va, _vm;
	
	public PsseExample(PsseModel model) throws PsseModelException
	{
		_model = model;
		
		/*
		 * the PowerCalculator class methods prefer arrays for bus voltage angle and magnitude
		 */
		BusList buses = _model.getBuses();
		int nbus = buses.size();
		_va = new float[nbus];
		_vm = new float[nbus];
		for(int i=0; i < nbus; ++i)
		{
			/* get angle in radians */
			_va[i] = buses.getVArad(i);
			/* get magnitude per-unit on bus KV */
			_vm[i] = buses.getVMpu(i);
		}
	}
	
	public void calculateLineFlows(PrintWriter out) throws PsseModelException
	{
		/*
		 * PowerCalculator contains utilities to calculate line flows
		 */
		PowerCalculator pcalc = new PowerCalculator(_model);
		
		/*
		 * calculate flows for just nontransformer branches, any list of objects
		 * maintaining the ACBranch interface will work.  
		 */
		LineList lines = _model.getLines();
		float[][] results = pcalc.calcACBranchFlows(lines, _va, _vm);

		/*
		 * Results come back as an array of arrays, from-side active power,
		 * from-side reactive power, to-side active power, and to-side reactive
		 * power
		 */
		float[] pfrom = results[0];
		float[] qfrom = results[1];
		float[] pto = results[2];
		float[] qto = results[3];
		
		/*
		 * report results
		 */
		out.println("Line                          FromMW  FromMVAr  ToMW  ToMVAr");
		for (Line l : lines)
		{
			int i = l.getIndex();
			out.format("%-30s %6.2f %6.2f %6.2f %6.2f\n", 
					l.getObjectName(), PAMath.pu2mw(pfrom[i]),
					PAMath.pu2mvar(qfrom[i]),
					PAMath.pu2mw(pto[i]),
					PAMath.pu2mvar(qto[i]));
		}
		out.flush();
	}

	public static void main(String[] args) throws Exception
	{
		String fname = args[0];
		File praw = new File(fname);
		if (!praw.exists())
		{
			System.err.format("No raw file found at: %s", fname);
			System.exit(1);
		}

		/*
		 * Open an instance of PsseModel that reads a PSS/e raw file. For a more
		 * advanced example that allows other sources of data besides just raw
		 * files, see FastDecoupledPowerFlow.main()
		 */
		PsseModel model = PsseModel.Open(String.format("psseraw:file=%s", fname));

		PsseExample example = new PsseExample(model);
		PrintWriter out = new PrintWriter(System.out);
		
		/*
		 * Calculate and display branch flows on Nontransformer Branches
		 */
		example.calculateLineFlows(out);
	}
}

