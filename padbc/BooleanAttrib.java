package com.powerdata.openpa.padbc;

public interface BooleanAttrib<T extends BaseObject>
{
	public boolean getVal(T obj);
	public void setVal(T obj, boolean val);
}
