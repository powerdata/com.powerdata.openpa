package com.powerdata.openpa.psse;

public abstract class SwitchedShuntList<T extends Bus> extends PsseBaseList<T>
{
	public SwitchedShuntList(PsseModel model) {super(model);}

	/* convenience methods */
	
	public abstract Bus getBus(int ndx)throws PsseModelException;

	/* convenience defaults */
	

	
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

	public SwShuntCtrlMode getCtrlMode(int _ndx)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* defaults */

//	public String getI(int ndx)
//	public int getMODSW(int ndx)
//	public int getVSWHI(int ndx)
//	public int getVSWLO(int ndx)
//	public String getSWREM(int ndx)
//	public float getRMPCT(int ndx)
//	public String getRMIDNT(int ndx)
//	public float getBINIT(int ndx)
//	public SwitchedShuntBlockList<?> getBlocks(int ndx)


}
