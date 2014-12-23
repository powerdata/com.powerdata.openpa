package com.powerdata.openpa;

/**
 * Base of OpenAPI object hierarchy
 * @author chris@powerdata.com
 *
 */
public interface BaseObject extends BaseObjectCore
{
	/** get unique String object identifier */
	String getID()throws PAModelException;
	/** set unique String object identifier */
	void setID(String id)throws PAModelException;
	/** get object name */
	String getName()throws PAModelException;
	/** set object name */
	void setName(String name)throws PAModelException;
	/** unique object integer identifier */
	int getKey();
	/** return the list */
	BaseList<? extends BaseObject> getList();
}
