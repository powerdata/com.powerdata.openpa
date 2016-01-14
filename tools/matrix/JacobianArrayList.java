package com.powerdata.openpa.tools.matrix;

import java.util.AbstractList;
import java.util.Arrays;
import com.powerdata.openpa.tools.matrix.JacobianElement.JacobianElementContainer;

/**
 * Back a JacobianList by fixed-length arrays.  This implementation of List can not be resized
 * 
 * @author chris@powerdata.com
 *
 */

public class JacobianArrayList extends AbstractList<JacobianElement>
		implements JacobianList
{
	float[] _dpda, _dpdv, _dqda, _dqdv;
	float[][] _vals;
	
	public JacobianArrayList(int size)
	{
		_dpda = new float[size];
		_dpdv = new float[size];
		_dqda = new float[size];
		_dqdv = new float[size];
		_vals = new float[][] {_dpda, _dpdv, _dqda, _dqdv};
	}
	
	@Override
	public float getDpda(int ndx) { return _dpda[ndx]; }
	@Override
	public float getDpdv(int ndx) { return _dpdv[ndx]; }
	@Override
	public float getDqda(int ndx) { return _dqda[ndx]; }
	@Override
	public float getDqdv(int ndx) { return _dqdv[ndx]; }
	@Override
	public void setDpda(int ndx, float v) { _dpda[ndx] = v; }
	@Override
	public void setDpdv(int ndx, float v) { _dpdv[ndx] = v; }
	@Override
	public void setDqda(int ndx, float v) { _dqda[ndx] = v; }
	@Override
	public void setDqdv(int ndx, float v) { _dqdv[ndx] = v; }
	@Override
	public JacobianElement get(int index) { return new JacobianList.Element(this, index); }
	@Override
	public int size() { return _dpda.length; }
	@Override
	public void reset()
	{
		for(float[] a : _vals) Arrays.fill(a, 0f);
	}

	@Override
	public void incDpda(int ndx, float v) {_dpda[ndx] += v;}
	@Override
	public void incDpdv(int ndx, float v) {_dpdv[ndx] += v;}
	@Override
	public void incDqda(int ndx, float v) {_dqda[ndx] += v;}
	@Override
	public void incDqdv(int ndx, float v) {_dqdv[ndx] += v;}
	@Override
	public void decDpda(int ndx, float v) {_dpda[ndx] -= v;}
	@Override
	public void decDpdv(int ndx, float v) {_dpdv[ndx] -= v;}
	@Override
	public void decDqda(int ndx, float v) {_dqda[ndx] -= v;}
	@Override
	public void decDqdv(int ndx, float v) {_dqdv[ndx] -= v;}
	@Override
	public JacobianElement set(int index, JacobianElement element)
	{
		JacobianElementContainer rv = new JacobianElement.JacobianElementContainer(
				_dpda[index], _dpdv[index], _dqda[index], _dqdv[index]);
		_dpda[index] = element.getDpda();
		_dpdv[index] = element.getDpdv();
		_dqda[index] = element.getDqda();
		_dqdv[index] = element.getDqdv();
		return rv;
	}
	
	/**
	 * Set the element, but don't worry about a return value
	 * @param index
	 * @param e
	 */
	public void replace(int index, JacobianElement e)
	{
		_dpda[index] = e.getDpda();
		_dpdv[index] = e.getDpdv();
		_dqda[index] = e.getDqda();
		_dqdv[index] = e.getDqdv();
	}
	
	@Override
	public void inc(int index, JacobianElement e)
	{
		_dpda[index] += e.getDpda();
		_dpdv[index] += e.getDpdv();
		_dqda[index] += e.getDqda();
		_dqdv[index] += e.getDqdv();
	}
	
	@Override
	public void dec(int index, JacobianElement e)
	{
		_dpda[index] += e.getDpda();
		_dpdv[index] += e.getDpdv();
		_dqda[index] += e.getDqda();
		_dqdv[index] += e.getDqdv();
	}
	
}
