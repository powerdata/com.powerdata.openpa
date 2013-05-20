package com.powerdata.openpa.padbc.incsys;

import java.io.IOException;

import com.powerdata.openpa.padbc.BooleanAttrib;
import com.powerdata.openpa.padbc.BranchList;
import com.powerdata.openpa.padbc.FloatAttrib;
import com.powerdata.openpa.padbc.IntAttrib;
import com.powerdata.openpa.padbc.StringAttrib;
import com.powerdata.openpa.tools.SimpleCSV;

public class CsvBranchList extends BranchList<CsvBranch>
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
	
	CsvEquipment _eq;
	SimpleCSV _branches;
	int _size;
	int _i[];
	int _j[];
	
	public CsvBranchList(CsvEquipment eq) throws IOException
	{
		_eq = eq;
		_branches = new SimpleCSV(_eq.getDir().getPath()+"/Branches.csv");
		_size = _branches.getRowCount();
		CsvNodeList nodes = _eq.getNodes();
		_i = new int[_size];
		_j = new int[_size];
		for(int i=0; i<_size; i++)
		{
			String busid = _branches.get(I,i);
			_i[i] = nodes.get(i).getNdx(busid);
			busid = _branches.get(J, i);
			_j[i] = nodes.get(i).getNdx(busid);
		}
	}

	@Override
	public int getFromNode(int ndx) { return _i[ndx]; }
	@Override
	public int getToNode(int ndx) { return _j[ndx]; }
	@Override
	public float getR(int ndx) { return 0; }
	@Override
	public float getX(int ndx) { return 0; }
	@Override
	public float getFromBChg(int ndx) { return 0; }
	@Override
	public float getToBChg(int ndx) { return 0; }
	@Override
	public float getFromTapRatio(int ndx) { return 0; }
	@Override
	public float getToTapRatio(int ndx) { return 0; }
	@Override
	public float getPhaseShift(int ndx) { return 0; }
	@Override
	public void updateActvPower(int ndx, float p) { }
	@Override
	public void updateReacPower(int ndx, float q) { }
	@Override
	public CsvBranch get(int ndx) { return new CsvBranch(ndx,this); }
	@Override
	public String getID(int ndx)
	{
		return _branches.get(I, ndx)+":"+_branches.get(J, ndx)+":"+_branches.get(CKT, ndx);
	}
	@Override
	public StringAttrib<CsvBranch> mapStringAttrib(String attribname) {
		return null;
	}
	@Override
	public FloatAttrib<CsvBranch> mapFloatAttrib(String attribname) {
		return null;
	}
	@Override
	public IntAttrib<CsvBranch> mapIntAttrib(String attribname) {
		return null;
	}
	@Override
	public BooleanAttrib<CsvBranch> mapBooleanAttrib(String attribname) {
		return null;
	}
	@Override
	public int size() { return _size; }
}
