package com.powerdata.openpa;

import com.powerdata.openpa.PAModel.ListMetaType;

public class VoltageLevelListImpl extends GroupListI<VoltageLevel> implements VoltageLevelList
{
	public static final VoltageLevelList Empty = new VoltageLevelListImpl();

	float[][] _bkv = IFlt();
	
	public VoltageLevelListImpl() {super();}
	
	public VoltageLevelListImpl(PAModel model, int[] busref, int nowner)
	{
		super(model, null);
		setupMap(busref, nowner);
	}
	
	public VoltageLevelListImpl(PAModel model, int[] keys, int[] busref)
	{
		super(model, keys, null);
		setupMap(busref, keys.length);
	}

	void setupMap(int[] busref, int ngrp)
	{
		_bgmap = new BasicBusGrpMap(getIndexesFromKeys(busref), ngrp);
	}

	@Override
	public float getBaseKV(int ndx)
	{
		return getFloat(_bkv, ndx);
	}

	@Override
	public void setBaseKV(int ndx, float k)
	{
		setFloat(_bkv, ndx, k);
	}

	@Override
	public float[] getBaseKV()
	{
		return getFloat(_bkv);
	}

	@Override
	public void setBaseKV(float[] kv)
	{
		setFloat(_bkv, kv);
	}

	@Override
	public VoltageLevel get(int index)
	{
		return new VoltageLevel(this, index);
	}

	@Override
	protected ListMetaType getMetaType()
	{
		return ListMetaType.VoltageLevel;
	}
	
	
}
