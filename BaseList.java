package com.powerdata.openpa;

import java.util.List;

import com.powerdata.openpa.tools.SNdxKeyOfs;

public interface BaseList<T extends BaseObject> extends List<T> 
{
	static class SNdxNoKey extends SNdxKeyOfs
	{
		int _size;

		public SNdxNoKey(int size) {super(null);_size = size;}

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
			int[] rv = new int[_size];
			for(int i=0; i < _size; ++i) rv[i] = keys[i]-1;
			return rv;
		}

		@Override
		public int[] getKeys()
		{
			int[] rv = new int[_size];
			for(int i=0; i < _size; ++i) rv[i] = i+1;
			return rv;
		}

		@Override
		public int getKey(int ndx)
		{
			return ndx+1;
		}
	};
	
	int getKey(int ndx);
	
	int[] getKeys();

	String getID(int ndx);

	/** return array of string object ID's */
	String[] getID();

	/** set unique object ID */
	void setID(String[] id);

	void setID(int ndx, String id);

	String getName(int ndx);

	void setName(int ndx, String name);

	/** name of object */
	String[] getName();

	/** set name of object */
	void setName(String[] name);

	T getByKey(int ndx);

	T[] toArray(int[] indexes);
	
	/** convert an array of objects to array of list offsets */
	static int[] ObjectNdx(BaseObject[] objects)
	{
		int n = objects.length;
		int[] s = new int[n];
		for(int i=0; i < n; ++i) s[i] = objects[i].getIndex();
		return s;
	}
	
	int[] getIndexesFromKeys(int[] keys);

	ListMetaType getMetaType();


}
