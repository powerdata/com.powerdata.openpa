package com.powerdata.openpa.impl;

import com.powerdata.openpa.ColumnMeta;
import com.powerdata.openpa.OutOfService;
import com.powerdata.openpa.OutOfServiceList;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.impl.TwoTermDevListI.TwoTermDevEnum;

public abstract class OutOfServiceListI<T extends OutOfService> extends AbstractPAList<T> implements
		OutOfServiceList<T>
{
	BoolData _insvc;

	protected OutOfServiceListI(){super();}

	protected OutOfServiceListI(PAModelI model, int[] keys, OutOfServiceEnum le) throws PAModelException
	{
		super(model, keys, le);
		setFields(le);
	}
	
	protected OutOfServiceListI(PAModelI model, int size, OutOfServiceEnum le) throws PAModelException
	{
		super(model, size, le);
		setFields(le);
	}
	
	private void setFields(OutOfServiceEnum le)
	{
		_insvc = new BoolData(le.insvc());
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

	interface OutOfServiceEnum extends PAListEnum
	{
		ColumnMeta insvc();
	}

}
