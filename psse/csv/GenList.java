package com.powerdata.openpa.psse.csv;

import com.powerdata.openpa.psse.Bus;
import com.powerdata.openpa.psse.OwnershipList;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.tools.LoadArray;
import com.powerdata.openpa.tools.SimpleCSV;

public class GenList extends com.powerdata.openpa.psse.GenList
{
	PsseModel _eq;
	BusList _buses;
	int _size;
	
	String _i[];
	String _id[];
	float _pg[],_qg[],_qt[],_qb[],_vs[];
	String _ireg[];
	float _mbase[],_zr[],_zx[],_rt[],_xt[],_gtap[];
	int _stat[];
	float _rmpct[],_pt[],_pb[];

	public GenList(PsseModel eq) throws PsseModelException
	{
		super(eq);
		try
		{
			_eq = eq;
			_buses = _eq.getBuses();
			String dbfile = _eq.getDir().getPath()+"/Generator.csv";
			SimpleCSV gens = new SimpleCSV(dbfile);
			_size	= gens.getRowCount();
			_i		= gens.get("I");
			_id		= LoadArray.String(gens,"ID",this,"getID");
			_pg		= LoadArray.Float(gens,"PG",this,"getPG");
			_qg		= LoadArray.Float(gens,"QG",this,"getQG");
			_qt		= LoadArray.Float(gens,"QT",this,"getQT");
			_qb		= LoadArray.Float(gens,"QB",this,"getQB");
			_vs		= LoadArray.Float(gens,"VS",this,"getVS");
			_ireg	= LoadArray.String(gens,"IREG",this,"getIREG");
			_mbase	= LoadArray.Float(gens,"MBASE",this,"getMBASE");
			_zr		= LoadArray.Float(gens,"ZR",this,"getZR");
			_zx		= LoadArray.Float(gens,"ZX",this,"getZX");
			_rt		= LoadArray.Float(gens,"RT",this,"getRT");
			_xt		= LoadArray.Float(gens,"XT",this,"getXT");
			_gtap	= LoadArray.Float(gens,"GTAP",this,"getGTAP");
			_stat	= LoadArray.Int(gens,"STAT",this,"getSTAT");
			_rmpct	= LoadArray.Float(gens,"RMPCT",this,"getRMPCT");
			_pt		= LoadArray.Float(gens,"PT",this,"getPT");
			_pb		= LoadArray.Float(gens,"PB",this,"getPB");
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
	public Bus getBus(int ndx) { return _buses.get(_i[ndx]); }
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
	public OwnershipList getOwnership(int ndx) { return null; }
	@Override
	public String getObjectID(int ndx) { return _i[ndx]+":"+_id[ndx]; }
	@Override
	public int size() { return _size; }
}
