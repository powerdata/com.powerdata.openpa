package com.powerdata.openpa.impl;

import com.powerdata.openpa.ColumnMeta;
import com.powerdata.openpa.FixedShunt;
import com.powerdata.openpa.FixedShuntListIfc;
import com.powerdata.openpa.PAModelException;

public abstract class FixedShuntListI<T extends FixedShunt> 
	extends OneTermDevListI<T> implements FixedShuntListIfc<T>
{
	interface ShuntEnum extends OneTermDevEnum
	{
		ColumnMeta b();
	}
	
	FloatData _b;
	
	public FixedShuntListI(PAModelI model, int[] keys, ShuntEnum le) throws PAModelException
	{
		super(model, keys, le);
		setFields(le);
	}
	public FixedShuntListI(PAModelI model, int size, ShuntEnum le) throws PAModelException
	{
		super(model, size, le);
		setFields(le);
	}

	public FixedShuntListI() {super();}
	
	private void setFields(ShuntEnum le)
	{
		_b = new FloatData(le.b());
	}

	@Override
	public float getB(int ndx) throws PAModelException
	{
		return _b.get(ndx);
	}
	@Override
	public void setB(int ndx, float b) throws PAModelException
	{
		_b.set(ndx, b);
	}
	@Override
	public float[] getB() throws PAModelException
	{
		return _b.get();
	}
	@Override
	public void setB(float[] b) throws PAModelException
	{
		_b.set(b);
	}
	
}
