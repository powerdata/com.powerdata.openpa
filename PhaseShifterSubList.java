package com.powerdata.openpa;

public class PhaseShifterSubList extends PhaseShifterList
{
	protected PhaseShifterSubList(PALists model, PhaseShifterList src, int[] srcndx)
	{
		super(model, src.getKeys(srcndx), src.getFBusKeys(srcndx),
				src.getTBusKeys(srcndx));
	}
	// TODO: override every method

}
