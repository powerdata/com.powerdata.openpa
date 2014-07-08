package com.powerdata.openpa;

public class OneTermDevListI<T extends OneTermDev> extends AbstractPAList<T> implements OneTermDevList<T> 
{
	int[][] _bus=IInt();
	float[][] _p=IFlt(), _q=IFlt();
	boolean[][] _insvc=IBool();
	BusList _buses;

	protected OneTermDevListI() {super();}

	protected OneTermDevListI(PAModel model, int[] keys)
	{
		super(model, keys);
		_buses = model.getBuses();
	}
	protected OneTermDevListI(PAModel model, int size)
	{
		super(model, size);
		_buses = model.getBuses();
	}
	
	@Override
	public Bus getBus(int ndx)
	{
		return _buses.get(getInt(_bus, ndx));
	}
	
	@Override
	public void setBus(int ndx, Bus b)
	{
		setInt(_bus, ndx, b.getIndex());
	}

	@Override
	public Bus[] getBus()
	{
		return _buses.toArray(getInt(_bus));
	}

	@Override
	public void setBus(Bus[] b)
	{
		setInt(_bus, BaseList.ObjectNdx(b));
	}

	@Override
	public float getP(int ndx)
	{
		return getFloat(_p, ndx);
	}
	
	@Override
	public float[] getP()
	{
		return getFloat(_p);
	}

	@Override
	public float getQ(int ndx)
	{
		return getFloat(_q, ndx);
	}

	/** Get device reactive power injection in MVAr */
	@Override
	public float[] getQ()
	{
		return getFloat(_q);
	}

	@Override
	public void setP(int ndx, float p)
	{
		setFloat(_p, ndx, p);
	}

	/** Set device active power injection in MW */
	@Override
	public void setP(float[] p)
	{
		setFloat(_p, p);
	}

	@Override
	public void setQ(int ndx, float q)
	{
		setFloat(_q, ndx, q);
	}

	/** Set device reactive power injection in MVAr */
	@Override
	public void setQ(float[] q)
	{
		setFloat(_q, q);
	}

	@Override
	public boolean isInSvc(int ndx)
	{
		return getBool(_insvc, ndx);
	}

	/** is device in service */
	@Override
	public boolean[] isInSvc()
	{
		return getBool(_insvc);
	}

	@Override
	public void setInSvc(int ndx, boolean state)
	{
		setBool(_insvc, ndx, state);
	}

	/** set device in/out of service */
	@Override
	public void setInSvc(boolean[] state)
	{
		setBool(_insvc, state);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T get(int index)
	{
		return (T) new OneTermDev(this, index);
	}
}
