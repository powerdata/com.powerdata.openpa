package com.powerdata.openpa;

public class SeriesReacListImpl extends ACBranchListImpl<SeriesReac> implements SeriesReacList
{

	public static final SeriesReacList	Empty	= new SeriesReacListImpl();

	protected SeriesReacListImpl() {super();}
	
	public SeriesReacListImpl(PAModel model, int[] keys)
	{
		super(model, keys);
	}
	public SeriesReacListImpl(PAModel model, int size)
	{
		super(model, size);
	}

	@Override
	public SeriesReac get(int index)
	{
		return new SeriesReac(this, index);
	}
}
