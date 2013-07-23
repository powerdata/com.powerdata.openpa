package com.powerdata.openpa.psse.csv;

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
	
	public ShuntList() {super();}

	public ShuntList(PsseModel model, SwitchedShuntRawList raw,
			List<Integer> shndx) throws PsseModelException
	{
		super(model);
		_size = shndx.size();
		
		_i = new int[_size];
		_b = new float[_size];
		_id = new String[_size];
		int addndx = 0;
		
		for(int iraw=0; iraw < _size; ++iraw)
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
						if (binit < 0)
						{
							swon = true;
							binit += bshblk;
						}
						mkShunt(bshblk, addndx++, bus, rawid, swon, posinswsh++);
						--nshblk;
					}
					if (bshblk > 0f && nshblk > 0)
					{
						boolean swon = false;
						if (binit > 0)
						{
							swon = true;
							binit -= bshblk;
						}
						mkShunt(bshblk, addndx++, bus, rawid, swon, posinswsh++);
						--nshblk;
					}
				}
			}
			
		}
	}

	void mkShunt(float bshblk, int index, int bus, String rawid, boolean swon, int posinswsh)
	{
		_i[index] = bus;
		_b[index] = bshblk;
		_swon[index] = swon;
		_id[index] = rawid+posinswsh;
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
	
}
