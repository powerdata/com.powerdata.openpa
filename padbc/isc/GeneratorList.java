package com.powerdata.openpa.padbc.isc;

import java.io.IOException;

import com.powerdata.openpa.tools.BooleanAttrib;
import com.powerdata.openpa.tools.FloatAttrib;
import com.powerdata.openpa.tools.IntAttrib;
import com.powerdata.openpa.tools.SimpleCSV;
import com.powerdata.openpa.tools.StringAttrib;

public class GeneratorList extends com.powerdata.openpa.padbc.GeneratorList<Generator>
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
	
	Equipment _eq;
	NodeList _nodes;
	SimpleCSV _gens;
	int _size;

	public GeneratorList(Equipment eq) throws IOException
	{
		_eq = eq;
		_gens = new SimpleCSV(_eq.getDir().getPath()+"/Machines.csv");
		_size = _gens.getRowCount();
		_nodes = _eq.getNodes();
	}
	public String getName(int ndx) { return _gens.get(ID, ndx); }
	@Override
	public Generator get(int ndx) { return new Generator(ndx,this); }
	@Override
	public int getNode(int ndx)
	{
		String busid = _gens.get(I,ndx);
		return _nodes.get(ndx).getNdx(busid);
	}
	@Override
	public AVRMode getAVRMode(int ndx) { return null; }
	@Override
	public GenMode getGenMode(int ndx) { return null; }
	@Override
	public SyncMachMode getSyncMachMode(int ndx) { return null; }
	@Override
	public UnitType getUnitType(int ndx) { return null;	}
	@Override
	public float getActvPwr(int ndx) { return 0; }
	@Override
	public float getReacPwr(int ndx) { return 0; }
	@Override
	public float getMinOperActvPwr(int ndx) { return 0;	}
	@Override
	public float getMaxOperActvPwr(int ndx)
	{
		return Float.parseFloat(_gens.get(PT, ndx)) / 100;
	}
	@Override
	public float getMinVoltage(int ndx) { return 0;	}
	@Override
	public float getMaxVoltage(int ndx) { return 0;	}
	@Override
	public float getRegVoltage(int ndx) { return 0;	}
	@Override
	public float getRegReacPwr(int ndx) { return 0;	}
	@Override
	public int getRegNode(int ndx) { return 0; }
	@Override
	public float getR(int ndx) { return 0; }
	@Override
	public float getX(int ndx) { return 0; }
	@Override
	public float getInertia(int ndx) { return 0; }
	@Override
	public int getCtrlSwitch(int ndx) { return 0; }
	@Override
	public String getObjectID(int ndx) { return "Gen:"+_gens.get(I,ndx)+":"+_gens.get(ID,ndx).trim();	}
	@Override
	public StringAttrib<Generator> mapStringAttrib(String attribname) {
		return null;
	}
	@Override
	public FloatAttrib<Generator> mapFloatAttrib(String attribname) {
		return null;
	}
	@Override
	public IntAttrib<Generator> mapIntAttrib(String attribname) {
		return null;
	}
	@Override
	public BooleanAttrib<Generator> mapBooleanAttrib(String attribname) {
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
