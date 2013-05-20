package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.BaseList;

public abstract class NontransformerBranchList<T extends NontransformerBranch> 
	extends BaseList<T>
{
	public abstract Bus getFromBus(int ndx);
	public abstract Bus getToBus(int ndx);
	public abstract MeteredEnd getMeteredEnd(int ndx);
	public abstract boolean inService(int ndx);
	
	public abstract String getI(int ndx);
	public abstract String getJ(int ndx);
	public abstract String getCKT(int ndx);
	public abstract float getR(int ndx);
	public abstract float getX(int ndx);
	public abstract float getB(int ndx);
	public abstract float getRATEA(int ndx);
	public abstract float getRATEB(int ndx);
	public abstract float getRATEC(int ndx);
	public abstract float getGI(int ndx);
	public abstract float getBI(int ndx);
	public abstract float getGJ(int ndx);
	public abstract float getBJ(int ndx);
	public abstract int getST(int ndx);
	public abstract float getLEN(int ndx);

	public abstract OwnershipList<?> getOwnership(int ndx);
}	
