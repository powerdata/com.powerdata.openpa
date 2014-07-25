package com.powerdata.openpa.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.powerdata.openpa.BaseObject;
import com.powerdata.openpa.ColumnMeta;
import com.powerdata.openpa.PAModelException;

/**
 * Base of the object list hierarchy
 * 
 * @author chris@powerdata.com
 * 
 */

public abstract class AbstractPAList<T extends BaseObject> extends AbstractBaseList<T> 
{
	protected PAModelI _model;

	abstract class Data
	{
		ColumnMeta _ctype;
		boolean _nochg = true, _notld = true;

		Data(ColumnMeta coltype)
		{
			_ctype = coltype;
			registerColumn(this);
		}
		ColumnMeta getColMeta() {return _ctype;}
		void clear() {_nochg = true;}
		abstract ColChange createChangeObj();
		void setChange()
		{
			if (_nochg && _ctype != null)
			{
				_model.setChange(createChangeObj());
				_nochg = false;
			}			
		}
		abstract int[] computeChanges();
		public int[] getKeys(int[] ndxs)
		{
			return AbstractPAList.this.getKeys(ndxs);
		}
		
	}
	
	abstract class IntDataIfc extends Data
	{
		IntDataIfc(ColumnMeta coltype) {super(coltype);}
		abstract int[] getInts(int[] ndxs);
	}
	
	protected class IntData extends IntDataIfc
	{
		int[] rw, ro;
		
		IntData(ColumnMeta coltype)
		{
			super(coltype);
		}

		int[] load() throws PAModelException
		{
			_notld = false;
			return _model.load(getListMeta(), getColMeta(), _keys);
		}

		@Override
		void clear() {super.clear(); ro = null;}

		@Override
		ColChange createChangeObj()
		{
			return new IntColChange(getListMeta(), this);
		}
		
		int get(int ndx) throws PAModelException
		{
			if (_notld) rw = load();
			return rw[ndx];
		}
		
		void set(int ndx, int s) throws PAModelException
		{
			if (_notld) rw = load();
			if (_nochg)
			{
				ro = rw.clone();
				setChange();
			}
			rw[ndx] = s;
		}
		int[] get() throws PAModelException
		{
			if (_notld) rw = load();
			if (_nochg)
			{
				ro = rw.clone();
				setChange();
			}
			return rw;
		}
		
		void set(int[] v) throws PAModelException
		{
			if (v != rw)
			{
				if (_notld) ro = load();
				rw = v.clone();
			}
		}

		@Override
		int[] computeChanges()
		{
			if (ro == null) return new int[0];
			int[] all = new int[_size];
			int cnt = 0;
			for(int i=0; i < _size; ++i)
			{
				if (ro[i] != rw[i])
					all[cnt++] = i;
			}
			return Arrays.copyOf(all, cnt);
		}

		int[] getInts(int[] ndxs)
		{
			int n = ndxs.length;
			int[] rv = new int[n];
			for(int i=0; i < n; ++i)
				rv[i] = rw[ndxs[i]];
			return rv;
		}
	}
	
	protected class FloatData extends Data
	{
		float[] rw, ro;
		
		protected FloatData(ColumnMeta coltype)
		{
			super(coltype);
		}
		protected float[] load() throws PAModelException
		{
			_notld = false;
			return _model.load(getListMeta(), getColMeta(), _keys);
		}

		@Override
		void clear() {super.clear(); ro = null;}
		@Override
		ColChange createChangeObj()
		{
			return new FloatColChange(getListMeta(), this);
		}
		
		float get(int ndx) throws PAModelException
		{
			if (_notld) rw = load();
			return rw[ndx];
		}
		
		
		void set(int ndx, float s) throws PAModelException
		{
			if (_notld) rw = load();
			if (_nochg)
			{
				ro = rw.clone();
				setChange();
			}
			rw[ndx] = s;
		}
		float[] get() throws PAModelException
		{
			if (_notld) rw = load();
			if (_nochg)
			{
				ro = rw.clone();
				setChange();
			}
			return rw;
		}
		
		void set(float[] v) throws PAModelException
		{
			if (v != rw)
			{
				if (_notld) ro = load();
				rw = v.clone();
			}
		}
		
		@Override
		int[] computeChanges()
		{
			if (ro == null) return new int[0];
			int[] all = new int[_size];
			int cnt = 0;
			for(int i=0; i < _size; ++i)
			{
				if (ro[i] != rw[i])
					all[cnt++] = i;
			}
			return Arrays.copyOf(all, cnt);
		}

		float[] getFloats(int[] ndxs)
		{
			int n = ndxs.length;
			float[] rv = new float[n];
			for(int i=0; i < n; ++i)
				rv[i] = rw[ndxs[i]];
			return rv;
		}

	}

	protected class BoolData extends Data
	{
		boolean[] rw, ro;
		
		BoolData(ColumnMeta coltype)
		{
			super(coltype);
		}
		boolean[] load() throws PAModelException
		{
			_notld = false;
			return _model.load(getListMeta(), getColMeta(), _keys);
		}

		@Override
		void clear() {super.clear(); ro = null;}
		@Override
		ColChange createChangeObj()
		{
			return new BoolColChange(getListMeta(), this);
		}
		
		boolean get(int ndx) throws PAModelException
		{
			if (_notld) rw = load();
			return rw[ndx];
		}
		
		void set(int ndx, boolean s) throws PAModelException
		{
			if (_notld) rw = load();
			if (_nochg)
			{
				ro = rw.clone();
				setChange();
			}
			rw[ndx] = s;
		}
		boolean[] get() throws PAModelException
		{
			if (_notld) rw = load();
			if (_nochg)
			{
				ro = rw.clone();
				setChange();
			}
			return rw;
		}
		
		void set(boolean[] v) throws PAModelException
		{
			if (v != rw)
			{
				if (_notld) ro = load();
				rw = v.clone();
			}
		}

		@Override
		int[] computeChanges()
		{
			if (ro == null) return new int[0];
			int[] all = new int[_size];
			int cnt = 0;
			for(int i=0; i < _size; ++i)
			{
				if (ro[i] != rw[i])
					all[cnt++] = i;
			}
			return Arrays.copyOf(all, cnt);
		}
		
		boolean[] getBools(int[] ndxs)
		{
			int n = ndxs.length;
			boolean[] rv = new boolean[n];
			for(int i=0; i < n; ++i)
				rv[i] = rw[ndxs[i]];
			return rv;
		}

	}
	
	protected class StringData extends Data
	{
		String[] rw, ro;
		
		StringData(ColumnMeta coltype)
		{
			super(coltype);
		}
		String[] load() throws PAModelException
		{
			_notld = false;
			return _model.load(getListMeta(), getColMeta(), _keys);
		}
		@Override
		void clear() {super.clear(); ro = null;}
		@Override
		ColChange createChangeObj()
		{
			return new StringColChange(getListMeta(), this);
		}
		
		String get(int ndx) throws PAModelException
		{
			if (_notld) rw = load();
			return rw[ndx];
		}

		void set(int ndx, String s) throws PAModelException
		{
			if (_notld) rw = load();
			if (_nochg)
			{
				ro = rw.clone();
				setChange();
			}
			rw[ndx] = s;
		}

		String[] get() throws PAModelException
		{
			if (_notld) rw = load();
			if (_nochg)
			{
				ro = rw.clone();
				setChange();
			}
			return rw;
		}
		
		void set(String[] v) throws PAModelException
		{
			if (v != rw)
			{
				if (_notld) ro = load();
				rw = v.clone();
			}
		}
		
		@Override
		int[] computeChanges()
		{
			if (ro == null) return new int[0];
			int[] all = new int[_size];
			int cnt = 0;
			for(int i=0; i < _size; ++i)
			{
				if (!ro[i].equals(rw[i]))
					all[cnt++] = i;
			}
			return Arrays.copyOf(all, cnt);
		}
		String[] getStrings(int[] ndxs)
		{
			int n = ndxs.length;
			String[] rv = new String[n];
			for(int i=0; i < n; ++i)
				rv[i] = rw[ndxs[i]];
			return rv;

		}

	}
	
	protected class EnumData<E extends Enum<?>> extends IntDataIfc
	{
		E[] rw, ro;
		
		EnumData(ColumnMeta coltype)
		{
			super(coltype);
		}

		E[] load() throws PAModelException
		{
			_notld = false;
			return _model.load(getListMeta(), _ctype, _keys);
		}

		@Override
		void clear() {super.clear(); ro = null;}
		@Override
		ColChange createChangeObj()
		{
			return new IntColChange(getListMeta(), this);
		}
		
		E get(int ndx) throws PAModelException
		{
			if (_notld) rw = load();
			return rw[ndx];
		}

		void set(int ndx, E s) throws PAModelException
		{
			if (_notld) rw = load();
			if (_nochg)
			{
				ro = rw.clone();
				setChange();
			}
			rw[ndx] = s;
		}

		E[] get() throws PAModelException
		{
			if (_notld) rw = load();
			if (_nochg)
			{
				ro = rw.clone();
				setChange();
			}
			return rw;
		}
		
		void set(E[] v) throws PAModelException
		{
			if (v != rw)
			{
				if (_notld) ro = load();
				rw = v.clone();
			}
		}
		@Override
		int[] computeChanges()
		{
			if (ro == null) return new int[0];
			int[] all = new int[_size];
			int cnt = 0;
			for(int i=0; i < _size; ++i)
			{
				if (!ro[i].equals(rw[i]))
					all[cnt++] = i;
			}
			return Arrays.copyOf(all, cnt);
		}
		
		@Override
		int[] getInts(int[] ndxs)
		{
			int n = ndxs.length;
			int[] rv = new int[n];
			for(int i=0; i < n; ++i)
				rv[i] = rw[ndxs[i]].ordinal();
			return rv;
		}
	}

	/** interface used to let superclass manage appropriate data but
	 * be correctly typed for leaf class
	 */
	protected interface PAListEnum
	{
		ColumnMeta id();
		ColumnMeta name();
	}
	
	protected StringData _id, _name;

	List<Data> _fields = new ArrayList<>();

	protected AbstractPAList(PAModelI model, int size, PAListEnum le)
	{
		super(size);
		_model = model;
		setFields(le);
	}
	
	protected AbstractPAList(PAModelI model, int[] keys, PAListEnum le)
	{
		super(keys);
		_model = model;
		setFields(le);
	}

	private void setFields(PAListEnum le)
	{
		_id = new StringData(le.id());
		_name = new StringData(le.name());
	}
	
	protected AbstractPAList()
	{
		_size = 0;
	}

	@Override
	public int size()
	{
		return _size;
	}
	
	protected void registerColumn(Data d)
	{
		_fields.add(d);
	}
	
	/** get unique object ID */
	@Override
	public String getID(int ndx) throws PAModelException
	{
		return _id.get(ndx);
	}

	/** set unique object ID */
	@Override
	public void setID(int ndx, String id) throws PAModelException
	{
		_id.set(ndx, id);
	}

	/** return array of string object ID's */
	@Override
	public String[] getID() throws PAModelException
	{
		return _id.get();
	}
	
	/** set unique object ID */
	@Override
	public void setID(String[] id) throws PAModelException
	{
		_id.set(id);
	}

	/** name of object */
	@Override
	public String getName(int ndx) throws PAModelException
	{
		return _name.get(ndx);
	}
	/** set name of object */
	@Override
	public void setName(int ndx, String name) throws PAModelException
	{
		_name.set(ndx, name);
	}
	/** name of object */
	@Override
	public String[] getName() throws PAModelException
	{
		return _name.get();
	}
	/** set name of object */
	@Override
	public void setName(String[] name) throws PAModelException
	{
		_name.set(name);
	}

	public int[] getKeys(int[] offsets)
	{
		int n = offsets.length;
		int[] rv = new int[n];
		for(int i=0; i < n; ++i)
			rv[i] = getKey(offsets[i]);
		return rv;
	}

}
