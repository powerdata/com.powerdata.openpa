package com.powerdata.openpa.psse;


public interface BaseObject
{
	public static final BaseObject Null = new BaseObject()
	{
		@Override
		public int getIndex() {return -1;}
		@Override
		public String getObjectID() throws PsseModelException {return "";}
		@Override
		public String getObjectName() throws PsseModelException {return "";}
		@Override
		public String getDebugName() throws PsseModelException {return "";}
		@Override
		public String getFullName() throws PsseModelException {return "";}
		@Override
		@Deprecated
		public int getRootIndex() {return getIndex();}
		@Override
		public long getKey() {return -1;}
	};
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
	@Deprecated /* remove, use list lookup by key and get index */
	public int getRootIndex();
	public long getKey() throws PsseModelException;
}
