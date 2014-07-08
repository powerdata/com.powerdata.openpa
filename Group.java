package com.powerdata.openpa;

public class Group extends AbstractBaseObject implements PALists
{
	protected GroupList<? extends Group> _list;
	
	public Group(GroupList<? extends Group> list, int ndx)
	{
		super(list, ndx);
		_list = list;
	}

	@Override
	public BusList getBuses()
	{
		return _list.getBuses(_ndx);
	}

	@Override
	public SwitchList getSwitches()
	{
		return _list.getSwitches(_ndx);
	}

	@Override
	public LineList getLines()
	{
		return _list.getLines(_ndx);
	}

	@Override
	public SeriesReacList getSeriesReactors()
	{
		return _list.getSeriesReactors(_ndx);
	}

	@Override
	public SeriesCapList getSeriesCapacitors()
	{
		return _list.getSeriesCapacitors(_ndx);
	}

	@Override
	public TransformerList getTransformers()
	{
		return _list.getTransformers(_ndx);
	}

	@Override
	public PhaseShifterList getPhaseShifters()
	{
		return _list.getPhaseShifters(_ndx);
	}

	@Override
	public TwoTermDCLineList getTwoTermDCLines()
	{
		return _list.getTwoTermDCLines(_ndx);
	}

	@Override
	public GenList getGenerators()
	{
		return _list.getGenerators(_ndx);
	}

	@Override
	public LoadList getLoads()
	{
		return _list.getLoads(_ndx);
	}

	@Override
	public ShuntReacList getShuntReactors()
	{
		return _list.getShuntReactors(_ndx);
	}

	@Override
	public ShuntCapList getShuntCapacitors()
	{
		return _list.getShuntCapacitors(_ndx);
	}

	@Override
	public SwitchedShuntList getSwitchedShunts()
	{
		return _list.getSwitchedShunts(_ndx);
	}

	@Override
	public SVCList getSVCs()
	{
		return _list.getSVCs(_ndx);
	}

}
