package com.powerdata.openpa;

import java.lang.ref.WeakReference;
import java.util.AbstractList;
import java.util.List;


public class BusList extends BusListIfc
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

//	float[] _bkv, _bkvo, _vm, _vmo, _va, _vao;
//	int[] _areas, _areao, _owners, _ownero;
	float[][] _bkv=IFlt(), _vm=IFlt(), _va=IFlt();
	int[][] _areas=IInt(), _owners=IInt(), _stations=IInt(), _vlevs=IInt();
	AreaList _arealist;
	OwnerList _ownerlist;
	StationList _stationlist;
	VoltageLevelList _vllist;
	
	public static final BusList Empty = new BusList();

	BusList(){super();}
	
	public BusList(PALists model, int[] keys)
	{
		super(model, keys, new UnityBusGrpMap(keys.length));
		_arealist = model.getAreas();
		_ownerlist = model.getOwners();
		_stationlist = model.getStations();
		_vllist = model.getVoltageLevels();
	}
	
	public BusList(PALists model, int size)
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
	public float getBaseKV(int ndx) {return getFloat(_bkv, ndx);}

	public float[] getBaseKV() {return getFloat(_bkv);}

	@Override
	public void setBaseKV(int ndx, float kv) {setFloat(_bkv, ndx, kv);}

	public void setBaseKV(float[] kv) {setFloat(_bkv, kv);}
	
	@Override
	public float getVM(int ndx) {return getFloat(_vm, ndx);}

	public float[] getVM() {return getFloat(_vm);}

	@Override
	public void setVM(int ndx, float vm) {setFloat(_vm, ndx, vm);}

	public void setVM(float[] vm) {setFloat(_vm, vm);}
	
	@Override
	public float getVA(int ndx) {return getFloat(_va, ndx);}

	public float[] getVA() {return getFloat(_va);}

	@Override
	public void setVA(int ndx, float va) {setFloat(_va, ndx, va);}
	
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

	public Area[] getArea()
	{
		return _arealist.toArray(getInt(_areas));
	}

	public void setArea(Area[] area)
	{
		setInt(_areas, objectNdx(area));
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
	public Owner[] getOwner()
	{
		return _ownerlist.toArray(getInt(_owners));
	}

	public void setOwner(Owner[] owner)
	{
		setInt(_owners, objectNdx(owner));
	}
	

	@Override
	public Island getIsland(int ndx)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getFrequencySourcePriority(int ndx)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getFrequency(int ndx)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setFrequencySourcePriority(int ndx, int fsp)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setFrequency(int ndx, float f)
	{
		// TODO Auto-generated method stub
		
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
	public VoltageLevel getVoltageLevel(int ndx)
	{
		return _vllist.get(getInt(_vlevs, ndx));
	}

	@Override
	public void setVoltageLevel(int ndx, VoltageLevel l)
	{
		setInt(_vlevs, ndx, l.getIndex());
	}

	public Station[] getStation()
	{
		return _stationlist.toArray(getInt(_stations));
	}
	
	public void setStation(Station[] s)
	{
		setInt(_stations, objectNdx(s));
	}

	public VoltageLevel[] getVoltageLevels()
	{
		return _vllist.toArray(getInt(_vlevs));
	}
	
	public void setVoltageLevel(VoltageLevel[] l)
	{
		setInt(_vlevs, objectNdx(l));
	}
	
	@Override
	public boolean isEnergized(int ndx)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setEnergized(int ndx, boolean state)
	{
		// TODO Auto-generated method stub
		
	}

}
