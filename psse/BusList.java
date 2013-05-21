package com.powerdata.openpa.psse;

public abstract class BusList<T extends Bus> extends PsseBaseList<T>
{
	public BusList(PsseModel model) {super(model);}

	/* convenience methods */
	
	public BusTypeCode getBusType(int ndx) {return BusTypeCode.fromCode(getIDE(ndx));}
	public float getShuntG(int ndx) {return 0F;}
	public float getShuntB(int ndx) {return 0F;}
	public AreaInterchange getAreaObject(int ndx) {return null;}
	public Zone getZoneObject(int ndx) {return null;}
	public Owner getOwnerObject(int ndx)  {return null;}
	public float getVangRad(int ndx) {return 0F;}

	/* raw methods */

	public abstract int getI(int ndx);
	public String getNAME(int ndx) {return "";}
	public float getBASKV(int ndx) {return 0F;}
	public int getIDE(int ndx) {return 1;}
	public float getGL(int ndx) {return 0F;}
	public float getBL(int ndx) {return 0F;}
	public int getAREA(int ndx) {return 1;}
	public int getZONE(int ndx) {return 1;}
	public float getVM(int ndx) {return 1F;}
	public float getVA(int ndx) {return 0F;}
	public int getOWNER(int ndx) {return 1;}
	
}
