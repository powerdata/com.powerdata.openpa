package com.powerdata.openpa.pwrflow;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import com.powerdata.openpa.ACBranch;
import com.powerdata.openpa.Bus;
import com.powerdata.openpa.BusGrpMapBldr;
import com.powerdata.openpa.BusList;
import com.powerdata.openpa.Gen;
import com.powerdata.openpa.GenList;
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
	static private final int PV = BusType.PV.ordinal();
	static private final int REF = BusType.Reference.ordinal();
	static private final int NGRP = BusType.values().length;

	WeakReference<GroupMap> _tmap = new WeakReference<>(null),
			_imap = new WeakReference<>(null);
	
	public BusTypeUtil(PAModel model, BusRefIndex bri) throws PAModelException
	{
		_model = model;
		BusList buses = bri.getBuses();
		int nbus = buses.size();
		_type = new int[nbus];
		_itype = new int[nbus];
		int pq = BusType.PQ.ordinal();
		IslandList islands = model.getIslands();
		_nisland = islands.size();
		_nigrp = _nisland * NGRP;
		
		Arrays.fill(_type, pq);

		for (int i = 0; i < nbus; ++i)
		{
			_itype[i] = calcigrp(buses.getIsland(i).getIndex(), pq);
		}
		
		GroupList glist = new GroupList(model, new BusGrpMapBldr(model)
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

		
		for(Island i : islands)
		{
			if (i.isEnergized()) configureTypes(i, bri);
		}
		findWidestPaths(glist, buses, islands);
	}
	
	void findWidestPaths(GroupList glist, BusList sbuses, IslandList islands) throws PAModelException
	{
		int ni = islands.size();
		int[] imax = new int[ni];
		Arrays.fill(imax, -1);
		float[] ymax = new float[ni];
		int ng = glist.size();
		for(int i=0; i < ng; ++i)
		{
			float ty = computeYB(glist.getLines(i));
			Bus tn = selectBus(glist.getBuses(i), sbuses);
			int isl = islands.getByBus(tn.getBuses().get(0)).getIndex();
			if (ymax[isl] < ty || imax[isl] == -1)
			{
				ymax[isl] = ty;
				imax[isl] = tn.getIndex();
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

	Bus selectBus(BusList gbuses, BusList sbuses) throws PAModelException
	{
		Bus max = null;
		for(Bus b : gbuses)
		{
			Bus t = sbuses.getByBus(b);
			if (max == null || max != t && max.getBuses().size() < t.getBuses().size())
			{
				max = t;
			}
		}
		return max;
	}

	int calcigrp(int i, int t)
	{
		return t + NGRP * i;
	}
	
	void configureTypes(Island island, BusRefIndex bri) throws PAModelException
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
	
}
