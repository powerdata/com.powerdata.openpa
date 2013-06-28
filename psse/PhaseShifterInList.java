package com.powerdata.openpa.psse;

public abstract class PhaseShifterInList extends PsseBaseInputList<PhaseShifterIn>
{
	
	public PhaseShifterInList(PsseInputModel model) 
	{
		super(model);
	}

	
	/** Get a Transformer by it's index. */
	@Override
	public PhaseShifterIn get(int ndx) { return new PhaseShifterIn(ndx,this); }
	/** Get a Transformer by it's ID. */
	@Override
	public PhaseShifterIn get(String id) { return super.get(id); }
	
	public BusIn getBus1(int ndx) throws PsseModelException {return null; /*TODO:*/}
	public BusIn getBus2(int ndx) throws PsseModelException {return null; /*TODO:*/}
	public BusIn getBus3(int ndx) throws PsseModelException {return null; /*TODO:*/}



}	
