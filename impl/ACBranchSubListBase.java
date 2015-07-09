package com.powerdata.openpa.impl;

import com.powerdata.openpa.ACBranch;
import com.powerdata.openpa.ACBranchListIfc;
import com.powerdata.openpa.PAModelException;

/**
 * Base implementation of branch sublists.  As new AC branches are added, subclass this class.
 * @author chris@powerdata.com
 *
 * @param <T>
 */
public class ACBranchSubListBase<T extends ACBranch> extends TwoTermDevSubList<T> implements
		ACBranchListIfc<T>
{
	
	ACBranchListIfc<T> _src;

	public ACBranchSubListBase(ACBranchListIfc<T> src, int[] ndx)
	{
		super(src, ndx);
		_src = src;
	}

	@Override
	public float getR(int ndx) throws PAModelException
	{
		return _src.getR(_ndx[ndx]);
	}

	@Override
	public void setR(int ndx, float r) throws PAModelException
	{
		_src.setR(_ndx[ndx], r);
	}

	@Override
	public float[] getR() throws PAModelException
	{
		return mapFloat(_src.getR());
	}

	@Override
	public void setR(float[] r) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			_src.setR(_ndx[i], r[i]);
	}

	@Override
	public float getX(int ndx) throws PAModelException
	{
		return _src.getX(_ndx[ndx]);
	}

	@Override
	public void setX(int ndx, float x) throws PAModelException
	{
		_src.setX(_ndx[ndx], x);
	}

	@Override
	public float[] getX() throws PAModelException
	{
		return  mapFloat(_src.getX());
	}

	@Override
	public void setX(float[] x) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			_src.setX(_ndx[i], x[i]);
	}

	@Override
	public float getFromTap(int ndx) throws PAModelException
	{
		return _src.getFromTap(_ndx[ndx]);
	}

	@Override
	public void setFromTap(int ndx, float a) throws PAModelException
	{
		_src.setFromTap(_ndx[ndx], a);
	}

	@Override
	public float[] getFromTap() throws PAModelException
	{
		return mapFloat(_src.getFromTap());
	}

	@Override
	public void setFromTap(float[] a) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			_src.setFromTap(_ndx[i], a[i]);
	}

	@Override
	public float getToTap(int ndx) throws PAModelException
	{
		return _src.getToTap(_ndx[ndx]);
	}

	@Override
	public void setToTap(int ndx, float a) throws PAModelException
	{
		_src.setToTap(_ndx[ndx], a);
	}

	@Override
	public float[] getToTap() throws PAModelException
	{
		return mapFloat(_src.getToTap());
	}

	@Override
	public void setToTap(float[] a) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			_src.setToTap(_ndx[i], a[i]);
	}

	@Override
	public float getGmag(int ndx) throws PAModelException
	{
		return _src.getGmag(_ndx[ndx]);
	}

	@Override
	public void setGmag(int ndx, float g) throws PAModelException
	{
		_src.setGmag(_ndx[ndx], g);
	}

	@Override
	public float[] getGmag() throws PAModelException
	{
		return mapFloat(_src.getGmag());
	}

	@Override
	public void setGmag(float[] g) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			_src.setGmag(_ndx[i], g[i]);
	}

	@Override
	public float getBmag(int ndx) throws PAModelException
	{
		return _src.getBmag(_ndx[ndx]);
	}

	@Override
	public void setBmag(int ndx, float b) throws PAModelException
	{
		_src.setBmag(_ndx[ndx], b);
	}

	@Override
	public float[] getBmag() throws PAModelException
	{
		return mapFloat(_src.getBmag());
	}

	@Override
	public void setBmag(float[] b) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			_src.setBmag(_ndx[i], b[i]);
	}

	@Override
	public float getFromBchg(int ndx) throws PAModelException
	{
		return _src.getFromBchg(_ndx[ndx]);
	}

	@Override
	public void setFromBchg(int ndx, float b) throws PAModelException
	{
		_src.setFromBchg(_ndx[ndx], b);
	}

	@Override
	public float[] getFromBchg() throws PAModelException
	{
		return mapFloat(_src.getFromBchg());
	}

	@Override
	public void setFromBchg(float[] b) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			_src.setFromBchg(_ndx[i], b[i]);
	}

	@Override
	public float getToBchg(int ndx) throws PAModelException
	{
		return _src.getToBchg(_ndx[ndx]);
	}

	@Override
	public void setToBchg(int ndx, float b) throws PAModelException
	{
		_src.setToBchg(_ndx[ndx], b);
	}

	@Override
	public float[] getToBchg() throws PAModelException
	{
		return mapFloat(_src.getToBchg());
	}

	@Override
	public void setToBchg(float[] b) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			_src.setToBchg(_ndx[i], b[i]);
	}

	@Override
	public float getShift(int ndx) throws PAModelException
	{
		return _src.getShift(_ndx[ndx]);
	}

	@Override
	public void setShift(int ndx, float sdeg) throws PAModelException
	{
		_src.setShift(_ndx[ndx], sdeg);
	}

	@Override
	public float[] getShift() throws PAModelException
	{
		return mapFloat(_src.getShift());
	}

	@Override
	public void setShift(float[] sdeg) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			_src.setShift(_ndx[i], sdeg[i]);
	}

	@Override
	public float getLTRating(int ndx) throws PAModelException
	{
		return _src.getLTRating(_ndx[ndx]);
	}

	@Override
	public float[] getLTRating() throws PAModelException
	{
		return mapFloat(_src.getLTRating());
	}

	@Override
	public void setLTRating(int ndx, float mva) throws PAModelException
	{
		_src.setLTRating(ndx, mva);
	}

	@Override
	public void setLTRating(float[] mva) throws PAModelException
	{
		for(int i=0; i < _size; ++i)
			_src.setLTRating(_ndx[i], mva[i]);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T get(int index)
	{
		return (T) new ACBranch(this, index);
	}

	
}
