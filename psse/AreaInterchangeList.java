package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.BaseList;

public abstract class AreaInterchangeList<T extends AreaInterchange> extends BaseList<T>
{
	/* Convenience methods */
	public abstract Bus getSlackBus(int ndx);
	public abstract float getIntExport(int ndx);
	public abstract float getIntTol(int ndx);

	/* Raw values */
	public abstract int getI(int ndx);
	public abstract String getISW(int ndx);
	public abstract String getARNAME(int ndx);
	public abstract float getPDES(int ndx);
	public abstract float getPTOL(int ndx);
}
