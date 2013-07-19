package com.powerdata.openpa.psse.csv;

import com.powerdata.openpa.psse.Bus;
import com.powerdata.openpa.psse.PsseModelException;

public class PhaseShifterList extends com.powerdata.openpa.psse.PhaseShifterList
{
	int _size;

	int[] _i, _j;

	public PhaseShifterList(PsseModel model, TransformerRawList rlist,
			TransformerPrep prep) throws PsseModelException
	{
		super(model);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Bus getFromBus(int ndx) throws PsseModelException {return _buses.get(_i[ndx]);}
	@Override
	public Bus getToBus(int ndx) throws PsseModelException {return _buses.get(_j[ndx]);}

	@Override
	public String getI(int ndx) throws PsseModelException {return _buses.get(_i[ndx]).getObjectID();}
	@Override
	public String getJ(int ndx) throws PsseModelException {return _buses.get(_j[ndx]).getObjectID();}


	@Override
	public float getX1_2(int ndx) throws PsseModelException
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getObjectID(int ndx) throws PsseModelException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size()
	{
		// TODO Auto-generated method stub
		return 0;
	}
}
