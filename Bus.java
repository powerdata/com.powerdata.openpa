package com.powerdata.openpa;

public class Bus extends Group
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
	
	public Owner getOwner()
	{
		return _list.getOwner(_ndx);
	}

	public void setOwner(Owner o)
	{
		_list.setOwner(_ndx, o);
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
	
	public VoltageLevel getVoltageLevel()
	{
		return _list.getVoltageLevel(_ndx);
	}
	
	public void setVoltageLevel(VoltageLevel l)
	{
		_list.setVoltageLevel(_ndx, l);
	}
	
	public boolean isEnergized()
	{
		return _list.isEnergized(_ndx);
	}
	
	public void setEnergized(boolean state)
	{
		_list.setEnergized(_ndx, state);
	}
}
