package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.PAMath;

public abstract class GeneratorList<T extends Generator> extends PsseBaseList<T>
{
	public GeneratorList(PsseModel model) {super(model);}
	
	/* convenience methods */

	public abstract Bus getBus(int ndx) throws PsseModelException;
	public abstract Bus getRemoteRegBus(int ndx) throws PsseModelException;
	public abstract boolean getInSvc(int ndx);
	public abstract float getActvPwr(int ndx);
	public abstract float getReacPwr(int ndx);
	public abstract float getMaxReacPwr(int ndx);
	public abstract float getMinReacPwr(int ndx);
	public abstract float getMachR(int ndx);
	public abstract float getTxfR(int ndx);
	public abstract float getMachX(int ndx);
	public abstract float getTxfX(int ndx);
	public abstract float getMaxActvPwr(int ndx);
	public abstract float getMinActvPwr(int ndx);

	
	/* convenience defaults */
	
	public Bus getDeftBus(int ndx) throws PsseModelException {return _model.getBus(getI(ndx));}
	public Bus getDeftRemoteRegBus(int ndx) throws PsseModelException {return _model.getBus(getIREG(ndx));}
	public boolean getDeftInSvc(int ndx) {return getSTAT(ndx) == 1;}
	public float getDeftActvPwr(int ndx) {return PAMath.mw2pu(getPG(ndx));}
	public float getDeftReacPwr(int ndx) {return PAMath.mvar2pu(getQG(ndx));}
	public float getDeftMaxReacPwr(int ndx) {return PAMath.mvar2pu(getQT(ndx));}
	public float getDeftMinReacPwr(int ndx) {return PAMath.mvar2pu(getQB(ndx));}
	public float getDeftMachR(int ndx) {return PAMath.rebaseZ100(getZR(ndx), getMBASE(ndx));}
	public float getDeftMachX(int ndx) {return PAMath.rebaseZ100(getZX(ndx), getMBASE(ndx));}
	public float getDeftTxfR(int ndx)  {return PAMath.rebaseZ100(getRT(ndx), getMBASE(ndx));}
	public float getDeftTxfX(int ndx)  {return PAMath.rebaseZ100(getXT(ndx), getMBASE(ndx));}
	public float getDeftMaxActvPwr(int ndx) {return PAMath.mw2pu(getPT(ndx));}
	public float getDeftMinActvPwr(int ndx) {return PAMath.mw2pu(getPB(ndx));}

	/* raw methods */

	public abstract String getI(int ndx);
	public abstract String getID(int ndx);
	public abstract float getPG(int ndx);
	public abstract float getQG(int ndx);
	public abstract float getQT(int ndx);
	public abstract float getQB(int ndx);
	public abstract float getVS(int ndx);
	public abstract String getIREG(int ndx);
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
	
	
	/* default methods */

	public String getDeftID(int ndx) {return "1";}
	public float getDeftPG(int ndx) {return 0F;}
	public float getDeftQG(int ndx) {return 0F;}
	public float getDeftQT(int ndx) {return 9999F;}
	public float getDeftQB(int ndx)  {return -9999F;}
	public float getDeftVS(int ndx) {return 1F;}
	public String getDeftIREG(int ndx) {return "0";}
	public float getDeftMBASE(int ndx) {return _model.getSBASE();}
	public float getDeftZR(int ndx) {return 0F;}
	public float getDeftZX(int ndx) {return 1F;}
	public float getDeftRT(int ndx) {return 0F;}
	public float getDeftXT(int ndx) {return 0F;}
	public float getDeftGTAP(int ndx) {return 1F;}
	public int getDeftSTAT(int ndx) {return 1;}
	public float getDeftRMPCT(int ndx) {return 100F;}
	public float getDeftPT(int ndx) {return 9999F;}
	public float getDeftPB(int ndx) {return -9999F;}

}
