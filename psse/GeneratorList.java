package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.Complex;
import com.powerdata.openpa.tools.PAMath;

public abstract class GeneratorList<T extends Generator> extends PsseBaseList<T>
{
	public GeneratorList(PsseModel model) {super(model);}
	
	/* convenience methods */

	public abstract Bus getBus(int ndx) throws PsseModelException;
	public abstract Bus getRemoteRegBus(int ndx) throws PsseModelException;
	public abstract boolean getInSvc(int ndx) throws PsseModelException;
	public abstract float getActvPwr(int ndx) throws PsseModelException;
	public abstract float getReacPwr(int ndx) throws PsseModelException;
	public abstract float getMaxReacPwr(int ndx) throws PsseModelException;
	public abstract float getMinReacPwr(int ndx) throws PsseModelException;
	public abstract float getMachR(int ndx) throws PsseModelException;
	public abstract float getTxfR(int ndx) throws PsseModelException;
	public abstract float getMachX(int ndx) throws PsseModelException;
	public abstract float getTxfX(int ndx) throws PsseModelException;
	public abstract float getMaxActvPwr(int ndx) throws PsseModelException;
	public abstract float getMinActvPwr(int ndx) throws PsseModelException;
	public abstract Complex getPwr(int ndx) throws PsseModelException;
	public abstract Complex getMachZ(int ndx) throws PsseModelException;
	public abstract Complex getTxZ(int ndx) throws PsseModelException;

	
	/* convenience defaults */
	
	public Bus getDeftBus(int ndx) throws PsseModelException {return _model.getBus(getObjectID(ndx));}
	public Bus getDeftRemoteRegBus(int ndx) throws PsseModelException {return _model.getBus(getIREG(ndx));}
	public boolean getDeftInSvc(int ndx) throws PsseModelException {return getSTAT(ndx) == 1;}
	public float getDeftActvPwr(int ndx) throws PsseModelException {return PAMath.mw2pu(getPG(ndx));}
	public float getDeftReacPwr(int ndx) throws PsseModelException {return PAMath.mvar2pu(getQG(ndx));}
	public float getDeftMaxReacPwr(int ndx) throws PsseModelException {return PAMath.mvar2pu(getQT(ndx));}
	public float getDeftMinReacPwr(int ndx) throws PsseModelException {return PAMath.mvar2pu(getQB(ndx));}
	public float getDeftMachR(int ndx) throws PsseModelException {return PAMath.rebaseZ100(getZR(ndx), getMBASE(ndx));}
	public float getDeftMachX(int ndx) throws PsseModelException {return PAMath.rebaseZ100(getZX(ndx), getMBASE(ndx));}
	public float getDeftTxfR(int ndx)  throws PsseModelException {return PAMath.rebaseZ100(getRT(ndx), getMBASE(ndx));}
	public float getDeftTxfX(int ndx)  throws PsseModelException {return PAMath.rebaseZ100(getXT(ndx), getMBASE(ndx));}
	public float getDeftMaxActvPwr(int ndx) throws PsseModelException {return PAMath.mw2pu(getPT(ndx));}
	public float getDeftMinActvPwr(int ndx) throws PsseModelException {return PAMath.mw2pu(getPB(ndx));}
	public Complex getDeftPwr(int ndx) throws PsseModelException {return new Complex(getActvPwr(ndx), getReacPwr(ndx));}
	public Complex getDeftMachZ(int ndx) throws PsseModelException {return new Complex(getMachR(ndx), getMachX(ndx));} 
	public Complex getDeftTxZ(int ndx) throws PsseModelException {return new Complex(getTxfR(ndx), getTxfX(ndx));}

	/* raw methods */

	public abstract String getI(int ndx) throws PsseModelException;
	public abstract String getID(int ndx) throws PsseModelException;
	public abstract float getPG(int ndx) throws PsseModelException;
	public abstract float getQG(int ndx) throws PsseModelException;
	public abstract float getQT(int ndx) throws PsseModelException;
	public abstract float getQB(int ndx) throws PsseModelException;
	public abstract float getVS(int ndx) throws PsseModelException;
	public abstract String getIREG(int ndx) throws PsseModelException;
	public abstract float getMBASE(int ndx) throws PsseModelException;
	public abstract float getZR(int ndx) throws PsseModelException;
	public abstract float getZX(int ndx) throws PsseModelException;
	public abstract float getRT(int ndx) throws PsseModelException;
	public abstract float getXT(int ndx) throws PsseModelException;
	public abstract float getGTAP(int ndx) throws PsseModelException;
	public abstract int getSTAT(int ndx) throws PsseModelException;
	public abstract float getRMPCT(int ndx) throws PsseModelException;
	public abstract float getPT(int ndx) throws PsseModelException;
	public abstract float getPB(int ndx) throws PsseModelException;

	public abstract OwnershipList<?> getOwnership(int ndx) throws PsseModelException;
	
	
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
