package com.powerdata.openpa.psse.csv;

import java.io.File;

import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.tools.ComplexList;
import com.powerdata.openpa.tools.LoadArray;
import com.powerdata.openpa.tools.SimpleCSV;
/**
 * Implement bus for CSV.  Currently based on Robin's version.
 * 
 * @author marck
 */
public class BusListRaw extends com.powerdata.openpa.psse.csv.BusList
{
	PsseModel _eq;
	
	public BusListRaw(File dir, PsseModel model) throws PsseModelException
	{
		super(model);
		_eq = model;
		try
		{
			File dbfile = new File(dir, "Bus.csv");
			SimpleCSV buses = new SimpleCSV(dbfile);
			_size	= buses.getRowCount();
			_i		= buses.getInts("I");
			_ids	= buses.get("I");
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

			reindex();
			
			if (_i == null)
			{
				throw new PsseModelException(getClass().getName()+" missing I in "+dbfile);
			}
			
			_mm = new ComplexList(_size, true);
		}
		catch(Exception e)
		{
			throw new PsseModelException(getClass().getName()+": "+e);
		}
	}
	public String getDeftNAME(int ndx) throws PsseModelException {return super.getNAME(ndx);}
	public float getDeftBASKV(int ndx) throws PsseModelException {return super.getBASKV(ndx);}
	public int getDeftIDE(int ndx) throws PsseModelException {return super.getIDE(ndx);}
	public float getDeftGL(int ndx) throws PsseModelException {return super.getGL(ndx);}
	public float getDeftBL(int ndx) throws PsseModelException {return super.getBL(ndx);}
	public int getDeftAREA(int ndx) throws PsseModelException {return super.getAREA(ndx);}
	public int getDeftZONE(int ndx) throws PsseModelException {return super.getZONE(ndx);}
	public float getDeftVM(int ndx) throws PsseModelException {return super.getVM(ndx);}
	public float getDeftVA(int ndx) throws PsseModelException {return super.getVA(ndx);}
	public int getDeftOWNER(int ndx) throws PsseModelException {return super.getOWNER(ndx);}
	

}
