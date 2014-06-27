package com.powerdata.openpa;

public class LineSubList extends LineList
{

	int[] _ndx;
	LineList _src;
	
	protected LineSubList(PALists model, LineList src, int[] srcndx)
	{
		super(model, srcndx.length, src.getFBusKeys(srcndx),
				src.getTBusKeys(srcndx));
		_ndx = srcndx;
		_src = src;
	}

	@Override
	public float getR(int ndx)
	{
		return _src.getR(_ndx[ndx]);
	}

	@Override
	public float[] getR()
	{
		return _src.getSubListR(_ndx);
	}

	@Override
	public void setR(int ndx, float r)
	{
		_src.setR(_ndx[ndx], r);
	}

	@Override
	public void setR(float[] r)
	{
		_src.setSubListR(r, _ndx);
	}

	@Override
	public float getX(int ndx)
	{
		return _src.getX(_ndx[ndx]);
	}

	@Override
	public float[] getX()
	{
		return _src.getSubListX(_ndx);
	}

	@Override
	public void setX(int ndx, float x)
	{
		_src.setX(_ndx[ndx], x);
	}

	@Override
	public void setX(float[] x)
	{
		_src.setSubListX(x, _ndx);
	}

	@Override
	public float getFromBchg(int ndx)
	{
		return _src.getFromBchg(_ndx[ndx]);
	}

	@Override
	public float[] getFromBchg()
	{
		return _src.getSubListFromBch(_ndx);
	}

	@Override
	public void setFromBchg(int ndx, float b)
	{
		_src.setFromBchg(_ndx[ndx], b);
	}

	@Override
	public void setFromBchg(float[] b)
	{
		_src.setSubListFromBch(b, _ndx);
	}

	@Override
	public float getToBchg(int ndx)
	{
		return _src.getToBchg(_ndx[ndx]);
	}

	@Override
	public float[] getToBchg()
	{
		return _src.getSubListToBch(_ndx);
	}

	@Override
	public void setToBchg(int ndx, float b)
	{
		_src.setToBchg(_ndx[ndx], b);
	}

	@Override
	public void setToBchg(float[] b)
	{
		_src.setSubListToBch(b, _ndx);
	}

	@Override
	public Bus getFromBus(int ndx)
	{
		return _src.getFromBus(_ndx[ndx]);
	}

	@Override
	public BusList getFromBuses()
	{
		return _src.getSubListFromBuses(_ndx);
	}

	@Override
	public Bus getToBus(int ndx)
	{
		return _src.getToBus(_ndx[ndx]);
	}

	@Override
	public BusList getToBuses()
	{
		return _src.getSubListToBuses(_ndx);
	}

	@Override
	public boolean isInSvc(int ndx)
	{
		return _src.isInSvc(_ndx[ndx]);
	}

	@Override
	public boolean[] isInSvc()
	{
		return _src.getSubListInSvc(_ndx);
	}

	@Override
	public void setInSvc(int ndx, boolean state)
	{
		_src.setInSvc(_ndx[ndx], state);
	}

	@Override
	public void setInSvc(boolean[] state)
	{
		_src.setSubListInSvc(state, _ndx);
	}

	@Override
	public String getID(int ndx)
	{
		return _src.getID(_ndx[ndx]);
	}

	@Override
	public void setID(int ndx, String id)
	{
		_src.setID(_ndx[ndx], id);
	}

	@Override
	public String[] getID()
	{
		return _src.getSubListIDs(_ndx);
	}

	@Override
	public void setID(String[] id)
	{
		_src.setSubListIDs(id, _ndx);
	}

	@Override
	public String getName(int ndx)
	{
		return _src.getName(_ndx[ndx]);
	}

	@Override
	public void setName(int ndx, String name)
	{
		_src.setName(_ndx[ndx], name);
	}

	@Override
	public String[] getName()
	{
		return _src.getSubListNames(_ndx);
	}

	@Override
	public void setName(String[] name)
	{
		_src.setSubListNames(name, _ndx);
	}


}
