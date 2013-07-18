package com.powerdata.openpa.psse.csv;

import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.tools.LoadArray;
import com.powerdata.openpa.tools.SimpleCSV;

public class LineList extends com.powerdata.openpa.psse.LineList
{
	PsseModel _eq;
	BusList _buses;
	int _size;
	
	String _i[],_j[],_ckt[];
	float _r[],_x[],_b[],_ratea[],_rateb[],_ratec[],_gi[],_bi[],_gj[],_bj[];
	int _st[];
	float _len[];
	
	public LineList(PsseModel eq) throws PsseModelException
	{
		super(eq);
		try
		{
			_eq = eq;
			_buses = _eq.getBuses();
			SimpleCSV branches = new SimpleCSV(_eq.getDir().getPath()+"/NontransformerBranch.csv");
			_size	= branches.getRowCount();
			_i		= branches.get("I");
			_j		= branches.get("J");
			_ckt	= LoadArray.String(branches,"CKT",this,"getCKT");
			_r		= LoadArray.Float(branches,"R",this,"getR");
			_x		= branches.getFloats("X");
			_b		= LoadArray.Float(branches,"B",this,"getB");
			_ratea	= LoadArray.Float(branches,"RATEA",this,"getRATEA");
			_rateb	= LoadArray.Float(branches,"RATEB",this,"getRATEB");
			_ratec	= LoadArray.Float(branches,"RATEC",this,"getRATEC");
			_gi		= LoadArray.Float(branches,"GI",this,"getGI");
			_bi		= LoadArray.Float(branches,"BI",this,"getBI");
			_gj		= LoadArray.Float(branches,"GJ",this,"getGJ");
			_bj		= LoadArray.Float(branches,"BJ",this,"getBJ");
			_st		= LoadArray.Int(branches,"ST",this,"getST");
			_len	= LoadArray.Float(branches,"LEN",this,"getLEN");
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
	public String getObjectID(int ndx) { return _i[ndx]+":"+_j[ndx]+":"+_ckt[ndx]; }
	@Override
	public int size() { return _size; }
	
	public String getDeftCKT(int ndx) throws PsseModelException {return super.getCKT(ndx);}
	public float getDeftR(int ndx) throws PsseModelException {return super.getR(ndx);}
	public float getDeftB(int ndx) throws PsseModelException {return super.getB(ndx);}
	public float getDeftRATEA(int ndx) throws PsseModelException {return super.getRATEA(ndx);}
	public float getDeftRATEB(int ndx) throws PsseModelException {return super.getRATEB(ndx);}
	public float getDeftRATEC(int ndx) throws PsseModelException {return super.getRATEC(ndx);}
	public float getDeftGI(int ndx) throws PsseModelException {return super.getGI(ndx);}
	public float getDeftBI(int ndx) throws PsseModelException {return super.getBI(ndx);}
	public float getDeftGJ(int ndx) throws PsseModelException {return super.getGJ(ndx);}
	public float getDeftBJ(int ndx) throws PsseModelException {return super.getBJ(ndx);}
	public int getDeftST(int ndx) throws PsseModelException {return super.getST(ndx);}
	public float getDeftLEN(int ndx) throws PsseModelException {return super.getLEN(ndx);}

}
