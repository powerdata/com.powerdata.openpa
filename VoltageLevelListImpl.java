package com.powerdata.openpa;

import com.powerdata.openpa.PAModel.ListMetaType;

public class VoltageLevelListImpl extends GroupListI<VoltageLevel> implements VoltageLevelList
{
	static final PAListEnum _PFld = new PAListEnum()
	{
		@Override
		public ColumnMeta id() {return ColumnMeta.VlevID;}
		@Override
		public ColumnMeta name() {return ColumnMeta.VlevNAME;}
	};

	FloatData _bkv = new FloatData(ColumnMeta.VlevBASKV);
	
	public VoltageLevelListImpl() {super();}
	
	public VoltageLevelListImpl(PAModel model, int[] busref, int nowner)
	{
		super(model, null, _PFld);
		setupMap(busref, nowner);
	}
	
	public VoltageLevelListImpl(PAModel model, int[] keys, int[] busref)
	{
		super(model, keys, null, _PFld);
		setupMap(busref, keys.length);
	}

	void setupMap(int[] busref, int ngrp)
	{
		_bgmap = new BasicBusGrpMap(getIndexesFromKeys(busref), ngrp);
	}

	@Override
	public float getBaseKV(int ndx)
	{
		return _bkv.get(ndx);
	}

	@Override
	public void setBaseKV(int ndx, float k)
	{
		_bkv.set(ndx, k);
	}

	@Override
	public float[] getBaseKV()
	{
		return _bkv.get();
	}

	@Override
	public void setBaseKV(float[] kv)
	{
		_bkv.set(kv);
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
