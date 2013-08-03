package com.powerdata.openpa.psse.csv;

import com.powerdata.openpa.psse.Bus;
import com.powerdata.openpa.psse.BusList;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.psse.Shunt;
import com.powerdata.openpa.tools.Complex;

public class ShuntList extends com.powerdata.openpa.psse.ShuntList
{
	ShuntRawList _base;
	BusList _buses;
	
	public ShuntList(BusList buses, ShuntRawList shunts)
	{
		_base = shunts;
		_buses = buses;
	}
	@Override
	public Shunt get(String id) { return _base.get(id); }
	@Override
	public Bus getBus(int ndx) throws PsseModelException { return _buses.get(getI(ndx)); }
	@Override
	public Complex getY(int ndx) throws PsseModelException { return _base.getY(ndx); }
	@Override
	public boolean isSwitchedOn(int ndx) throws PsseModelException { return _base.isSwitchedOn(ndx); }
	@Override
	public String getI(int ndx) throws PsseModelException {return _base.getI(ndx);}
	@Override
	public float getB(int ndx) throws PsseModelException { return _base.getB(ndx); }
	@Override
	public float getG(int ndx) throws PsseModelException { return _base.getG(ndx); }
	@Override
	public void setRTS(int ndx, Complex s) { _base.setRTS(ndx, s); }
	@Override
	public Complex getRTS(int ndx) throws PsseModelException { return _base.getRTS(ndx); }
	@Override
	public boolean isInSvc(int ndx) throws PsseModelException { return _base.isInSvc(ndx); }
	@Override
	public String getObjectID(int ndx) throws PsseModelException {return _base.getObjectID(ndx);}
	@Override
	public String getObjectName(int ndx) throws PsseModelException { return _base.getObjectName(ndx); }
	@Override
	public int size() {return _base.size();}

}
