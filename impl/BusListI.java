package com.powerdata.openpa.impl;

import java.lang.ref.WeakReference;
import java.util.AbstractList;
import java.util.List;
import com.powerdata.openpa.Area;
import com.powerdata.openpa.AreaList;
import com.powerdata.openpa.BaseList;
import com.powerdata.openpa.Bus;
import com.powerdata.openpa.BusGrpMap;
import com.powerdata.openpa.BusList;
import com.powerdata.openpa.ColumnMeta;
import com.powerdata.openpa.Island;
import com.powerdata.openpa.IslandList;
import com.powerdata.openpa.ListMetaType;
import com.powerdata.openpa.Owner;
import com.powerdata.openpa.OwnerList;
import com.powerdata.openpa.Station;
import com.powerdata.openpa.StationList;
import com.powerdata.openpa.VoltageLevel;
import com.powerdata.openpa.VoltageLevelList;


public class BusListI extends GroupListI<Bus> implements BusList
{
	static class UnityBusGrpMap implements BusGrpMap
	{
		int _size;
		WeakReference<int[]> _tok = new WeakReference<>(null);
		
		UnityBusGrpMap(int size)
		{
			_size = size;
		}
		@Override
		public int getGrp(int index)
		{
			return index;
		}

		@Override
		public List<int[]> map()
		{
			return new AbstractList<int[]>()
			{
				@Override
				public int[] get(int index)
				{
					return new int[0];
				}

				@Override
				public int size()
				{
					return _size;
				}};
		}

		@Override
		public int size()
		{
			return _size;
		}
		@Override
		public int[] getTokens()
		{
			int[] rv = _tok.get();
			if (rv == null)
			{
				rv = new int[_size];
				for(int i=0; i < _size; ++i) rv[i] = i;
				_tok = new WeakReference<>(rv);
			}
			return rv;
		}
	}

	static final PAListEnum _PFld = new PAListEnum()
	{
		@Override
		public ColumnMeta id() {return ColumnMeta.BusID;}
		@Override
		public ColumnMeta name() {return ColumnMeta.BusNAME;}
	};
	
	FloatData _vm = new FloatData(ColumnMeta.BusVM),
			_va = new FloatData(ColumnMeta.BusVA);
	
	IntData _area = new IntData(ColumnMeta.BusAREA),
			_own = new IntData(ColumnMeta.BusOWNER),
			_sta = new IntData(ColumnMeta.BusSTATION),
			_vl = new IntData(ColumnMeta.BusVLEV),
			_fspri = new IntData(ColumnMeta.BusFREQSRCPRI);
	
	AreaList _arealist;
	OwnerList _ownerlist;
	StationList _stationlist;
	VoltageLevelList _vllist;
	
	BusListI(){super();}
	
	public BusListI(PAModelI model, int[] keys)
	{
		super(model, keys, new UnityBusGrpMap(keys.length), _PFld);
		_arealist = model.getAreas();
		_ownerlist = model.getOwners();
		_stationlist = model.getStations();
		_vllist = model.getVoltageLevels();
	}
	
	public BusListI(PAModelI model, int size)
	{
		super(model, new UnityBusGrpMap(size), _PFld);
		_arealist = model.getAreas();
	}

	@Override
	public Bus get(int index)
	{
		return new Bus(this, index);
	}
	
	@Override
	public float getVM(int ndx) {return _vm.get(ndx);}

	@Override
	public float[] getVM() {return _vm.get();}

	@Override
	public void setVM(int ndx, float vm) {_vm.set(ndx, vm);}

	@Override
	public void setVM(float[] vm) {_vm.set(vm);}
	
	@Override
	public float getVA(int ndx) {return _va.get(ndx);}

	@Override
	public float[] getVA() {return _va.get();}

	@Override
	public void setVA(int ndx, float va) {_va.set(ndx, va);}
	
	@Override
	public void setVA(float[] va) {_va.set(va);}

	@Override
	public Area getArea(int ndx)
	{
		return _arealist.get(_area.get(ndx));
	}
	
	@Override
	public void setArea(int ndx, Area a)
	{
		_area.set(ndx, a.getIndex());
	}

	@Override
	public Area[] getArea()
	{
		return _arealist.toArray(_area.get());
	}

	@Override
	public void setArea(Area[] area)
	{
		_area.set(BaseList.ObjectNdx(area));
	}
	
	@Override
	public Owner getOwner(int ndx)
	{
		return _ownerlist.get(_own.get(ndx));
	}

	@Override
	public void setOwner(int ndx, Owner o)
	{
		_own.set(ndx, o.getIndex());
	}
	@Override
	public Owner[] getOwner()
	{
		return _ownerlist.toArray(_own.get());
	}

	@Override
	public void setOwner(Owner[] owner)
	{
		_own.set(BaseList.ObjectNdx(owner));
	}
	

	@Override
	public Island getIsland(int ndx)
	{
		return _model.getIslands().getByBus(get(ndx));
	}

	public Island[] getIsland()
	{
		IslandList islands = _model.getIslands();
		Island[] rv = new Island[_size];
		for(int i=0; i < _size; ++i)
			rv[i] = islands.getByBus(get(i));
		return rv;
	}
	
	@Override
	public int getFrequencySourcePriority(int ndx)
	{
		return _fspri.get(ndx);
	}

	@Override
	public void setFrequencySourcePriority(int ndx, int fsp)
	{
		_fspri.set(ndx, fsp);
	}

	@Override
	public int[] getFrequencySourcePriority()
	{
		return _fspri.get();
	}
	
	@Override
	public void setFrequencySourcePriority(int[] fsrc)
	{
		_fspri.set(fsrc);
	}
	
	@Override
	public Station getStation(int ndx)
	{
		return _stationlist.get(_sta.get(ndx));
	}

	@Override
	public void setStation(int ndx, Station s)
	{
		_sta.set(ndx, s.getIndex());
	}
	
	@Override
	public Station[] getStation()
	{
		return _stationlist.toArray(_sta.get());
	}
	
	@Override
	public void setStation(Station[] s)
	{
		_sta.set(BaseList.ObjectNdx(s));
	}

	@Override
	public VoltageLevel getVoltageLevel(int ndx)
	{
		return _vllist.get(_vl.get(ndx));
	}

	@Override
	public void setVoltageLevel(int ndx, VoltageLevel l)
	{
		_vl.set(ndx, l.getIndex());
	}

	@Override
	public VoltageLevel[] getVoltageLevel()
	{
		return _vllist.toArray(_vl.get());
	}
	
	@Override
	public void setVoltageLevel(VoltageLevel[] l)
	{
		_vl.set(BaseList.ObjectNdx(l));
	}

	@Override
	protected ListMetaType getMetaType()
	{
		return ListMetaType.Bus;
	}
	
}
