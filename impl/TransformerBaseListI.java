package com.powerdata.openpa.impl;

import com.powerdata.openpa.ACBranch;
import com.powerdata.openpa.ACBranchListIfc;
import com.powerdata.openpa.ColumnMeta;
import com.powerdata.openpa.PAModelException;

public abstract class TransformerBaseListI<T extends ACBranch>
		extends ACBranchListI<T> implements ACBranchListIfc<T>
{
	
	FloatData _b, _g, _deg, _ft, _tt;

	protected TransformerBaseListI()
	{
		super();
	}

	protected TransformerBaseListI(PAModelI model, int[] keys, TransBaseEnum le) throws PAModelException
	{
		super(model, keys, le);
		setFields(le);
	}

	protected TransformerBaseListI(PAModelI model, int size, TransBaseEnum le) throws PAModelException
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
	public float getGmag(int ndx) throws PAModelException
	{
		return _g.get(ndx);
	}

	@Override
	public void setGmag(int ndx, float g) throws PAModelException
	{
		_g.set(ndx, g);
	}

	@Override
	public float[] getGmag() throws PAModelException
	{
		return _g.get();
	}

	@Override
	public void setGmag(float[] g) throws PAModelException
	{
		_g.set(g);
	}

	@Override
	public float getBmag(int ndx) throws PAModelException
	{
		return _b.get(ndx);
	}

	@Override
	public void setBmag(int ndx, float b) throws PAModelException
	{
		_b.set(ndx, b);
	}

	@Override
	public float[] getBmag() throws PAModelException
	{
		return _b.get();
	}

	@Override
	public void setBmag(float[] b) throws PAModelException
	{
		_b.set(b);
	}

	@Override
	public float getShift(int ndx) throws PAModelException
	{
		return _deg.get(ndx);
	}

	@Override
	public void setShift(int ndx, float sdeg) throws PAModelException
	{
		_deg.set(ndx, sdeg);
	}

	@Override
	public float[] getShift() throws PAModelException
	{
		return _deg.get();
	}

	@Override
	public void setShift(float[] sdeg) throws PAModelException
	{
		_deg.set(sdeg);
	}
	@Override
	public float getFromTap(int ndx) throws PAModelException
	{
		return _ft.get(ndx);
	}

	@Override
	public void setFromTap(int ndx, float a) throws PAModelException
	{
		_ft.set(ndx, a);
	}

	@Override
	public float[] getFromTap() throws PAModelException
	{
		return _ft.get();
	}

	@Override
	public void setFromTap(float[] a) throws PAModelException
	{
		_ft.set(a);
	}

	@Override
	public float getToTap(int ndx) throws PAModelException
	{
		return _tt.get(ndx);
	}

	@Override
	public void setToTap(int ndx, float a) throws PAModelException
	{
		_tt.set(ndx, a);
	}

	@Override
	public float[] getToTap() throws PAModelException
	{
		return _tt.get();
	}

	@Override
	public void setToTap(float[] a) throws PAModelException
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
