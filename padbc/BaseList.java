package com.powerdata.openpa.padbc;

public interface BaseList
{
	/**
	 * return an identifier or name for this object which is meaningful for
	 * reporting and debugging purposes
	 */
	public abstract String getID(int ndx);
	
//	public abstract <T extends BaseObject> StringAttrb<T> mapStringAttrib(String attribname);
}
