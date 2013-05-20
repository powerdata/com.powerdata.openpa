package com.powerdata.openpa.tools;

public interface StringAttrib<T extends BaseObject>
{
	public String get(T obj);
	public void set(T obj, String val);
}
