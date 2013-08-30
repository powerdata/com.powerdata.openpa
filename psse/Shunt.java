package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.Complex;

public class Shunt extends PsseBaseObject implements OneTermDev
{

	protected ShuntList	_list;

	public Shunt(int ndx, ShuntList list)
	{
		super(list,ndx);
		_list = list;
	}

	@Override
	public Bus getBus() throws PsseModelException {return _list.getBus(_ndx);}

	/** shunt nominal susceptance, p.u. at nominal bus kv */
	public float getGpu() throws PsseModelException {return _list.getGpu(_ndx);}
	/** shunt nominal susceptance, p.u. at nominal bus kv */
	public float getBpu() throws PsseModelException {return _list.getBpu(_ndx);}
	/** shunt complex admittance p.u. at nominal bus kv */
	public Complex getY() throws PsseModelException {return _list.getY(_ndx);}

	/** is Switched on */
	public boolean isSwitchedOn() throws PsseModelException {return _list.isSwitchedOn(_ndx);}
	@Override
	public boolean isInSvc() throws PsseModelException {return _list.isInSvc(_ndx);}
	/** get connected bus */
	public String getI() throws PsseModelException {return _list.getI(_ndx);}
	/** shunt nominal B in MVAr at unity bus voltage */
	public float getB() throws PsseModelException {return _list.getB(_ndx);}
	/** shunt nominal G in MW at unity bus voltage */
	public float getG() throws PsseModelException {return _list.getG(_ndx);}

	@Override
	public float getRTMW() throws PsseModelException {return _list.getRTMW(_ndx);}
	@Override
	public float getRTMVar() throws PsseModelException {return _list.getRTMVAr(_ndx);}
	@Override
	public void setRTMW(float mw) throws PsseModelException {_list.setRTMW(_ndx, mw);}
	@Override
	public void setRTMVAr(float mvar) throws PsseModelException {_list.setRTMVAr(_ndx, mvar);}
	@Override
	public float getRTP() throws PsseModelException {return _list.getRTP(_ndx);}
	@Override
	public void setRTP(float p) throws PsseModelException {_list.setRTP(_ndx, p);}
	@Override
	public float getRTQ() throws PsseModelException {return _list.getRTQ(_ndx);}
	@Override
	public void setRTQ(float q) throws PsseModelException {_list.setRTQ(_ndx, q);}
}
