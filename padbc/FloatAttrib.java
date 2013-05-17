package com.powerdata.openpa.padbc;

public interface FloatAttrib<T extends BaseObject>
{
	public float getVal(T obj);
	public void setVal(T obj, float val);
}
