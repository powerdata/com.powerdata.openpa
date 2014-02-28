package com.powerdata.openpa.psse.csv;

import java.util.List;

import com.powerdata.openpa.psse.Bus;
import com.powerdata.openpa.psse.BusList;
import com.powerdata.openpa.psse.Limits;
import com.powerdata.openpa.psse.PsseModel;
import com.powerdata.openpa.psse.PsseModelException;

public class SvcRawList extends com.powerdata.openpa.psse.SvcList
{
	int _size;
	
	String[] _i, _swrem;
	float[] _rmpct, _binit, _minB, _maxB, _vsp;
	String[] _id;
	int[] _stat;
	
	public SvcRawList() {super();}

	public SvcRawList(PsseModel model, SwitchedShuntRawList raw,
			List<Integer> svcndx) throws PsseModelException
	{
		super(model);
		_size = svcndx.size();
		
		_i = new String[_size];
		_swrem = new String[_size];
		_rmpct = new float[_size];
		_binit = new float[_size];
		_minB = new float[_size];
		_maxB = new float[_size];
		_id = new String[_size];
		_vsp = new float[_size];
		_stat = new int[_size];

		BusList rawbus = model.getBuses();
		
		for (int i=0; i < _size; ++i)
		{
			int ndx = svcndx.get(i);
			Bus bus = rawbus.get(raw.getI(ndx));
			_i[i] = bus.getObjectID();
			String swrem = raw.getSWREM(ndx);
			_swrem[i] = (swrem.isEmpty() || swrem.equals("0")) ? _i[i] : swrem;
			_rmpct[i] = raw.getRMPCT(ndx);
			_binit[i] = raw.getBINIT(ndx);
			_vsp[i] = (raw.getVSWHI(ndx)+raw.getVSWLO(ndx))/2f;
			_stat[i] = raw.isInSvc(ndx)?1:0;
			
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
	public String getI(int ndx) throws PsseModelException {return _i[ndx];}
	@Override
	public String getSWREM(int ndx) throws PsseModelException {return _swrem[ndx];}

	@Override
	public float getRMPCT(int ndx) throws PsseModelException {return _rmpct[ndx];}
	@Override
	public float getBINIT(int ndx) throws PsseModelException {return _binit[ndx];}

	@Override
	public Limits getReactivePowerLimits(int ndx) throws PsseModelException
	{
		return new Limits(_minB[ndx], _maxB[ndx]);
	}

	@Override
	public boolean isInSvc(int ndx) throws PsseModelException {return _stat[ndx] == 1;}
	@Override
	public void setInSvc(int ndx, boolean state) throws PsseModelException 
	{
		_stat[ndx] = state ? 1 : 0;
	}

}
