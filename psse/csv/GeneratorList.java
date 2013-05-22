package com.powerdata.openpa.psse.csv;

import java.io.IOException;
import java.util.HashMap;

import com.powerdata.openpa.psse.Bus;
import com.powerdata.openpa.psse.OwnershipList;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.tools.BooleanAttrib;
import com.powerdata.openpa.tools.FloatAttrib;
import com.powerdata.openpa.tools.IntAttrib;
import com.powerdata.openpa.tools.SimpleCSV;
import com.powerdata.openpa.tools.StringAttrib;

public class GeneratorList extends com.powerdata.openpa.psse.GeneratorList<Generator>
{
	PsseModel _eq;
	BusList _buses;
	HashMap<String,Integer> _objIDtoNdx = new HashMap<String,Integer>();
	int _size;
	
	String _i[];
	String _id[];
	float _pg[],_qg[],_qt[],_qb[],_vs[];
	String _ireg[];
	float _mbase[],_zr[],_zx[],_rt[],_xt[],_gtap[];
	int _stat[];
	float _rmpct[],_pt[],_pb[];

	public GeneratorList(PsseModel eq) throws PsseModelException
	{
		super(eq);
		try
		{
			_eq = eq;
			_buses = _eq.getBuses();
			String dbfile = _eq.getDir().getPath()+"/Machines.csv";
			SimpleCSV gens = new SimpleCSV(dbfile);
			_size	= gens.getRowCount();
			_i		= gens.get("I");
			_id		= gens.get("ID");
			_pg		= gens.getFloats("PG");
			_qg		= gens.getFloats("QG");
			_qt		= gens.getFloats("QT");
			_qb		= gens.getFloats("QB");
			_vs		= gens.getFloats("VS");
			_ireg	= gens.get("IREG");
			_mbase	= gens.getFloats("MBASE");
			_zr		= gens.getFloats("ZR");
			_zx		= gens.getFloats("ZX");
			_rt		= gens.getFloats("RT");
			_xt		= gens.getFloats("XT");
			_gtap	= gens.getFloats("GTAP");
			_stat	= gens.getInts("STAT");
			_rmpct	= gens.getFloats("RMPCT");
			_pt		= gens.getFloats("PT");
			_pb		= gens.getFloats("PB");
			if (_i == null)
			{
				throw new PsseModelException(getClass().getName()+" missing I in "+dbfile);
			}
			for(int i=0; i<_size; i++) _objIDtoNdx.put(getObjectID(i),i);
		}
		catch(IOException e)
		{
			throw new PsseModelException(getClass().getName()+": "+e);
		}
	}
	@Override
	public Bus getBus(int ndx) { return _buses.get(_i[ndx]); }
	@Override
	public String getI(int ndx) { return _i[ndx]; }
	@Override
	public String getID(int ndx) { return (_id != null)?_id[ndx]:getDeftID(ndx); }
	@Override
	public float getPG(int ndx) { return (_pg != null)?_pg[ndx]:getDeftPG(ndx); }
	@Override
	public float getQG(int ndx) { return (_qg != null)?_qg[ndx]:getDeftQG(ndx); }
	@Override
	public float getQT(int ndx) { return (_qt != null)?_qt[ndx]:getDeftQT(ndx); }
	@Override
	public float getQB(int ndx) { return (_qb != null)?_qb[ndx]:getDeftQB(ndx); }
	@Override
	public float getVS(int ndx) { return (_vs != null)?_vs[ndx]:getDeftVS(ndx); }
	@Override
	public String getIREG(int ndx) { return (_ireg != null)?_ireg[ndx]:getDeftIREG(ndx);	}
	@Override
	public float getMBASE(int ndx) { return (_mbase != null)?_mbase[ndx]:getDeftMBASE(ndx); }
	@Override
	public float getZR(int ndx) { return (_zr != null)?_zr[ndx]:getDeftZR(ndx); }
	@Override
	public float getZX(int ndx) { return (_zx != null)?_zx[ndx]:getDeftZX(ndx); }
	@Override
	public float getRT(int ndx) { return (_rt != null)?_rt[ndx]:getDeftRT(ndx); }
	@Override
	public float getXT(int ndx) { return (_xt != null)?_xt[ndx]:getDeftXT(ndx); }
	@Override
	public float getGTAP(int ndx) { return (_gtap != null)?_gtap[ndx]:getDeftGTAP(ndx); }
	@Override
	public int getSTAT(int ndx) { return (_stat != null)?_stat[ndx]:getDeftSTAT(ndx); }
	@Override
	public float getRMPCT(int ndx) { return (_rmpct != null)?_rmpct[ndx]:getDeftRMPCT(ndx); }
	@Override
	public float getPT(int ndx) { return (_pt != null)?_pt[ndx]:getDeftPT(ndx); }
	@Override
	public float getPB(int ndx) { return (_pb != null)?_pb[ndx]:getDeftPB(ndx); }
	@Override
	public OwnershipList<?> getOwnership(int ndx) { return null; }
	@Override
	public String getObjectID(int ndx) { return _i[ndx]+":"+_id[ndx]; }
	@Override
	public Generator get(String objectid)
	{
		Integer ndx = _objIDtoNdx.get(objectid);
		return (ndx != null)?new Generator(ndx,this):null;
	}
	@Override
	public StringAttrib<Generator> mapStringAttrib(String attribname) { return null; }
	@Override
	public FloatAttrib<Generator> mapFloatAttrib(String attribname) { return null; }
	@Override
	public IntAttrib<Generator> mapIntAttrib(String attribname) { return null; }
	@Override
	public BooleanAttrib<Generator> mapBooleanAttrib(String attribname) { return null; }
	@Override
	public Generator get(int index) { return new Generator(index,this); }
	@Override
	public int size() { return _size; }
}
