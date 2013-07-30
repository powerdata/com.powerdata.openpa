package com.powerdata.openpa.tools;

import java.util.Collection;
import java.util.List;

/**
 * 
 * List of complex numbers in polar form
 * 
 * @author chris@powerdata.com
 *
 */
public class PComplexList extends ComplexListBase<PComplex>
{
	public float[] r() {return _v1;}
	public float[] theta() {return _v2;}
	public float r(int ndx) {return _v1[ndx];}
	public float theta(int ndx) {return _v2[ndx];}


	public PComplexList() {super();}

	public PComplexList(int descap, boolean setsize) {super(descap, setsize);}

	
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

	public PComplexList(List<Float> r, List<Float> theta)
	{
		int n = r.size();
		_v1 = new float[n];
		_v2 = new float[n];
		_size = n;
		for(int i=0; i < n; ++i)
		{
			_v1[i] = r.get(i);
			_v2[i] = theta.get(i);
		}
	}
	public PComplexList(float[] r, float[] theta)
	{
		_v1 = r.clone();
		_v2 = theta.clone();
		_size = r.length;
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

