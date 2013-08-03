package com.powerdata.openpa.psse.csv;

import com.powerdata.openpa.psse.Bus;
import com.powerdata.openpa.psse.BusList;
import com.powerdata.openpa.psse.PsseModelException;

public class TransformerSubList extends com.powerdata.openpa.psse.TransformerSubList
{
	BusList _buses;
	
	public TransformerSubList(BusList buses, TransformerRawList xfrlist,
			int[] keeptx) throws PsseModelException
	{
		super(xfrlist, keeptx);
		_buses = buses;
	}

	@Override
	public Bus getFromBus(int ndx) throws PsseModelException { return _buses.get(getI(ndx)); }
	@Override
	public Bus getToBus(int ndx) throws PsseModelException { return _buses.get(getJ(ndx)); }
}
