package com.powerdata.openpa;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Set;

public interface BaseList<T extends BaseObject> extends List<T> 
{
	int getKey(int ndx);
	
	int[] getKeys();

	String getID(int ndx) throws PAModelException;

	/** return array of string object ID's */
	String[] getID() throws PAModelException;

	/** set unique object ID PAModelException */
	void setID(String[] id) throws PAModelException;

	void setID(int ndx, String id) throws PAModelException;

	String getName(int ndx) throws PAModelException;

	void setName(int ndx, String name) throws PAModelException;

	/** name of object */
	String[] getName() throws PAModelException;

	/** set name of object  */
	void setName(String[] name) throws PAModelException;

	T getByKey(int key);
	
	T getByID(String id) throws PAModelException;

	ListMetaType getListMeta();

	int getIndex(int ndx);

	boolean objEquals(int ndx, Object obj);

	int objHash(int ndx);

	public static int CalcListHash(ListMetaType t, int key)
	{
		return 400009 * t.ordinal() + key;
	}

	Set<ColumnMeta> getColTypes();

	default void reset(){}
	
	default int[] getIndexesFromIDs(String[] ids) throws PAModelException
	{
		int n = ids.length;
		int[] rv = new int[n];
		for(int i=0; i < n; ++i)
		{
			rv[i] = getByID(ids[i]).getIndex();
		}
		return rv;
	}
	
	default int[] getIndexesFromKeys(int[] keys) throws PAModelException
	{
		int n = keys.length;
		int[] rv = new int[n];
		for(int i=0; i < n; ++i)
		{
			rv[i] = getByKey(keys[i]).getIndex();
		}
		return rv;
	}
	
	default T[] toArray(int[] indexes)
	{
		int n = indexes.length;
		Class<?> clt = get(0).getClass();
		@SuppressWarnings("unchecked")
		T[] rv = (T[]) Array.newInstance(clt, n);
		for(int i=0; i < n; ++i)
		{
			rv[i] = get(indexes[i]);
		}
		
		return rv;
	}

	default int[] getIndexes(T[] objects)
	{
		int n = objects.length;
		int[] rv = new int[n];
		for(int i=0; i < n; ++i)
		{
			rv[i] = objects[i].getIndex();
		}
		return rv;
	}
}
