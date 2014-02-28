package com.powerdata.openpa.psse.util;

import com.powerdata.openpa.psse.Bus;
import com.powerdata.openpa.psse.Limits;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.psse.SVC;
import com.powerdata.openpa.psse.SVC.ControlMode;
import com.powerdata.openpa.psse.SVC.State;
import com.powerdata.openpa.psse.SvcList;

public class SvcSubList extends SvcList
{
	SvcList _base;
	int[] _ndxs;
	boolean _indexed = false;
		
	public SvcSubList(SvcList base, int[] ndxs) throws PsseModelException
	{
		super(base.getPsseModel());
		_base = base;
		_ndxs = ndxs;
	}
	
	protected int map(int ndx) {return _ndxs[ndx];}

	@Override
	public SVC get(String id)
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

	@Override
	public Bus getBus(int ndx) throws PsseModelException {return _base.getBus(map(ndx));}
	@Override
	public Bus getRegBus(int ndx) throws PsseModelException {return _base.getRegBus(map(ndx));}
	@Override
	public String getI(int ndx) throws PsseModelException {return _base.getI(map(ndx));}
	@Override
	public String getSWREM(int ndx) throws PsseModelException {return _base.getSWREM(map(ndx));}
	@Override
	public float getRMPCT(int ndx) throws PsseModelException {return _base.getRMPCT(map(ndx));}
	@Override
	public float getBINIT(int ndx) throws PsseModelException {return _base.getBINIT(map(ndx));}
	@Override
	public float getVSWHI(int ndx) throws PsseModelException {return _base.getVSWHI(map(ndx));}
	@Override
	public float getVSWLO(int ndx) throws PsseModelException {return _base.getVSWLO(map(ndx));}
	@Override
	public boolean isInSvc(int ndx) throws PsseModelException {return _base.isInSvc(map(ndx));}
	@Override
	public float getP(int ndx) throws PsseModelException {return _base.getP(map(ndx));}
	@Override
	public float getQ(int ndx) throws PsseModelException {return _base.getQ(map(ndx));}
	@Override
	public void setP(int ndx, float mw) throws PsseModelException {_base.setP(map(ndx), mw);}
	@Override
	public void setQ(int ndx, float mvar) throws PsseModelException {_base.setQ(map(ndx), mvar);}
	@Override
	public float getPpu(int ndx) throws PsseModelException {return _base.getPpu(map(ndx));}
	@Override
	public void setPpu(int ndx, float p) throws PsseModelException {_base.setPpu(map(ndx), p);}
	@Override
	public float getQpu(int ndx) throws PsseModelException {return _base.getQpu(map(ndx));}
	@Override
	public void setQpu(int ndx, float q) throws PsseModelException {_base.setQpu(map(ndx), q);}
	@Override
	public int getMODSW(int ndx) throws PsseModelException {return _base.getMODSW(map(ndx));}
	@Override
	public ControlMode getControlMode(int ndx) throws PsseModelException
	{
		return _base.getControlMode(map(ndx));
	}
	@Override
	public void setControlMode(int ndx, ControlMode cmode)
			throws PsseModelException
	{
		_base.setControlMode(map(ndx), cmode);
	}
	@Override
	public State getState(int ndx) throws PsseModelException {return _base.getState(map(ndx));}
	@Override
	public float getVS(int ndx) throws PsseModelException {return _base.getVS(map(ndx));}
	@Override
	public void setVS(int ndx, float vs) throws PsseModelException {_base.setVS(map(ndx), vs);}
	@Override
	public float getQS(int ndx) throws PsseModelException {return _base.getQS(map(ndx));}
	@Override
	public void setQS(int ndx) throws PsseModelException {_base.setQS(map(ndx));}
	@Override
	public Limits getReactivePowerLimits(int ndx) throws PsseModelException
	{
		return _base.getReactivePowerLimits(map(ndx));
	}
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
	public long getKey(int ndx) throws PsseModelException {return _base.getKey(ndx);}
}
