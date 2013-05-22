package com.powerdata.openpa.psse.csv;

import java.io.IOException;
import java.util.HashMap;

import com.powerdata.openpa.psse.PsseModelException;
//import com.powerdata.openpa.psse.Bus;
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
	PsseModel _eq;
	/** translate I to an offset */
	HashMap<String,Integer> _idToNdx = new HashMap<String,Integer>();
	/** number of items in the DB */
	int _size;
	/** object IDs (really just the bus number) */
	String _ids[];
	
	// Base values from the CSV file
	int _i[];
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
	
	public BusList(PsseModel eq) throws PsseModelException
	{
		super(eq);
		try
		{
			_eq 	= eq;
			String dbfile = _eq.getDir().getPath()+"/Buses.csv";
			SimpleCSV buses = new SimpleCSV(dbfile);
			_size	= buses.getRowCount();
			_i		= buses.getInts("I");
			_ids	= buses.get("I");
			for(int i=0; i<_size; i++) _idToNdx.put(_ids[i], i);
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
			if (_i == null)
			{
				throw new PsseModelException(getClass().getName()+" missing I in "+dbfile);
			}
		}
		catch(IOException e)
		{
			throw new PsseModelException(getClass().getName()+": "+e);
		}
	}
	@Override
	public int getI(int ndx) { return _i[ndx]; }
	@Override
	public String getNAME(int ndx) { return (_name != null)?_name[ndx]:super.getNAME(ndx); }
	@Override
	public float getBASKV(int ndx) { return (_basekv != null)?_basekv[ndx]:super.getBASKV(ndx); }
	@Override
	public int getIDE(int ndx) { return (_ide != null)?_ide[ndx]:super.getIDE(ndx); }
	@Override
	public float getGL(int ndx) { return (_gl != null)?_gl[ndx]:super.getGL(ndx); }
	@Override
	public float getBL(int ndx) { return (_bl != null)?_bl[ndx]:super.getBL(ndx); }
	@Override
	public int getAREA(int ndx) { return (_area != null)?_area[ndx]:super.getAREA(ndx); }
	@Override
	public int getZONE(int ndx) { return (_zone != null)?_zone[ndx]:super.getZONE(ndx); }
	@Override
	public float getVM(int ndx) { return (_vm != null)?_vm[ndx]:super.getVM(ndx); }
	@Override
	public float getVA(int ndx) { return (_va != null)?_va[ndx]:super.getVA(ndx); }
	@Override
	public int getOWNER(int ndx) { return (_owner != null)?_owner[ndx]:super.getOWNER(ndx); }
	@Override
	public String getObjectID(int ndx) { return _ids[ndx];	}
	@Override
	public Bus get(String id)
	{
		Integer ndx = _idToNdx.get(id);
		return (ndx == null) ? null : get(ndx);
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
