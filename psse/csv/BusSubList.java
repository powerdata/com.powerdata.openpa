package com.powerdata.openpa.psse.csv;

import com.powerdata.openpa.psse.BusList;
import com.powerdata.openpa.psse.BusTypeCode;
import com.powerdata.openpa.psse.PsseModelException;

public class BusSubList extends com.powerdata.openpa.psse.BusSubList
{
	PsseModel _csvmodel;
	
	public BusSubList(PsseModel model, BusList base, int[] ndxs)
	{
		super(base, ndxs);
		_csvmodel = model;
	}

	@Override
	public boolean isEnergized(int ndx) throws PsseModelException
	{
		return _csvmodel.isNodeEnergized(ndx);
	}

	@Override
	public int getIsland(int ndx) throws PsseModelException
	{
		return _csvmodel.getIsland(ndx);
	}

	@Override
	public BusTypeCode getBusType(int ndx) throws PsseModelException
	{
		return _csvmodel.getBusType(ndx);
	}

	
}
