package com.powerdata.openpa.psse.util;

import com.powerdata.openpa.psse.Bus;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.psse.Shunt;
import com.powerdata.openpa.psse.ShuntList;

public class ShuntSubList extends ShuntList
{
	ShuntList _base;
	int[] _ndxs;
	boolean _indexed = false;
		
	public ShuntSubList(ShuntList base, int[] ndxs) throws PsseModelException
	{
		super(base.getPsseModel());
		_base = base;
		_ndxs = ndxs;
	}
	
	protected int map(int ndx) {return _ndxs[ndx];}

	@Override
	public Shunt get(String id)
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
	public float getBpu(int ndx) throws PsseModelException {return _base.getBpu(map(ndx));}
	@Override
	public float getGpu(int ndx) throws PsseModelException {return _base.getGpu(map(ndx));}
	@Override
	public String getI(int ndx) throws PsseModelException {return _base.getI(map(ndx));}
	@Override
	public float getB(int ndx) throws PsseModelException {return _base.getB(map(ndx));}
	@Override
	public float getG(int ndx) throws PsseModelException {return _base.getG(map(ndx));}
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
	public String getID(int ndx) throws PsseModelException {return _base.getID(map(ndx));}
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
}
