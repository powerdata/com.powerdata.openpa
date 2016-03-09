package com.powerdata.openpa.impl;

import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TObjectIntHashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.powerdata.openpa.BaseObject;
import com.powerdata.openpa.ColumnMeta;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.tools.SNdxKeyOfs;

/**
 * Base of the object list hierarchy
 * 
 * @author chris@powerdata.com
 * 
 */

public abstract class AbstractPAList<T extends BaseObject> extends AbstractBaseList<T> 
{
	protected PAModelI _model;
	protected SNdxKeyOfs _keyndx;
	private int[] _keys;
	protected KeyMgr _km;
	TObjectIntMap<String> _idMap;
	
	interface KeyMgr
	{
		int key(int ndx);
		int[] keys();
	}
	
	class NoKeyMgr implements KeyMgr
	{
		@Override
		public int[] keys()
		{
			int[] rv = new int[_size];
			for(int i=0; i < _size; ++i) rv[i] = i+1;
			return rv;
		}

		@Override
		public int key(int ndx)
		{
			return ndx+1;
		}
	}
	
	class YesKeyMgr implements KeyMgr
	{
		@Override
		public int key(int ndx)
		{
			return _keys[ndx];
		}

		@Override
		public int[] keys()
		{
			return _keys;
		}
	}

	class SNdxNoKey extends SNdxKeyOfs
	{
		@Override
		public int size() {return _size;}

		@Override
		public boolean containsKey(int key)
		{
			return key > 0 && key <= _size;
		}

		@Override
		public int getOffset(int key)
		{
			return key-1;
		}

		@Override
		public int[] getOffsets(int[] keys)
		{
			int size = keys.length;
			int[] rv = new int[size];
			for(int i=0; i < size; ++i) rv[i] = keys[i]-1;
			return rv;
		}

	};

	
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
			int n = ndxs.length;
			int[] rv = new int[n];
			for(int i=0; i < n; ++i)
				rv[i] = getKey(ndxs[i]);
			return rv;
		}
		abstract void reset();
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
			return _model.load(getListMeta(), getColMeta(), AbstractPAList.this.getKeys());
		}

		@Override
		void clear() {super.clear(); ro = null;}
		
		@Override
		void reset() { rw = null; ro = null;  _notld = true;}

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
			return _model.load(getListMeta(), getColMeta(), AbstractPAList.this.getKeys());
		}

		@Override
		void clear() {super.clear(); ro = null;}		
		@Override
		void reset() { rw = null; ro = null; _notld = true;}
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
			return _model.load(getListMeta(), getColMeta(), AbstractPAList.this.getKeys());
		}

		@Override
		void clear() {super.clear(); ro = null;}		
		@Override
		void reset() { rw = null; ro = null; _notld = true;}
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
			return _model.load(getListMeta(), getColMeta(), AbstractPAList.this.getKeys());
		}
		@Override
		void clear() {super.clear(); ro = null;}		
		@Override
		void reset() { rw = null; ro = null; _notld = true;}
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
		
		public EnumData(ColumnMeta coltype)
		{
			super(coltype);
		}

		E[] load() throws PAModelException
		{
			_notld = false;
			
			return _model.load(getListMeta(), _ctype, AbstractPAList.this.getKeys());
		}

		@Override
		void clear() {super.clear(); ro = null;}		
		@Override
		void reset() { rw = null; ro = null; _notld = true;}
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
		_km = new NoKeyMgr();
		setFields(le);
	}
	
	protected AbstractPAList(PAModelI model, int[] keys, PAListEnum le)
	{
		super(keys.length);
		_model = model;
		_keys = keys;
		_km = new YesKeyMgr();
		setFields(le);
	}

	private void setFields(PAListEnum le)
	{
		_id = new StringData((le == null)?null:le.id());
		_name = new StringData((le == null)?null:le.name());
	}
	
	protected AbstractPAList()
	{
		super();
		_km = new NoKeyMgr();
	}
	
	protected AbstractPAList(int size)
	{
		super(size);
	}

	protected AbstractPAList(int[] keys)
	{
	}
	
	protected void registerColumn(Data d)
	{
		_fields.add(d);
	}
	/**
	 * This function is used by the refresh function to make sure 
	 * any data access from now on will have just "new" data
	 */
	@Override
	public void reset()
	{
		// clear all the columns
		for(Data d : _fields) { d.reset(); }
	}
	
	/** Set up keys in the event that the "key" constructor isn't used */
	protected void setupKeys(int[] keys)
	{
		_size = keys.length;
		_keys = keys;
		_km = new YesKeyMgr();
	}

	@Override
	@Nodump
	public T getByKey(int key)
	{
		if (_keyndx == null) mkKeyNdx();
		return get(_keyndx.getOffset(key));
	}
	
	void mkKeyNdx()
	{
		_keyndx = (_keys == null) ? new SNdxNoKey() : SNdxKeyOfs.Create(_keys);
	}

	/** get unique object key */
	@Override
	public int getKey(int ndx)
	{
		return _km.key(ndx);
	}
	
	@Override
	public T getByID(String id) throws PAModelException 
	{
		if(_idMap == null) mapIDs();
		return get(_idMap.get(id));
	}
	
	void mapIDs() throws PAModelException
	{
		String[] ids = getID();
		_idMap = new TObjectIntHashMap<>(_size);
		for(int i = 0; i < _size; ++i)
		{
			_idMap.put(ids[i], i);
		}
	}

	@Override
	public int[] getKeys()
	{
		return _km.keys();
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

	@Override
	public int getIndex(int ndx)
	{
		return ndx;
	}

	public int[] getIndexesFromKeys(int[] keys)
	{
		if (_keyndx == null) mkKeyNdx();
		return _keyndx.getOffsets(keys);
	}

//	public int[] getIndexesFromIDs(String[] ids) throws PAModelException
//	{
//		int size = ids.length;
//		int[] indexes = new int[size];
//		
//		if (_idMap == null) mapIDs();
//		
//		for(int i = 0; i < size; ++i)
//		{
////			System.out.println("\n------\nid: "+ids[i]);
////			System.out.println("_idMap: "+_idMap.get(ids[i]));
//			indexes[i] = _idMap.get(ids[i]);
//		}
//		
//		return indexes;
//	}
//	
//	public T[] toArray(int[] indexes)
//	{
//		T[] us = newArray(_size);
//		for(int i=0; i < _size; ++i)
//			us[i] = get(i);
//		int n = indexes.length;
//		T[] rv = newArray(n);
//		for(int i=0; i < n; ++i)
//			rv[i] = us[indexes[i]];
//		return rv;
//	}
//	
//	@SuppressWarnings("unchecked")
//	T[] newArray(int size)
//	{
//		return (T[]) Array.newInstance(get(0).getClass(), size);
//	}
//
}
