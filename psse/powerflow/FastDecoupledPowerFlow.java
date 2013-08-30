package com.powerdata.openpa.psse.powerflow;

import java.util.Arrays;

import com.powerdata.openpa.psse.ACBranch;
import com.powerdata.openpa.psse.ACBranchList;
import com.powerdata.openpa.psse.BusList;
import com.powerdata.openpa.psse.BusTypeCode;
import com.powerdata.openpa.psse.Island;
import com.powerdata.openpa.psse.IslandList;
import com.powerdata.openpa.psse.PsseModel;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.psse.Shunt;
import com.powerdata.openpa.tools.Complex;
import com.powerdata.openpa.tools.FactorizedBMatrix;
import com.powerdata.openpa.tools.LinkNet;
import com.powerdata.openpa.tools.SparseBMatrix;

public class FastDecoupledPowerFlow
{
	PsseModel _model;
	FactorizedBMatrix _bp, _bpp;
	SparseBMatrix _prepbpp;
	int[] _pq, _pv, _angref, _hotislands;
	
	public FastDecoupledPowerFlow(PsseModel model) throws PsseModelException
	{
		_model = model;
		
		setupHotIslands();
		setupBusTypes();
		buildMatrices();
		
	}

	public void runPowerFlow() throws PsseModelException
	{
		int itermax = 40;
		boolean[] cvg = new boolean[_hotislands.length];
		BusList buses = _model.getBuses();
		int nbus = buses.size();
		float[] pmm = new float[nbus], qmm = new float[nbus];
		for(int i=0; i < nbus; ++i)
		{
			
		}
		
		
		for(int iiter=0; iiter < itermax; ++iiter)
		{
			
		}
		
		
	}
	
	void setupBusTypes() throws PsseModelException
	{
		IslandList islands = _model.getIslands();
		int npq=0, npv=0;
		for(int ihot : _hotislands)
		{
			Island i = islands.get(ihot);
			npq += i.getBusNdxsForType(BusTypeCode.Load).length;
			npv += i.getBusNdxsForType(BusTypeCode.Gen).length;
		}
		
		_pq = new int[npq];
		_pv = new int[npv];
		_angref = new int[_hotislands.length];
		npq=0; npv=0;
		
		for(int i=0; i < _hotislands.length; ++i)
		{
			Island island = islands.get(_hotislands[i]);
			int[] pq = island.getBusNdxsForType(BusTypeCode.Load);
			int[] pv = island.getBusNdxsForType(BusTypeCode.Gen);
			System.arraycopy(pq, 0, _pq, npq, pq.length);
			System.arraycopy(pv, 0, _pv, npv, pv.length);
			_angref[i] = island.getAngleRefBusNdx();
		}
	}

	void setupHotIslands() throws PsseModelException
	{
		IslandList islands = _model.getIslands();
		int nhot = 0;
		int[] hotisl = new int[islands.size()];
		for(int i=0; i < islands.size(); ++i)
		{
			if (islands.get(i).isEnergized())
				hotisl[nhot++] = i; 
		}
		
		_hotislands = Arrays.copyOf(hotisl, nhot);
	}

	void buildMatrices() throws PsseModelException
	{
		LinkNet net = new LinkNet();
		ACBranchList branches = _model.getBranches();
		int nbus = _model.getBuses().size(), nbranch = branches.size();
		net.ensureCapacity(nbus-1, nbranch);
		float[] bselfbp = new float[nbus];
		float[] bbranchbp = new float[nbranch];
		float[] bselfbpp = new float[nbus];
		float[] bbranchbpp = new float[nbranch];
		
		for(Shunt shunt : _model.getShunts())
		{
			bselfbpp[shunt.getBus().getIndex()] -= shunt.getBpu();
		}
		
		for(ACBranch br : branches)
		{
			int fbus = br.getFromBus().getIndex();
			int tbus = br.getToBus().getIndex();
			int brx = net.findBranch(fbus, tbus);
			if (brx == -1)
			{
				brx = net.addBranch(fbus, tbus);
			}
			Complex z = br.getZ();
			float bbp = 1/z.im();
			Complex y = z.inv();
			
			bbranchbp[brx] -= bbp;
			bselfbp[fbus] += bbp;
			bselfbp[tbus] += bbp;
			bbranchbpp[brx] -= y.im();
			bselfbpp[fbus] += (y.im() - br.getFromBcm());
			bselfbpp[tbus] += (y.im() - br.getToBcm());
		}
		int[] pbus = Arrays.copyOf(_pq, _pq.length+_pv.length);
		System.arraycopy(_pv, 0, pbus, _pq.length, _pv.length);
		SparseBMatrix prepbp = new SparseBMatrix(net.clone(), pbus, bbranchbp, bselfbp);
		_prepbpp = new SparseBMatrix(net, _pq, bbranchbpp, bselfbpp);
		
		_bp = prepbp.factorize();
		_bpp = _prepbpp.factorize();
	}
	
}
