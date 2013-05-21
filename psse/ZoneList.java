package com.powerdata.openpa.psse;

public abstract class ZoneList<T extends Zone> extends PsseBaseList<T>
{
	public ZoneList(PsseModel model) {super(model);}

	public abstract int getI(int ndx);
	public abstract String getZONAME(int ndx);
}
