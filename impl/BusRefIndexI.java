package com.powerdata.openpa.impl;

import java.util.WeakHashMap;
import com.powerdata.openpa.BaseList;
import com.powerdata.openpa.BaseObject;
import com.powerdata.openpa.BusRefIndex;
import com.powerdata.openpa.OneTermDev;
import com.powerdata.openpa.OneTermDevListIfc;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.BusList;
import com.powerdata.openpa.TwoTermDev;
import com.powerdata.openpa.TwoTermDevListIfc;

public class BusRefIndexI implements BusRefIndex
{
	@FunctionalInterface
	public interface BusGroupResolver
	{
		int[] apply(BusList l, int[] b) throws PAModelException;
	}

	/** index a list of buses for any list method returning a bus */
	class AnyListIndex 
	{
		int[] _index;
		
		AnyListIndex(BaseList<? extends BaseObject> list, BusFunction bfcn) throws PAModelException
		{
			int n = list.size();
			int[] lbx = new int[n];
			for(int i=0; i < n; ++i)
				lbx[i] = bfcn.apply(i).getIndex();
			_index = _idx.apply(_buses, lbx);
		}
		
		public int[] get() { return _index; }
	}

	class OneTermIndex
	{
		int[] _index;
		
		OneTermIndex(OneTermDevListIfc<? extends OneTermDev> list) throws PAModelException
		{
			_index = _idx.apply(_buses, list.getBusIndexes());
		}
		
		public int[] get() {return _index;}
	}
	
	class TwoTermIndex
	{
		int[] _idx1, _idx2;
		
		TwoTermIndex(TwoTermDevListIfc<? extends TwoTermDev> list) throws PAModelException
		{
			_idx1 = _idx.apply(_buses, list.getFromBusIndexes());
			_idx2 = _idx.apply(_buses, list.getToBusIndexes());
		}
		
		TwoTerm get() {return new TwoTerm(_idx1, _idx2);}
	}
	
	
	WeakHashMap<OneTermDevListIfc<? extends OneTermDev>, OneTermIndex> _map1t = new WeakHashMap<>();
	WeakHashMap<TwoTermDevListIfc<? extends TwoTermDev>, TwoTermIndex> _map2t = new WeakHashMap<>();
	WeakHashMap<BaseList<? extends BaseObject>, AnyListIndex> 
		_mapany = new WeakHashMap<>();
	
	
	BusGroupResolver _idx;
	
	BusList _buses;
	public BusRefIndexI(BusList buses, BusGroupResolver idx)
	{
		_idx = idx;
		_buses = buses;
	}
	
	@Override 
	public BusList getBuses() {return _buses;}
	
	@Override
	public int[] get1TBus(OneTermDevListIfc<? extends OneTermDev> t1list) throws PAModelException	
	{
		OneTermIndex i = _map1t.get(t1list);
		if (i == null)
		{
			i = new OneTermIndex(t1list);
			_map1t.put(t1list, i);
		}
		return i.get();
	}	

	@Override
	public TwoTerm get2TBus(TwoTermDevListIfc<? extends TwoTermDev> t2list) throws PAModelException
	{
		TwoTermIndex i = _map2t.get(t2list);
		if (i == null)
		{
			
			i = new TwoTermIndex(t2list);
			_map2t.put(t2list, i);
		}
		return i.get();
	}
	
	
	
	@Override
	public int[] mapBusFcn(BaseList<? extends BaseObject> list, BusFunction bfc) throws PAModelException
	{
		AnyListIndex i = _mapany.get(list);
		if (i == null)
		{
			i = new AnyListIndex(list, bfc);
			_mapany.put(list, i);
		}
		return i.get();
	}
//	public <T extends BaseList<? extends BaseObject>> 
//		int[] mapBusFcn(T rlist, BusFunction<T> bfc) throws PAModelException
//	{
//		ListIndex<T> lit = new ListIndex<T>(rlist, bfc);
//		return lit.get();
//	}
	
}