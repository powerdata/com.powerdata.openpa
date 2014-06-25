package com.powerdata.openpa;

public class SeriesCapSubList extends SeriesCapList
{
	protected SeriesCapSubList(PALists model, SeriesCapList src, int[] srcndx)
	{
		super(model, srcndx.length, src.getFBusKeys(srcndx),
				src.getTBusKeys(srcndx));
	}
	// TODO: override every method
}
