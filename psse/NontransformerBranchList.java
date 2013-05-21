package com.powerdata.openpa.psse;

import java.io.IOException;

public abstract class NontransformerBranchList<T extends NontransformerBranch> 
	extends PsseBaseList<T>
{
	public enum MeteredEnd
	{
		From, To;
	}

	public NontransformerBranchList(PsseModel model) {super(model);}

	/* Convenience Methods */
	public Bus getFromBus(int ndx) throws IOException {return _model.getBus(getI(ndx));}
	public Bus getToBus(int ndx)  throws IOException
	{
		String j = getJ(ndx);
		return _model.getBus((!j.isEmpty()&&j.charAt(0)=='-')?
			j.substring(1):j); 
	}
	public MeteredEnd getMeteredEnd(int ndx)
	{
		String j = getJ(ndx);
		return (!j.isEmpty() && j.charAt(0) == '-') ? 
			MeteredEnd.To : MeteredEnd.From;
	}
	public boolean inService(int ndx) {return getST(ndx) == 1;}
	
	/* raw PSS/e methods */
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
