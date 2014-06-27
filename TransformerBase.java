package com.powerdata.openpa;

public class TransformerBase extends ACBranch
{
	TransformerBaseList<? extends TransformerBase> _list;
	
	public TransformerBase(TransformerBaseList<? extends TransformerBase> list, int ndx)
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
	/** get transformer magnetizing conductance p.u. on 100MVA base */
	@Override
	public float getGmag()
	{
		return _list.getGmag(_ndx);
	}
	/** set transformer magnetizing conductance p.u. on 100MVA base */
	@Override
	public void setGmag(float g)
	{
		_list.setGmag(_ndx, g);
	}
	/** get transformer magnetizing susceptance p.u. on 100 MVA base */
	@Override
	public float getBmag()
	{
		return _list.getBmag(_ndx);
	}
	/** set transformer magnetizing susceptance p.u. on 100 MVA base */
	@Override
	public void setBmag(float b)
	{
		_list.setBmag(_ndx, b);
	}

}
