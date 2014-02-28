package com.powerdata.openpa.psse;

import java.util.List;

import com.powerdata.openpa.tools.AbstractBaseObject;
import com.powerdata.openpa.tools.BaseList;

public class TwoTermDevList extends BaseList<TwoTermDev>
{
	Object[] _twoTermDevs;
	int _size;
	int[] _lsize;
	
	
	class TwoTermDevObj extends AbstractBaseObject implements TwoTermDev
	{
		public TwoTermDevObj(int ndx) {super(TwoTermDevList.this, ndx);}

		@Override
		public String getI() throws PsseModelException {return TwoTermDevList.this.getI(_ndx);}
		@Override
		public String getJ() throws PsseModelException {return TwoTermDevList.this.getJ(_ndx);}
		@Override
		public Bus getFromBus() throws PsseModelException {return TwoTermDevList.this.getFromBus(_ndx);}
		@Override
		public Bus getToBus() throws PsseModelException {return TwoTermDevList.this.getToBus(_ndx);}
		@Override
		public boolean isInSvc() throws PsseModelException {return TwoTermDevList.this.isInSvc(_ndx);}
		@Override
		public void setInSvc(boolean state) throws PsseModelException
		{
			TwoTermDevList.this.setInSvc(_ndx, state);
		}
	}
	
	public TwoTermDevList(Object[] lists)
	{
		_twoTermDevs = lists;
		int nlist = lists.length;
		_lsize = new int[nlist];
		for(int i=0; i < nlist; ++i)
		{
			int s = ((List<?>)_twoTermDevs[i]).size();
			_lsize[i] = s;
			_size += s;
		}
	}
	
	public void setInSvc(int ndx, boolean state) throws PsseModelException
	{
		findDev(ndx).setInSvc(state);
	}

	@SuppressWarnings("unchecked")
	public TwoTermDev findDev(int ndx)
	{
		for (int i = 0; i < _lsize.length; ++i)
		{
			int ls = _lsize[i];
			if (ndx < ls)
				return ((List<? extends TwoTermDev>) _twoTermDevs[i]).get(ndx);
			ndx -= ls;
		}
		return null;
	}
	
	@Override
	public String getObjectID(int ndx) throws PsseModelException
	{
		return findDev(ndx).getObjectID();
	}

	public Bus getToBus(int ndx) throws PsseModelException
	{
		return findDev(ndx).getToBus();
	}

	public Bus getFromBus(int ndx) throws PsseModelException
	{
		return findDev(ndx).getFromBus();
	}

	public String getJ(int ndx) throws PsseModelException
	{
		return findDev(ndx).getJ();
	}

	public String getI(int ndx) throws PsseModelException
	{
		return findDev(ndx).getI();
	}

	@Override
	public TwoTermDev get(int index) {return new TwoTermDevObj(index);}

	public boolean isInSvc(int ndx) throws PsseModelException
	{
		return findDev(ndx).isInSvc();
	}


	@Override
	public int size() {return _size;}

	@Override
	public long getKey(int ndx) throws PsseModelException 
	{
		return findDev(ndx).getKey();
	}
}
