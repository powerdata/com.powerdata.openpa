package com.powerdata.openpa;

public class SeriesReacSubList extends SeriesReacList
{
	protected SeriesReacSubList(PALists model, SeriesReacList src, int[] srcndx)
	{
		super(model, srcndx.length, src.getFBusKeys(srcndx),
				src.getTBusKeys(srcndx));
	}
	// TODO: override every method
}
