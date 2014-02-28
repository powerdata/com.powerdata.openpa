package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.AbstractBaseObject;
import com.powerdata.openpa.tools.BaseList;

public class OneTermDevList extends BaseList<OneTermDev>
{
	public static final OneTermDevList Empty = new OneTermDevList()
	{
		@Override
		public int size() {return 0;}
	};

	class OneTermDevObj extends AbstractBaseObject implements OneTermDev
	{
		public OneTermDevObj(int ndx) {super(OneTermDevList.this, ndx);}
		@Override
		public Bus getBus() throws PsseModelException
		{
			return OneTermDevList.this.getBus(_ndx);
		}
		@Override
		public float getP() throws PsseModelException
		{
			return OneTermDevList.this.getP(_ndx);
		}
		@Override
		public float getQ() throws PsseModelException
		{
			return OneTermDevList.this.getQ(_ndx);
		}
		@Override
		public void setP(float mw) throws PsseModelException
		{
			OneTermDevList.this.setP(_ndx, mw);
		}
		@Override
		public void setQ(float mvar) throws PsseModelException
		{
			OneTermDevList.this.setQ(_ndx, mvar);
		}
		@Override
		public boolean isInSvc() throws PsseModelException
		{
			return OneTermDevList.this.isInSvc(_ndx);
		}
		@Override
		public float getPpu() throws PsseModelException
		{
			return OneTermDevList.this.getPpu(_ndx);
		}
		@Override
		public void setPpu(float p) throws PsseModelException
		{
			OneTermDevList.this.setPpu(_ndx, p);
		}
		@Override
		public float getQpu() throws PsseModelException
		{
			return OneTermDevList.this.getQpu(_ndx);
		}
		@Override
		public void setQpu(float q) throws PsseModelException
		{
			OneTermDevList.this.setQpu(_ndx, q);
		}
		@Override
		public void setInSvc(boolean state) throws PsseModelException
		{
			OneTermDevList.this.setInSvc(_ndx, state);
		}
	}
	
	int _nload, _ngen, _nsh, _nsvc, _size;
	LoadList	_loads;
	GenList		_gens;
	ShuntList	_shunts;
	SvcList		_svcs;

	OneTermDevList() {super();}
	
	public OneTermDevList(LoadList loads, GenList gens, ShuntList shunts, SvcList svcs)
	{
		_loads = loads;
		_gens = gens;
		_shunts = shunts;
		_svcs = svcs;
		
		_nload = loads.size();
		_ngen = gens.size();
		_nsh = shunts.size();
		_nsvc = svcs.size();
		_size = _nload + _ngen + _nsh + _nsvc;
	}
	
	@Override
	public OneTermDev get(int ndx) { return new OneTermDevObj(ndx); }
	@Override
	public OneTermDev get(String id) { return super.get(id); }

	protected OneTermDev findDev(int ndx)
	{
		if (ndx < _nload) 
			return _loads.get(ndx);
		else if ((ndx-=_nload) < _ngen)
			return _gens.get(ndx);
		else if ((ndx-=_ngen) < _nsh)
			return _shunts.get(ndx);
		else return _svcs.get(ndx-_nsh);
	}
	
	
	@Override
	public String getObjectID(int ndx) throws PsseModelException
	{
		return findDev(ndx).getObjectID();
	}

	@Override
	public String getObjectName(int ndx) throws PsseModelException
	{
		return findDev(ndx).getObjectName();
	}

	public void setQ(int ndx, float mvar) throws PsseModelException
	{
		findDev(ndx).setQ(mvar);
	}

	public void setP(int ndx, float mw) throws PsseModelException
	{
		findDev(ndx).setP(mw);
	}

	public float getQ(int ndx) throws PsseModelException
	{
		return findDev(ndx).getQ();
	}

	public float getP(int ndx) throws PsseModelException
	{
		return findDev(ndx).getP();
	}

	public Bus getBus(int ndx) throws PsseModelException
	{
		return findDev(ndx).getBus();
	}
	public boolean isInSvc(int ndx) throws PsseModelException
	{
		return findDev(ndx).isInSvc();
	}
	public void setInSvc(int ndx, boolean state) throws PsseModelException
	{
		findDev(ndx).setInSvc(state);
	}
	public void setQpu(int ndx, float q) throws PsseModelException
	{
		findDev(ndx).setQpu(q);
	}
	public float getQpu(int ndx) throws PsseModelException
	{
		return findDev(ndx).getQpu();
	}
	public void setPpu(int ndx, float p) throws PsseModelException
	{
		findDev(ndx).setPpu(p);
	}
	public float getPpu(int ndx) throws PsseModelException
	{
		return findDev(ndx).getPpu();
	}

	@Override
	public int size() {return _size;}

	@Override
	public long getKey(int ndx) throws PsseModelException
	{
		return findDev(ndx).getKey();
	}
}
