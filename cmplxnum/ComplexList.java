package com.powerdata.openpa.cmplxnum;

public class ComplexList
{
	protected float[] _re;
	protected float[] _im;
	
	public ComplexList(int size)
	{
		_re = new float[size];
		_im = new float[size];
	}

	public Complex get(int i)
	{
		return new Complex(_re[i], _im[i]);
	}
	
	public float[] re() {return _re;}
	public float[] im() {return _im;}

	public void set(int i, Complex v)
	{
		_re[i] = v.re();
		_im[i] = v.im();
	}
	
	public void set(int i, float re, float im)
	{
		_re[i] = re;
		_im[i] = im;
	}
	
	public float abs(int idx)
	{
		float re = _re[idx];
		float im = _im[idx];
		return (float) Math.sqrt(re*re+im*im);
	}
	
	public void add(int idx, Complex v)
	{
		_re[idx] += v.re();
		_im[idx] += v.im();
	}

	public void add(int idx, float re, float im)
	{
		_re[idx] += re;
		_im[idx] += im;
	}

	public void sub(int idx, Complex v)
	{
		_re[idx] -= v.re();
		_im[idx] -= v.im();
	}

	public void sub(int idx, float re, float im)
	{
		_re[idx] -= re;
		_im[idx] -= im;
	}

	@Override
	public String toString()
	{
		int n = (_re == null) ? 0 : _re.length;
		String rv = null;
		if (n > 0)
		{
			StringBuilder rvb = new StringBuilder(10*n);
			rvb.append('[');
			for(int i=0; i < n; ++i)
			{
				if (i > 0) rvb.append(", ");
				rvb.append('(');
				rvb.append(_re[i]);
				rvb.append(',');
				rvb.append(_im[i]);
				rvb.append(')');
			}
			rvb.append(']');
			rv = rvb.toString();
		}
		return rv;
	}

	
	
}
