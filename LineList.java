package com.powerdata.openpa;

public class LineList extends ACBranchList<Line>
{
	public static final LineList	Empty	= new LineList();
	
	protected float[] _fbch, _fbcho, _tbch, _tbcho;
	
	protected LineList(){super();}
	
	public LineList(PALists model, int[] keys, int[] fbkey, int[] tbkey)
	{
		super(model, keys, fbkey, tbkey);
	}
	protected LineList(PALists model, int size, int[] fbkey, int[] tbkey)
	{
		super(model, size, fbkey, tbkey);
	}

	@Override
	public Line get(int index)
	{
		return new Line(this, index);
	}
	
	/** get from-side charging susceptance */
	public float getFromBchg(int ndx)
	{
		return _fbch[ndx];
	}
	/** get from-side charging susceptance */
	public float[] getFromBchg()
	{
		return _fbch;
	}
	/** set from-side charging susceptance */
	public void setFromBchg(int ndx, float b)
	{
		if (_fbcho == null && _fbch != null)
			_fbcho = _fbch.clone();
		_fbch[ndx] = b;
	}
	/** set from-side charging susceptance */
	public void setFromBchg(float[] b)
	{
		if (_fbch != b)
		{
			if (_fbcho == null)
				_fbcho = _fbch;
			_fbch = b;
		}
	}
	/** get to-side charging susceptance */
	public float getToBchg(int ndx)
	{
		return _tbch[ndx];
	}
	/** get to-side charging susceptance */
	public float[] getToBchg()
	{
		return _tbch;
	}
	/** set to-side charging susceptance */
	public void setToBchg(int ndx, float b)
	{
		if (_tbcho == null && _tbch != null)
			_tbcho = _tbch.clone();
		_tbch[ndx] = b;
	}
	/** set to-side charging susceptance */
	public void setToBchg(float[] b)
	{
		if (_tbch != b)
		{
			if (_tbcho == null)
				_tbcho = _tbch;
			_tbch = b;
		}
	}
	/** from-side Bch access for "sublist" subclasses to use */
	protected float[] getSubListFromBch(int[] slndx)
	{
		int n = size();
		float[] rv = new float[n];
		for(int i=0; i < n; ++i)
			rv[i] = getFromBchg(slndx[i]);
		return rv;
	}
	/** from-side Bch access for "sublist" subclasses to use */
	protected void setSubListFromBch(float[] b, int[] slndx)
	{
		int n = size();
		for(int i=0; i < n; ++i)
			setFromBchg(slndx[i], b[i]);
	}
	/** from-side Bch access for "sublist" subclasses to use */
	protected float[] getSubListToBch(int[] slndx)
	{
		int n = size();
		float[] rv = new float[n];
		for(int i=0; i < n; ++i)
			rv[i] = getToBchg(slndx[i]);
		return rv;
	}
	/** from-side Bch access for "sublist" subclasses to use */
	protected void setSubListToBch(float[] b, int[] slndx)
	{
		int n = size();
		for(int i=0; i < n; ++i)
			setToBchg(slndx[i], b[i]);
	}
}
