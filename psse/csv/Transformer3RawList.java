package com.powerdata.openpa.psse.csv;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Method;

import com.powerdata.openpa.psse.Bus;
import com.powerdata.openpa.psse.BusList;
import com.powerdata.openpa.psse.PsseModel;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.tools.LoadArray;
import com.powerdata.openpa.tools.SimpleCSV;

public class Transformer3RawList extends com.powerdata.openpa.psse.util.TransformerRawList
{
	PsseModel _eq;
	BusList _buses;
	int _size;

	/* line 1 */
	String[] _i, _j, _k, _ckt, _name;
	int[] _cw, _cz, _cm, _nmetr, _stat;
	float[] _mag1, _mag2;
	
	/* line 2 */
	float[] _r12, _x12, _sb12, _r23, _x23, _sb23, _r31, _x31, _sb31,
		_vmstar, _anstar;
	
	/* line 3 */
	float[] _windv1, _nomv1, _ang1, _rata1, _ratb1, _ratc1, _rma1,
		_rmi1, _vma1, _vmi1, _cr1, _cx1;
	int[] _cod1, _ntp1, _tab1;
	String[] _cont1;
	
	/* line 4 */
	float[] _windv2, _nomv2, _ang2, _rata2, _ratb2, _ratc2, _rma2,
		_rmi2, _vma2, _vmi2, _cr2, _cx2;
	int[] _cod2, _ntp2, _tab2;
	String[] _cont2;
	
	/* line 5 */
	float[] _windv3, _nomv3, _ang3, _rata3, _ratb3, _ratc3, _rma3,
		_rmi3, _vma3, _vmi3, _cr3, _cx3;
	int[] _cod3, _ntp3, _tab3;
	String[] _cont3;
	
	public Transformer3RawList(PsseModel eq, File dir) throws PsseModelException
	{
		super(eq);
		try
		{
			_eq = eq;
			_buses = eq.getBuses();
			SimpleCSV xfr = new SimpleCSV(new File(dir,"Transformer.csv"));
			_size 		= xfr.getRowCount();

			/* Line 1 */
			_i			= xfr.get("I");
			_j			= xfr.get("J");
			_k			= LoadArray.String(xfr, "K", this, "getDeftK");
			_ckt		= LoadArray.String(xfr,"CKT",this,"getDeftCKT");
			_cw			= LoadArray.Int(xfr,"CW",this,"getDeftCW");
			_cz			= LoadArray.Int(xfr,"CZ",this,"getDeftCZ");
			_cm			= LoadArray.Int(xfr,"CM",this,"getDeftCM");
			_mag1		= LoadArray.Float(xfr,"MAG1",this,"getDeftMAG1");
			_mag2		= LoadArray.Float(xfr,"MAG2",this,"getDeftMAG2");
			_nmetr		= LoadArray.Int(xfr,"NMETR",this,"getDeftNMETR");
			_name		= LoadArray.String(xfr,"NAME",this, "getDeftNAME");
			_stat		= LoadArray.Int(xfr,"STAT",this, "getDeftSTAT");
			
			/* Line 2 */
			
			_r12		= LoadArray.Float(xfr,"R1-2",this,"getDeftR1_2");
			_x12 		= xfr.getFloats("X1-2");
			_sb12		= LoadArray.Float(xfr,"SBASE1-2",this,"getDeftSBASE1_2");
			_r23		= LoadArray.Float(xfr,"R2-3",this,"getDeftR2_3");
			_x23		= LoadArray.Float(xfr,"X2-3",this,"getDeftX2_3");
			_sb23		= LoadArray.Float(xfr,"SBASE2-3",this,"getDeftSBASE2_3");
			_r31		= LoadArray.Float(xfr,"R3-1",this,"getDeftR3_1");
			_x31		= LoadArray.Float(xfr,"X3-1",this,"getDeftX3_1");
			_sb31		= LoadArray.Float(xfr,"SBASE3-1",this, "getDeftSBASE3_1");
			_vmstar 	= LoadArray.Float(xfr, "VMSTAR", this, "getDeftVMSTAR");
			_anstar		= LoadArray.Float(xfr, "ANSTAR", this, "getDeftANSTAR");
			
			/* Line 3 */
			
			_windv1		= LoadArray.Float(xfr,"WINDV1",this,"getDeftWINDV1");
			_nomv1		= LoadArray.Float(xfr,"NOMV1",this,"getDeftNOMV1");
			_ang1		= LoadArray.Float(xfr,"ANG1",this,"getDeftANG1");
			_rata1		= LoadArray.Float(xfr,"RATA1",this,"getDeftRATA1");
			_ratb1		= LoadArray.Float(xfr,"RATB1",this,"getDeftRATB1");
			_ratc1		= LoadArray.Float(xfr,"RATC1",this,"getDeftRATC1");
			_cod1		= LoadArray.Int(xfr,"COD1",this,"getDeftCOD1");
			_cont1		= LoadArray.String(xfr,"CONT1",this,"getDeftCONT1");
			_rma1		= LoadArray.Float(xfr,"RMA1",this,"getDeftRMA1");
			_rmi1		= LoadArray.Float(xfr,"RMI1",this,"getDeftRMI1");
			_vma1		= LoadArray.Float(xfr,"VMA1",this,"getDeftVMA1");
			_vmi1		= LoadArray.Float(xfr,"VMI1",this,"getDeftVMI1");
			_ntp1		= LoadArray.Int(xfr,"NTP1",this,"getDeftNTP1");
			_tab1		= LoadArray.Int(xfr,"TAB1",this,"getDeftTAB1");
			_cr1		= LoadArray.Float(xfr,"CR1",this,"getDeftCR1");
			_cx1		= LoadArray.Float(xfr,"CX1",this,"getDeftCX1");
			
			/* Line 4 */
			
			_windv2		= LoadArray.Float(xfr,"WINDV2",this,"getDeftWINDV2");
			_nomv2		= LoadArray.Float(xfr,"NOMV2",this,"getDeftNOMV2");
			_ang2		= LoadArray.Float(xfr,"ANG2",this,"getDeftANG2");
			_rata2		= LoadArray.Float(xfr,"RATA2",this,"getDeftRATA2");
			_ratb2		= LoadArray.Float(xfr,"RATB2",this,"getDeftRATB2");
			_ratc2		= LoadArray.Float(xfr,"RATC2",this,"getDeftRATC2");
			_cod2		= LoadArray.Int(xfr,"COD2",this,"getDeftCOD2");
			_cont2		= LoadArray.String(xfr,"CONT2",this,"getDeftCONT2");
			_rma2		= LoadArray.Float(xfr,"RMA2",this,"getDeftRMA2");
			_rmi2		= LoadArray.Float(xfr,"RMI2",this,"getDeftRMI2");
			_vma2		= LoadArray.Float(xfr,"VMA2",this,"getDeftVMA2");
			_vmi2		= LoadArray.Float(xfr,"VMI2",this,"getDeftVMI2");
			_ntp2		= LoadArray.Int(xfr,"NTP2",this,"getDeftNTP2");
			_tab2		= LoadArray.Int(xfr,"TAB2",this,"getDeftTAB2");
			_cr2		= LoadArray.Float(xfr,"CR2",this,"getDeftCR2");
			_cx2		= LoadArray.Float(xfr,"CX2",this,"getDeftCX2");
			
			/* Line 5 */
			
			_windv3		= LoadArray.Float(xfr,"WINDV3",this,"getDeftWINDV3");
			_nomv3		= LoadArray.Float(xfr,"NOMV3",this,"getDeftNOMV3");
			_ang3		= LoadArray.Float(xfr,"ANG3",this,"getDeftANG3");
			_rata3		= LoadArray.Float(xfr,"RATA3",this,"getDeftRATA3");
			_ratb3		= LoadArray.Float(xfr,"RATB3",this,"getDeftRATB3");
			_ratc3		= LoadArray.Float(xfr,"RATC3",this,"getDeftRATC3");
			_cod3		= LoadArray.Int(xfr,"COD3",this,"getDeftCOD3");
			_cont3		= LoadArray.String(xfr,"CONT3",this,"getDeftCONT3");
			_rma3		= LoadArray.Float(xfr,"RMA3",this,"getDeftRMA3");
			_rmi3		= LoadArray.Float(xfr,"RMI3",this,"getDeftRMI3");
			_vma3		= LoadArray.Float(xfr,"VMA3",this,"getDeftVMA3");
			_vmi3		= LoadArray.Float(xfr,"VMI3",this,"getDeftVMI3");
			_ntp3		= LoadArray.Int(xfr,"NTP3",this,"getDeftNTP3");
			_tab3		= LoadArray.Int(xfr,"TAB3",this,"getDeftTAB3");
			_cr3		= LoadArray.Float(xfr,"CR3",this,"getDeftCR3");
			_cx3		= LoadArray.Float(xfr,"CX3",this,"getDeftCX3");
			
			/* A default of 0 for NOMV* means to really use the bus base KV.  */
			fixNomKV();
			
			reindex();
			
		}
		catch(Exception e)
		{
			throw new PsseModelException(getClass().getName()+": "+e, e);
		}
	}
	
	private void fixNomKV() throws PsseModelException
	{
		for (int i=0; i < _size; ++i)
		{
			if (_nomv1[i] == 0f) _nomv1[i] = _buses.get(getI(i)).getBASKV();
			if (_nomv2[i] == 0f) _nomv2[i] = _buses.get(getJ(i)).getBASKV();
			if (_nomv3[i] == 0f)
			{
				String k = getK(i);
				if (!k.isEmpty() && !k.equals("0"))
				{
					_nomv3[i] = _buses.get(k).getBASKV();
				}
			}
		}
	}

	@Override
	public String getI(int ndx) { return _i[ndx]; }
	@Override
	public String getJ(int ndx) { return _j[ndx]; }
	@Override
	public String getK(int ndx) { return _k[ndx]; }
	@Override
	public String getCKT(int ndx) { return _ckt[ndx]; }
	@Override
	public int getCW(int ndx) { return _cw[ndx]; }
	@Override
	public int getCZ(int ndx) { return _cz[ndx]; }
	@Override
	public int getCM(int ndx) { return _cm[ndx]; }
	@Override
	public float getMAG1(int ndx) { return _mag1[ndx]; }
	@Override
	public float getMAG2(int ndx) { return _mag2[ndx]; }
	@Override
	public int getNMETR(int ndx) { return _nmetr[ndx]; }
	@Override
	public String getNAME(int ndx) { return _name[ndx]; }
	@Override
	public int getSTAT(int ndx) { return _stat[ndx]; }
	
	
	@Override
	public float getR1_2(int ndx) { return _r12[ndx]; }
	@Override
	public float getX1_2(int ndx) { return _x12[ndx]; }
	@Override
	public float getSBASE1_2(int ndx) { return _sb12[ndx]; }
	@Override
	public float getR2_3(int ndx) { return _r23[ndx]; }
	@Override
	public float getX2_3(int ndx) { return _x23[ndx]; }
	@Override
	public float getSBASE2_3(int ndx) { return _sb23[ndx]; }
	@Override
	public float getR3_1(int ndx) { return _r31[ndx]; }
	@Override
	public float getX3_1(int ndx) { return _x31[ndx]; }
	@Override
	public float getSBASE3_1(int ndx) { return _sb31[ndx]; }
	@Override
	public float getVMSTAR(int ndx) {return _vmstar[ndx];}
	@Override
	public float getANSTAR(int ndx) {return _anstar[ndx];}

	@Override
	public float getWINDV1(int ndx) { return _windv1[ndx]; }
	@Override
	public float getNOMV1(int ndx) { return _nomv1[ndx]; }
	@Override
	public float getANG1(int ndx) { return _ang1[ndx]; }
	@Override
	public float getRATA1(int ndx) { return _rata1[ndx]; }
	@Override
	public float getRATB1(int ndx) { return _ratb1[ndx]; }
	@Override
	public float getRATC1(int ndx) { return _ratc1[ndx]; }
	@Override
	public int getCOD1(int ndx) { return _cod1[ndx]; }
	@Override
	public String getCONT1(int ndx) { return _cont1[ndx]; }
	@Override
	public float getRMA1(int ndx) { return _rma1[ndx]; }
	@Override
	public float getRMI1(int ndx) { return _rmi1[ndx]; }
	@Override
	public float getVMA1(int ndx) { return _vma1[ndx]; }
	@Override
	public float getVMI1(int ndx) { return _vmi1[ndx]; }
	@Override
	public int getNTP1(int ndx) { return _ntp1[ndx]; }
	@Override
	public int getTAB1(int ndx) { return _tab1[ndx]; }
	@Override
	public float getCR1(int ndx) { return _cr1[ndx]; }
	@Override
	public float getCX1(int ndx) { return _cx1[ndx]; }

	@Override
	public float getWINDV2(int ndx) { return _windv2[ndx]; }
	@Override
	public float getNOMV2(int ndx) { return _nomv2[ndx]; }
	@Override
	public float getANG2(int ndx) { return _ang2[ndx]; }
	@Override
	public float getRATA2(int ndx) { return _rata2[ndx]; }
	@Override
	public float getRATB2(int ndx) { return _ratb2[ndx]; }
	@Override
	public float getRATC2(int ndx) { return _ratc2[ndx]; }
	@Override
	public int getCOD2(int ndx) { return _cod2[ndx]; }
	@Override
	public String getCONT2(int ndx) { return _cont2[ndx]; }
	@Override
	public float getRMA2(int ndx) { return _rma2[ndx]; }
	@Override
	public float getRMI2(int ndx) { return _rmi2[ndx]; }
	@Override
	public float getVMA2(int ndx) { return _vma2[ndx]; }
	@Override
	public float getVMI2(int ndx) { return _vmi2[ndx]; }
	@Override
	public int getNTP2(int ndx) { return _ntp2[ndx]; }
	@Override
	public int getTAB2(int ndx) { return _tab2[ndx]; }
	@Override
	public float getCR2(int ndx) { return _cr2[ndx]; }
	@Override
	public float getCX2(int ndx) { return _cx2[ndx]; }
	
	@Override
	public float getWINDV3(int ndx) { return _windv3[ndx]; }
	@Override
	public float getNOMV3(int ndx) { return _nomv3[ndx]; }
	@Override
	public float getANG3(int ndx) { return _ang3[ndx]; }
	@Override
	public float getRATA3(int ndx) { return _rata3[ndx]; }
	@Override
	public float getRATB3(int ndx) { return _ratb3[ndx]; }
	@Override
	public float getRATC3(int ndx) { return _ratc3[ndx]; }
	@Override
	public int getCOD3(int ndx) { return _cod3[ndx]; }
	@Override
	public String getCONT3(int ndx) { return _cont3[ndx]; }
	@Override
	public float getRMA3(int ndx) { return _rma3[ndx]; }
	@Override
	public float getRMI3(int ndx) { return _rmi3[ndx]; }
	@Override
	public float getVMA3(int ndx) { return _vma3[ndx]; }
	@Override
	public float getVMI3(int ndx) { return _vmi3[ndx]; }
	@Override
	public int getNTP3(int ndx) { return _ntp3[ndx]; }
	@Override
	public int getTAB3(int ndx) { return _tab3[ndx]; }
	@Override
	public float getCR3(int ndx) { return _cr3[ndx]; }
	@Override
	public float getCX3(int ndx) { return _cx3[ndx]; }


	public String getDeftK(int ndx) throws PsseModelException { return super.getK(ndx);}
	public String getDeftCKT(int ndx) throws PsseModelException  {return super.getCKT(ndx);}
	public int getDeftCW(int ndx) throws PsseModelException  {return super.getCW(ndx);}
	public int getDeftCZ(int ndx) throws PsseModelException  {return super.getCZ(ndx);}
	public int getDeftCM(int ndx) throws PsseModelException  {return super.getCM(ndx);}
	public float getDeftMAG1(int ndx) throws PsseModelException  {return super.getMAG1(ndx);}
	public float getDeftMAG2(int ndx) throws PsseModelException  {return super.getMAG2(ndx);}
	public int getDeftNMETR(int ndx) throws PsseModelException  {return super.getNMETR(ndx);}
	public String getDeftNAME(int ndx) throws PsseModelException  {return super.getNAME(ndx);}
	public int getDeftSTAT(int ndx) throws PsseModelException  {return super.getSTAT(ndx);}

	public float getDeftR1_2(int ndx) throws PsseModelException  {return super.getR1_2(ndx);}
	public float getDeftSBASE1_2(int ndx) throws PsseModelException  {return super.getSBASE1_2(ndx);}
	public float getDeftR2_3(int ndx) throws PsseModelException  {return super.getR2_3(ndx);}
	// technically there is no default for X, but if no 3-winding transformer exists, we need to allow the
	// array loader to have a primitive value at least as a placeholder. 
	public float getDeftX2_3(int ndx) throws PsseModelException  {return 0f;}
	public float getDeftSBASE2_3(int ndx) throws PsseModelException  {return super.getSBASE2_3(ndx);}
	public float getDeftR3_1(int ndx) throws PsseModelException  {return super.getR3_1(ndx);}
	// technically there is no default for X, but if no 3-winding transformer exists, we need to allow the
	// array loader to have a primitive value at least as a placeholder. 
	public float getDeftX3_1(int ndx) throws PsseModelException  {return 0f;}
	public float getDeftSBASE3_1(int ndx) throws PsseModelException  {return super.getSBASE3_1(ndx);}
	public float getDeftVMSTAR(int ndx) throws PsseModelException {return super.getVMSTAR(ndx);}
	public float getDeftANSTAR(int ndx) throws PsseModelException {return super.getANSTAR(ndx);}

	public float getDeftWINDV1(int ndx) throws PsseModelException  {return super.getWINDV1(ndx);}
	public float getDeftNOMV1(int ndx) throws PsseModelException  {return super.getNOMV1(ndx);}
	public float getDeftANG1(int ndx) throws PsseModelException  {return super.getANG1(ndx);}
	public float getDeftRATA1(int ndx) throws PsseModelException  {return super.getRATA1(ndx);}
	public float getDeftRATB1(int ndx) throws PsseModelException  {return super.getRATB1(ndx);}
	public float getDeftRATC1(int ndx) throws PsseModelException  {return super.getRATC1(ndx);}
	public int getDeftCOD1(int ndx) throws PsseModelException  {return super.getCOD1(ndx);}
	public String getDeftCONT1(int ndx) throws PsseModelException  {return super.getCONT1(ndx);}
	public float getDeftRMA1(int ndx) throws PsseModelException  {return super.getRMA1(ndx);}
	public float getDeftRMI1(int ndx) throws PsseModelException  {return super.getRMI1(ndx);}
	public float getDeftVMA1(int ndx) throws PsseModelException  {return super.getVMA1(ndx);}
	public float getDeftVMI1(int ndx) throws PsseModelException  {return super.getVMI1(ndx);}
	public int getDeftNTP1(int ndx) throws PsseModelException  {return super.getNTP1(ndx);}
	public int getDeftTAB1(int ndx) throws PsseModelException  {return super.getTAB1(ndx);}
	public float getDeftCR1(int ndx) throws PsseModelException  {return super.getCR1(ndx);}
	public float getDeftCX1(int ndx) throws PsseModelException  {return super.getCX1(ndx);}

	public float getDeftWINDV2(int ndx) throws PsseModelException  {return super.getWINDV2(ndx);}
	public float getDeftNOMV2(int ndx) throws PsseModelException  {return super.getNOMV2(ndx);}
	public float getDeftANG2(int ndx) throws PsseModelException  {return super.getANG2(ndx);}
	public float getDeftRATA2(int ndx) throws PsseModelException  {return super.getRATA2(ndx);}
	public float getDeftRATB2(int ndx) throws PsseModelException  {return super.getRATB2(ndx);}
	public float getDeftRATC2(int ndx) throws PsseModelException  {return super.getRATC2(ndx);}
	public int getDeftCOD2(int ndx) throws PsseModelException  {return super.getCOD2(ndx);}
	public String getDeftCONT2(int ndx) throws PsseModelException  {return super.getCONT2(ndx);}
	public float getDeftRMA2(int ndx) throws PsseModelException  {return super.getRMA2(ndx);}
	public float getDeftRMI2(int ndx) throws PsseModelException  {return super.getRMI2(ndx);}
	public float getDeftVMA2(int ndx) throws PsseModelException  {return super.getVMA2(ndx);}
	public float getDeftVMI2(int ndx) throws PsseModelException  {return super.getVMI2(ndx);}
	public int getDeftNTP2(int ndx) throws PsseModelException  {return super.getNTP2(ndx);}
	public int getDeftTAB2(int ndx) throws PsseModelException  {return super.getTAB2(ndx);}
	public float getDeftCR2(int ndx) throws PsseModelException  {return super.getCR2(ndx);}
	public float getDeftCX2(int ndx) throws PsseModelException  {return super.getCX2(ndx);}

	public float getDeftWINDV3(int ndx) throws PsseModelException  {return super.getWINDV3(ndx);}
	public float getDeftNOMV3(int ndx) throws PsseModelException  {return (getK(ndx).equals("0")) ? 0f : super.getNOMV3(ndx);}
	public float getDeftANG3(int ndx) throws PsseModelException  {return super.getANG3(ndx);}
	public float getDeftRATA3(int ndx) throws PsseModelException  {return super.getRATA3(ndx);}
	public float getDeftRATB3(int ndx) throws PsseModelException  {return super.getRATB3(ndx);}
	public float getDeftRATC3(int ndx) throws PsseModelException  {return super.getRATC3(ndx);}
	public int getDeftCOD3(int ndx) throws PsseModelException  {return super.getCOD3(ndx);}
	public String getDeftCONT3(int ndx) throws PsseModelException  {return super.getCONT3(ndx);}
	public float getDeftRMA3(int ndx) throws PsseModelException  {return super.getRMA3(ndx);}
	public float getDeftRMI3(int ndx) throws PsseModelException  {return super.getRMI3(ndx);}
	public float getDeftVMA3(int ndx) throws PsseModelException  {return super.getVMA3(ndx);}
	public float getDeftVMI3(int ndx) throws PsseModelException  {return super.getVMI3(ndx);}
	public int getDeftNTP3(int ndx) throws PsseModelException  {return super.getNTP3(ndx);}
	public int getDeftTAB3(int ndx) throws PsseModelException  {return super.getTAB3(ndx);}
	public float getDeftCR3(int ndx) throws PsseModelException  {return super.getCR3(ndx);}
	public float getDeftCX3(int ndx) throws PsseModelException  {return super.getCX3(ndx);}
	
	
	@Override
	public Bus getBusI(int ndx) throws PsseModelException
	{
		return _buses.get(getI(ndx));
	}

	@Override
	public Bus getBusJ(int ndx) throws PsseModelException
	{
		return _buses.get(getK(ndx));
	}

	@Override
	public Bus getBusK(int ndx) throws PsseModelException
	{
		String k = getK(ndx);
		return (k==null||k.equals("0")) ? null : _buses.get(k);
	}

	@Override
	public String getObjectID(int ndx)
	{
		StringBuilder sb = new StringBuilder(_i[ndx]);
		sb.append(':');
		sb.append(_j[ndx]);
		sb.append(':');
		if (!_k[ndx].equals("0"))
		{
			sb.append(_k[ndx]);
			sb.append(':');
		}
		sb.append(_ckt[ndx]);
		return sb.toString();
	}
	@Override
	public int size() { return _size; }
	
	static Object loadArray(Transformer3RawList rlist, int[] xfndx,
			String prop, Class<?> type) throws PsseModelException
	{
		int n = xfndx.length;
		Object rv = Array.newInstance(type, n);
		try
		{
			Method m = Transformer3RawList.class.getMethod("get" + prop,
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
	
	static int[] loadNmetr(Transformer3RawList rlist, int[] xfndx, int[] wndx)
	{
		int n = xfndx.length;
		int[] nmetr = new int[n];
		for(int i=0; i < n; ++i)
		{
			int nm = rlist.getNMETR(xfndx[i]);
			int w = wndx[i];
			nmetr[i] = (w == nm) ? 1 : 2;
		}
		return nmetr;
	}

	static int[] loadStat(Transformer3RawList rlist, int[] xfndx, int[] wndx)
	{
		int n = xfndx.length;
		int[] stat = new int[n];
		for(int i=0; i < n; ++i)
		{
			int s = rlist.getSTAT(xfndx[i]);
			int w = wndx[i];
			switch(s)
			{
				case 0:
				case 1: stat[i] = s; break;
				case 4: stat[i] = (w == 1) ? 0 : 1; break; 
				default: stat[i] = (w == s) ? 0 : 1;
			}
		}
		return stat;
	}
	
	static float[][] loadMag(Transformer3RawList rlist, int[] xfndx, int[] wndx)
	{
		int n = xfndx.length;
		float[] _mag1 = new float[n];
		float[] _mag2 = new float[n];
		for(int i=0; i < n; ++i)
		{
			if (wndx[i] == 1)
			{
				int tx = xfndx[i];
				_mag1[i] = rlist.getMAG1(tx);
				_mag2[i] = rlist.getMAG2(tx);
			}
		}
		
		return new float[][] {_mag1, _mag2};
	}

	static float[] loadSbase(Transformer3RawList rlist, int[] xfndx, int[] wndx)
	{
		int n = xfndx.length;
		float[] sbase = new float[n];
		for(int i=0; i < n; ++i)
		{
			int x = xfndx[i];
			switch(wndx[i])
			{
				case 1: sbase[i] = rlist.getSBASE1_2(x); break;
				case 2: sbase[i] = rlist.getSBASE2_3(x); break;
				case 3: sbase[i] = rlist.getSBASE3_1(x); break;
			}
		}
		return sbase;
	}


}
