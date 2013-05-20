package com.powerdata.openpa.tools;

public interface FloatAttrib<T extends BaseObject>
{
	public float get(T obj);
	public void set(T obj, float val);
}
