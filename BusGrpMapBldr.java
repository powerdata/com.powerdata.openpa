package com.powerdata.openpa;

import java.lang.ref.WeakReference;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.List;
import com.powerdata.openpa.impl.BasicGroupIndex;
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
	BusRefIndex _bri;
	
	public BusGrpMapBldr(PALists model) throws PAModelException
	{
		_model = model;
		_lnet = new LinkNet();
		_nbus = model.getBuses().size();
		_lnet.ensureCapacity(_nbus-1, 0);
		_lnet.addBuses(0, _nbus);
		_bri = BusRefIndex.CreateFromConnectivityBuses(model);
	}
	
	public BusGrpMapBldr addSwitches() throws PAModelException
	{
		addDev(_model.getSwitches(), d -> incSW(d));
		return this;
	}

	@FunctionalInterface
	interface T2DevPredicate<T extends TwoTermDev>
	{
		boolean test(T d) throws PAModelException;
	}
	
	<T extends TwoTermDev> void addDev(TwoTermDevListIfc<T> list, T2DevPredicate<T> p) throws PAModelException
	{
		int n = list.size();
		_nbr += n;
		_lnet.ensureCapacity(0, _nbr);
		BusRefIndex.TwoTerm tt = _bri.get2TBus(list);
		int[] frm = tt.getFromBus(), to = tt.getToBus();
		for(int i=0; i < n; ++i)
		{
			if (p.test(list.get(i)))
				_lnet.addBranch(frm[i], to[i]);
		}
	}
	
	public BusGrpMapBldr addLines() throws PAModelException
	{
		addDev(_model.getLines(), d -> incLN(d));
		return this;
	}
	public BusGrpMapBldr addSeriesReac() throws PAModelException
	{
		addDev(_model.getSeriesReactors(), d -> incSR(d));
		return this;
	}
	public BusGrpMapBldr addSeriesCap() throws PAModelException
	{
		addDev(_model.getSeriesCapacitors(), d -> incSC(d));
		return this;
	}
	public BusGrpMapBldr addTransformers() throws PAModelException
	{
		addDev(_model.getTransformers(), d -> incTX(d));
		return this;
	}
	public BusGrpMapBldr addPhaseShifters() throws PAModelException
	{
		addDev(_model.getPhaseShifters(), d -> incPS(d));
		return this;
	}
	public BusGrpMapBldr addTwoTermDCLines() throws PAModelException
	{
		addDev(_model.getTwoTermDCLines(), d -> incD2(d));
		return this;
	}
	
	public BusGrpMapBldr addAll() throws PAModelException
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
	protected boolean incSW(Switch d) throws PAModelException {return true;}
	/** subclass to define which objects get included, default to all */
	protected boolean incLN(Line d) throws PAModelException {return true;}
	/** subclass to define which objects get included, default to all */
	protected boolean incSR(SeriesReac d) throws PAModelException {return true;}
	/** subclass to define which objects get included, default to all */
	protected boolean incSC(SeriesCap d) throws PAModelException {return true;}
	/** subclass to define which objects get included, default to all */
	protected boolean incTX(Transformer d) throws PAModelException {return true;}
	/** subclass to define which objects get included, default to all */
	protected boolean incPS(PhaseShifter d) throws PAModelException {return true;}
	/** subclass to define which objects get included, default to all */
	protected boolean incD2(TwoTermDCLine d) throws PAModelException {return true;}
	
	public GroupIndex getMap() 
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

	static class BBusGrpMap extends BasicGroupIndex
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
