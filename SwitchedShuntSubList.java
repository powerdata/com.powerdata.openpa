package com.powerdata.openpa;

public class SwitchedShuntSubList extends SwitchedShuntList
{
	protected SwitchedShuntSubList(PALists model, SwitchedShuntList src,
			int[] srcndx)
	{
		super(model, srcndx.length, src.getBusKeys(srcndx));
	}
	// TODO: override every method

}
