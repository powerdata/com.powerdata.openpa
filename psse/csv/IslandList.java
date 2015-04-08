package com.powerdata.openpa.psse.csv;

import com.powerdata.openpa.psse.BusList;
import com.powerdata.openpa.psse.BusTypeCode;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.psse.util.BusSubList;
import com.powerdata.openpa.psse.util.TP;
import com.powerdata.openpa.psse.PsseModel;

public class IslandList extends com.powerdata.openpa.psse.IslandList
{
	PsseRawModel _model;
	public IslandList(PsseRawModel eq) throws PsseModelException
	{
		super(eq);
		_model = eq;
	}
	
	@Override
	public String getObjectID(int ndx) throws PsseModelException
	{
		return String.valueOf(ndx);
	}

	@Override
	public int size()
	{
		int rv = 0;
		try
		{
			rv = _model.tp().getIslandCount();
		}
		catch(PsseModelException ex)
		{
			System.err.println(ex);
		}
		return rv;
	}

	@Override
	public BusList getBusesForType(int ndx, BusTypeCode bustype) throws PsseModelException
	{
		return new BusSubList(_model.getBuses(), _model.tp().getBusNdxsForType(ndx, bustype));
	}

	@Override
	public boolean isEnergized(int ndx) throws PsseModelException
	{
		return _model.tp().isIslandEnergized(ndx);
	}

	@Override
	public long getKey(int ndx) throws PsseModelException
	{
		// TODO Auto-generated method stub
		return 0;
	}

}
