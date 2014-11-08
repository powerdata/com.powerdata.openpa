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
	


}
