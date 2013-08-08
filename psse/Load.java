package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.Complex;

public class Load extends PsseBaseObject implements OneTermDev
{
	protected LoadList _list;
	
	public Load(int ndx, LoadList list)
	{
		super(list,ndx);
		_list = list;
	}

	@Override
	public String getDebugName() throws PsseModelException
	{
		return String.format("%s %s", getBus().getNAME(), getID());
	}

	/* convenience methods */
	
	/** Load bus (I) */ 
	@Override
	public Bus getBus() throws PsseModelException {return _list.getBus(_ndx);}
	@Override
	/** get load in-service status (STATUS) as a boolean.  Returns true if in service */
	public boolean isInSvc() throws PsseModelException {return _list.isInSvc(_ndx);}
	/** get Area Interchange record */
	public Area getAreaObj() throws PsseModelException {return _list.getAreaObj(_ndx);}
	/** get Zone record */
	public Zone getZoneObj() throws PsseModelException {return _list.getZoneObj(_ndx);}
	/** return Owner */
	public Owner getOwnerObj() throws PsseModelException {return _list.getOwnerObj(_ndx);}
	

	
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
	
	/* Real-Time Methods */
	/** get the load MW */
	@Override
	public float getRTMW() throws PsseModelException { return _list.getRTMW(_ndx); }
	/** get the load MVar */
	@Override
	public float getRTMVar() throws PsseModelException { return _list.getRTMVar(_ndx); }
	@Override
	public void setRTMW(float mw) throws PsseModelException {_list.setRTMW(_ndx, mw);}
	@Override
	public void setRTMVar(float mvar) throws PsseModelException {_list.setRTMVar(_ndx, mvar);}
	/** get the cold load MW */
	public float getRTColdMW() throws PsseModelException { return _list.getRTColdMW(_ndx); }
	/** get the cold load MVar */
	public float getRTColdMVar() throws PsseModelException { return _list.getRTColdMVar(_ndx); }

	@Override
	public Complex getRTS() throws PsseModelException {return _list.getRTS(_ndx);}
	@Override
	public void setRTS(Complex s) throws PsseModelException {_list.setRTS(_ndx, s);}

}
