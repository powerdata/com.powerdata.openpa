package com.powerdata.openpa.psse.csv;

import java.io.IOException;

import com.powerdata.openpa.psse.Bus;
import com.powerdata.openpa.psse.OwnershipList;
import com.powerdata.openpa.tools.BooleanAttrib;
import com.powerdata.openpa.tools.FloatAttrib;
import com.powerdata.openpa.tools.IntAttrib;
import com.powerdata.openpa.tools.SimpleCSV;
import com.powerdata.openpa.tools.StringAttrib;

public class NontransformerBranchList extends com.powerdata.openpa.psse.NontransformerBranchList<NontransformerBranch>
{
	static final int I = 0;
	static final int J = 1;
	static final int CKT = 2;
	static final int R = 3;
	static final int X = 4;
	static final int B = 5;
	static final int RATEA = 6;
	static final int RATEB = 7;
	static final int RATEC = 8;
	static final int GI = 9;
	static final int BI = 10;
	static final int GJ = 11;
	static final int BT = 12;
	static final int ST = 13;
	static final int LEN = 14;
	static final int O1 = 15;
	static final int F1 = 16;
	static final int I1 = 17;
	
	PsseEquipment _eq;
	BusList _buses;
	SimpleCSV _branches;
	int _size;
	
	public NontransformerBranchList(PsseEquipment eq) throws IOException
	{
		super(eq);
		_eq = eq;
		_branches = new SimpleCSV(_eq.getDir().getPath()+"/Branches.csv");
		_size = _branches.getRowCount();
		_buses = _eq.getBuses();
	}

	@Override
	public Bus getFromBus(int ndx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Bus getToBus(int ndx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MeteredEnd getMeteredEnd(int ndx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean inService(int ndx) {
		// TODO Auto-generated method stub
		return false;
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
	public String getCKT(int ndx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getR(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getX(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getB(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getRATEA(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getRATEB(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getRATEC(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getGI(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getBI(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getGJ(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getBJ(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getST(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getLEN(int ndx) {
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
	public StringAttrib<NontransformerBranch> mapStringAttrib(String attribname) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FloatAttrib<NontransformerBranch> mapFloatAttrib(String attribname) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IntAttrib<NontransformerBranch> mapIntAttrib(String attribname) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BooleanAttrib<NontransformerBranch> mapBooleanAttrib(
			String attribname) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NontransformerBranch get(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NontransformerBranch get(String objectid) {throw new UnsupportedOperationException();}
	
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

}
