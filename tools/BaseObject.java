package com.powerdata.openpa.tools;

import com.powerdata.openpa.psse.PsseModelException;

public interface BaseObject
{
	public int getIndex();
	public String getObjectID() throws PsseModelException;
	public String getObjectName() throws PsseModelException;
	public String getDebugName() throws PsseModelException;

}
