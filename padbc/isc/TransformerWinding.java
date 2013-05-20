package com.powerdata.openpa.padbc.isc;

public class TransformerWinding extends com.powerdata.openpa.padbc.TransformerWinding 
{
	TransformerWndList _list;
	public TransformerWinding(int ndx, TransformerWndList list)
	{
		super(ndx, list);
		_list = list;
	}
	public void dump() { _list.dumpRow(_ndx); }
}
