package com.powerdata.openpa.tools;

import java.util.Collection;

/**
 * 
 * List of complex numbers in polar form
 * 
 * @author chris@powerdata.com
 *
 */
public class PComplexList extends ComplexListBase<PComplex>
{
	public float[] re() {return _v1;}
	public float[] im() {return _v2;}

	public PComplexList() {super();}
	
	public PComplexList(Collection<PComplex> collection)
	{
		int csize = collection.size();
		_v1 = new float[csize];
		_v2 = new float[csize];
		_size = csize;
		int i=0;
		for(PComplex c : collection)
		{
			_v1[i] = c.r();
			_v2[i++] = c.theta();
		}
	}

	@Override
	public void add(int index, PComplex element)
	{
		_add(index, element.r(), element.theta());
	}
	
	@Override
	public PComplex remove(int index)
	{
		PComplex rv = new PComplex(_v1[index], _v2[index]);
		_remove(index);
		return rv;
	}

	@Override
	public PComplex set(int index, PComplex element)
	{
		PComplex rv = new PComplex(_v1[index], _v2[index]);
		_v1[index] = element.r();
		_v2[index] = element.theta();
		return rv;
	}
	
	@Override
	public PComplex get(int index)
	{
		return new PComplex(_v1[index], _v2[index]);
	}
}

