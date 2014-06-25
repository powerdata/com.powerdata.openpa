package com.powerdata.openpa;

public class TwoTermDCLineSubList extends TwoTermDCLineList
{
	protected TwoTermDCLineSubList(PALists model, TwoTermDCLineList src, int[] srcndx)
	{
		super(model, srcndx.length, src.getFBusKeys(srcndx),
				src.getTBusKeys(srcndx));
	}
	// TODO: override every method

}
