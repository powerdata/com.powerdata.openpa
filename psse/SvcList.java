package com.powerdata.openpa.psse;

import com.powerdata.openpa.busmismatch.PowerCalculator;
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
		@Override
		public Limits getBLimits(int ndx) throws PsseModelException {return null;}
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
	
	public float getVoltageSetpoint(int ndx) throws PsseModelException {return 1f;}
	public Complex getY(int ndx) throws PsseModelException {return new Complex(0, getBINIT(ndx)/100f);}

	public abstract String getI(int ndx) throws PsseModelException;
	public String getSWREM(int ndx) throws PsseModelException {return getI(ndx);}
	public float getRMPCT(int ndx) throws PsseModelException {return 100f;}
	public float getBINIT(int ndx) throws PsseModelException {return 0f;}
	public Limits getBLimitsPU(int ndx) throws PsseModelException
	{
		Limits b = getBLimits(ndx);
		return new Limits(b.getMin()/100f, b.getMax()/100f);
	}
	public abstract Limits getBLimits(int ndx) throws PsseModelException;

	public void setRTS(int ndx, Complex s) throws PsseModelException {_tmps = s;}
	public Complex getRTS(int ndx) throws PsseModelException {PowerCalculator.calcSVC(get(ndx)); return _tmps;}
	public Complex getRTY(int ndx) throws PsseModelException {return getY(ndx);}
	public void setRTY(int ndx, Complex y) throws PsseModelException {/* do nothing */}

	public boolean isInSvc(int ndx) throws PsseModelException {return getBINIT(ndx) != 0f;}
}
