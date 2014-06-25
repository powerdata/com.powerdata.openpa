package com.powerdata.openpa;

public class GenSubList extends GenList
{
	protected GenSubList(PALists model, GenList src, int[] srcndx)
	{
		super(model, srcndx.length, src.getBusKeys(srcndx));
		// TODO Auto-generated constructor stub
	}
	// TODO: override every method

}
