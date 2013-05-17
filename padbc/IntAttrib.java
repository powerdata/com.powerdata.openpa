package com.powerdata.openpa.padbc;

public interface IntAttrib<T extends BaseObject>
{
	public int getVal(T obj);
	public void setVal(T obj, int val);
}
