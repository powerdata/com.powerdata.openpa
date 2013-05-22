package com.powerdata.openpa.psse;

public abstract class AreaInterchangeList<T extends AreaInterchange> extends PsseBaseList<T>
{
	public AreaInterchangeList(PsseModel model) {super(model);}

	/* Convenience methods */
	
	public Bus getSlackBus(int ndx) throws PsseModelException
	{
		return _model.getBuses().get(getISW(ndx));
	}
	public float getIntExport(int ndx) {return 0;}
	public float getIntTol(int ndx) {return 0;}

	/* Raw values */
	public abstract int getI(int ndx);
	public String getISW(int ndx) {return "0";}
	public String getARNAME(int ndx) {return "";}
	public float getPDES(int ndx) {return 0F;}
	public float getPTOL(int ndx) {return 10F;}
}
