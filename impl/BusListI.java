package com.powerdata.openpa.impl;

import java.lang.ref.WeakReference;
import java.util.AbstractList;
import java.util.List;
import com.powerdata.openpa.Area;
import com.powerdata.openpa.AreaList;
import com.powerdata.openpa.Bus;
import com.powerdata.openpa.GroupIndex;
import com.powerdata.openpa.BusList;
import com.powerdata.openpa.ColumnMeta;
import com.powerdata.openpa.GroupListI;
import com.powerdata.openpa.ElectricalIsland;
import com.powerdata.openpa.ElectricalIslandList;
import com.powerdata.openpa.ListMetaType;
import com.powerdata.openpa.Owner;
import com.powerdata.openpa.OwnerList;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.Station;
import com.powerdata.openpa.StationList;
import com.powerdata.openpa.VoltageLevel;
import com.powerdata.openpa.VoltageLevelList;


public class BusListI extends GroupListI<Bus> implements BusList
{
	static class UnityBusGrpMap implements GroupIndex
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
	
	public BusListI(){super();}
	
	public BusListI(PAModelI model, int[] keys) throws PAModelException
	{
		super(model, keys, new UnityBusGrpMap(keys.length), _PFld);
		_arealist = model.getAreas();
		_ownerlist = model.getOwners();
		_stationlist = model.getStations();
		_vllist = model.getVoltageLevels();
	}
	
	public BusListI(PAModelI model, int size) throws PAModelException
	{
		super(model, new UnityBusGrpMap(size), _PFld);
		_arealist = model.getAreas();
		_ownerlist = model.getOwners();
		_stationlist = model.getStations();
		_vllist = model.getVoltageLevels();
	}

	@Override
	public Bus get(int index)
	{
		return new Bus(this, index);
	}
	
	@Override
	public float getVM(int ndx) throws PAModelException {return _vm.get(ndx);}

	@Override
	public float[] getVM() throws PAModelException {return _vm.get();}

	@Override
	public void setVM(int ndx, float vm) throws PAModelException {_vm.set(ndx, vm);}

	@Override
	public void setVM(float[] vm) throws PAModelException {_vm.set(vm);}
	
	@Override
	public float getVA(int ndx) throws PAModelException {return _va.get(ndx);}

	@Override
	public float[] getVA() throws PAModelException {return _va.get();}

	@Override
	public void setVA(int ndx, float va) throws PAModelException {_va.set(ndx, va);}
	
	@Override
	public void setVA(float[] va) throws PAModelException {_va.set(va);}

	@Override
	public Area getArea(int ndx) throws PAModelException 
	{
		return _arealist.get(_area.get(ndx));
	}
	
	@Override
	public void setArea(int ndx, Area a) throws PAModelException 
	{
		_area.set(ndx, a.getIndex());
	}

	@Override
	public Area[] getArea() throws PAModelException
	{
		return _arealist.toArray(_area.get());
	}

	@Override
	public void setArea(Area[] area) throws PAModelException
	{
		_area.set(_arealist.getIndexes(area));
	}
	
	@Override
	public Owner getOwner(int ndx) throws PAModelException
	{
		return _ownerlist.get(_own.get(ndx));
	}

	@Override
	public void setOwner(int ndx, Owner o) throws PAModelException
	{
		_own.set(ndx, o.getIndex());
	}
	@Override
	public Owner[] getOwner() throws PAModelException
	{
		return _ownerlist.toArray(_own.get());
	}

	@Override
	public void setOwner(Owner[] owner) throws PAModelException
	{
		_own.set(_ownerlist.getIndexes(owner));
	}
	

	@Override
	public ElectricalIsland getIsland(int ndx) throws PAModelException
	{
		return _model.getElectricalIslands().getByBus(get(ndx));
	}

	public ElectricalIsland[] getIsland() throws PAModelException
	{
		ElectricalIslandList islands = _model.getElectricalIslands();
		ElectricalIsland[] rv = new ElectricalIsland[_size];
		for(int i=0; i < _size; ++i)
			rv[i] = islands.getByBus(get(i));
		return rv;
	}
	
	@Override
	public int getFreqSrcPri(int ndx) throws PAModelException
	{
		return _fspri.get(ndx);
	}

	@Override
	public void setFreqSrcPri(int ndx, int fsp) throws PAModelException
	{
		_fspri.set(ndx, fsp);
	}

	@Override
	public int[] getFreqSrcPri() throws PAModelException
	{
		return _fspri.get();
	}
	
	@Override
	public void setFreqSrcPri(int[] fsrc) throws PAModelException
	{
		_fspri.set(fsrc);
	}
	
	@Override
	public Station getStation(int ndx) throws PAModelException
	{
		return _stationlist.get(_sta.get(ndx));
	}

	@Override
	public void setStation(int ndx, Station s) throws PAModelException
	{
		_sta.set(ndx, s.getIndex());
	}
	
	@Override
	public Station[] getStation() throws PAModelException
	{
		return _stationlist.toArray(_sta.get());
	}
	
	@Override
	public void setStation(Station[] s) throws PAModelException
	{
		_sta.set(_stationlist.getIndexes(s));
	}

	@Override
	public VoltageLevel getVoltageLevel(int ndx) throws PAModelException
	{
		return _vllist.get(_vl.get(ndx));
	}

	@Override
	public void setVoltageLevel(int ndx, VoltageLevel l) throws PAModelException
	{
		_vl.set(ndx, l.getIndex());
	}

	@Override
	public VoltageLevel[] getVoltageLevel() throws PAModelException
	{
		return _vllist.toArray(_vl.get());
	}
	
	@Override
	public void setVoltageLevel(VoltageLevel[] l) throws PAModelException
	{
		_vl.set(_vllist.getIndexes(l));
	}

	@Override
	public ListMetaType getListMeta()
	{
		return ListMetaType.Bus;
	}

	@Override
	public Bus getByBus(Bus b)
	{
		/* since we are the "unity" bus list, the typical
		 * lookup by bus (similar to all other group instances)
		 * doesn't make sense.  
		 * 
		 *  However, it seems like a lookup by key would be reasonable
		 *  here.
		 */
		return getByKey(b.getKey());
	}

}
