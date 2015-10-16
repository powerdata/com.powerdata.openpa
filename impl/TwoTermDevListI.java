package com.powerdata.openpa.impl;

import com.powerdata.openpa.Bus;
import com.powerdata.openpa.BusList;
import com.powerdata.openpa.ColumnMeta;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.TwoTermDev;
import com.powerdata.openpa.TwoTermDevListIfc;

public abstract class TwoTermDevListI<T extends TwoTermDev> extends
		InServiceListI<T> implements TwoTermDevListIfc<T>
{
	BusList _buses;
	
	IntData _fbus, _tbus;
	FloatData _fp, _fq, _tp, _tq;
	
	protected TwoTermDevListI(){super();}

	protected TwoTermDevListI(PAModelI model, int[] keys, TwoTermDevEnum le) throws PAModelException
	{
		super(model, keys, le);
		_buses = model.getBuses();
		setFields(le);
	}
	
	protected TwoTermDevListI(PAModelI model, int size, TwoTermDevEnum le) throws PAModelException
	{
		super(model, size, le);
		_buses = model.getBuses();
		setFields(le);
	}

	private void setFields(TwoTermDevEnum le)
	{
		_fbus = new IntData(le.fbus());
		_tbus = new IntData(le.tbus());
		_fp = new FloatData(le.fp());
		_fq = new FloatData(le.fq());
		_tp = new FloatData(le.tp());
		_tq = new FloatData(le.tq());
		
	}

	@Override
	public Bus getFromBus(int ndx) throws PAModelException
	{
		return _buses.get(_fbus.get(ndx));
	}
	
	@Override
	public void setFromBus(int ndx, Bus b) throws PAModelException
	{
		_fbus.set(ndx, b.getIndex());
	}

	@Override
	public Bus[] getFromBus() throws PAModelException
	{
		return _buses.toArray(_fbus.get());
	}

	@Override
	public void setFromBus(Bus[] b) throws PAModelException
	{
		_fbus.set(_buses.getIndexes(b));
	}

	@Override
	public Bus getToBus(int ndx) throws PAModelException
	{
		return _buses.get(_tbus.get(ndx));
	}

	@Override
	public void setToBus(int ndx, Bus b) throws PAModelException
	{
		_tbus.set(ndx, b.getIndex());
	}

	@Override
	public Bus[] getToBus() throws PAModelException
	{
		return _buses.toArray(_tbus.get());
	}

	@Override
	public void setToBus(Bus[] b) throws PAModelException
	{
		_tbus.set(_buses.getIndexes(b));
	}

	@Override
	public float getFromP(int ndx) throws PAModelException
	{
		return _fp.get(ndx);
	}
	@Override
	public void setFromP(int ndx, float mw) throws PAModelException
	{
		_fp.set(ndx, mw);
	}
	@Override
	public float[] getFromP() throws PAModelException
	{
		return _fp.get();
	}
	@Override
	public void setFromP(float[] mw) throws PAModelException
	{
		_fp.set(mw);
	}
	@Override
	public float getFromQ(int ndx) throws PAModelException
	{
		return _fq.get(ndx);
	}
	@Override
	public void setFromQ(int ndx, float mvar) throws PAModelException
	{
		_fq.set(ndx, mvar);
	}
	@Override
	public float[] getFromQ() throws PAModelException
	{
		return _fq.get();
	}
	@Override
	public void setFromQ(float[] mvar) throws PAModelException
	{
		_fq.set(mvar);
	}
	@Override
	public float getToP(int ndx) throws PAModelException
	{
		return _tp.get(ndx);
	}
	@Override
	public void setToP(int ndx, float mw) throws PAModelException
	{
		_tp.set(ndx, mw);
	}
	@Override
	public float[] getToP() throws PAModelException
	{
		return _tp.get();
	}
	@Override
	public void setToP(float[] mw) throws PAModelException
	{
		_tp.set(mw);
	}
	@Override
	public float getToQ(int ndx) throws PAModelException
	{
		return _tq.get(ndx);
	}
	@Override
	public void setToQ(int ndx, float mvar) throws PAModelException
	{
		_tq.set(ndx, mvar);
	}
	@Override
	public float[] getToQ() throws PAModelException
	{
		return _tq.get();
	}
	@Override
	public void setToQ(float[] mvar) throws PAModelException
	{
		_tq.set(mvar);
	}
	interface TwoTermDevEnum extends InServiceEnum
	{
		ColumnMeta fbus();
		ColumnMeta tbus();
		ColumnMeta fp(); 
		ColumnMeta fq(); 
		ColumnMeta tp(); 
		ColumnMeta tq(); 
	}
	
	public int[] getFromBusIndexes() throws PAModelException {return _fbus.get();}
	public int[] getToBusIndexes() throws PAModelException {return _tbus.get();}
}
