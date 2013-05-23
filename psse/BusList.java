package com.powerdata.openpa.psse;

public abstract class BusList<T extends Bus> extends PsseBaseList<T>
{
	public BusList(PsseModel model) {super(model);}

	/* convenience methods */
	
	public abstract BusTypeCode getBusType(int ndx);
	public abstract AreaInterchange getAreaObject(int ndx) throws PsseModelException;
	public abstract Zone getZoneObject(int ndx) throws PsseModelException;
	public abstract Owner getOwnerObject(int ndx) throws PsseModelException;
	public abstract float getShuntG(int ndx);
	public abstract float getShuntB(int ndx);
	public abstract float getVaRad(int ndx);

	/* convenience defaults */
	
	public BusTypeCode getDeftBusType(int ndx) {return BusTypeCode.fromCode(getIDE(ndx));}
	public AreaInterchange getDeftAreaObject(int ndx) throws PsseModelException
	{
		return _model.getAreas().get(getAREA(ndx));
	}
	public Zone getDeftZoneObject(int ndx) throws PsseModelException {return _model.getZones().get(getZONE(ndx));}
	public Owner getDeftOwnerObject(int ndx) throws PsseModelException {return _model.getOwners().get(getAREA(ndx));}
	public float getDeftShuntG(int ndx) {return mw2pu(getGL(ndx));}
	public float getDeftShuntB(int ndx) {return mvar2pu(getBL(ndx));}
	public float getDeftVaRad(int ndx)  {return deg2rad(getVA(ndx));}

	
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

	public String getDeftNAME(int ndx) {return "";}
	public float getDeftBASKV(int ndx) {return 0F;}
	public int getDeftIDE(int ndx) {return 1;}
	public float getDeftGL(int ndx) {return 0F;}
	public float getDeftBL(int ndx) {return 0F;}
	public int getDeftAREA(int ndx) {return 1;}
	public int getDeftZONE(int ndx) {return 1;}
	public float getDeftVM(int ndx) {return 1F;}
	public float getDeftVA(int ndx) {return 0F;}
	public int getDeftOWNER(int ndx) {return 1;}


}
