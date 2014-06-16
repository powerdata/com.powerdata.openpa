package com.powerdata.openpa;

public class TransformerSubList extends TransformerList
{
	protected TransformerSubList(PALists model, TransformerList src, int[] srcndx)
	{
		super(model, cvtSublistKeys(src, srcndx));
		// TODO Auto-generated constructor stub
	}
	// TODO: override every method
}
