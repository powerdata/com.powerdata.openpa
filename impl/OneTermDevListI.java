package com.powerdata.openpa.impl;

import com.powerdata.openpa.Bus;
import com.powerdata.openpa.BusList;
import com.powerdata.openpa.ColumnMeta;
import com.powerdata.openpa.OneTermDev;
import com.powerdata.openpa.OneTermDevListIfc;
import com.powerdata.openpa.PAModelException;

public abstract class OneTermDevListI<T extends OneTermDev> extends
		InServiceListI<T> implements OneTermDevListIfc<T>
{
	IntData _bus;
	FloatData _p, _q;
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
		_bus.set(_buses.getIndexes(b));
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

	interface OneTermDevEnum extends InServiceEnum
	{
		ColumnMeta bus();
		ColumnMeta p();
		ColumnMeta q();
	}
	
	public int[] getBusIndexes() throws PAModelException {return _bus.get();}

}
