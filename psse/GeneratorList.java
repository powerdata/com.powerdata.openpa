package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.BaseList;

public abstract class GeneratorList<T extends Generator> extends BaseList<T>
{
	/* convenience methods */
	
	public abstract Bus getBus(int ndx);
	public abstract String getActvPwr(int ndx);
	public abstract String getReacPwr(int ndx);
	public abstract String getMaxReacPwr(int ndx);
	public abstract String getMinReacPwr(int ndx);
	public abstract Bus getRemoteRegBus(int ndx);
	public abstract float getMachR(int ndx);
	public abstract float getMachX(int ndx);
	public abstract float getStepupR(int ndx);
	public abstract float getStepupX(int ndx);
	public abstract boolean inService(int ndx);
	public abstract float getMaxActvPwr(int ndx);
	public abstract float getMinActvPwr(int ndx);

	/* raw methods */

	public abstract String getI(int ndx);
	public abstract String getID(int ndx);
	public abstract String getPG(int ndx);
	public abstract String getQG(int ndx);
	public abstract String getQT(int ndx);
	public abstract String getQB(int ndx);
	public abstract float getVS(int ndx);
	public abstract float getIREG(int ndx);
	public abstract float getMBASE(int ndx);
	public abstract float getZR(int ndx);
	public abstract float getZX(int ndx);
	public abstract float getRT(int ndx);
	public abstract float getXT(int ndx);
	public abstract float getGTAP(int ndx);
	public abstract int getSTAT(int ndx);
	public abstract float getRMPCT(int ndx);
	public abstract float getPT(int ndx);
	public abstract float getPB(int ndx);

	public abstract OwnershipList<?> getOwnership(int ndx);
}
