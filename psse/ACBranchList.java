package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.AbstractBaseObject;
import com.powerdata.openpa.tools.BaseList;
import com.powerdata.openpa.psse.util.HashKeyFinder;
import com.powerdata.openpa.psse.util.KeyFinder;
import com.powerdata.openpa.tools.Complex;

/** Representation of in-service branches */

public class ACBranchList extends BaseList<ACBranch>
{
	public static final ACBranchList Empty = new ACBranchList()
	{
		@Override
		public int size() {return 0;}
	};

	class ACBranchObj extends AbstractBaseObject implements ACBranch
	{
		public ACBranchObj(int ndx) {super(ACBranchList.this, ndx);}
		@Override
		public String getI() throws PsseModelException {return ACBranchList.this.getI(_ndx);}
		@Override
		public String getJ() throws PsseModelException {return ACBranchList.this.getJ(_ndx);}
		@Override
		public Bus getFromBus() throws PsseModelException {return ACBranchList.this.getFromBus(_ndx);}
		@Override
		public Bus getToBus() throws PsseModelException {return ACBranchList.this.getToBus(_ndx);}
		@Override
		public float getR() throws PsseModelException {return ACBranchList.this.getR(_ndx);}
		@Override
		public float getX() throws PsseModelException {return ACBranchList.this.getX(_ndx);}
		@Override
		public Complex getZ() throws PsseModelException {return ACBranchList.this.getZ(_ndx);}
		@Override
		public Complex getY() throws PsseModelException {return ACBranchList.this.getY(_ndx);}
		@Override
		public float getFromTap() throws PsseModelException {return ACBranchList.this.getFromTap(_ndx);}
		@Override
		public float getToTap() throws PsseModelException {return ACBranchList.this.getToTap(_ndx);}
		@Override
		public float getPhaseShift() throws PsseModelException {return ACBranchList.this.getPhaseShift(_ndx);}
		@Override
		public boolean isInSvc() throws PsseModelException {return ACBranchList.this.isInSvc(_ndx);}
		@Override
		public float getGmag() throws PsseModelException {return ACBranchList.this.getGmag(_ndx);}
		@Override
		public float getBmag() throws PsseModelException {return ACBranchList.this.getBmag(_ndx);}
		@Override
		public float getFromBchg() throws PsseModelException {return ACBranchList.this.getFromBchg(_ndx);}
		@Override
		public float getToBchg() throws PsseModelException  {return ACBranchList.this.getToBchg(_ndx);}
		@Override
		public void setInSvc(boolean state) throws PsseModelException {ACBranchList.this.setInSvc(_ndx, state);}
	}
	
	int _nlines;
	int _ntransformers;
	int _size;
	LineList _lines;
	TransformerList _transformers;
	PhaseShifterList _phaseshifters;
	
	ACBranchList() {super();}

	public void setInSvc(int ndx, boolean state) throws PsseModelException {findBranch(ndx).setInSvc(state);}
	public float getR(int ndx) throws PsseModelException {return findBranch(ndx).getR();}
	public float getX(int ndx) throws PsseModelException {return findBranch(ndx).getX();}

	public String getI(int ndx) throws PsseModelException
	{
		return findBranch(ndx).getI();
	}
	public String getJ(int ndx) throws PsseModelException
	{
		return findBranch(ndx).getJ();
	}

	public ACBranchList(LineList l, TransformerList xf, PhaseShifterList ps)
			throws PsseModelException
	{
		_lines = l;
		_transformers = xf;
		_phaseshifters = ps;
		_nlines = _lines.size();
		_ntransformers = _transformers.size();
		_size = _nlines + _ntransformers + ps.size();
	}

	/* Standard object retrieval */
	@Override
	public ACBranch get(int ndx) { return new ACBranchObj(ndx); }
	@Override
	public ACBranch get(String id) { return super.get(id); }
	@Override
	public ACBranch getByKey(long key) throws PsseModelException
	{
		return get(kf().findNdx(key));
	}
	protected KeyFinder kf() throws PsseModelException
	{
		if (_kf == null)
		{
			int n = size();
			_kf = new HashKeyFinder(n);
			for(int i=0; i < n; ++i) _kf.map(getKey(i));
		}
		return _kf;
	}
	public Complex getZ(int ndx) throws PsseModelException {return findBranch(ndx).getZ();}
	public Complex getY(int ndx) throws PsseModelException {return findBranch(ndx).getY();}
	public Bus getToBus(int ndx) throws PsseModelException {return findBranch(ndx).getToBus();}
	public Bus getFromBus(int ndx) throws PsseModelException {return findBranch(ndx).getFromBus();}
	public float getPhaseShift(int ndx) throws PsseModelException {return findBranch(ndx).getPhaseShift();}
	public float getToTap(int ndx) throws PsseModelException {return findBranch(ndx).getToTap();}
	public float getFromTap(int ndx) throws PsseModelException {return findBranch(ndx).getFromTap();}
	public float getGmag(int ndx) throws PsseModelException {return findBranch(ndx).getGmag();}
	public float getBmag(int ndx) throws PsseModelException {return findBranch(ndx).getBmag();}
	public float getFromBchg(int ndx) throws PsseModelException {return findBranch(ndx).getFromBchg();}
	public float getToBchg(int ndx) throws PsseModelException {return findBranch(ndx).getToBchg();}
	public boolean isInSvc(int ndx) throws PsseModelException {return findBranch(ndx).isInSvc();}

	ACBranch findBranch(int ndx)
	{
		if (ndx < _nlines)
		{
			return _lines.get(ndx);
		}
		else if ((ndx-=_nlines) < _ntransformers)
		{
			return _transformers.get(ndx);
		}
		else return _phaseshifters.get(ndx-_ntransformers);
	}

	@Override
	public String getObjectID(int ndx) throws PsseModelException
	{
		return findBranch(ndx).getObjectID();
	}

	@Override
	public String getObjectName(int ndx) throws PsseModelException
	{
		return findBranch(ndx).getObjectName();
	}

	@Override
	public int size() {return _size;}

	@Override
	public long getKey(int ndx) throws PsseModelException
	{
		return findBranch(ndx).getKey();
	}

}

