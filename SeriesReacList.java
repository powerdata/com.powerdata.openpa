package com.powerdata.openpa;

public class SeriesReacList extends ACBranchList<SeriesReac>
{

	public static final SeriesReacList	Empty	= new SeriesReacList();

	protected SeriesReacList() {super();}
	
	protected SeriesReacList(PALists model, int[] keys, int[] fbkey, int[] tbkey)
	{
		super(model, keys, fbkey, tbkey);
	}

	@Override
	public SeriesReac get(int index)
	{
		// TODO Auto-generated method stub
		return null;
	}
}
