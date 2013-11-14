package com.powerdata.openpa.tools;

import com.powerdata.openpa.psse.PsseModelException;

public interface BaseObject
{
	public int getIndex();
	/** get unique object identifier */
	public String getObjectID() throws PsseModelException;
	/** get object name */
	public String getObjectName() throws PsseModelException;
	/** return a name suitable for debugging */
	public String getDebugName() throws PsseModelException;
	/** return the name of the object with additional context to aid recognition */
	public String getFullName() throws PsseModelException;
	/** get index from main list.  If a sublist, this can be different from getIndex */
	public int getRootIndex();
}
