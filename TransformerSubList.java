package com.powerdata.openpa;

public class TransformerSubList extends TransformerBaseSubList<Transformer> implements TransformerList
{
	TransformerList _src;
	
	public TransformerSubList(TransformerList src, int[] ndx)
	{
		super(src, ndx);
		_src = src;
	}

	@Override
	public Transformer get(int index)
	{
		return new Transformer(this, index);
	}

}
