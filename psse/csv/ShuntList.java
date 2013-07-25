package com.powerdata.openpa.psse.csv;

import java.util.ArrayList;
import java.util.List;

import com.powerdata.openpa.psse.Bus;
import com.powerdata.openpa.psse.PsseModel;
import com.powerdata.openpa.psse.PsseModelException;

public class ShuntList extends com.powerdata.openpa.psse.ShuntList
{
	int _size;
	
	int[] _i;
	boolean[] _swon;
	float[] _b;
	String[] _id;
	
	ArrayList<Integer> _il = new ArrayList<>();
	ArrayList<Boolean> _swonl = new ArrayList<>();
	ArrayList<Float> _bl = new ArrayList<>();
	ArrayList<String> _idl = new ArrayList<>();
	
	public ShuntList() {super();}

	public ShuntList(PsseModel model, SwitchedShuntRawList raw,
			List<Integer> shndx) throws PsseModelException
	{
		super(model);

		int nraw = shndx.size();
		
		for(int iraw=0; iraw < nraw; ++iraw)
		{
			int ndx = shndx.get(iraw);
			String rawid = raw.getObjectID(ndx);
			int bus = raw.getBus(ndx).getIndex();
			int[] nblk = raw.getN(ndx);
			float[] bblk = raw.getB(ndx);
			float binit = raw.getBINIT(ndx);
			int nblocks = nblk.length;
			int posinswsh = 0;
			for (int iblk = 0; iblk < nblocks; ++iblk)
			{
				int nshblk = nblk[iblk];
				while (nshblk > 0)
				{
					float bshblk = bblk[iblk];
					/* figure out if any are energized */
					if (bshblk < 0f && nshblk > 0)
					{
						boolean swon = false;
						if (binit < -0.0001f)
						{
							swon = true;
							binit += bshblk;
						}
						mkShunt(bshblk, bus, rawid, swon, posinswsh++);
						--nshblk;
					}
					if (bshblk > 0f && nshblk > 0)
					{
						boolean swon = false;
						if (binit > 0.0001f)
						{
							swon = true;
							binit -= bshblk;
						}
						mkShunt(bshblk, bus, rawid, swon, posinswsh++);
						--nshblk;
					}
				}
			}
			_size = _il.size();
			_i = new int[_size];
			_swon = new boolean[_size];
			_b = new float[_size];
			_id = new String[_size];
			
			for (int i=0; i < _size; ++i)
			{
				_i[i] = _il.get(i);
				_swon[i] = _swonl.get(i);
				_b[i] = _bl.get(i);
				_id[i] = _idl.get(i);
			}
			
		}
	}

	void mkShunt(float bshblk, int bus, String rawid, boolean swon, int posinswsh)
	{
		_il.add(bus);
		_bl.add(bshblk);
		_swonl.add(swon);
		StringBuilder sb = new StringBuilder(rawid);
		sb.append('-');
		sb.append(posinswsh);
		_idl.add(sb.toString());
	}

	@Override
	public String getObjectID(int ndx) throws PsseModelException {return _id[ndx];}
	@Override
	public int size() {return _size;}

	@Override
	public Bus getBus(int ndx) throws PsseModelException  {return _buses.get(_i[ndx]);}

	@Override
	public String getI(int ndx) throws PsseModelException {return _buses.get(_i[ndx]).getObjectID();}
	@Override
	public float getB(int ndx) throws PsseModelException {return _b[ndx];}

	@Override
	public boolean isSwitchedOn(int ndx) throws PsseModelException
	{
		return _swon[ndx];
	}
	
}
