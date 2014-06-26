package com.powerdata.openpa;

public abstract class TwoTermDevList<T extends TwoTermDev> extends BaseList<T> 
{
	protected int[] _fbx, _tbx;
	protected BusList _buses;
	boolean[] _insvc, _insvco;
	
	protected TwoTermDevList(){super();}

	protected TwoTermDevList(PALists model, int[] keys, int[] fbkey, int[] tbkey)
	{
		super(model, keys);
		_buses = model.getBuses();
		_fbx = cvtBusKeys(fbkey);
		_tbx = cvtBusKeys(tbkey);
	}
	
	protected TwoTermDevList(PALists model, int size, int[] fbkey, int[] tbkey)
	{
		super(model, size);
		_buses = model.getBuses();
		_fbx = cvtBusKeys(fbkey);
		_tbx = cvtBusKeys(tbkey);
	}

	protected int[] cvtBusKeys(int[] bkeys)
	{
		int n = bkeys.length;
		int[] rv = new int[n];
		for(int i=0; i < n; ++i)
			rv[i] = _buses.getOfs(bkeys[i]);
		return rv;
	}
	
	public Bus getFromBus(int ndx)
	{
		return _buses.get(_fbx[ndx]);
	}
	
	/** get from-side buses */
	public BusList getFromBuses()
	{
		return new BusSubList(_model, _buses, _fbx);
	}

	public Bus getToBus(int ndx)
	{
		return _buses.get(_tbx[ndx]);
	}

	/** get to-side bus indexes */
	public BusList getToBuses()
	{
		return new BusSubList(_model, _buses, _tbx);
	}

	/** is device in service */
	public boolean isInSvc(int ndx)
	{
		return _insvc[ndx];
	}

	/** is device in service */
	public boolean[] isInSvc()
	{
		return _insvc;
	}

	/** set device in/out of service */
	public void setInSvc(int ndx, boolean state)
	{
		if (_insvco == null && _insvc != null)
			_insvco = _insvc.clone();
		_insvc[ndx] = state;
	}

	/** set device in/out of service */
	public void setInSvc(boolean[] state)
	{
		if (_insvc != state)
		{
			if (_insvco == null)
				_insvco = _insvc;
			_insvc = state;
		}
	}
	
	/** resolve from-bus keys for the given offsets */
	protected int[] getFBusKeys(int[] offsets)
	{
		int n = offsets.length;
		int[] rv = new int[n];
		for(int i=0; i < n; ++i)
			rv[i] = getFromBus(offsets[i]).getKey();
		return rv;
	}
	/** resolve to-bus keys for the given offsets */
	protected int[] getTBusKeys(int[] offsets)
	{
		int n = offsets.length;
		int[] rv = new int[n];
		for(int i=0; i < n; ++i)
			rv[i] = getToBus(offsets[i]).getKey();
		return rv;
	}
	
	/** sublist support for bulk from-side buses */
	protected BusList getSubListFromBuses(int[] map)
	{
		int n = size();
		int[] fx = new int[n];
		for(int i=0; i < n; ++i)
			fx[i] = _fbx[map[i]];
		return  new BusSubList(_model, _buses, fx);
	}

	/** sublist support for bulk to-side buses */
	protected BusList getSubListToBuses(int[] map)
	{
		int n = size();
		int[] tx = new int[n];
		for(int i=0; i < n; ++i)
			tx[i] = _tbx[map[i]];
		return  new BusSubList(_model, _buses, tx);
	}
	/** InSvc access for "sublist" subclasses to use */
	protected boolean[] getSubListInSvc(int[] slndx)
	{
		int n = size();
		boolean[] rv = new boolean[n];
		for(int i=0; i < n; ++i)
			rv[i] = isInSvc(slndx[i]);
		return rv;
	}
	/** InSvc access for "sublist" subclasses to use */
	protected void setSubListInSvc(boolean[] insvc, int[] slndx)
	{
		int n = size();
		for(int i=0; i < n; ++i)
			setInSvc(slndx[i], insvc[i]);
	}

}
