package com.powerdata.openpa.psse.csv;

import java.io.IOException;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.tools.LoadArray;
import com.powerdata.openpa.tools.SimpleCSV;

public class SwitchedShuntRawList extends com.powerdata.openpa.psse.SwitchedShuntList
{
	/** parent container */
	PsseRawModel _eq;
	/** number of items in the DB */
	int _size;
	/** object IDs (really just the bus number) */
	String _ids[];
	
	// Base values from the CSV file
	String[] _i, _swrem, _rmidnt;
	int[] _modsw;
	float[] _vswhi, _vswlo, _rmpct, _binit;

	int[][] _n;
	float[][] _b;

	public SwitchedShuntRawList(PsseRawModel eq) throws PsseModelException
	{
		super(eq);
		_eq = eq;
		String dbfile = _eq.getDir().getPath() + "/SwitchedShunt.csv";
		try
		{
			SimpleCSV swsh = new SimpleCSV(dbfile);
			_size = swsh.getRowCount();
			_i = swsh.get("I");
			_modsw = LoadArray.Int(swsh,"MODSW", this, "getDeftMODSW");
			_vswhi = LoadArray.Float(swsh, "VSWHI", this, "getDeftVSWHI");
			_vswlo = LoadArray.Float(swsh, "VSWLO", this, "getDeftVSWLO");
			_swrem = LoadArray.String(swsh, "SWREM", this, "getDeftSWREM");
			_rmpct = LoadArray.Float(swsh, "RMPCT", this, "getDeftRMPCT");
			_rmidnt = LoadArray.String(swsh, "RMIDNT", this, "getDeftRMIDNT");
			_binit = LoadArray.Float(swsh, "BINIT", this, "getDeftBINIT");
			
			_n = new int[_size][8];
			_b = new float[_size][8];
			
			
			
			for (int i=0, j=1; i < 8; ++i, ++j)
			{
				int[] tn = LoadArray.Int(swsh, "N"+j, this, "getDeftN");
				float[] tb = LoadArray.Float(swsh, "B"+j, this, "getDeftB");
				for(int k=0; k < _size; ++k)
				{
					_n[k][i] = tn[k];
					_b[k][i] = tb[k];
				}
			}

		} catch (IOException | ReflectiveOperationException | RuntimeException e)
		{
			throw new PsseModelException(e);
		}
	}

	@Override
	public String getI(int ndx) throws PsseModelException {return _i[ndx];}
	@Override
	public String getObjectID(int ndx) throws PsseModelException {return _i[ndx];}
	@Override
	public int size() {return _size;}
	@Override
	public int getMODSW(int ndx) throws PsseModelException {return _modsw[ndx];}
	@Override
	public float getVSWHI(int ndx) throws PsseModelException {return _vswhi[ndx];}
	@Override
	public float getVSWLO(int ndx) throws PsseModelException {return _vswlo[ndx];}
	@Override
	public String getSWREM(int ndx) throws PsseModelException {return _swrem[ndx];}
	@Override
	public float getRMPCT(int ndx) throws PsseModelException {return _rmpct[ndx];}
	@Override
	public String getRMIDNT(int ndx) throws PsseModelException {return _rmidnt[ndx];}
	@Override
	public float getBINIT(int ndx) throws PsseModelException {return _binit[ndx];}

	
	public int getDeftMODSW(int ndx) throws PsseModelException {return super.getMODSW(ndx);}
	public float getDeftVSWHI(int ndx) throws PsseModelException {return super.getVSWHI(ndx);}
	public float getDeftVSWLO(int ndx) throws PsseModelException {return super.getVSWLO(ndx);}
	public String getDeftSWREM(int ndx) throws PsseModelException {return super.getSWREM(ndx);}
	public float getDeftRMPCT(int ndx) throws PsseModelException {return super.getRMPCT(ndx);}
	public String getDeftRMIDNT(int ndx) throws PsseModelException {return super.getRMIDNT(ndx);}
	public float getDeftBINIT(int ndx) throws PsseModelException {return super.getBINIT(ndx);}
	
	public int getDeftN(int ndx) throws PsseModelException {return 0;}
	public float getDeftB(int ndx) throws PsseModelException {return 0f;}
	
	public int[] getN(int ndx) {return _n[ndx];}
	public float[] getB(int ndx) {return _b[ndx];}
	
	
}
