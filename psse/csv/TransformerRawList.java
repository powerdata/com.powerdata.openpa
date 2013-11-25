package com.powerdata.openpa.psse.csv;



import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.psse.Transformer;
import com.powerdata.openpa.psse.conversions.XfrWndTool;
import com.powerdata.openpa.psse.util.TransformerRaw;
import com.powerdata.openpa.tools.Complex;
import com.powerdata.openpa.tools.ComplexList;

public class TransformerRawList extends com.powerdata.openpa.psse.TransformerList
{
	int _size;

	/* line 1 */
	String[] _ckt, _name, _cont1, _i, _j;
	int[] _cw, _cm, _nmetr, _stat;
	float[] _mag1, _mag2;
	
	/* line 2 */
	ComplexList _z;
	float[] _sbase;
	
	/* line 3 */
	float[]		_windv1, _nomv1, _ang1, _rata1, _ratb1, _ratc1, _rma1,
			_rmi1, _vma1, _vmi1, _cr1, _cx1;
	int[]		_cod1, _ntp1, _tab1;
	
	/* line 4 */
	float[] _windv2, _nomv2, _rma2, _rmi2;
	int[] _ntp2;
	
	ComplexList _fs, _ts;

	public TransformerRawList(PsseRawModel model, Transformer3RawList rlist,
			TransformerPrep prep) throws PsseModelException 
	{
		super(model);
		_size = prep.size();
		
		int[] xfndx = prep.getXfRaw(), wndx = prep.getWndx(); 
		_i = prep.getBusI();
		_j = prep.getBusJ();
		_z = prep.getZ();
		
		_ckt = (String[]) Transformer3RawList.loadArray(rlist, xfndx, "CKT", String.class);
		_name = (String[]) Transformer3RawList.loadArray(rlist, xfndx, "NAME", String.class);
		/*
		 * This class always converts CZ to 1, so do not override and let
		 * default handle it
		 */
		_cw = (int[]) Transformer3RawList.loadArray(rlist, xfndx, "CW", int.class);
		_cm = (int[]) Transformer3RawList.loadArray(rlist, xfndx, "CM", int.class);
		_nmetr = Transformer3RawList.loadNmetr(rlist, xfndx, wndx);
		_stat = Transformer3RawList.loadStat(rlist, xfndx, wndx);
		float[][] tmag = Transformer3RawList.loadMag(rlist, xfndx, wndx);
		_mag1 = tmag[0];
		_mag2 = tmag[1];
		_sbase = Transformer3RawList.loadSbase(rlist, xfndx, wndx);
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
		
		loadLine4(rlist, xfndx, wndx);

//		adjustImpedances();
		
		_fs = new ComplexList(_size, true);
		_ts = new ComplexList(_size, true);
	}	
	
	void adjustImpedances() throws PsseModelException
	{
		float[] zr = _z.re();
		float[] zx = _z.im();
		
		for(int i=0; i < _size; ++i)
		{
			XfrWndTool t = XfrWndTool.get(_cw[i]);
			Transformer xf = get(i);
			_windv1[i] = t.getRatio1(xf);
			float wv2 = t.getRatio2(xf);
			float wv22 = wv2*wv2;
			_windv2[i] = wv2;
			_cw[i] = 1;
			
			zr[i] *= wv22;
			zx[i] *= wv22;
		}
	}

	private void loadLine4(Transformer3RawList rlist, int[] xfndx, int[] wndx) throws PsseModelException
	{
		int n = xfndx.length;
		_windv2 = new float[n];
		_nomv2 = new float[n];
		_rma2 = new float[n];
		_rmi2 = new float[n];
		_ntp2 = new int[n];
		
		for(int i=0; i < n; ++i)
		{
			TransformerRaw xf = rlist.get(xfndx[i]);
			String k = xf.getK();
			boolean is3w = !(k.isEmpty() || k.equals("0"));
			if (is3w)
			{
				_windv2[i] = 1f; 
				_nomv2[i] = 1f;
				_rma2[i] = 1.1f;
				_rmi2[i] = 0.9f;
				_ntp2[i] = 33;
			}
			else
			{
				_windv2[i] = xf.getWINDV2();
				_nomv2[i] = xf.getNOMV2();
				_rma2[i] = xf.getRMA2();
				_rmi2[i] = xf.getRMI2();
				_ntp2[i] = xf.getNTP2();
			}
		}
	}

	@Override
	public String getI(int ndx) throws PsseModelException {return _i[ndx];}
	@Override
	public String getJ(int ndx) throws PsseModelException {return _j[ndx];}
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
	public float getWINDV2(int ndx) throws PsseModelException {return _windv2[ndx];}
	@Override
	public float getNOMV2(int ndx) throws PsseModelException {return _nomv2[ndx];}
	@Override
	public float getRMA2(int ndx) throws PsseModelException {return _rma2[ndx];}
	@Override
	public float getRMI2(int ndx) throws PsseModelException {return _rmi2[ndx];}
	@Override
	public int getNTP2(int ndx) throws PsseModelException {return _ntp2[ndx];}

	@Override
	public Complex getZ(int ndx) throws PsseModelException {return _z.get(ndx);}

	@Override
	public float getR(int ndx) throws PsseModelException {return _z.re(ndx);}
	@Override
	public float getX(int ndx) throws PsseModelException {return _z.im(ndx);}
	@Override
	public String getObjectID(int ndx) throws PsseModelException
	{
		StringBuilder sb = new StringBuilder("XF-");
		sb.append(getI(ndx));
		sb.append('-');
		sb.append(getJ(ndx));
		sb.append('-');
		sb.append(getCKT(ndx));
		return sb.toString();
	}
	@Override
	public String getObjectName(int ndx) throws PsseModelException
	{
		StringBuilder sb = new StringBuilder("XF-");
		sb.append(getFromBus(ndx).getObjectName());
		sb.append('-');
		sb.append(getToBus(ndx).getObjectName());
		sb.append('-');
		sb.append(getCKT(ndx));
		return sb.toString();
	}

	@Override
	public int size() {return _size;}
}

