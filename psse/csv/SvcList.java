package com.powerdata.openpa.psse.csv;

import java.util.List;

import com.powerdata.openpa.psse.Limits;
import com.powerdata.openpa.psse.PsseModel;
import com.powerdata.openpa.psse.PsseModelException;

public class SvcList extends com.powerdata.openpa.psse.SvcList
{
	int _size;
	
	int[] _i, _swrem;
	float[] _rmpct, _binit, _minB, _maxB, _vsp;
	String[] _id;
	
	
	public SvcList() {super();}
	public SvcList(PsseModel model, SwitchedShuntRawList raw,
			List<Integer> svcndx) throws PsseModelException
	{
		super(model);
		_size = svcndx.size();
		
		_i = new int[_size];
		_swrem = new int[_size];
		_rmpct = new float[_size];
		_binit = new float[_size];
		_minB = new float[_size];
		_maxB = new float[_size];
		_id = new String[_size];
		_vsp = new float[_size];
		
		for (int i=0; i < _size; ++i)
		{
			int ndx = svcndx.get(i);
			_i[i] = raw.getBus(ndx).getIndex();
			String swrem = raw.getSWREM(ndx);
			_swrem[i] = (swrem.isEmpty() || swrem.equals("0")) ? _i[i] : _buses
					.get(swrem).getIndex();
			_rmpct[i] = raw.getRMPCT(ndx);
			_binit[i] = raw.getBINIT(ndx);
			_vsp[i] = (raw.getVSWHI(ndx)+raw.getVSWLO(ndx))/2f;
			
			int[] n = raw.getN(ndx);
			float[] b = raw.getB(ndx);
			for (int iblk=0; iblk < n.length; ++iblk)
			{
				float bblk = b[iblk];
				if (bblk < 0f)
				{
					_minB[i] += bblk * n[iblk];
				}
				else if (bblk > 0f)
				{
					_maxB[i] += bblk * n[iblk];
				}
			}
		}
	}

	@Override
	public String getObjectID(int ndx) throws PsseModelException {return _id[ndx];}
	@Override
	public int size() {return _size;}
	@Override
	public String getI(int ndx) throws PsseModelException {return _buses.get(_i[ndx]).getObjectID();}
	@Override
	public Limits getBLimits(int ndx) throws PsseModelException {return new Limits(_minB[ndx], _maxB[ndx]);}
	@Override
	public float getVoltageSetpoint(int ndx) throws PsseModelException {return _vsp[ndx];}
	@Override
	public String getSWREM(int ndx) throws PsseModelException {return _buses.get(_swrem[ndx]).getObjectID();}

	@Override
	public float getRMPCT(int ndx) throws PsseModelException {return _rmpct[ndx];}
	@Override
	public float getBINIT(int ndx) throws PsseModelException {return _binit[ndx];}
}
