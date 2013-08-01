package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.AbstractBaseObject;
import com.powerdata.openpa.tools.BaseList;
import com.powerdata.openpa.tools.Complex;

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
		public void setRTMVar(float mvar) throws PsseModelException
		{
			OneTermDevList.this.setRTMVar(_ndx, mvar);
		}
		@Override
		public Complex getRTS() throws PsseModelException
		{
			return OneTermDevList.this.getRTS(_ndx);
		}
		@Override
		public void setRTS(Complex s) throws PsseModelException
		{
			OneTermDevList.this.setRTS(_ndx, s);
		}
		@Override
		public boolean isInSvc() throws PsseModelException
		{
			return OneTermDevList.this.isInSvc(_ndx);
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

	public void setRTS(int ndx, Complex s) throws PsseModelException
	{
		findDev(ndx).setRTS(s);
	}

	public Complex getRTS(int ndx) throws PsseModelException
	{
		return findDev(ndx).getRTS();
	}

	public void setRTMVar(int ndx, float mvar) throws PsseModelException
	{
		findDev(ndx).setRTMVar(mvar);
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

	@Override
	public int size() {return _size;}

}
