package com.powerdata.openpa.tools;

import com.powerdata.openpa.psse.PsseModelException;

public abstract class BaseObject
{
	protected int _ndx;
	
	public BaseObject(int ndx)
	{
		_ndx = ndx;
	}

	public int getIndex() {return _ndx;}
	public abstract String getObjectID() throws PsseModelException;
	

	@Override
	public String toString()
	{
		try
		{
		StringBuilder sb = new StringBuilder(String.format("%s[%d](%s) ",getClass().getSimpleName(),
				_ndx,getObjectID()));
			sb.append(getDebugName());
			return sb.toString();
		} catch (Exception e) {return "err";}
	}
	
	public String getDebugName() throws PsseModelException {return "";}

}
