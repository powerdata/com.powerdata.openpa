package com.powerdata.openpa.padbc;

import com.powerdata.openpa.tools.BaseList;

public abstract class StaticVarCompList<T extends StaticVarComp> extends BaseList<T>
{
	public enum SVCMode
	{
		RegulateVoltage, FixedReactive;
	}
	
	public abstract float getMinB(int ndx);
	public abstract float getMaxB(int ndx);
	public abstract float getSlope(int ndx);
	public abstract SVCMode getMode(int ndx);
	public abstract float getBSetpt(int ndx);
	public abstract float getVoltageSetpt(int ndx);

	public abstract float getReacPwr(int ndx);
	public abstract void updateReacPwr(int ndx, float b);


}
