package com.powerdata.openpa;

public abstract class TransformerBaseListI<T extends TransformerBase>
		extends ACBranchListI<T> implements TransformerBaseList<T>
{
	
	FloatData _b, _g, _deg, _ft, _tt;

	protected TransformerBaseListI()
	{
		super();
	}

	protected TransformerBaseListI(PAModel model, int[] keys, TransBaseEnum le)
	{
		super(model, keys, le);
		setFields(le);
	}

	protected TransformerBaseListI(PAModel model, int size, TransBaseEnum le)
	{
		super(model, size, le);
		setFields(le);
	}

	private void setFields(TransBaseEnum le)
	{
		_b = new FloatData(le.bmag());
		_g = new FloatData(le.gmag());
		_deg = new FloatData(le.shift());
		_ft = new FloatData(le.ftap());
		_tt = new FloatData(le.ttap());
	}

	
	@Override
	public float getGmag(int ndx)
	{
		return _g.get(ndx);
	}

	@Override
	public void setGmag(int ndx, float g)
	{
		_g.set(ndx, g);
	}

	@Override
	public float[] getGmag()
	{
		return _g.get();
	}

	@Override
	public void setGmag(float[] g)
	{
		_g.set(g);
	}

	@Override
	public float getBmag(int ndx)
	{
		return _b.get(ndx);
	}

	@Override
	public void setBmag(int ndx, float b)
	{
		_b.set(ndx, b);
	}

	@Override
	public float[] getBmag()
	{
		return _b.get();
	}

	@Override
	public void setBmag(float[] b)
	{
		_b.set(b);
	}

	@Override
	public float getShift(int ndx)
	{
		return _deg.get(ndx);
	}

	@Override
	public void setShift(int ndx, float sdeg)
	{
		_deg.set(ndx, sdeg);
	}

	@Override
	public float[] getShift()
	{
		return _deg.get();
	}

	@Override
	public void setShift(float[] sdeg)
	{
		_deg.set(sdeg);
	}
	@Override
	public float getFromTap(int ndx)
	{
		return _ft.get(ndx);
	}

	@Override
	public void setFromTap(int ndx, float a)
	{
		_ft.set(ndx, a);
	}

	@Override
	public float[] getFromTap()
	{
		return _ft.get();
	}

	@Override
	public void setFromTap(float[] a)
	{
		_ft.set(a);
	}

	@Override
	public float getToTap(int ndx)
	{
		return _tt.get(ndx);
	}

	@Override
	public void setToTap(int ndx, float a)
	{
		_tt.set(ndx, a);
	}

	@Override
	public float[] getToTap()
	{
		return _tt.get();
	}

	@Override
	public void setToTap(float[] a)
	{
		_tt.set(a);
	}
	
	interface TransBaseEnum extends ACBranchEnum
	{
		ColumnMeta gmag();
		ColumnMeta bmag();
		ColumnMeta shift();
		ColumnMeta ftap();
		ColumnMeta ttap();
	}

}
