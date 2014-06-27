package com.powerdata.openpa;

public class AreaList extends EquipLists<Area>
{
	public static final AreaList Empty = new AreaList();

	public AreaList() {super();}

	public AreaList(PALists model, int[] busref, int narea)
	{
		super(model, null);
		setupMap(busref, narea);
	}

	public AreaList(PALists model, int[] keys, int[] busref)
	{
		super(model, keys, null);
		setupMap(busref, keys.length);
	}

	void setupMap(int[] busref, int ngrp)
	{
		_bgmap = new BasicBusGrpMap(getIndexes(busref), ngrp);
	}

	@Override
	public Area get(int index)
	{
		return new Area(this, index);
	}

	/** return an array of Areas for the given offset */
	public Area[] toArray(int[] indexes)
	{
		Area[] us = toArray(new Area[_size]);
		int n = indexes.length;
		Area[] rv = new Area[n];
		for(int i=0; i < n; ++i)
			rv[i] = us[indexes[i]];
		return rv;
	}
}
