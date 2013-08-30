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
		public float getRTMW() throws PsseModelException
		{
			return OneTermDevList.this.getRTMW(_ndx);
		}
		@Override
		public float getRTMVar() throws PsseModelException
		{
			return OneTermDevList.this.getRTMVar(_ndx);
		}
		@Override
		public void setRTMW(float mw) throws PsseModelException
		{
			OneTermDevList.this.setRTMW(_ndx, mw);
		}
		@Override
		public void setRTMVAr(float mvar) throws PsseModelException
		{
			OneTermDevList.this.setRTMVAr(_ndx, mvar);
		}
		@Override
		public boolean isInSvc() throws PsseModelException
		{
			return OneTermDevList.this.isInSvc(_ndx);
		}
		@Override
		public float getRTP() throws PsseModelException
		{
			return OneTermDevList.this.getRTP(_ndx);
		}
		@Override
		public void setRTP(float p) throws PsseModelException
		{
			OneTermDevList.this.setRTP(_ndx, p);
		}
		@Override
		public float getRTQ() throws PsseModelException
		{
			return OneTermDevList.this.getRTQ(_ndx);
		}
		@Override
		public void setRTQ(float q) throws PsseModelException
		{
			OneTermDevList.this.setRTQ(_ndx, q);
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

	public void setRTMVAr(int ndx, float mvar) throws PsseModelException
	{
		findDev(ndx).setRTMVAr(mvar);
	}

	public void setRTMW(int ndx, float mw) throws PsseModelException
	{
		findDev(ndx).setRTMW(mw);
	}

	public float getRTMVar(int ndx) throws PsseModelException
	{
		return findDev(ndx).getRTMVar();
	}

	public float getRTMW(int ndx) throws PsseModelException
	{
		return findDev(ndx).getRTMW();
	}

	public Bus getBus(int ndx) throws PsseModelException
	{
		return findDev(ndx).getBus();
	}
	public boolean isInSvc(int ndx) throws PsseModelException
	{
		return findDev(ndx).isInSvc();
	}
	public void setRTQ(int ndx, float q) throws PsseModelException
	{
		findDev(ndx).setRTQ(q);
	}
	public float getRTQ(int ndx) throws PsseModelException
	{
		return findDev(ndx).getRTQ();
	}
	public void setRTP(int ndx, float p) throws PsseModelException
	{
		findDev(ndx).setRTP(p);
	}
	public float getRTP(int ndx) throws PsseModelException
	{
		return findDev(ndx).getRTP();
	}

	@Override
	public int size() {return _size;}

}
