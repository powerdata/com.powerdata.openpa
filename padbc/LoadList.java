package com.powerdata.openpa.padbc;

import java.util.AbstractList;

public abstract class LoadList extends AbstractList<Load> implements BaseList
{
	@Override
	public Load get(int ndx) {return new Load(ndx, this);}

	public abstract float getReacPwr(int ndx);
	public abstract float getActvPwr(int ndx);
	public abstract void updateReacPwr(int ndx, float p);
	public abstract void updateActvPwr(int ndx, float q);
}
