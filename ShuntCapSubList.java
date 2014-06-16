package com.powerdata.openpa;

public class ShuntCapSubList extends ShuntCapList
{
	protected ShuntCapSubList(PALists model, ShuntCapList src, int[] srcndx)
	{
		super(model, src.getKeys(srcndx));
	}
	// TODO: override every method
}
