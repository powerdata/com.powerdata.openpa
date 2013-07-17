package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.BaseObject;

public class SwitchedShunt extends BaseObject
{
	protected SwitchedShuntList _list;
	
	public SwitchedShunt(int ndx, SwitchedShuntList list)
	{
		super(ndx);
		_list = list;
	}

	@Override
	public String getDebugName() throws PsseModelException {return "";}

	@Override
	public String getObjectID() throws PsseModelException {return _list.getObjectID(_ndx);}
	
	/* Convenience methods */
	/** Load bus (I) */ 
	public Bus getBus() throws PsseModelException {return _list.getBus(_ndx);}
	/** control mode */
	public SwShuntCtrlMode getCtrlMode() throws PsseModelException {return _list.getCtrlMode(_ndx);}
	/** get voltage limits for controlled bus */
	public Limits getVoltageLimits() throws PsseModelException {return _list.getVoltageLimits(_ndx);}
	/** get reactive power limits for controlled bus */
	public Limits getReacPwrLimits() throws PsseModelException {return _list.getVoltageLimits(_ndx);}
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
	/** block information */
	public SwShuntBlkList getBlocks() throws PsseModelException {return _list.getBlocks(_ndx);}
	
}
