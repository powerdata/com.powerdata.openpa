package com.powerdata.openpa;

import com.powerdata.openpa.PAModel.ListMetaType;

public class StationListImpl extends GroupListI<Station> implements StationList
{
	static final PAListEnum _PFld = new PAListEnum()
	{
		@Override
		public ColumnMeta id() {return ColumnMeta.StationID;}
		@Override
		public ColumnMeta name() {return ColumnMeta.StationNAME;}
	};

	public StationListImpl() {super();}
	
	public StationListImpl(PAModel model, int[] busref, int nowner)
	{
		super(model, null, _PFld);
		setupMap(busref, nowner);
	}
	
	public StationListImpl(PAModel model, int[] keys, int[] busref)
	{
		super(model, keys, null, _PFld);
		setupMap(busref, keys.length);
	}

	void setupMap(int[] busref, int ngrp)
	{
		_bgmap = new BasicBusGrpMap(getIndexesFromKeys(busref), ngrp);
	}
	
	@Override
	public Station get(int index)
	{
		return new Station(this, index);
	}

	@Override
	protected ListMetaType getMetaType()
	{
		return ListMetaType.Station;
	}
}
