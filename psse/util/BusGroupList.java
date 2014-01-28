package com.powerdata.openpa.psse.util;

import java.lang.ref.WeakReference;
import java.util.Arrays;

import com.powerdata.openpa.psse.*;
import com.powerdata.openpa.tools.BaseList;
import com.powerdata.openpa.tools.LinkNet;

/**
 * Create connectivity-based groups of buses
 * 
 * @author chris@powerdata.com
 *
 */
public class BusGroupList extends BaseList<BusGroup>
{
	protected enum Dev
	{
		/** Switch */				SW, 
		/** Line */ 				LN,
		/** Transformer */ 			TX,
		/** Phase Shifter */ 		PS,
		/** Two-Terminal DC Line */	D2;
	}
	
	protected enum List
	{
		/** Bus */  			BUS,
		/** Generator */ 		GEN,
		/** Load */ 			LD,
		/** Line */ 			LN,
		/** Transformer */ 		TX,
		/** PhaseShifter */ 	PS,
		/** Switch */ 			SW,
		/** Shunt */ 			SH,
		/** SVC */ 				SVC,
		/** Switched Shunt */ 	SS,
		/** Two Term DC Line */	D2;
	}
	
	protected PsseLists	_model;
	
	protected boolean[] _use = new boolean[Dev.values().length];
	protected boolean _needsinit = true;
	protected int _nbr = 0, _ngrp=0;
	protected int[] _bus2grp;
	
	@SuppressWarnings("unchecked")
	protected WeakReference<GrpMap>[] _lstref = 
		new WeakReference[List.values().length];
	
	protected abstract class ListSelector
	{
		protected int _selsize;
		public abstract boolean[] select() throws PsseModelException;
		public int getSelectedSize() {return _selsize;}
	}	
	
	protected ListSelector _swSel = new ListSelector()
	{
		@Override
		public boolean[] select() throws PsseModelException
		{
			_selsize = 0;
			SwitchList switches = _model.getSwitches();
			int nsw = switches.size();
			boolean[] rv = new boolean[nsw];
			for(int i=0; i < nsw; ++i)
			{
				if (incSW(switches.get(i)))
					{rv[i] = true; ++_selsize;}
			}
			return rv;
		}
	};
	
	protected ListSelector _lnSel = new ListSelector()
	{
		@Override
		public boolean[] select() throws PsseModelException
		{
			_selsize = 0;
			LineList lines = _model.getLines();
			int nsw = lines.size();
			boolean[] rv = new boolean[nsw];
			for(int i=0; i < nsw; ++i)
			{
				if (incLN(lines.get(i)))
					{rv[i] = true; ++_selsize;}
			}
			return rv;
		}
	};
	
	protected ListSelector _txSel = new ListSelector()
	{
		@Override
		public boolean[] select() throws PsseModelException
		{
			_selsize = 0;
			TransformerList transformers = _model.getTransformers();
			int nsw = transformers.size();
			boolean[] rv = new boolean[nsw];
			for(int i=0; i < nsw; ++i)
			{
				if (incTX(transformers.get(i)))
					{rv[i] = true; ++_selsize;}
			}
			return rv;
		}
	};
	
	protected ListSelector _psSel = new ListSelector()
	{
		@Override
		public boolean[] select() throws PsseModelException
		{
			_selsize = 0;
			PhaseShifterList pslist = _model.getPhaseShifters();
			int nsw = pslist.size();
			boolean[] rv = new boolean[nsw];
			for(int i=0; i < nsw; ++i)
			{
				if (incPS(pslist.get(i)))
					{rv[i] = true; ++_selsize;}
			}
			return rv;
		}
	};
	
	protected ListSelector _d2Sel = new ListSelector()
	{
		@Override
		public boolean[] select() throws PsseModelException
		{
			_selsize = 0;
			TwoTermDCLineList lines = _model.getTwoTermDCLines();
			int nsw = lines.size();
			boolean[] rv = new boolean[nsw];
			for(int i=0; i < nsw; ++i)
			{
				if (incD2(lines.get(i)))
					{rv[i] = true; ++_selsize;}
			}
			return rv;
		}
	};
	
	protected static interface GrpMap
	{
		int[] get(int grpndx);
	}
	
	/** Map groups back to original objects */
	protected class GrpMapDev implements GrpMap
	{
		int[] start, next, cnt;

		public GrpMapDev(int[] map)
		{
			start = new int[_ngrp];
			cnt = new int[_ngrp];
			next = new int[map.length]; 
			Arrays.fill(start, -1);
			for(int i=0; i < map.length; ++i)
			{
				int g = map[i];
				if (g != -1)
				{
					next[i] = start[g];
					start[g] = i;
					++cnt[g];
				}
			}
		}
		@Override
		public int[] get(int grpndx)
		{
			int[] rv = new int[cnt[grpndx]];
			fill(rv, 0, grpndx);
			return rv;
		}
		
		public void fill(int[] rv, int ofs, int grpndx)
		{
			int s = start[grpndx];
			while (s != -1)
			{
				rv[ofs++] = s;
				s = next[s];
			}
		}
		
		public int getCount(int grpndx) {return cnt[grpndx];}
	}
	
	protected class GrpMapDev2T implements GrpMap
	{
		GrpMapDev _frm, _to;
		
		public GrpMapDev2T(int[] fmap, int[] tmap)
		{
			_frm = new GrpMapDev(fmap);
			_to = new GrpMapDev(tmap);
		}

		@Override
		public int[] get(int grpndx)
		{
			int fcnt = _frm.getCount(grpndx);
			int cnt = fcnt + _to.getCount(grpndx);
			int[] rv = new int[cnt];
			_frm.fill(rv, 0, grpndx);
			_to.fill(rv, fcnt, grpndx);
			return rv;
		}
		
	}

	protected class GrpMapBus implements GrpMap
	{
		int[][] grps;
		public GrpMapBus(int[][] grps) {this.grps = grps;}
		@Override
		public int[] get(int grpndx) {return grps[grpndx];}
	}
	
	public BusGroupList(PsseLists plists)
	{
		_model = plists;
		Arrays.fill(_lstref, new WeakReference<int[][]>(null));
	}
	
	protected boolean use(Dev d) {return _use[d.ordinal()];}
	protected void use(Dev d, boolean tf) {_use[d.ordinal()] = tf; if (!_needsinit) _needsinit = true;}

	/** Group buses by switch */
	public BusGroupList addSwitches() throws PsseModelException {use(Dev.SW, true); return this;}
	/** Group buses by line */
	public BusGroupList addLines() throws PsseModelException {use(Dev.LN, true); return this; }
	/** Group buses by Transformer */
	public BusGroupList addTransformers() throws PsseModelException {use(Dev.TX, true); return this; }
	/** Group buses by PhaseShifter */
	public BusGroupList addPhaseShifters() throws PsseModelException {use(Dev.PS, true); return this; }
	/** Group buses by Two-Terminal DC Line */
	public BusGroupList addTwoTermDCLines() throws PsseModelException {use(Dev.D2, true); return this; }

	/** subclass to define which Switches get included, defaults to all */
	protected boolean incSW(Switch s) throws PsseModelException {return true;}
	/** subclass to define which Lines get included, defaults to all */
	protected boolean incLN(Line l) throws PsseModelException {return true;}
	/** subclass to define which Transformers get included, defaults to all */
	protected boolean incTX(Transformer t) throws PsseModelException {return true;}
	/** subclass to define which PhaseShifters get included, defaults to all */
	protected boolean incPS(PhaseShifter ps) throws PsseModelException {return true;}
	/** subclass to define which Two-Terminal DC Lines get included, defaults to all */
	protected boolean incD2(TwoTermDCLine l) throws PsseModelException {return true;}

	@Override
	public String getObjectID(int ndx) throws PsseModelException
	{
		return String.format("BusGroup %d", ndx);
	}

	@Override
	public BusGroup get(int ndx) {return new BusGroup(this, ndx);}

	@Override
	public int size()
	{
		if (_needsinit)
			try
			{
				initialize();
			} catch (PsseModelException e)
			{
				e.printStackTrace();
			}
		return _ngrp;
	}
	
	protected void initialize() throws PsseModelException
	{
		_needsinit = false;
		LinkNet net = createNet();
		
		int[][] lngrp = net.findGroups();
		_ngrp = lngrp.length;
		_bus2grp = new int[_model.getBuses().size()];
		for(int igrp=0; igrp < _ngrp; ++igrp)
		{
			for(int bus : lngrp[igrp])
				_bus2grp[bus] = igrp;
		}
		_lstref[List.BUS.ordinal()] =
				new WeakReference<GrpMap>(new GrpMapBus(lngrp));
	}
	
	protected LinkNet createNet() throws PsseModelException
	{
		LinkNet net = new LinkNet();
		BusList buses = _model.getBuses();
		int nbus = buses.size();
		net.ensureCapacity(nbus-1, _nbr);
		for (int i=0; i < nbus; ++i) net.addBus(i);
		
		if (use(Dev.SW)) addToNet(net, _model.getSwitches(),		_swSel);
		if (use(Dev.LN)) addToNet(net, _model.getLines(),			_lnSel);
		if (use(Dev.TX)) addToNet(net, _model.getTransformers(),	_txSel);
		if (use(Dev.PS)) addToNet(net, _model.getPhaseShifters(),	_psSel);
		if (use(Dev.D2)) addToNet(net, _model.getTwoTermDCLines(),	_d2Sel);

		return net;
	}

	protected void addToNet(LinkNet net, BaseList<? extends TwoTermDev> list,
			ListSelector sel) throws PsseModelException
	{
		boolean[] s = sel.select();
		int descnt = net.getBranchCount()+sel.getSelectedSize();
		if (_nbr < descnt)
		{
			_nbr = descnt;
			net.ensureCapacity(0, _nbr);
		}
		for(int i=0; i < list.size(); ++i)
		{
			TwoTermDev d = list.get(i);
			if (s[i])
			{
				net.addBranch(d.getFromBus().getIndex(),
						d.getToBus().getIndex());
			}
		}
	}


	protected GrpMap getListMap1T(List ltype,
			BaseList<? extends OneTermDev> list) throws PsseModelException
	{
		int idx = ltype.ordinal();
		GrpMap gmap = _lstref[idx].get();
		if (gmap == null)
		{
			gmap = map1TList(ltype, list);
			_lstref[idx] = new WeakReference<>(gmap);
		}
		return gmap;
	}

	protected GrpMap map1TList(List ltype, BaseList<? extends OneTermDev> list)
			throws PsseModelException
	{
		int cnt = list.size();
		int[] map = new int[cnt];
		for (int i = 0; i < cnt; ++i)
			map[i] = _bus2grp[list.get(i).getBus().getIndex()];

		System.err.format("%s cache miss\n", ltype.toString());
		return new GrpMapDev(map);
	}

	protected GrpMap getListMap2T(List ltype,
			BaseList<? extends TwoTermDev> list) throws PsseModelException
	{
		int idx = ltype.ordinal();
		GrpMap gmap = _lstref[idx].get();
		if (gmap == null)
		{
			gmap = map2TList(ltype, list);
			_lstref[idx] = new WeakReference<>(gmap);
		}
		return gmap;
	}

	protected GrpMap map2TList(List ltype,
			BaseList<? extends TwoTermDev> list) throws PsseModelException
	{
		int cnt = list.size();
		int[] fmap = new int[cnt], tmap = new int[cnt];
		for (int i = 0; i < cnt; ++i)
		{
			TwoTermDev d = list.get(i);
			int f = _bus2grp[d.getFromBus().getIndex()];
			int t = _bus2grp[d.getToBus().getIndex()];
			fmap[i] = f;
			tmap[i] = (f == t) ? -1 : t;
		}
		System.err.format("%s cache miss\n", ltype.toString());
		return new GrpMapDev2T(fmap, tmap);
	}
	
	protected GrpMap getBusGrpMap()
	{
		int idx = List.BUS.ordinal();
		GrpMap gmap = _lstref[idx].get();
		if (gmap ==  null)
		{
			gmap = new GrpMapDev(_bus2grp);
			_lstref[idx] = new WeakReference<>(gmap);
			System.err.println("Bus Group Map cache miss");
		}
		return gmap;
	}

	
	public BusList getBuses(int ndx) throws PsseModelException
	{
		return new BusSubList(_model.getBuses(), getBusGrpMap().get(ndx));
	}

	public GenList getGenerators(int ndx) throws PsseModelException
	{
		GenList g = _model.getGenerators();
		return new GenSubList(g, getListMap1T(List.GEN, g).get(ndx));
	}
	public LoadList getLoads(int ndx) throws PsseModelException
	{
		LoadList l = _model.getLoads();
		return new LoadSubList(l, getListMap1T(List.LD, l).get(ndx));
	}
	
	public LineList getLines(int ndx) throws PsseModelException
	{
		LineList l = _model.getLines();
		return new LineSubList(l, getListMap2T(List.LN, l).get(ndx));
	}
	public TransformerList getTransformers(int ndx) throws PsseModelException
	{
		TransformerList tlist = _model.getTransformers();
		return  new TransformerSubList(tlist, getListMap2T(List.TX, tlist).get(ndx));
	}
	public PhaseShifterList getPhaseShifters(int ndx) throws PsseModelException
	{
		PhaseShifterList pslist = _model.getPhaseShifters();
		return new PhaseShifterSubList(pslist, getListMap2T(List.PS, pslist).get(ndx));
	}
	public SwitchList getSwitches(int ndx) throws PsseModelException
	{
		SwitchList swlist = _model.getSwitches();
		return new SwitchSubList(swlist, getListMap2T(List.SW, swlist).get(ndx));
	}
	public ShuntList getShunts(int ndx) throws PsseModelException
	{
		ShuntList shlist = _model.getShunts();
		return new ShuntSubList(shlist, getListMap1T(List.SH, shlist).get(ndx));
	}
	public SvcList getSvcs(int ndx) throws PsseModelException
	{
		SvcList svclist = _model.getSvcs();
		return new SvcSubList(svclist, getListMap1T(List.SVC, svclist).get(ndx));
	}
	public SwitchedShuntList getSwitchedShunts(int ndx) throws PsseModelException
	{
		SwitchedShuntList shlist = _model.getSwitchedShunts();
		return new SwitchedShuntSubList(shlist, getListMap1T(List.SS, shlist).get(ndx));
	}
	public TwoTermDCLineList getTwoTermDCLines(int ndx) throws PsseModelException
	{
		TwoTermDCLineList dclist = _model.getTwoTermDCLines();
		return new TwoTermDCLineSubList(dclist, getListMap2T(List.D2, dclist).get(ndx));
	}
}
