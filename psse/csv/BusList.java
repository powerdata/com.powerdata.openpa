package com.powerdata.openpa.psse.csv;

import java.util.HashMap;

import com.powerdata.openpa.psse.AreaInterchange;
import com.powerdata.openpa.psse.BusTypeCode;
import com.powerdata.openpa.psse.Owner;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.psse.Zone;
//import com.powerdata.openpa.psse.Bus;
import com.powerdata.openpa.tools.BooleanAttrib;
import com.powerdata.openpa.tools.FloatAttrib;
import com.powerdata.openpa.tools.IntAttrib;
import com.powerdata.openpa.tools.LoadArray;
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
	float _gl[];
	float _bl[];
	
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
			this.getClass().getSuperclass();
			_name	= LoadArray.String(buses,"NAME",this,"getDeftNAME");
			_basekv	= LoadArray.Float(buses,"BASKV",this,"getDeftBASKV");
			_ide	= LoadArray.Int(buses,"IDE",this,"getDeftIDE");
			_area	= LoadArray.Int(buses,"AREA",this,"getDeftAREA");
			_zone	= LoadArray.Int(buses,"ZONE",this,"getDeftZONE");
			_owner	= LoadArray.Int(buses,"OWNER",this,"getDeftOWNER");
			_vm		= LoadArray.Float(buses,"VM",this,"getDeftVM");
			_va		= LoadArray.Float(buses,"VA",this,"getDeftVA");
			_gl		= LoadArray.Float(buses,"GL",this,"getDeftGL");
			_bl		= LoadArray.Float(buses,"BL",this,"getDeftBL");
			
			if (_i == null)
			{
				throw new PsseModelException(getClass().getName()+" missing I in "+dbfile);
			}
		}
		catch(Exception e)
		{
			throw new PsseModelException(getClass().getName()+": "+e);
		}
	}
	@Override
	public int getI(int ndx) { return _i[ndx]; }
	@Override
	public String getNAME(int ndx) { return _name[ndx]; }
	@Override
	public float getBASKV(int ndx) { return _basekv[ndx]; }
	@Override
	public int getIDE(int ndx) { return _ide[ndx]; }
	@Override
	public float getGL(int ndx) { return _gl[ndx]; }
	@Override
	public float getBL(int ndx) { return _bl[ndx]; }
	@Override
	public int getAREA(int ndx) { return _area[ndx]; }
	@Override
	public int getZONE(int ndx) { return _zone[ndx]; }
	@Override
	public float getVM(int ndx) { return _vm[ndx]; }
	@Override
	public float getVA(int ndx) { return _va[ndx]; }
	@Override
	public int getOWNER(int ndx) { return _owner[ndx]; }
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
	@Override
	public BusTypeCode getBusType(int ndx) { return getDeftBusType(ndx); }
	@Override
	public AreaInterchange getAreaObject(int ndx) throws PsseModelException
	{
		return getDeftAreaObject(ndx);
	}
	@Override
	public Zone getZoneObject(int ndx) throws PsseModelException { return getDeftZoneObject(ndx); }
	@Override
	public Owner getOwnerObject(int ndx) throws PsseModelException { return getDeftOwnerObject(ndx); }
	@Override
	public float getShuntG(int ndx) { return getDeftShuntG(ndx); }
	@Override
	public float getShuntB(int ndx) { return getDeftShuntB(ndx); }
	@Override
	public float getVaRad(int ndx) { return getDeftVaRad(ndx); }
}
