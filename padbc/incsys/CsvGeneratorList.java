package com.powerdata.openpa.padbc.incsys;

import java.io.IOException;

import com.powerdata.openpa.padbc.BooleanAttrib;
import com.powerdata.openpa.padbc.FloatAttrib;
import com.powerdata.openpa.padbc.GeneratorList;
import com.powerdata.openpa.padbc.IntAttrib;
import com.powerdata.openpa.padbc.StringAttrib;
import com.powerdata.openpa.tools.SimpleCSV;

public class CsvGeneratorList extends GeneratorList<CsvGenerator>
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
	
	CsvEquipment _eq;
	SimpleCSV _gens;
	int _size;
	int _node[];
	public CsvGeneratorList(CsvEquipment eq) throws IOException
	{
		_eq = eq;
		_gens = new SimpleCSV(_eq.getDir().getPath()+"/Machines.csv");
		_size = _gens.getRowCount();
		CsvNodeList nodes = _eq.getNodes();
		_node = new int[_size];
		for(int i=0; i<_size; i++)
		{
			String busid = _gens.get(I,i);
			_node[i] = nodes.get(i).getNdx(busid);
		}
	}
	public String getName(int ndx) { return _gens.get(ID, ndx); }
	@Override
	public CsvGenerator get(int ndx) { return new CsvGenerator(ndx,this); }
	@Override
	public int getNode(int ndx) { return _node[ndx]; }
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
	public String getID(int ndx) { return "Gen:"+_gens.get(I,ndx)+":"+_gens.get(ID,ndx).trim();	}
	@Override
	public StringAttrib<CsvGenerator> mapStringAttrib(String attribname) {
		return null;
	}
	@Override
	public FloatAttrib<CsvGenerator> mapFloatAttrib(String attribname) {
		return null;
	}
	@Override
	public IntAttrib<CsvGenerator> mapIntAttrib(String attribname) {
		return null;
	}
	@Override
	public BooleanAttrib<CsvGenerator> mapBooleanAttrib(String attribname) {
		return null;
	}
	@Override
	public int size() { return _size; }
}
