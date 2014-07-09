package com.powerdata.openpa;

public class SeriesCapListImpl extends ACBranchListImpl<SeriesCap> implements SeriesCapList
{

	public SeriesCapListImpl(PAModel model, int[] keys)
	{
		super(model, keys);
	}

	public SeriesCapListImpl(PAModel model, int size)
	{
		super(model, size);
	}

	public SeriesCapListImpl() {super();}

	@Override
	public SeriesCap get(int index)
	{
		return new SeriesCap(this, index);
	}
}
