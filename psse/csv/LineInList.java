package com.powerdata.openpa.psse.csv;

import com.powerdata.openpa.psse.BusIn;
import com.powerdata.openpa.psse.LineIn;
import com.powerdata.openpa.psse.OwnershipInList;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.tools.Complex;
import com.powerdata.openpa.tools.LoadArray;
import com.powerdata.openpa.tools.SimpleCSV;

public class LineInList extends com.powerdata.openpa.psse.LineInList
{
	PsseInputModel _eq;
	BusInList _buses;
	int _size;
	
	String _i[],_j[],_ckt[];
	float _r[],_x[],_b[],_ratea[],_rateb[],_ratec[],_gi[],_bi[],_gj[],_bj[];
	int _st[];
	float _len[];
	
	public LineInList(PsseInputModel eq) throws PsseModelException
	{
		super(eq);
		try
		{
			_eq = eq;
			_buses = _eq.getBuses();
			SimpleCSV branches = new SimpleCSV(_eq.getDir().getPath()+"/Branches.csv");
			_size	= branches.getRowCount();
			_i		= branches.get("I");
			_j		= branches.get("J");
			_ckt	= LoadArray.String(branches,"CKT",this,"getDeftCKT");
			_r		= branches.getFloats("R");
			_x		= branches.getFloats("X");
			_b		= LoadArray.Float(branches,"B",this,"getDeftB");
			_ratea	= LoadArray.Float(branches,"RATEA",this,"getDeftRATEA");
			_rateb	= LoadArray.Float(branches,"RATEB",this,"getDeftRATEB");
			_ratec	= LoadArray.Float(branches,"RATEC",this,"getDeftRATEC");
			_gi		= LoadArray.Float(branches,"GI",this,"getDeftGI");
			_bi		= LoadArray.Float(branches,"BI",this,"getDeftBI");
			_gj		= LoadArray.Float(branches,"GJ",this,"getDeftGJ");
			_bj		= LoadArray.Float(branches,"BJ",this,"getDeftBJ");
			_st		= LoadArray.Int(branches,"ST",this,"getDeftST");
			_len	= LoadArray.Float(branches,"LEN",this,"getDeftLEN");
			reindex();
		}
		catch(Exception e)
		{
			throw new PsseModelException(getClass().getName()+": "+e);
		}
	}

	@Override
	public String getI(int ndx) { return _i[ndx]; }
	@Override
	public String getJ(int ndx) { return _j[ndx]; }
	@Override
	public String getCKT(int ndx) { return _ckt[ndx]; }
	@Override
	public float getR(int ndx) { return _r[ndx]; }
	@Override
	public float getX(int ndx) { return _x[ndx]; }
	@Override
	public float getB(int ndx) { return _b[ndx]; }
	@Override
	public float getRATEA(int ndx) { return _ratea[ndx]; }
	@Override
	public float getRATEB(int ndx) { return _rateb[ndx]; }
	@Override
	public float getRATEC(int ndx) { return _ratec[ndx]; }
	@Override
	public float getGI(int ndx) { return _gi[ndx]; }
	@Override
	public float getBI(int ndx) { return _bi[ndx]; }
	@Override
	public float getGJ(int ndx) { return _gj[ndx]; }
	@Override
	public float getBJ(int ndx) { return _bj[ndx]; }
	@Override
	public int getST(int ndx) { return _st[ndx]; }
	@Override
	public float getLEN(int ndx) { return _len[ndx]; }
	@Override
	public OwnershipInList getOwnership(int ndx) { return null; }
	@Override
	public String getObjectID(int ndx) { return _i[ndx]+":"+_j[ndx]+":"+_ckt[ndx]; }
	@Override
	public int size() { return _size; }

	@Override
	public BusIn getFromBus(int ndx) throws PsseModelException {return getDeftFromBus(ndx);}
	@Override
	public BusIn getToBus(int ndx) throws PsseModelException {return getDeftToBus(ndx);}
	@Override
	public com.powerdata.openpa.psse.LineMeterEnd getMeteredEnd(
			int ndx) throws PsseModelException
	{
		return getDeftMeteredEnd(ndx);
	}
	@Override
	public boolean getInSvc(int ndx) throws PsseModelException {return getDeftInSvc(ndx);}
	@Override
	public float getR100(int ndx) throws PsseModelException {return getDeftR100(ndx);}
	@Override
	public float getX100(int ndx) throws PsseModelException {return getDeftX100(ndx);}
	@Override
	public Complex getZ100(int ndx) throws PsseModelException {return getDeftZ100(ndx);}
	@Override
	public float getFromShuntG(int ndx) throws PsseModelException {return getDeftFromShuntG(ndx);}
	@Override
	public float getFromShuntB(int ndx) throws PsseModelException {return getDeftFromShuntB(ndx);}
	@Override
	public Complex getFromShuntY(int ndx) throws PsseModelException {return getDeftFromShuntY(ndx);}
	@Override
	public float getToShuntG(int ndx) throws PsseModelException {return getDeftToShuntG(ndx);}
	@Override
	public float getToShuntB(int ndx) throws PsseModelException {return getDeftToShuntB(ndx);}
	@Override
	public Complex getToShuntY(int ndx) throws PsseModelException {return getDeftToShuntY(ndx);}

	@Override
	public float getFromBch(int ndx) throws PsseModelException {return getDeftFromBch(ndx);}
	@Override
	public float getToBch(int ndx) throws PsseModelException {return getDeftToBch(ndx);}
}
