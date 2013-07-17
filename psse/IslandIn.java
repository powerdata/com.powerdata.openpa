package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.BaseObject;

public class IslandIn extends BaseObject implements EquipGroup
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
	@Override
	public GenInList getGenerators() throws PsseModelException {return _list.getGenerators(_ndx);}
	@Override
	public LoadInList getLoads() throws PsseModelException {return _list.getLoads(_ndx);}
	@Override
	public LineInList getLines() throws PsseModelException {return _list.getLines(_ndx);}
	@Override
	public TransformerInList getTransformers() throws PsseModelException {return _list.getTransformers(_ndx);}
	@Override
	public PhaseShifterInList getPhaseShifters() throws PsseModelException {return _list.getPhaseShifters(_ndx);}
	@Override
	public SwitchedShuntInList getSwitchedShunts() throws PsseModelException {return _list.getSwitchedShunts(_ndx);}
	@Override
	public SwitchList getSwitches() throws PsseModelException {return _list.getSwitches(_ndx);}

}
