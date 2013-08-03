package com.powerdata.openpa.psse.csv;

import java.io.File;

import com.powerdata.openpa.psse.Bus;
import com.powerdata.openpa.psse.BusList;
import com.powerdata.openpa.psse.LineList;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.tools.Complex;
import com.powerdata.openpa.tools.ComplexList;
import com.powerdata.openpa.tools.LoadArray;
import com.powerdata.openpa.tools.SimpleCSV;

public class LineListRaw extends LineList
{
	PsseRawModel _eq;
	BusList _buses;
	protected String _i[],_j[],_ckt[];
	protected float _r[],_x[],_b[],_ratea[],_rateb[],_ratec[],_gi[],_bi[],_gj[],_bj[];
	protected int _st[];
	protected float _len[];
	protected ComplexList _fs, _ts;
	protected int _size;
	
	public LineListRaw(PsseRawModel model) throws PsseModelException
	{
		super(model);
		try
		{
			File dbfile = new File(model.getDir(), "NontransformerBranch.csv");
			_buses = model.getBuses();
			SimpleCSV branches = new SimpleCSV(dbfile);
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
			
			_fs = new ComplexList(_size, true);
			_ts = new ComplexList(_size, true);
		}
		catch(Exception e)
		{
			throw new PsseModelException(getClass().getName()+": "+e);
		}
	}
	
	@Override
	public int size() { return _size; }

	
	@Override
	public Bus getFromBus(int ndx) throws PsseModelException
	{
		return _buses.get(getI(ndx));
	}

	@Override
	public Bus getToBus(int ndx) throws PsseModelException
	{
		String j = getJ(ndx);
		if (j.charAt(0)=='-') j = j.substring(1);
		return _buses.get(j);
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
	public String getObjectID(int ndx) { return "LN-"+_i[ndx]+":"+_j[ndx]+":"+_ckt[ndx]; }

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

	/* Realtime fields */

	@Override
	public void setRTFromS(int ndx, Complex s) throws PsseModelException {_fs.set(ndx, s);}
	@Override
	public void setRTToS(int ndx, Complex s) throws PsseModelException {_ts.set(ndx, s);}
	@Override
	public Complex getRTFromS(int ndx) throws PsseModelException {return _fs.get(ndx);}
	@Override
	public Complex getRTToS(int ndx) throws PsseModelException {return _ts.get(ndx);}


}
