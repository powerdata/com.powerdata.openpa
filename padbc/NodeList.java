package com.powerdata.openpa.padbc;

import com.powerdata.openpa.tools.BaseList;

public abstract class NodeList<T extends Node> extends BaseList<T>
{
	public abstract float getNominalKV(int ndx);

	public abstract float getVmag(int ndx);

	public abstract float getVang(int ndx);

	public abstract void updateVmag(int ndx, float vm);

	public abstract void updateVang(int ndx, float va);

	@Override
	public abstract T get(int ndx);
}
