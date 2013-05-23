package com.powerdata.openpa.psse;

public abstract class GeneratorList<T extends Generator> extends PsseBaseList<T>
{
	public GeneratorList(PsseModel model) {super(model);}
	
	/* convenience methods */
	
	public Bus getBus(int ndx) throws PsseModelException {return _model.getBus(getI(ndx));}
	public Bus getRemoteRegBus(int ndx) throws PsseModelException {return _model.getBus(getIREG(ndx));}
	public boolean inService(int ndx) {return getSTAT(ndx) == 1;}

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

	protected String getDeftID(int ndx) {return "1";}
	protected float getDeftPG(int ndx) {return 0F;}
	protected float getDeftQG(int ndx) {return 0F;}
	protected float getDeftQT(int ndx) {return 9999F;}
	protected float getDeftQB(int ndx)  {return -9999F;}
	protected float getDeftVS(int ndx) {return 1F;}
	protected String getDeftIREG(int ndx) {return "0";}
	protected float getDeftMBASE(int ndx) {return _model.getSBASE();}
	protected float getDeftZR(int ndx) {return 0F;}
	protected float getDeftZX(int ndx) {return 1F;}
	protected float getDeftRT(int ndx) {return 0F;}
	protected float getDeftXT(int ndx) {return 0F;}
	protected float getDeftGTAP(int ndx) {return 1F;}
	protected int getDeftSTAT(int ndx) {return 1;}
	protected float getDeftRMPCT(int ndx) {return 100F;}
	protected float getDeftPT(int ndx) {return 9999F;}
	protected float getDeftPB(int ndx) {return -9999F;}

}
