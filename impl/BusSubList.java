package com.powerdata.openpa.impl;

import com.powerdata.openpa.Area;
import com.powerdata.openpa.Bus;
import com.powerdata.openpa.BusList;
import com.powerdata.openpa.Island;
import com.powerdata.openpa.ListMetaType;
import com.powerdata.openpa.Owner;
import com.powerdata.openpa.Station;
import com.powerdata.openpa.VoltageLevel;

/**
 * Create  a sublist of buses.  
 * @author chris@powerdata.com
 */

public class BusSubList extends GroupSubList<Bus> implements BusList
{
	BusList _src;
	
	public BusSubList(BusList src, int[] ndx)
	{
		super(src, ndx);
		_src = src;
	}

	@Override
	public float getVM(int ndx)
	{
		return _src.getVM(_ndx[ndx]);
	}

	@Override
	public void setVM(int ndx, float vm)
	{
		_src.setVM(_ndx[ndx], vm);
	}

	@Override
	public float[] getVM()
	{
		return mapFloat(_src.getVM());
	}

	@Override
	public void setVM(float[] vm)
	{
		for(int i=0; i < _size; ++i)
			_src.setVM(_ndx[i], vm[i]);
	}

	@Override
	public float getVA(int ndx)
	{
		return _src.getVA(_ndx[ndx]);
	}

	@Override
	public void setVA(int ndx, float va)
	{
		_src.setVA(_ndx[ndx], va);
	}

	@Override
	public float[] getVA()
	{
		return mapFloat(_src.getVA());
	}

	@Override
	public void setVA(float[] va)
	{
		for(int i=0; i < _size; ++i)
			_src.setVA(_ndx[i], va[i]);
	}

	@Override
	public int getFrequencySourcePriority(int ndx)
	{
		return _src.getFrequencySourcePriority(_ndx[ndx]);
	}

	@Override
	public void setFrequencySourcePriority(int ndx, int fsp)
	{
		_src.setFrequencySourcePriority(_ndx[ndx], fsp);
	}

	@Override
	public int[] getFrequencySourcePriority()
	{
		return mapInt(_src.getFrequencySourcePriority());
	}

	@Override
	public void setFrequencySourcePriority(int[] fsp)
	{
		for(int i=0; i < _size; ++i)
			_src.setFrequencySourcePriority(_ndx[i], fsp[i]);
	}

	@Override
	public Island getIsland(int ndx)
	{
		return _src.getIsland(_ndx[ndx]);
	}

	@Override
	public Area getArea(int ndx)
	{
		return _src.getArea(_ndx[ndx]);
	}

	@Override
	public void setArea(int ndx, Area a)
	{
		_src.setArea(_ndx[ndx], a);
	}

	@Override
	public Area[] getArea()
	{
		return mapObject(_src.getArea());
	}

	@Override
	public void setArea(Area[] a)
	{
		for(int i=0; i < _size; ++i)
			_src.setArea(_ndx[i], a[i]);
	}

	@Override
	public Station getStation(int ndx)
	{
		return _src.getStation(_ndx[ndx]);
	}

	@Override
	public void setStation(int ndx, Station s)
	{
		_src.setStation(_ndx[ndx], s);
	}

	@Override
	public Station[] getStation()
	{
		return mapObject(_src.getStation());
	}

	@Override
	public void setStation(Station[] s)
	{
		for(int i=0; i < _size; ++i)
			_src.setStation(_ndx[i], s[i]);
	}

	@Override
	public Owner getOwner(int ndx)
	{
		return _src.getOwner(_ndx[ndx]);
	}

	@Override
	public void setOwner(int ndx, Owner o)
	{
		_src.setOwner(_ndx[ndx], o);
	}

	@Override
	public Owner[] getOwner()
	{
		return mapObject(_src.getOwner());
	}

	@Override
	public void setOwner(Owner[] o)
	{
		for(int i=0; i < _size; ++i)
			_src.setOwner(_ndx[i], o[i]);
	}

	@Override
	public VoltageLevel getVoltageLevel(int ndx)
	{
		return _src.getVoltageLevel(_ndx[ndx]);
	}

	@Override
	public void setVoltageLevel(int ndx, VoltageLevel l)
	{
		_src.setVoltageLevel(_ndx[ndx], l);
	}

	@Override
	public VoltageLevel[] getVoltageLevel()
	{
		return mapObject(_src.getVoltageLevel());
	}

	@Override
	public void setVoltageLevel(VoltageLevel[] l)
	{
		for(int i=0; i < _size; ++i)
			_src.setVoltageLevel(_ndx[i], l[i]);
	}

	@Override
	public Bus get(int index)
	{
		return new Bus(this, index);
	}

	@Override
	public ListMetaType getMetaType()
	{
		return ListMetaType.Bus;
	}

}
