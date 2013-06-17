package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.BaseObject;

public class SwitchedShuntIn extends BaseObject
{
	protected SwitchedShuntiNList _list;
	
	public SwitchedShuntIn(int ndx, SwitchedShuntiNList list)
	{
		super(ndx);
		_list = list;
	}

	@Override
	public String getDebugName() throws PsseModelException {return "";}

	@Override
	public String getObjectID() {return _list.getObjectID(_ndx);}
	
	/* Convenience methods */
	/** Load bus (I) */ 
	public BusIn getBus() throws PsseModelException {return _list.getBus(_ndx);}
	/** control mode */
	public SwShuntCtrlMode getCtrlMode() throws PsseModelException {return _list.getCtrlMode(_ndx);}
	/** controlled voltage upper limit (VSWLO) */
	public float getVoltHighLim() throws PsseModelException {return _list.getVoltHighLim(_ndx);}
	/** controlled voltage lower limit (VSWHI) */
	public float getVoltLowLim() throws PsseModelException {return _list.getVoltLowLim(_ndx);}
	/** controlled reactive power upper limit (VSWLO) */
	public float getQHighLim() throws PsseModelException {return _list.getQHighLim(_ndx);}
	/** controlled reactive power lower limit (VSWHI) */
	public float getQLowLim() throws PsseModelException {return _list.getQLowLim(_ndx);}
	/** get regulated bus */
	public BusIn getRegBus() throws PsseModelException {return _list.getRegBus(_ndx);}
	/** get initial susceptance per-unit */
	public float getInitB() throws PsseModelException {return _list.getInitB(_ndx);}
	
	
	/* Raw PSS/e methods */

	/** bus number or name */
	public String getI() throws PsseModelException {return _list.getI(_ndx);}
	/** Control mode */
	public int getMODSW() throws PsseModelException {return _list.getMODSW(_ndx);}
	/** controlled upper limit either voltage or reactive power p.u. */
	public int getVSWHI() throws PsseModelException {return _list.getVSWHI(_ndx);}
	/** controlled lower limit either voltage or reactive power p.u. */
	public int getVSWLO() throws PsseModelException {return _list.getVSWLO(_ndx);}
	/** controlled bus */
	public String getSWREM() throws PsseModelException {return _list.getSWREM(_ndx);}
	/** percent of total MVAr required to hold bus voltage contributed by this shunt */
	public float getRMPCT()  throws PsseModelException {return _list.getRMPCT(_ndx);}
	/** Name of VSC dc line if bus is specified for control (MODSW = 4) */
	public String getRMIDNT()  throws PsseModelException {return _list.getRMIDNT(_ndx);}
	/** switched shunt admittance */
	public float getBINIT() throws PsseModelException {return _list.getBINIT(_ndx);}
	/** block information */
	public SwShuntBlkList getBlocks() throws PsseModelException {return _list.getBlocks(_ndx);}
	
}
