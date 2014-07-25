package com.powerdata.openpa.impl;

import com.powerdata.openpa.BaseList;
import com.powerdata.openpa.Bus;
import com.powerdata.openpa.BusList;
import com.powerdata.openpa.ColumnMeta;
import com.powerdata.openpa.OneTermDev;
import com.powerdata.openpa.OneTermDevListIfc;
import com.powerdata.openpa.PAModelException;

public abstract class OneTermDevListI<T extends OneTermDev> extends
		AbstractPAList<T> implements OneTermDevListIfc<T>
{
	IntData _bus;
	FloatData _p, _q;
	BoolData _oos;

	BusList _buses;

	protected OneTermDevListI() {super();}

	protected OneTermDevListI(PAModelI model, int[] keys, OneTermDevEnum le) throws PAModelException
	{
		super(model, keys, le);
		_buses = model.getBuses();
		setFields(le);
	}
	protected OneTermDevListI(PAModelI model, int size, OneTermDevEnum le) throws PAModelException
	{
		super(model, size, le);
		_buses = model.getBuses();
		setFields(le);
	}
	
	private void setFields(OneTermDevEnum le)
	{
		 _bus = new IntData(le.bus());
		 _p = new FloatData(le.p());
		 _q = new FloatData(le.q());
		 _oos = new BoolData(le.insvc());
	}
	
	@Override
	public Bus getBus(int ndx) throws PAModelException
	{
		return _buses.get(_bus.get(ndx));
	}
	
	@Override
	public void setBus(int ndx, Bus b) throws PAModelException
	{
		_bus.set(ndx, b.getIndex());
	}

	@Override
	public Bus[] getBus() throws PAModelException
	{
		return _buses.toArray(_bus.get());
	}

	@Override
	public void setBus(Bus[] b) throws PAModelException
	{
		_bus.set(BaseList.ObjectNdx(b));
	}

	@Override
	public float getP(int ndx) throws PAModelException
	{
		return _p.get(ndx);
	}
	
	@Override
	public float[] getP() throws PAModelException
	{
		return _p.get();
	}

	@Override
	public float getQ(int ndx) throws PAModelException
	{
		return _q.get(ndx);
	}

	/** Get device reactive power injection in MVAr */
	@Override
	public float[] getQ() throws PAModelException
	{
		return _q.get();
	}

	@Override
	public void setP(int ndx, float p) throws PAModelException
	{
		_p.set(ndx, p);
	}

	/** Set device active power injection in MW */
	@Override
	public void setP(float[] p) throws PAModelException
	{
		_p.set(p);
	}

	@Override
	public void setQ(int ndx, float q) throws PAModelException
	{
		_q.set(ndx, q);
	}

	/** Set device reactive power injection in MVAr */
	@Override
	public void setQ(float[] q) throws PAModelException
	{
		_q.set(q);
	}

	@Override
	public boolean isOutOfSvc(int ndx) throws PAModelException
	{
		return _oos.get(ndx);
	}

	/** is device in service */
	@Override
	public boolean[] isOutOfSvc() throws PAModelException
	{
		return _oos.get();
	}

	@Override
	public void setOutOfSvc(int ndx, boolean state) throws PAModelException
	{
		_oos.set(ndx, state);
	}

	/** set device in/out of service */
	@Override
	public void setOutOfSvc(boolean[] state) throws PAModelException
	{
		_oos.set(state);
	}

	interface OneTermDevEnum extends PAListEnum
	{
		ColumnMeta bus();
		ColumnMeta p();
		ColumnMeta q();
		ColumnMeta insvc();
	}
	
}
