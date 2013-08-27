package com.powerdata.openpa.psse.csv;

import com.powerdata.openpa.psse.BusList;
import com.powerdata.openpa.psse.PsseModelException;

public class BusSubList extends com.powerdata.openpa.psse.BusSubList
{
	TP _tp;
	
	public BusSubList(BusList base, int[] ndxs, TP tp)
	{
		super(base, ndxs);
		_tp = tp;
	}

	@Override
	public boolean isEnergized(int ndx) throws PsseModelException
	{
		return _tp.isIslandEnergized(_tp.getIsland(ndx));
	}

	@Override
	public int getIsland(int ndx) throws PsseModelException
	{
		return _tp.getIsland(ndx);
	}

	
	
}
