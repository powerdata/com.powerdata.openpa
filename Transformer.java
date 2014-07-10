package com.powerdata.openpa;

public class Transformer extends TransformerBase
{
	TransformerList _list;
	
	public Transformer(TransformerList list, int ndx)
	{
		super(list, ndx);
		_list = list;
	}
	
}