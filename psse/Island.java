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
	public ShuntList getShunts() throws PsseModelException { return _list.getShunts(_ndx); }
	public SvcList getSvcs() throws PsseModelException { return _list.getSvcs(_ndx); }
	public SwitchList getSwitches() throws PsseModelException {return _list.getSwitches(_ndx);}
	
	public BusList getBusesForType(BusTypeCode bustype) throws PsseModelException
	{
		return _list.getBusesForType(_ndx, bustype);
	}
	
	public int[] getBusNdxsForType(BusTypeCode bustype) throws PsseModelException
	{
		return _list.getBusNdxsForType(_ndx, bustype);
	}

	public boolean isEnergized() throws PsseModelException {return _list.isEnergized(_ndx);}

	public int getAngleRefBusNdx() throws PsseModelException {return _list.getAngleRefBusNdx(_ndx);}
}
