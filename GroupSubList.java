package com.powerdata.openpa;

import gnu.trove.map.hash.TIntIntHashMap;

public abstract class GroupSubList<T extends Group> extends SubList<T> implements GroupList<T>
{
	GroupList<T> _src;
	TIntIntHashMap _grp;

	public GroupSubList(GroupList<T> src, int[] ndx)
	{
		super(src, ndx);
		_src = src;
		
		_grp = new TIntIntHashMap(_size, 0.5f, -1, -1);
		for(int i=0; i < _size; ++i)
		{
			_grp.put(_ndx[i], i);
		}
	}

	@Override
	public BusList getBuses(int ndx)
	{
		return _src.getBuses(_ndx[ndx]);
	}

	@Override
	public SwitchList getSwitches(int ndx)
	{
		return _src.getSwitches(_ndx[ndx]);
	}

	@Override
	public LineList getLines(int ndx)
	{
		return _src.getLines(_ndx[ndx]);
	}

	@Override
	public SeriesReacList getSeriesReactors(int ndx)
	{
		return _src.getSeriesReactors(_ndx[ndx]);
	}

	@Override
	public SeriesCapList getSeriesCapacitors(int ndx)
	{
		return _src.getSeriesCapacitors(_ndx[ndx]);
	}

	@Override
	public TransformerList getTransformers(int ndx)
	{
		return _src.getTransformers(_ndx[ndx]);
	}

	@Override
	public PhaseShifterList getPhaseShifters(int ndx)
	{
		return _src.getPhaseShifters(_ndx[ndx]);
	}

	@Override
	public TwoTermDCLineList getTwoTermDCLines(int ndx)
	{
		return _src.getTwoTermDCLines(_ndx[ndx]);
	}

	@Override
	public GenList getGenerators(int ndx)
	{
		return _src.getGenerators(_ndx[ndx]);
	}

	@Override
	public LoadList getLoads(int ndx)
	{
		return _src.getLoads(_ndx[ndx]);
	}

	@Override
	public ShuntReacList getShuntReactors(int ndx)
	{
		return _src.getShuntReactors(_ndx[ndx]);
	}

	@Override
	public ShuntCapList getShuntCapacitors(int ndx)
	{
		return _src.getShuntCapacitors(_ndx[ndx]);
	}

	@Override
	public SwitchedShuntList getSwitchedShunts(int ndx)
	{
		return _src.getSwitchedShunts(_ndx[ndx]);
	}

	@Override
	public SVCList getSVCs(int ndx)
	{
		return _src.getSVCs(_ndx[ndx]);
	}

	@Override
	public T getByBus(Bus b)
	{
		int bgx = _grp.get(getByBus(b).getIndex());
		return (bgx == -1) ? null : get(bgx);
	}

	
}
