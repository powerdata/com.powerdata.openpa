package com.powerdata.openpa.psse.csv;

import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

import com.powerdata.openpa.psse.BusList;
import com.powerdata.openpa.psse.BusTypeCode;
import com.powerdata.openpa.psse.LineList;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.psse.conversions.XfrZToolFactory;
import com.powerdata.openpa.psse.conversions.XfrZTools;
import com.powerdata.openpa.psse.util.TP;
import com.powerdata.openpa.psse.util.TransformerRaw;
import com.powerdata.openpa.psseraw.Psse2CSV;
import com.powerdata.openpa.psseraw.PsseProcException;
import com.powerdata.openpa.tools.QueryString;
import com.powerdata.openpa.tools.StarNetwork;

public class PsseRawModel extends com.powerdata.openpa.psse.PsseModel
{
	/** root of the directory where the csv files are stored */
	File _dir;
	BusListRaw				_buses;
	LineListRaw			_branchList;
	TransformerRawList		_xfrList;
	PhaseShifterRawList	_psList;
	ShuntRawList			_shList;
	SvcRawList				_svcList;
	LoadList			_loads;
	GenList				_generatorList;
	TP					_tp;
	IslandList			_islands;

	public PsseRawModel(String parms) throws PsseModelException
	{
		QueryString q = new QueryString(parms);
		if (!q.containsKey("path") && !q.containsKey("file"))
		{
			throw new PsseModelException("com.powerdata.openpa.psse.csv.PsseInputModel Missing path= or file= in uri.");
		}
		if (q.containsKey("file"))
		{
			File raw = new File(q.get("file")[0]);
			File tmpdir = new File(System.getProperty("java.io.tmpdir"));
			String sname = raw.getName();
			_dir = new File(tmpdir, sname.substring(0, sname.length()-4));
			try
			{
				if (_dir.exists())
				{
					File[] flist = _dir.listFiles(new FilenameFilter()
					{
						@Override
						public boolean accept(File arg0, String arg1)
						{
							return arg1.toLowerCase().endsWith(".csv");
						}
					});
					for(File f : flist) f.delete();
				}
				_dir.mkdirs();
				Reader rpsse = new FileReader(raw);
				Psse2CSV p2c = new Psse2CSV(rpsse, null, _dir);
				p2c.process();
				rpsse.close();
				p2c.cleanup();
			} catch (IOException | PsseProcException e)
			{
				throw new PsseModelException(e);
			}
		}
		else
		{
			_dir = new File(q.get("path")[0]);
		}
		analyzeRawShunts();
		analyzeRawTransformers();
		_tp = new TP(this);

	}
	
	
	public File getDir() { return _dir; }
	@Override
	public BusListRaw getBuses() throws PsseModelException
	{
		if (_buses == null) _buses = new BusListRaw(this);
		return _buses;
	}
	@Override
	public LineList getLines() throws PsseModelException
	{
		if (_branchList == null) _branchList = new LineListRaw(this);
		return _branchList;
	}
	@Override
	public TransformerRawList getTransformers() throws PsseModelException
	{
		return _xfrList;
	}
	@Override
	public PhaseShifterRawList getPhaseShifters() throws PsseModelException
	{
		return _psList;
	}
	
	@Override
	public ShuntRawList getShunts() throws PsseModelException
	{
		return _shList;
	}
	@Override
	public SvcRawList getSvcs() throws PsseModelException
	{
		return _svcList;
	}
	
	/** convert 3-winding to 2-winding and detect phase shifters */
	protected void analyzeRawTransformers() throws PsseModelException
	{
		BusList rbus = getBuses();

		XfrZToolFactory ztf = XfrZToolFactory.Open(getPsseVersion());
		
		Transformer3RawList rlist = new Transformer3RawList(this, getDir());
		final TransformerPrep psprep = new TransformerPrep(),
				xfprep = new TransformerPrep();
		
		class ResolveXfrPrep
		{
			TransformerPrep get(int mode)
			{
				return (Math.abs(mode) == 3) ? psprep : xfprep;
			}
		}

		ResolveXfrPrep rp = new ResolveXfrPrep();
		ArrayList<Integer> ndx3w = new ArrayList<>();
		
		for (TransformerRaw xf : rlist)
		{
			String k = xf.getK();
			String bus1 = rbus.get(xf.getI()).getObjectID();
			String bus2 = rbus.get(xf.getJ()).getObjectID();
			XfrZTools zt = ztf.get(xf.getCZ());
			
			if (k.equals("0"))
			{
				rp.get(xf.getCOD1()).prep(xf, 1, bus1, bus2, zt.convert2W(xf));
			}
			else
			{
				String bus3 = rbus.get(xf.getK()).getObjectID();
				ndx3w.add(xf.getIndex());
				String newstar = "TXSTAR-"+xf.getObjectID();
				StarNetwork z = zt.convert3W(xf).star();
				rp.get(xf.getCOD1()).prep(xf, 1, bus1, newstar, z.getZ1());
				rp.get(xf.getCOD2()).prep(xf, 2, bus2, newstar, z.getZ2());
				rp.get(xf.getCOD3()).prep(xf, 3, bus3, newstar, z.getZ3());
			}
		}
		_buses.addStarNodes(rlist, ndx3w);
		_xfrList = new TransformerRawList(this, rlist, xfprep);
		_psList = new PhaseShifterRawList(this, rlist, psprep);
	}

	protected void analyzeRawShunts() throws PsseModelException
	{
		SwitchedShuntRawList rsh = new SwitchedShuntRawList(this);
		
		ArrayList<Integer> shndx = new ArrayList<>();
		ArrayList<Integer> svcndx = new ArrayList<>();
		
		for (int i=0; i < rsh.size(); ++i)
		{
			((testForSvc(rsh.getMODSW(i), rsh.getBINIT(i), rsh.getN(i), rsh.getB(i)))?svcndx:shndx).add(i);
		}
		
		_shList = new ShuntRawList(this, rsh, shndx);
		_svcList = new SvcRawList(this, rsh, svcndx);
	}
	
	
	boolean testForSvc(int modsw, float binit, int[] n, float[] b) throws PsseModelException
	{
		if (modsw == 2) return true;
		if (modsw == 0 && n[0] == 1)
		{
			if (n[1] == 0 && 0f < binit && binit < b[0]) return true;
			if (n[2] == 0 && b[0] < binit && binit < b[1]) return true;
		}
		return false;
	}


	@Override
	public LoadList getLoads() throws PsseModelException
	{
		if (_loads == null) _loads = new LoadList(this, getDir());
		return _loads;
	}

	@Override
	public GenList getGenerators() throws PsseModelException
	{
		if (_generatorList == null) _generatorList = new GenList(this, getDir());
		return _generatorList;
	}

	public int getIslandCount() throws PsseModelException
	{
		return _tp.getIslandCount();
	}

	public boolean isNodeEnergized(int node) throws PsseModelException
	{
		int island = _tp.getIsland(node);
		return (island == -1) ? false : _tp.isIslandEnergized(island);
	}

	public int getIsland(int node) throws PsseModelException
	{
		return _tp.getIsland(node);
	}

	public BusList getBusesForIsland(int island) throws PsseModelException
	{
		return new com.powerdata.openpa.psse.util.BusSubList(_buses,
				_tp.getIslandNodes(island));
	}

	public BusTypeCode getBusType(int node) throws PsseModelException
	{
		return _tp.getBusType(node);
	}

	@Override
	public IslandList getIslands() throws PsseModelException
	{
		if (_islands == null)
			_islands = new IslandList(this, _tp);
		return _islands;
	}

	@Override
	public int[] getBusNdxForType(BusTypeCode bustype)
			throws PsseModelException
	{
		return _tp.getBusNdxsForType(bustype);
	}


}