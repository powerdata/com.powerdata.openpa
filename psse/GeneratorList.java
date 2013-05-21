package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.BaseList;

public abstract class GeneratorList<T extends Generator> extends BaseList<T>
{
	/* convenience methods */
	
	public Bus getBus(int ndx) {return null;}
	public float getActvPwr(int ndx) {return 0;}
	public float getReacPwr(int ndx) {return 0;}
	public float getMaxReacPwr(int ndx) {return 0;}
	public float getMinReacPwr(int ndx) {return 0;}
	public Bus getRemoteRegBus(int ndx) {return null;}
	public float getMachR(int ndx)  {return 0;}
	public float getMachX(int ndx) {return 0;}
	public float getStepupR(int ndx) {return 0;}
	public float getStepupX(int ndx) {return 0;}
	public boolean inService(int ndx) {return getSTAT(ndx) == 1;}
	public float getMaxActvPwr(int ndx) {return 0;}
	public float getMinActvPwr(int ndx) {return 0;}

	/* raw methods */

	public abstract String getI(int ndx);
	public abstract String getID(int ndx);
	public abstract float getPG(int ndx);
	public abstract float getQG(int ndx);
	public abstract float getQT(int ndx);
	public abstract float getQB(int ndx);
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
