package com.powerdata.openpa;

public class TransformerSubList extends TransformerList
{
	protected TransformerSubList(PALists model, TransformerList src, int[] srcndx)
	{
		super(model, src.getKeys(srcndx), src.getFBusKeys(srcndx),
				src.getTBusKeys(srcndx));
	}
	// TODO: override every method
}
