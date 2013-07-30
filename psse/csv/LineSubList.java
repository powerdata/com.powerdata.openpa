package com.powerdata.openpa.psse.csv;

import com.powerdata.openpa.psse.Bus;
import com.powerdata.openpa.psse.LineList;
import com.powerdata.openpa.psse.PsseModelException;

public class LineSubList extends com.powerdata.openpa.psse.LineSubList
{

	public LineSubList() {super();}
	public LineSubList(LineList lines, int[] ndxs) throws PsseModelException
	{
		super(lines, ndxs);
	}
	@Override
	public Bus getFromBus(int ndx) throws PsseModelException {return _model.getBus(getI(ndx));}
	@Override
	public Bus getToBus(int ndx) throws PsseModelException
	{
		String j = getJ(ndx);
		if (j.charAt(0)=='-') j = j.substring(1);
		if (j.equals("22938"))
		{
			int xxx = 5;
		}
		Bus rv = _model.getBus(j);
		if (rv.getIndex() == 8696)
		{
			int xxx = 5;
		}
		return rv;
	}
	
	
}
