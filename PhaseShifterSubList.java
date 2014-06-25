package com.powerdata.openpa;

public class PhaseShifterSubList extends PhaseShifterList
{
	protected PhaseShifterSubList(PALists model, PhaseShifterList src, int[] srcndx)
	{
		super(model, srcndx.length, src.getFBusKeys(srcndx),
				src.getTBusKeys(srcndx));
	}
	// TODO: override every method

}
