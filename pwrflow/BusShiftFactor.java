package com.powerdata.openpa.pwrflow;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import com.powerdata.openpa.ACBranch;
import com.powerdata.openpa.ACBranchList;
import com.powerdata.openpa.Bus;
import com.powerdata.openpa.BusList;
import com.powerdata.openpa.BusRefIndex;
import com.powerdata.openpa.Gen;
import com.powerdata.openpa.GenList;
import com.powerdata.openpa.PAModel;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.PflowModelBuilder;

/**
 * Calculate shift factor between two buses.
 * 
 * 
 * 
 * @author chris@powerdata.com
 *
 */
public class BusShiftFactor
{
	PAModel _model, _fabmodel;
	
	public BusShiftFactor(PAModel model)
	{
		_model = model;
	}
	
	public void run(Gen source, Gen sink) throws PAModelException
	{
		run(source.getBus(), sink.getBus());
	}
	
	/**
	 * Calculate shift factors
	 * @param source bus to inject 100MW
	 * @param sink  bus to absorb 100MW
	 * @throws PAModelException
	 */
	public void run(Bus source, Bus sink) throws PAModelException
	{
		/*
		 * Fabricate a PAModel, backed by the provided model, with a single
		 * generator of 100MW at the source bus, and a load of 100MW at the sink
		 * bus
		 */
		_fabmodel = new ShiftFactorModelBuilder(_model, source, sink).load();

		/*
		 * eliminate closed switches and use a single-bus topology 
		 */
		BusRefIndex bri = BusRefIndex.CreateFromSingleBuses(_model);
		
		/*
		 * Run the DC power flow. This updates results (in MW) back to the
		 * branch objects.
		 * 
		 * The BusTypeUtil object is used to determine an angle reference for
		 * each island
		 */
		new DCPowerFlow(_fabmodel, bri, new BusTypeUtil(_model, bri)).
			runPF().
			updateResults();
	}
	
	public void outputResults(PrintWriter pw) throws PAModelException
	{
		pw.println("BranchID,BranchName,FromBusID,FromBusName,ToBusID,ToBusName,ShiftFactor");
		for(ACBranchList list : _fabmodel.getACBranches())
		{
			for(ACBranch b : list)
			{
				Bus f = b.getFromBus(), t = b.getToBus();
				/*
				 * The DC power flow only calculates a single flow for each branch, so just
				 * print one side or the other. 
				 */
				pw.format("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",%f\n",
					b.getID(),
					b.getName(),
					f.getID(),
					f.getName(),
					t.getID(),
					t.getName(),
					b.getFromP()/100f);
			}
		}
	}
	
	public static void main(String...args) throws Exception
	{
		/*
		 * Process parameters
		 */
		String uri = null;
		File outfile = new File(new File(System.getProperty("user.dir")), "shiftfactors.csv");
		String srcbusid = null, sinkbusid = null, srcgenid = null, sinkgenid = null;
		for (int i = 0; i < args.length;)
		{
			String s = args[i++].toLowerCase();
			int ssx = 1;
			if (s.startsWith("--")) ++ssx;
			switch (s.substring(ssx))
			{
				case "uri":
					uri = args[i++];
					break;
				case "outfile":
					outfile = new File(args[i++]);
					break;
				case "sourcebus":
					srcbusid = args[i++];
					break;
				case "sinkbus":
					sinkbusid = args[i++];
					break;
				case "sourcegen":
					srcgenid = args[i++];
					break;
				case "sinkgen":
					sinkgenid = args[i++];
					break;
			}
		}
		if (uri == null)
			errorExit("No URI specified");
		
		File dir = outfile.getParentFile();
		if (!dir.exists()) dir.mkdirs();
		
		/*
		 * Create and configure a model builder, enforcing that branch
		 * reactance is not too close to zero.
		 */
		PflowModelBuilder bldr = PflowModelBuilder.Create(uri);
		bldr.setLeastX(0.0001f);
		
		/*
		 * Load the model
		 */
		PAModel m = bldr.load();
		
		/*
		 * Check for a valid source & sink
		 */
		Configuration cfg = new Configuration(m, srcgenid, sinkgenid, srcbusid, sinkbusid);
		if (cfg.getValid() == Configuration.Valid.No)
			errorExit(cfg.getMsg());
			
		/*
		 * Calculate the shift factors
		 */
		BusShiftFactor sf = new BusShiftFactor(m);
		if (cfg.getValid() == Configuration.Valid.Gen)
			sf.run(cfg.getSourceGen(), cfg.getSinkGen());
		else
			sf.run(cfg.getSourceBus(), cfg.getSinkBus());
		
		/*
		 * Output the results
		 */
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(outfile)));
		sf.outputResults(pw);
		pw.flush();
		pw.close();
		System.exit(0);
	}

	static void errorExit(String msg)
	{
		System.err.println(msg);
		System.err.println();
		System.err.format("Usage: --uri model_uri "
				+ "[ --outfile output_file (deft to $CWD/shiftfactors.csv ] "
				+ "[ --sourcebus source_bus_id --sinkbus sink_bus_id ] "
				+ "[ --sourcegen source_gen_id --sinkgen sink_gen_id ] \n\n");
		System.err.println("Either buses or generator id's must be specified");
		System.exit(1);

	}
	
	static class Configuration
	{
		enum Valid {No, Gen, Bus};
		
		Valid _valid = Valid.No;
		String _msg;
		Gen _srcgen, _sinkgen;
		Bus _srcbus, _sinkbus;
		
		Configuration(PAModel model, String srcgenid, String sinkgenid,
			String srcbusid, String sinkbusid) throws PAModelException
		{
			boolean gnn = srcgenid != null && !srcgenid.isEmpty() && sinkgenid != null && !sinkgenid.isEmpty();
			boolean bnn = srcbusid != null && !srcbusid.isEmpty() && sinkbusid != null && !sinkbusid.isEmpty();
			if (gnn)
			{
				GenList gens = model.getGenerators();
				_srcgen = gens.getByID(srcgenid);
				_sinkgen = gens.getByID(sinkgenid);
				if (_srcgen == null)
					_msg = String.format("No source generator found for ID %s", srcgenid);
				else if (_sinkgen == null)
					_msg = String.format("No sink generator found for ID %s", sinkgenid);
				else
					_valid = Valid.Gen;
			}
			else if (bnn)
			{
				BusList buses = model.getBuses();
				_srcbus = buses.getByID(srcbusid);
				_sinkbus = buses.getByID(sinkbusid);
				if (_srcbus == null)
					_msg = String.format("No source bus found for ID %s", srcbusid);
				else if (_sinkbus == null)
					_msg = String.format("No sink bus found for ID %s", sinkbusid);
				else
					_valid = Valid.Bus;
			}
			else
			{
				_msg = "Either a valid set of source/sink buses or generators must be specified";
			}
		}
		
		Valid getValid() {return _valid;}
		String getMsg() {return _msg;}
		Bus getSourceBus() {return _srcbus;}
		Bus getSinkBus() {return _sinkbus;}
		Gen getSourceGen() {return _srcgen;}
		Gen getSinkGen() {return _sinkgen;}
		
	}
}
