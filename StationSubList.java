package com.powerdata.openpa;

public class StationSubList extends GroupSubList<Station> implements
		StationList
{

	public StationSubList(StationList src, int[] ndx)
	{
		super(src, ndx);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Station get(int index)
	{
		return new Station(this, index);
	}

}
