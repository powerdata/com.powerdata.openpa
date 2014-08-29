package com.powerdata.openpa.impl;

import java.util.Arrays;
import com.powerdata.openpa.ACBranch;
import com.powerdata.openpa.ACBranchListIfc;
import com.powerdata.openpa.ColumnMeta;
import com.powerdata.openpa.PAModelException;

public abstract class ACBranchListI<T extends ACBranch> extends
		TwoTermDevListI<T> implements ACBranchListIfc<T>
{
	protected ACBranchListI()
	{
		super();
	}
	FloatData _r, _x, _ratlt;
	
	protected ACBranchListI(PAModelI model, int[] keys, ACBranchEnum le) throws PAModelException
	{
		super(model, keys, le);
		setFields(le);
	}
	protected ACBranchListI(PAModelI model, int size, ACBranchEnum le) throws PAModelException
	{
		super(model, size, le);
		setFields(le);
	}
	private void setFields(ACBranchEnum le)
	{
		_r = new FloatData(le.r());
		_x = new FloatData(le.x());
		_ratlt = new FloatData(le.ratLT());
	}
	@Override
	public float getR(int ndx) throws PAModelException
	{
		return _r.get(ndx);
	}
	@Override
	public void setR(int ndx, float r) throws PAModelException
	{
		_r.set(ndx, r);
	}
	@Override
	public float[] getR() throws PAModelException
	{
		return _r.get();
	}
	@Override
	public void setR(float[] r) throws PAModelException
	{
		_r.set(r);
	}
	@Override
	public float getX(int ndx) throws PAModelException
	{
		return _x.get(ndx);
	}
	@Override
	public void setX(int ndx, float x) throws PAModelException
	{
		_x.set(ndx, x);
	}
	@Override
	public float[] getX() throws PAModelException
	{
		return _x.get();
	}
	@Override
	public void setX(float[] x) throws PAModelException
	{
		_x.set(x);
	}

	interface ACBranchEnum extends TwoTermDevEnum
	{
		ColumnMeta r();
		ColumnMeta x();
		ColumnMeta  ratLT();
	}

	@Override
	public float getFromTap(int ndx) throws PAModelException
	{
		return 1f;
	}
	@Override
	public void setFromTap(int ndx, float a) throws PAModelException
	{
		// do nothing here
	}

	float[] filldeft(float v)
	{
		float[] rv = new float[_size];
		Arrays.fill(rv, v);
		return rv;
	}
	
	@Override
	public float[] getFromTap() throws PAModelException
	{
		return filldeft(1f);
	}
	@Override
	public void setFromTap(float[] a) throws PAModelException
	{
		// do nothing here
	}
	@Override
	public float getToTap(int ndx) throws PAModelException
	{
		return 1f;
	}
	@Override
	public void setToTap(int ndx, float a) throws PAModelException
	{
		// do nothing here
	}
	@Override
	public float[] getToTap() throws PAModelException
	{
		return filldeft(1);
	}
	@Override
	public void setToTap(float[] a) throws PAModelException
	{
		// do nothing here
	}
	@Override
	public float getGmag(int ndx) throws PAModelException
	{
		return 0f;
	}
	@Override
	public void setGmag(int ndx, float g) throws PAModelException
	{
		// do nothing here
	}
	@Override
	public float[] getGmag() throws PAModelException
	{
		return new float[_size];
	}
	@Override
	public void setGmag(float[] g) throws PAModelException
	{
		// do nothing here
	}
	@Override
	public float getBmag(int ndx) throws PAModelException
	{
		return 0f;
	}
	@Override
	public void setBmag(int ndx, float b) throws PAModelException
	{
		// do nothing here
	}
	@Override
	public float[] getBmag() throws PAModelException
	{
		return new float[_size];
	}
	@Override
	public void setBmag(float[] b) throws PAModelException
	{
		// do nothing here
	}
	@Override
	public float getFromBchg(int ndx) throws PAModelException
	{
		return 0f;
	}
	@Override
	public void setFromBchg(int ndx, float b) throws PAModelException
	{
		// do nothing here
	}
	@Override
	public float[] getFromBchg() throws PAModelException
	{
		return new float[_size];
	}
	@Override
	public void setFromBchg(float[] b) throws PAModelException
	{
		// do nothing here
	}
	@Override
	public float getToBchg(int ndx) throws PAModelException
	{
		return 0f;
	}
	@Override
	public void setToBchg(int ndx, float b) throws PAModelException
	{
		// do nothing here
	}
	@Override
	public float[] getToBchg() throws PAModelException
	{
		return new float[_size];
	}
	@Override
	public void setToBchg(float[] b) throws PAModelException
	{
		// do nothing here
	}
	@Override
	public float getShift(int ndx) throws PAModelException
	{
		return 0f;
	}
	@Override
	public void setShift(int ndx, float sdeg) throws PAModelException
	{
		// do nothing here
	}
	@Override
	public float[] getShift() throws PAModelException
	{
		return new float[_size];
	}
	@Override
	public void setShift(float[] sdeg) throws PAModelException
	{
		// do nothing here
	}
	@Override
	public float getLTRating(int ndx) throws PAModelException
	{
		return _ratlt.get(ndx);
	}
	@Override
	public float[] getLTRating() throws PAModelException
	{
		return _ratlt.get();
	}
	@Override
	public void setLTRating(int ndx, float mva) throws PAModelException
	{
		_ratlt.set(ndx, mva);
	}
	@Override
	public void setLTRating(float[] mva) throws PAModelException
	{
		_ratlt.set(mva);
	}
}
