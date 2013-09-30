package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.Complex;

public class Switch extends PsseBaseObject implements ACBranch
{
	protected SwitchList _list;
	
	public Switch(int ndx, SwitchList list)
	{
		super(list,ndx);
		_list = list;
	}

	@Override
	public String getDebugName() throws PsseModelException {return "Switch "+getName();}

	@Override
	public Bus getFromBus() throws PsseModelException {return _list.getFromBus(_ndx);}
	@Override
	public Bus getToBus() throws PsseModelException {return _list.getToBus(_ndx);}
	public String getName() throws PsseModelException {return _list.getName(_ndx);}
	public SwitchState getState() throws PsseModelException {return _list.getState(_ndx);}
	public void setState(SwitchState state) throws PsseModelException { _list.setState(_ndx,state); }
	public boolean canOperateUnderLoad() throws PsseModelException {return _list.canOperateUnderLoad(_ndx); }

	@Override
	public String getI() throws PsseModelException {return _list.getI(_ndx);}
	@Override
	public String getJ() throws PsseModelException {return _list.getJ(_ndx);}

	@Override
	public float getR() throws PsseModelException {return 0;}
	@Override
	public float getX() throws PsseModelException {return 0;}
	@Override
	public Complex getZ() throws PsseModelException {return Complex.Zero;}
	@Override
	public Complex getY() throws PsseModelException {return Complex.Zero;}
	@Override
	public float getFromTap() throws PsseModelException {return 1;}
	@Override
	public float getToTap() throws PsseModelException {return 1;}
	@Override
	public float getGmag() throws PsseModelException {return 0;}
	@Override
	public float getBmag() throws PsseModelException {return 0;}
	@Override
	public float getFromBchg() throws PsseModelException {return 0;}
	@Override
	public float getToBchg() throws PsseModelException {return 0;}
	@Override
	public float getPhaseShift() throws PsseModelException {return 0;}
	@Override
	public boolean isInSvc() throws PsseModelException {return _list.isInSvc(_ndx);}
}