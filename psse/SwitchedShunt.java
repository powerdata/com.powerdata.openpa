package com.powerdata.openpa.psse;

/* TODO:  Not fully implemented.  Shunts are broken out into separate lists */

public class SwitchedShunt extends PsseBaseObject implements OneTermDev
{
	protected SwitchedShuntList _list;
	
	public SwitchedShunt(int ndx, SwitchedShuntList list)
	{
		super(list,ndx);
		_list = list;
	}

	@Override
	public String getDebugName() throws PsseModelException {return "";}

	/* Convenience methods */
	/** Load bus (I) */ 
	public Bus getBus() throws PsseModelException {return _list.getBus(_ndx);}
	/** control mode */
	public SwShuntCtrlMode getCtrlMode() throws PsseModelException {return _list.getCtrlMode(_ndx);}
	/** get voltage limits for controlled bus TODO:  this does not reflect how these objects are modeled in PSS/e*/
	/** get controlled bus */
	public Bus getCtrlBus() throws PsseModelException {return _list.getCtrlBus(_ndx);}
	/** get case shunt susceptance */
	public float getCaseB() throws PsseModelException {return _list.getCaseB(_ndx);}
	
	/* Raw PSS/e methods */

	/** bus number or name */
	public String getI() throws PsseModelException {return _list.getI(_ndx);}
	/** Control mode */
	public int getMODSW() throws PsseModelException {return _list.getMODSW(_ndx);}
	/** controlled upper limit either voltage or reactive power p.u. */
	public float getVSWHI() throws PsseModelException {return _list.getVSWHI(_ndx);}
	/** controlled lower limit either voltage or reactive power p.u. */
	public float getVSWLO() throws PsseModelException {return _list.getVSWLO(_ndx);}
	/** controlled bus */
	public String getSWREM() throws PsseModelException {return _list.getSWREM(_ndx);}
	/** percent of total MVAr required to hold bus voltage contributed by this shunt */
	public float getRMPCT()  throws PsseModelException {return _list.getRMPCT(_ndx);}
	/** Name of VSC dc line if bus is specified for control (MODSW = 4) */
	public String getRMIDNT()  throws PsseModelException {return _list.getRMIDNT(_ndx);}
	/** switched shunt susceptance */
	public float getBINIT() throws PsseModelException {return _list.getBINIT(_ndx);}
	
	ShuntList getShunts() throws PsseModelException {return _list.getShunts(_ndx);}

	@Override
	public float getP() throws PsseModelException {return _list.getP(_ndx);}
	@Override
	public float getQ() throws PsseModelException {return _list.getQ(_ndx);}
	@Override
	public void setP(float mw) throws PsseModelException {_list.setP(_ndx, mw);}
	@Override
	public void setQ(float mvar) throws PsseModelException {_list.setQ(_ndx, mvar);}
	@Override
	public float getPpu() throws PsseModelException {return _list.getPpu(_ndx);}
	@Override
	public void setPpu(float p) throws PsseModelException {_list.setPpu(_ndx, p);}
	@Override
	public float getQpu() throws PsseModelException {return _list.getQpu(_ndx);}
	@Override
	public void setQpu(float q) throws PsseModelException {_list.setQpu(_ndx, q);}
	@Override
	public boolean isInSvc() throws PsseModelException {return _list.isInSvc(_ndx);}
}
