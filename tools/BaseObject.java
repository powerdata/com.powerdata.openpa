package com.powerdata.openpa.tools;

public abstract class BaseObject
{
	protected int _ndx;
	
	public BaseObject(int ndx)
	{
		_ndx = ndx;
	}

	public int getIndex() {return _ndx;}
	public abstract String getObjectID();
	

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder(String.format("%s[%d](%s) ",getClass().getSimpleName(),
				_ndx,getObjectID()));
		try
		{
			sb.append(getDebugName());
		} catch (Exception e) {}
		return sb.toString();
	}
	
	public String getDebugName() throws Exception {return "";}

}
