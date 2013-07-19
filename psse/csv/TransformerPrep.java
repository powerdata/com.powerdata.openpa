package com.powerdata.openpa.psse.csv;

import java.util.ArrayList;

import com.powerdata.openpa.psse.conversions.TransformerRaw;
import com.powerdata.openpa.tools.Complex;
import com.powerdata.openpa.tools.ComplexList;

public class TransformerPrep
{
	ArrayList<Integer>	xf	= new ArrayList<>(), wndx = new ArrayList<>(),
			bus1 = new ArrayList<>(), bus2 = new ArrayList<>();

	ArrayList<Float> zr = new ArrayList<>(), zx = new ArrayList<>();
			
	public void prep(TransformerRaw xf, int wndx, int bus1, int bus2, Complex z)
	{
		this.xf.add(xf.getIndex());
		this.wndx.add(wndx);
		this.bus1.add(bus1);
		this.bus2.add(bus2);
		zr.add(z.re());
		zx.add(z.im());
	}
	
	public int size() {return xf.size();}
	public int[] getXfRaw() {return makeIntArray(xf);}
	public int[] getWndx() {return makeIntArray(wndx);}
	public int[] getBusI() {return makeIntArray(bus1);}
	public int[] getBusJ() {return makeIntArray(bus2);}
	public ComplexList getZ() {return new ComplexList(zr, zx);}
	
	int[] makeIntArray(ArrayList<Integer> list)
	{
		int n = list.size();
		int[] rv = new int[n];
		for(int i=0; i < n; ++i)
			rv[i] = list.get(i);
		return rv;
	}
}
