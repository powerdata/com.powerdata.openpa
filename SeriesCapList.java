package com.powerdata.openpa;

public class SeriesCapList extends ACBranchList<SeriesCap>
{

	public static final SeriesCapList	Empty	= new SeriesCapList();

	protected SeriesCapList(PALists model, int[] keys, int[] fbkey, int[] tbkey)
	{
		super(model, keys, fbkey, tbkey);
	}

	protected SeriesCapList() {super();}

	@Override
	public SeriesCap get(int index)
	{
		// TODO Auto-generated method stub
		return null;
	}
}
