package com.powerdata.openpa.psse.csv;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import com.powerdata.openpa.psse.BusTypeCode;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.psse.util.TP;
import com.powerdata.openpa.psse.util.TransformerRaw;
import com.powerdata.openpa.tools.LoadArray;
import com.powerdata.openpa.tools.PAMath;
import com.powerdata.openpa.tools.SimpleCSV;
/**
 * Implement bus for CSV.  Currently based on Robin's version.
 * 
 * @author marck
 */
public class BusListRaw extends com.powerdata.openpa.psse.BusList 
{
	PsseRawModel _eq;
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
	float _vmpu[];
	float _va[];
	float _gl[];
	float _bl[];
	
	public BusListRaw(PsseRawModel model) throws PsseModelException
	{
		super(model);
		_eq = model;
		try
		{
			File dbfile = new File(model.getDir(), "Bus.csv");
			SimpleCSV buses = new SimpleCSV(dbfile);
			_size	= buses.getRowCount();
			_i		= buses.getInts("I");
			_ids	= buses.get("I");
			_name	= LoadArray.String(buses,"NAME",this,"getDeftNAME");
			_basekv	= LoadArray.Float(buses,"BASKV",this,"getDeftBASKV");
			_ide	= LoadArray.Int(buses,"IDE",this,"getDeftIDE");
			_area	= LoadArray.Int(buses,"AREA",this,"getDeftAREA");
			_zone	= LoadArray.Int(buses,"ZONE",this,"getDeftZONE");
			_owner	= LoadArray.Int(buses,"OWNER",this,"getDeftOWNER");
			_vmpu	= LoadArray.Float(buses,"VM",this,"getDeftVMpu");
			_va		= LoadArray.Float(buses,"VA",this,"getDeftVA");
			_gl		= LoadArray.Float(buses,"GL",this,"getDeftGL");
			_bl		= LoadArray.Float(buses,"BL",this,"getDeftBL");

			reindex();
			
			if (_i == null)
			{
				throw new PsseModelException(getClass().getName()+" missing I in "+dbfile);
			}
			
		}
		catch(Exception e)
		{
			throw new PsseModelException(getClass().getName()+": "+e);
		}
	}
	public String getDeftNAME(int ndx) throws PsseModelException {return super.getNAME(ndx);}
	public float getDeftBASKV(int ndx) throws PsseModelException {return super.getBASKV(ndx);}
	public int getDeftIDE(int ndx) throws PsseModelException {return super.getIDE(ndx);}
	public float getDeftGL(int ndx) throws PsseModelException {return super.getGL(ndx);}
	public float getDeftBL(int ndx) throws PsseModelException {return super.getBL(ndx);}
	public int getDeftAREA(int ndx) throws PsseModelException {return super.getAREA(ndx);}
	public int getDeftZONE(int ndx) throws PsseModelException {return super.getZONE(ndx);}
	public float getDeftVMpu(int ndx) throws PsseModelException {return super.getVMpu(ndx);}
	public float getDeftVA(int ndx) throws PsseModelException {return super.getVA(ndx);}
	public int getDeftOWNER(int ndx) throws PsseModelException {return super.getOWNER(ndx);}
	
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
	public float getVM(int ndx) throws PsseModelException
	{
		return getVMpu(ndx) * getBASKV(ndx);
	}
	@Override
	public float getVMpu(int ndx) { return _vmpu[ndx]; }
	@Override
	public float getVA(int ndx) { return _va[ndx]; }
	@Override
	public float getVArad(int ndx) throws PsseModelException
	{
		return PAMath.deg2rad(getVA(ndx));
	}
	@Override
	public int getOWNER(int ndx) { return _owner[ndx]; }
	@Override
	public String getObjectID(int ndx) { return _ids[ndx];	}

	
	public void addStarNodes(Transformer3RawList txraw, List<Integer> ndx3w)
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
		_vmpu = Arrays.copyOf(_vmpu, newsz);
		_va = Arrays.copyOf(_va, newsz);

		for (int i = 0, bi = _size; i < nxfr; ++i, ++bi)
		{
			TransformerRaw t = txraw.get(ndx3w.get(i));
			_name[bi] = t.getNAME();
			int busindx = t.getBusI().getIndex();
			_area[bi] = _area[busindx];
			_zone[bi] = _zone[busindx];
			_owner[bi] = _owner[busindx];
			_vmpu[bi] = t.getVMSTAR();
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

		_size = newsz;
	}
	@Override
	public BusTypeCode getBusType(int ndx) throws PsseModelException
	{
		return _eq.tp().getBusType(ndx);
	}
	@Override
	public long getKey(int ndx) throws PsseModelException
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
}
