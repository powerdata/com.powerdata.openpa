package com.powerdata.openpa.tools;

import com.powerdata.openpa.psse.PsseModelException;

public abstract class BaseObject
{
	protected int _ndx;
	protected BaseList<?> _list;
	
	public BaseObject(BaseList<?> list, int ndx)
	{
		_list = list;
		_ndx = ndx;
	}

	public int getIndex() {return _ndx;}
	public String getObjectID() throws PsseModelException
	{
		return _list.getObjectID(_ndx);
	}
	public String getObjectName() throws PsseModelException
	{
		return _list.getObjectName(_ndx);
	}

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
	
	public String getDebugName() throws PsseModelException {return getObjectID();}

}
