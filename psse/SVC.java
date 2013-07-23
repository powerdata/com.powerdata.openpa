package com.powerdata.openpa.psse;

public class SVC extends PsseBaseObject implements OneTermDev
{

	protected SvcList	_list;

	public SVC(int ndx, SvcList list)
	{
		super(list,ndx);
		_list = list;
	}

	@Override
	public Bus getBus() throws PsseModelException {return _list.getBus(_ndx);}
	public float getVoltageSetpoint() throws PsseModelException {return _list.getVoltageSetpoint(_ndx);}
	public Limits getBLimits() throws PsseModelException {return _list.getBLimits(_ndx);}
	public Bus getRegBus() throws PsseModelException {return _list.getRegBus(_ndx);}
	
	public String getI() throws PsseModelException {return _list.getI(_ndx);}
	public String getSWREM() throws PsseModelException {return _list.getSWREM(_ndx);}
	public float getRMPCT() throws PsseModelException {return _list.getRMPCT(_ndx);}
	public float getBINIT() throws PsseModelException {return _list.getBINIT(_ndx);}
	

}
