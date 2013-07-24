package com.powerdata.openpa.psse;

public class ShuntSubList extends ShuntList
{
	ShuntList _shunts;
	int _ndxs[];
	public ShuntSubList(ShuntList switches, int ndxs[]) throws PsseModelException
	{
		super(switches._model);
		_shunts = switches;
		_ndxs = ndxs;
	}
	@Override
	public void commit() throws PsseModelException
	{
		_shunts.commit();
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
	@Override
	public String getI(int ndx) throws PsseModelException
	{
		// TODO Auto-generated method stub
		return null;
	}

}
