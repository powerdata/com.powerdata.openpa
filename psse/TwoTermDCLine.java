package com.powerdata.openpa.psse;

public class TwoTermDCLine extends PsseBaseObject implements TwoTermDev
{
	protected TwoTermDCLineList _list;
	
	public TwoTermDCLine(TwoTermDCLineList list, int ndx)
	{
		super(list, ndx);
		_list = list;
	}

	@Override
	public String getI() throws PsseModelException {return _list.getI(_ndx);}
	@Override
	public String getJ() throws PsseModelException {return _list.getJ(_ndx);}
	@Override
	public Bus getFromBus() throws PsseModelException {return _list.getFromBus(_ndx);}
	@Override
	public Bus getToBus() throws PsseModelException {return _list.getToBus(_ndx);}
	
	
}
