package com.powerdata.openpa.impl;
/*
 * Copyright (c) 2016, PowerData Corporation, Incremental Systems Corporation
 * All rights reserved.
 * Licensed under the BSD-3 Clause License.
 * See full license at https://powerdata.github.io/openpa/LICENSE.md
 */


import com.powerdata.openpa.ColumnMeta;
import com.powerdata.openpa.GroupListI;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.VoltageLevel;
import com.powerdata.openpa.VoltageLevelList;

public class VoltageLevelListI extends GroupListI<VoltageLevel> implements
		VoltageLevelList
{
	static final PAListEnum _PFld = new PAListEnum()
	{
		@Override
		public ColumnMeta id() {return ColumnMeta.VlevID;}
		@Override
		public ColumnMeta name() {return ColumnMeta.VlevNAME;}
	};

	FloatData _bkv = new FloatData(ColumnMeta.VlevBASKV);
	
	public VoltageLevelListI() {super();}
	
	public VoltageLevelListI(PAModelI model, int[] busref, int nvl)
	{
		super(model, nvl, _PFld);
		setupMap(busref, nvl);
	}
	
	public VoltageLevelListI(PAModelI model, int[] keys, int[] busref)
	{
		super(model, keys, null, _PFld);
		setupMap(busref, keys.length);
	}

	void setupMap(int[] busref, int ngrp)
	{
		_bgmap = new BasicGroupIndex(getIndexesFromKeys(busref), ngrp);
	}

	@Override
	public float getBaseKV(int ndx) throws PAModelException
	{
		return _bkv.get(ndx);
	}

	@Override
	public void setBaseKV(int ndx, float k) throws PAModelException
	{
		_bkv.set(ndx, k);
	}

	@Override
	public float[] getBaseKV() throws PAModelException
	{
		return _bkv.get();
	}

	@Override
	public void setBaseKV(float[] kv) throws PAModelException
	{
		_bkv.set(kv);
	}

	@Override
	public VoltageLevel get(int index)
	{
		return new VoltageLevel(this, index);
	}

}
