package com.powerdata.openpa.psse.csv;

import java.io.File;

import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.tools.LoadArray;
import com.powerdata.openpa.tools.SimpleCSV;

public class AreaList extends com.powerdata.openpa.psse.AreaList
{
	int[] _i;
	float[] _pdes, _ptol;
	String[] _arname, _isw;	
	PsseRawModel _eq;
	int _size;
	
	public AreaList(PsseRawModel model, File dir) throws PsseModelException
	{
		super(model);
		_eq = model;
		try
		{
			File dbfile = new File(dir, "AreaInterchange.csv");
			SimpleCSV area = new SimpleCSV(dbfile);
			_size	= area.getRowCount();
			_i = area.getInts("I");
			_isw = LoadArray.String(area, "ISW", this, "getDeftISW");
			_pdes = LoadArray.Float(area, "PDES", this, "getDeftPDES");
			_ptol = LoadArray.Float(area, "PTOL", this, "getDeftPTOL");
			_arname = LoadArray.String(area, "ARNAME", this, "getDeftARNAME");
		}
		catch(Exception e)
		{
			throw new PsseModelException(e);
		}

	}

	public String getDeftISW(int ndx) throws PsseModelException
	{
		return super.getISW(ndx);
	}

	public float getDeftPDES(int ndx) throws PsseModelException
	{
		return super.getPDES(ndx);
	}

	public float getDeftPTOL(int ndx) throws PsseModelException
	{
		return super.getPTOL(ndx);
	}

	public String getDeftARNAME(int ndx) throws PsseModelException
	{
		return super.getARNAME(ndx);
	}
	
	@Override
	public int getI(int ndx) throws PsseModelException
	{
		return _i[ndx];
	}

	@Override
	public String getObjectID(int ndx) throws PsseModelException
	{
		return String.valueOf(_i[ndx]);
	}

	@Override
	public long getKey(int ndx) throws PsseModelException
	{
		return ndx;
	}

	
	
	@Override
	public String getISW(int ndx) throws PsseModelException
	{
		return _isw[ndx];
	}

	@Override
	public String getARNAME(int ndx) throws PsseModelException
	{
		return _arname[ndx];
	}

	@Override
	public float getPDES(int ndx) throws PsseModelException
	{
		return _pdes[ndx];
	}

	@Override
	public float getPTOL(int ndx) throws PsseModelException
	{
		return _ptol[ndx];
	}

	@Override
	public int size()
	{
		return _size;
	}
}
