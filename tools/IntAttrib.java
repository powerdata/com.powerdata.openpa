package com.powerdata.openpa.tools;

public interface IntAttrib<T extends BaseObject>
{
	public int get(T obj);
	public void set(T obj, int val);
}
