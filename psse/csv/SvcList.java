package com.powerdata.openpa.psse.csv;

import java.util.List;

import com.powerdata.openpa.psse.PsseModel;
import com.powerdata.openpa.psse.PsseModelException;

public class SvcList extends com.powerdata.openpa.psse.SvcList
{
	int _size;
	
	int[] _i, _swrem;
	float[] _rmpct, _binit, _minB, _maxB;
	String[] _id;
	
	
	public SvcList() {super();}
	public SvcList(PsseModel model, SwitchedShuntRawList raw,
			List<Integer> svcndx) throws PsseModelException
	{
		super(model);

	}

	@Override
	public String getObjectID(int ndx) throws PsseModelException {return _id[ndx];}
	@Override
	public int size() {return _size;}
	@Override
	public String getI(int ndx) throws PsseModelException {return _buses.get(_i[ndx]).getObjectID();}
	

}
