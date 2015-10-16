package com.powerdata.openpa;

public class Transformer extends ACBranch
{
	TransformerList _list;
	
	public Transformer(TransformerList list, int ndx)
	{
		super(list, ndx);
		_list = list;
	}
	public boolean hasLTC() throws PAModelException
	{
		return _list.hasLTC(_ndx);
	}
	public void setHasLTC(boolean b) throws PAModelException
	{
		_list.setHasLTC(_ndx, b);
	}
	public boolean isRegEnabled() throws PAModelException
	{
		return _list.isRegEnabled(_ndx);
	}
	public void setRegEnabled(boolean enabl) throws PAModelException
	{
		_list.setRegEnabled(_ndx, enabl);
	}
	public float getMinKV() throws PAModelException
	{
		return _list.getMinKV(_ndx);
	}
	public void setMinKV(float kv) throws PAModelException
	{
		_list.setMinKV(_ndx, kv);
	}
	public float getMaxKV() throws PAModelException
	{
		return _list.getMaxKV(_ndx);
	}
	public void setMaxKV(float kv) throws PAModelException
	{
		_list.setMaxKV(_ndx, kv);
	}
	public Bus getRegBus() throws PAModelException
	{
		return _list.getRegBus(_ndx);
	}
	public Bus getTapBus() throws PAModelException
	{
		return _list.getTapBus(_ndx);
	}
	
	public float getFromMinTap() throws PAModelException
	{
		return _list.getFromMinTap(_ndx);
	}
	
	public void setFromMinTap(float a) throws PAModelException
	{
		_list.setFromMinTap(_ndx, a);
	}
	
	public float getToMinTap() throws PAModelException
	{
		return _list.getToMinTap(_ndx);
	}
	
	public void setToMinTap(float a) throws PAModelException
	{
		_list.setToMinTap(_ndx, a);
	}
	
	public float getFromMaxTap() throws PAModelException
	{
		return _list.getFromMaxTap(_ndx);
	}
	
	public void setFromMaxTap(float a) throws PAModelException
	{
		_list.setFromMaxTap(_ndx, a);
	}
	
	public float getToMaxTap() throws PAModelException
	{
		return _list.getToMaxTap(_ndx);
	}
	
	public void setToMaxTap(float a) throws PAModelException
	{
		_list.setToMaxTap(_ndx, a);
	}

	public float getFromStepSize() throws PAModelException
	{
		return _list.getFromStepSize(_ndx);
	}

	public void setFromStepSize(float step) throws PAModelException
	{
		_list.setFromStepSize(_ndx, step);
	}

	public float getToStepSize() throws PAModelException
	{
		return _list.getToStepSize(_ndx);
	}

	public void setToStepSize(float step) throws PAModelException
	{
		_list.setToStepSize(_ndx, step);
	}
}