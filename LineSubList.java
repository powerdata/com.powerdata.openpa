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
	public float getFromTap(int ndx)
	{
		return _src.getFromTap(_ndx[ndx]);
	}

	@Override
	public float[] getFromTap()
	{
		return _src.getSubListFromTap(_ndx);
	}

	@Override
	public void setFromTap(int ndx, float a)
	{
		_src.setFromTap(_ndx[ndx], a);
	}

	@Override
	public void setFromTap(float[] a)
	{
		_src.setSubListFromTap(a, _ndx);
	}

	@Override
	public float getToTap(int ndx)
	{
		return _src.getToTap(_ndx[ndx]);
	}

	@Override
	public float[] getToTap()
	{
		return _src.getSubListToTap(_ndx);
	}

	@Override
	public void setToTap(int ndx, float a)
	{
		_src.setToTap(_ndx[ndx], a);
	}

	@Override
	public void setToTap(float[] a)
	{
		_src.setSubListToTap(a, _ndx);
	}

	@Override
	public float getGmag(int ndx)
	{
		return _src.getGmag(_ndx[ndx]);
	}

	@Override
	public float[] getGmag()
	{
		return _src.getSubListGmag(_ndx);
	}

	@Override
	public void setGmag(int ndx, float g)
	{
		_src.setGmag(_ndx[ndx], g);
	}

	@Override
	public void setGmag(float[] g)
	{
		_src.setSubListGmag(g, _ndx);
		
	}

	@Override
	public float getBmag(int ndx)
	{
		return _src.getBmag(_ndx[ndx]);
	}

	@Override
	public float[] getBmag()
	{
		return _src.getSubListBmag(_ndx);
	}

	@Override
	public void setBmag(int ndx, float b)
	{
		_src.setBmag(_ndx[ndx], b);
	}

	@Override
	public void setBmag(float[] b)
	{
		_src.setSubListBmag(b, _ndx);
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
	public float getShift(int ndx)
	{
		return _src.getShift(_ndx[ndx]);
	}

	@Override
	public float[] getShift()
	{
		return _src.getSubListShift(_ndx);
	}

	@Override
	public void setShift(int ndx, float sdeg)
	{
		_src.setShift(_ndx[ndx], sdeg);
	}

	@Override
	public void setShift(float[] sdeg)
	{
		_src.setSubListShift(sdeg, _ndx);
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
