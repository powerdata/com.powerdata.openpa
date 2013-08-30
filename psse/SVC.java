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
	public Bus getRegBus() throws PsseModelException {return _list.getRegBus(_ndx);}
	
	public String getI() throws PsseModelException {return _list.getI(_ndx);}
	public String getSWREM() throws PsseModelException {return _list.getSWREM(_ndx);}
	public float getRMPCT() throws PsseModelException {return _list.getRMPCT(_ndx);}
	public float getBINIT() throws PsseModelException {return _list.getBINIT(_ndx);}
	@Override
	public boolean isInSvc() throws PsseModelException {return _list.isInSvc(_ndx);}

	@Override
	public float getRTMW() throws PsseModelException {return _list.getRTMW(_ndx);}
	@Override
	public float getRTMVar() throws PsseModelException {return _list.getRTMVAr(_ndx);}
	@Override
	public void setRTMW(float mw) throws PsseModelException { _list.setRTMW(_ndx, mw);}
	@Override
	public void setRTMVAr(float mvar) throws PsseModelException { _list.setRTMVAr(_ndx, mvar);}
	@Override
	public float getRTP() throws PsseModelException {return _list.getRTP(_ndx);}
	@Override
	public void setRTP(float p) throws PsseModelException {_list.setRTP(_ndx, p);}
	@Override
	public float getRTQ() throws PsseModelException {return _list.getRTQ(_ndx);}
	@Override
	public void setRTQ(float q) throws PsseModelException {_list.setRTQ(_ndx, q);}
	
}
