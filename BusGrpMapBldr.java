package com.powerdata.openpa;

import java.lang.ref.WeakReference;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.List;
import com.powerdata.openpa.impl.BasicBusGrpMap;
import com.powerdata.openpa.tools.LinkNet;

/**
 * Build a bus group map based on device connectivity
 * @author chris@powerdata.com
 *
 */
public class BusGrpMapBldr
{
	PALists _model;
	LinkNet _lnet;
	int _nbus;
	int _nbr = 0;
	
	public BusGrpMapBldr(PALists model)
	{
		_model = model;
		_lnet = new LinkNet();
		_nbus = model.getBuses().size();
		_lnet.ensureCapacity(_nbus-1, 0);
		_lnet.addBuses(0, _nbus);
	}
	
	public BusGrpMapBldr addSwitches()
	{
		SwitchList list = _model.getSwitches();
		int n = list.size();
		_nbr += n;
		_lnet.ensureCapacity(0, _nbr);
		for(Switch s : list)
			if (incSW(s)) addDev2T(s);
		return this;
	}

	void addDev2T(TwoTermDev d)
	{
		int fb = d.getFromBus().getIndex();
		int tb = d.getToBus().getIndex();
		_lnet.addBranch(fb, tb);
	}

	public BusGrpMapBldr addLines()
	{
		LineList list = _model.getLines();
		int n = list.size();
		_nbr += n;
		_lnet.ensureCapacity(0, _nbr);
		for(Line s : list)
		{
			if (incLN(s))
				addDev2T(s);
		}
		return this;
	}
	public BusGrpMapBldr addSeriesReac()
	{
		SeriesReacList list = _model.getSeriesReactors();
		int n = list.size();
		_nbr += n;
		_lnet.ensureCapacity(0, _nbr);
		for(SeriesReac s : list)
		{
			if (incSR(s))
				addDev2T(s);
		}
		return this;
	}
	public BusGrpMapBldr addSeriesCap()
	{
		SeriesCapList list = _model.getSeriesCapacitors();
		int n = list.size();
		_nbr += n;
		_lnet.ensureCapacity(0, _nbr);
		for(SeriesCap s : list)
		{
			if (incSC(s))
				addDev2T(s);
		}
		return this;
	}
	public BusGrpMapBldr addTransformers()
	{
		TransformerList list = _model.getTransformers();
		int n = list.size();
		_nbr += n;
		_lnet.ensureCapacity(0, _nbr);
		for(Transformer s : list)
		{
			if (incTX(s))
				addDev2T(s);
		}
		return this;
	}
	public BusGrpMapBldr addPhaseShifters()
	{
		PhaseShifterList list = _model.getPhaseShifters();
		int n = list.size();
		_nbr += n;
		_lnet.ensureCapacity(0, _nbr);
		for(PhaseShifter s : list)
		{
			if (incPS(s))
				addDev2T(s);
		}
		return this;
	}
	public BusGrpMapBldr addTwoTermDCLines()
	{
		TwoTermDCLineList list = _model.getTwoTermDCLines();
		int n = list.size();
		_nbr += n;
		_lnet.ensureCapacity(0, _nbr);
		for(TwoTermDCLine s : list)
		{
			if (incD2(s))
				addDev2T(s);
		}
		return this;
	}
	
	public BusGrpMapBldr addAll()
	{
		addSwitches();
		addLines();
		addSeriesReac();
		addSeriesCap();
		addTransformers();
		addPhaseShifters();
		addTwoTermDCLines();
		return this;
	}
	
	/** subclass to define which objects get included, default to all */
	protected boolean incSW(Switch d) {return true;}
	/** subclass to define which objects get included, default to all */
	protected boolean incLN(Line d) {return true;}
	/** subclass to define which objects get included, default to all */
	protected boolean incSR(SeriesReac d) {return true;}
	/** subclass to define which objects get included, default to all */
	protected boolean incSC(SeriesCap d) {return true;}
	/** subclass to define which objects get included, default to all */
	protected boolean incTX(Transformer d) {return true;}
	/** subclass to define which objects get included, default to all */
	protected boolean incPS(PhaseShifter d) {return true;}
	/** subclass to define which objects get included, default to all */
	protected boolean incD2(TwoTermDCLine d) {return true;}
	
	public BusGrpMap getMap() 
	{
		int[][] grps = _lnet.findGroups();
		return new BBusGrpMap(grps, _nbus);
	}

	static class FixedGrpMap extends AbstractList<int[]>
	{
		int[][] _grps;
		
		FixedGrpMap(int[][] grps)
		{
			_grps = grps;
		}
		
		@Override
		public int[] get(int index)
		{
			return _grps[index];
		}

		@Override
		public int size()
		{
			return _grps.length;
		}
		
	};
	

	static class BBusGrpMap extends BasicBusGrpMap
	{
		public BBusGrpMap(int[][] grps, int nbus)
		{
			List<int[]> fm = new FixedGrpMap(grps);
			_grps = new WeakReference<>(fm);
			_ngrp = grps.length;
			_map = new int[nbus];
			Arrays.fill(_map, -1);
			for(int ig = 0; ig < _ngrp; ++ig)
			{
				for(int b : grps[ig])
					_map[b] = ig;
			}
		}

		
	}
	
}
