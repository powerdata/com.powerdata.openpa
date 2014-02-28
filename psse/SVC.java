package com.powerdata.openpa.psse;

public class SVC extends PsseBaseObject implements OneTermDev
{
	public enum ControlMode
	{
		FixedReactivePower, Voltage;
	}
	public enum State
	{
		Off, CapacitorLimit, ReactorLimit, Normal, FixedReactivePower; 
	}
	
	protected SvcList	_list;

	public SVC(int ndx, SvcList list)
	{
		super(list,ndx);
		_list = list;
	}

	@Override
	public Bus getBus() throws PsseModelException {return _list.getBus(_ndx);}
	public Bus getRegBus() throws PsseModelException {return _list.getRegBus(_ndx);}
	
	public String getI() throws PsseModelException {return _list.getI(_ndx);}
	public String getSWREM() throws PsseModelException {return _list.getSWREM(_ndx);}
	public float getRMPCT() throws PsseModelException {return _list.getRMPCT(_ndx);}
	public float getBINIT() throws PsseModelException {return _list.getBINIT(_ndx);}
	/** voltage or reactive power upper limit p.u */
	public float getVSWHI() throws PsseModelException {return _list.getVSWHI(_ndx);}
	/** voltage or reactive power lower limit p.u */
	public float getVSWLO() throws PsseModelException {return _list.getVSWLO(_ndx);}
	/** Control Mode */
	public int getMODSW() throws PsseModelException {return _list.getMODSW(_ndx);}
	/** Enumerated control mode */
	public ControlMode getControlMode() throws PsseModelException {return _list.getControlMode(_ndx);}
	/** enumerated control mode */
	public void setControlMode(ControlMode cmode) throws PsseModelException {_list.setControlMode(_ndx, cmode);}
	/** get state */
	public State getState() throws PsseModelException {return _list.getState(_ndx);}
	/** get voltage set point p.u on bus base voltage */
	public float getVS() throws PsseModelException {return _list.getVS(_ndx);}
	/** set voltage setpoint p.u. on bus base voltage */
	public void setVS(float vs) throws PsseModelException {_list.setVS(_ndx, vs);}
	/** get reactive power setpoint in MVAr */
	public float getQS() throws PsseModelException {return _list.getQS(_ndx);}
	/** set reactive power setpoint in MVAr */
	public void setQS(float qs) throws PsseModelException {_list.setQS(_ndx);} 
	/** get reactive power setpoint p.u. on 100MVA base */
	
	/** max reactive limit */
	public Limits getReactivePowerLimits() throws PsseModelException {return _list.getReactivePowerLimits(_ndx);}
	
	@Override
	public boolean isInSvc() throws PsseModelException {return _list.isInSvc(_ndx);}
	@Override
	public void setInSvc(boolean state) throws PsseModelException {_list.setInSvc(_ndx, state);}
	@Override
	public float getP() throws PsseModelException {return _list.getP(_ndx);}
	@Override
	public float getQ() throws PsseModelException {return _list.getQ(_ndx);}
	@Override
	public void setP(float mw) throws PsseModelException { _list.setP(_ndx, mw);}
	@Override
	public void setQ(float mvar) throws PsseModelException { _list.setQ(_ndx, mvar);}
	@Override
	public float getPpu() throws PsseModelException {return _list.getPpu(_ndx);}
	@Override
	public void setPpu(float p) throws PsseModelException {_list.setPpu(_ndx, p);}
	@Override
	public float getQpu() throws PsseModelException {return _list.getQpu(_ndx);}
	@Override
	public void setQpu(float q) throws PsseModelException {_list.setQpu(_ndx, q);}
	
}
