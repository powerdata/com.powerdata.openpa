package com.powerdata.openpa.impl;

import com.powerdata.openpa.ColumnMeta;
import com.powerdata.openpa.InService;
import com.powerdata.openpa.InServiceList;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.impl.TwoTermDevListI.TwoTermDevEnum;

public abstract class InServiceListI<T extends InService> extends AbstractPAList<T> implements
		InServiceList<T>
{
	BoolData _insvc;

	protected InServiceListI(){super();}

	protected InServiceListI(PAModelI model, int[] keys, InServiceEnum le) throws PAModelException
	{
		super(model, keys, le);
		setFields(le);
	}
	
	protected InServiceListI(PAModelI model, int size, InServiceEnum le) throws PAModelException
	{
		super(model, size, le);
		setFields(le);
	}
	
	private void setFields(InServiceEnum le)
	{
		_insvc = new BoolData(le.insvc());
	}
	
	@Override
	public boolean isInService(int ndx) throws PAModelException
	{
		return _insvc.get(ndx);
	}

	/** is device in service */
	@Override
	public boolean[] isInService() throws PAModelException
	{
		return _insvc.get();
	}

	@Override
	public void setInService(int ndx, boolean state) throws PAModelException
	{
		_insvc.set(ndx, state);
	}

	/** set device in/out of service */
	@Override
	public void setInService(boolean[] state) throws PAModelException
	{
		_insvc.set(state);
	}

	interface InServiceEnum extends PAListEnum
	{
		ColumnMeta insvc();
	}

}
