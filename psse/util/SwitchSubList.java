package com.powerdata.openpa.psse.util;

import com.powerdata.openpa.psse.Bus;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.psse.Switch;
import com.powerdata.openpa.psse.SwitchList;
import com.powerdata.openpa.psse.SwitchState;

public class SwitchSubList extends SwitchList
{
	SwitchList _base;
	int[] _ndxs;
	boolean _indexed = false;
	
	public SwitchSubList() {super();}
	public SwitchSubList(SwitchList switches, int[] ndxs) throws PsseModelException
	{
		super(switches.getPsseModel());
		_base = switches;
		_ndxs = ndxs;
	}

	
	@Override
	public Switch get(String id)
	{
		if (!_indexed)
		{
			_indexed = true;
			try
			{
				reindex();
			} catch (PsseModelException e)
			{
				e.printStackTrace();
			}
		}
		return super.get(id);
	}

	protected int map(int ndx) {return _ndxs[ndx];}
	@Override
	public Bus getFromBus(int ndx) throws PsseModelException {return _base.getFromBus(map(ndx));}
	@Override
	public Bus getToBus(int ndx) throws PsseModelException {return _base.getToBus(map(ndx));}
	@Override
	@Deprecated // use getObjectName
	public String getName(int ndx) throws PsseModelException {return _base.getName(map(ndx));}
	@Override
	public SwitchState getState(int ndx) throws PsseModelException {return _base.getState(map(ndx));}
	@Override
	public void setState(int ndx, SwitchState state) throws PsseModelException {_base.setState(map(ndx), state);}
	@Override
	public boolean canOperateUnderLoad(int ndx) throws PsseModelException
	{
		return _base.canOperateUnderLoad(map(ndx));
	}
	@Override
	public String getI(int ndx) throws PsseModelException {return _base.getI(map(ndx));}
	@Override
	public String getJ(int ndx) throws PsseModelException {return _base.getJ(map(ndx));}
	@Override
	public boolean isInSvc(int ndx) throws PsseModelException {return _base.isInSvc(map(ndx));}
	@Override
	public void commit() throws PsseModelException {_base.commit();}
	@Override
	public String getObjectID(int ndx) throws PsseModelException {return _base.getObjectID(map(ndx));}
	@Override
	public String getObjectName(int ndx) throws PsseModelException {return _base.getObjectName(map(ndx));}
	@Override
	public String getFullName(int ndx) throws PsseModelException {return _base.getFullName(map(ndx));}
	@Override
	public String getDebugName(int ndx) throws PsseModelException {return _base.getDebugName(map(ndx));}
	@Override
	public int getRootIndex(int ndx) {return _base.getRootIndex(map(ndx));}
	@Override
	public int size() {return _ndxs.length;}
	@Override
	public void setInSvc(int ndx, boolean state) throws PsseModelException {_base.setInSvc(map(ndx), state);}
	@Override
	public long getKey(int ndx) throws PsseModelException {return _base.getKey(map(ndx));}
}
