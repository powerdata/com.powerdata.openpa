package com.powerdata.openpa.se;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import com.powerdata.openpa.ACBranchList;
import com.powerdata.openpa.BusList;
import com.powerdata.openpa.BusRefIndex;
import com.powerdata.openpa.PAModel;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.PflowModelBuilder;
import com.powerdata.openpa.SubLists;
import com.powerdata.openpa.pwrflow.ACBranchJacobianList;
import com.powerdata.openpa.pwrflow.ACBranchJacobianList.ACBranchJacobian;
import com.powerdata.openpa.tools.EqType;
import com.powerdata.openpa.tools.PAMath;
import com.powerdata.openpa.tools.matrix.ArrayJacobianMatrix;
import com.powerdata.openpa.tools.matrix.FloatArrayMatrix;
import com.powerdata.openpa.tools.matrix.FloatMatrix;
import com.powerdata.openpa.tools.matrix.JacobianElement;
import com.powerdata.openpa.tools.matrix.JacobianMatrix;
import com.powerdata.openpa.tools.matrix.MatrixDebug;


public class MeasurementMatrix extends FloatArrayMatrix
{
	interface MMDebug extends MatrixDebug<Float>
	{
		public void addRowID(ACBranchJacobian j);

		public void addBusRow(int irow);
		
		void setRows(int nrows);
	}
	
	static class NoDbg implements MMDebug
	{
		@Override
		public void addRowID(ACBranchJacobian j) {}
		@Override
		public void addBusRow(int irow) {}
		@Override
		public String getRowID(int ir) {return "";}
		@Override
		public String getColumnID(int col) {return "";}
		@Override
		public void setRows(int nrows) {}
	}
	
	static class Debug implements MMDebug
	{

		BusList _buses;
		long[] _eqtype;
		int _neq = 0;
		PAModel _m;
		
		public Debug(BusList buses, PAModel m)
		{
			_buses = buses;
			_m = m;
		}
		
		@Override
		public void setRows(int nrows) {_eqtype = new long[nrows];}


		@Override
		public void addRowID(ACBranchJacobian j)
		{
			_eqtype[_neq++] = EqType.GetID(j.getBranch());
		}

		@Override
		public void addBusRow(int row)
		{
			_eqtype[_neq++] = EqType.GetID(_buses.get(row));
		}

		@Override
		public String getRowID(int row)
		{
			try
			{
				return EqType.getObject(_m, _eqtype[row]).getID();
			}
			catch (PAModelException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		public String getColumnID(int col)
		{
			try
			{
			int nc = col - _buses.size();
			if (nc < 0) nc = col;
			return _buses.get(nc).getID();
			}
			catch(PAModelException e)
			{
				e.printStackTrace();
			}
			return null;
		}
		
	}
	
	public MeasurementMatrix(List<ACBranchJacobianList> bj, JacobianMatrix mj)
			throws PAModelException
	{
		construct(bj, mj, new NoDbg());
	}
	
	public MeasurementMatrix(List<ACBranchJacobianList> bj, JacobianMatrix mj, MMDebug dbg)
			throws PAModelException
	{
		construct(bj, mj, dbg);
	}
	
	public MeasurementMatrix(PAModel m, BusRefIndex bri) throws PAModelException
	{
		construct(m, bri, new NoDbg());
	}
	
	void construct(PAModel m, BusRefIndex bri, MMDebug dbg) throws PAModelException
	{
		BusList buses = bri.getBuses();
		int nbus = buses.size();
		JacobianMatrix jm = new ArrayJacobianMatrix(nbus, nbus);
		float[] vm = PAMath.vmpu(buses);
		float[] va = PAMath.deg2rad(buses.getVA());
		List<ACBranchJacobianList> jl = new ArrayList<>();
		for(ACBranchList b : SubLists.getBranchInsvc(m.getACBranches()) )
			jl.add(new ACBranchJacobianList(b, bri).calc(vm, va));
		jl.forEach(i -> i.apply(jm));
		construct(jl, jm, dbg);
		
	}

	public MeasurementMatrix(PAModel m, BusRefIndex bri, MMDebug dbg) throws PAModelException
	{
		construct(m, bri, dbg);
	}
	
	protected void construct(List<ACBranchJacobianList> bj, JacobianMatrix mj,  MMDebug dbg) throws PAModelException
	{
		int nbus = mj.getRowCount();
		int nrows = bj.stream().mapToInt(i->i.size()).sum()*4+nbus*2;
		setSize(nrows,nbus*2);
		dbg.setRows(nrows);
		
		int nrow = 0;
		for (ACBranchJacobianList blist : bj)
		{
			for(ACBranchJacobian j : blist)
			{
				dbg.addRowID(j);
				JacobianElement jacfs = j.getFromSelf();
				JacobianElement jacfm = j.getFromMutual();
				int fbus = j.getFromBus().getIndex();
				int tbus = j.getToBus().getIndex();
				addValue(nrow, fbus, jacfs.getDpda());
				addValue(nrow, tbus, jacfm.getDpda());
				addValue(nrow, fbus+nbus, jacfs.getDpdv());
				addValue(nrow, tbus+nbus, jacfm.getDpdv());
				++nrow;
			}
		}
			
		for (ACBranchJacobianList blist : bj)
		{
			for(ACBranchJacobian j : blist)
			{
				dbg.addRowID(j);
				JacobianElement jacts = j.getToSelf();
				JacobianElement jactm = j.getToMutual();
				int fbus = j.getFromBus().getIndex();
				int tbus = j.getToBus().getIndex();
				addValue(nrow, fbus, jactm.getDpda());
				addValue(nrow, tbus, jacts.getDpda());
				addValue(nrow, fbus+nbus, jactm.getDpdv());
				addValue(nrow, tbus+nbus, jacts.getDpdv());
				++nrow;
			}
		}
			
		for(int ir=0; ir < nbus; ++ir)
		{
			dbg.addBusRow(ir);
			for(int ic=0; ic < nbus; ++ic)
			{
				JacobianElement e = mj.getValue(ir, ic);
				addValue(nrow, ic, e.getDpda());
				addValue(nrow, ic+nbus, e.getDpdv());
			}
			++nrow;
		}
			
		for (ACBranchJacobianList blist : bj)
		{
			for(ACBranchJacobian j : blist)
			{
				dbg.addRowID(j);
				JacobianElement jacfs = j.getFromSelf();
				JacobianElement jacfm = j.getFromMutual();
				int fbus = j.getFromBus().getIndex();
				int tbus = j.getToBus().getIndex();
				addValue(nrow, fbus, jacfs.getDqda());
				addValue(nrow, tbus, jacfm.getDqda());
				addValue(nrow, fbus+nbus, jacfs.getDqdv());
				addValue(nrow, tbus+nbus, jacfm.getDqdv());
				++nrow;
			}
		}
		for (ACBranchJacobianList blist : bj)
		{
			for(ACBranchJacobian j : blist)
			{
				dbg.addRowID(j);
				JacobianElement jacts = j.getToSelf();
				JacobianElement jactm = j.getToMutual();
				int fbus = j.getFromBus().getIndex();
				int tbus = j.getToBus().getIndex();
				addValue(nrow, fbus, jactm.getDqda());
				addValue(nrow, tbus, jacts.getDqda());
				addValue(nrow, fbus+nbus, jactm.getDqdv());
				addValue(nrow, tbus+nbus, jacts.getDqdv());
				++nrow;
			}
		}			
		for (int ir = 0; ir < nbus; ++ir)
		{
			dbg.addBusRow(ir);
			for (int ic = 0; ic < nbus; ++ic)
			{
				JacobianElement e = mj.getValue(ir, ic);
				addValue(nrow, ic, e.getDqda());
				addValue(nrow, ic + nbus, e.getDqdv());
			}
			++nrow;
		}
		/*
			for(ACBranchJacobian j : blist)
			{
				JacobianElement fj = j.getFromSelf(), tj = j.getFromMutual();
				int fb = j.getFromBus().getIndex(), tb = j.getToBus().getIndex();
				int fb2 = fb+nbus, tb2 = tb+nbus;
				addValue(nrow, fb, fj.getDpda());
				addValue(nrow, tb, tj.getDpda());
				addValue(nrow, fb2, fj.getDpdv());
				addValue(nrow, tb2, tj.getDpdv());
				dbg.addRowID(j);
				++nrow;
				
				addValue(nrow, fb, fj.getDqda());
				addValue(nrow, tb, tj.getDqda());
				addValue(nrow, fb2, fj.getDqdv());
				addValue(nrow, tb2, tj.getDqdv());
				dbg.addRowID(j);
				++nrow;
			}
		}
		
		for(int irow=0; irow < nbus; ++irow, nrow+=2)
		{
			int nrp1=nrow+1;
			dbg.addBusRow(irow);
			for(int icol=0; icol < nbus; ++icol)
			{
				int icol2 = icol+nbus;
				JacobianElement e = mj.getValue(irow, icol);
				addValue(nrow, icol, e.getDpda());
				addValue(nrow, icol2, e.getDpdv());

				addValue(nrp1, icol, e.getDqda());
				addValue(nrp1, icol2, e.getDqdv());
			}
		}
		*/
	}
	
	static public void main(String...args) throws Exception
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
		bldr.enableFlatVoltage(false);
		bldr.setLeastX(0.0001f);
		bldr.setUnitRegOverride(false);
		PAModel m = bldr.load();
		BusRefIndex bri = BusRefIndex.CreateFromSingleBuses(m);
//		BusList buses = bri.getBuses();
//		int nbus = buses.size();
		MMDebug dbg = new Debug(bri.getBuses(), m);
		MeasurementMatrix mm = new MeasurementMatrix(m, bri, dbg);
		PrintWriter pw = new PrintWriter(new BufferedWriter(
			new FileWriter(new File(outdir, "measmtrx.csv"))));
		FloatMatrix.wrap(mm).dump(pw, dbg);
		pw.close();
	}

}
