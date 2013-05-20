package com.powerdata.openpa.padbc.isc;

public class Branch extends com.powerdata.openpa.padbc.Branch
{
	private BranchList _list;
	public Branch(int ndx, BranchList list)
	{
		super(ndx, list);
		_list = list;
	}
	@Override
	public String toString()
	{
		return String.format("[%d] %s %d,%d",_ndx,getObjectID(),getFromNode(),getToNode());
	}
}
