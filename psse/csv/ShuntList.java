package com.powerdata.openpa.psse.csv;

import java.util.ArrayList;
import java.util.List;

import com.powerdata.openpa.psse.Bus;
import com.powerdata.openpa.psse.Line;
import com.powerdata.openpa.psse.PsseModel;
import com.powerdata.openpa.psse.PsseModelException;

public class ShuntList extends com.powerdata.openpa.psse.ShuntList
{
	int _size;
	
	int[] _i;
	boolean[] _swon;
	float[] _b;
	float[] _g;
	String[] _id;
	
	ArrayList<Integer> _il = new ArrayList<>();
	ArrayList<Boolean> _swonl = new ArrayList<>();
	ArrayList<Float> _bl = new ArrayList<>(), _gl = new ArrayList<>();
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
						mkShunt(bshblk, 0f, bus, rawid+"-"+(posinswsh++), swon);
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
						mkShunt(bshblk, 0f, bus, rawid+"-"+(posinswsh++), swon);
						--nshblk;
					}
				}
			}
			
			
		}
		
		scanBuses();
		scanLines();
		
		_size = _il.size();
		_i = new int[_size];
		_swon = new boolean[_size];
		_b = new float[_size];
		_g = new float[_size];
		_id = new String[_size];

		for (int i=0; i < _size; ++i)
		{
			_i[i] = _il.get(i);
			_swon[i] = _swonl.get(i);
			_b[i] = _bl.get(i);
			_id[i] = _idl.get(i);
		}
	
	}

	void scanBuses() throws PsseModelException
	{
		for (Bus b : _model.getBuses())
		{
			float gl = b.getGL();
			float bl = b.getBL();
			if (gl != 0f || bl != 0f)
			{
				mkShunt(bl, gl, b.getIndex(), b.getObjectID()+"BSH", true);
			}
		}
	}
	
	void scanLines() throws PsseModelException
	{
		for (Line l : _model.getLines())
		{
			float gi = l.getGI();
			float bi = l.getBI();
			float gj = l.getGJ();
			float bj = l.getBJ();
			if (gi != 0f || bi != 0f)
				mkShunt(gi, bi, l.getFromBus().getIndex(), l.getObjectID()+"FSH", true);
			if (gj != 0f || bj != 0f)
				mkShunt(gj, bj, l.getToBus().getIndex(), l.getObjectID()+"TSH", true);
		}
	}

	void mkShunt(float b, float g, int bus, String id, boolean swon)
	{
		_il.add(bus);
		_bl.add(b);
		_gl.add(g);
		_swonl.add(swon);
		_idl.add(id);
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
