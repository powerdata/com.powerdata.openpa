package com.powerdata.openpa;

public class ShuntReacSubList extends ShuntReacList
{
	protected ShuntReacSubList(PALists model, ShuntReacList src, int[] srcndx)
	{
		super(model, srcndx.length, src.getBusKeys(srcndx));
		// TODO Auto-generated constructor stub
	}
	// TODO: override every method

}
