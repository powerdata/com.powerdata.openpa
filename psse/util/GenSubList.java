package com.powerdata.openpa.psse.util;

import com.powerdata.openpa.psse.Bus;
import com.powerdata.openpa.psse.Gen;
import com.powerdata.openpa.psse.Gen.GenRegMode;
import com.powerdata.openpa.psse.GenList;
import com.powerdata.openpa.psse.GenMode;
import com.powerdata.openpa.psse.GenType;
import com.powerdata.openpa.psse.Limits;
import com.powerdata.openpa.psse.OwnershipList;
import com.powerdata.openpa.psse.PsseModelException;

public class GenSubList extends GenList
{
	GenList _base;
	int[] _ndxs;
	boolean _idndxd = false;
		
	public GenSubList(GenList base, int[] ndxs) throws PsseModelException
	{
		super(base.getPsseModel());
		_base = base;
		_ndxs = ndxs;
	}
	
	protected int map(int ndx) {return _ndxs[ndx];}

	@Override
	public Gen get(String id)
	{
		if (!_idndxd)
		{
			_idndxd = true;
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
	public void commit() throws PsseModelException {_base.commit();}
	@Override
	public Bus getBus(int ndx) throws PsseModelException { return _base.getBus(map(ndx)); }
	@Override
	public Bus getRemoteRegBus(int ndx) throws PsseModelException {return _base.getRemoteRegBus(map(ndx));}
	@Override
	public GenMode getMode(int ndx) throws PsseModelException {return _base.getMode(map(ndx));}
	@Override
	public GenType getType(int ndx) throws PsseModelException {return _base.getType(map(ndx));}
	@Override
	public Limits getReactiveLimits(int ndx) throws PsseModelException {return _base.getReactiveLimits(map(ndx));}
	@Override
	public Limits getActiveLimits(int ndx) throws PsseModelException {return _base.getActiveLimits(map(ndx));}
	@Override
	public boolean isInSvc(int ndx) throws PsseModelException {return _base.isInSvc(map(ndx));}
	@Override
	public String getObjectName(int ndx) throws PsseModelException {return _base.getObjectName(map(ndx));}
	@Override
	public void setMode(int ndx, GenMode mode) throws PsseModelException {_base.setMode(map(ndx), mode);}
	@Override
	public float getPpu(int ndx) throws PsseModelException {return _base.getPpu(map(ndx));}
	@Override
	public void setPpu(int ndx, float p) throws PsseModelException {_base.setPpu(map(ndx), p);}
	@Override
	public float getQpu(int ndx) throws PsseModelException {return _base.getQpu(map(ndx));}
	@Override
	public void setQpu(int ndx, float q) throws PsseModelException {_base.setQpu(map(ndx), q);}
	@Override
	public String getI(int ndx) throws PsseModelException {return _base.getI(map(ndx));}
	@Override
	public String getID(int ndx) throws PsseModelException {return _base.getID(map(ndx));}
	@Override
	public float getP(int ndx) throws PsseModelException {return _base.getP(map(ndx));}
	@Override
	public void setP(int ndx, float p) throws PsseModelException {_base.setP(map(ndx), p);}
	@Override
	public float getQ(int ndx) throws PsseModelException {return _base.getQ(map(ndx));}
	@Override
	public void setQ(int ndx, float q) throws PsseModelException {_base.setQ(map(ndx), q);}
	@Override
	public float getQT(int ndx) throws PsseModelException {return _base.getQT(map(ndx));}
	@Override
	public void setQT(int ndx, float mvar) throws PsseModelException {_base.setQT(map(ndx), mvar);}
	@Override
	public float getQB(int ndx) throws PsseModelException {return _base.getQB(map(ndx));}
	@Override
	public void setQB(int ndx, float mvar) throws PsseModelException {_base.setQB(map(ndx), mvar);}
	@Override
	public float getVS(int ndx) throws PsseModelException {return _base.getVS(map(ndx));}
	@Override
	public void setVS(int ndx, float vmpu) throws PsseModelException {_base.setVS(map(ndx), vmpu);}
	@Override
	public String getIREG(int ndx) throws PsseModelException {return _base.getIREG(map(ndx));}
	@Override
	public float getMBASE(int ndx) throws PsseModelException {return _base.getMBASE(map(ndx));}
	@Override
	public float getZR(int ndx) throws PsseModelException {return _base.getZR(map(ndx));}
	@Override
	public float getZX(int ndx) throws PsseModelException {return _base.getZX(map(ndx));}
	@Override
	public float getRT(int ndx) throws PsseModelException {return _base.getRT(map(ndx));}
	@Override
	public float getXT(int ndx) throws PsseModelException {return _base.getXT(map(ndx));}
	@Override
	public float getGTAP(int ndx) throws PsseModelException {return _base.getGTAP(map(ndx));}
	@Override
	public int getSTAT(int ndx) throws PsseModelException {return _base.getSTAT(map(ndx));}
	@Override
	public float getRMPCT(int ndx) throws PsseModelException {return _base.getRMPCT(map(ndx));}
	@Override
	public float getPT(int ndx) throws PsseModelException {return _base.getPT(map(ndx));}
	@Override
	public void setPT(int ndx, float mw) throws PsseModelException {_base.setPT(map(ndx), mw);}
	@Override
	public float getPB(int ndx) throws PsseModelException {return _base.getPB(map(ndx));}
	@Override
	public void setPB(int ndx, float mw) throws PsseModelException {_base.setPB(map(ndx), mw);}
	@Override
	public OwnershipList getOwnership(int ndx) throws PsseModelException
	{
		return _base.getOwnership(map(ndx));
	}
	@Override
	public float getPS(int ndx) throws PsseModelException {return _base.getPS(map(ndx));}
	@Override
	public void setPS(int ndx, float mw) throws PsseModelException {_base.setPS(map(ndx), mw);}
	@Override
	@Deprecated
	public boolean isInAvr(int ndx) throws PsseModelException {return _base.isInAvr(map(ndx));}
	@Override
	public GenRegMode getRegMode(int ndx) throws PsseModelException {return _base.getRegMode(map(ndx));}
	@Override
	public void setRegMode(int ndx, GenRegMode mode) throws PsseModelException {_base.setRegMode(map(ndx), mode);}
	@Override
	public float getQS(int ndx) throws PsseModelException {return _base.getQS(map(ndx));}
	@Override
	public void setQS(int ndx, float mvar) throws PsseModelException {_base.setQS(map(ndx), mvar);}
	@Override
	public String getObjectID(int ndx) throws PsseModelException {return _base.getObjectID(map(ndx));}
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
