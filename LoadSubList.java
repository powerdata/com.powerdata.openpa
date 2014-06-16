package com.powerdata.openpa;

public class LoadSubList extends LoadList
{
	protected LoadSubList(PALists model, LoadList src, int[] srcndx)
	{
		super(model, cvtSublistKeys(src, srcndx));
		// TODO Auto-generated constructor stub
	}
	// TODO: override every method
}
