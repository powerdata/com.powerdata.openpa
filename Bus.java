package com.powerdata.openpa;

public class Bus extends AbstractBaseObject implements PALists
{
	BusListIfc _list;
	
	public Bus(BusListIfc list, int ndx)
	{
		super(list, ndx);
		_list = list;
	}
	
	public Area getArea()
	{
		return _list.getArea(_ndx);
	}

	public void setArea(Area a)
	{
		_list.setArea(_ndx, a);
	}
	
	/** get bus base KV */
	public float getBaseKV()
	{
		return _list.getBaseKV(_ndx);
	}
	
	/** set bus base KV */
	public void setBaseKV(float kv)
	{
		_list.setBaseKV(_ndx, kv);
	}
	/** get voltage magnitude in kV */
	public float getVM() 
	{
		return _list.getVM(_ndx);
	}
	/** set voltage magnitude in kV */
	public void setVM(float vm) 
	{
		_list.setVM(_ndx, vm);
	}
	/** get voltage angle in degrees */
	public float getVA() 
	{
		return _list.getVA(_ndx);
	}
	/** set voltage angle in degrees */
	public void setVA(float va) 
	{
		_list.setVA(_ndx, va);
	}
	public float getVMpu()
	{
		return 0;
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
	public TwoTermDCLineList getTwoTermDCLines() 
	{
		return _list.getTwoTermDCLines(_ndx);
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
	
	/** get frequency source priority */
	public int getFrequencySourcePriority()
	{
		return _list.getFrequencySourcePriority(_ndx);
	}
	
	public void setFrequencySourcePriority(int fsp)
	{
		_list.setFrequencySourcePriority(_ndx, fsp);
	}
	
	/**
	 * Bus Frequency
	 * @return frequency at the bus, or -1 if not available
	 */
	public float getFrequency()
	{
		return _list.getFrequency(_ndx);
	}
	
	public void setFrequency(float f)
	{
		_list.setFrequency(_ndx, f);
	}
	
	public Island getIsland()
	{
		return _list.getIsland(_ndx);
	}
	
	public Station getStation()
	{
		return _list.getStation(_ndx);
	}

	public void setStation(Station s)
	{
		_list.setStation(_ndx, s);
	}
}
