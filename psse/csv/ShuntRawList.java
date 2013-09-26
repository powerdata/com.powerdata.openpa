package com.powerdata.openpa.psse.csv;

import java.util.ArrayList;
import java.util.List;

import com.powerdata.openpa.psse.Bus;
import com.powerdata.openpa.psse.Line;
import com.powerdata.openpa.psse.LineList;
import com.powerdata.openpa.psse.PsseModelException;

public class ShuntRawList extends com.powerdata.openpa.psse.ShuntList
{
	int _size;
	
	String[] _i;
	boolean[] _swon;
	float[] _b;
	float[] _g;
	String[] _id, _name;
	
	ArrayList<String> _il = new ArrayList<>();
	ArrayList<Boolean> _swonl = new ArrayList<>();
	ArrayList<Float> _bl = new ArrayList<>(), _gl = new ArrayList<>();
	ArrayList<String> _idl = new ArrayList<>(), _namel = new ArrayList<>();
	
	public ShuntRawList() {super();}

	public ShuntRawList(PsseRawModel model, SwitchedShuntRawList raw,
			List<Integer> shndx) throws PsseModelException
	{
		super(model);

		int nraw = shndx.size();
		BusListRaw rawbus = model.getBuses();
		LineList rawline = model.getLines();
		
		for(int iraw=0; iraw < nraw; ++iraw)
		{
			int ndx = shndx.get(iraw);
			String rawid = raw.getObjectID(ndx);
			Bus obus = rawbus.get(raw.getI(ndx));
			String rawname = obus.getObjectName();
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
							binit -= bshblk;
						}
						mkShunt(bshblk, 0f, obus, rawid+"-"+posinswsh, rawname+"-"+posinswsh,swon);
						++posinswsh;
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
						mkShunt(bshblk, 0f, obus, rawid+"-"+posinswsh, rawname+"-"+posinswsh,swon);
						++posinswsh;
						--nshblk;
					}
				}
			}
		}
		
		scanBuses(rawbus);
		scanLines(rawline, rawbus);
		
		_size = _il.size();
		_i = new String[_size];
		_swon = new boolean[_size];
		_b = new float[_size];
		_g = new float[_size];
		_id = new String[_size];
		_name = new String[_size];

		for (int i=0; i < _size; ++i)
		{
			_i[i] = _il.get(i);
			_swon[i] = _swonl.get(i);
			_b[i] = _bl.get(i);
			_g[i] = _gl.get(i);
			_id[i] = _idl.get(i);
			_name[i] = _namel.get(i);
		}
		_bl = null;
		_gl = null;
	}

	void scanBuses(BusListRaw rawbus) throws PsseModelException
	{
		for (Bus b : rawbus)
		{
			float gl = b.getGL();
			float bl = b.getBL();
			if (gl != 0f || bl != 0f)
			{
				mkShunt(bl, gl, b, b.getObjectID() + "BSH",
						b.getObjectName() + "-BSH", true);
			}
		}
	}
	
	void scanLines(LineList rawline, BusListRaw rawbus) throws PsseModelException
	{
		for (Line l : rawline)
		{
			if (l.isInSvc())
			{
				float gi = l.getGI()*100f;
				float bi = l.getBI()*100f;
				float gj = l.getGJ()*100f;
				float bj = l.getBJ()*100f;
				Bus fb = rawbus.get(l.getI());
				String j = l.getJ();
				if (j.charAt(0) == '-')
					j = j.substring(1);
				Bus tb = rawbus.get(j);
				String objname = String.format("%s-%s:%s", fb.getObjectName(), tb.getObjectName(), l.getCKT());
				if (gi != 0f || bi != 0f)
				{
					mkShunt(bi, gi, fb, l.getObjectID() + "FSH",
							objname + "-FSH", true);
				}
				if (gj != 0f || bj != 0f)
				{
					mkShunt(bj, gj, tb, l.getObjectID()
							+ "TSH", objname + "-TSH", true);
				}
			}
		}
	}

	void mkShunt(float b, float g, Bus bus, String id, String name, boolean swon) throws PsseModelException
	{
		_il.add(bus.getObjectID());
		_bl.add(b);
		_gl.add(g);
		_swonl.add(swon);
		_namel.add(name);
		_idl.add("SH-"+id);
	}

	@Override
	public String getObjectID(int ndx) throws PsseModelException {return _id[ndx];}
	@Override
	public String getObjectName(int ndx) throws PsseModelException {return _name[ndx];}
	@Override
	public int size() {return _size;}

	@Override
	public String getI(int ndx) throws PsseModelException {return _i[ndx];}
	@Override
	public float getB(int ndx) throws PsseModelException {return _b[ndx];}
	@Override
	public float getG(int ndx) throws PsseModelException {return _g[ndx];}

	@Override
	public boolean isSwitchedOn(int ndx) throws PsseModelException
	{
		return _swon[ndx];
	}

}
