package com.powerdata.openpa.psse;

import com.powerdata.openpa.busmismatch.PowerCalculator;
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
	
	protected BusList _buses;
	Complex _tmps;
	
	protected ShuntList(){super();}

	public ShuntList(PsseModel model) throws PsseModelException
	{
		super(model);
		_buses = model.getBuses();
	}

	/** Get a Switch by it's index. */
	@Override
	public Shunt get(int ndx) { return new Shunt(ndx,this); }
	/** Get an SwitchIn by it's ID. */
	@Override
	public Shunt get(String id) { return super.get(id); }
	
	/* convenience methods */
	
	public Bus getBus(int ndx) throws PsseModelException {return _model.getBus(getI(ndx));}
	public Complex getCaseY(int ndx) throws PsseModelException {return new Complex(getG(ndx), getB(ndx)).div(100f);}
	public boolean isSwitchedOn(int ndx) throws PsseModelException {return false;}

	/* raw methods */
	
	/** get connected bus */
	public abstract String getI(int ndx) throws PsseModelException;
	/** shunt nominal B in MVAr at unity bus voltage */
	public float getB(int ndx) throws PsseModelException {return 0f;}
	/** get G, MVAr at unity voltage */
	public float getG(int ndx) throws PsseModelException {return 0f;}

	/* RT fields */
	
	public void setRTS(int ndx, Complex s) {_tmps = s;}

	public Complex getRTS(int ndx) throws PsseModelException
	{
		PowerCalculator.calcShunt(get(ndx));
		return _tmps;
	}

}
