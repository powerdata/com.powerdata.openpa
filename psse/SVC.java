package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.Complex;

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
	public Complex getY() throws PsseModelException {return _list.getY(_ndx);}
	
	public String getI() throws PsseModelException {return _list.getI(_ndx);}
	public String getSWREM() throws PsseModelException {return _list.getSWREM(_ndx);}
	public float getRMPCT() throws PsseModelException {return _list.getRMPCT(_ndx);}
	public float getBINIT() throws PsseModelException {return _list.getBINIT(_ndx);}

	@Override
	public void setRTS(Complex s) throws PsseModelException {_list.setRTS(_ndx, s);}
	@Override
	public Complex getRTS() throws PsseModelException {return _list.getRTS(_ndx);}
	public Complex getRTY() throws PsseModelException {return _list.getRTY(_ndx);}
	public void setRTY(Complex y) throws PsseModelException {_list.setRTY(_ndx, y);}

	@Override
	public float getRTMW() throws PsseModelException
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getRTMVar() throws PsseModelException
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setRTMW(float mw) throws PsseModelException
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setRTMVar(float mvar) throws PsseModelException
	{
		// TODO Auto-generated method stub
		
	}

}
