package com.powerdata.openpa.psse;

import java.util.AbstractList;
import java.util.HashMap;
import com.powerdata.openpa.psse.util.HashKeyFinder;
import com.powerdata.openpa.psse.util.KeyFinder;

/**
 * Start of the object list hierarchy
 * 
 * @author chris@powerdata.com
 *
 * @param <T>
 */

public abstract class BaseList<T extends BaseObject> extends AbstractList<T> 
{
	protected KeyFinder _kf = null;
	protected HashMap<String,Integer> _idToNdx = new HashMap<String,Integer>();
	
	/** Get a unique identifier for the object */
	public abstract String getObjectID(int ndx) throws PsseModelException;
	/** return a persistent key for this object */
	public abstract long getKey(int ndx) throws PsseModelException;
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
	/** get an object by it's key */
	public T getByKey(long key) throws PsseModelException
	{
		int n = kf().findNdx(key);
		return (n == -1) ? null : get(n);
	}
	protected KeyFinder kf() throws PsseModelException
	{
		if (_kf == null)
		{
			int n = size();
			_kf = new HashKeyFinder(n);
			for(int i=0; i < n; ++i) _kf.map(getKey(i));
		}
		return _kf;
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
	@Deprecated
	public int getRootIndex(int ndx) {return ndx;}
	
	public void cleanup() throws PsseModelException{}
}
