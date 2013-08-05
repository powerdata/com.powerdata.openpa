package com.powerdata.openpa.psse.csv;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.powerdata.openpa.psse.ACBranch;
import com.powerdata.openpa.psse.Bus;
import com.powerdata.openpa.psse.BusList;
import com.powerdata.openpa.psse.BusSubList;
import com.powerdata.openpa.psse.LineList;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.psse.TransformerList;
import com.powerdata.openpa.tools.Complex;
import com.powerdata.openpa.tools.LinkNet;
import com.powerdata.openpa.tools.PComplex;

public class PsseModel extends com.powerdata.openpa.psse.PsseModel
{
	float				_lowxthr = 0.001f;
	float				_lowrthr = 0.0001f;

	PsseRawModel 		_rmodel;
	LoadList			_loads;
	GenList				_generatorList;
	BusList				_buses;
	TransformerSubList	_transformers;
	LineSubList	_lines;
	PhaseShifterList	_phaseshifters;
	ShuntList	_shunts;
	SvcList	_svcs;

	public PsseModel(String parms) throws PsseModelException
	{
		_rmodel = new PsseRawModel(parms);
		eliminateLowZBranches();

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
		elimlnet.ensureCapacity(rbuses.size(), nbr);
		ArrayList<Integer> keepln = new ArrayList<>(nbr);
		ArrayList<Integer> keeptx = new ArrayList<>(nbr);
		
		eliminate(rlines, elimlnet, keepln);
		eliminate(xfrlist, elimlnet, keeptx);
		
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
		
		_buses = new BusSubList(rbuses, busndx);
		_transformers = new TransformerSubList(_buses, xfrlist, convertIndex(keeptx));
		_lines = new LineSubList(_buses, rlines, convertIndex(keepln));
		_phaseshifters = new PhaseShifterList(_buses, pslist);
		_shunts = new ShuntList(_buses, _rmodel.getShunts());
		_svcs = new SvcList(_buses, _rmodel.getSvcs());
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
				Complex z = br.getZ();
				Bus fbus = br.getFromBus();
				Bus tbus = br.getToBus();
				int fbusx = fbus.getIndex();
				int tbusx = tbus.getIndex();
				PComplex vf = fbus.getVoltage();
				PComplex vt = tbus.getVoltage();
				
				
//				if ((Math.round(vf.r()*cutoff) == Math.round(vt.r()*cutoff) &&
//						Math.round(vf.theta()*cutoff) == Math.round(vt.theta()*cutoff)))
//					Math.round(vf.theta()*cutoff) == Math.round(vt.theta()*cutoff)) ||
//					(z.re() <= _lowrthr && Math.abs(z.im()) <= _lowxthr))

				if (Math.abs(vf.r()-vt.r()) < 0.00003 &&
					Math.abs(vf.theta()-vt.theta()) < 0.00003)
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


}

