package com.powerdata.openpa;

public abstract class OneTermDevList<T extends OneTermDev> extends BaseList<T> 
{
	protected int[] _bx;
	protected BusList _buses;

	protected OneTermDevList(PALists model, int[] keys)
	{
		super(model, keys);
		int n = size();
		_bx = new int[n];
		_buses = model.getBuses();
		for(int i=0; i < n; ++i)
			_bx[i] = _buses.getOfs(keys[i]);
	}
	
	protected OneTermDevList() {super();}

	public Bus getBus(int ndx)
	{
		return _buses.get(_bx[ndx]);
	}
	
	public int[] getBusIndexes()
	{
		// TODO
		return null;
	}
	
	public BusList getBuses()
	{
		return new BusSubList(_model, _buses, _bx);
	}
	
	public float getP(int ndx)
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	/** Get device active power injection in MW */
	public float[] getP()
	{
		//TODO
		return null;
	}

	public float getQ(int ndx)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/** Get device reactive power injection in MVAr */
	public float[] getQ()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public void setP(int ndx, float p)
	{
		// TODO Auto-generated method stub
	}

	/** Set device active power injection in MW */
	public void setP(float[] p)
	{
		// TODO Auto-generated method stub
	}

	public void setQ(int ndx, float q)
	{
		// TODO Auto-generated method stub
	}

	/** Set device reactive power injection in MVAr */
	public void setQ(float[] q)
	{
		// TODO Auto-generated method stub
	}

	public boolean isInSvc(int ndx)
	{
		// TODO Auto-generated method stub
		return false;
	}

	/** is device in service */
	public boolean[] isInSvc()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public void setInSvc(int ndx, boolean state)
	{
		// TODO Auto-generated method stub
	}

	/** set device in/out of service */
	public void setInSvc(boolean[] state)
	{
		// TODO Auto-generated method stub
	}

}
