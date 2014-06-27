package com.powerdata.openpa;

public class AreaList extends EquipLists<Area>
{
	public static final AreaList Empty = new AreaList();

	public AreaList() {super();}

	public AreaList(PALists model, int[] busref)
	{
		super(model, null);
		setupMap(busref);
	}

	public AreaList(PALists model, int[] keys, int[] busref)
	{
		super(model, keys, null);
		setupMap(busref);
	}

	void setupMap(int[] busref)
	{
		int[] map = getOffsets(busref);
//		_bgmap = new
	}

	@Override
	public Area get(int index)
	{
		return new Area(this, index);
	}

}
