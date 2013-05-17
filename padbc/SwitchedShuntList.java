package com.powerdata.openpa.padbc;

public abstract class SwitchedShuntList extends BaseList<SwitchedShunt>
{
	@Override
	public SwitchedShunt get(int ndx) {return new SwitchedShunt(ndx, this);}

	public abstract float getNominalB(int index);
	public abstract void updateReacPwr(int index, float b);

}
