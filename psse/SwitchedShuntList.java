package com.powerdata.openpa.psse;

import com.powerdata.openpa.psse.SwitchedShunt.SwitchedShuntBlock;
import com.powerdata.openpa.tools.PAMath;

public abstract class SwitchedShuntList extends PsseBaseList<SwitchedShunt>
{
	public static final SwitchedShuntList Empty = new SwitchedShuntList()
	{
		@Override
		public String getI(int ndx) {return null;}
		@Override
		public String getObjectID(int ndx) {return null;}
		@Override
		public int size() {return 0;}
		@Override
		public long getKey(int ndx) {return -1;}
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
	public float getCaseB(int ndx) throws PsseModelException {return PAMath.mva2pu(getBINIT(ndx), _model.getSBASE());}
	
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
	public float getP(int ndx) throws PsseModelException {return 0f;}
	public float getQ(int ndx) throws PsseModelException {return 0f;}
	public void setP(int ndx, float mw) throws PsseModelException {}
	public void setQ(int ndx, float mvar) throws PsseModelException {}
	public float getPpu(int ndx) throws PsseModelException {return PAMath.mva2pu(getP(ndx), _model.getSBASE());}
	public void setPpu(int ndx, float p) throws PsseModelException {setP(ndx, PAMath.pu2mva(p, _model.getSBASE()));}
	public float getQpu(int ndx) throws PsseModelException {return PAMath.mva2pu(getQ(ndx), _model.getSBASE());}
	public void setQpu(int ndx, float q) throws PsseModelException {setQ(ndx, PAMath.mva2pu(q, _model.getSBASE()));}
	public boolean isInSvc(int ndx) throws PsseModelException {return true;}
	public void setInSvc(int ndx, boolean state) throws PsseModelException {}
	public SwitchedShuntBlock[] getBlocks(int ndx) throws PsseModelException
	{
		return new SwitchedShuntBlock[0];
	}
}
