package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.PAMath;

public abstract class SwitchedShuntList extends PsseBaseList<SwitchedShunt>
{
	public static final SwitchedShuntList Empty = new SwitchedShuntList()
	{
		@Override
		public String getI(int ndx) throws PsseModelException {return null;}
		@Override
		public String getObjectID(int ndx) throws PsseModelException {return null;}
		@Override
		public int size() {return 0;}
	};
	
	protected SwitchedShuntList() {super();}
	public SwitchedShuntList(PsseModel model) {super(model);}

	/* Standard object retrieval */

	/** Get a SwitchedShunt by it's index. */
	@Override
	public SwitchedShunt get(int ndx) { return new SwitchedShunt(ndx,this); }
	/** Get a SwitchedShunt by it's ID. */
	@Override
	public SwitchedShunt get(String id) { return super.get(id); }

	/* convenience methods */
	
	/** Load bus */ 
	public Bus getBus(int ndx) throws PsseModelException {return _model.getBus(getI(ndx));}
	/** control mode */
	public SwShuntCtrlMode getCtrlMode(int ndx) throws PsseModelException 
	{
		return SwShuntCtrlMode.fromCode(getMODSW(ndx));
	}

	/** get controlled bus */
	public Bus getCtrlBus(int ndx) throws PsseModelException
	{
		return _model.getBus(getSWREM(ndx));
	}

	/** get case shunt susceptance */
	public float getCaseB(int ndx) throws PsseModelException {return PAMath.mvar2pu(getBINIT(ndx));}
	
	/* raw methods */

	/** bus number or name */
	public abstract String getI(int ndx)throws PsseModelException;
	/** Control mode */
	public int getMODSW(int ndx) throws PsseModelException {return 1;}
	/** controlled upper limit either voltage or reactive power p.u. */
	public float getVSWHI(int ndx) throws PsseModelException {return 1f;}
	/** controlled lower limit either voltage or reactive power p.u. */
	public float getVSWLO(int ndx) throws PsseModelException {return 1f;}
	/** controlled bus */
	public String getSWREM(int ndx) throws PsseModelException {return getI(ndx);}
	/** percent of total MVAr required to hold bus voltage contributed by this shunt */
	public float getRMPCT(int ndx) throws PsseModelException {return 100f;}
	/** Name of VSC dc line if bus is specified for control (MODSW = 4) */
	public String getRMIDNT(int ndx) throws PsseModelException {return "";}
	/** switched shunt admittance */
	public float getBINIT(int ndx) throws PsseModelException {return 0f;}
	
	public ShuntList getCapacitors(int ndx) throws PsseModelException {return ShuntList.Empty;}
	public ShuntList getReactors(int ndx) throws PsseModelException {return ShuntList.Empty;}
	public ShuntList getShunts(int ndx) throws PsseModelException {return ShuntList.Empty;}
	
	/**
	 * TODO:  add limits for 
	 */
}
