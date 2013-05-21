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
	/** parent container */
	PsseEquipment _eq;
	/** translate I to an offset */
	HashMap<String,Integer> _idToNdx = new HashMap<String,Integer>();
	/** number of items in the DB */
	int _size;
	
	// Base values from the CSV file
	String _i[];
	String _name[];
	float _basekv[];
	int _ide[];
	int _area[];
	int _zone[];
	int _owner[];
	float _vm[];
	float _va[];
	int _gl[];
	int _bl[];
	int _type[];
	
	public BusList(PsseEquipment eq) throws IOException
	{
		_eq = eq;
		SimpleCSV buses = new SimpleCSV(_eq.getDir().getPath()+"/Buses.csv");
		_size = buses.getRowCount();
		_i		= buses.get("I");
		for(int i=0; i<_size; i++) _idToNdx.put(_i[i], i);
		_name	= buses.get("NAME");
		_basekv	= buses.getFloats("BASKV");
		_ide	= buses.getInts("IDE");
		_area	= buses.getInts("AREA");
		_zone	= buses.getInts("ZONE");
		_owner	= buses.getInts("OWNER");
		_vm		= buses.getFloats("VM");
		_va		= buses.getFloats("VA");
		_gl		= buses.getInts("GL");
		_bl		= buses.getInts("BL");
		_type	= buses.getInts("Flag");
	}
	@Override
	public BusTypeCode getBusType(int ndx)
	{
		return (_type != null)?BusTypeCode.fromCode(_type[ndx]):BusTypeCode.Unknown;
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
	public String getI(int ndx) { return _i[ndx]; }
	@Override
	public String getNAME(int ndx) { return (_name != null)?_name[ndx]:""; }
	@Override
	public float getBASKV(int ndx) { return (_basekv != null)?_basekv[ndx]:0; }
	@Override
	public int getIDE(int ndx) { return (_ide != null)?_ide[ndx]:0; }
	@Override
	public int getGL(int ndx) { return (_gl != null)?_gl[ndx]:0; }
	@Override
	public int getBL(int ndx) { return (_bl != null)?_bl[ndx]:0; }
	@Override
	public int getAREA(int ndx) { return (_area != null)?_area[ndx]:0; }
	@Override
	public int getZONE(int ndx) { return (_zone != null)?_zone[ndx]:0; }
	@Override
	public float getVM(int ndx) { return (_vm != null)?_vm[ndx]:0; }
	@Override
	public float getVA(int ndx) { return (_va != null)?_va[ndx]:0; }
	@Override
	public int getOWNER(int ndx) { return (_owner != null)?_owner[ndx]:0; }
	@Override
	public String getObjectID(int ndx) { return _i[ndx];	}
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
	public int size() { return _size; }
}
