package com.powerdata.openpa;

public abstract class ACBranchList<T extends ACBranch> extends TwoTermDevList<T>
{
	float[]	_r, _ro, _x, _xo, _fbch, _fbcho, _tbch, _tbcho, _bmag, _bmago,
			_gmag, _gmago, _ftap, _ftapo, _ttap, _ttapo, _shift, _shifto;
	
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
	/** get phase shift through branch in Degrees */
	public float getShift(int ndx)
	{
		return _shift[ndx];
	}
	/** get phase shift through branch in Degrees */
	public float[] getShift()
	{
		return _shift;
	}
	/** set phase shift through branch in Degrees */
	public void setShift(int ndx, float sdeg)
	{
		if (_shifto == null && _shift != null)
			_shifto = _shift.clone();
		_shift[ndx] = sdeg;
	}
	/** set phase shift through branch in Degrees */
	public void setShift(float[] sdeg)
	{
		if (_shift != sdeg)
		{
			if (_shifto == null)
				_shifto = _shift;
			_shift = sdeg;
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
	/** shift access for "sublist" subclasses to use */
	protected float[] getSubListShift(int[] slndx)
	{
		int n = size();
		float[] rv = new float[n];
		for(int i=0; i < n; ++i)
			rv[i] = getShift(slndx[i]);
		return rv;
	}
	/** shift access for "sublist" subclasses to use */
	protected void setSubListShift(float[] shift, int[] slndx)
	{
		int n = size();
		for(int i=0; i < n; ++i)
			setShift(slndx[i], shift[i]);
	}

}
