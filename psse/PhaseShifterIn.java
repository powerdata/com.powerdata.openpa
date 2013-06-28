package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.BaseObject;

public class PhaseShifterIn extends BaseObject
{
	protected PhaseShifterInList _list;
	
	public PhaseShifterIn(int ndx, PhaseShifterInList list)
	{
		super(ndx);
		_list = list;
	}

	@Override
	public String getDebugName() throws PsseModelException
	{
		//TODO: implement this
		return null;
	}
	@Override
	public String getObjectID() {return _list.getObjectID(_ndx);}
	
	/* Convenience methods */

	/** Winding 1 bus */ 
	public BusIn getBus1() throws PsseModelException {return _list.getBus1(_ndx);}
	/** Winding 2 bus */
	public BusIn getBus2() throws PsseModelException {return _list.getBus2(_ndx);}
	/** Winding 3 bus for 3 winding transformers*/
	public BusIn getBus3() throws PsseModelException {return _list.getBus3(_ndx);}

}
