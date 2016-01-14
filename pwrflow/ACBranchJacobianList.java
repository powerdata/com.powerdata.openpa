package com.powerdata.openpa.pwrflow;

import java.io.File;
import java.util.List;
import com.powerdata.openpa.ACBranch;
import com.powerdata.openpa.ACBranchList;
import com.powerdata.openpa.ACBranchListIfc;
import com.powerdata.openpa.BusList;
import com.powerdata.openpa.BusRefIndex;
import com.powerdata.openpa.PAModel;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.PflowModelBuilder;
import com.powerdata.openpa.tools.Complex;
import com.powerdata.openpa.tools.PAMath;
import com.powerdata.openpa.tools.matrix.JacobianArrayList;
import com.powerdata.openpa.tools.matrix.JacobianElement;
import com.powerdata.openpa.tools.matrix.JacobianList;
import com.powerdata.openpa.tools.matrix.JacobianMatrix;

/**
 * Compute the Jacobian for each AC Branch
 * 
 * @author chris@powerdata.com
 *
 */

public class ACBranchJacobianList extends ACBranchExtListI
	<com.powerdata.openpa.pwrflow.ACBranchJacobianList.ACBranchJacobian>
{
	/**
	 * Provide access to calculated jacobian values from this list.
	 * @author chris@powerdata.com
	 *
	 */
	public class ACBranchJacobian extends ACBranchExtList.ACBranchExt
	{
		ACBranchJacobian(int index)
		{
			super(ACBranchJacobianList.this, index);
		}
	
		/** get partial derivatives of from-side power with respect to from-side voltage */
		public JacobianElement getFromSelf() {return ACBranchJacobianList.this.getFromSelf(_ndx);}
		/** get partial derivatives of to-side power with respect to to-side voltage */
		public JacobianElement getToSelf() {return ACBranchJacobianList.this.getToSelf(_ndx);}
		/** get partial derivatives of from-side power with respect to to-side voltage */
		public JacobianElement getFromMutual() {return ACBranchJacobianList.this.getFromMutual(_ndx);}
		/** get partial derivatives of to-side power with respect to from-side voltage */
		public JacobianElement getToMutual() {return ACBranchJacobianList.this.getToMutual(_ndx);}
		
	}



	/** get partial derivatives of from-side power with respect to from-side voltage */
	JacobianList _fself;
	/** get partial derivatives of from-side power with respect to to-side voltage */
	JacobianList _fmut;
	/** get partial derivatives of to-side power with respect to to-side voltage */
	JacobianList _tself;
	/** get partial derivatives of to-side power with respect to from-side voltage */
	JacobianList _tmut;
	/** container of all jacobian lists */
	JacobianList[] _jlist;

	int[] _fbus;
	int[] _tbus;
	
	/**
	 * Construct a new list with the specified branches and bus topology
	 * @param branches
	 * @param bri
	 * @throws PAModelException
	 */
	public ACBranchJacobianList(ACBranchListIfc<? extends ACBranch> branches, BusRefIndex bri)
			throws PAModelException
	{
		super(branches, bri);
		prep();
	}
	
	/**
	 * Construct a new list from another extended AC Branch list.
	 * 
	 * The purpose of using this constructor would be to reduce computation of
	 * common elements, such as Y
	 * 
	 * @param copy
	 * @throws PAModelException
	 */
	public ACBranchJacobianList(ACBranchExtList<? extends ACBranchExt> copy)
		throws PAModelException
	{
		super(copy);
		prep();
	}
	
	private void prep() throws PAModelException
	{
		BusRefIndex.TwoTerm t = _bri.get2TBus(_list);
		_fbus = t.getFromBus();
		_tbus = t.getToBus();
		int n = size();
		_fself = new JacobianArrayList(n);
		_tself = new JacobianArrayList(n);
		_fmut = new JacobianArrayList(n);
		_tmut = new JacobianArrayList(n);
		_jlist = new JacobianList[] {_fself, _tself, _fmut, _tmut };
	}
	
	/** get partial derivatives of from-side power with respect to from-side voltage */
	public JacobianElement getFromSelf(int ndx) { return _fself.get(ndx); }
	/** get partial derivatives of to-side power with respect to to-side voltage */
	public JacobianElement getToSelf(int ndx) { return _tself.get(ndx); }
	/** get partial derivatives of from-side power with respect to to-side voltage */
	public JacobianElement getFromMutual(int ndx) { return _fmut.get(ndx); }
	/** get partial derivatives of to-side power with respect to from-side voltage */
	public JacobianElement getToMutual(int ndx) { return _tmut.get(ndx); }

	/**
	 * Calculate jacobian on each branch
	 * @param vm voltage magnitude on each bus
	 * @param va voltage angle on each bus
	 * @return This list
	 * @throws PAModelException
	 */
	public ACBranchJacobianList calc(float[] vm, float[] va) throws PAModelException
	{
		for(JacobianList l : _jlist) l.reset();
		int n = size();
		
		float[] lshift = _list.getShift();
		float[] ftap = _list.getFromTap(), ttap = _list.getToTap();
		float[] bmag = _list.getBmag();
		float[] fbch = _list.getFromBchg(), tbch = _list.getToBchg();
		for(int i=0; i < n; ++i)
		{
			Complex y = _y.get(i);
			float b = y.im(), g = y.re();
			int f = _fbus[i], t = _tbus[i];
			float fvm = vm[f], tvm = vm[t], fva = va[f], tva = va[t];
			float shift = fva - tva - lshift[i];
			float ft = ftap[i], tt = ttap[i], ft2 = ft*ft, tt2 = tt*tt;
			float tprod = 1f/(ft*tt);
			float w = (fvm*tvm)/(ft*tt);
			float dwdvf = tvm * tprod;
			float dwdvt = fvm * tprod;
			float wg = w * g, wb = w * b;
			float cos = (float) Math.cos(shift);
			float sin = (float) Math.sin(shift);
			float gcos = cos * g, gsin = sin * g;
			float bcos = b * cos, bsin = b * sin;
			float bbmag = b + bmag[i];
			float wbcos = wb * cos, wgsin = wg * sin;
			float wgcos = wg * cos, wbsin = wb * sin;
			
			/* From-side active power */
			float tdfpdv = gcos + bsin;
			_fself.decDpdv(i, dwdvf * tdfpdv - 2f * g * fvm / ft2);
			_fmut.decDpdv(i, dwdvt * tdfpdv);
			float dfpdfa = wbcos - wgsin;
			_fself.decDpda(i, dfpdfa);
			_fmut.incDpda(i, dfpdfa); 

			/* from-side reactive power */
			float tdfqv = gsin - bcos;
			_fself.decDqdv(i, dwdvf * tdfqv + 2f * fvm * (bbmag + fbch[i]) / ft2);
			_fmut.decDqdv(i, dwdvt * tdfqv);
			float dfqdfa = wgcos + wbsin;
			_fself.decDqda(i, dfqdfa);
			_fmut.incDqda(i, dfqdfa);
			
			/* to-side active power */
			float tdtpdv = gcos - bsin;
			_tmut.decDpdv(i, dwdvf * tdtpdv);
			_tself.decDpdv(i, dwdvt * tdtpdv - 2f * g * tvm / tt2);
			float dtpdfa = -wgsin - wbcos; 
			_tmut.decDpda(i, dtpdfa);
			_tself.incDpda(i, dtpdfa);
			
			/* to-side reactive power */
			float tdtqdv = gsin + bcos;
			_tmut.decDqdv(i, -dwdvf * tdtqdv);
			_tself.decDqdv(i, -dwdvt * tdtqdv + 2f * tvm * (bbmag + tbch[i]) / tt2);
			float dtqdfa = -wgcos + wbsin; 
			_tmut.decDqda(i, dtqdfa); 
			_tself.incDqda(i, dtqdfa);
			
		}
		return this;
	}

	/**
	 * Apply the results into an nbus x nbus Jacobian Matrix
	 * @param m
	 */
	public void apply(JacobianMatrix m)
	{
		int n = size();
		for(int i=0; i < n; ++i)
		{
			int f = _fbus[i], t = _tbus[i];
			m.addValue(f, f, _fself.get(i));
			m.addValue(t, t, _tself.get(i));
			m.addValue(f, t, _fmut.get(i));
			m.addValue(t, f, _tmut.get(i));
		}
	}

	@Override
	public ACBranchJacobian get(int index)
	{
		return new ACBranchJacobian(index);
	}
	
	/** Test routine */
	public static void main(String...args) throws Exception
	{
		/** The URI is a subsystem-independent way to specify how to access source model data */
		String uri = null;
		/** Output directory for any debug or report files */
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
		if (!outdir.exists()) outdir.mkdirs();
		
		/**A ModelBuilder object is used to build one or more models */
		PflowModelBuilder bldr = PflowModelBuilder.Create(uri);
		/* use the voltages present in case data */
		bldr.enableFlatVoltage(false);
		/* condition X so that it's not too close to 0 */
		bldr.setLeastX(0.0001f);
		
		/** load (build) the model */
		PAModel m = bldr.load();
		
		/**
		 * This tool allows for a uniform interface to access buses related to a
		 * device regardless of topology. We are choosing a single-bus topology in this
		 * case.
		 */
		BusRefIndex bri = BusRefIndex.CreateFromSingleBuses(m);
		/** list of buses for the chosen topology (single bus)*/
		BusList buses = bri.getBuses();
		/** array voltage magnitude on each bus, converted to per-unit on bus base KV */
		float[] vm = PAMath.vmpu(buses);
		/** array of voltage angle on each bus in radians */
		float[] va = PAMath.deg2rad(buses.getVA());
		/** list of all the AC branch lists available in the model (lines, series devices, transformers, etc) */
		List<ACBranchList> branches = m.getACBranches();
		
		/*
		 * The ListDumper utility provides a generic way to turn any list
		 * subclassed from BaseList into a CSV file for debug and review
		 */
		ListDumper ldump = new ListDumper();

		/*
		 * Loop through all the AC branches, calculate the Jacobians and then
		 * dump the results to a CSV file
		 */
		for(ACBranchList brlist : branches)
		{
			ldump.dumpList(new File(outdir, String.format("jacobian-%s.csv", brlist.getListMeta().toString())),
				new ACBranchJacobianList(brlist, bri).calc(vm, va));
		}
	}
}
