package com.powerdata.openpa.psse.csv;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.powerdata.openpa.psse.Bus;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.tools.ComplexList;

public class TransformerList extends com.powerdata.openpa.psse.TransformerList
{
	int _size;

	/* line 1 */
	String[] _ckt, _name;
	int[] _i, _j, _cw, _cm, _nmetr, _stat;
	float[] _mag1, _mag2;
	ComplexList _z;
	
	public TransformerList(PsseModel model, TransformerRawList rlist,
			TransformerPrep prep) throws PsseModelException 
	{
		super(model);
		_size = prep.size();
		
		int[] xfndx = prep.getXfRaw(), wndx = prep.getWndx(); 
		_i = prep.getBusI();
		_j = prep.getBusJ();
		_z = prep.getZ();
		
		_cw = loadIntArray(rlist, xfndx, "CW");
		_cm = loadIntArray(rlist, xfndx, "CM");
		loadNmetr(rlist, xfndx, wndx);
		loadStat(rlist, xfndx, wndx);
		loadMag(rlist, xfndx, wndx);
	}
	
	private void loadMag(TransformerRawList rlist, int[] xfndx, int[] wndx)
	{
		int n = xfndx.length;
		_mag1 = new float[n];
		_mag2 = new float[n];
		for(int i=0; i < n; ++i)
		{
			if (wndx[i] == 1)
			{
				int tx = xfndx[i];
				_mag1[i] = rlist.getMAG1(tx);
				_mag2[i] = rlist.getMAG2(tx);
			}
		}
	}

	private void loadStat(TransformerRawList rlist, int[] xfndx, int[] wndx)
	{
		int n = xfndx.length;
		_stat = new int[n];
		for(int i=0; i < n; ++i)
		{
			int s = rlist.getSTAT(xfndx[i]);
			int w = wndx[i];
			switch(s)
			{
				case 0:
				case 1: _stat[i] = s; break;
				case 4: _stat[i] = (w == 1) ? 0 : 1; break; 
				default: _stat[i] = (w == s) ? 0 : 1;
			}
		}
	}
	
	private void loadNmetr(TransformerRawList rlist, int[] xfndx, int[] wndx)
	{
		int n = xfndx.length;
		_nmetr = new int[n];
		for(int i=0; i < n; ++i)
		{
			int nm = rlist.getNMETR(xfndx[i]);
			int w = wndx[i];
			_nmetr[i] = (w == nm) ? 1 : 2;
		}
	}

	private int[] loadIntArray(TransformerRawList rlist, int[] xfndx, String prop) throws PsseModelException
	{
		int n = xfndx.length;
		int[] rv = new int[n];
		try
		{
			Method m = TransformerRawList.class.getMethod("get"+prop, int.class);
			for(int i=0; i < n; ++i)
			{
				rv[i] = (int) m.invoke(rlist, xfndx[i]);
			}
		
		} catch (SecurityException | ReflectiveOperationException e)
		{
			throw new PsseModelException(e);
		}
		return rv;
	}
	

	@Override
	public Bus getFromBus(int ndx) throws PsseModelException {return _buses.get(_i[ndx]);}
	@Override
	public Bus getToBus(int ndx) throws PsseModelException {return _buses.get(_j[ndx]);}

	@Override
	public String getI(int ndx) throws PsseModelException {return _buses.get(_i[ndx]).getObjectID();}
	@Override
	public String getJ(int ndx) throws PsseModelException {return _buses.get(_j[ndx]).getObjectID();}

	@Override
	public float getR1_2(int ndx) throws PsseModelException {return _z.re(ndx);}
	@Override
	public float getX1_2(int ndx) throws PsseModelException {return _z.im(ndx);}

	@Override
	public String getObjectID(int ndx) throws PsseModelException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size() {return _size;}
}
