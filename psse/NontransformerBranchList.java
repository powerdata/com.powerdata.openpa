package com.powerdata.openpa.psse;

public abstract class NontransformerBranchList<T extends NontransformerBranch> 
	extends PsseBaseList<T>
{
	public enum MeteredEnd
	{
		From, To;
	}

	public NontransformerBranchList(PsseModel model) {super(model);}

	/* Convenience Methods */
	public Bus getFromBus(int ndx) throws PsseModelException {return _model.getBus(getI(ndx));}
	public Bus getToBus(int ndx)  throws PsseModelException
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
	
	/* Defaults */
	public String getDeftCKT(int ndx) {return "1";}
	public float getDeftB(int ndx) {return 0F;}
	public float getDeftRATEA(int ndx) {return 0F;}
	public float getDeftRATEB(int ndx) {return 0F;}
	public float getDeftRATEC(int ndx) {return 0F;}
	public float getDeftGI(int ndx) {return 0F;}
	public float getDeftBI(int ndx) {return 0F;}
	public float getDeftGJ(int ndx) {return 0F;}
	public float getDeftBJ(int ndx) {return 0F;}
	public int getDeftST(int ndx) {return 1;}
	public float getDeftLEN(int ndx) {return 0F;}
}	
