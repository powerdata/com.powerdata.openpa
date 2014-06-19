package com.powerdata.openpa;

public interface ModelEventListener
{
	public void modelChange(BaseList<? extends BaseObject> l, String name, int ofs[]) throws PAModelException;
}
