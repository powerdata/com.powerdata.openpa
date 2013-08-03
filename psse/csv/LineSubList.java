package com.powerdata.openpa.psse.csv;

import com.powerdata.openpa.psse.Bus;
import com.powerdata.openpa.psse.BusList;
import com.powerdata.openpa.psse.LineList;
import com.powerdata.openpa.psse.PsseModelException;

public class LineSubList extends com.powerdata.openpa.psse.LineSubList
{
	BusList _buses;
	
	public LineSubList(BusList buses, LineList rlines, int[] convertIndex)
			throws PsseModelException
	{
		super(rlines, convertIndex);
		_buses = buses;
	}

	@Override
	public Bus getFromBus(int ndx) throws PsseModelException {return _buses.get(getI(ndx));}
	@Override
	public Bus getToBus(int ndx) throws PsseModelException
	{
		String j = getJ(ndx);
		if (j.charAt(0) == '-') j = j.substring(1);
		return _buses.get(j);
	}
}
