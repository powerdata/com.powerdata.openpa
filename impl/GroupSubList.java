package com.powerdata.openpa.impl;

import com.powerdata.openpa.Bus;
import com.powerdata.openpa.BusList;
import com.powerdata.openpa.GenList;
import com.powerdata.openpa.Group;
import com.powerdata.openpa.GroupListIfc;
import com.powerdata.openpa.LineList;
import com.powerdata.openpa.LoadList;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.PhaseShifterList;
import com.powerdata.openpa.SVCList;
import com.powerdata.openpa.SeriesCapList;
import com.powerdata.openpa.SeriesReacList;
import com.powerdata.openpa.ShuntCapList;
import com.powerdata.openpa.ShuntReacList;
import com.powerdata.openpa.SwitchList;
import com.powerdata.openpa.TransformerList;
import com.powerdata.openpa.TwoTermDCLineList;

public abstract class GroupSubList<T extends Group> extends SubList<T> implements GroupListIfc<T>
{
	GroupListIfc<T> _src;

	public GroupSubList(GroupListIfc<T> src, int[] ndx)
	{
		super(src, ndx);
		_src = src;
		
//		_grp = new TIntIntHashMap(_size, 0.5f, -1, -1);
//		for(int i=0; i < _size; ++i)
//		{
//			_grp.put(_ndx[i], i);
//		}
	}

	@Override
	public BusList getBuses(int ndx) throws PAModelException
	{
		return _src.getBuses(_ndx[ndx]);
	}

	@Override
	public SwitchList getSwitches(int ndx) throws PAModelException
	{
		return _src.getSwitches(_ndx[ndx]);
	}

	@Override
	public LineList getLines(int ndx) throws PAModelException
	{
		return _src.getLines(_ndx[ndx]);
	}

	@Override
	public SeriesReacList getSeriesReactors(int ndx) throws PAModelException
	{
		return _src.getSeriesReactors(_ndx[ndx]);
	}

	@Override
	public SeriesCapList getSeriesCapacitors(int ndx) throws PAModelException
	{
		return _src.getSeriesCapacitors(_ndx[ndx]);
	}

	@Override
	public TransformerList getTransformers(int ndx) throws PAModelException
	{
		return _src.getTransformers(_ndx[ndx]);
	}

	@Override
	public PhaseShifterList getPhaseShifters(int ndx) throws PAModelException
	{
		return _src.getPhaseShifters(_ndx[ndx]);
	}

	@Override
	public TwoTermDCLineList getTwoTermDCLines(int ndx) throws PAModelException
	{
		return _src.getTwoTermDCLines(_ndx[ndx]);
	}

	@Override
	public GenList getGenerators(int ndx) throws PAModelException
	{
		return _src.getGenerators(_ndx[ndx]);
	}

	@Override
	public LoadList getLoads(int ndx) throws PAModelException
	{
		return _src.getLoads(_ndx[ndx]);
	}

	@Override
	public ShuntReacList getShuntReactors(int ndx) throws PAModelException
	{
		return _src.getShuntReactors(_ndx[ndx]);
	}

	@Override
	public ShuntCapList getShuntCapacitors(int ndx) throws PAModelException
	{
		return _src.getShuntCapacitors(_ndx[ndx]);
	}

	@Override
	public SVCList getSVCs(int ndx) throws PAModelException
	{
		return _src.getSVCs(_ndx[ndx]);
	}

	@Override
	public T getByBus(Bus b) throws PAModelException
	{
		return _src.getByBus(b);
	}

	@Override
	public int[] translateBusIndexes(int[] indexes)
	{
		return _src.translateBusIndexes(indexes);
	}

}
