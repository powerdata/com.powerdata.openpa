package com.powerdata.openpa.pwrflow;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import com.powerdata.openpa.ACBranch;
import com.powerdata.openpa.Bus;
import com.powerdata.openpa.BusGrpMapBldr;
import com.powerdata.openpa.BusList;
import com.powerdata.openpa.BusRefIndex;
import com.powerdata.openpa.Line;
import com.powerdata.openpa.PhaseShifter;
import com.powerdata.openpa.SVC;
import com.powerdata.openpa.SVCList;
import com.powerdata.openpa.Gen;
import com.powerdata.openpa.GenList;
import com.powerdata.openpa.Group;
import com.powerdata.openpa.GroupList;
import com.powerdata.openpa.ElectricalIsland;
import com.powerdata.openpa.ElectricalIslandList;
import com.powerdata.openpa.LineList;
import com.powerdata.openpa.PAModel;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.SeriesCap;
import com.powerdata.openpa.SeriesReac;
import com.powerdata.openpa.SubLists;
import com.powerdata.openpa.Switch;
import com.powerdata.openpa.Switch.State;
import com.powerdata.openpa.Transformer;
import com.powerdata.openpa.TwoTermDCLine;
import com.powerdata.openpa.impl.GroupMap;
import com.powerdata.openpa.tools.Complex;


public class BusTypeUtil
{
	PAModel _model;
	int[] _type, _itype;
	int _nisland, _nigrp;
	static private final int PV = BusType.PV.ordinal();
	static private final int PQ = BusType.PQ.ordinal();
	static private final int REF = BusType.Reference.ordinal();
	/** internal-only code to represent the possibility that a pump is regulating KV, may be able to remove it */
	static private final int PVNR = -PV;
	static private final int NGRP = BusType.values().length;

	WeakReference<GroupMap> _tmap = new WeakReference<>(null),
			_imap = new WeakReference<>(null);
	public BusTypeUtil() {}
	public BusTypeUtil(PAModel model, BusRefIndex bri) throws PAModelException
	{
		_model = model;
		SVCList svcs = model.getSVCs();
		int nsvc = svcs.size(), npv=0;
		int[] sx = new int[nsvc];
		for(SVC s : svcs)
		{
			if (s.getSlope() == 0f && s.isInService())
				sx[npv++] = s.getIndex();
		}
		setup(bri, SubLists.getSVCSublist(svcs, sx));
	}
	
	public BusTypeUtil(PAModel model, BusRefIndex bri, SVCList pvsvc) throws PAModelException
	{
		_model = model;
		setup(bri, pvsvc);
	}
	
	void setup(BusRefIndex bri, SVCList svcpv) throws PAModelException
	{
		BusList buses = bri.getBuses();
		int nbus = buses.size();
		_type = new int[nbus];
		_itype = new int[nbus];
		ElectricalIslandList islands = _model.getElectricalIslands();
		_nisland = islands.size();
		_nigrp = _nisland * NGRP;
		
		Arrays.fill(_type, PQ);

		for (int i = 0; i < nbus; ++i)
		{
			_itype[i] = calcigrp(buses.getIsland(i).getIndex(), PQ);
		}
		/** Group buses that are effectively in the same station */
		GroupList glist = new GroupList(_model, new BusGrpMapBldr(_model)
		{
			@Override
			protected boolean incSW(Switch d) throws PAModelException
			{
				return d.getState() == State.Closed;
			}

			@Override
			protected boolean incLN(Line d) throws PAModelException
			{
				return d.isInService();
			}

			@Override
			protected boolean incSR(SeriesReac d) throws PAModelException
			{
				return d.isInService();
			}

			@Override
			protected boolean incSC(SeriesCap d) throws PAModelException
			{
				return d.isInService();
			}

			@Override
			protected boolean incTX(Transformer d) throws PAModelException
			{
				return d.isInService();
			}

			@Override
			protected boolean incPS(PhaseShifter d) throws PAModelException
			{
				return d.isInService();
			}

			@Override
			protected boolean incD2(TwoTermDCLine d) throws PAModelException
			{
				return d.isInService();
			}}
			.addPhaseShifters()
			.addSeriesCap()
			.addSeriesReac()
			.addSwitches()
			.addTransformers().getMap());

		float[] pmax = new float[nbus], qmax = new float[nbus];
		for(ElectricalIsland i : islands)
		{
			if (i.isEnergized()) configureTypes(i, bri, pmax, qmax, svcpv);
		}
		findWidestPaths(glist, buses, islands, pmax, qmax);
	}
	
	private static class Score
	{
		int score;
		Bus bus;
		Score(int score, Bus bus)
		{
			this.score = score;
			this.bus = bus;
		}
		@Override
		public String toString()
		{
			return String.format("%s: %d", bus, score);
		}
		
	}
	
	void findWidestPaths(GroupList glist, BusList sbuses, ElectricalIslandList islands,
			float[] pmax, float[] qmax) throws PAModelException
	{
		int ni = islands.size();
		int[] imax = new int[ni], smax = new int[ni];
		Arrays.fill(imax, -1);
		int ng = glist.size();
		for(int i=0; i < ng; ++i)
		{
			Group g = glist.get(i);
			Score score = mkScore(g, computeYB(g.getLines()), sbuses, pmax, qmax);
			if (score != null)
			{
				int isl = score.bus.getIsland().getIndex();
				if (smax[isl] < score.score)
				{
					smax[isl] = score.score;
					imax[isl] = score.bus.getIndex();
				}
			}			
		}
		
		for(int i=0; i < ni; ++i)
		{
			int bx = imax[i];
			if (bx != -1 && _type[bx] == PV)
			{
				_type[bx] = REF;
				_itype[bx] = calcigrp(i, REF);
			}
		}
	}
	
	

	Score mkScore(Group g, float yb, BusList sbuses, float[] pmax, float[] qmax)
			throws PAModelException
	{
		float qm = 0f;
		Bus br = null;
		boolean pmp = false;
		for (Bus b : g.getBuses())
		{
			Bus sb = sbuses.getByBus(b);
			int sbndx = sb.getIndex();
			float tqm = qmax[sbndx];
			if (_type[sbndx] == PVNR)
			{
				pmp = true;
			}
			else if (qm < tqm)
			{
				qm = tqm;
				br = sb;
			}
		}
		return (br == null || pmp) ? null : new Score(Math.round(yb/2f + pmax[br.getIndex()] + qm), br);
//		return (br == null) ? null : new Score(Math.round(qm), br);
	}

//	Bus selectBus(BusList gbuses, BusList sbuses) throws PAModelException
//	{
//		Bus max = null;
//		for(Bus b : gbuses)
//		{
//			Bus t = sbuses.getByBus(b);
//			if (max == null || max != t && max.getBuses().size() < t.getBuses().size())
//			{
//				max = t;
//			}
//		}
//		return max;
//	}

	int calcigrp(int i, int t)
	{
		return t + NGRP * i;
	}
	
	void configureTypes(ElectricalIsland island, BusRefIndex bri, float[] pmax, float[] qmax, SVCList svcpv)
			throws PAModelException
	{
		GenList gens = island.getGenerators();
		int indx = island.getIndex();
		int[] gbx = bri.get1TBus(gens);
		int ngen = gens.size();
		
		for(int i=0; i < ngen; ++i)
		{
			Gen g = gens.get(i);
			if (g.unitInAVR())
			{
				int bx = gbx[i];
				_type[bx] = PV;
				int t = (g.getMode() == Gen.Mode.PMP) ? PVNR : PV;
				if (t != PVNR)
				{
					pmax[bx] += g.getOpMaxP();
					qmax[bx] += g.getMaxQ() - g.getMinQ();
				}
				_itype[bx] = calcigrp(indx, t); 
			}
		}
		
		BusList sbus = bri.getBuses();
		for (SVC s : svcpv)
		{
			if (s.isInService())
			{
				int bx = sbus.getByBus(s.getBus()).getIndex(); 
				_type[bx] = PV;
				_itype[bx] = calcigrp(indx, PV);
			}
		}
	}

	float computeYB(LineList lines) throws PAModelException
	{
		Complex ysum = new Complex(0,0);
		for(ACBranch b : lines)
		{
			if (b.isInService())
				ysum = ysum.add(new Complex(b.getR(), b.getX()).inv());
		}
		return ysum.abs();
	}

	int[] getBuses(BusType type)
	{
		GroupMap m = _tmap.get();
		if (m == null)
		{
			m = new GroupMap(_type, NGRP);
			_tmap = new WeakReference<>(m);
		}
		return m.get(type.ordinal());
	}
	
	int[] getBuses(BusType type, ElectricalIsland island)
	{
		GroupMap m = _imap.get();
		if (m == null)
		{
			m = new GroupMap(_itype, _nigrp);
			_imap = new WeakReference<>(m);
		}
		return m.get(calcigrp(island.getIndex(), type.ordinal()));
	}

	public BusType[] getTypes()
	{
		BusType[] src = BusType.values();
		int n = _type.length;
		BusType[] rv = new BusType[n];
		for(int i=0; i < n; ++i)
			rv[i] = src[_type[i]];
		return rv;
	}
	
	public BusType getType(int ndx)
	{
		return BusType.values()[_type[ndx]];
	}
	
	public int[] getTypesOrdinal()
	{
		return _type;
	}

	public void changeType(BusType ntype, int bus, int busisland)
	{
		_imap = new WeakReference<>(null);
		_tmap = new WeakReference<>(null);
		_itype[bus] = calcigrp(busisland, ntype.ordinal());
		_type[bus] = ntype.ordinal();
	}
	

}
