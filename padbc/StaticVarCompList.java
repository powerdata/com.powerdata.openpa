package com.powerdata.openpa.padbc;

import java.util.AbstractList;

public abstract class StaticVarCompList extends AbstractList<StaticVarComp> implements BaseList
{
	public enum SVCMode
	{
		RegulateVoltage, FixedReactive;
	}
	
	@Override
	public StaticVarComp get(int ndx) {return new StaticVarComp(ndx, this);}

	public abstract float getMinB(int ndx);
	public abstract float getMaxB(int ndx);
	public abstract float getSlope(int ndx);
	public abstract SVCMode getMode(int ndx);
	public abstract float getBSetpt(int ndx);
	public abstract float getVoltageSetpt(int ndx);

	public abstract float getReacPwr(int index);
	public abstract void updateReacPwr(int ndx, float b);


}
