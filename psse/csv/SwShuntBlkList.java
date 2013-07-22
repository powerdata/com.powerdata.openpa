package com.powerdata.openpa.psse.csv;

import com.powerdata.openpa.psse.PsseModel;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.psse.SwitchedShunt;

public class SwShuntBlkList extends com.powerdata.openpa.psse.SwShuntBlkList
{
	int _size;
	int[] _n;
	float[] _b;
	
	protected SwShuntBlkList() {super();}
	public SwShuntBlkList(PsseModel model, SwitchedShunt sh, int[] n, float[] b)
	{
		super(model, sh);
		_size = (n == null) ? 0 : n.length;
		_n = n;
		_b = b;
	}

	@Override
	public int size() {return _size;}
	@Override
	public int getN(int ndx) throws PsseModelException {return _n[ndx];}
	@Override
	public float getB(int ndx) throws PsseModelException {return _b[ndx];}
}
