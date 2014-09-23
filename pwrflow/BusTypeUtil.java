package com.powerdata.openpa.pwrflow;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import com.powerdata.openpa.ACBranch;
import com.powerdata.openpa.Bus;
import com.powerdata.openpa.BusGrpMapBldr;
import com.powerdata.openpa.BusList;
import com.powerdata.openpa.SVCList;
import com.powerdata.openpa.Gen;
import com.powerdata.openpa.GenList;
import com.powerdata.openpa.Group;
import com.powerdata.openpa.GroupList;
import com.powerdata.openpa.Island;
import com.powerdata.openpa.IslandList;
import com.powerdata.openpa.LineList;
import com.powerdata.openpa.PAModel;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.Switch;
import com.powerdata.openpa.Switch.State;
import com.powerdata.openpa.impl.GroupMap;
import com.powerdata.openpa.tools.Complex;


public class BusTypeUtil
{
	PAModel _model;
	int[] _type, _itype;
	int _nisland, _nigrp;
	static public final int PV = BusType.PV.ordinal();
	static public final int PQ = BusType.PQ.ordinal();
	static public final int REF = BusType.Reference.ordinal();
	static public final int NGRP = BusType.values().length;

	WeakReference<GroupMap> _tmap = new WeakReference<>(null),
			_imap = new WeakReference<>(null);
	
	public BusTypeUtil(PAModel model, BusRefIndex bri) throws PAModelException
	{
		_model = model;
		setup(bri, null);
	}
	
	public BusTypeUtil(PAModel model, BusRefIndex bri, int[] svcpv) throws PAModelException
	{
		_model = model;
		setup(bri, svcpv);
	}
	
	void setup(BusRefIndex bri, int[] svcpv) throws PAModelException
	{
		BusList buses = bri.getBuses();
		int nbus = buses.size();
		_type = new int[nbus];
		_itype = new int[nbus];
		IslandList islands = _model.getIslands();
		_nisland = islands.size();
		_nigrp = _nisland * NGRP;
		
		Arrays.fill(_type, PQ);

		for (int i = 0; i < nbus; ++i)
		{
			_itype[i] = calcigrp(buses.getIsland(i).getIndex(), PQ);
		}
		
		GroupList glist = new GroupList(_model, new BusGrpMapBldr(_model)
		{
			@Override
			protected boolean incSW(Switch d) throws PAModelException
			{
				return d.getState() == State.Closed;
			}}
			.addPhaseShifters()
			.addSeriesCap()
			.addSeriesReac()
			.addSwitches()
			.addTransformers()
			.addTwoTermDCLines().getMap());

		float[] pmax = new float[nbus];
		for(Island i : islands)
		{
			if (i.isEnergized()) configureTypes(i, bri, pmax, svcpv);
		}
		findWidestPaths(glist, buses, islands, pmax);
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
	
	void findWidestPaths(GroupList glist, BusList sbuses, IslandList islands, float[] pmax) throws PAModelException
	{
		int ni = islands.size();
		int[] imax = new int[ni], smax = new int[ni];
		Arrays.fill(imax, -1);
		int ng = glist.size();
		for(int i=0; i < ng; ++i)
		{
			Group g = glist.get(i);
			Score score = mkScore(g, computeYB(g.getLines()), sbuses, pmax);
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
			if (bx != -1)
			{
				_type[bx] = REF;
				_itype[bx] = calcigrp(i, REF);
			}
		}
	}
	
	

	Score mkScore(Group g, float yb, BusList sbuses, float[] pmax) throws PAModelException
	{
		float pm = 0f;
		Bus br = null;
		
		for (Bus b : g.getBuses())
		{
			Bus sb = sbuses.getByBus(b);
			float tpm = pmax[sb.getIndex()];
			if (pm < tpm)
			{
				pm = tpm;
				br = sb;
			}
			
		}
		return (br == null) ? null : new Score(Math.round(yb + pm), br);
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
	
	void configureTypes(Island island, BusRefIndex bri, float[] pmax, int[] svcpv) throws PAModelException
	{
		GenList gens = island.getGenerators();
		int indx = island.getIndex();
		int[] gbx = bri.get1TBus(gens);
		int ngen = gens.size();
		
		for(int i=0; i < ngen; ++i)
		{
			Gen.Mode mode = gens.getMode(i);
			if (!gens.isOutOfSvc(i) && gens.isRegKV(i) && mode != Gen.Mode.OFF && mode != Gen.Mode.PMP)
			{
				int bx = gbx[i];
				_type[bx] = PV;
				pmax[bx] += gens.getOpMaxP(i);
				_itype[bx] = calcigrp(indx, PV); 
			}
		}
		
		if (svcpv != null)
		{
			SVCList svcs = island.getSVCs();
			int[] sbx = bri.get1TBus(svcs);
			int nsvc = svcpv.length;
			for (int i = 0; i < nsvc; ++i)
			{
				int bx = sbx[svcpv[i]];
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
			if (!b.isOutOfSvc())
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
	
	int[] getBuses(BusType type, int island)
	{
		GroupMap m = _imap.get();
		if (m == null)
		{
			m = new GroupMap(_itype, _nigrp);
			_imap = new WeakReference<>(m);
		}
		return m.get(calcigrp(island, type.ordinal()));
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
	
	public int[] getTypesOrdinal()
	{
		return _type;
	}
	
}
