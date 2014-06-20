package com.powerdata.openpa;

/**
 * Base of OpenAPI object hierarchy
 * @author chris@powerdata.com
 *
 */
public interface BaseObject
{
	/** get Index within associated list */
	public int getIndex();
	/** get unique String object identifier */
	public String getID();
	/** set unique String object identifier */
	public void setID(String id);
	/** get object name */
	public String getName();
	/** FPC, June 20, 2014 Not sure this is really necessary, if it is not we can remove it and rename all the calls */
	public String getFullName();
	/*
	 * Difference between getName and getFullName??? Does it matter?
	 */
	/** set object name */
	public void setName(String name);
	/** unique object integer identifier */
	public int getKey();
}
