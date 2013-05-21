package com.powerdata.openpa.psse.csv;

public class NontransformerBranch extends com.powerdata.openpa.psse.NontransformerBranch
{
	private NontransformerBranchList _list;
	public NontransformerBranch(int ndx, NontransformerBranchList list)
	{
		super(ndx, list);
		_list = list;
	}
	/*
	@Override
	public String toString()
	{
		//return String.format("[%d] %s %d,%d",_ndx,getObjectID(),getFromNode(),getToNode());
	}
	*/
}
