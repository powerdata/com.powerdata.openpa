package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.BaseObject;

public class Load extends BaseObject
{
	protected LoadList<?> _list;
	
	public Load(int ndx, LoadList<?> list)
	{
		super(ndx);
		_list = list;
	}

	@Override
	public String getObjectID() {return _list.getObjectID(_ndx);}

	/* raw PSS/e methods */
	
}
