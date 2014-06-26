package com.powerdata.openpa;

public class StationList extends EquipLists<Station>
{
	@Override
	public Station get(int index)
	{
		return new Station(this, index);
	}
}
