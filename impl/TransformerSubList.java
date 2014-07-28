package com.powerdata.openpa.impl;

import com.powerdata.openpa.ListMetaType;
import com.powerdata.openpa.Transformer;
import com.powerdata.openpa.TransformerList;

public class TransformerSubList extends ACBranchSubList<Transformer> implements TransformerList
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

	@Override
	public ListMetaType getListMeta()
	{
		return ListMetaType.Transformer;
	}
	

}
