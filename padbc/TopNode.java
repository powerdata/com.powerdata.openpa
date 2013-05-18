package com.powerdata.openpa.padbc;

public abstract class TopNode extends BaseObject implements Container
{
	private TopNodeList _list;
	
	public TopNode(int ndx, TopNodeList list)
	{
		super(ndx);
		_list = list;
	}
	
	@Override
	public String getID() {return _list.getID(getIndex());}
}
