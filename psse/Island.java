package com.powerdata.openpa.psse;

public class Island extends PsseBaseObject
{
	protected IslandList _list;
	
	public Island(int ndx, IslandList list)
	{
		super(list,ndx);
		_list = list;
	}

	public BusList getBuses() throws PsseModelException {return _list.getBuses(_ndx);}
	public GenList getGenerators() throws PsseModelException {return _list.getGenerators(_ndx);}
	public LoadList getLoads() throws PsseModelException {return _list.getLoads(_ndx);}
	public LineList getLines() throws PsseModelException {return _list.getLines(_ndx);}
	public TransformerList getTransformers() throws PsseModelException {return _list.getTransformers(_ndx);}
	public PhaseShifterList getPhaseShifters() throws PsseModelException {return _list.getPhaseShifters(_ndx);}
	public SwitchedShuntList getSwitchedShunts() throws PsseModelException {return _list.getSwitchedShunts(_ndx);}
	public SwitchList getSwitches() throws PsseModelException {return _list.getSwitches(_ndx);}

}
