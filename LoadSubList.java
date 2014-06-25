package com.powerdata.openpa;

public class LoadSubList extends LoadList
{
	protected LoadSubList(PALists model, LoadList src, int[] srcndx)
	{
		super(model, srcndx.length, src.getBusKeys(srcndx));
		// TODO Auto-generated constructor stub
	}
	// TODO: override every method
}
