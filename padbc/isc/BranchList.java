package com.powerdata.openpa.padbc.isc;

import java.io.IOException;

import com.powerdata.openpa.tools.BooleanAttrib;
import com.powerdata.openpa.tools.FloatAttrib;
import com.powerdata.openpa.tools.IntAttrib;
import com.powerdata.openpa.tools.SimpleCSV;
import com.powerdata.openpa.tools.StringAttrib;

public class BranchList extends com.powerdata.openpa.padbc.BranchList<Branch>
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
	
	Equipment _eq;
	NodeList _nodes;
	SimpleCSV _branches;
	int _size;
	
	public BranchList(Equipment eq) throws IOException
	{
		_eq = eq;
		_branches = new SimpleCSV(_eq.getDir().getPath()+"/Branches.csv");
		_size = _branches.getRowCount();
		_nodes = _eq.getNodes();
	}

	@Override
	public int getFromNode(int ndx)
	{
		String busid = _branches.get(I,ndx);
		return _nodes.get(ndx).getNdx(busid);
	}
	@Override
	public int getToNode(int ndx)
	{
		String busid = _branches.get(J,ndx);
		return _nodes.get(ndx).getNdx(busid);
	}
	@Override
	public float getR(int ndx) { return 0; }
	@Override
	public float getX(int ndx) { return 0; }
	@Override
	public float getFromBChg(int ndx)
	{
		return Float.parseFloat(_branches.get(B, ndx));
	}
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
	public Branch get(int ndx) { return new Branch(ndx,this); }
	@Override
	public String getID(int ndx)
	{
		return _branches.get(I, ndx)+":"+_branches.get(J, ndx)+":"+_branches.get(CKT, ndx);
	}
	@Override
	public StringAttrib<Branch> mapStringAttrib(String attribname) {
		return null;
	}
	@Override
	public FloatAttrib<Branch> mapFloatAttrib(String attribname) {
		return null;
	}
	@Override
	public IntAttrib<Branch> mapIntAttrib(String attribname) {
		return null;
	}
	@Override
	public BooleanAttrib<Branch> mapBooleanAttrib(String attribname) {
		return null;
	}
	@Override
	public int size() { return _size; }

	@Override
	public int getIndex(String id)
	{
		// TODO Auto-generated method stub
		return 0;
	}
}
