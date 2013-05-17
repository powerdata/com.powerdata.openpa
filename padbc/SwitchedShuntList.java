package com.powerdata.openpa.padbc;

import java.util.AbstractList;

public abstract class SwitchedShuntList extends AbstractList<SwitchedShunt> implements BaseList
{
	@Override
	public SwitchedShunt get(int ndx) {return new SwitchedShunt(ndx, this);}

	public abstract float getNominalB(int index);
	public abstract void updateReacPwr(int index, float b);

}
