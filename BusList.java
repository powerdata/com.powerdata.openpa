package com.powerdata.openpa;

import java.lang.ref.WeakReference;
import java.util.AbstractList;
import java.util.List;


public class BusList extends BusListIfc
{
	static class SmplBusGrpMap implements BusGrpMap
	{
		int _size;
		WeakReference<int[]> _tok = new WeakReference<>(null);
		
		SmplBusGrpMap(int size)
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

	float[] _bkv, _bkvo, _vm, _vmo, _va, _vao;
	int[] _area;
	
	public static final BusList Empty = new BusList();

	BusList(){super();}
	
	protected BusList(PALists model, int[] keys)
	{
		super(model, keys, new SmplBusGrpMap(keys.length));
	}
	
	protected BusList(PALists model, int size)
	{
		super(model, new SmplBusGrpMap(size));
	}

	@Override
	public Bus get(int index)
	{
		return new Bus(this, index);
	}
	
	@Override
	public float getBaseKV(int ndx)
	{
		return _bkv[ndx];
	}

	public float[] getBaseKV()
	{
		return _bkv;
	}

	@Override
	public void setBaseKV(int ndx, float kv)
	{
		if (_bkvo == null && _bkv != null)
			_bkvo = _bkv.clone();
		_bkv[ndx] = kv;
	}

	public void setBaseKV(float[] kv)
	{
		if (_bkv != kv)
		{
			if (_bkvo == null)
				_bkvo = _bkv;
			_bkv = kv;
		}
	}
	
	@Override
	public float getVM(int ndx)
	{
		return _vm[ndx];
	}

	public float[] getVM()
	{
		return _vm;
	}

	@Override
	public void setVM(int ndx, float vm)
	{
		if (_vmo == null && _vm != null)
			_vmo = _vm.clone();
		_vm[ndx] = vm;
	}

	public void setVM(float[] vm)
	{
		if (_vm != vm)
		{
			if (_vmo == null)
				_vmo = _vm;
			_vm = vm;
		}
	}
	
	@Override
	public float getVA(int ndx)
	{
		return _va[ndx];
	}

	public float[] getVA()
	{
		return _va;
	}

	@Override
	public void setVA(int ndx, float va)
	{
		if (_vao == null && _va != null) 
			_vao = _va.clone();
		_va[ndx] = va;
	}
	
	public void setVA(float[] va)
	{
		if (_va != va)
		{
			if (_vao == null)
				_vao = va;
			_va = va;
		}
	}
	@Override
	public Area getArea(int ndx)
	{
		//TODO: 
		return null;
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
	public void setArea(int ndx, Area a)
	{
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setStation(int ndx, Station s)
	{
		// TODO Auto-generated method stub
		
	}
}
