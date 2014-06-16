package com.powerdata.openpa;

public class SeriesCapSubList extends SeriesCapList
{
	protected SeriesCapSubList(PALists model, SeriesCapList src, int[] srcndx)
	{
		super(model, src.getKeys(srcndx), src.getFBusKeys(srcndx),
				src.getTBusKeys(srcndx));
	}
	// TODO: override every method
}
