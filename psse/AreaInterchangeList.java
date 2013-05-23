package com.powerdata.openpa.psse;

public abstract class AreaInterchangeList<T extends AreaInterchange> extends PsseBaseList<T>
{
	public AreaInterchangeList(PsseModel model) {super(model);}

	/* Convenience methods */
	public abstract Bus getSlackBus(int ndx) throws PsseModelException;
	
	/* Convenience defaults */
	public Bus getDeftSlackBus(int ndx) throws PsseModelException
	{
		return _model.getBuses().get(getISW(ndx));
	}
	
	/* Raw values */
	public abstract int getI(int ndx) throws PsseModelException;
	public abstract String getISW(int ndx) throws PsseModelException;
	public abstract String getARNAME(int ndx) throws PsseModelException;
	public abstract float getPDES(int ndx) throws PsseModelException;
	public abstract float getPTOL(int ndx) throws PsseModelException;

	/* Default values */
	public String getDeftISW(int ndx) throws PsseModelException {return "0";}
	public String getDeftARNAME(int ndx) throws PsseModelException {return "";}
	public float getDeftPDES(int ndx) throws PsseModelException {return 0F;}
	public float getDeftPTOL(int ndx) throws PsseModelException {return 10F;}

}
