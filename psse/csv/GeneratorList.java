package com.powerdata.openpa.psse.csv;

import java.io.IOException;

import com.powerdata.openpa.psse.Bus;
import com.powerdata.openpa.psse.OwnershipList;
import com.powerdata.openpa.tools.BooleanAttrib;
import com.powerdata.openpa.tools.FloatAttrib;
import com.powerdata.openpa.tools.IntAttrib;
import com.powerdata.openpa.tools.SimpleCSV;
import com.powerdata.openpa.tools.StringAttrib;

public class GeneratorList extends com.powerdata.openpa.psse.GeneratorList<Generator>
{
	static final int I = 0;
	static final int ID = 1;
	static final int PG = 2;
	static final int QG = 3;
	static final int QT = 4;
	static final int QB = 5;
	static final int VS = 6;
	static final int IREG = 7;
	static final int MBASE = 8;
	static final int ZR = 9;
	static final int ZX = 10;
	static final int RT = 11;
	static final int XT = 12;
	static final int GTAP = 13;
	static final int STAT = 14;
	static final int RMPCT = 15;
	static final int PT = 16;
	static final int PB = 17;
	static final int O1 = 18;
	static final int F1 = 19;
	
	PsseEquipment _eq;
	BusList _busses;
	SimpleCSV _gens;
	int _size;

	public GeneratorList(PsseEquipment eq) throws IOException
	{
		_eq = eq;
		_gens = new SimpleCSV(_eq.getDir().getPath()+"/Machines.csv");
		_size = _gens.getRowCount();
		_busses = _eq.getBusses();
	}
	public String getName(int ndx) { return _gens.get(ID, ndx); }
	@Override
	public Bus getBus(int ndx) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getActvPwr(int ndx) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getReacPwr(int ndx) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getMaxReacPwr(int ndx) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getMinReacPwr(int ndx) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Bus getRemoteRegBus(int ndx) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public float getMachR(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public float getMachX(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public float getStepupR(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public float getStepupX(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public boolean inService(int ndx) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public float getMaxActvPwr(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public float getMinActvPwr(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public String getI(int ndx) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getID(int ndx) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getPG(int ndx) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getQG(int ndx) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getQT(int ndx) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getQB(int ndx) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public float getVS(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public float getIREG(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public float getMBASE(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public float getZR(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public float getZX(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public float getRT(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public float getXT(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public float getGTAP(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int getSTAT(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public float getRMPCT(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public float getPT(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public float getPB(int ndx) {
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
	public int getIndex(String id) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public StringAttrib<Generator> mapStringAttrib(String attribname) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public FloatAttrib<Generator> mapFloatAttrib(String attribname) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public IntAttrib<Generator> mapIntAttrib(String attribname) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public BooleanAttrib<Generator> mapBooleanAttrib(String attribname) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Generator get(int index) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}
}
