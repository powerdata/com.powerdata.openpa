package com.powerdata.openpa.psse;

public class Load extends PsseBaseObject implements OneTermDev
{
	protected LoadList _list;
	
	public Load(int ndx, LoadList list)
	{
		super(list,ndx);
		_list = list;
	}

	/** Load bus (I) */ 
	@Override
	public Bus getBus() throws PsseModelException {return _list.getBus(_ndx);}
	@Override
	/** get load in-service status (STATUS) as a boolean.  Returns true if in service */
	public boolean isInSvc() throws PsseModelException {return _list.isInSvc(_ndx);}
	@Override
	public void setInSvc(boolean state) throws PsseModelException {_list.setInSvc(_ndx, state);}
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
	@Override
	public float getP() throws PsseModelException {return _list.getP(_ndx);}
	/** reactive power of constant MVA load in MVAr */
	@Override
	public float getQ() throws PsseModelException {return _list.getQ(_ndx);}
	@Override
	public void setP(float mw) throws PsseModelException {_list.setP(_ndx, mw);}
	@Override
	public void setQ(float mvar) throws PsseModelException {_list.setQ(_ndx, mvar);}
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

	@Override
	public float getPpu() throws PsseModelException {return _list.getPpu(_ndx);}
	@Override
	public void setPpu(float p) throws PsseModelException {_list.setPpu(_ndx, p);}
	@Override
	public float getQpu() throws PsseModelException {return _list.getQpu(_ndx);}
	@Override
	public void setQpu(float q) throws PsseModelException {_list.setQpu(_ndx, q);}
	
	/** get the load MW "setpoint" */
	public float getPS() throws PsseModelException {return _list.getPS(_ndx);}
	/** set the load MW setpoint */
	public void setPS(float mw) throws PsseModelException {_list.setPS(_ndx, mw);}
	/** get the load MVAr setpoint */
	public float getQS() throws PsseModelException {return _list.getQS(_ndx);}
	/** set the load MVAr setpoint */
	public void setQS(float mvar) throws PsseModelException {_list.setQS(_ndx, mvar);} 
	
	/** get the cold load MW */
	public float getPcold()  throws PsseModelException {return _list.getPcold(_ndx);}
	/** get the cold load MVAr */
	public float getQcold() throws PsseModelException {return _list.getQcold(_ndx);}
}
