package com.powerdata.openpa.psse.csv;

import com.powerdata.openpa.psse.Bus;
import com.powerdata.openpa.psse.BusList;
import com.powerdata.openpa.psse.Limits;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.psse.SVC;
import com.powerdata.openpa.tools.Complex;

public class SvcList extends com.powerdata.openpa.psse.SvcList
{
	SvcRawList _base;
	BusList _buses;
	
	public SvcList(BusList buses, SvcRawList svcs)
	{
		_base = svcs;
		_buses = buses;
	}
	@Override
	public SVC get(String id) { return _base.get(id); }
	@Override
	public Bus getBus(int ndx) throws PsseModelException { return _base.getBus(ndx); }
	@Override
	public Bus getRegBus(int ndx) throws PsseModelException { return _base.getRegBus(ndx); }
	@Override
	public float getVoltageSetpoint(int ndx) throws PsseModelException { return _base.getVoltageSetpoint(ndx); }
	@Override
	public Complex getY(int ndx) throws PsseModelException { return _base.getY(ndx); }
	@Override
	public String getI(int ndx) throws PsseModelException {return _base.getI(ndx);}
	@Override
	public String getSWREM(int ndx) throws PsseModelException { return _base.getSWREM(ndx); }
	@Override
	public float getRMPCT(int ndx) throws PsseModelException { return _base.getRMPCT(ndx); }
	@Override
	public float getBINIT(int ndx) throws PsseModelException { return _base.getBINIT(ndx); }
	@Override
	public Limits getBLimitsPU(int ndx) throws PsseModelException { return _base.getBLimitsPU(ndx); }
	@Override
	public Limits getBLimits(int ndx) throws PsseModelException {return _base.getBLimits(ndx);}
	@Override
	public void setRTS(int ndx, Complex s) throws PsseModelException { _base.setRTS(ndx, s); }
	@Override
	public Complex getRTS(int ndx) throws PsseModelException { return _base.getRTS(ndx); }
	@Override
	public Complex getRTY(int ndx) throws PsseModelException { return _base.getRTY(ndx); }
	@Override
	public void setRTY(int ndx, Complex y) throws PsseModelException { _base.setRTY(ndx, y); }
	@Override
	public boolean isInSvc(int ndx) throws PsseModelException { return _base.isInSvc(ndx); }
	@Override
	public String getObjectID(int ndx) throws PsseModelException {return _base.getObjectID(ndx);}
	@Override
	public String getObjectName(int ndx) throws PsseModelException { return _base.getObjectName(ndx); }
	@Override
	public int size() {return _base.size();}
	
}
