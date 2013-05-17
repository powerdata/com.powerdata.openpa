package com.powerdata.openpa.padbc;

public interface StringAttrib<T extends BaseObject>
{
	public String getVal(T obj);
	public String setVal(T obj, String val);
}
