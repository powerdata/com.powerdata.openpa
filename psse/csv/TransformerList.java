package com.powerdata.openpa.psse.csv;

import com.powerdata.openpa.psse.Bus;
import com.powerdata.openpa.psse.OwnershipList;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.tools.SimpleCSV;

public class TransformerList extends com.powerdata.openpa.psse.TransformerList
{
	PsseModel _eq;
	BusList _buses;
	int _size;
	
	String _i[],_j[],_ckt[],_name[],_cont1[];
	int _cm[], _cod1[], _cw[], _cz[], _nmetr[];
	int _ntp1[], _stat[], _tab1[];
	float _ang1[], _cr1[];
	float _cx1[], _mag1[], _mag2[];
	float _nomv1[],_r1_2[];
	float _rata1[], _ratb1[];
	float _ratc1[], _rma1[];
	float _rmi1[], _sbase1_2[];
	float _vma1[], _vmi1[];
	float _windv1[], _x1_2[];

	public TransformerList(PsseModel eq) throws PsseModelException
	{
		super(eq);
		try
		{
			_eq = eq;
			_buses = _eq.getBuses();
			SimpleCSV xfr = new SimpleCSV(_eq.getDir().getPath()+"/Transformers.csv");
//			_size 		= xfr.getRowCount();
//			_i			= xfr.get("I");
//			_j			= xfr.get("J");
//			_ckt		= LoadArray.String(xfr,"CKT",this,"getCKT");
//			_cw			= LoadArray.Int(xfr,"CW",this,"getCW");
//			_cz			= LoadArray.Int(xfr,"CZ",this,"getCZ");
//			_cm			= LoadArray.Int(xfr,"CM",this,"getCM");
//			_mag1		= LoadArray.Float(xfr,"MAG1",this,"getMAG1");
//			_mag2		= LoadArray.Float(xfr,"MAG2",this,"getMAG2");
//			_nmetr		= LoadArray.Int(xfr,"NMETR",this,"getNMETR");
//			_name		= LoadArray.String(xfr,"NAME",this,"getNAME");
//			_stat		= LoadArray.Int(xfr,"STAT",this,"getSTAT");
//			_r1_2		= LoadArray.Float(xfr,"R1-2",this,"getR1_2");
//			_sbase1_2	= LoadArray.Float(xfr,"SBASE1-2",this,"getSBASE1_2");
//			_windv1		= LoadArray.Float(xfr,"WINDV1",this,"getWINDV1");
//			_nomv1		= LoadArray.Float(xfr,"NOMV1",this,"getNOMV1");
//			_ang1		= LoadArray.Float(xfr,"ANG1",this,"getANG1");
//			_rata1		= LoadArray.Float(xfr,"RATA1",this,"getRATA1");
//			_ratb1		= LoadArray.Float(xfr,"RATB1",this,"getRATB1");
//			_ratc1		= LoadArray.Float(xfr,"RATC1",this,"getRATC1");
//			_cod1		= LoadArray.Int(xfr,"COD1",this,"getCOD1");
//			_cont1		= LoadArray.String(xfr,"CONT1",this,"getCONT1");
//			_rma1		= LoadArray.Float(xfr,"RMA1",this,"getRMA1");
//			_rmi1		= LoadArray.Float(xfr,"RMI1",this,"getRMI1");
//			_vma1		= LoadArray.Float(xfr,"VMA1",this,"getVMA1");
//			_vmi1		= LoadArray.Float(xfr,"VMI1",this,"getVMI1");
//			_ntp1		= LoadArray.Int(xfr,"NTP1",this,"getNTP1");
//			_tab1		= LoadArray.Int(xfr,"TAB1",this,"getTAB1");
//			_cr1		= LoadArray.Float(xfr,"CR1",this,"getCR1");
//			_cx1		= LoadArray.Float(xfr,"CX1",this,"getCX1");
			
			reindex();
		}
		catch(Exception e)
		{
			throw new PsseModelException(getClass().getName()+": "+e);
		}
	}

	@Override
	public Bus getBus1(int ndx) { return _buses.get(getI(ndx)); }
	@Override
	public Bus getBus2(int ndx) { return _buses.get(getJ(ndx)); }
	@Override
	public String getI(int ndx) { return _i[ndx]; }
	@Override
	public String getJ(int ndx) { return _j[ndx]; }
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
	public OwnershipList getOwnership(int ndx) { return null; }
	@Override
	public String getObjectID(int ndx)
	{ 
		return _i[ndx]+":"+_j[ndx]+":"+_ckt[ndx];
	}
	@Override
	public int size() { return _size; }

}
