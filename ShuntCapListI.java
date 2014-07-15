package com.powerdata.openpa;

import com.powerdata.openpa.PAModel.ListMetaType;

public class ShuntCapListI extends ShuntListImpl<ShuntCapacitor> implements ShuntCapList
{

	public ShuntCapListI(PAModel model, int[] keys)
	{
		super(model, keys);
	}
	public ShuntCapListI(PAModel model, int size)
	{
		super(model, size);
	}
	public ShuntCapListI() {super();}

	@Override
	public ShuntCapacitor get(int index)
	{
		return new ShuntCapacitor(this, index);
	}
	@Override
	protected ListMetaType getMetaType()
	{
		return ListMetaType.ShuntCap;
	}

}
