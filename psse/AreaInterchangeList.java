package com.powerdata.openpa.psse;

public abstract class AreaInterchangeList<T extends AreaInterchange> extends PsseBaseList<T>
{
	public AreaInterchangeList(PsseModel model) {super(model);}

	/* Convenience methods */
	
	public Bus getSlackBus(int ndx) throws PsseModelException
	{
		return _model.getBuses().get(getISW(ndx));
	}

	/* Raw values */
	public abstract int getI(int ndx);
	public abstract String getISW(int ndx);
	public abstract String getARNAME(int ndx);
	public abstract float getPDES(int ndx);
	public abstract float getPTOL(int ndx);

	/* Default values */
	protected String getDeftISW(int ndx) {return "0";}
	protected String getDeftARNAME(int ndx) {return "";}
	protected float getDeftPDES(int ndx) {return 0F;}
	protected float getDeftPTOL(int ndx) {return 10F;}

}
