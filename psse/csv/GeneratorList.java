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
	BusList _buses;
	SimpleCSV _gens;

	public GeneratorList(PsseEquipment eq) throws IOException
	{
		_eq = eq;
		_gens = new SimpleCSV(_eq.getDir().getPath()+"/Machines.csv");
		_buses = _eq.getBuses();
	}
	public String getName(int ndx) { return _gens.get(ID, ndx); }
	@Override
	public Bus getBus(int ndx) { return null; }
	@Override
	public float getActvPwr(int ndx) { return 0; }
	@Override
	public float getReacPwr(int ndx) { return 0; }
	@Override
	public float getMaxReacPwr(int ndx) { return 0; }
	@Override
	public float getMinReacPwr(int ndx) { return 0; }
	@Override
	public Bus getRemoteRegBus(int ndx) { return null; }
	@Override
	public float getMachR(int ndx) { return 0; }
	@Override
	public float getMachX(int ndx) { return 0; }
	@Override
	public float getStepupR(int ndx) { return 0; }
	@Override
	public float getStepupX(int ndx) { return 0; }
	@Override
	public boolean inService(int ndx) { return false; }
	@Override
	public float getMaxActvPwr(int ndx) { return 0; }
	@Override
	public float getMinActvPwr(int ndx) { return 0; }
	@Override
	public String getI(int ndx) { return _gens.get(I, ndx); }
	@Override
	public String getID(int ndx) { return _gens.get(ID, ndx); }
	@Override
	public float getPG(int ndx) { return 0; }
	@Override
	public float getQG(int ndx) { return 0; }
	@Override
	public float getQT(int ndx) { return 0; }
	@Override
	public float getQB(int ndx) { return 0; }
	@Override
	public float getVS(int ndx) { return 0; }
	@Override
	public float getIREG(int ndx) { return 0; }
	@Override
	public float getMBASE(int ndx) { return 0; }
	@Override
	public float getZR(int ndx) { return 0; }
	@Override
	public float getZX(int ndx) { return 0; }
	@Override
	public float getRT(int ndx) { return 0;	}
	@Override
	public float getXT(int ndx) { return 0; }
	@Override
	public float getGTAP(int ndx) { return 0; }
	@Override
	public int getSTAT(int ndx) { return 0; }
	@Override
	public float getRMPCT(int ndx) { return 0; }
	@Override
	public float getPT(int ndx) { return 0; }
	@Override
	public float getPB(int ndx) { return 0; }
	@Override
	public OwnershipList<?> getOwnership(int ndx) { return null; }
	@Override
	public String getObjectID(int ndx) { return null; }
	@Override
	public int getIndex(String id) { return 0; }
	@Override
	public StringAttrib<Generator> mapStringAttrib(String attribname) { return null; }
	@Override
	public FloatAttrib<Generator> mapFloatAttrib(String attribname) { return null; }
	@Override
	public IntAttrib<Generator> mapIntAttrib(String attribname) { return null; }
	@Override
	public BooleanAttrib<Generator> mapBooleanAttrib(String attribname) { return null; }
	@Override
	public Generator get(int index) { return new Generator(index,this); }
	@Override
	public int size() { return _gens.getRowCount(); }
}
