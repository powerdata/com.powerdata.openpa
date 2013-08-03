package com.powerdata.openpa.psse.csv;

import java.io.File;
import java.util.ArrayList;

import com.powerdata.openpa.psse.BusList;
import com.powerdata.openpa.psse.LineList;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.psse.SwitchedShunt;
import com.powerdata.openpa.psse.TransformerCtrlMode;
import com.powerdata.openpa.psse.TransformerRaw;
import com.powerdata.openpa.psse.conversions.XfrZToolFactory;
import com.powerdata.openpa.psse.conversions.XfrZTools;
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

	public PsseRawModel(String parms) throws PsseModelException
	{
		QueryString q = new QueryString(parms);
		if (!q.containsKey("path"))
		{
			throw new PsseModelException("com.powerdata.openpa.psse.csv.PsseInputModel Missing path= in uri.");
		}
		_dir = new File(q.get("path")[0]);
		analyzeRawShunts();
		analyzeRawTransformers();

		
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
//		if (_shList == null) analyzeRawShunts();
		return _shList;
	}
	@Override
	public SvcRawList getSvcs() throws PsseModelException
	{
//		if (_svcList == null) analyzeRawShunts();
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
			TransformerPrep get(TransformerCtrlMode mode)
			{
				return (mode == TransformerCtrlMode.ActivePowerFlow) ? psprep : xfprep;
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
				rp.get(xf.getCtrlMode1()).prep(xf, 1, bus1, bus2, zt.convert2W(xf));
			}
			else
			{
				String bus3 = rbus.get(xf.getK()).getObjectID();
				ndx3w.add(xf.getIndex());
				String newstar = "TXSTAR-"+xf.getObjectID();
				StarNetwork z = zt.convert3W(xf).star();
				rp.get(xf.getCtrlMode1()).prep(xf, 1, bus1, newstar, z.getZ1());
				rp.get(xf.getCtrlMode2()).prep(xf, 2, bus2, newstar, z.getZ2());
				rp.get(xf.getCtrlMode3()).prep(xf, 3, bus3, newstar, z.getZ3());
			}
		}
		_xfrList = new TransformerRawList(this, rlist, xfprep);
		_psList = new PhaseShifterRawList(this, rlist, psprep);
		_buses.addStarNodes(rlist, ndx3w);
	}

	protected void analyzeRawShunts() throws PsseModelException
	{
		SwitchedShuntRawList rsh = new SwitchedShuntRawList(this);
		
		ArrayList<Integer> shndx = new ArrayList<>();
		ArrayList<Integer> svcndx = new ArrayList<>();
		
		for (SwitchedShunt s : rsh)
		{
			((s.getMODSW()==2)?svcndx:shndx).add(s.getIndex());
		}
		
		_shList = new ShuntRawList(this, rsh, shndx);
		_svcList = new SvcRawList(this, rsh, svcndx);
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


}