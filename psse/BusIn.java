package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.BaseObject;
import com.powerdata.openpa.tools.Complex;
import com.powerdata.openpa.tools.PComplex;

public class BusIn extends BaseObject
{
	protected BusInList _list;
	
	public BusIn(int ndx, BusInList list)
	{
		super(ndx);
		_list = list;
	}

	@Override
	public String getDebugName() throws PsseModelException {return getNAME();}

	@Override
	public String getObjectID() {return _list.getObjectID(_ndx);}
	
	/* Convenience methods */
	
	/** enumerated IDE code */
	public BusTypeCode getBusType() throws PsseModelException {return _list.getBusType(_ndx);}
	/** Area */
	public Area getAreaObject() throws PsseModelException {return _list.getAreaObject(_ndx);}
	/** Zone */
	public Zone getZoneObject() throws PsseModelException {return _list.getZoneObject(_ndx);}
	/** Owner */
	public Owner getOwnerObject() throws PsseModelException {return _list.getOwnerObject(_ndx);}
	/** Active component of shunt admittance to ground (GL) p.u. */
	public float getShuntG() throws PsseModelException {return _list.getShuntG(_ndx);}
	/** Reactive component of shunt admittance to ground (BL) p.u. */
	public float getShuntB() throws PsseModelException {return _list.getShuntB(_ndx);}
	/** get complex shunt admittance to ground */
	public Complex getShuntY() throws PsseModelException {return _list.getShuntY(_ndx);}
	/** Bus voltage phase angle in radians */
	public float getVaRad() throws PsseModelException {return _list.getVaRad(_ndx);}
	/** complex bus voltage */
	public PComplex getVoltage() throws PsseModelException {return _list.getVoltage(_ndx);}


	/* Raw PSS/e methods */
	
	/** Bus number */
	public int getI() throws PsseModelException {return _list.getI(_ndx);}
	/** Alphanumeric identifier */
	public String getNAME() throws PsseModelException {return _list.getNAME(_ndx);}
	/** Bus base voltage */
	public float getBASKV() throws PsseModelException {return _list.getBASKV(_ndx);}
	/** Bus type code */
	public int getIDE() throws PsseModelException {return _list.getIDE(_ndx);}
	/** Active component of shunt admittance to ground in MW at unity voltage*/
	public float getGL() throws PsseModelException {return _list.getGL(_ndx);}
	/** Reactive component of shunt admittance to ground in MVAr at unity voltage*/
	public float getBL() throws PsseModelException {return _list.getBL(_ndx);}
	/** Area number */
	public int getAREA() throws PsseModelException {return _list.getAREA(_ndx);}
	/** Zone number */
	public int getZONE() throws PsseModelException {return _list.getZONE(_ndx);}
	/** Bus voltage magnitude p.u.*/
	public float getVM() throws PsseModelException {return _list.getVM(_ndx);}
	/** Bus voltage phase angle in degrees */
	public float getVA() throws PsseModelException {return _list.getVA(_ndx);}
	/** Owner number */
	public int getOWNER() throws PsseModelException {return _list.getOWNER(_ndx);}
	
}
