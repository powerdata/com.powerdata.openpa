package com.powerdata.openpa.pwrflow;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.Set;
import com.powerdata.openpa.ACBranch;
import com.powerdata.openpa.ACBranchList;
import com.powerdata.openpa.BusList;
import com.powerdata.openpa.GenList;
import com.powerdata.openpa.Island;
import com.powerdata.openpa.PAModel;
import com.powerdata.openpa.PAModelException;
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
		_nisland = model.getIslands().size();
		_nigrp = _nisland * NGRP;
		
		Arrays.fill(_type, pq);

		for (int i = 0; i < nbus; ++i)
		{
			_itype[i] = calcigrp(buses.getIsland(i).getIndex(), pq);
		}
		
		float[] ybbus = new float[nbus];
		
		for(Island i : model.getIslands())
		{
			configureTypes(i, bri, ybbus);
		}
	}
	
	int calcigrp(int i, int t)
	{
		return t + NGRP * i;
	}
	
	void configureTypes(Island island, BusRefIndex bri, float[] ybbus) throws PAModelException
	{
		float maxy = 0f;
		int maxb = -1;
		GenList gens = island.getGenerators();
		int indx = island.getIndex();
		int[] gbx = bri.get1TBus(gens);
		int ngen = gens.size();
		BusList buses = bri.getBuses();
		
		for(int i=0; i < ngen; ++i)
		{
			if (!gens.isOutOfSvc(i) && gens.isRegKV(i))
			{
				int bx = gbx[i];
				float yb = ybbus[bx];
				if (yb == 0f)
				{
					yb = computeYB(buses.get(bx).getACBranches());
					ybbus[bx] = yb;
				}
				if (maxy < yb)
				{
					maxy = yb;
					maxb = bx;
				}
				_type[bx] = PV;
				_itype[bx] = calcigrp(indx, PV); 
			}
		}

		if (maxb != -1)
		{
			_type[maxb] = REF;
			_itype[maxb] = calcigrp(indx, REF);
		}
	}


	float computeYB(Set<ACBranchList> acBranches) throws PAModelException
	{
		Complex ysum = new Complex(0,0);
		for(ACBranchList list : acBranches)
		{
			for(ACBranch b : list)
			{
				ysum = ysum.add(new Complex(b.getR(), b.getX()).inv());
			}
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
	
}
