package com.powerdata.openpa.psse.csv;

import java.io.File;
import java.util.ArrayList;

import com.powerdata.openpa.psse.Bus;
import com.powerdata.openpa.psse.Gen;
import com.powerdata.openpa.psse.Line;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.psse.Transformer;
import com.powerdata.openpa.psse.conversions.TransformerRaw;
import com.powerdata.openpa.psse.conversions.XfrZToolFactory;
import com.powerdata.openpa.psse.conversions.XfrZTools;
import com.powerdata.openpa.psse.TransformerCtrlMode;
import com.powerdata.openpa.tools.QueryString;
import com.powerdata.openpa.tools.StarNetwork;

public class PsseModel extends com.powerdata.openpa.psse.PsseModel
{
	/** root of the directory where the csv files are stored */
	File _dir;
	
	GenList				_generatorList;
	BusList				_buses;
	LineList			_branchList;
	TransformerList		_xfrList;
	PhaseShifterList	_psList;
	
	public PsseModel(String parms) throws PsseModelException
	{
		QueryString q = new QueryString(parms);
		if (!q.containsKey("path"))
		{
			throw new PsseModelException("com.powerdata.openpa.psse.csv.PsseInputModel Missing path= in uri.");
		}
		_dir = new File(q.get("path")[0]);
	}
	public File getDir() { return _dir; }
	@Override
	public BusList getBuses() throws PsseModelException
	{
		if (_buses == null) _buses = new BusList(this);
		return _buses;
	}
	@Override
	public GenList getGenerators() throws PsseModelException
	{
		if (_generatorList == null) _generatorList = new GenList(this);
		return _generatorList;
	}
	@Override
	public LineList getLines() throws PsseModelException
	{
		if (_branchList == null) _branchList = new LineList(this);
		return _branchList;
	}
	@Override
	public TransformerList getTransformers() throws PsseModelException
	{
		if (_xfrList == null) analyzeRawTransformers();
		return _xfrList;
	}
	@Override
	public PhaseShifterList getPhaseShifters() throws PsseModelException
	{
		if (_psList == null) analyzeRawTransformers();
		return _psList;
	}
	
	/** convert 3-winding to 2-winding and detect phase shifters */
	protected void analyzeRawTransformers() throws PsseModelException
	{
		BusList buses = getBuses();
		int starnode = buses.size();

		XfrZToolFactory ztf = XfrZToolFactory.Open(getPsseVersion());
		
		TransformerRawList rlist = new TransformerRawList(this);
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
			int bus1 = xf.getBusI().getIndex();
			int bus2 = xf.getBusJ().getIndex();
			XfrZTools zt = ztf.get(xf.getCZ());
			
			if (k.equals("0"))
			{
				rp.get(xf.getCtrlMode1()).prep(xf, 1, bus1, bus2, zt.convert2W(xf));
			}
			else
			{
				int bus3 = xf.getBusK().getIndex();
				int newstar = starnode++;
				ndx3w.add(xf.getIndex());
				StarNetwork z = zt.convert3W(xf).star();
				rp.get(xf.getCtrlMode1()).prep(xf, 1, bus1, newstar, z.getZ1());
				rp.get(xf.getCtrlMode2()).prep(xf, 2, bus2, newstar, z.getZ2());
				rp.get(xf.getCtrlMode3()).prep(xf, 3, bus3, newstar, z.getZ3());
			}
		}
		_xfrList = new TransformerList(this, rlist, xfprep);
		_psList = new PhaseShifterList(this, rlist, psprep);
		buses.addStarNodes(rlist, ndx3w);
	}

	
	static public void main(String args[]) throws Exception
	{
		PsseModel eq = new PsseModel("path=/tmp/frcc/");
		for (Bus b : eq.getBuses())
		{
			System.out.println(b);
		}
		for (Gen g : eq.getGenerators())
		{
			System.out.println(g);
		}
		for (Line b : eq.getLines())
		{
			System.out.println(b);
		}
		for (Transformer t : eq.getTransformers())
		{
			System.out.println(t);
		}
	}
	
	
}

