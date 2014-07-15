package com.powerdata.openpa;

import java.lang.ref.WeakReference;
import java.util.AbstractList;
import java.util.List;
import com.powerdata.openpa.PAModel.ListMetaType;


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

	float[][] _vm=IFlt(), _va=IFlt();
	int[][] _areas=IInt(), _owners=IInt(), _stations=IInt(), _vlevs=IInt();
	int[][] _fspri=IInt();
	AreaList _arealist;
	OwnerList _ownerlist;
	StationList _stationlist;
	VoltageLevelList _vllist;
	
	public static final BusList Empty = new BusListI();

	BusListI(){super();}
	
	public BusListI(PAModel model, int[] keys)
	{
		super(model, keys, new UnityBusGrpMap(keys.length));
		_arealist = model.getAreas();
		_ownerlist = model.getOwners();
		_stationlist = model.getStations();
		_vllist = model.getVoltageLevels();
	}
	
	public BusListI(PAModel model, int size)
	{
		super(model, new UnityBusGrpMap(size));
		_arealist = model.getAreas();
	}

	@Override
	public Bus get(int index)
	{
		return new Bus(this, index);
	}
	
	@Override
	public float getVM(int ndx) {return getFloat(_vm, ndx);}

	@Override
	public float[] getVM() {return getFloat(_vm);}

	@Override
	public void setVM(int ndx, float vm) {setFloat(_vm, ndx, vm);}

	@Override
	public void setVM(float[] vm) {setFloat(_vm, vm);}
	
	@Override
	public float getVA(int ndx) {return getFloat(_va, ndx);}

	@Override
	public float[] getVA() {return getFloat(_va);}

	@Override
	public void setVA(int ndx, float va) {setFloat(_va, ndx, va);}
	
	@Override
	public void setVA(float[] va) {setFloat(_va, va);}

	@Override
	public Area getArea(int ndx)
	{
		return _arealist.get(getInt(_areas, ndx));
	}
	
	@Override
	public void setArea(int ndx, Area a)
	{
		setInt(_areas, ndx, a.getIndex());
	}

	@Override
	public Area[] getArea()
	{
		return _arealist.toArray(getInt(_areas));
	}

	@Override
	public void setArea(Area[] area)
	{
		setInt(_areas, BaseList.ObjectNdx(area));
	}
	
	@Override
	public Owner getOwner(int ndx)
	{
		return _ownerlist.get(getInt(_owners, ndx));
	}

	@Override
	public void setOwner(int ndx, Owner o)
	{
		setInt(_owners, ndx, o.getIndex());
	}
	@Override
	public Owner[] getOwner()
	{
		return _ownerlist.toArray(getInt(_owners));
	}

	@Override
	public void setOwner(Owner[] owner)
	{
		setInt(_owners, BaseList.ObjectNdx(owner));
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
		return getInt(_fspri, ndx);
	}

	@Override
	public void setFrequencySourcePriority(int ndx, int fsp)
	{
		setInt(_fspri, ndx, fsp);
	}

	@Override
	public int[] getFrequencySourcePriority()
	{
		return getInt(_fspri);
	}
	
	@Override
	public void setFrequencySourcePriority(int[] fsrc)
	{
		setInt(_fspri, fsrc);
	}
	
	@Override
	public Station getStation(int ndx)
	{
		return _stationlist.get(getInt(_stations, ndx));
	}

	@Override
	public void setStation(int ndx, Station s)
	{
		setInt(_stations, ndx, s.getIndex());
	}
	
	@Override
	public Station[] getStation()
	{
		return _stationlist.toArray(getInt(_stations));
	}
	
	@Override
	public void setStation(Station[] s)
	{
		setInt(_stations, BaseList.ObjectNdx(s));
	}

	@Override
	public VoltageLevel getVoltageLevel(int ndx)
	{
		return _vllist.get(getInt(_vlevs, ndx));
	}

	@Override
	public void setVoltageLevel(int ndx, VoltageLevel l)
	{
		setInt(_vlevs, ndx, l.getIndex());
	}

	@Override
	public VoltageLevel[] getVoltageLevel()
	{
		return _vllist.toArray(getInt(_vlevs));
	}
	
	@Override
	public void setVoltageLevel(VoltageLevel[] l)
	{
		setInt(_vlevs, BaseList.ObjectNdx(l));
	}

	@Override
	protected ListMetaType getMetaType()
	{
		return ListMetaType.Bus;
	}
	
}
