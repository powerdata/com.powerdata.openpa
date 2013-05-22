package com.powerdata.openpa.psse;

public abstract class BusList<T extends Bus> extends PsseBaseList<T>
{
	public BusList(PsseModel model) {super(model);}

	/* convenience methods */
	
	public BusTypeCode getBusType(int ndx) {return BusTypeCode.fromCode(getIDE(ndx));}
	public float getShuntG(int ndx) {return 0F;}
	public float getShuntB(int ndx) {return 0F;}
	public AreaInterchange getAreaObject(int ndx) throws PsseModelException
	{
		return _model.getAreas().get(getAREA(ndx));
	}
	public Zone getZoneObject(int ndx) throws PsseModelException {return _model.getZones().get(getZONE(ndx));}
	public Owner getOwnerObject(int ndx) throws PsseModelException {return _model.getOwners().get(getAREA(ndx));}

	/* raw methods */

	public abstract int getI(int ndx);
	public abstract String getNAME(int ndx);
	public abstract float getBASKV(int ndx);
	public abstract int getIDE(int ndx);
	public abstract float getGL(int ndx);
	public abstract float getBL(int ndx);
	public abstract int getAREA(int ndx);
	public abstract int getZONE(int ndx);
	public abstract float getVM(int ndx);
	public abstract float getVA(int ndx);
	public abstract int getOWNER(int ndx);

	/* defaults */

	protected String getDeftNAME(int ndx) {return "";}
	protected float getDeftBASKV(int ndx) {return 0F;}
	protected int getDeftIDE(int ndx) {return 1;}
	protected float getDeftGL(int ndx) {return 0F;}
	protected float getDeftBL(int ndx) {return 0F;}
	protected int getDeftAREA(int ndx) {return 1;}
	protected int getDeftZONE(int ndx) {return 1;}
	protected float getDeftVM(int ndx) {return 1F;}
	protected float getDeftVA(int ndx) {return 0F;}
	protected int getDeftOWNER(int ndx) {return 1;}

	
}
