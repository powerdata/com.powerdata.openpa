package com.powerdata.openpa.psse.csv;

import com.powerdata.openpa.psse.BusIn;
import com.powerdata.openpa.psse.GenIn;
import com.powerdata.openpa.psse.OwnershipInList;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.tools.BooleanAttrib;
import com.powerdata.openpa.tools.Complex;
import com.powerdata.openpa.tools.FloatAttrib;
import com.powerdata.openpa.tools.IntAttrib;
import com.powerdata.openpa.tools.LoadArray;
import com.powerdata.openpa.tools.SimpleCSV;
import com.powerdata.openpa.tools.StringAttrib;

public class GenInList extends com.powerdata.openpa.psse.GenInList
{
	PsseInputModel _eq;
	BusInList _buses;
	int _size;
	
	String _i[];
	String _id[];
	float _pg[],_qg[],_qt[],_qb[],_vs[];
	String _ireg[];
	float _mbase[],_zr[],_zx[],_rt[],_xt[],_gtap[];
	int _stat[];
	float _rmpct[],_pt[],_pb[];

	public GenInList(PsseInputModel eq) throws PsseModelException
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
			_id		= LoadArray.String(gens,"ID",this,"getDeftID");
			_pg		= LoadArray.Float(gens,"PG",this,"getDeftPG");
			_qg		= LoadArray.Float(gens,"QG",this,"getDeftQG");
			_qt		= LoadArray.Float(gens,"QT",this,"getDeftQT");
			_qb		= LoadArray.Float(gens,"QB",this,"getDeftQB");
			_vs		= LoadArray.Float(gens,"VS",this,"getDeftVS");
			_ireg	= LoadArray.String(gens,"IREG",this,"getDeftIREG");
			_mbase	= LoadArray.Float(gens,"MBASE",this,"getDeftMBASE");
			_zr		= LoadArray.Float(gens,"ZR",this,"getDeftZR");
			_zx		= LoadArray.Float(gens,"ZX",this,"getDeftZX");
			_rt		= LoadArray.Float(gens,"RT",this,"getDeftRT");
			_xt		= LoadArray.Float(gens,"XT",this,"getDeftXT");
			_gtap	= LoadArray.Float(gens,"GTAP",this,"getDeftGTAP");
			_stat	= LoadArray.Int(gens,"STAT",this,"getDeftSTAT");
			_rmpct	= LoadArray.Float(gens,"RMPCT",this,"getDeftRMPCT");
			_pt		= LoadArray.Float(gens,"PT",this,"getDeftPT");
			_pb		= LoadArray.Float(gens,"PB",this,"getDeftPB");
			if (_i == null)
			{
				throw new PsseModelException(getClass().getName()+" missing I in "+dbfile);
			}
			reindex();
		}
		catch(Exception e)
		{
			throw new PsseModelException(getClass().getName()+": "+e);
		}
	}
	@Override
	public BusIn getBus(int ndx) { return _buses.get(_i[ndx]); }
	@Override
	public String getI(int ndx) { return _i[ndx]; }
	@Override
	public String getID(int ndx) { return _id[ndx]; }
	@Override
	public float getPG(int ndx) { return _pg[ndx]; }
	@Override
	public float getQG(int ndx) { return _qg[ndx]; }
	@Override
	public float getQT(int ndx) { return _qt[ndx]; }
	@Override
	public float getQB(int ndx) { return _qb[ndx]; }
	@Override
	public float getVS(int ndx) { return _vs[ndx]; }
	@Override
	public String getIREG(int ndx) { return _ireg[ndx];	}
	@Override
	public float getMBASE(int ndx) { return _mbase[ndx]; }
	@Override
	public float getZR(int ndx) { return _zr[ndx]; }
	@Override
	public float getZX(int ndx) { return _zx[ndx]; }
	@Override
	public float getRT(int ndx) { return _rt[ndx]; }
	@Override
	public float getXT(int ndx) { return _xt[ndx]; }
	@Override
	public float getGTAP(int ndx) { return _gtap[ndx]; }
	@Override
	public int getSTAT(int ndx) { return _stat[ndx]; }
	@Override
	public float getRMPCT(int ndx) { return _rmpct[ndx]; }
	@Override
	public float getPT(int ndx) { return _pt[ndx]; }
	@Override
	public float getPB(int ndx) { return _pb[ndx]; }
	@Override
	public OwnershipInList getOwnership(int ndx) { return null; }
	@Override
	public String getObjectID(int ndx) { return _i[ndx]+":"+_id[ndx]; }
	@Override
	public StringAttrib<GenIn> mapStringAttrib(String attribname) { return null; }
	@Override
	public FloatAttrib<GenIn> mapFloatAttrib(String attribname) { return null; }
	@Override
	public IntAttrib<GenIn> mapIntAttrib(String attribname) { return null; }
	@Override
	public BooleanAttrib<GenIn> mapBooleanAttrib(String attribname) { return null; }
	@Override
	public int size() { return _size; }
	@Override
	public BusIn getRemoteRegBus(int ndx) throws PsseModelException {return getDeftRemoteRegBus(ndx);}
	@Override
	public boolean getInSvc(int ndx) throws PsseModelException { return getDeftInSvc(ndx); }
	@Override
	public float getActvPwr(int ndx) throws PsseModelException { return getDeftActvPwr(ndx); }
	@Override
	public float getReacPwr(int ndx) throws PsseModelException { return getDeftReacPwr(ndx); }
	@Override
	public float getMaxReacPwr(int ndx) throws PsseModelException { return getDeftMaxReacPwr(ndx); }
	@Override
	public float getMinReacPwr(int ndx) throws PsseModelException { return getDeftMinReacPwr(ndx); }
	@Override
	public float getMachR(int ndx) throws PsseModelException { return getDeftMachR(ndx); }
	@Override
	public float getTxfR(int ndx) throws PsseModelException { return getDeftTxfR(ndx); }
	@Override
	public float getMachX(int ndx) throws PsseModelException { return getDeftMachX(ndx); }
	@Override
	public float getTxfX(int ndx) throws PsseModelException { return getDeftTxfX(ndx); }
	@Override
	public float getMaxActvPwr(int ndx) throws PsseModelException { return getDeftMaxActvPwr(ndx); }
	@Override
	public float getMinActvPwr(int ndx) throws PsseModelException { return getDeftMinActvPwr(ndx); }
	@Override
	public Complex getPwr(int ndx) throws PsseModelException { return getDeftPwr(ndx); }
	@Override
	public Complex getMachZ(int ndx) throws PsseModelException { return getDeftMachZ(ndx); }
	@Override
	public Complex getTxZ(int ndx) throws PsseModelException { return getDeftTxZ(ndx); } 
}
