package com.powerdata.openpa;

public class TwoTermDevListI<T extends TwoTermDev> extends AbstractPAList<T> implements TwoTermDevList<T> 
{
	int[][] _fbus=IInt(), _tbus=IInt();
	boolean[][] _insvc=IBool();
	BusList _buses;
	
	protected TwoTermDevListI(){super();}

	protected TwoTermDevListI(PAModel model, int[] keys)
	{
		super(model, keys);
		_buses = model.getBuses();
	}
	
	protected TwoTermDevListI(PAModel model, int size)
	{
		super(model, size);
		_buses = model.getBuses();
	}

	@Override
	public Bus getFromBus(int ndx)
	{
		return _buses.get(getInt(_fbus, ndx));
	}
	
	@Override
	public void setFromBus(int ndx, Bus b)
	{
		setInt(_fbus, ndx, b.getIndex());
	}

	@Override
	public Bus[] getFromBus()
	{
		return _buses.toArray(getInt(_fbus));
	}

	@Override
	public void setFromBus(Bus[] b)
	{
		setInt(_fbus, BaseList.ObjectNdx(b));
	}

	@Override
	public Bus getToBus(int ndx)
	{
		return _buses.get(getInt(_tbus, ndx));
	}

	@Override
	public void setToBus(int ndx, Bus b)
	{
		setInt(_tbus, ndx, b.getIndex());
	}

	@Override
	public Bus[] getToBus()
	{
		return _buses.toArray(getInt(_tbus));
	}

	@Override
	public void setToBus(Bus[] b)
	{
		setInt(_tbus, BaseList.ObjectNdx(b));
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
		return (T) new TwoTermDev(this, index);
	}

}
