package com.powerdata.openpa.psse.csv;

import java.io.File;
import java.io.IOException;

import com.powerdata.openpa.psse.PsseModel;
import com.powerdata.openpa.psse.BusList;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.tools.Complex;
import com.powerdata.openpa.tools.ComplexList;
import com.powerdata.openpa.tools.LoadArray;
import com.powerdata.openpa.tools.PAMath;
import com.powerdata.openpa.tools.SimpleCSV;

public class LoadList extends com.powerdata.openpa.psse.LoadList
{
	PsseModel _eq;
	int _size;
	
	String[] _i, _id;
	
	int[] _status, _area, _zone, _owner;
	float[] _pl, _ql, _ip, _iq, _yp, _yq;
	
	BusList _buses;
	
	ComplexList _rts;
	
	public LoadList() {super();}
	public LoadList(PsseModel eq, File dir) throws PsseModelException
	{
		super(eq);
		_eq = eq;
		_buses = eq.getBuses();
		try
		{
			_eq = eq;
			_buses = _eq.getBuses();
			SimpleCSV loads = new SimpleCSV(new File(dir, "Load.csv"));
			_size = loads.getRowCount();
			_i		= loads.get("I");
			_id		= LoadArray.String(loads,"ID",this,"getID");
			_pl		= LoadArray.Float(loads,"PL",this,"getPL");
			_ql		= LoadArray.Float(loads,"QL",this,"getQL");
			_ip		= LoadArray.Float(loads,"IP",this,"getIP");
			_iq		= LoadArray.Float(loads,"IQ",this,"getIQ");
			_yp		= LoadArray.Float(loads,"YP",this,"getYP");
			_yq		= LoadArray.Float(loads,"YQ",this,"getYQ");
			_status = LoadArray.Int(loads, "STATUS", this, "getSTATUS");
			_area = LoadArray.Int(loads, "AREA", this, "getAREA");
			_zone = LoadArray.Int(loads, "ZONE", this, "getZONE");
			_owner = LoadArray.Int(loads, "OWNER", this, "getOWNER");
			reindex();

		}
		catch(IOException | SecurityException | ReflectiveOperationException e)
		{
			throw new PsseModelException(e);
		}
		
		float[] sre = new float[_size], sim = new float[_size];
		for(int i=0; i < _size; ++i)
		{
			sre[i] = PAMath.mw2pu(_pl[i])*-1f;
			sim[i] = PAMath.mvar2pu(_ql[i])*-1f;
		}
		_rts = new ComplexList(sre, sim);
	}
	
	@Override
	public String getI(int ndx) throws PsseModelException {return _i[ndx];}
	@Override
	public String getObjectID(int ndx) throws PsseModelException {return "LD-"+_i[ndx]+":"+_id[ndx];}
	@Override
	public Complex getRTS(int ndx) throws PsseModelException {return _rts.get(ndx);}
	@Override
	public void setRTS(int ndx, Complex s) throws PsseModelException {_rts.set(ndx, s);}
	@Override
	public String getID(int ndx) throws PsseModelException {return _id[ndx];}
	@Override
	public int getSTATUS(int ndx) throws PsseModelException {return _status[ndx];}
	@Override
	public int getAREA(int ndx) throws PsseModelException {return _area[ndx];}
	@Override
	public int getZONE(int ndx) throws PsseModelException {return _zone[ndx];}
	@Override
	public float getPL(int ndx) throws PsseModelException {return _pl[ndx];}
	@Override
	public float getQL(int ndx) throws PsseModelException {return _ql[ndx];}
	@Override
	public float getIP(int ndx) throws PsseModelException {return _ip[ndx];}
	@Override
	public float getIQ(int ndx) throws PsseModelException {return _iq[ndx];}
	@Override
	public float getYP(int ndx) throws PsseModelException {return _yp[ndx];}
	@Override
	public float getYQ(int ndx) throws PsseModelException {return _yq[ndx];}
	@Override
	public int getOWNER(int ndx) throws PsseModelException {return _owner[ndx];}
	@Override
	public int size() {return _size;}

}
