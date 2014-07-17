package com.powerdata.openpa.impl;

import com.powerdata.openpa.BaseList;
import com.powerdata.openpa.Bus;
import com.powerdata.openpa.BusList;
import com.powerdata.openpa.ColumnMeta;
import com.powerdata.openpa.TwoTermDev;
import com.powerdata.openpa.TwoTermDevList;

public abstract class TwoTermDevListI<T extends TwoTermDev> extends AbstractPAList<T> implements TwoTermDevList<T> 
{
	BusList _buses;
	
	IntData _fbus, _tbus;
	BoolData _insvc;
	FloatData _fp, _fq, _tp, _tq;
	
	protected TwoTermDevListI(){super();}

	protected TwoTermDevListI(PAModelI model, int[] keys, TwoTermDevEnum le)
	{
		super(model, keys, le);
		_buses = model.getBuses();
		setFields(le);
	}
	
	protected TwoTermDevListI(PAModelI model, int size, TwoTermDevEnum le)
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
	public Bus getFromBus(int ndx)
	{
		return _buses.get(_fbus.get(ndx));
	}
	
	@Override
	public void setFromBus(int ndx, Bus b)
	{
		_fbus.set(ndx, b.getIndex());
	}

	@Override
	public Bus[] getFromBus()
	{
		return _buses.toArray(_fbus.get());
	}

	@Override
	public void setFromBus(Bus[] b)
	{
		_fbus.set(BaseList.ObjectNdx(b));
	}

	@Override
	public Bus getToBus(int ndx)
	{
		return _buses.get(_tbus.get(ndx));
	}

	@Override
	public void setToBus(int ndx, Bus b)
	{
		_tbus.set(ndx, b.getIndex());
	}

	@Override
	public Bus[] getToBus()
	{
		return _buses.toArray(_tbus.get());
	}

	@Override
	public void setToBus(Bus[] b)
	{
		_tbus.set(BaseList.ObjectNdx(b));
	}

	@Override
	public boolean isInSvc(int ndx)
	{
		return _insvc.get(ndx);
	}

	/** is device in service */
	@Override
	public boolean[] isInSvc()
	{
		return _insvc.get();
	}

	@Override
	public void setInSvc(int ndx, boolean state)
	{
		_insvc.set(ndx, state);
	}

	/** set device in/out of service */
	@Override
	public void setInSvc(boolean[] state)
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
