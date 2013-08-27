package com.powerdata.openpa.psse.csv;

import java.util.HashMap;
import java.util.Random;

public class Test
{
	String[] vals;
	HashMap<String,Integer> hash;
	
	public Test(int size)
	{
		vals = new String[size];
		hash = new HashMap<>(size);
		Random r = new Random(System.nanoTime());
		for(int i=0; i < size; ++i)
		{
			int v = r.nextInt(100000);
			vals[i] = String.valueOf(v);
			hash.put(vals[i], i);
		}
		
	}
	
	
	public void runTests()
	{
		System.out.format("HashTest: %d\n", runHashTest());
		System.out.format("ParseTest: %d\n", runIntTest());
	}
	
	long runIntTest()
	{
		long ts = System.currentTimeMillis();
		for (int i=0; i < vals.length; ++i)
		{
			int v = Integer.parseInt(vals[i]);
		}
		return System.currentTimeMillis() - ts;
	}


	long runHashTest()
	{
		long ts = System.currentTimeMillis();
		for (int i=0; i < vals.length; ++i)
		{
			int ndx = hash.get(vals[i]);
		}
		return System.currentTimeMillis() - ts;
	}


	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		Test t = new Test(10000000);
		t.runTests();
	}

}
