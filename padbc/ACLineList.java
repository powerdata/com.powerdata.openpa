package com.powerdata.openpa.padbc;

import java.util.AbstractList;

public abstract class ACLineList extends AbstractList<ACLine> implements BranchList
{
	public abstract float getFromBChg(int ndx);
	
	public abstract float getToBChg(int ndx);
	
	public abstract void updateActvPower(int ndx, float p);
	
	public abstract void updateReacPower(int ndx, float q);
	
	@Override
	public ACLine get(int ndx) {return new ACLine(ndx, this);}

}
