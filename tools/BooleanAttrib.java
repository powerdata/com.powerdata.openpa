package com.powerdata.openpa.tools;

public interface BooleanAttrib<T>
{
	public boolean get(T obj);
	public void set(T obj, boolean val);
}
