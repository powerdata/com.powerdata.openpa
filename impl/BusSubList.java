package com.powerdata.openpa.impl;

import com.powerdata.openpa.Area;
import com.powerdata.openpa.Bus;
import com.powerdata.openpa.BusList;
import com.powerdata.openpa.ElectricalIsland;
import com.powerdata.openpa.ListMetaType;
import com.powerdata.openpa.Owner;
import com.powerdata.openpa.PAModelException;
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
	public float getVM(int ndx) throws PAModelException
	{
		return _src.getVM(_ndx[ndx]);
	}

	@Override
	public void setVM(int ndx, float vm) throws PAModelException
	{
		_src.setVM(_ndx[ndx], vm);
	}

	@Override
	public float[] getVM() throws PAModelException
	{
		return mapFloat(_src.getVM());
	}

	@Override
	public void setVM(float[] vm) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			_src.setVM(_ndx[i], vm[i]);
	}

	@Override
	public float getVA(int ndx) throws PAModelException
	{
		return _src.getVA(_ndx[ndx]);
	}

	@Override
	public void setVA(int ndx, float va) throws PAModelException
	{
		_src.setVA(_ndx[ndx], va);
	}

	@Override
	public float[] getVA() throws PAModelException
	{
		return mapFloat(_src.getVA());
	}

	@Override
	public void setVA(float[] va) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			_src.setVA(_ndx[i], va[i]);
	}

	@Override
	public int getFreqSrcPri(int ndx) throws PAModelException
	{
		return _src.getFreqSrcPri(_ndx[ndx]);
	}

	@Override
	public void setFreqSrcPri(int ndx, int fsp) throws PAModelException
	{
		_src.setFreqSrcPri(_ndx[ndx], fsp);
	}

	@Override
	public int[] getFreqSrcPri() throws PAModelException
	{
		return mapInt(_src.getFreqSrcPri());
	}

	@Override
	public void setFreqSrcPri(int[] fsp) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			_src.setFreqSrcPri(_ndx[i], fsp[i]);
	}

	@Override
	public ElectricalIsland getIsland(int ndx) throws PAModelException
	{
		return _src.getIsland(_ndx[ndx]);
	}

	@Override
	public Area getArea(int ndx) throws PAModelException
	{	
		return _src.getArea(_ndx[ndx]);
	}

	@Override
	public void setArea(int ndx, Area a) throws PAModelException
	{
		_src.setArea(_ndx[ndx], a);
	}

	@Override
	public Area[] getArea() throws PAModelException
	{
		return mapObject(_src.getArea());
	}

	@Override
	public void setArea(Area[] a) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			_src.setArea(_ndx[i], a[i]);
	}

	@Override
	public Station getStation(int ndx) throws PAModelException
	{
		return _src.getStation(_ndx[ndx]);
	}

	@Override
	public void setStation(int ndx, Station s) throws PAModelException
	{
		_src.setStation(_ndx[ndx], s);
	}

	@Override
	public Station[] getStation() throws PAModelException
	{
		return mapObject(_src.getStation());
	}

	@Override
	public void setStation(Station[] s) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			_src.setStation(_ndx[i], s[i]);
	}

	@Override
	public Owner getOwner(int ndx) throws PAModelException
	{
		return _src.getOwner(_ndx[ndx]);
	}

	@Override
	public void setOwner(int ndx, Owner o) throws PAModelException
	{
		_src.setOwner(_ndx[ndx], o);
	}

	@Override
	public Owner[] getOwner() throws PAModelException
	{
		return mapObject(_src.getOwner());
	}

	@Override
	public void setOwner(Owner[] o) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			_src.setOwner(_ndx[i], o[i]);
	}

	@Override
	public VoltageLevel getVoltageLevel(int ndx) throws PAModelException
	{
		return _src.getVoltageLevel(_ndx[ndx]);
	}

	@Override
	public void setVoltageLevel(int ndx, VoltageLevel l) throws PAModelException
	{
		_src.setVoltageLevel(_ndx[ndx], l);
	}

	@Override
	public VoltageLevel[] getVoltageLevel() throws PAModelException
	{
		return mapObject(_src.getVoltageLevel());
	}

	@Override
	public void setVoltageLevel(VoltageLevel[] l) throws PAModelException
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
	public ListMetaType getListMeta()
	{
		return ListMetaType.Bus;
	}

}
