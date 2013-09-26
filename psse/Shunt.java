package com.powerdata.openpa.psse;

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
	public float getP() throws PsseModelException {return _list.getP(_ndx);}
	@Override
	public float getQ() throws PsseModelException {return _list.getQ(_ndx);}
	@Override
	public void setP(float mw) throws PsseModelException {_list.setP(_ndx, mw);}
	@Override
	public void setQ(float mvar) throws PsseModelException {_list.setQ(_ndx, mvar);}
	@Override
	public float getPpu() throws PsseModelException {return _list.getPpu(_ndx);}
	@Override
	public void setPpu(float p) throws PsseModelException {_list.setPpu(_ndx, p);}
	@Override
	public float getQpu() throws PsseModelException {return _list.getQpu(_ndx);}
	@Override
	public void setQpu(float q) throws PsseModelException {_list.setQpu(_ndx, q);}
}
