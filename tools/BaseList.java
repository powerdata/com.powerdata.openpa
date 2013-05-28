package com.powerdata.openpa.tools;

import java.util.AbstractList;
import java.util.HashMap;

public abstract class BaseList<T extends BaseObject> extends AbstractList<T> 
{
	HashMap<String,Integer> _idToNdx = new HashMap<String,Integer>();
	
	/** Get a unique identifier for the object */
	public abstract String getObjectID(int ndx);
	/** Get an object by it's ID */
	public T get(String objectid)
	{
		Integer ndx = _idToNdx.get(objectid);
		return (ndx != null)?get(ndx):null;
	}
	/** Reindex the objectID to ndx mapping. */
	protected void reindex()
	{
		HashMap<String,Integer> idToNdx = new HashMap<String,Integer>();
		int count = this.size();
		for(int i=0; i<count; i++) idToNdx.put(getObjectID(i), i);
		_idToNdx = idToNdx;
	}
	
	public abstract StringAttrib<T>  mapStringAttrib(String attribname);
	public abstract FloatAttrib<T>   mapFloatAttrib(String attribname);
	public abstract IntAttrib<T>     mapIntAttrib(String attribname);
	public abstract BooleanAttrib<T> mapBooleanAttrib(String attribname);
	
}
