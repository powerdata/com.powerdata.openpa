package com.powerdata.openpa.psse.util;

import java.lang.ref.WeakReference;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.List;

import com.powerdata.openpa.psse.*;
import com.powerdata.openpa.tools.BaseList;
import com.powerdata.openpa.tools.LinkNet;

/**
 * Create connectivity-based groups of buses, where the groups
 * are derived from connections of two-terminal devices
 * 
 * @author chris@powerdata.com
 *
 */
public class BusGroup2TDevList extends BusGroupList
{
	protected enum Dev
	{
		/** Switch */				SW, 
		/** Line */ 				LN,
		/** Transformer */ 			TX,
		/** Phase Shifter */ 		PS,
		/** Two-Terminal DC Line */	D2;
	}
	
	protected boolean[] _use = new boolean[Dev.values().length];
	/** number of branches */
	protected int _nbr = 0;
	boolean _initialized = false;
	
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
	
	/** Save the output from LinkNet.findGroups in a mapping for as long as possible */
	protected class GrpMapBus extends AbstractList<int[]>
	{
		int[][] grps;
		public GrpMapBus(int[][] grps) {this.grps = grps;}
		@Override
		public int[] get(int grpndx) {return grps[grpndx];}
		@Override
		public int size() {return _ngrp;}		
	}
	
	public BusGroup2TDevList(PsseLists plists) throws PsseModelException {super(plists);}

	protected boolean use(Dev d) {return _use[d.ordinal()];}
	protected void use(Dev d, boolean tf) {_use[d.ordinal()] = tf; _initialized = false;}

	/** Group buses by switch */
	public BusGroup2TDevList addSwitches() throws PsseModelException {use(Dev.SW, true); return this;}
	/** Group buses by line */
	public BusGroup2TDevList addLines() throws PsseModelException {use(Dev.LN, true); return this; }
	/** Group buses by Transformer */
	public BusGroup2TDevList addTransformers() throws PsseModelException {use(Dev.TX, true); return this; }
	/** Group buses by PhaseShifter */
	public BusGroup2TDevList addPhaseShifters() throws PsseModelException {use(Dev.PS, true); return this; }
	/** Group buses by Two-Terminal DC Line */
	public BusGroup2TDevList addTwoTermDCLines() throws PsseModelException {use(Dev.D2, true); return this; }

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
	public int findGrpNdx(Bus bus) throws PsseModelException {testinit(); return _bus2grp[bus.getRootIndex()];}
	@Override
	public int findGrpNdx(int busndx) throws PsseModelException {testinit(); return _bus2grp[busndx];}
	
	@Override
	public int size()
	{
		try
		{
			testinit();
		} catch (PsseModelException e)
		{
			e.printStackTrace();
		}
		return _ngrp;
	}
	
	protected void testinit() throws PsseModelException
	{
		if (!_initialized) initialize();
	}
	
	protected void initialize() throws PsseModelException
	{
		_initialized = true;
		LinkNet net = createNet();
		
		int[][] lngrp = net.findGroups();
		_ngrp = lngrp.length;
		_bus2grp = new int[_model.getBuses().size()];
		for(int igrp=0; igrp < _ngrp; ++igrp)
		{
			for(int bus : lngrp[igrp])
				_bus2grp[bus] = igrp;
		}
		_lstref[EqList.BUS.ordinal()] =
				new WeakReference<List<int[]>>(new GrpMapBus(lngrp));
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


}
