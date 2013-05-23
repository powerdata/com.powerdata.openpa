package com.powerdata.openpa.psse.csv;

import java.io.IOException;
import java.util.HashMap;

import com.powerdata.openpa.psse.Bus;
import com.powerdata.openpa.psse.OwnershipList;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.tools.BooleanAttrib;
import com.powerdata.openpa.tools.FloatAttrib;
import com.powerdata.openpa.tools.IntAttrib;
import com.powerdata.openpa.tools.SimpleCSV;
import com.powerdata.openpa.tools.StringAttrib;

public class TransformerList extends com.powerdata.openpa.psse.TransformerList<Transformer>
{
	PsseModel _eq;
	BusList _buses;
	HashMap<String,Integer> _objIDtoNdx = new HashMap<String,Integer>();
	int _size;
	
	String _i[],_j[],_k[],_ckt[];
	int _cw[],_cz[],_cm[];
	float _mag1[],_mag2[];
	int _nmetr[];
	String _name[];
	int _stat[];
	float _r1_2[],_x1_2[],_sbase1_2[];
	float _r2_3[],_x2_3[],_sbase2_3[];
	float _r3_1[],_x3_1[],_sbase3_1[];
	
	public TransformerList(PsseModel eq) throws PsseModelException
	{
		super(eq);
		try
		{
			_eq = eq;
			_buses = _eq.getBuses();
			SimpleCSV xfr = new SimpleCSV(_eq.getDir().getPath()+"/Transformers.csv");
			_size 		= xfr.getRowCount();
			_size		= xfr.getRowCount();
			_i			= xfr.get("I");
			_j			= xfr.get("J");
			_k			= xfr.get("k");
			_ckt		= xfr.get("CKT");
			_cw			= xfr.getInts("CW");
			_cz			= xfr.getInts("CZ");
			_cm			= xfr.getInts("CM");
			_mag1		= xfr.getFloats("MAG1");
			_mag2		= xfr.getFloats("MAG2");
			_nmetr		= xfr.getInts("NMETR");
			_name		= xfr.get("NAME");
			_stat		= xfr.getInts("STAT");
			_r1_2		= xfr.getFloats("R1-2");
			_x1_2		= xfr.getFloats("X1-2");
			_sbase1_2	= xfr.getFloats("SBASE1-2");
			_r2_3		= xfr.getFloats("R2-3");
			_x2_3		= xfr.getFloats("X2-3");
			_sbase2_3	= xfr.getFloats("SBASE2-3");
			_r3_1		= xfr.getFloats("R3-1");
			_x3_1		= xfr.getFloats("X3-1");
			_sbase3_1	= xfr.getFloats("SBASE3-1");
			for(int i=0; i<_size; i++) _objIDtoNdx.put(getObjectID(i),i);
		}
		catch(IOException e)
		{
			throw new PsseModelException(getClass().getName()+": "+e);
		}
	}

	@Override
	public String getI(int ndx) { return _i[ndx]; }
	@Override
	public String getJ(int ndx) { return _j[ndx]; }
	@Override
	public String getK(int ndx) { return (_k != null)?_k[ndx]:getDeftK(ndx); }
	@Override
	public Bus getBus1(int ndx) { return _buses.get(getI(ndx)); }
	@Override
	public Bus getBus2(int ndx) { return _buses.get(getJ(ndx)); }
	@Override
	public Bus getBus3(int ndx) { return _buses.get(getK(ndx)); }
	@Override
	public String getCKT(int ndx) { return (_ckt != null)?_ckt[ndx]:getDeftCKT(ndx); }
	@Override
	public int getCW(int ndx) { return (_cw != null)?_cw[ndx]:getDeftCW(ndx); }
	@Override
	public int getCZ(int ndx) {	return (_cz != null)?_cz[ndx]:getDeftCZ(ndx); }

	@Override
	public int getCM(int ndx) {
		// TODO Auto-generated method stub
		return getDeftCM(ndx);
	}

	@Override
	public float getMAG1(int ndx) {
		// TODO Auto-generated method stub
		return getDeftMAG1(ndx);
	}

	@Override
	public float getMAG2(int ndx) {
		// TODO Auto-generated method stub
		return getDeftMAG2(ndx);
	}

	@Override
	public int getNMETR(int ndx) {
		// TODO Auto-generated method stub
		return getDeftNMETR(ndx);
	}

	@Override
	public String getNAME(int ndx) {
		// TODO Auto-generated method stub
		return getDeftNAME(ndx);
	}

	@Override
	public int getSTAT(int ndx) {
		// TODO Auto-generated method stub
		return getDeftSTAT(ndx);
	}

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
	public OwnershipList<?> getOwnership(int ndx) { return null; }
	@Override
	public String getObjectID(int ndx)
	{
		return _i[ndx]+":"+_j[ndx]+":"+_k[ndx]+":"+_ckt[ndx];
	}
	@Override
	public Transformer get(String objectid)
	{
		Integer ndx = _objIDtoNdx.get(objectid);
		return (ndx != null)?new Transformer(ndx,this):null;
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
	public Transformer get(int index) { return new Transformer(index,this); }
	@Override
	public int size() { return _size; }

	@Override
	public float getVMSTAR(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getANSTAR(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getWINDV1(int ndx) throws PsseModelException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getNOMV1(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getANG1(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getRATA1(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getRATB1(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getRATC1(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getCOD1(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getCONT1(int ndx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getRMA1(int ndx) throws PsseModelException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getRMI1(int ndx) throws PsseModelException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getVMA1(int ndx) throws PsseModelException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getVMI1(int ndx) throws PsseModelException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNTP1(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTAB1(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getCR1(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getCX1(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getWINDV2(int ndx) throws PsseModelException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getNOMV2(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getANG2(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getRATA2(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getRATB2(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getRATC2(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getCOD2(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getCONT2(int ndx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getRMA2(int ndx) throws PsseModelException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getRMI2(int ndx) throws PsseModelException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getVMA2(int ndx) throws PsseModelException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getVMI2(int ndx) throws PsseModelException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNTP2(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTAB2(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getCR2(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getCX2(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getWINDV3(int ndx) throws PsseModelException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getNOMV3(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getANG3(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getRATA3(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getRATB3(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getRATC3(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getCOD3(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getCONT3(int ndx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getRMA3(int ndx) throws PsseModelException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getRMI3(int ndx) throws PsseModelException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getVMA3(int ndx) throws PsseModelException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getVMI3(int ndx) throws PsseModelException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNTP3(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTAB3(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getCR3(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getCX3(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}
}

