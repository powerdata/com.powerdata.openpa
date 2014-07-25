package com.powerdata.openpa.impl;

import com.powerdata.openpa.BaseList;
import com.powerdata.openpa.Bus;
import com.powerdata.openpa.BusList;
import com.powerdata.openpa.ColumnMeta;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.TwoTermDev;
import com.powerdata.openpa.TwoTermDevListIfc;

public abstract class TwoTermDevListI<T extends TwoTermDev> extends AbstractPAList<T> implements TwoTermDevListIfc<T> 
{
	BusList _buses;
	
	IntData _fbus, _tbus;
	BoolData _insvc;
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
		_insvc = new BoolData(le.insvc());
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
		_fbus.set(BaseList.ObjectNdx(b));
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
		_tbus.set(BaseList.ObjectNdx(b));
	}

	@Override
	public boolean isOutOfSvc(int ndx) throws PAModelException
	{
		return _insvc.get(ndx);
	}

	/** is device in service */
	@Override
	public boolean[] isOutOfSvc() throws PAModelException
	{
		return _insvc.get();
	}

	@Override
	public void setOutOfSvc(int ndx, boolean state) throws PAModelException
	{
		_insvc.set(ndx, state);
	}

	/** set device in/out of service */
	@Override
	public void setOutOfSvc(boolean[] state) throws PAModelException
	{
		_insvc.set(state);
	}

	interface TwoTermDevEnum extends PAListEnum
	{
		ColumnMeta fbus();
		ColumnMeta tbus();
		ColumnMeta insvc();
		ColumnMeta fp(); 
		ColumnMeta fq(); 
		ColumnMeta tp(); 
		ColumnMeta tq(); 
	}
}
