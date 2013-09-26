package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.Complex;
import com.powerdata.openpa.tools.PAMath;

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

	public float getP(int ndx) throws PsseModelException {return 0f;}
	public float getQ(int ndx) throws PsseModelException {return 0f;} 
	public void setP(int ndx, float mw) throws PsseModelException {}
	public void setQ(int ndx, float mvar) throws PsseModelException {} 
	public float getPpu(int ndx) throws PsseModelException {return PAMath.mw2pu(getP(ndx));}
	public void setPpu(int ndx, float p) throws PsseModelException {}
	public float getQpu(int ndx) throws PsseModelException {return PAMath.mvar2pu(getQ(ndx));}
	public void setQpu(int ndx, float q) throws PsseModelException {}
}
