package com.powerdata.openpa;

public class StationList extends GroupList<Station>
{
	public static final StationList Empty = new StationList();

	public StationList() {super();}
	
	public StationList(PALists model, int[] busref, int nowner)
	{
		super(model, null);
		setupMap(busref, nowner);
	}
	
	public StationList(PALists model, int[] keys, int[] busref)
	{
		super(model, keys, null);
		setupMap(busref, keys.length);
	}

	void setupMap(int[] busref, int ngrp)
	{
		_bgmap = new BasicBusGrpMap(getIndexes(busref), ngrp);
	}
	
	@Override
	public Station get(int index)
	{
		return new Station(this, index);
	}
}
