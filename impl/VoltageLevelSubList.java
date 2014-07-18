package com.powerdata.openpa.impl;

import com.powerdata.openpa.ListMetaType;
import com.powerdata.openpa.VoltageLevel;
import com.powerdata.openpa.VoltageLevelList;

public class VoltageLevelSubList extends GroupSubList<VoltageLevel> implements VoltageLevelList
{

	public VoltageLevelSubList(VoltageLevelList src, int[] ndx)
	{
		super(src, ndx);
		// TODO Auto-generated constructor stub
	}

	@Override
	public float getBaseKV(int ndx)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setBaseKV(int ndx, float k)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public float[] getBaseKV()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setBaseKV(float[] kv)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public VoltageLevel get(int index)
	{
		return new VoltageLevel(this, index);
	}

	@Override
	public ListMetaType getMetaType()
	{
		return ListMetaType.VoltageLevel;
	}

	
}
