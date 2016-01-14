package com.powerdata.openpa.tools.matrix;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;
import com.powerdata.openpa.ACBranchList;
import com.powerdata.openpa.BusList;
import com.powerdata.openpa.BusRefIndex;
import com.powerdata.openpa.PAModel;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.PflowModelBuilder;
import com.powerdata.openpa.pwrflow.ACBranchJacobianList;
import com.powerdata.openpa.tools.PAMath;
import com.powerdata.openpa.tools.matrix.Matrix;

/**
 * Matrix with JacobianElement values.
 * 
 * @author chris@powerdata.com
 *
 */
public interface JacobianMatrix extends Matrix<JacobianElement>
{
	/**
	 * JacobianElement implementation where elements are backed by an
	 * implementation of JacobianMatrix
	 * 
	 * @author chris@powerdata.com
	 *
	 */
	public static class Element implements JacobianElement
	{
		int _r, _c;
		JacobianMatrix _m;
		protected Element(JacobianMatrix m, int row, int col)
		{
			_m = m;
			_r = row;
			_c = col;
		}
		@Override
		public float getDpda() {return _m.getDpda(_r, _c);}
		@Override
		public float getDpdv() {return _m.getDpdv(_r, _c);}
		@Override
		public float getDqda() {return _m.getDqda(_r, _c);}
		@Override
		public float getDqdv() {return _m.getDqdv(_r, _c);}
		@Override
		public void setDpda(float v) {_m.setDpda(_r, _c, v);}
		@Override
		public void setDpdv(float v) {_m.setDpdv(_r, _c, v);}
		@Override
		public void setDqda(float v) {_m.setDqda(_r, _c, v);}
		@Override
		public void setDqdv(float v) {_m.setDqdv(_r, _c, v);}
		@Override
		public void incDpda(float v) {_m.incDpda(_r, _c, v);}
		@Override
		public void incDpdv(float v) {_m.incDpdv(_r, _c, v);}
		@Override
		public void incDqda(float v) {_m.incDqda(_r, _c, v);}
		@Override
		public void incDqdv(float v) {_m.incDqdv(_r, _c, v);}
		@Override
		public void decDpda(float v) {_m.decDpda(_r, _c, v);}
		@Override
		public void decDpdv(float v) {_m.decDpdv(_r, _c, v);}
		@Override
		public void decDqda(float v) {_m.decDqda(_r, _c, v);}
		@Override
		public void decDqdv(float v) {_m.decDqdv(_r, _c, v);}
		@Override
		public void inc(JacobianElement e) {_m.addValue(_r, _c, e);}
		@Override
		public void dec(JacobianElement e) {_m.subValue(_r, _c, e);}
	}

	/** get the partial derivative of active power with respect to voltage angle */
	default float getDpda(int row, int column) {return getValue(row, column).getDpda();}
	/** get the partial derivative of active power with respect to voltage magnitude */
	default float getDpdv(int row, int column) {return getValue(row, column).getDpdv();}
	/** get the partial derivative of reactive power with respect to voltage angle */
	default float getDqda(int row, int column) {return getValue(row, column).getDqda();}
	/** get the partial derivative of reactive power with respect to voltage magnitude */
	default float getDqdv(int row, int column) {return getValue(row, column).getDqdv();}
	/** set the partial derivative of active power with respect to voltage angle */
	default void setDpda(int row, int column, float v) {getValue(row, column).setDpda(v);} 
	/** set the partial derivative of active power with respect to voltage magnitude */
	default void setDpdv(int row, int column, float v) {getValue(row, column).setDpdv(v);}
	/** set the partial derivative of reactive power with respect to voltage angle */
	default void setDqda(int row, int column, float v) {getValue(row, column).setDqda(v);}
	/** set the partial derivative of reactive power with respect to voltage magnitude */
	default void setDqdv(int row, int column, float v) {getValue(row, column).setDqdv(v);}
	/** increment the partial derivative of active power with respect to voltage angle */
	default void incDpda(int row, int column, float v)
	{
		setDpda(row, column, v+getDpda(row, column));
	}
	/** increment the partial derivative of active power with respect to voltage magnitude */
	default void incDpdv(int row, int column, float v)
	{
		setDpdv(row, column, v+getDpdv(row, column));
	}
	/** increment the partial derivative of reactive power with respect to voltage angle */
	default void incDqda(int row, int column, float v)
	{
		setDqda(row, column, v+getDqda(row, column));
	}
	/** increment the partial derivative of reactive power with respect to voltage magnitude */
	default void incDqdv(int row, int column, float v)
	{
		setDqdv(row, column, v+getDqdv(row, column));
	}
	/** decrement the partial derivative of active power with respect to voltage angle */
	default void decDpda(int row, int column, float v)
	{
		incDpda(row, column, -v);
	}
	/** decrement the partial derivative of active power with respect to voltage magnitude */
	default void decDpdv(int row, int column, float v)
	{
		incDpdv(row, column, -v);
	}
	/** decrement the partial derivative of reactive power with respect to voltage angle */
	default void decDqda(int row, int column, float v)
	{
		incDqda(row, column, -v);
	}
	/** decrement the partial derivative of reactive power with respect to voltage magnitude */
	default void decDqdv(int row, int column, float v)
	{
		incDqdv(row, column, -v);
	}
	@Override
	default void addValue(int row, int column, JacobianElement e)
	{
		incDpda(row, column, e.getDpda());
		incDpdv(row, column, e.getDpdv());
		incDqda(row, column, e.getDqda());
		incDqdv(row, column, e.getDqdv());
	}
	@Override
	default void subValue(int row, int column, JacobianElement e)
	{
		decDpda(row, column, e.getDpda());
		decDpdv(row, column, e.getDpdv());
		decDqda(row, column, e.getDqda());
		decDqdv(row, column, e.getDqdv());
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
		/** number of buses in the chosen topology */
		int nbus = buses.size();
		/** array voltage magnitude on each bus, converted to per-unit on bus base KV */
		float[] vm = PAMath.vmpu(buses);
		/** array of voltage angle on each bus in radians */
		float[] va = PAMath.deg2rad(buses.getVA());
		/**
		 * list of all the AC branch lists available in the model (lines, series
		 * devices, transformers, etc)
		 */
		List<ACBranchList> branches = m.getACBranches();
		
		/** array-based matrix */
		JacobianMatrix jm = new ArrayJacobianMatrix(nbus, nbus);
		
		/*
		 * Loop through all the AC branches, calculate the Jacobians and apply them within the matrix
		 */
		for(ACBranchList brlist : branches)
			new ACBranchJacobianList(brlist, bri).calc(vm, va).apply(jm);
		
		//TODO:  Shunts & SVC's
		
		/* Print out the matrix to a csv file */
		PrintWriter pw = new PrintWriter(new BufferedWriter(
			new FileWriter(new File(outdir, "jacobian-matrix.csv"))));
		jm.dump(pw, new MatrixDebug<JacobianElement>()
		{
			@Override
			public String getRowID(int ir) throws PAModelException
			{
				return buses.get(ir).getID();
			}

			@Override
			public String getColumnID(int col) throws PAModelException
			{
				return buses.get(col).getID();
			}});
		pw.close();
	}
}
