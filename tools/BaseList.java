package com.powerdata.openpa.tools;

import java.util.AbstractList;
import java.util.HashMap;
import com.powerdata.openpa.psse.PsseModelException;

/**
 * Start of the object list hierarchy
 * 
 * @author chris@powerdata.com
 *
 * @param <T>
 */

public abstract class BaseList<T extends BaseObject> extends AbstractList<T> 
{
	protected HashMap<String,Integer> _idToNdx = new HashMap<String,Integer>();
	
	/** Get a unique identifier for the object */
	public abstract String getObjectID(int ndx) throws PsseModelException;
	public String getObjectName(int ndx) throws PsseModelException
	{
		return getObjectID(ndx);
	}
	public String getFullName(int ndx) throws PsseModelException
	{
		return getObjectName(ndx);
	}
	public String getDebugName(int ndx) throws PsseModelException
	{
		return getFullName(ndx);
	}
	
	public HashMap<String,Integer> idmap() {return _idToNdx;}
	
	/** Get an object by it's ID */
	public T get(String objectid)
	{
		Integer ndx = _idToNdx.get(objectid);
		return (ndx != null)?get(ndx):null;
	}
	/** Reindex the objectID to ndx mapping. 
	 * @throws PsseModelException */
	protected void reindex() throws PsseModelException
	{
		HashMap<String,Integer> idToNdx = new HashMap<String,Integer>();
		int count = this.size();
		for(int i=0; i<count; i++) idToNdx.put(getObjectID(i), i);
		_idToNdx = idToNdx;
	}
	
}
