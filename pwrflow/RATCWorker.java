package com.powerdata.openpa.pwrflow;

import java.io.File;
import java.util.AbstractList;
import java.util.List;

import com.powerdata.openpa.ACBranch;
import com.powerdata.openpa.ACBranchListIfc;
import com.powerdata.openpa.Bus;
import com.powerdata.openpa.BusRefIndex;
import com.powerdata.openpa.LineList;
import com.powerdata.openpa.PAModel;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.PflowModelBuilder;

/**
 * Remedial adaptive topology control (RATC)
 * 
 * Evaluate power system to recommend switching actions that will alleviate flow
 * on specified branch by calculating a set of Line Outage Distribution Factors (LODF)
 * and applying them to the solved flows
 * 
 * 
 * 
 * The algorithm works as follows:
 * 
 * <ol>
 * <li>Fabricate and place 100MW flow across selected branch</li>
 * <li>Solve flows for fabricated case</li>
 * <li>Calculate fraction of flow in target branch</li>
 * <li>Assign remaining fracction to network, NF</li>
 * <li>LODF for each Line (branch, actually), = flow / 100 / NF</li>
 * </ol>
 * 
 * @author chris@powerdata.com
 *
 */

public class RATCWorker
{
	protected PAModel _model;
	protected BusRefIndex _bri;
	protected BusTypeUtil _btu;
	Bus _source, _sink;
	ACBranch _targ;
	
	void setEnds(ACBranch target) throws PAModelException
	{
		Bus f = target.getFromBus();
		Bus t = target.getToBus();
		if (target.getFromP() < 0f)
		{
			_source = t;
			_sink = f;
		}
		else
		{
			_source = f;
			_sink = t;
		}
	}
	
	@SuppressWarnings("deprecation")
	public RATCWorker(PAModel model, ACBranch target) throws PAModelException
	{
		System.out.format("Target: %s\n", target.getName());
		_targ = target;
		_model = model;
		setEnds(target);
		_bri = BusRefIndex.CreateFromSingleBuses(model);
		FDPowerFlow pf = new FDPowerFlow(model, _bri);
		pf.runPF();
		pf.updateResults();
//		try
//		{
//			new ListDumper().dump(_model, new File("/run/shm/actest"));
//		}
//		catch (IOException | ReflectiveOperationException | RuntimeException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		//TODO:  make the bus type util creation external to the ac power flow
		_btu = pf.getBusTypes();
	}
	
	float[] _lodf, _ratc;
	ACBranchListIfc<? extends ACBranch> _branches;
	
	
	public void runRATC() throws PAModelException
	{
		ShiftFactorModelBuilder rmb = new ShiftFactorModelBuilder(_model, _source, _sink);
		PAModel dcmodel = rmb.load();
		DCPowerFlow dcpf = new DCPowerFlow(dcmodel, _bri, _btu);
		dcpf.runPF().updateResults();
//		try
//		{
//			new ListDumper().dump(dcmodel, new File("/run/shm/dctest"));
//		}
//		catch (IOException | ReflectiveOperationException | RuntimeException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		int nlodf = _model.getLines().size();
		_lodf = new float[nlodf];
		_ratc = new float[nlodf];
		// TODO:  Update to support all AC branches
		LineList dclineflow = dcmodel.getLines(), aclineflow = _model.getLines();
		_branches = aclineflow;
		ACBranch dctarg = dclineflow.get(_targ.getIndex());
		/** fraction of artificial 100MW flow across target */
		float lnfact = Math.abs(dctarg.getFromP())/100f;
		_lodf[_targ.getIndex()] = lnfact;
		float netfrac = 1f-lnfact;
		float div = 1f/(100f*netfrac);
		for(int i=0; i < nlodf; ++i)
		{
			if (i != _targ.getIndex())
			{
				ACBranch dc = dclineflow.get(i), ac = aclineflow.get(i);
				_lodf[i] = dc.getFromP() * div;
				_ratc[i] = _lodf[i] * ac.getFromP();
			}
		}
		
//		try
//		{
//			File dir = new File("/run/shm");
//			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(new File(dir, "lodf-palco.csv"))));
//			pw.println("LineName,X,ACFlow(MW),LODF,RATC Order");
//			for(int i=0; i < nlodf; ++i)
//			{
//				ACBranch ac = aclineflow.get(i);
//				pw.format("\"%s\",%f,%f,%f,%f\n",
//					ac.getName(), ac.getX(), ac.getFromP(), _lodf[i], _ratc[i]);
//			}
//			pw.close();
//		}
//		catch(Exception x)
//		{
//			x.printStackTrace();
//		}
//		
//		
	}
	
	public class Result implements Comparable<Result>
	{
		int _ndx;
		Result(int ndx) {_ndx = ndx;}
		public ACBranch getBranch() {return _branches.get(_ndx);}
		public float getLODF() {return _lodf[_ndx];}
		public float getOrder() {return _ratc[_ndx];}
		@Override
		public int compareTo(Result o)
		{
			return Float.compare(getOrder(), o.getOrder());
		}
		
	}
	
	public List<Result> getResults()
	{
		List<Result> rv = new AbstractList<Result>()
		{
			@Override
			public Result get(int index) {return new Result(index);}

			@Override
			public int size() {return _branches.size();}
		};
		return rv;
	}
	
	public static void main(String[] args) throws Exception
	{
		String uri = null;
		File poutdir = new File(System.getProperty("user.dir"));
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
					poutdir = new File(args[i++]);
					break;
			}
		}
		if (uri == null)
		{
			System.err.format("Usage: -uri model_uri "
					+ "[ --outdir output_directory (deft to $CWD ]\n");
			System.exit(1);
		}
		final File outdir = poutdir;
		if (!outdir.exists()) outdir.mkdirs();
		PflowModelBuilder bldr = PflowModelBuilder.Create(uri);
		bldr.enableFlatVoltage(true);
		bldr.setLeastX(0.0001f);
		PAModel base = bldr.load();
		RATCWorker rw = new RATCWorker(base, base.getLines().getByKey(6512));
		rw.runRATC();
//		new ListDumper().dump(rmb.load(), outdir);
	}

}
