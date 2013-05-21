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
/**
 * Implement bus for CSV.  Currently based on Robin's version.
 * 
 * @author marck
 */
public class BusList extends com.powerdata.openpa.psse.BusList<Bus>
{
	// not mapped yet
	static final int GL = -1;
	static final int BL = -1;
	
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
	SimpleCSV _buses;
	
	public BusList(PsseEquipment eq) throws IOException
	{
		_eq = eq;
		_buses = new SimpleCSV(_eq.getDir().getPath()+"/Buses.csv");
		int size = _buses.getRowCount();
		for(int i=0; i<size; i++) _idToNdx.put(_buses.get(I, i), i);
	}
	@Override
	public BusTypeCode getBusType(int ndx)
	{
		return BusTypeCode.fromCode(_buses.getInt(FLAG, ndx));
	}
	@Override
	public float getShuntConductance(int ndx) { return 0; }
	@Override
	public float getShuntSusceptance(int ndx) {	return 0; }
	@Override
	public AreaInterchange getAreaObject(int ndx) {	return null; }
	@Override
	public Zone getZoneObject(int ndx) { return null; }
	@Override
	public Owner getOwnerObject(int ndx) { return null;	}
	@Override
	public float getVangRad(int ndx) { return 0; }
	@Override
	public int getI(int ndx) { return _buses.getInt(I, ndx); }
	@Override
	public String getNAME(int ndx) { return _buses.get(NAME, ndx); }
	@Override
	public float getBASKV(int ndx) { return _buses.getFloat(BASEKV, ndx); }
	@Override
	public int getIDE(int ndx) { return _buses.getInt(IDE, ndx); }
	@Override
	public int getGL(int ndx) { return 0; }
	@Override
	public int getBL(int ndx) { return 0; }
	@Override
	public int getAREA(int ndx) { return _buses.getInt(AREA, ndx); }
	@Override
	public int getZONE(int ndx) { return _buses.getInt(ZONE, ndx); }
	@Override
	public float getVM(int ndx) { return _buses.getFloat(VM, ndx); }
	@Override
	public float getVA(int ndx) { return _buses.getFloat(VA, ndx); }
	@Override
	public int getOWNER(int ndx) { return _buses.getInt(OWNER, ndx); }
	@Override
	public String getObjectID(int ndx) { return _buses.get(I, ndx);	}
	@Override
	public int getIndex(String id)
	{
		Integer ndx = _idToNdx.get(id);
		return (ndx != null)?ndx:-1;
	}
	@Override
	public StringAttrib<Bus> mapStringAttrib(String attribname) { return null; }
	@Override
	public FloatAttrib<Bus> mapFloatAttrib(String attribname) { return null; }
	@Override
	public IntAttrib<Bus> mapIntAttrib(String attribname) { return null; }
	@Override
	public BooleanAttrib<Bus> mapBooleanAttrib(String attribname) { return null; }
	@Override
	public Bus get(int index) { return new Bus(index,this);	}
	@Override
	public int size() { return _buses.getRowCount(); }
}
