package com.powerdata.openpa;

public class StationList extends EquipLists<Station>
{
	@Override
	public Station get(int index)
	{
		return new Station(this, index);
	}

	@Override
	protected Station[] newarray(int size)
	{
		// TODO Auto-generated method stub
		return null;
	}
}
