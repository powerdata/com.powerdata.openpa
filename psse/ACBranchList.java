package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.AbstractBaseObject;
import com.powerdata.openpa.tools.BaseList;
import com.powerdata.openpa.tools.Complex;

public class ACBranchList extends BaseList<ACBranch>
{
	public static final ACBranchList Empty = new ACBranchList() 
	{
		@Override
		public String getObjectID(int ndx) throws PsseModelException {return null;}
		@Override
		public int size() {return 0;}
	};

	class ACBranchObj extends AbstractBaseObject implements ACBranch
	{
		public ACBranchObj(int ndx) {super(ACBranchList.this, ndx);}
		@Override
		public Bus getFromBus() throws PsseModelException {return ACBranchList.this.getFromBus(_ndx);}
		@Override
		public Bus getToBus() throws PsseModelException {return ACBranchList.this.getToBus(_ndx);}
		@Override
		public Complex getZ() throws PsseModelException {return ACBranchList.this.getZ(_ndx);}
		@Override
		public Complex getY() throws PsseModelException {return ACBranchList.this.getY(_ndx);}
		@Override
		public Complex getFromYcm() throws PsseModelException {return ACBranchList.this.getFromYcm(_ndx);}
		@Override
		public Complex getToYcm() throws PsseModelException {return ACBranchList.this.getToYcm(_ndx);}
		@Override
		public float getFromTap() throws PsseModelException {return ACBranchList.this.getFromTap(_ndx);}
		@Override
		public float getToTap() throws PsseModelException {return ACBranchList.this.getToTap(_ndx);}
		@Override
		public float getPhaseShift() throws PsseModelException {return ACBranchList.this.getPhaseShift(_ndx);}
		@Override
		public void setRTFromS(Complex s) throws PsseModelException {ACBranchList.this.setRTFromS(_ndx, s);}
		@Override
		public void setRTToS(Complex s) throws PsseModelException {ACBranchList.this.setRTToS(_ndx, s);}
		@Override
		public Complex getRTFromS() throws PsseModelException {return ACBranchList.this.getRTFromS(_ndx);}
		@Override
		public Complex getRTToS() throws PsseModelException {return ACBranchList.this.getRTToS(_ndx);}
	}
	
	int _nlines;
	int _ntransformers;
	int _size;
	LineList _lines;
	TransformerList _transformers;
	PhaseShifterList _phaseshifters;
	
	ACBranchList() {super();}

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
	/** Get an AreaInterchange by it's index. */
	@Override
	public ACBranch get(int ndx) { return new ACBranchObj(ndx); }
	/** Get an AreaInterchange by it's ID. */
	@Override
	public ACBranch get(String id) { return super.get(id); }

	public Complex getZ(int ndx) throws PsseModelException {return findBranch(ndx).getZ();}
	public Complex getY(int ndx) throws PsseModelException {return findBranch(ndx).getY();}
	public Bus getToBus(int ndx) throws PsseModelException {return findBranch(ndx).getToBus();}
	public Bus getFromBus(int ndx) throws PsseModelException {return findBranch(ndx).getFromBus();}
	public float getPhaseShift(int ndx) throws PsseModelException {return findBranch(ndx).getPhaseShift();}
	public float getToTap(int ndx) throws PsseModelException {return findBranch(ndx).getToTap();}
	public float getFromTap(int ndx) throws PsseModelException {return findBranch(ndx).getFromTap();}
	public Complex getToYcm(int ndx) throws PsseModelException {return findBranch(ndx).getToYcm();}
	public Complex getFromYcm(int ndx) throws PsseModelException {return findBranch(ndx).getFromYcm();}
	public Complex getRTToS(int ndx) throws PsseModelException { return findBranch(ndx).getRTToS(); }
	public Complex getRTFromS(int ndx) throws PsseModelException {return findBranch(ndx).getRTFromS();}
	public void setRTToS(int ndx, Complex s) throws PsseModelException {findBranch(ndx).setRTToS(s);}
	public void setRTFromS(int ndx, Complex s) throws PsseModelException {findBranch(ndx).setRTFromS(s);}

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
		else return _transformers.get(ndx-_ntransformers);
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
}

