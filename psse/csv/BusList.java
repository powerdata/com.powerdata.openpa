package com.powerdata.openpa.psse.csv;

import java.util.Arrays;
import java.util.List;

import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.psse.TransformerRaw;
import com.powerdata.openpa.tools.Complex;
import com.powerdata.openpa.tools.ComplexList;

public abstract class BusList extends com.powerdata.openpa.psse.BusList
{
	int _size;
	/** object IDs (really just the bus number) */
	String _ids[];
	
	// Base values from the CSV file
	int _i[];
	String _name[];
	float _basekv[];
	int _ide[];
	int _area[];
	int _zone[];
	int _owner[];
	float _vm[];
	float _va[];
	float _gl[];
	float _bl[];
	
	ComplexList _mm;

	public BusList() {super();}
	public BusList(PsseModel model) {super(model);}
	
	@Override
	public int size() {return _size;}
	@Override
	public int getI(int ndx) { return _i[ndx]; }
	@Override
	public String getNAME(int ndx) { return _name[ndx]; }
	@Override
	public float getBASKV(int ndx) { return _basekv[ndx]; }
	@Override
	public int getIDE(int ndx) { return _ide[ndx]; }
	@Override
	public float getGL(int ndx) { return _gl[ndx]; }
	@Override
	public float getBL(int ndx) { return _bl[ndx]; }
	@Override
	public int getAREA(int ndx) { return _area[ndx]; }
	@Override
	public int getZONE(int ndx) { return _zone[ndx]; }
	@Override
	public float getVM(int ndx) { return _vm[ndx]; }
	@Override
	public float getVA(int ndx) { return _va[ndx]; }
	@Override
	public int getOWNER(int ndx) { return _owner[ndx]; }
	@Override
	public String getObjectID(int ndx) { return _ids[ndx];	}
	
	public void addStarNodes(TransformerRawList txraw, List<Integer> ndx3w)
			throws PsseModelException
	{
		int nxfr = ndx3w.size();
		int newsz = _size + nxfr;

		/* find the largest node number and generate new node numbers */
		int maxndnum = -1;
		for (int i = 0; i < _size; ++i)
		{
			int n = _i[i];
			if (n > maxndnum) maxndnum = n;
		}
		_i = Arrays.copyOf(_i, newsz);
		_ids = Arrays.copyOf(_ids, newsz);
		for (int i = _size; i < newsz; ++i)
		{
			int newi = ++maxndnum; 
			_i[i] = newi;
//			_ids[i] = String.valueOf(newi);
		}

		/* Get info from transformer */
		_name = Arrays.copyOf(_name, newsz);
		_area = Arrays.copyOf(_area, newsz);
		_zone = Arrays.copyOf(_zone, newsz);
		_owner = Arrays.copyOf(_owner, newsz);
		_vm = Arrays.copyOf(_vm, newsz);
		_va = Arrays.copyOf(_va, newsz);

		for (int i = 0, bi = _size; i < nxfr; ++i, ++bi)
		{
			TransformerRaw t = txraw.get(ndx3w.get(i));
			_name[bi] = t.getNAME();
			int busindx = t.getBusI().getIndex();
			_area[bi] = _area[busindx];
			_zone[bi] = _zone[busindx];
			_owner[bi] = _owner[busindx];
			_vm[bi] = t.getVMSTAR();
			_va[bi] = t.getANSTAR();
			_ids[bi] = "TXSTAR-"+t.getObjectID();
			if (_idToNdx.put(_ids[bi], bi)!= null)
				System.err.format("Duplicate bus ID: %s", _ids[bi]);
		}

		/* set all base kv to 1 KV */
		_basekv = Arrays.copyOf(_basekv, newsz);
		Arrays.fill(_basekv, _size, newsz, 1f);

		/* set all bus type codes for load type */
		_ide = Arrays.copyOf(_ide, newsz);
		Arrays.fill(_ide, _size, newsz, 1);

		/* size gl and bl correctly, but leave them at 0 */
		_gl = Arrays.copyOf(_gl, newsz);
		_bl = Arrays.copyOf(_bl, newsz);

		_mm.ensureCapacity(newsz);
		_mm.setSize(newsz);

		_size = newsz;
	}
	
	@Override
	public void setRTMismatch(int ndx, Complex mismatch)
			throws PsseModelException
	{
		_mm.set(ndx, mismatch);
	}
	@Override
	public Complex getRTMismatch(int ndx) throws PsseModelException
	{
		return _mm.get(ndx);
	}



}
