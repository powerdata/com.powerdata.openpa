package com.powerdata.openpa.psse.csv;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.powerdata.openpa.psse.ACBranch;
import com.powerdata.openpa.psse.Bus;
import com.powerdata.openpa.psse.BusList;
import com.powerdata.openpa.psse.BusTypeCode;
import com.powerdata.openpa.psse.LineList;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.psse.TP;
import com.powerdata.openpa.psse.TransformerList;
import com.powerdata.openpa.tools.Complex;
import com.powerdata.openpa.tools.LinkNet;
import com.powerdata.openpa.tools.PComplex;

public class PsseModel extends com.powerdata.openpa.psse.PsseModel
{

	PsseRawModel 		_rmodel;
	LoadList			_loads;
	GenList				_generatorList;
	BusList				_buses;
//	TransformerSubList	_transformers;
	TransformerList	_transformers;
	LineSubList	_lines;
	PhaseShifterList	_phaseshifters;
	ShuntList	_shunts;
	SvcList	_svcs;
	TP _tp;
	IslandList _islands;
	LowXHandling _lowx;

	public PsseModel(String parms) throws PsseModelException
	{
		_rmodel = new PsseRawModel(parms);
		_lowx = _rmodel.getLowXHandling();
		
		//TODO:  This needs to be optional
		eliminateLowZBranches();
		_tp = new TP(this);
	}
	
	public File getDir() {return _rmodel.getDir();}
	
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
	
	@Override
	public Bus getBus(String id) throws PsseModelException
	{
		return getBuses().get(id);
	}

	@Override
	public BusList getBuses() throws PsseModelException {return _buses;}
	@Override
	public LineList getLines() throws PsseModelException {return _lines;}
	@Override
	public TransformerList getTransformers() throws PsseModelException {return _transformers;}
	@Override
	public PhaseShifterList getPhaseShifters() throws PsseModelException {return _phaseshifters;}
	@Override
	public ShuntList getShunts() throws PsseModelException {return _shunts;}
	@Override
	public SvcList getSvcs() throws PsseModelException {return _svcs;}

	void eliminateLowZBranches() throws PsseModelException
	{
		BusListRaw rbuses = _rmodel.getBuses();
		LineList rlines = _rmodel.getLines();
		
		TransformerRawList xfrlist = _rmodel.getTransformers();
		PhaseShifterRawList pslist = _rmodel.getPhaseShifters();
		
		int nbr = rlines.size()+xfrlist.size()+pslist.size();
		LinkNet elimlnet = new LinkNet();
		elimlnet.ensureCapacity(rbuses.size()-1, nbr);
		ArrayList<Integer> keepln = new ArrayList<>(nbr);
		ArrayList<Integer> keeptx = new ArrayList<>(nbr);
		
		eliminate(rlines, elimlnet, keepln);
		eliminate(xfrlist, elimlnet, keeptx);
		
		if (keepln.size() == rlines.size() && xfrlist.size() == keeptx.size())
		{
			System.err.println("TODO:  Clean up case where no elimination performed");
		}
		
		int nrbus = rbuses.size();
		int[] busndx = new int[nrbus];
		for(int i=0; i < nrbus; ++i) busndx[i] = i;
		for(int[] g : elimlnet.findGroups())
		{
			int ng = g.length;
			if (ng > 1)
			{
				int first = g[0];
				for (int i = 1; i < ng; ++i)
				{
					busndx[g[i]] = first;
				}
			}
		}
		
		/* branches in parallel with an eliminated branch also need to be eliminated */
		ArrayList<Integer> keepln2 = new ArrayList<>(keepln.size());
		ArrayList<Integer> keeptx2 = new ArrayList<>(keeptx.size());
		for(Integer br : keepln)
		{
			ACBranch b = rlines.get(br);
			int fbr = elimlnet.findBranch(b.getFromBus().getIndex(), b.getToBus().getIndex()); 
			if (fbr == -1)
			{
				keepln2.add(br);
			}
		}
		for(Integer br : keeptx)
		{
			ACBranch b = xfrlist.get(br);
			int fbr = elimlnet.findBranch(b.getFromBus().getIndex(), b.getToBus().getIndex()); 
			if (fbr == -1)
			{
				keeptx2.add(br);
			}
		}
		
		
		_buses = new BusSubList(this, rbuses, busndx);
		_transformers = new TransformerSubList(_buses, xfrlist, convertIndex(keeptx2));
		_transformers = xfrlist;
		_lines = new LineSubList(_buses, rlines, convertIndex(keepln2));
		_phaseshifters = new PhaseShifterList(_buses, pslist);
		_shunts = new ShuntList(_buses, _rmodel.getShunts());
		_svcs = new SvcList(_buses, _rmodel.getSvcs());

		if (_lowx == LowXHandling.Adjust)
		{
			_rmodel.adjustLowX(.001f);
		}
	}
	
	static int[] convertIndex(List<Integer> indexes)
	{
		int n = indexes.size();
		int[] ndxs = new int[n];
		for(int i=0; i < n; ++i)
			ndxs[i] = indexes.get(i);
		return ndxs;
	}
	
	void eliminate(List<?> branchList, LinkNet elimlnet, List<Integer> keep) throws PsseModelException
	{
		int nbr = branchList.size();
		for(int i=0; i < nbr; ++i)
		{
			ACBranch br = (ACBranch) branchList.get(i);
			if (br.isInSvc())
			{
				Bus fbus = br.getFromBus();
				Bus tbus = br.getToBus();
				int fbusx = fbus.getIndex();
				int tbusx = tbus.getIndex();
				float fvm = fbus.getVM(), tvm = tbus.getVM();
				float fva = fbus.getVArad(), tva = tbus.getVArad();
				
				if (_lowx == LowXHandling.ElimByVoltage && (Math.abs(fvm - tvm) < 0.00003 &&
					Math.abs(fva - tva) < 0.00003))
				{
					elimlnet.addBranch(fbusx, tbusx);
				}
				else if (_lowx == LowXHandling.ElimByX && Math.abs(br.getX()) < 0.001f)
				{
					elimlnet.addBranch(fbusx, tbusx);
				}
				else
				{
					keep.add(i);
				}
			}
		}
		
	}

	boolean isCloseWithSmallX(PComplex vf, PComplex vt, Complex z)
	{
//		return (Math.abs(vf.r() - vt.r()) <= .0001f && Math.abs(z.im()) < .0001f);
//		return z.re() <= 0.001f && Math.abs(z.im()) <= 0.001f;
		return false;
	}

	public int getIslandCount() throws PsseModelException
	{
		return _tp.getIslandCount();
	}

	public boolean isNodeEnergized(int node) throws PsseModelException
	{
		return _tp.isIslandEnergized(_tp.getIsland(node));
	}

	public int getIsland(int node) throws PsseModelException
	{
		return _tp.getIsland(node);
	}

	public BusList getBusesForIsland(int island) throws PsseModelException
	{
		return new com.powerdata.openpa.psse.BusSubList(_buses,
				_tp.getIslandNodes(island));
	}

	public BusTypeCode getBusType(int node) throws PsseModelException
	{
		return _tp.getBusType(node);
	}

	@Override
	public IslandList getIslands() throws PsseModelException
	{
		if (_islands == null) _islands = new IslandList(this, _tp);
		return _islands;
	}

	@Override
	public int[] getBusNdxForType(BusTypeCode bustype)
			throws PsseModelException
	{
		return _tp.getBusNdxsForType(bustype);
	}

}

