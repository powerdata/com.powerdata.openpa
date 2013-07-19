package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.BaseObject;

public class Owner extends BaseObject
{
	protected OwnerList _list;
	
	public Owner(int ndx, OwnerList list)
	{
		super(list,ndx);
		_list = list;
	}


	@Override
	public String getDebugName() throws PsseModelException {return getOWNAME();}
	/* Groups */
	public SwitchList getSwitches() throws PsseModelException { return _list.getSwitches(_ndx); }
	public GenList getGenerators() throws PsseModelException { return _list.getGenerators(_ndx); }
	public LoadList getLoads() throws PsseModelException { return _list.getLoads(_ndx); }
	public LineList getLines() throws PsseModelException { return _list.getLines(_ndx); }
	public TransformerList getTransformers() throws PsseModelException { return _list.getTransformers(_ndx); }
	public SwitchedShuntList getSwitchedShunts() throws PsseModelException { return _list.getSwitchedShunts(_ndx); }
	public PhaseShifterList getPhaseShifters() throws PsseModelException {return _list.getPhaseShifters(_ndx);}
	
	/* raw PSS/e methods */
	
	/** Owner number */
	public int getI() throws PsseModelException {return _list.getI(_ndx);}
	/** Alphanumeric identifier assigned to owner */
	public String getOWNAME() throws PsseModelException {return _list.getOWNAME(_ndx);}
	
}
