package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.Complex;

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
	public Complex getY(int ndx) throws PsseModelException
	{
		return isSwitchedOn(ndx) ? new Complex(getG(ndx), getB(ndx)).div(100f) : Complex.Zero;
	}
	
	public float getBpu(int ndx) throws PsseModelException
	{
		return isSwitchedOn(ndx) ? getB(ndx)/100f : 0;
	}
	public float getGpu(int ndx) throws PsseModelException
	{
		return isSwitchedOn(ndx) ? getG(ndx)/100f : 0;
	}

	public boolean isSwitchedOn(int ndx) throws PsseModelException {return false;}

	/* raw methods */
	
	/** get connected bus */
	public abstract String getI(int ndx) throws PsseModelException;
	/** shunt nominal B in MVAr at unity bus voltage */
	public float getB(int ndx) throws PsseModelException {return 0f;}
	/** get G, MVAr at unity voltage */
	public float getG(int ndx) throws PsseModelException {return 0f;}

	public boolean isInSvc(int ndx) throws PsseModelException {return isSwitchedOn(ndx);}

	public float getRTMW(int ndx) throws PsseModelException {return 0f;}
	public float getRTMVAr(int ndx) throws PsseModelException {return 0f;}
	public void setRTMW(int ndx, float mw) throws PsseModelException {}
	public void setRTMVAr(int ndx, float mvar) throws PsseModelException {}
	public float getRTP(int ndx) throws PsseModelException {return 0f;}
	public void setRTP(int ndx, float p) throws PsseModelException {}
	public float getRTQ(int ndx) throws PsseModelException {return 0f;}
	public void setRTQ(int ndx, float q) throws PsseModelException {}
}
