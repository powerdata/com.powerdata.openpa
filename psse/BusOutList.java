package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.Complex;


public abstract class BusOutList extends PsseBaseOutputList<BusOut>
{

	public BusOutList(PsseOutputModel model) {super(model);}

	public abstract void setVmag(int ndx, float vm);
	public abstract void setVang(int ndx, float va);
	public abstract void setVoltage(int ndx, Complex v);
}
