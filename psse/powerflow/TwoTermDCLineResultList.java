package com.powerdata.openpa.psse.powerflow;

import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.psse.TwoTermDCLineList;
import com.powerdata.openpa.tools.BaseList;

public class TwoTermDCLineResultList extends BaseList<TwoTermDCLineResult>
{
	protected TwoTermDCLineList _lines;
	protected float[] _rtap, _itap, _alpha, _gamma, _mwr, _mvarr, _mwi, _mvari;
	
	public TwoTermDCLineResultList(TwoTermDCLineList lines, float[] rtap,
			float[] itap, float[] alpha, float[] gamma, float[] mwr,
			float[] mvarr, float[] mwi, float[] mvari)
	{
		_lines = lines;
		_rtap = rtap;
		_itap = itap;
		_alpha = alpha;
		_gamma = gamma;
		_mwr = mwr;
		_mvarr = mvarr;
		_mwi = mwi;
		_mvari = mvari;
	}

	@Override
	public String getObjectID(int ndx) throws PsseModelException
	{
		return _lines.getObjectID(ndx);
	}

	@Override
	public TwoTermDCLineResult get(int index)
	{
		return new TwoTermDCLineResult(this, index);
	}
	

	@Override
	public int size()
	{
		return _lines.size();
	}

	public float getTapR(int ndx) throws PsseModelException {return _rtap[ndx];}
	public float getTapI(int ndx) throws PsseModelException {return _itap[ndx];}
	public float getAlpha(int ndx) throws PsseModelException {return _alpha[ndx];}
	public float getGamma(int ndx) throws PsseModelException {return _gamma[ndx];}

	public float getMWR(int ndx) throws PsseModelException {return _mwr[ndx];}
	public float getMWI(int ndx) throws PsseModelException {return _mwi[ndx];}
	public float getMVArR(int ndx) throws PsseModelException {return _mvarr[ndx];}
	public float getMVArI(int ndx) throws PsseModelException {return _mvari[ndx];}

	@Override
	public long getKey(int ndx) throws PsseModelException {return ndx;}
}
