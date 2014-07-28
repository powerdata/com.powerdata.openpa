package com.powerdata.openpa;

/**
 * Base of OpenAPI object hierarchy
 * @author chris@powerdata.com
 *
 */
public interface BaseObject
{
	/** get Index within master list owned by PAModel */
	public int getIndex();
	/** get unique String object identifier */
	public String getID()throws PAModelException;
	/** set unique String object identifier */
	public void setID(String id)throws PAModelException;
	/** get object name */
	public String getName()throws PAModelException;
	/** set object name */
	public void setName(String name)throws PAModelException;
	/** unique object integer identifier */
	public int getKey();
}
