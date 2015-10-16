package com.powerdata.openpa;

public class Bus extends Group
{
	BusList _list;
	
	public Bus(BusList list, int ndx)
	{
		super(list, ndx);
		_list = list;
	}
	
	public Area getArea() throws PAModelException
	{
		return _list.getArea(_ndx);
	}

	public void setArea(Area a) throws PAModelException
	{
		_list.setArea(_ndx, a);
	}
	
	public Owner getOwner() throws PAModelException
	{
		return _list.getOwner(_ndx);
	}

	public void setOwner(Owner o) throws PAModelException
	{
		_list.setOwner(_ndx, o);
	}
	
	/** get voltage magnitude in kV */
	public float getVM()  throws PAModelException
	{
		return _list.getVM(_ndx);
	}
	/** set voltage magnitude in kV */
	public void setVM(float vm)  throws PAModelException
	{
		_list.setVM(_ndx, vm);
	}
	/** get voltage angle in degrees */
	public float getVA()  throws PAModelException
	{
		return _list.getVA(_ndx);
	}
	/** set voltage angle in degrees */
	public void setVA(float va)  throws PAModelException
	{
		_list.setVA(_ndx, va);
	}
	/** get frequency source priority */
	public int getFreqSrcPri() throws PAModelException
	{
		return _list.getFreqSrcPri(_ndx);
	}
	
	public void setFreqSrcPri(int fsp) throws PAModelException
	{
		_list.setFreqSrcPri(_ndx, fsp);
	}
	
	public ElectricalIsland getIsland() throws PAModelException
	{
		return _list.getIsland(_ndx);
	}
	
	public Station getStation() throws PAModelException
	{
		return _list.getStation(_ndx);
	}

	public void setStation(Station s) throws PAModelException
	{
		_list.setStation(_ndx, s);
	}
	
	public VoltageLevel getVoltageLevel() throws PAModelException
	{
		return _list.getVoltageLevel(_ndx);
	}
	
	public void setVoltageLevel(VoltageLevel l) throws PAModelException
	{
		_list.setVoltageLevel(_ndx, l);
	}

	@Override
	public BusList getList()
	{
		return _list;
	}
	
	
}
