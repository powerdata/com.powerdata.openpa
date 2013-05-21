package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.BaseList;

public abstract class GeneratorList<T extends Generator> extends BaseList<T>
{
	/* convenience methods */
	
	public Bus getBus(int ndx) {return null;}
	public float getActvPwr(int ndx) {return 0F;}
	public float getReacPwr(int ndx) {return 0F;}
	public float getMaxReacPwr(int ndx) {return 0F;}
	public float getMinReacPwr(int ndx) {return 0F;}
	public Bus getRemoteRegBus(int ndx) {return null;}
	public float getMachR(int ndx)  {return 0F;}
	public float getMachX(int ndx) {return 0F;}
	public float getStepupR(int ndx) {return 0F;}
	public float getStepupX(int ndx) {return 0F;}
	public boolean inService(int ndx) {return getSTAT(ndx) == 1;}
	public float getMaxActvPwr(int ndx) {return 0F;}
	public float getMinActvPwr(int ndx) {return 0F;}

	/* raw methods */

	public abstract String getI(int ndx);
	public String getID(int ndx) {return "1";}
	public float getPG(int ndx) {return 0F;}
	public float getQG(int ndx) {return 0F;}
	public float getQT(int ndx) {return 9999F;}
	public float getQB(int ndx)  {return -9999F;}
	public float getVS(int ndx) {return 1F;}
	public String getIREG(int ndx) {return "0";}
	public float getMBASE(int ndx) {/*TODO: implement*/return 0F;}
	public float getZR(int ndx) {return 0F;}
	public float getZX(int ndx) {return 1F;}
	public float getRT(int ndx) {return 0F;}
	public float getXT(int ndx) {return 0F;}
	public float getGTAP(int ndx) {return 1F;}
	public int getSTAT(int ndx) {return 1;}
	public float getRMPCT(int ndx) {return 100F;}
	public float getPT(int ndx) {return 9999F;}
	public float getPB(int ndx) {return -9999F;}

	public abstract OwnershipList<?> getOwnership(int ndx);
}
