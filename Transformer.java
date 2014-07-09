package com.powerdata.openpa;

public class Transformer extends TransformerBase
{
	TransformerList _list;
	
	public Transformer(TransformerList list, int ndx)
	{
		super(list, ndx);
		_list = list;
	}
	
	/** get from-side off-nominal tap ratio p.u. on 100MVA base and bus base KV */
	@Override
	public float getFromTap()
	{
		return _list.getFromTap(_ndx);
	}
	/** set from-side off-nominal tap ratio p.u. on 100MVA base and bus base KV */
	@Override
	public void setFromTap(float a)
	{
		_list.setFromTap(_ndx, a);
	}
	/** get to-side off-nominal tap ratio p.u on 100MVA base and bus base KV */
	@Override
	public float getToTap()
	{
		return _list.getToTap(_ndx);
	}
	/** set to-side off-nominal tap ratio p.u on 100MVA base and bus base KV */
	@Override
	public void setToTap(float a)
	{
		_list.setToTap(_ndx, a);
	}
}