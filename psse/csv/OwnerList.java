package com.powerdata.openpa.psse.csv;

import java.io.File;

import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.tools.LoadArray;
import com.powerdata.openpa.tools.SimpleCSV;

public class OwnerList extends com.powerdata.openpa.psse.OwnerList
{
	int[] _i;
	PsseRawModel _eq;
	int _size;
	String[] _owname;

	public OwnerList(PsseRawModel model, File dir) throws PsseModelException
	{
		super(model);
		_eq = model;
		try
		{
			File dbfile = new File(dir, "Owner.csv");
			SimpleCSV own = new SimpleCSV(dbfile);
			_size	= own.getRowCount();
			_i = own.getInts("I");
			_owname = LoadArray.String(own, "OWNAME", this, "getDeftOWNAME");
		}
		catch(Exception e)
		{
			throw new PsseModelException(e);
		}
	}
	
	public String getDeftOWNAME(int ndx) throws PsseModelException
	{
		return super.getOWNAME(ndx);
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
	public int size()
	{
		return _size;
	}

	@Override
	public String getOWNAME(int ndx)
	{
		return _owname[ndx];
	}


}
