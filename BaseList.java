package com.powerdata.openpa;

import java.util.List;

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

	ListMetaType getListMeta();


}
