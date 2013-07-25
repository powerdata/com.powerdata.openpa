package com.powerdata.openpa.psse;

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
	
	protected BusList _buses;
	
	protected SvcList(){super();}

	public SvcList(PsseModel model) throws PsseModelException
	{
		super(model);
		_buses = model.getBuses();
	}

	/** Get a SVC by it's index. */
	@Override
	public SVC get(int ndx) { return new SVC(ndx,this); }
	/** Get an SwitchIn by it's ID. */
	@Override
	public SVC get(String id) { return super.get(id); }
	
	public Bus getBus(int ndx) throws PsseModelException { return _buses.get(getI(ndx));}
	public Bus getRegBus(int ndx) throws PsseModelException { return getBus(ndx);}
	
	public float getVoltageSetpoint(int ndx) throws PsseModelException {return 1f;}

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
}
