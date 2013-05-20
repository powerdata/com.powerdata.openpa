package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.BaseList;

public abstract class BusList<T extends Bus> extends BaseList<T>
{
	public abstract int getI(int ndx);
	public abstract String getNAME(int ndx);
	public abstract float getBASKV(int ndx);
	public abstract int getIDE(int ndx);
	public abstract int getGL(int ndx);
	public abstract int getBL(int ndx);
	public abstract int getAREA(int ndx);
	public abstract int getZONE(int ndx);
	public abstract float getVM(int ndx);
	public abstract float getVA(int ndx);
	public abstract int getOWNER(int ndx);
}
