package com.powerdata.openpa;

public class VoltageLevelList extends GroupList<VoltageLevel>
{
	public static final VoltageLevelList Empty = new VoltageLevelList();

	public VoltageLevelList() {super();}
	
	public VoltageLevelList(PALists model, int[] busref, int nowner)
	{
		super(model, null);
		setupMap(busref, nowner);
	}
	
	public VoltageLevelList(PALists model, int[] keys, int[] busref)
	{
		super(model, keys, null);
		setupMap(busref, keys.length);
	}

	void setupMap(int[] busref, int ngrp)
	{
		_bgmap = new BasicBusGrpMap(getIndexes(busref), ngrp);
	}

	
	@Override
	public VoltageLevel get(int index)
	{
		return new VoltageLevel(this, index);
	}

}
