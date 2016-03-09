package com.powerdata.openpa;

import java.util.List;
import com.powerdata.openpa.impl.SuperList;


public class Group extends AbstractBaseObject implements PALists
{
	protected GroupListIfc<? extends Group> _list;
	
	SuperList _slist = new SuperList(this);
	
	public Group(GroupListIfc<? extends Group> list, int ndx)
	{
		super(list, ndx);
		_list = list;
	}

	@Override
	public BusList getBuses() throws PAModelException
	{
		return _list.getBuses(_ndx);
	}

	@Override
	public SwitchList getSwitches() throws PAModelException
	{
		return _list.getSwitches(_ndx);
	}

	@Override
	public LineList getLines() throws PAModelException
	{
		return _list.getLines(_ndx);
	}

	@Override
	public SeriesReacList getSeriesReactors() throws PAModelException
	{
		return _list.getSeriesReactors(_ndx);
	}

	@Override
	public SeriesCapList getSeriesCapacitors() throws PAModelException
	{
		return _list.getSeriesCapacitors(_ndx);
	}

	@Override
	public TransformerList getTransformers() throws PAModelException
	{
		return _list.getTransformers(_ndx);
	}

	@Override
	public PhaseShifterList getPhaseShifters() throws PAModelException
	{
		return _list.getPhaseShifters(_ndx);
	}

	@Override
	public TwoTermDCLineList getTwoTermDCLines() throws PAModelException
	{
		return _list.getTwoTermDCLines(_ndx);
	}

	@Override
	public GenList getGenerators() throws PAModelException
	{
		return _list.getGenerators(_ndx);
	}

	@Override
	public LoadList getLoads() throws PAModelException
	{
		return _list.getLoads(_ndx);
	}

	@Override
	public ShuntReacList getShuntReactors() throws PAModelException
	{
		return _list.getShuntReactors(_ndx);
	}

	@Override
	public ShuntCapList getShuntCapacitors() throws PAModelException
	{
		return _list.getShuntCapacitors(_ndx);
	}

	@Override
	public SVCList getSVCs() throws PAModelException
	{
		return _list.getSVCs(_ndx);
	}

	@Override
	public List<OneTermDevList> getOneTermDevices() throws PAModelException
	{
		return _slist.getOneTermDevs();
	}

	@Override
	public List<TwoTermDevList> getTwoTermDevices() throws PAModelException
	{
		return _slist.getTwoTermDevs();
	}

	@Override
	public List<ACBranchList> getACBranches() throws PAModelException
	{
		return _slist.getACBranches();
	}

	@Override
	public List<FixedShuntList> getFixedShunts() throws PAModelException
	{
		return _slist.getFixedShunts();
	}

	@Override
	public SteamTurbineList getSteamTurbines() throws PAModelException
	{
		throw new PAModelException("Not yet implemented");
	}

}
