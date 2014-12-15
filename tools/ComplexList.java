package com.powerdata.openpa.tools;

import java.util.Collection;
import java.util.List;

/**
 * Support Complex numbers in cartesian form.
 * 
 * @author chris@powerdata.com
 *
 */
public class ComplexList extends ComplexListBase<Complex>
{
	public float[] re() {return _v1;}
	public float[] im() {return _v2;}
	
	public float re(int ndx) {return _v1[ndx];}
	public float im(int ndx) {return _v2[ndx];}

	public ComplexList() {super();}
	
	public ComplexList(int descap, boolean setsize) {super(descap, setsize);}
	
	public ComplexList(Collection<Complex> collection)
	{
		int csize = collection.size();
		_v1 = new float[csize];
		_v2 = new float[csize];
		_size = csize;
		int i=0;
		for(Complex c : collection)
		{
			_v1[i] = c.re();
			_v2[i++] = c.im();
		}
	}

	public ComplexList(List<Float> re, List<Float> im)
	{
		int n = re.size();
		_v1 = new float[n];
		_v2 = new float[n];
		_size = n;
		for(int i=0; i < n; ++i)
		{
			_v1[i] = re.get(i);
			_v2[i] = im.get(i);
		}
	}
	
	public ComplexList(float[] re, float[] im)
	{
		_v1 = re.clone();
		_v2 = im.clone();
		_size = re.length;
	}

	@Override
	public void add(int index, Complex element)
	{
		_add(index, element.re(), element.im());
	}
	
	@Override
	public Complex remove(int index)
	{
		Complex rv = new Complex(_v1[index], _v2[index]);
		_remove(index);
		return rv;
	}

	@Override
	public Complex set(int index, Complex element)
	{
		Complex rv = new Complex(_v1[index], _v2[index]);
		_v1[index] = element.re();
		_v2[index] = element.im();
		return rv;
	}
	
	@Override
	public Complex get(int index)
	{
		return new Complex(_v1[index], _v2[index]);
	}
	
	public ComplexList inv()
	{
		ComplexList rv = new ComplexList(_size, true);
		float[] nre = rv.re(), nim = rv.im();
		for(int i=0; i < _size; ++i)
		{
			Complex c = get(i).inv();
			nre[i] = c.re();
			nim[i] = c.im();
		}
		return rv;
	}
	
}
