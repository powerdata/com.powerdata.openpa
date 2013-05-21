package com.powerdata.openpa.psse.csv;

import java.io.IOException;
import java.util.HashMap;

import com.powerdata.openpa.psse.AreaInterchange;
import com.powerdata.openpa.psse.Owner;
import com.powerdata.openpa.psse.Zone;
import com.powerdata.openpa.tools.BooleanAttrib;
import com.powerdata.openpa.tools.FloatAttrib;
import com.powerdata.openpa.tools.IntAttrib;
import com.powerdata.openpa.tools.SimpleCSV;
import com.powerdata.openpa.tools.StringAttrib;

public class BusList extends com.powerdata.openpa.psse.BusList<Bus>
{
	static final int FLAG = 0;
	static final int I = 1;
	static final int NAME = 2;
	static final int BASEKV = 3;
	static final int IDE = 4;
	static final int AREA = 5;
	static final int ZONE = 6;
	static final int OWNER = 7;
	static final int VM = 8;
	static final int VA = 9;
	
	PsseEquipment _eq;
	HashMap<String,Integer> _idToNdx = new HashMap<String,Integer>();
	SimpleCSV _nodes;
	int _size = 0;
	
	public BusList(PsseEquipment eq) throws IOException
	{
		_eq = eq;
		_nodes = new SimpleCSV(_eq.getDir().getPath()+"/Buses.csv");
		_size = _nodes.getRowCount();
		for(int i=0; i<_size; i++) _idToNdx.put(_nodes.get(I, i), i);
	}
	@Override
	public com.powerdata.openpa.psse.BusList.BusTypeCode getBusType(int ndx) {
		return null;
	}
	@Override
	public float getShuntConductance(int ndx) {
		return 0;
	}
	@Override
	public float getShuntSusceptance(int ndx) {
		return 0;
	}
	@Override
	public AreaInterchange getAreaObject(int ndx) {
		return null;
	}
	@Override
	public Zone getZoneObject(int ndx) {
		return null;
	}
	@Override
	public Owner getOwnerObject(int ndx) {
		return null;
	}
	@Override
	public float getVangRad(int ndx) {
		return 0;
	}
	@Override
	public int getI(int ndx) {
		return 0;
	}
	@Override
	public String getNAME(int ndx) {
		return null;
	}
	@Override
	public float getBASKV(int ndx) {
		return 0;
	}
	@Override
	public int getIDE(int ndx) {
		return 0;
	}
	@Override
	public int getGL(int ndx) {
		return 0;
	}
	@Override
	public int getBL(int ndx) {
		return 0;
	}

	@Override
	public int getAREA(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getZONE(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getVM(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getVA(int ndx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getOWNER(int ndx) {
		// TODO Auto-generated method stub
		return 0;
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
	public StringAttrib<Bus> mapStringAttrib(String attribname) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FloatAttrib<Bus> mapFloatAttrib(String attribname) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IntAttrib<Bus> mapIntAttrib(String attribname) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BooleanAttrib<Bus> mapBooleanAttrib(String attribname) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Bus get(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}
}
