package com.powerdata.openpa.padbc.incsys;

import com.powerdata.openpa.padbc.Branch;

public class CsvBranch extends Branch
{
	private CsvBranchList _list;
	public CsvBranch(int ndx, CsvBranchList list)
	{
		super(ndx, list);
		_list = list;
	}
	@Override
	public String toString()
	{
		return String.format("[%d] %s %d,%d",_ndx,getID(),getFromNode(),getToNode());
	}
}
