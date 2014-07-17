package com.powerdata.openpa;

import com.powerdata.openpa.PAModel.ListMetaType;

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
	public AreaListI(PAModel model, int[] busref, int narea)
	{
		super(model, null, _PFld);
		setupMap(busref, narea);
	}
	public AreaListI(PAModel model, int[] keys, int[] busref)
	{
		super(model, keys, null, _PFld);
		setupMap(busref, keys.length);
	}
	void setupMap(int[] busref, int ngrp)
	{
		_bgmap = new BasicBusGrpMap(getIndexesFromKeys(busref), ngrp);
	}
	@Override
	public Area get(int index)
	{
		return new Area(this, index);
	}
	@Override
	protected ListMetaType getMetaType()
	{
		return ListMetaType.Area;
	}
}
