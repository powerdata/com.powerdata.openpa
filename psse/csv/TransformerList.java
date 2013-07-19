package com.powerdata.openpa.psse.csv;


import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.powerdata.openpa.psse.Bus;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.tools.ComplexList;

public class TransformerList extends com.powerdata.openpa.psse.TransformerList
{
	int _size;

	/* line 1 */
	private String[] _ckt, _name, _cont1;
	private int[] _i, _j, _cw, _cm, _nmetr, _stat;
	private float[] _mag1, _mag2;
	
	/* line 2 */
	private ComplexList _z;
	private float[] _sbase;
	
	/* line 3 */
	private float[] _windv1, _nomv1, _ang1, _rata1, _ratb1, _ratc1, _rma1, _rmi1, _vma1, _vmi1, _cr1, _cx1;
	private int[] _cod1, _ntp1, _tab1;
	
	public TransformerList(PsseModel model, TransformerRawList rlist,
			TransformerPrep prep) throws PsseModelException 
	{
		super(model);
		_size = prep.size();
		
		int[] xfndx = prep.getXfRaw(), wndx = prep.getWndx(); 
		_i = prep.getBusI();
		_j = prep.getBusJ();
		_z = prep.getZ();
		
		_ckt = (String[]) loadSimpleArray(rlist, xfndx, "CKT", String.class);
		_name = (String[]) loadSimpleArray(rlist, xfndx, "NAME", String.class);
		/*
		 * This class always converts CZ to 1, so do not override and let
		 * default handle it
		 */
		_cw = (int[]) loadSimpleArray(rlist, xfndx, "CW", int.class);
		_cm = (int[]) loadSimpleArray(rlist, xfndx, "CM", int.class);
		loadNmetr(rlist, xfndx, wndx);
		loadStat(rlist, xfndx, wndx);
		loadMag(rlist, xfndx, wndx);
		loadSbase(rlist, xfndx, wndx);
		_windv1 = (float[]) new WndLoader("WINDV").load(rlist, xfndx, wndx, float.class);
		_nomv1 = (float[]) new WndLoader("NOMV").load(rlist, xfndx, wndx, float.class);
		_ang1 = (float[]) new WndLoader("ANG").load(rlist, xfndx, wndx, float.class);
		_rata1 = (float[]) new WndLoader("RATA").load(rlist, xfndx, wndx, float.class);
		_ratb1 = (float[]) new WndLoader("RATB").load(rlist, xfndx, wndx, float.class);
		_ratc1 = (float[]) new WndLoader("RATC").load(rlist, xfndx, wndx, float.class);
		_cod1 = (int[]) new WndLoader("COD").load(rlist, xfndx, wndx, int.class);
		_cont1 = (String[]) new WndLoader("CONT").load(rlist, xfndx, wndx, String.class);
		_rma1 = (float[]) new WndLoader("RMA").load(rlist, xfndx, wndx, float.class);
		_rmi1 = (float[]) new WndLoader("RMI").load(rlist, xfndx, wndx, float.class);
		_vma1 = (float[]) new WndLoader("VMA").load(rlist, xfndx, wndx, float.class);
		_vmi1 = (float[]) new WndLoader("VMI").load(rlist, xfndx, wndx, float.class);
		_ntp1 = (int[]) new WndLoader("NTP").load(rlist, xfndx, wndx, int.class);
		_tab1 = (int[]) new WndLoader("TAB").load(rlist, xfndx, wndx, int.class);
		_cr1 = (float[]) new WndLoader("CR").load(rlist, xfndx, wndx, float.class);
		_cx1 = (float[]) new WndLoader("CX").load(rlist, xfndx, wndx, float.class);
	}	
	
	private void loadSbase(TransformerRawList rlist, int[] xfndx, int[] wndx)
	{
		int n = xfndx.length;
		_sbase = new float[n];
		for(int i=0; i < n; ++i)
		{
			int x = xfndx[i];
			switch(wndx[i])
			{
				case 1: _sbase[i] = rlist.getSBASE1_2(x); break;
				case 2: _sbase[i] = rlist.getSBASE2_3(x); break;
				case 3: _sbase[i] = rlist.getSBASE3_1(x); break;
			}
		}
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

	private Object loadSimpleArray(TransformerRawList rlist, int[] xfndx,
			String prop, Class<?> type) throws PsseModelException
	{
		int n = xfndx.length;
		Object rv = Array.newInstance(type, n);
		try
		{
			Method m = TransformerRawList.class.getMethod("get" + prop,
					int.class);
			for (int i = 0; i < n; ++i)
			{
				Array.set(rv, i, m.invoke(rlist, xfndx[i]));
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
	public String getCKT(int ndx) throws PsseModelException {return _ckt[ndx];}
	@Override
	public int getCW(int ndx) throws PsseModelException {return _cw[ndx];}
	@Override
	public int getCM(int ndx) throws PsseModelException {return _cm[ndx];}
	@Override
	public float getMAG1(int ndx) throws PsseModelException {return _mag1[ndx];}
	@Override
	public float getMAG2(int ndx) throws PsseModelException {return _mag2[ndx];}
	@Override
	public int getNMETR(int ndx) throws PsseModelException {return _nmetr[ndx];}
	@Override
	public String getNAME(int ndx) throws PsseModelException {return _name[ndx];}
	@Override
	public int getSTAT(int ndx) throws PsseModelException {return _stat[ndx];}

	@Override
	public float getR1_2(int ndx) throws PsseModelException {return _z.re(ndx);}
	@Override
	public float getX1_2(int ndx) throws PsseModelException {return _z.im(ndx);}
	@Override
	public float getSBASE1_2(int ndx) throws PsseModelException {return _sbase[ndx];}

	@Override
	public float getWINDV1(int ndx) throws PsseModelException {return _windv1[ndx];}
	@Override
	public float getNOMV1(int ndx) throws PsseModelException {return _nomv1[ndx];}
	@Override
	public float getANG1(int ndx) throws PsseModelException {return _ang1[ndx];}
	@Override
	public float getRATA1(int ndx) throws PsseModelException {return _rata1[ndx];}
	@Override
	public float getRATB1(int ndx) throws PsseModelException {return _ratb1[ndx];}
	@Override
	public float getRATC1(int ndx) throws PsseModelException {return _ratc1[ndx];}
	@Override
	public int getCOD1(int ndx) throws PsseModelException {return _cod1[ndx];}
	@Override
	public String getCONT1(int ndx) throws PsseModelException {return _cont1[ndx];}
	@Override
	public float getRMA1(int ndx) throws PsseModelException {return _rma1[ndx];}
	@Override
	public float getRMI1(int ndx) throws PsseModelException {return _rmi1[ndx];}
	@Override
	public float getVMA1(int ndx) throws PsseModelException {return _vma1[ndx];}
	@Override
	public float getVMI1(int ndx) throws PsseModelException {return _vmi1[ndx];}
	@Override
	public int getNTP1(int ndx) throws PsseModelException {return _ntp1[ndx];}
	@Override
	public int getTAB1(int ndx) throws PsseModelException {return _tab1[ndx];}
	@Override
	public float getCR1(int ndx) throws PsseModelException {return _cr1[ndx];}
	@Override
	public float getCX1(int ndx) throws PsseModelException {return _cx1[ndx];}

	@Override
	public String getObjectID(int ndx) throws PsseModelException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size() {return _size;}
}

class WndLoader
{
	static final Class<TransformerRawList> _class = TransformerRawList.class;
	
	Method[] _methods;
	public WndLoader(String prop) throws PsseModelException
	{
		try
		{
			String pn = "get"+prop;
			_methods = new Method[] {null,
					_class.getMethod(pn+"1", int.class),
					_class.getMethod(pn+"2", int.class),
					_class.getMethod(pn+"3", int.class)};
		} catch (ReflectiveOperationException | SecurityException e)
		{
			throw new PsseModelException(e);
		}
	}
	
	public Object load(TransformerRawList rlist, int[] ndx,
			int[] wnd, Class<?> type) throws PsseModelException
	{
		int n = ndx.length;
		Object rv = Array.newInstance(type, n);
		try
		{
			for (int i = 0; i < n; ++i)
			{
				Array.set(rv, i, _methods[wnd[i]].invoke(rlist, ndx[i]));

			}
		} catch (ArrayIndexOutOfBoundsException | ReflectiveOperationException e)
		{
			throw new PsseModelException(e);
		}
		return rv;
	}
}

