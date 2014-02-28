package com.powerdata.openpa.psse;

public class Station extends PsseBaseObject implements PsseLists
{
	protected StationList _stlist;

	public Station(StationList list, int ndx)
	{
		super(list, ndx);
		_stlist = list;
	}

	@Override
	public BusList getBuses() throws PsseModelException {return _stlist.getBuses(_ndx);}
	@Override
	public GenList getGenerators() throws PsseModelException {return _stlist.getGenerators(_ndx);}
	@Override
	public LoadList getLoads() throws PsseModelException {return _stlist.getLoads(_ndx);}
	@Override
	public LineList getLines() throws PsseModelException {return _stlist.getLines(_ndx);}
	@Override
	public TransformerList getTransformers() throws PsseModelException {return _stlist.getTransformers(_ndx);}
	@Override
	public PhaseShifterList getPhaseShifters() throws PsseModelException {return _stlist.getPhaseShifters(_ndx);}
	@Override
	public SwitchList getSwitches() throws PsseModelException {return _stlist.getSwitches(_ndx);}
	@Override
	public ShuntList getShunts() throws PsseModelException {return _stlist.getShunts(_ndx);}
	@Override
	public SvcList getSvcs() throws PsseModelException {return _stlist.getSvcs(_ndx);}
	@Override
	public SwitchedShuntList getSwitchedShunts() throws PsseModelException {return _stlist.getSwitchedShunts(_ndx);}
	@Override
	public TwoTermDCLineList getTwoTermDCLines() throws PsseModelException {return _stlist.getTwoTermDCLines(_ndx);}
}
