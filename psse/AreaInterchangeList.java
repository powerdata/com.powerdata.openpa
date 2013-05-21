package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.BaseList;

public abstract class AreaInterchangeList<T extends AreaInterchange> extends BaseList<T>
{
	/* Convenience methods */
	public Bus getSlackBus(int ndx) {return null;}
	public float getIntExport(int ndx) {return 0;}
	public float getIntTol(int ndx) {return 0;}

	/* Raw values */
	public abstract int getI(int ndx);
	public abstract String getISW(int ndx);
	public abstract String getARNAME(int ndx);
	public abstract float getPDES(int ndx);
	public abstract float getPTOL(int ndx);
}
