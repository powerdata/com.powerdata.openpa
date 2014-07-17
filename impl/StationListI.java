package com.powerdata.openpa.impl;

import com.powerdata.openpa.ColumnMeta;
import com.powerdata.openpa.ListMetaType;
import com.powerdata.openpa.Station;
import com.powerdata.openpa.StationList;

public class StationListI extends GroupListI<Station> implements StationList
{
	static final PAListEnum _PFld = new PAListEnum()
	{
		@Override
		public ColumnMeta id() {return ColumnMeta.StationID;}
		@Override
		public ColumnMeta name() {return ColumnMeta.StationNAME;}
	};

	public StationListI() {super();}
	
	public StationListI(PAModelI model, int[] busref, int nowner)
	{
		super(model, null, _PFld);
		setupMap(busref, nowner);
	}
	
	public StationListI(PAModelI model, int[] keys, int[] busref)
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
