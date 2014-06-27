package com.powerdata.openpa;

public abstract class ACBranchList<T extends ACBranch> extends TwoTermDevList<T>
{
	float[]	_r, _ro, _x, _xo;
	
	protected ACBranchList(){super();}
	
	protected ACBranchList(PALists model, int[] keys, int[] fbkey, int[] tbkey)
	{
		super(model, keys, fbkey, tbkey);
	}
	protected ACBranchList(PALists model, int size, int[] fbkey, int[] tbkey)
	{
		super(model, size, fbkey, tbkey);
	}

	/** get resistance p.u. on 100 MVA and bus base kv */
	public float getR(int ndx)
	{
		return _r[ndx];
	}

	/** get resistance p.u. on 100 MVA and bus base kv */
	public float[] getR()
	{
		return _r;
	}
	/** set resistance p.u. on 100 MVA and bus base kv */
	public void setR(int ndx, float r)
	{
		if (_ro == null && _r != null)
			_ro = _r.clone();
		_r[ndx] = r;
	}
	/** set resistance p.u. on 100 MVA and bus base kv */
	public void setR(float[] r)
	{
		if (_r != r)
		{
			if (_ro == null)
				_ro = _r;
			_r = r;
		}
	}

	/** get reactance p.u. on 100MVA and bus base KV */
	public float getX(int ndx)
	{
		return _x[ndx];
	}
	/** get reactance p.u. on 100MVA and bus base KV */
	public float[] getX()
	{
		return _x;
	}
	/** set reactance p.u. on 100MVA and bus base KV */
	public void setX(int ndx, float x)
	{
		if (_xo == null && _x != null)
			_xo = _x.clone();
		_x[ndx] = x;
	}
	/** set reactance p.u. on 100MVA and bus base KV */
	public void setX(float[] x)
	{
		if (_x != x)
		{
			if (_xo == null)
				_xo = _x;
			_x = x;
		}
	}
	
	/** Resistance access for "sublist" subclasses to use */
	protected float[] getSubListR(int[] slndx)
	{
		int n = size();
		float[] rv = new float[n];
		for(int i=0; i < n; ++i)
			rv[i] = getR(slndx[i]);
		return rv;
	}
	/** Resistance access for "sublist" subclasses to use */
	protected void setSubListR(float[] r, int[] slndx)
	{
		int n = size();
		for(int i=0; i < n; ++i)
			setR(slndx[i], r[i]);
	}
	
	/** Reactance access for "sublist" subclasses to use */
	protected float[] getSubListX(int[] slndx)
	{
		int n = size();
		float[] rv = new float[n];
		for(int i=0; i < n; ++i)
			rv[i] = getX(slndx[i]);
		return rv;
	}
	/** Reactance access for "sublist" subclasses to use */
	protected void setSubListX(float[] x, int[] slndx)
	{
		int n = size();
		for(int i=0; i < n; ++i)
			setX(slndx[i], x[i]);
	}

}
