package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.BaseObject;
import com.powerdata.openpa.tools.Complex;

public class LoadIn extends BaseObject
{
	protected LoadInList _list;
	
	public LoadIn(int ndx, LoadInList list)
	{
		super(ndx);
		_list = list;
	}

	@Override
	public String getDebugName() throws PsseModelException
	{
		return String.format("%s %s", getBus().getNAME(), getID());
	}

	@Override
	public String getObjectID() throws PsseModelException {return _list.getObjectID(_ndx);}

	/* convenience methods */
	
	/** Load bus (I) */ 
	public BusIn getBus() throws PsseModelException {return _list.getBus(_ndx);}
	/** get load in-service status (STATUS) as a boolean.  Returns true if in service */
	public boolean getInSvc() throws PsseModelException {return _list.getInSvc(_ndx);}
	/** get Area Interchange record */
	public AreaIn getAreaObj() throws PsseModelException {return _list.getAreaObj(_ndx);}
	/** get Zone record */
	public ZoneIn getZoneObj() throws PsseModelException {return _list.getZoneObj(_ndx);}
	/** base active power of (PL) constant MVA load */
	public float getActvPwr() throws PsseModelException {return _list.getActvPwr(_ndx);}
	/** base reactive power of (QL) constant MVA load */
	public float getReacPwr() throws PsseModelException {return _list.getReacPwr(_ndx);}
	/** get complex power (PL) */
	public Complex getPwr() throws PsseModelException {return _list.getPwr(_ndx);}
	/** active power of constant current load (IP) at 1pu voltage */
	public float getActvPwrI() throws PsseModelException {return _list.getActvPwrI(_ndx);}
	/** reactive power of constant current load (IQ) MVAr at 1pu voltage */
	public float getReacPwrI() throws PsseModelException {return _list.getReacPwrI(_ndx);}
	/** Complex constant current load at 1pu voltage */
	public Complex getPwrI() throws PsseModelException {return _list.getPwrI(_ndx);}
	/** active power of constant admittance load at 1pu voltage*/
	public float getActvPwrY() throws PsseModelException {return _list.getActvPwrY(_ndx);}
	/** reactive power of constant admittance load at 1pu voltage*/
	public float getReacPwrY() throws PsseModelException {return _list.getReacPwrY(_ndx);}
	/** Complex constant admittance load at 1pu voltage */
	public Complex getPwrY() throws PsseModelException  {return _list.getPwrY(_ndx);}
	/** return Owner */
	public OwnerIn getOwnerObj() throws PsseModelException {return _list.getOwnerObj(_ndx);}
	

	
	/* raw PSS/e methods */

	/** bus number or name */
	public String getI() throws PsseModelException {return _list.getI(_ndx);}
	/** load identifies used to differentiate multiple loads at the same bus */
	public String getID() throws PsseModelException {return _list.getID(_ndx);}
	/** in-service status of load (1 for in-service, 0 for out-of-service) */
	public int getSTATUS() throws PsseModelException {return _list.getSTATUS(_ndx);}
	/** index of related area record.  Defaults to same area as bus I */
	public int getAREA() throws PsseModelException {return _list.getAREA(_ndx);}
	/** index of related zone record.  Defaults to same zone as bus I */
	public int getZONE() throws PsseModelException {return _list.getZONE(_ndx);}
	/** active power of constant MVA load in MW */
	public float getPL() throws PsseModelException {return _list.getPL(_ndx);}
	/** reactive power of constant MVA load in MVAr */
	public float getQL() throws PsseModelException {return _list.getQL(_ndx);}
	/** active power of constant current load MW at 1pu voltage */
	public float getIP() throws PsseModelException {return _list.getIP(_ndx);}
	/** reactive power of constant current load MVAr at 1pu voltage */
	public float getIQ() throws PsseModelException {return _list.getIQ(_ndx);}
	/** active power of constant admittance load MW at 1pu voltage*/
	public float getYP() throws PsseModelException {return _list.getYP(_ndx);}
	/** reactive power of constant admittance load MW at 1pu voltage*/
	public float getYQ() throws PsseModelException {return _list.getYQ(_ndx);}
	/** index of related OWNER record.  Defaults to same owner as bus I */
	public int getOWNER() throws PsseModelException {return _list.getOWNER(_ndx);}
}
