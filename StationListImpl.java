package com.powerdata.openpa;

public class StationListImpl extends GroupListI<Station> implements StationList
{
	public static final StationList Empty = new StationListImpl();

	public StationListImpl() {super();}
	
	public StationListImpl(PAModel model, int[] busref, int nowner)
	{
		super(model, null);
		setupMap(busref, nowner);
	}
	
	public StationListImpl(PAModel model, int[] keys, int[] busref)
	{
		super(model, keys, null);
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
}
