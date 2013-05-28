package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.PAMath;

public abstract class SwitchedShuntList<T extends SwitchedShunt> extends PsseBaseList<T>
{
	public SwitchedShuntList(PsseModel model) {super(model);}

	protected float vsVoltDeft(int ndx, boolean ishigh)
			throws PsseModelException
	{
		int modsw = getMODSW(ndx);
		if (modsw != 1 || modsw != 2)
		{
			throw new PsseModelException("VSWHI and VSWLO are not coded for voltage limits,"
				+"MODSW must either be 1 or 2");
		}

		return ishigh ? getVSWHI(ndx) : getVSWLO(ndx);
	}

	protected float vsQDeft(int ndx, boolean ishigh)
			throws PsseModelException
	{
		int modsw = getMODSW(ndx);
		if (modsw != 3 || modsw != 4 || modsw != 5)
		{
			throw new PsseModelException("VSWHI and VSWLO are not coded for reactive limits,"
				+"MODSW must either be 3, 4, or 5");
		}
		return ishigh ? getVSWHI(ndx) : getVSWLO(ndx);
	}

	
	/* Standard object retrieval */

	/** Get a SwitchedShunt by it's index. */
	@Override
	@SuppressWarnings("unchecked")
	public T get(int ndx) { return (T) new SwitchedShunt(ndx,this); }
	/** Get a SwitchedShunt by it's ID. */
	@Override
	public T get(String id) { return super.get(id); }

	/* convenience methods */
	
	public abstract Bus getBus(int ndx) throws PsseModelException;
	public abstract SwShuntCtrlMode getCtrlMode(int ndx) throws PsseModelException;
	public abstract float getVoltHighLim(int ndx) throws PsseModelException;
	public abstract float getVoltLowLim(int ndx) throws PsseModelException;
	public abstract float getQHighLim(int ndx) throws PsseModelException;
	public abstract float getQLowLim(int ndx) throws PsseModelException;
	public abstract Bus getRegBus(int ndx) throws PsseModelException;
	public abstract float getInitB(int ndx) throws PsseModelException;

	/* convenience defaults */
	public SwShuntCtrlMode getDeftCtrlMode(int ndx) throws PsseModelException {return SwShuntCtrlMode.fromCode(getMODSW(ndx));}
	public float getDeftVoltHighLim(int ndx) throws PsseModelException {return vsVoltDeft(ndx, true);}
	public float getDeftVoltLowLim(int ndx) throws PsseModelException {return vsVoltDeft(ndx, false);}
	public float getDeftQHighLim(int ndx) throws PsseModelException {return vsQDeft(ndx, true);}
	public float getDeftQLowLim(int ndx) throws PsseModelException {return vsQDeft(ndx, false);}
	public Bus getDeftRegBus(int ndx) throws PsseModelException
	{
		String swrem = getSWREM(ndx);
		return (swrem.equals("0")) ? getBus(ndx) :
			_model.getBus(swrem);
	}
	public float getDeftInitB(int ndx) throws PsseModelException {return PAMath.mvar2pu(getBINIT(ndx));}
	
	/* raw methods */

	public abstract String getI(int ndx)throws PsseModelException;
	public abstract int getMODSW(int ndx)throws PsseModelException;
	public abstract int getVSWHI(int ndx)throws PsseModelException;
	public abstract int getVSWLO(int ndx)throws PsseModelException;
	public abstract String getSWREM(int ndx)throws PsseModelException;
	public abstract float getRMPCT(int ndx)throws PsseModelException;
	public abstract String getRMIDNT(int ndx)throws PsseModelException;
	public abstract float getBINIT(int ndx)throws PsseModelException;
	public abstract SwitchedShuntBlockList<?> getBlocks(int ndx)throws PsseModelException;


	/* defaults */

	public int getDeftMODSW(int ndx) {return 1;}
	public float getDeftVSWHI(int ndx) {return 1F;}
	public float getDeftVSWLO(int ndx) {return 1F;}
	public String getDeftSWREM(int ndx) {return "0";}
	public float getDeftRMPCT(int ndx) {return 100F;}
	public String getDeftRMIDNT(int ndx) {return "";}
	public float getDeftBINIT(int ndx) {return 0F;}

}
