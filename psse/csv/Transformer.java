package com.powerdata.openpa.psse.csv;

public class Transformer extends com.powerdata.openpa.psse.Transformer 
{
	TransformerList _list;
	public Transformer(int ndx, TransformerList list)
	{
		super(ndx, list);
		_list = list;
	}
}
