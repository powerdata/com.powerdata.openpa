package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.BaseObject;

public class IslandIn extends BaseObject
{
	protected IslandInList _list;
	
	public IslandIn(int ndx, IslandInList list)
	{
		super(ndx);
		_list = list;
	}

	@Override
	public String getObjectID() throws PsseModelException {return _list.getObjectID(_ndx);}
	public BusInList getBuses() throws PsseModelException {return _list.getBuses(_ndx);}
	public GenInList getGenerators() throws PsseModelException {return _list.getGenerators(_ndx);}
	public LoadInList getLoads() throws PsseModelException {return _list.getLoads(_ndx);}
	public LineInList getLines() throws PsseModelException {return _list.getLines(_ndx);}
	public TransformerInList getTransformers() throws PsseModelException {return _list.getTransformers(_ndx);}
	public PhaseShifterInList getPhaseShifters() throws PsseModelException {return _list.getPhaseShifters(_ndx);}
	public SwitchedShuntInList getSwitchedShunts() throws PsseModelException {return _list.getSwitchedShunts(_ndx);}
	public SwitchList getSwitches() throws PsseModelException {return _list.getSwitches(_ndx);}

}
