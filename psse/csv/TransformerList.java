package com.powerdata.openpa.psse.csv;

import java.io.IOException;

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
	static final int I = 0;
	static final int J = 1;
	static final int K = 2;

	PsseModel _eq;
	BusList _buses;
	SimpleCSV _xfr;
	int _size;
	
	public TransformerList(PsseModel eq) throws PsseModelException
	{
		super(eq);
		try
		{
			_eq = eq;
			_xfr = new SimpleCSV(_eq.getDir().getPath()+"/Transformers.csv");
			_size = _xfr.getRowCount();
			_buses = _eq.getBuses();
		}
		catch(IOException e)
		{
			throw new PsseModelException(getClass().getName()+": "+e);
		}
	}

	@Override
	public Bus getBus1(int ndx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Bus getBus2(int ndx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Bus getBus3(int ndx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getMagCondPerUnit(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getMagSuscPerUnit(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getNoLoadLoss(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getExcitingCurrent(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public com.powerdata.openpa.psse.TransformerStatus getInitTransformerStat(
			int ndx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getResistance1_2(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getReactance1_2(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getResistance2_3(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getReactance2_3(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getResistance3_1(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getReactance3_1(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getI(int ndx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getJ(int ndx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getK(int ndx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCKT(int ndx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getCW(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getCZ(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getCM(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getMAG1(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getMAG2(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNMETR(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getNAME(int ndx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getSTAT(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getR1_2(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getX1_2(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getSBASE1_2(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getR2_3(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getX2_3(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getSBASE2_3(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getR3_1(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getX3_1(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getSBASE3_1(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public OwnershipList<?> getOwnership(int ndx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getObjectID(int ndx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StringAttrib<Transformer> mapStringAttrib(String attribname) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FloatAttrib<Transformer> mapFloatAttrib(String attribname) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IntAttrib<Transformer> mapIntAttrib(String attribname) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BooleanAttrib<Transformer> mapBooleanAttrib(String attribname) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Transformer get(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Transformer get(String objectid) {throw new UnsupportedOperationException();}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

}

