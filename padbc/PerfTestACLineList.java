package com.powerdata.openpa.padbc;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.Random;

import com.powerdata.openpa.tools.BooleanAttrib;
import com.powerdata.openpa.tools.FloatAttrib;
import com.powerdata.openpa.tools.IntAttrib;
import com.powerdata.openpa.tools.StringAttrib;

public class PerfTestACLineList extends ACLineList<ACLine>
{
	protected int _size;
	protected float[] _data;
	
	public PerfTestACLineList(int size)
	{
		_size = size;
		_data = new float[size];
		Random rand = new Random();
		for (int i=0; i < size; ++i)
			_data[i] = rand.nextFloat()*2F;
			
	}
	
	@Override
	public String getObjectID(int ndx) {return String.valueOf(ndx);}

	@Override
	public int getFromNode(int ndx) {return 0;}

	@Override
	public int getToNode(int ndx) {return 0;}

	@Override
	public float getR(int ndx) {return 0;}

	@Override
	public float getX(int ndx) {return 0;}

	@Override
	public float getFromBChg(int ndx) {return _data[ndx];}

	@Override
	public float getToBChg(int ndx) {return 0;}

	@Override
	public void updateActvPower(int ndx, float p) {}

	@Override
	public void updateReacPower(int ndx, float q) {} 
	
	@Override
	public int size() {return _size;}

	public long testNative()
	{
		@SuppressWarnings("unused")
		float sum = 0;
		long ts = System.currentTimeMillis();
		for(int i=0; i < _size; ++i)
			sum += _data[i];
		return System.currentTimeMillis() - ts;
	}
	
	public long testAccessor()
	{
		@SuppressWarnings("unused")
		float sum = 0;
		long ts = System.currentTimeMillis();
		for(int i=0; i < _size; ++i)
			sum += getFromBChg(i);
		return System.currentTimeMillis() - ts;
	}
	
	public long testObject()
	{
		@SuppressWarnings("unused")
		float sum = 0;
		long ts = System.currentTimeMillis();
		for(int i=0; i < _size; ++i)
			sum += get(i).getFromBChg();
		return System.currentTimeMillis() - ts;
	}
	
	public long testPrettyFor()
	{
		@SuppressWarnings("unused")
		float sum = 0;
		long ts = System.currentTimeMillis();
		for(ACLine n : this)
			sum += n.getFromBChg();
		return System.currentTimeMillis() - ts;
	}
	
	public long testIterator()
	{
		@SuppressWarnings("unused")
		float sum = 0;
		Iterator<ACLine> li = iterator();
		long ts = System.currentTimeMillis();
		while (li.hasNext())
			sum += li.next().getFromBChg();
		return System.currentTimeMillis() - ts;
	}

	public long testListIterator()
	{
		@SuppressWarnings("unused")
		float sum = 0;
		ListIterator<ACLine> li = listIterator();
		long ts = System.currentTimeMillis();
		while (li.hasNext())
			sum += li.next().getFromBChg();
		return System.currentTimeMillis() - ts;
	}

	public void report(String desc, long time, long base)
	{
		long pcnt = (100*time)/base;
		System.out.printf("%25s: %3dms (%3d%%)%n",desc,time,pcnt);
	}
	
	public void test()
	{
		long n = testNative();
		report("Native Array",n,n);
		report("Accessor through List",testAccessor(),n);
		report("Accessor through Object",testObject(),n);
		report("Convenient For Loop",testPrettyFor(),n);
		report("Iterator",testIterator(),n);
		report("List Iterator",testListIterator(),n);
	}
	
	@Override
	public StringAttrib<ACLine> mapStringAttrib(String attribname) {return null;}
	@Override
	public FloatAttrib<ACLine> mapFloatAttrib(String attribname) {return null;}
	@Override
	public IntAttrib<ACLine> mapIntAttrib(String attribname) {return null;}
	@Override
	public BooleanAttrib<ACLine> mapBooleanAttrib(String attribname) {return null;}
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		PerfTestACLineList test = new PerfTestACLineList(100000000);
		test.test();
	}

	@Override
	public ACLine get(int ndx) {return new ACLine(ndx, this);} 

	@Override
	public ACLine get(String objectid) {throw new UnsupportedOperationException();}
}