package com.powerdata.openpa;

public class TransformerSubList extends TransformerList
{
	protected TransformerSubList(PALists model, TransformerList src, int[] srcndx)
	{
		super(model, srcndx.length, src.getFBusKeys(srcndx),
				src.getTBusKeys(srcndx));
	}
	// TODO: override every method
}
