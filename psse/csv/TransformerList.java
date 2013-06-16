package com.powerdata.openpa.psse.csv;

import com.powerdata.openpa.psse.BusIn;
import com.powerdata.openpa.psse.ImpCorrTbl;
import com.powerdata.openpa.psse.OwnershipList;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.psse.Transformer;
import com.powerdata.openpa.psse.TransformerCtrlMode;
import com.powerdata.openpa.psse.TransformerStatus;
import com.powerdata.openpa.tools.BooleanAttrib;
import com.powerdata.openpa.tools.Complex;
import com.powerdata.openpa.tools.FloatAttrib;
import com.powerdata.openpa.tools.IntAttrib;
import com.powerdata.openpa.tools.LoadArray;
import com.powerdata.openpa.tools.SimpleCSV;
import com.powerdata.openpa.tools.StringAttrib;

public class TransformerList extends com.powerdata.openpa.psse.TransformerList
{
	PsseInputModel _eq;
	BusInList _buses;
	int _size;
	
	String _i[],_j[],_k[],_ckt[],_name[],_cont1[],_cont2[],_cont3[];
	int _cm[], _cod1[], _cod2[], _cod3[], _cw[], _cz[], _nmetr[];
	int _ntp1[], _ntp2[], _ntp3[], _stat[], _tab1[], _tab2[], _tab3[];
	float _ang1[], _ang2[], _ang3[], _anstar[], _cr1[], _cr2[], _cr3[];
	float _cx1[], _cx2[], _cx3[], _mag1[], _mag2[];
	float _nomv1[], _nomv2[], _nomv3[],_r1_2[], _r2_3[], _r3_1[];
	float _rata1[], _rata2[], _rata3[], _ratb1[], _ratb2[], _ratb3[];
	float _ratc1[], _ratc2[], _ratc3[], _rma1[], _rma2[], _rma3[];
	float _rmi1[], _rmi2[], _rmi3[], _sbase1_2[], _sbase2_3[], _sbase3_1[];
	float _vma1[], _vma2[], _vma3[], _vmi1[], _vmi2[], _vmi3[], _vmstar[];
	float _windv1[], _windv2[], _windv3[], _x1_2[], _x2_3[], _x3_1[];

	public TransformerList(PsseInputModel eq) throws PsseModelException
	{
		super(eq);
		try
		{
			_eq = eq;
			_buses = _eq.getBuses();
			SimpleCSV xfr = new SimpleCSV(_eq.getDir().getPath()+"/Transformers.csv");
			_size 		= xfr.getRowCount();
			_i			= xfr.get("I");
			_j			= xfr.get("J");
			_k			= LoadArray.String(xfr,"K",this,"getDeftK");
			_ckt		= LoadArray.String(xfr,"CKT",this,"getDeftCKT");
			_cw			= LoadArray.Int(xfr,"CW",this,"getDeftCW");
			_cz			= LoadArray.Int(xfr,"CZ",this,"getDeftCZ");
			_cm			= LoadArray.Int(xfr,"CM",this,"getDeftCM");
			_mag1		= LoadArray.Float(xfr,"MAG1",this,"getDeftMAG1");
			_mag2		= LoadArray.Float(xfr,"MAG2",this,"getDeftMAG2");
			_nmetr		= LoadArray.Int(xfr,"NMETR",this,"getDeftNMETR");
			_name		= LoadArray.String(xfr,"NAME",this,"getDeftNAME");
			_stat		= LoadArray.Int(xfr,"STAT",this,"getDeftSTAT");
			_r1_2		= LoadArray.Float(xfr,"R1-2",this,"getDeftR1_2");
			_sbase1_2	= LoadArray.Float(xfr,"SBASE1-2",this,"getDeftSBASE1_2");
			_r2_3		= LoadArray.Float(xfr,"R2-3",this,"getDeftR2_3");
			_sbase2_3	= LoadArray.Float(xfr,"SBASE2-3",this,"getDeftSBASE2_3");
			_r3_1		= LoadArray.Float(xfr,"R3-1",this,"getDeftR3_1");
			_sbase3_1	= LoadArray.Float(xfr,"SBASE3-1",this,"getDeftSBASE3_1");
			_vmstar		= LoadArray.Float(xfr,"VMSTAR",this,"getDeftVMSTAR");
			_anstar		= LoadArray.Float(xfr,"ANSTAR",this,"getDeftANSTAR");
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
			
			reindex();
		}
		catch(Exception e)
		{
			throw new PsseModelException(getClass().getName()+": "+e);
		}
	}

	@Override
	public BusIn getBus1(int ndx) { return _buses.get(getI(ndx)); }
	@Override
	public BusIn getBus2(int ndx) { return _buses.get(getJ(ndx)); }
	@Override
	public BusIn getBus3(int ndx) { return _buses.get(getK(ndx)); }
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
	public float getR1_2(int ndx) { return _r1_2[ndx]; }
	@Override
	public float getX1_2(int ndx) { return _x1_2[ndx]; }
	@Override
	public float getSBASE1_2(int ndx) { return _sbase1_2[ndx]; }
	@Override
	public float getR2_3(int ndx) { return _r2_3[ndx]; }
	@Override
	public float getX2_3(int ndx) { return _x2_3[ndx]; }
	@Override
	public float getSBASE2_3(int ndx) { return _sbase2_3[ndx]; }
	@Override
	public float getR3_1(int ndx) { return _r3_1[ndx]; }
	@Override
	public float getX3_1(int ndx) { return _x3_1[ndx]; }
	@Override
	public float getSBASE3_1(int ndx) { return _sbase3_1[ndx]; }
	@Override
	public float getVMSTAR(int ndx) { return _vmstar[ndx]; }
	@Override
	public float getANSTAR(int ndx) { return _anstar[ndx]; }
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
	@Override
	public OwnershipList<?> getOwnership(int ndx) { return null; }
	@Override
	public String getObjectID(int ndx)
	{ 
		return _i[ndx]+":"+_j[ndx]+":"+_k[ndx]+":"+_ckt[ndx];
	}
	@Override
	public StringAttrib<Transformer> mapStringAttrib(String attribname) { return null; }
	@Override
	public FloatAttrib<Transformer> mapFloatAttrib(String attribname) { return null; }
	@Override
	public IntAttrib<Transformer> mapIntAttrib(String attribname) { return null; }
	@Override
	public BooleanAttrib<Transformer> mapBooleanAttrib(String attribname) { return null; }
	@Override
	public int size() { return _size; }

	@Override
	public float getMagG(int ndx) throws PsseModelException {return getDeftMagG(ndx);}
	@Override
	public float getMagB(int ndx) throws PsseModelException {return getDeftMagB(ndx);}
	@Override
	public Complex getMagY(int ndx) throws PsseModelException {return getDeftMagY(ndx);}
	@Override
	public TransformerStatus getInSvc(int ndx) throws PsseModelException {return getDeftInSvc(ndx);}
	@Override
	public float getR100_1_2(int ndx) throws PsseModelException {return getDeftR100_1_2(ndx);}
	@Override
	public float getX100_1_2(int ndx) throws PsseModelException {return getDeftX100_1_2(ndx);}
	@Override
	public float getR100_2_3(int ndx) throws PsseModelException {return getDeftR100_2_3(ndx);}
	@Override
	public float getX100_2_3(int ndx) throws PsseModelException {return getDeftX100_2_3(ndx);}
	@Override
	public float getR100_3_1(int ndx) throws PsseModelException {return getDeftR100_3_1(ndx);}
	@Override
	public float getX100_3_1(int ndx) throws PsseModelException {return getDeftX100_3_1(ndx);}
	@Override
	public float getWnd1Ratio(int ndx) throws PsseModelException {return getDeftWnd1Ratio(ndx);}
	@Override
	public float getWnd1NomKV(int ndx) throws PsseModelException {return getDeftWnd1NomKV(ndx);}
	@Override
	public TransformerCtrlMode getCtrlMode1(int ndx) throws PsseModelException {return getDeftCtrlMode1(ndx);}
	@Override
	public boolean getAdjEnab1(int ndx) throws PsseModelException {return getDeftAdjEnab1(ndx);}
	@Override
	public BusIn getRegBus1(int ndx) throws PsseModelException {return getDeftRegBus1(ndx);}
	@Override
	public boolean getCtrlTapSide1(int ndx) throws PsseModelException {return getDeftCtrlTapSide1(ndx);}
	@Override
	public float getMaxRatio1(int ndx) throws PsseModelException {return getDeftMaxRatio1(ndx);}
	@Override
	public float getMinRatio1(int ndx) throws PsseModelException {return getDeftMinRatio1(ndx);}
	@Override
	public float getMaxShift1(int ndx) throws PsseModelException {return getDeftMaxShift1(ndx);}
	@Override
	public float getMinShift1(int ndx) throws PsseModelException {return getDeftMinShift1(ndx);}

	@Override
	public float getMaxVolt1(int ndx) throws PsseModelException {return getDeftMaxVolt1(ndx);}
	@Override
	public float getMinVolt1(int ndx) throws PsseModelException {return getDeftMinVolt1(ndx);}
	@Override
	public float getMaxReacPwr1(int ndx) throws PsseModelException {return getDeftMaxReacPwr1(ndx);}
	@Override
	public float getMinReacPwr1(int ndx) throws PsseModelException {return getDeftMinReacPwr1(ndx);}
	@Override
	public float getMaxActvPwr1(int ndx) throws PsseModelException {return getDeftMaxActvPwr1(ndx);}
	@Override
	public float getMinActvPwr1(int ndx) throws PsseModelException {return getDeftMinActvPwr1(ndx);}
	@Override
	public ImpCorrTbl getImpCorrTbl1(int ndx) throws PsseModelException {return getDeftImpCorrTbl1(ndx);}
	@Override
	public float getWnd2Ratio(int ndx) throws PsseModelException {return getDeftWnd2Ratio(ndx);}
	@Override
	public float getWnd2NomKV(int ndx) throws PsseModelException {return getDeftWnd2NomKV(ndx);}
	@Override
	public TransformerCtrlMode getCtrlMode2(int ndx) throws PsseModelException {return getDeftCtrlMode2(ndx);}
	@Override
	public boolean getAdjEnab2(int ndx) throws PsseModelException {return getDeftAdjEnab2(ndx);}
	@Override
	public BusIn getRegBus2(int ndx) throws PsseModelException {return getDeftRegBus2(ndx);}
	@Override
	public boolean getCtrlTapSide2(int ndx) throws PsseModelException {return getDeftCtrlTapSide2(ndx);}
	@Override
	public float getMaxRatio2(int ndx) throws PsseModelException {return getDeftMaxRatio2(ndx);}
	@Override
	public float getMinRatio2(int ndx) throws PsseModelException {return getDeftMinRatio2(ndx);}
	@Override
	public float getMaxShift2(int ndx) throws PsseModelException {return getDeftMaxShift2(ndx);}
	@Override
	public float getMinShift2(int ndx) throws PsseModelException {return getDeftMinShift2(ndx);}
	@Override
	public float getMaxVolt2(int ndx) throws PsseModelException {return getDeftMaxVolt2(ndx);}
	@Override
	public float getMinVolt2(int ndx) throws PsseModelException {return getDeftMinVolt2(ndx);}
	@Override
	public float getMaxReacPwr2(int ndx) throws PsseModelException {return getDeftMaxReacPwr2(ndx);}
	@Override
	public float getMinReacPwr2(int ndx) throws PsseModelException {return getDeftMinReacPwr2(ndx);}
	@Override
	public float getMaxActvPwr2(int ndx) throws PsseModelException {return getDeftMaxActvPwr2(ndx);}
	@Override
	public float getMinActvPwr2(int ndx) throws PsseModelException {return getDeftMinActvPwr2(ndx);}
	@Override
	public ImpCorrTbl getImpCorrTbl2(int ndx) throws PsseModelException {return getDeftImpCorrTbl2(ndx);}
	@Override
	public float getWnd3Ratio(int ndx) throws PsseModelException {return getDeftWnd3Ratio(ndx);}
	@Override
	public float getWnd3NomKV(int ndx) throws PsseModelException {return getDeftWnd3NomKV(ndx);}
	@Override
	public TransformerCtrlMode getCtrlMode3(int ndx) throws PsseModelException {return getDeftCtrlMode3(ndx);}
	@Override
	public boolean getAdjEnab3(int ndx) throws PsseModelException {return getDeftAdjEnab3(ndx);}
	@Override
	public BusIn getRegBus3(int ndx) throws PsseModelException {return getDeftRegBus3(ndx);}
	@Override
	public boolean getCtrlTapSide3(int ndx) throws PsseModelException {return getDeftCtrlTapSide3(ndx);}
	@Override
	public float getMaxRatio3(int ndx) throws PsseModelException {return getDeftMaxRatio3(ndx);}
	@Override
	public float getMinRatio3(int ndx) throws PsseModelException {return getDeftMinRatio3(ndx);}
	@Override
	public float getMaxShift3(int ndx) throws PsseModelException {return getDeftMaxShift3(ndx);}
	@Override
	public float getMinShift3(int ndx) throws PsseModelException {return getDeftMinShift3(ndx);}
	@Override
	public float getMaxVolt3(int ndx) throws PsseModelException {return getDeftMaxVolt3(ndx);}
	@Override
	public float getMinVolt3(int ndx) throws PsseModelException {return getDeftMinVolt3(ndx);}
	@Override
	public float getMaxReacPwr3(int ndx) throws PsseModelException {return getDeftMaxReacPwr3(ndx);}
	@Override
	public float getMinReacPwr3(int ndx) throws PsseModelException {return getDeftMinReacPwr3(ndx);}
	@Override
	public float getMaxActvPwr3(int ndx) throws PsseModelException {return getDeftMaxActvPwr3(ndx);}
	@Override
	public float getMinActvPwr3(int ndx) throws PsseModelException {return getDeftMinActvPwr3(ndx);}
	@Override
	public ImpCorrTbl getImpCorrTbl3(int ndx) throws PsseModelException {return getDeftImpCorrTbl3(ndx);}

	@Override
	public Complex getZ100_1_2(int ndx) throws PsseModelException {return getDeftZ100_1_2(ndx);}
	@Override
	public Complex getZ100_2_3(int ndx) throws PsseModelException {return getDeftZ100_2_3(ndx);}
	@Override
	public Complex getZ100_3_1(int ndx) throws PsseModelException {return getDeftZ100_3_1(ndx);}

	@Override
	public float getWnd1PhaseShift(int ndx) throws PsseModelException {return getDeftWnd1PhaseShift(ndx);}
	@Override
	public float getWnd2PhaseShift(int ndx) throws PsseModelException {return getDeftWnd2PhaseShift(ndx);}
	@Override
	public float getWnd3PhaseShift(int ndx) throws PsseModelException {return getDeftWnd3PhaseShift(ndx);}
}
