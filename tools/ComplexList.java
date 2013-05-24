package com.powerdata.openpa.tools;

import java.util.Collection;

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

	public ComplexList() {super();}
	
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

}
