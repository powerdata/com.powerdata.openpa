package com.powerdata.openpa;

import com.powerdata.openpa.PAModel.ListMetaType;

public class LoadListImpl extends OneTermDevListI<Load> implements LoadList 
{
	public LoadListImpl() {super();}
	
	float[][] _pmx=IFlt(), _qmx=IFlt();

	public LoadListImpl(PAModel model, int size)
	{
		super(model, size);
	}

	public LoadListImpl(PAModel model, int[] keys)
	{
		super(model, keys);
	}

	@Override
	public Load get(int index)
	{
		return new Load(this, index);
	}

	@Override
	public float getMaxP(int ndx)
	{
		return getFloat(_pmx, ndx);
	}

	@Override
	public void setMaxP(int ndx, float mw)
	{
		setFloat(_pmx, ndx, mw);
	}

	@Override
	public float[] getMaxP()
	{
		return getFloat(_pmx);
	}

	@Override
	public void setMaxP(float[] mw)
	{
		setFloat(_pmx, mw);
	}

	@Override
	public float getMaxQ(int ndx)
	{
		return getFloat(_qmx, ndx);
	}

	@Override
	public void setMaxQ(int ndx, float mvar)
	{
		setFloat(_qmx, ndx, mvar);
	}

	@Override
	public float[] getMaxQ()
	{
		return getFloat(_qmx);
	}

	@Override
	public void setMaxQ(float[] mvar)
	{
		setFloat(_qmx, mvar);
	}

	@Override
	protected ListMetaType getMetaType()
	{
		return ListMetaType.Load;
	}

}
