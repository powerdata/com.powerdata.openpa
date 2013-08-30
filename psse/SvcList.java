package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.Complex;

public abstract class SvcList extends PsseBaseList<SVC>
{
	public static final SvcList Empty = new SvcList()
	{
		@Override
		public String getI(int ndx) throws PsseModelException {return null;}
		@Override
		public String getObjectID(int ndx) throws PsseModelException {return null;}
		@Override
		public int size() {return 0;}
	};
	
	Complex _tmps;
	
	protected SvcList(){super();}

	public SvcList(PsseModel model) throws PsseModelException
	{
		super(model);
	}

	/** Get a SVC by it's index. */
	@Override
	public SVC get(int ndx) { return new SVC(ndx,this); }
	/** Get an SwitchIn by it's ID. */
	@Override
	public SVC get(String id) { return super.get(id); }
	
	public Bus getBus(int ndx) throws PsseModelException { return _model.getBus(getI(ndx));}
	public Bus getRegBus(int ndx) throws PsseModelException { return getBus(ndx);}
	
	public abstract String getI(int ndx) throws PsseModelException;
	public String getSWREM(int ndx) throws PsseModelException {return getI(ndx);}
	public float getRMPCT(int ndx) throws PsseModelException {return 100f;}
	public float getBINIT(int ndx) throws PsseModelException {return 0f;}

	public boolean isInSvc(int ndx) throws PsseModelException {return getBINIT(ndx) != 0f;}

	public float getRTMW(int ndx) throws PsseModelException {return 0f;}
	public float getRTMVAr(int ndx) throws PsseModelException {return 0f;} 
	public void setRTMW(int ndx, float mw) throws PsseModelException {}
	public void setRTMVAr(int ndx, float mvar) throws PsseModelException {} 
	public float getRTP(int ndx) throws PsseModelException {return 0f;}
	public void setRTP(int ndx, float p) throws PsseModelException {}
	public float getRTQ(int ndx) throws PsseModelException {return 0f;}
	public void setRTQ(int ndx, float q) throws PsseModelException {}
}
