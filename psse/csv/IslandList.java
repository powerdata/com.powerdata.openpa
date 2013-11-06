package com.powerdata.openpa.psse.csv;

import com.powerdata.openpa.psse.BusTypeCode;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.psse.util.TP;

public class IslandList extends com.powerdata.openpa.psse.IslandList
{
	
	TP _tp;
	
	public IslandList(PsseModel eq, TP tp) throws PsseModelException
	{
		super(eq);
		_tp = tp;
	}
	
	@Override
	public String getObjectID(int ndx) throws PsseModelException
	{
		return String.valueOf(ndx);
	}

	@Override
	public int size() {return _tp.getIslandCount();}

	@Override
	public int[] getBusNdxsForType(int ndx, BusTypeCode bustype)
	{
		return _tp.getBusNdxsForType(ndx, bustype);
	}

	@Override
	public boolean isEnergized(int ndx) throws PsseModelException
	{
		return _tp.isIslandEnergized(ndx);
	}

	@Override
	public int getAngleRefBusNdx(int ndx) throws PsseModelException
	{
		return _tp.getAngleRefBusNdx(ndx);
	}
}
