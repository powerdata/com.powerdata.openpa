package com.powerdata.openpa.pwrflow;

import java.util.HashMap;
import com.powerdata.openpa.BaseList;
import com.powerdata.openpa.BaseObject;
import com.powerdata.openpa.Bus;
import com.powerdata.openpa.BusRegulator;
import com.powerdata.openpa.OneTermDev;
import com.powerdata.openpa.OneTermDevListIfc;
import com.powerdata.openpa.PAModel;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.BusList;
import com.powerdata.openpa.TwoTermDev;
import com.powerdata.openpa.TwoTermDevListIfc;

public class BusRefIndex
{
	@FunctionalInterface
	private interface BusFunction<T>
	{
		Bus apply(T list, int index) throws PAModelException;
	}
	@FunctionalInterface
	private interface BusGroupResolver
	{
		Bus apply(BusList l, Bus b) throws PAModelException;
	}

	class ListIndex<T extends BaseList<? extends BaseObject>>
	{
		protected int[] _index;
		protected BusFunction<T> _bfcn;
		protected T _list;

		ListIndex(T list, BusFunction<T> bfcn)
		{
			_list = list;
			_bfcn = bfcn;
		}
		
		public int[] get() throws PAModelException
		{
			if (_index == null) _index = buildNdx(_bfcn);
			return _index;
		}

		protected int[] buildNdx(BusFunction<T> bfcn) throws PAModelException
		{
			int n = _list.size();
			int[] rv = new int[n];
			for(int i=0; i < n; ++i)
				rv[i] = _idx.apply(_buses, bfcn.apply(_list,i)).getIndex();
			return rv;
		}
	}
	
	class ListIndex2T extends ListIndex<TwoTermDevListIfc<? extends TwoTermDev>>
	{
		BusFunction<TwoTermDevListIfc<? extends TwoTermDev>> _to;
		int[] _tondx;
		
		ListIndex2T(TwoTermDevListIfc<? extends TwoTermDev> list)
		{
			super(list, TwoTermDevListIfc::getFromBus);
			_to = TwoTermDevListIfc::getToBus;
		}
		
		public int[][] getTwoTerm() throws PAModelException
		{
			if (_tondx == null) _tondx = buildNdx(_to);
			return new int[][] {super.get(), _tondx};
		}
		
	}
	
	HashMap<OneTermDevListIfc<? extends OneTermDev>,
		ListIndex<OneTermDevListIfc<? extends OneTermDev>>> _map1t = new HashMap<>();
	HashMap<TwoTermDevListIfc<? extends TwoTermDev>, ListIndex2T> _map2t = new HashMap<>();
	HashMap<BusRegulator, ListIndex<?>> _mapreg;
	BusGroupResolver _idx;
	
	BusList _buses;
	private BusRefIndex(PAModel m, BusList buses, BusGroupResolver idx)
	{
		_idx = idx;
		_buses = buses;
	}
	
	public BusList getBuses() {return _buses;}
	
	public int[] get1TBus(OneTermDevListIfc<? extends OneTermDev> t1list) throws PAModelException
	{
		ListIndex<OneTermDevListIfc<? extends OneTermDev>> i = _map1t.get(t1list);
		if (i == null)
		{
			i = new ListIndex<OneTermDevListIfc<? extends OneTermDev>>(t1list,
					OneTermDevListIfc::getBus);
			_map1t.put(t1list, i);
		}
		return i.get();
	}	

	public <T extends TwoTermDevListIfc<? extends TwoTermDev>> int[][] get2TBus(T t2list) throws PAModelException
	{
		ListIndex2T i = _map2t.get(t2list);
		if (i == null)
		{
			i = new ListIndex2T(t2list);
			_map2t.put(t2list, i);
		}
		return i.getTwoTerm();
	}
	
	public <T extends BaseList<? extends BaseObject> & BusRegulator> 
		int[] getRegBus(T rlist) throws PAModelException
	{
		@SuppressWarnings("unchecked")
		ListIndex<T> i = (ListIndex<T>) _mapreg.get(rlist);
		if (i == null) 
		{
			i = new ListIndex<T>(rlist, BusRegulator::getRegBus);
			_mapreg.put(rlist, i);
		}
		return i.get();
	}
	
	
	public static BusRefIndex CreateFromConnectivityBus(PAModel m) throws PAModelException
	{
		return new BusRefIndex(m, m.getBuses(), (l,b) -> b);
	}
	
	public static BusRefIndex CreateFromSingleBus(PAModel m) throws PAModelException
	{
		return new BusRefIndex(m, m.getSingleBus(), BusList::getByBus);
	}
	
	
}