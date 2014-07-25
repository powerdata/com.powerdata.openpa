package com.powerdata.openpa;

public class Island extends Group
{
	protected IslandList _list;
	
	public Island(IslandList list, int ndx)
	{
		super(list, ndx);
		_list = list;
	}
	
	public boolean isEnergized() throws PAModelException
	{
		return _list.isEnergized(_ndx);
	}
	
	public float getFreq() throws PAModelException
	{
		return _list.getFreq(_ndx);
	}
	
	public void setFreq(float f) throws PAModelException
	{
		_list.setFreq(_ndx, f);
	}
	
	public Bus getFreqSrc() throws PAModelException
	{
		return _list.getFreqSrc(_ndx);
	}
	
	public void setFreqSrc(Bus fsrc) throws PAModelException
	{
		_list.setFreqSrc(_ndx, fsrc);
	}

}
