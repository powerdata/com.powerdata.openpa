package com.powerdata.openpa.psse;

public abstract class ShuntList extends PsseBaseList<Shunt>
{
	public static final ShuntList Empty = new ShuntList()
	{
		@Override
		public String getI(int ndx) throws PsseModelException {return null;}
		@Override
		public String getObjectID(int ndx) throws PsseModelException {return null;}
		@Override
		public int size() {return 0;}
	};
	
	protected ShuntList(){super();}

	public ShuntList(PsseModel model) throws PsseModelException
	{
		super(model);
	}

	/** Get a Switch by it's index. */
	@Override
	public Shunt get(int ndx) { return new Shunt(ndx,this); }
	/** Get an SwitchIn by it's ID. */
	@Override
	public Shunt get(String id) { return super.get(id); }
	
	/* convenience methods */
	
	public Bus getBus(int ndx) throws PsseModelException {return _model.getBus(getI(ndx));}
	
	public float getBpu(int ndx) throws PsseModelException
	{
		return isInSvc(ndx) ? getB(ndx)/100f : 0;
	}
	public float getGpu(int ndx) throws PsseModelException
	{
		return isInSvc(ndx) ? getG(ndx)/100f : 0;
	}

	/** get connected bus */
	public abstract String getI(int ndx) throws PsseModelException;
	/** shunt nominal B in MVAr at unity bus voltage */
	public float getB(int ndx) throws PsseModelException {return 0f;}
	/** shunt nominal G, MW at unity voltage */
	public float getG(int ndx) throws PsseModelException {return 0f;}

	public boolean isInSvc(int ndx) throws PsseModelException {return false;}

	public float getP(int ndx) throws PsseModelException {return 0f;}
	public float getQ(int ndx) throws PsseModelException {return 0f;}
	public void setP(int ndx, float mw) throws PsseModelException {}
	public void setQ(int ndx, float mvar) throws PsseModelException {}
	public float getPpu(int ndx) throws PsseModelException {return 0f;}
	public void setPpu(int ndx, float p) throws PsseModelException {setP(ndx, p*100f);}
	public float getQpu(int ndx) throws PsseModelException {return 0f;}
	public void setQpu(int ndx, float q) throws PsseModelException {setQ(ndx, q*100f);}
}
