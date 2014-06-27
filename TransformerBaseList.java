package com.powerdata.openpa;

public abstract class TransformerBaseList<T extends ACBranch> extends ACBranchList<T>
{
	protected float[] _ftap, _ftapo, _ttap, _ttapo, _gmag, _gmago, _bmag, _bmago;

	protected TransformerBaseList(){super();}
	
	protected TransformerBaseList(PALists model, int[] keys, int[] fbkey, int[] tbkey)
	{
		super(model, keys, fbkey, tbkey);
	}
	protected TransformerBaseList(PALists model, int size, int[] fbkey, int[] tbkey)
	{
		super(model, size, fbkey, tbkey);
	}

	/** get from-side off-nominal tap ratio p.u. on 100MVA base and bus base KV */
	public float getFromTap(int ndx)
	{
		return _ftap[ndx];
	}
	/** get from-side off-nominal tap ratio p.u. on 100MVA base and bus base KV */
	public float[] getFromTap()
	{
		return _ftap;
	}
	/** set from-side off-nominal tap ratio p.u. on 100MVA base and bus base KV */
	public void setFromTap(int ndx, float a)
	{
		if (_ftapo == null && _ftap != null)
			_ftapo = _ftap.clone();
		_ftap[ndx] = a;
	}
	/** set from-side off-nominal tap ratio p.u. on 100MVA base and bus base KV */
	public void setFromTap(float[] a)
	{
		if (_ftap != a)
		{
			if (_ftapo == null)
				_ftapo = _ftap;
			_ftap = a;
		}
	}
	/** get to-side off-nominal tap ratio p.u on 100MVA base and bus base KV */
	public float getToTap(int ndx)
	{
		return _ttap[ndx];
	}
	/** get to-side off-nominal tap ratio p.u on 100MVA base and bus base KV */
	public float[] getToTap()
	{
		return _ttap;
	}
	/** set to-side off-nominal tap ratio p.u on 100MVA base and bus base KV */
	public void setToTap(int ndx, float a)
	{
		if (_ttapo == null && _ttap != null)
			_ttapo = _ttap.clone();
		_ttap[ndx] = a;
	}
	/** set to-side off-nominal tap ratio p.u on 100MVA base and bus base KV */
	public void setToTap(float[] a)
	{
		if (_ttap != a)
		{
			if (_ttapo == null)
				_ttapo = _ttap;
			_ttap = a;
		}
	}
	/** get transformer magnetizing conductance p.u. on 100MVA base */
	public float getGmag(int ndx)
	{
		return _gmag[ndx];
	}
	/** get transformer magnetizing conductance p.u. on 100MVA base */
	public float[] getGmag()
	{
		return _gmag;
	}
	/** set transformer magnetizing conductance p.u. on 100MVA base */
	public void setGmag(int ndx, float g)
	{
		if (_gmago == null && _gmag != null)
			_gmago = _gmag.clone();
		_gmag[ndx] = g;
	}
	/** set transformer magnetizing conductance p.u. on 100MVA base */
	public void setGmag(float[] g)
	{
		if (_gmag != g)
		{
			if (_gmago == null)
				_gmago = _gmag;
			_gmag = g;
		}
	}
	/** get transformer magnetizing susceptance p.u. on 100 MVA base */
	public float getBmag(int ndx)
	{
		return _bmag[ndx];
	}
	/** get transformer magnetizing susceptance p.u. on 100 MVA base */
	public float[] getBmag()
	{
		return _bmag;
	}
	/** set transformer magnetizing susceptance p.u. on 100 MVA base */
	public void setBmag(int ndx, float b)
	{
		if (_bmago == null && _bmag != null)
			_bmago = _bmag.clone();
		_bmag[ndx] = b;
	}
	/** set transformer magnetizing susceptance p.u. on 100 MVA base */
	public void setBmag(float[] b)
	{
		if (_bmag != b)
		{
			if (_bmago == null)
				_bmago = _bmag;
			_bmag = b;
		}
	}

	/** from-side tap access for "sublist" subclasses to use */
	protected float[] getSubListFromTap(int[] slndx)
	{
		int n = size();
		float[] rv = new float[n];
		for(int i=0; i < n; ++i)
			rv[i] = getFromTap(slndx[i]);
		return rv;
	}
	/** from-side tap access for "sublist" subclasses to use */
	protected void setSubListFromTap(float[] a, int[] slndx)
	{
		int n = size();
		for(int i=0; i < n; ++i)
			setFromTap(slndx[i], a[i]);
	}
	/** to-side tap access for "sublist" subclasses to use */
	protected float[] getSubListToTap(int[] slndx)
	{
		int n = size();
		float[] rv = new float[n];
		for(int i=0; i < n; ++i)
			rv[i] = getToTap(slndx[i]);
		return rv;
	}
	/** to-side tap access for "sublist" subclasses to use */
	protected void setSubListToTap(float[] a, int[] slndx)
	{
		int n = size();
		for(int i=0; i < n; ++i)
			setToTap(slndx[i], a[i]);
	}
	/** gmag access for "sublist" subclasses to use */
	protected float[] getSubListGmag(int[] slndx)
	{
		int n = size();
		float[] rv = new float[n];
		for(int i=0; i < n; ++i)
			rv[i] = getGmag(slndx[i]);
		return rv;
	}
	/** gmag access for "sublist" subclasses to use */
	protected void setSubListGmag(float[] g, int[] slndx)
	{
		int n = size();
		for(int i=0; i < n; ++i)
			setGmag(slndx[i], g[i]);
	}
	/** Bmag access for "sublist" subclasses to use */
	protected float[] getSubListBmag(int[] slndx)
	{
		int n = size();
		float[] rv = new float[n];
		for(int i=0; i < n; ++i)
			rv[i] = getBmag(slndx[i]);
		return rv;
	}
	/** Bmag access for "sublist" subclasses to use */
	protected void setSubListBmag(float[] b, int[] slndx)
	{
		int n = size();
		for(int i=0; i < n; ++i)
			setBmag(slndx[i], b[i]);
	}

}
