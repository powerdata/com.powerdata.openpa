package com.powerdata.openpa.impl;

import com.powerdata.openpa.Area;
import com.powerdata.openpa.AreaList;
import com.powerdata.openpa.ColumnMeta;
import com.powerdata.openpa.GroupListI;
import com.powerdata.openpa.ListMetaType;

public class AreaListI extends GroupListI<Area> implements AreaList
{
	static final PAListEnum _PFld = new PAListEnum()
	{
		@Override
		public ColumnMeta id()
		{
			return ColumnMeta.AreaID;
		}
		@Override
		public ColumnMeta name()
		{
			return ColumnMeta.AreaNAME;
		}
	};
	public AreaListI()
	{
		super();
	}
	public AreaListI(PAModelI model, int[] busref, int narea)
	{
		super(model, narea, _PFld);
		setupMap(busref, narea);
	}
	public AreaListI(PAModelI model, int[] keys, int[] busref)
	{
		super(model, keys, null, _PFld);
		setupMap(busref, keys.length);
	}
	void setupMap(int[] busref, int ngrp)
	{
		_bgmap = new BasicGroupIndex(getIndexesFromKeys(busref), ngrp);
	}
	@Override
	public Area get(int index)
	{
		return new Area(this, index);
	}
	@Override
	public ListMetaType getListMeta()
	{
		return ListMetaType.Area;
	}
}
