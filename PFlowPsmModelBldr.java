package com.powerdata.openpa;


import gnu.trove.list.TFloatList;
import gnu.trove.list.array.TFloatArrayList;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TFloatIntHashMap;
import gnu.trove.map.hash.TObjectIntHashMap;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import com.powerdata.openpa.Gen.Type;
import com.powerdata.openpa.tools.QueryString;
import com.powerdata.openpa.tools.SimpleCSV;
import com.powerdata.openpa.impl.AreaListI;
import com.powerdata.openpa.impl.BusListI;
import com.powerdata.openpa.impl.GenListI;
import com.powerdata.openpa.impl.GroupMap;
import com.powerdata.openpa.impl.ElectricalIslandListI;
import com.powerdata.openpa.impl.LineListI;
import com.powerdata.openpa.impl.LoadListI;
import com.powerdata.openpa.impl.OwnerListI;
import com.powerdata.openpa.impl.PhaseShifterListI;
import com.powerdata.openpa.impl.SVCListI;
import com.powerdata.openpa.impl.SeriesCapListI;
import com.powerdata.openpa.impl.SeriesReacListI;
import com.powerdata.openpa.impl.ShuntCapListI;
import com.powerdata.openpa.impl.ShuntReacListI;
import com.powerdata.openpa.impl.StationListI;
import com.powerdata.openpa.impl.SteamTurbineListI;
import com.powerdata.openpa.impl.SwitchListI;
import com.powerdata.openpa.impl.TransformerListI;
import com.powerdata.openpa.impl.VoltageLevelListI;

public class PFlowPsmModelBldr extends PflowModelBuilder 
{
	File _dir;
	
	//CSV files
	SimpleCSV _busCSV;
	SimpleCSV _switchCSV;
	SimpleCSV _lineCSV;
	SimpleCSV _areaCSV;
	SimpleCSV _substationCSV;
	SimpleCSV _svcCSV;
	SimpleCSV _shuntCapCSV;
	SimpleCSV _shuntReacCSV;
	SimpleCSV _loadCSV;
	SimpleCSV _genCSV;
	SimpleCSV _seriesCapCSV;
	SimpleCSV _seriesReacCSV;
	SimpleCSV _transformerCSV;
	SimpleCSV _switchTypeCSV;
	SimpleCSV _tfmrWindingCSV;
	SimpleCSV _phaseTapChgCSV;
	SimpleCSV _synchMachineCSV;
	SimpleCSV _ratioTapChgCSV;
	SimpleCSV _reacCapCurveCSV;
	SimpleCSV _orgCSV;

	
	//Case CSV files
	SimpleCSV _areaCaseCSV;
	SimpleCSV _busCaseCSV;
	SimpleCSV _loadCaseCSV;
	SimpleCSV _shuntCapCaseCSV;
	SimpleCSV _shuntReacCaseCSV;
	SimpleCSV _seriesCapCaseCSV;
	SimpleCSV _seriesReacCaseCSV;
	SimpleCSV _genCaseCSV;
	SimpleCSV _synchCaseCSV;
	SimpleCSV _genToSynchCSV;
	SimpleCSV _ratioTapChgCaseCSV;
	SimpleCSV _phaseTapChgCaseCSV;
	SimpleCSV _switchCaseCSV;
	SimpleCSV _lineCaseCSV;
	SimpleCSV _windingCaseCSV;
	SimpleCSV _svcCaseCSV;
	
	SimpleCSV _steamTurbineCSV;
	
	
	//Not yet importing
//	SimpleCSV _voltageRelayCSV;
//	SimpleCSV _curRelayCSV;
//	SimpleCSV _freqRelayCSV;
//	SimpleCSV _loadAreaCSV;
//	SimpleCSV _modelParmsCSV;
//	SimpleCSV _primeMoverCSV;
//	SimpleCSV _relayOperateCSV;
	
	/** keep the bus references around for multiple indexes */
	IndexBuilder _busrb;
	int[] _busCaseIdx;
	/** gen unit to sync mach idx */
	int[] _smIdx;
	/** gen unit to gen unit case idx */
	int[] _guCaseIdx;
	/** gen unit to sync mach case idx */
	int[] _smCaseIdx;
	/** index capability curve points by synchronous machine */
	GroupMap _smCapIdx;
	/** load to load case index */
	int[] _ldCaseIdx;
	/** shuntcap to case */
	int[] _capCaseIdx;
	/** shunt reac to case */
	int[] _reacCaseIdx;
	/** area case */
	int[] _areaCaseIdx;
	/** svc case index */
	int[] _svcCaseIdx;
	/** line case index */
	int[] _lineCaseIdx;
	/** series capacitor index */
	int[] _serCapIdx;
	/** series reactor index */
	int[] _serReacIdx;
	/** switch case index */
	int[] _swCaseIdx;
	/** switch type index */
	int[] _swTypeIdx;
	/** Track power transformers */
	TxWindPart _xf;
	/** Track phase shifters */
	PsWindPart _ps;
	
	int[] _busAreaIndex;
	int[] _busOwnerIndex;
	int[] _busStationIndex;

	/** invented voltage levels */
	TFloatArrayList _vlkv;
	/** index to invented voltage level from bus */
	int[] _busvl;
	
	WeakReference<MVArLimits> _mvarLimits = new WeakReference<>(null);
	
	/** Create a group index to partition phase shifter and power transformer */

	enum DevSide {From, To};
	
	abstract class WndPart2
	{
		/** list of winding offsets for our "set" (either phase shifter or power transformer) */
		int[] _wndx;
		/** list of case offsets for winding set */
		int[] _wcasex;
		/** winding to transformer */
		int[] _wtx;
		SimpleCSV _tapcase;
		
		class Side
		{
			/** index from winding to tap */
			int[] tapx;
			/** winding to tap case */
			int[] casex;
			/** winding to tap node */
			int[] tapnodex;
			Side(int[] tapx, int[] casex, int[] tapnodex)
			{
				this.tapx = tapx;
				this.casex = casex;
				this.tapnodex = tapnodex;
			}
			
			public float[] getTap() {return calcTaps(this);}
		}
		
		Side _from, _to;
		
		protected abstract float[] calcTaps(Side s);
		public abstract float[] calcShift(); 
		public abstract boolean[] hasReg();
		
		protected WndPart2(int[] wndx, int[] wcx, int[] wnd2tx, GroupMap tapgrp, SimpleCSV tapcsv, SimpleCSV tapcase)
		{
			_wndx = wndx;
			int nw = wndx.length;
			/*
			 * track an index to the case data relative to our restricted winding list
			 */
			_wcasex = remapIndex(wndx, wcx);
			/*
			 * track from winding to our parent transformer 
			 */
			_wtx = remapIndex(wndx, wnd2tx);
			
			/*
			 * get winding indexes on either side, in our new restricted winding order
			 */
			
			/** taps that exist on the from-side of the winding, index to partitioned winding */
			int[] ftapx = new int[nw];
			/** taps that exist*/
			int[] ttapx = new int[nw];
			int[] ftcasex = new int[nw];
			int[] ttcasex = new int[nw];
			Arrays.fill(ftapx, -1);
			Arrays.fill(ttapx, -1);
			Arrays.fill(ftcasex, -1);
			Arrays.fill(ttcasex, -1);
			String[] tapnode = tapcsv.get("TapNode");
			String[] wndfromnd = _tfmrWindingCSV.get("Node1");
			int[] tapcasex = new IndexBuilder(tapcsv, "ID").index(tapcase, "ID");
//			int[] ftpaxInv = new int[nw], ttapxInv = new int[nw];
			
			for(int i=0; i < nw; ++i)
			{
				int wi = _wndx[i];
				for(int tapx : tapgrp.get(wi))
				{
					int[] side, tcase;
					if (tapnode[tapx].equals(wndfromnd[wi])) 
					{
						side = ftapx;
						tcase = ftcasex;
//						inv = ftpaxInv;
					}
					else
					{
						side = ttapx;
						tcase = ttcasex;
//						inv = ttapxInv;
					}
					side[i] = tapx;
					tcase[i] = tapcasex[tapx];
				}
			}
			_tapcase = tapcase;
			_from = new Side(ftapx, ftcasex, _busrb.revndx(tapnode, ftapx));
			_to = new Side(ttapx, ttcasex, _busrb.revndx(tapnode, ttapx));
		}
		int size() {return _wndx.length;}
		public int[] getWindingIndex() {return _wndx;}
		public int[] getTransformerIndex() {return _wtx;}
		public int[] getWindingCaseIndex() {return _wcasex;}

		public Side getSide(DevSide side)
		{
			return (side == DevSide.From) ? _from : _to;
		}
	}

	class PsWindPart extends WndPart2
	{

		protected PsWindPart(int[] wndx, int[] wcx, int[] wnd2tx,
				GroupMap tapgrp)
		{
			super(wndx, wcx, wnd2tx, tapgrp, _phaseTapChgCSV, _phaseTapChgCaseCSV);
		}

		@Override
		protected float[] calcTaps(Side s)
		{
			int nw = size();
			float[] rv = new float[nw];
			float[] neukv = getFloatData(s.tapx, _phaseTapChgCSV.get("NeutralKV"), 0f);
			for(int i=0; i < nw; ++i)
			{
				float nkv = neukv[i];
				rv[i] = (nkv == 0f) ? 1f : nkv / _vlkv.getQuick(_busvl[s.tapnodex[i]]);
			}
			
			return rv;
		}

		@Override
		public float[] calcShift()
		{
			float[] fixed = getFloatData(_wndx, _tfmrWindingCSV.get("PhaseShift"));
			String[] phcase = _phaseTapChgCaseCSV.get("PhaseShift");
			float[] fsh = getFloatData(_from.casex, phcase, 0f);
			float[] tsh = getFloatData(_to.casex, phcase, 0f);
			int nw = size();
			float[] rv = new float[nw];
			for(int i=0; i < nw; ++i)
				rv[i] = fixed[i] + fsh[i] - tsh[i];
			return rv;
		}

		public float[] getMinAng()
		{
			int nw = size();
			int[] ndx = _from.tapx;
			float[] rv = new float[nw];
			String[] mnang = _phaseTapChgCSV.get("MinAng");
			for (int i=0; i < nw; ++i)
				rv[i] = Float.parseFloat(mnang[ndx[i]]);
			return rv;
		}

		public float[] getMaxAng()
		{
			int nw = size();
			int[] ndx = _from.tapx;
			float[] rv = new float[nw];
			String[] mxang = _phaseTapChgCSV.get("MaxAng");
			for (int i=0; i < nw; ++i)
				rv[i] = Float.parseFloat(mxang[ndx[i]]);
			return rv;
		}

		public float[] getMaxMW()
		{
			int nw = size();
			int[] ndx = _from.tapx;
			float[] rv = new float[nw];
			float[] mxmw = getFloatData(ndx, _phaseTapChgCSV.get("MaxRegMW"), 0f);
			for (int i=0; i < nw; ++i)
				rv[i] = mxmw[i];
			return rv;
		}

		public float[] getMinMW()
		{
			int nw = size();
			float[] rv = new float[nw];
			float[] mnmw = getFloatData(_from.tapx, _phaseTapChgCSV.get("MinRegMW"), 0f);
			for (int i=0; i < nw; ++i)
				rv[i] = mnmw[i];
			return rv;
		}

		@Override
		public boolean[] hasReg()
		{
			int nw = size();
			int[] ndx = _from.tapx;
			boolean[] rv = new boolean[nw];
			String[] mnmw = _phaseTapChgCSV.get("IsRegulating");
			for (int i=0; i < nw; ++i)
				rv[i] = Boolean.parseBoolean(mnmw[ndx[i]].toLowerCase());
			return rv;
		}
	}
	
	class TxWindPart extends WndPart2
	{
		boolean[] _hasreg, _ltcenabl;
		int[] _regbus, _tapbus;
		float[] _minkv, _maxkv;
		float[] _fmintap, _fmaxtap, _tmintap, _tmaxtap;
		
		protected TxWindPart(int[] wndx, int[] wcx, int[] wnd2tx,
				GroupMap tapgrp)
		{
			super(wndx, wcx, wnd2tx, tapgrp, _ratioTapChgCSV, _ratioTapChgCaseCSV);
			setupRegulation();
		}

		@Override
		protected float[] calcTaps(Side s)
		{
			return getFloatData(s.casex, _ratioTapChgCaseCSV.get("Ratio"), 1f);
		}

		@Override
		public float[] calcShift()
		{
			return getFloatData(_wndx, _tfmrWindingCSV.get("PhaseShift"), 0f);
		}

		void setupRegulation()
		{
			int nw = size();
			_hasreg = new boolean[nw];
			_regbus = new int[nw];
			_tapbus = new int[nw];
			_ltcenabl = new boolean[nw];
			_minkv = new float[nw];
			_maxkv = new float[nw];
			String[] sisreg = _ratioTapChgCSV.get("IsRegulating");
			String[] sregnd = _ratioTapChgCSV.get("RegulatedNode");
			String[] stapnd = _ratioTapChgCSV.get("TapNode");
			String[] senabl = _ratioTapChgCaseCSV.get("LTCEnable");
			String[] sminkv = _ratioTapChgCSV.get("MinKV");
			String[] smaxkv = _ratioTapChgCSV.get("MaxKV");
			boolean[] fisreg, tisreg;
			if(sisreg.length == 0)
			{
				fisreg = new boolean[0];
				tisreg = fisreg;
			}
			else
			{
				fisreg = getBooleanData(_from.tapx, sisreg, false);
				tisreg = getBooleanData(_to.tapx, sisreg, false);
			}
			int[] freg;
			int[] treg;
			if(sregnd == null)
			{
				freg = _busrb.revndx(sregnd, _from.tapx);
				treg = _busrb.revndx(sregnd, _to.tapx);
			}
			else
			{
				freg = new int[0];
				treg = freg;
			}
			int[] ftn = _busrb.revndx(stapnd, _from.tapx);
			int[] ttn = _busrb.revndx(stapnd, _to.tapx);
			boolean[] fenabl = getBooleanData(_from.tapx, senabl, false);
			boolean[] tenabl = getBooleanData(_to.tapx, senabl, false);
			float[] fminkv = getFloatData(_from.tapx, sminkv, 0f);
			float[] tminkv = getFloatData(_to.tapx, sminkv, 0f);
			float[] fmaxkv = getFloatData(_from.tapx, smaxkv, 0f);
			float[] tmaxkv = getFloatData(_to.tapx, smaxkv, 0f);
			
			for(int i=0; i < size(); ++i)
			{
				if(fisreg.length > 0 && fisreg[i])
				{
					_hasreg[i] = true;
					_regbus[i] = freg[i];
					_tapbus[i] = ftn[i];
					_ltcenabl[i] = fenabl[i];
					_minkv[i] = fminkv[i];
					_maxkv[i] = fmaxkv[i];
				}
				else if (tisreg.length > 0 && tisreg[i])
				{
					_hasreg[i] = true;
					_regbus[i] = treg[i];
					_tapbus[i] = ttn[i];
					_ltcenabl[i] = tenabl[i];
					_minkv[i] = tminkv[i];
					_maxkv[i] = tmaxkv[i];
				}
				else
				{
					_hasreg[i] = false;
//					_regbus[i] = freg[i];
					_tapbus[i] = ftn[i];
//					_ltcenabl[i] = fenabl[i];
//					_minkv[i] = fminkv[i];
//					_maxkv[i] = fmaxkv[i];
				}
				
			}
		}
		
		public int[] getRegBus()
		{
			return _regbus;
		}

		@Override
		public boolean[] hasReg()
		{
			return _hasreg;
		}

		public int[] getTapBus()
		{
			return _tapbus;
		}

		public boolean[] isRegEnabl()
		{
			return _ltcenabl;
		}
		
		public float[] getMinKV()
		{
			return _minkv;
		}
		public float[] getMaxKV()
		{
			return _maxkv;
		}
		
		public float[] getFromMinTap()
		{
			if (_fmintap == null)
				_fmintap = getFloatData(_from.tapx,
				_ratioTapChgCSV.get("MinRatio"), 0.9f);
			return _fmintap;
		}
		
		public float[] getFromMaxTap()
		{
			if (_fmaxtap == null) 
				_fmaxtap = getFloatData(_from.tapx,
				_ratioTapChgCSV.get("MaxRatio"), 1.1f);
			return _fmaxtap;
		}
		
		public float[] getToMinTap()
		{
			if (_tmintap == null)
				_tmintap = getFloatData(_to.tapx,
				_ratioTapChgCSV.get("MinRatio"), 0.9f);
			return _tmintap;
		}
		
		public float[] getToMaxTap()
		{
			if (_tmaxtap == null)
				_tmaxtap = getFloatData(_to.tapx,
				_ratioTapChgCSV.get("MaxRatio"), 1.1f);
			return _tmaxtap;
		}

		public float[] getFromStep()
		{
			int nw = size();
			float[] rv = new float[nw];
			int[] pos = getIntData(_from.tapx,
				_ratioTapChgCSV.get("TapPositions"), 33);
			for(int i=0; i < nw; ++i) 
				rv[i] = (_fmaxtap[i] - _fmintap[i]) / ((float) (pos[i]-1));
			return rv;
		}
		public float[] getToStep()
		{
			int nw = size();
			float[] rv = new float[nw];
			int[] pos = getIntData(_from.tapx,
				_ratioTapChgCSV.get("TapPositions"), 33);
			for(int i=0; i < nw; ++i) 
				rv[i] = (_tmaxtap[i] - _tmintap[i]) / ((float) (pos[i]-1));
			return rv;
		}
	}
	
	public PFlowPsmModelBldr(String parms) throws PAModelException
	{
		QueryString q = new QueryString(parms);
		if(!q.containsKey("dir")) throw new PAModelException("Missing dir= in query string.");
		String basedb = q.get("dir")[0];
		
		_dir = new File(basedb);
		if(!_dir.exists())
		{
			_dir.mkdirs();
		}
	}
	
	public PFlowPsmModelBldr(File dir)
	{
		_dir = dir;
	}
	
	@Override
	protected void loadPrep() 
	{
		//Load prep does nothing, yet
		//This is where PD3 calls columnPrep
	}

	@Override
	protected BusListI loadBuses() throws PAModelException 
	{
		try 
		{
			if(_busCSV == null)
			{
				_busCSV = new SimpleCSV(new File(_dir, "Node.csv"));
				_busCaseCSV = new SimpleCSV(new File(_dir, "PsmCaseNode.csv"));
				_busrb = new IndexBuilder(_busCSV, "ID");
				_busCaseIdx = _busrb.index(_busCaseCSV, "ID");
			}
			return new BusListI(_m, _busCSV.getRowCount());
		} 
		catch (IOException e)
		{
			throw new PAModelException(e);
		}
	}

	@Override
	protected SwitchListI loadSwitches() throws PAModelException 
	{
		try 
		{
			_switchCSV = new SimpleCSV(new File(_dir, "Switch.csv"));
			_switchTypeCSV = new SimpleCSV(new File(_dir, "SwitchType.csv"));
			_switchCaseCSV = new SimpleCSV(new File(_dir, "PsmCaseSwitch.csv"));
			IndexBuilder rb = new IndexBuilder(_switchCSV, "ID");
			_swCaseIdx = rb.index(_switchCaseCSV, "ID");
			IndexBuilder rbt = new IndexBuilder(_switchTypeCSV, "ID");
			_swTypeIdx = rbt.revndx(_switchCSV.get("SwitchType"));
			return new SwitchListI(_m, _switchCSV.getRowCount());
		} 
		catch (IOException e) 
		{
			throw new PAModelException(e);
		}
	}

	@Override
	protected LineListI loadLines() throws PAModelException 
	{
		try
		{
			_lineCSV = new SimpleCSV(new File(_dir, "Line.csv"));
			_lineCaseCSV = new SimpleCSV(new File(_dir, "PsmCaseLine.csv"));
			IndexBuilder rb = new IndexBuilder(_lineCSV, "ID");
			_lineCaseIdx = rb.index(_lineCaseCSV, "ID");
			return new LineListI(_m, _lineCSV.getRowCount());
		}
		catch (IOException e) 
		{
			throw new PAModelException(e);
		}
	}

	@Override
	protected AreaListI loadAreas() throws PAModelException 
	{
		try
		{
			if (_busCSV == null) loadBuses();
			_areaCSV = new SimpleCSV(new File(_dir, "ControlArea.csv"));
			_areaCaseCSV = new SimpleCSV(new File(_dir, "PsmCaseControlArea.csv"));
			IndexBuilder rb = new IndexBuilder(_areaCSV, "ID");
			_areaCaseIdx = rb.index(_areaCaseCSV, "ID");
			_busAreaIndex = rb.revndx(_busCSV.get("ControlArea"));
			return new AreaListI(_m, _busAreaIndex, _areaCSV.getRowCount());
		}
		catch (IOException e) 
		{
			throw new PAModelException(e);
		}
	}

	@Override
	protected OwnerListI loadOwners() throws PAModelException 
	{
		try 
		{
			if (_busCSV == null) loadBuses();
			_orgCSV = new SimpleCSV(new File(_dir, "Organization.csv"));
			IndexBuilder rb = new IndexBuilder(_orgCSV, "ID");
			_busOwnerIndex = rb.revndx(_busCSV.get("Organization"));
			return new OwnerListI(_m, _busOwnerIndex, _orgCSV.getRowCount());
		} 
		catch (IOException e) 
		{
			throw new PAModelException(e);
		}
	}

	@Override
	protected StationListI loadStations() throws PAModelException 
	{
		try
		{
			if (_busCSV == null) loadBuses();
			_substationCSV = new SimpleCSV(new File(_dir, "Substation.csv"));
			IndexBuilder rb = new IndexBuilder(_substationCSV, "ID");
			_busStationIndex = rb.revndx(_busCSV.get("Substation"));
			return new StationListI(_m, _busStationIndex, _substationCSV.getRowCount());
		}
		catch (IOException e) 
		{
			throw new PAModelException(e);
		}
	}

	@Override
	protected VoltageLevelListI loadVoltageLevels() throws PAModelException 
	{
		float[] baskv = _busCSV.getFloats("NominalKV");
		int nb = _busCSV.getRowCount();
		TFloatIntHashMap m = new TFloatIntHashMap();
		_vlkv = new TFloatArrayList();
		for(int i=0; i < nb; ++i)
		{
			m.putIfAbsent(baskv[i], m.size());
			if (m.size() != _vlkv.size()) _vlkv.add(baskv[i]);
		}
		_busvl = new int[nb];
		Arrays.fill(_busvl, -1);
		for(int i=0; i < nb; ++i)
		{ 
			_busvl[i] = m.get(baskv[i]);
		}
		return new VoltageLevelListI(_m, _busvl, m.size());
	}

	@Override
	protected ElectricalIslandList loadIslands() throws PAModelException 
	{
		ElectricalIslandList islands = new ElectricalIslandListI(_m);	
		
		return islands;
	}

	@Override
	protected SVCListI loadSVCs() throws PAModelException 
	{
		try
		{
			_svcCSV = new SimpleCSV(new File(_dir, "SVC.csv"));
			_svcCaseCSV = new SimpleCSV(new File(_dir, "PsmCaseSVC.csv"));
			IndexBuilder rb = new IndexBuilder(_svcCSV, "ID");
			_svcCaseIdx = rb.index(_svcCaseCSV, "ID");
			return new SVCListI(_m, _svcCSV.getRowCount());
		}
		catch (IOException e) 
		{
			throw new PAModelException(e);
		}
	}

	@Override
	protected SwitchedShuntList loadSwitchedShunts() throws PAModelException 
	{
		// TODO Incomplete
		// Didn't see a csv in doc
		// PD3 builder returns an empty list
		
		return SwitchedShuntList.emptyList();
	}

	@Override
	protected TwoTermDCLineList loadTwoTermDCLines() throws PAModelException 
	{
		// TODO Incomplete
		// Didn't see a csv in doc
		// PD3 builder returns an empty list
		return TwoTermDCLineList.emptyList();
	}

	@Override
	protected ShuntCapListI loadShuntCapacitors() throws PAModelException 
	{
		try
		{
			_shuntCapCSV = new SimpleCSV(new File(_dir, "ShuntCapacitor.csv"));
			_shuntCapCaseCSV = new SimpleCSV(new File(_dir, "PsmCaseShuntCapacitor.csv"));
			IndexBuilder rb = new IndexBuilder(_shuntCapCSV, "ID");
			_capCaseIdx = rb.index(_shuntCapCaseCSV, "ID");
			return new ShuntCapListI(_m, _shuntCapCSV.getRowCount());
		}
		catch (IOException e) 
		{
			throw new PAModelException(e);
		}
	}

	@Override
	protected ShuntReacListI loadShuntReactors() throws PAModelException 
	{
		try
		{
			_shuntReacCSV = new SimpleCSV(new File(_dir, "ShuntReactor.csv"));
			_shuntReacCaseCSV = new SimpleCSV(new File(_dir, "PsmCaseShuntReactor.csv"));
			IndexBuilder rb = new IndexBuilder(_shuntReacCSV, "ID");
			_reacCaseIdx = rb.index(_shuntReacCaseCSV, "ID");
			return new ShuntReacListI(_m, _shuntReacCSV.getRowCount());
		}
		catch (IOException e) 
		{
			throw new PAModelException(e);
		}
	}

	@Override
	protected LoadListI loadLoads() throws PAModelException 
	{
		try
		{
			_loadCSV = new SimpleCSV(new File(_dir, "Load.csv"));
			_loadCaseCSV = new SimpleCSV(new File(_dir, "PsmCaseLoad.csv"));
			IndexBuilder rb = new IndexBuilder(_loadCSV, "ID");
			_ldCaseIdx = rb.index(_loadCaseCSV, "ID");
			return new LoadListI(_m, _loadCSV.getRowCount());
		}
		catch (IOException e) 
		{
			throw new PAModelException(e);
		}
	}
	
	

	@Override
	protected SteamTurbineList loadSteamTurbines() throws PAModelException
	{
		try
		{
			_steamTurbineCSV = new SimpleCSV(new File(_dir, "SteamTurbine.csv"));
			return new SteamTurbineListI(_m, _steamTurbineCSV.getRowCount());
		}
		catch (IOException e)
		{
			throw new PAModelException(e);
		}
	}



	/**
	 * build reference indexes. These create a list of offsets for a related
	 * object.
	 * 
	 * For example, a Bus requires information from both Node.csv, and
	 * PsmCaseNode.csv. These two sets of object are related by their "ID", a
	 * column present in both files. Since data is loaded in arrays, we can use
	 * features of lists, specifically indices. We then build a "lookup" array
	 * from an offset in Bus to an offset in PsmCaseNode
	 */
	class IndexBuilder
	{
		/** hash the link column (probably ID's) to list offset */
		TObjectIntMap<String> _m;
		/** source object */
		SimpleCSV _src;
		
		/**
		 * Create a reference builder
		 * 
		 * @param source
		 *            The source object. Entries in the source object are hashed
		 *            for subsequent reference builders.
		 * @param linkcol
		 *            The source column used to create linkages to related
		 *            objects
		 */
		public IndexBuilder(SimpleCSV source, String linkcol)
		{
			int nsrc = source.getRowCount();
			_m = new TObjectIntHashMap<>(nsrc, 0.5f, -1);
			String[] cdata = source.get(linkcol);
			for(int i=0; i < nsrc; ++i)
			{
				if (_m.put(cdata[i], i) != -1)
					System.err.format("Duplicate ID's found \"%s\"\n",cdata[i]);
			}
			_src = source;
		}
		
		public IndexBuilder(String[] id, int[] idx)
		{
			int nsrc = idx.length;
			_m = new TObjectIntHashMap<>(nsrc, 0.5f, -1);
			for(int i=0; i < nsrc; ++i)
			{
				if (_m.put(id[idx[i]], i) != -1)
					System.err.format("DuplicateID's found \"%s\"\n", id[idx[i]]);
			}
		}

		/** Index the source offsets to the target offsets */
		public int[] index(SimpleCSV targobj, String targfld)
		{
			int nsrc = _src.getRowCount();
			int[] rv = new int[nsrc];
			Arrays.fill(rv, -1);

			int nc = targobj.getRowCount();
			String[] cid = targobj.get(targfld);
			for(int i=0; i < nc; ++i)
			{
				int x = _m.get(cid[i]);
				if (x == -1)
				{
					System.err.format("ID \"%s\" specified in %s not found in %s\n",
						cid[i], targobj.getMeta(), _src.getMeta());
				}
				else
					rv[x] = i;
			}
			return rv;
		}
		
		public int[] revndx(String[] targid, int[] idx)
		{
			int ntarg = idx.length;
			int[] rv = new int[ntarg];
			Arrays.fill(rv, -1);
			for(int i=0; i < ntarg; ++i)
			{
				int x = idx[i];
				rv[i] = (x == -1) ? -1 : _m.get(targid[x]);
			}
			return rv;
		}
		
		public int[] revndx(String[] ids)
		{
			int ntarg = ids.length;
			int[] rv = new int[ntarg];
			Arrays.fill(rv, -1);
			for(int i=0; i < ntarg; ++i)
				rv[i] = _m.get(ids[i]);
			return rv;
		}
		
	}
	
	@Override
	protected GenListI loadGens() throws PAModelException 
	{
		try
		{
			if (_genCSV == null)
			{
				_genCSV = new SimpleCSV(new File(_dir, "GeneratingUnit.csv"));
				_genCaseCSV = new SimpleCSV(new File(_dir,
						"PsmCaseGeneratingUnit.csv"));
				_synchMachineCSV = new SimpleCSV(new File(_dir,
						"SynchronousMachine.csv"));
				_synchCaseCSV = new SimpleCSV(new File(_dir,
						"PsmCaseSynchronousMachine.csv"));
				_reacCapCurveCSV = new SimpleCSV(new File(_dir,
						"ReactiveCapabilityCurve.csv"));
				
				IndexBuilder rb = new IndexBuilder(_genCSV, "ID");
				_smIdx = rb.index(_synchMachineCSV, "GeneratingUnit");
				_guCaseIdx = rb.index(_genCaseCSV, "ID");
				
				IndexBuilder rbsm = new IndexBuilder(_synchMachineCSV, "ID")
				{
					{
						int n = _reacCapCurveCSV.getRowCount();
						int[] cx = new int[n];
						String[] smid = _reacCapCurveCSV.get("SynchronousMachine");
						for(int i=0; i < n; ++i)
							cx[i] = _m.get(smid[i]);
						_smCapIdx = new GroupMap(cx, _synchCaseCSV.getRowCount());
					}
				};
				
				int[] tx = rbsm.index(_synchCaseCSV, "ID");
				int n = tx.length;
				_smCaseIdx = new int[n];
				for(int i=0; i < n; ++i)
					_smCaseIdx[i] = tx[_smIdx[i]];
				
			}
			return new GenListI(_m, _genCSV.getRowCount());

		}
		catch (IOException e) 
		{
			throw new PAModelException(e);
		}
	}

	@Override
	protected SeriesCapListI loadSeriesCapacitors() throws PAModelException 
	{
		try
		{
			_seriesCapCSV = new SimpleCSV(new File(_dir, "SeriesCapacitor.csv"));
			_seriesCapCaseCSV = new SimpleCSV(new File(_dir, "PsmCaseSeriesCapacitor.csv"));
			IndexBuilder rb = new IndexBuilder(_seriesCapCSV, "ID");
			_serCapIdx = rb.index(_seriesCapCaseCSV, "ID");
			return new SeriesCapListI(_m, _seriesCapCSV.getRowCount());
		}
		catch (IOException e) 
		{
			throw new PAModelException(e);
		}
	}

	@Override
	protected SeriesReacListI loadSeriesReactors() throws PAModelException 
	{
		try
		{
			_seriesReacCSV = new SimpleCSV(new File(_dir, "SeriesReactor.csv"));
			_seriesReacCaseCSV = new SimpleCSV(new File(_dir, "PsmCaseSeriesReactor.csv"));
			IndexBuilder rb = new IndexBuilder(_seriesReacCSV, "ID");
			_serReacIdx = rb.index(_seriesReacCaseCSV, "ID");
			return new SeriesReacListI(_m, _seriesReacCSV.getRowCount());
		}
		catch (IOException e) 
		{
			throw new PAModelException(e);
		}
	}

	@Override
	protected PhaseShifterListI loadPhaseShifters() throws PAModelException 
	{
		try
		{
			testLoadTransformerCSVs();
			return new PhaseShifterListI(_m, _ps.size());
		}
		catch (IOException e)
		{
			throw new PAModelException(e);
		}
	}

	@Override
	protected TransformerListI loadTransformers() throws PAModelException 
	{
		try
		{
			testLoadTransformerCSVs();

			return new TransformerListI(_m, _xf.size());
		}
		catch (IOException e) 
		{
			throw new PAModelException(e);
		}
	}
	
	void testLoadTransformerCSVs() throws IOException
	{
		if(_transformerCSV == null)
		{
			_transformerCSV = new SimpleCSV(new File(_dir, "Transformer.csv"));
			_tfmrWindingCSV = new SimpleCSV(new File(_dir, "TransformerWinding.csv"));
			_ratioTapChgCSV = new SimpleCSV(new File(_dir, "RatioTapChanger.csv"));
			_ratioTapChgCaseCSV = new SimpleCSV(new File(_dir, "PsmCaseRatioTapChanger.csv"));
			_phaseTapChgCSV = new SimpleCSV(new File(_dir, "PhaseTapChanger.csv"));
			_windingCaseCSV = new SimpleCSV(new File(_dir, "PsmCaseTransformerWinding.csv"));
			_phaseTapChgCaseCSV = new SimpleCSV(new File(_dir, "PsmCasePhaseTapChanger.csv"));
			
			/* map transformers to windings */
			IndexBuilder rbtx = new IndexBuilder(_transformerCSV, "ID");
			int[] wndTxIdx = rbtx.revndx(_tfmrWindingCSV.get("Transformer"));
			
			/** winding index builder */
			IndexBuilder rbtw = new IndexBuilder(_tfmrWindingCSV, "ID");
			int[] wcx = rbtw.index(_windingCaseCSV, "ID");
			
			/* 
			 * partition into phase shifter and transformer winding.
			 * The first step generates an array (in order of winding) to the offset within
			 * the phase tap changer, or -1 if no mapping is possible (because it's not a phase shifter)  
			 */

			int nw = _tfmrWindingCSV.getRowCount();

			/*
			 * make a one-many mapping from winding to tap changer 
			 */
			GroupMap w2pstap = new GroupMap(rbtw.revndx(_phaseTapChgCSV.get("TransformerWinding")), nw);
			GroupMap w2txtap = new GroupMap(rbtw.revndx(_ratioTapChgCSV.get("TransformerWinding")), nw);

			
			/*
			 * Make a GroupMap, but first it needs a mapping array just
			 * identifying group membership. So, we just say that any -1's are
			 * really transformers (group 0), and > -1 is a phase shifter (group
			 * 1).
			 */
			int[] wmap = new int[nw];
			for(int i=0; i < nw; ++i)
			{
				if (w2pstap.get(i).length > 0)
					wmap[i] = 1;
			}
			GroupMap type = new GroupMap(wmap, 2);
			_ps = new PsWindPart(type.get(1), wcx, wndTxIdx, w2pstap);
			_xf = new TxWindPart(type.get(0), wcx, wndTxIdx, w2txtap);
			
		}
	}
	
	public int[] remapIndex(int[] inner, int[] outer)
	{
		int n = inner.length;
		int[] rv = new int[n];
		for(int i=0; i < n; ++i)
			rv[i] = outer[inner[i]];
		return rv;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected <R> R load(ListMetaType ltype, ColumnMeta ctype, int[] keys)
			throws PAModelException 
	{
		switch(ctype)
		{
		//Bus
		case BusID:
			return (R)_busCSV.get("ID");
		case BusNAME:
			return (R)_busCSV.get("Name");
		case BusVM: return (R) getBusVM();
		case BusVA:	//Returns float
			return (R) ((useFlatVoltage()) ? new float[_busCaseIdx.length] :
				getFloatData(_busCaseIdx, _busCaseCSV.get("Ang")));
		case BusFREQSRCPRI:
			return (R) _busCSV.getInts("FrequencySourcePriority");
		case BusAREA:
			if (_areaCSV == null) loadAreas();
			return (R) _busAreaIndex;
		case BusOWNER:
			if(_orgCSV == null) loadOwners();
			return (R) _busOwnerIndex;
		case BusSTATION:
			if (_substationCSV == null) loadStations();
			return (R) _busStationIndex;
		case BusVLEV:
			if (_busvl == null) loadVoltageLevels();
			return (R) _busvl;
		//Gen
		case GenID:
			return (R) _genCSV.get("ID");
		case GenNAME:
			return (R) _genCSV.get("Name");
		case GenBUS:
			return (R) _busrb.revndx(_synchMachineCSV.get("Node"), _smIdx);
		case GenP:
			return (R) getFloatData(_guCaseIdx, _genCaseCSV.get("MW"));
		case GenQ:
			return (R) getFloatData(_smCaseIdx, _synchCaseCSV.get("MVAr"));
		case GenINSVC:
			return (R) resolveOOS(_smCaseIdx, _synchCaseCSV.get("InService"));
		case GenTYPE:
			String[] genList = _genCSV.get("GeneratingUnitType");
			Type[] genType = new Type[_genCSV.getRowCount()];
			Arrays.fill(genType, Type.Unknown);
			for(int i = 0; i < genList.length; i++)
				genType[i] = Type.valueOf(genList[i]);
			return (R) genType;
		case GenMODE:
			return (R) getGenMode();
		case GenOPMINP:
			return (R) _genCSV.getFloats("MinOperatingMW");
		case GenOPMAXP:
			return (R) _genCSV.getFloats("MaxOperatingMW");
		case GenMINQ:
			return (R) getMVArLimits().getMin();
		case GenMAXQ:
			return (R) getMVArLimits().getMax();
		case GenPS:
			return (R) getFloatData(_guCaseIdx, _genCaseCSV.get("MW"));
		case GenQS:
			return (R) getFloatData(_smCaseIdx, _synchCaseCSV.get("MVArSetpoint"),0f);
		case GenAVR:
			return (R) getGenAVRMode();
		case GenVS:
			return (R) getFloatData(_smCaseIdx, _synchCaseCSV.get("KVSetPoint"));
		case GenREGBUS:
			return (R) getGenRegBus();

		// Steam Turbine 
		case SteamTurbineID:
			return (R) _steamTurbineCSV.get("ID");
		case SteamTurbineNAME:
			return (R) _steamTurbineCSV.get("Name");
		case SteamTurbineSteamSupply:
		{
			int n = _steamTurbineCSV.getRowCount();
			String[] stList = _steamTurbineCSV.get("SteamSupply");
			SteamTurbine.SteamSupply[] stType = new SteamTurbine.SteamSupply[n];
			Arrays.fill(stType, SteamTurbine.SteamSupply.Unknown);
			for(int i = 0; i < n; i++)
				stType[i] = SteamTurbine.SteamSupply.valueOf(stList[i]);
			return (R) stType;
		}	
			
		//Load
		case LoadID:
			return	(R) _loadCSV.get("ID");
		case LoadNAME:
			return	(R) _loadCSV.get("Name");
		case LoadBUS:
			return (R) _busrb.revndx(_loadCSV.get("Node"));
		case LoadP:
			return (R) invertValues(getFloatData(_ldCaseIdx, _loadCaseCSV.get("MW")));
		case LoadQ:
			return (R) invertValues(getFloatData(_ldCaseIdx, _loadCaseCSV.get("MVAr")));
		case LoadINSVC:
			return (R) resolveOOS(_ldCaseIdx, _loadCaseCSV.get("InService"));
		case LoadPMAX:
			return (R) _m.getLoads().getP();
		case LoadQMAX:
			return (R) _m.getLoads().getQ();
		//Shunt Capacitor
		case ShcapID:
			return (R) _shuntCapCSV.get("ID");
		case ShcapNAME:
			return (R) _shuntCapCSV.get("Name");
		case ShcapBUS: 
			return (R) _busrb.revndx(_shuntCapCSV.get("Node"));
		case ShcapP:
			return (R) getFloatData(_capCaseIdx, _shuntCapCaseCSV.get("MW"), 0f);
		case ShcapQ:
			return (R) getFloatData(_capCaseIdx, _shuntCapCaseCSV.get("MVAr"), 0f);
		case ShcapINSVC:
			return (R) resolveOOS(_capCaseIdx, _shuntCapCaseCSV.get("InService"));
		case ShcapB:
			return (R) _shuntCapCSV.getFloats("MVAr");
		//Shunt Reactor
		case ShreacID:
			return (R) _shuntReacCSV.get("ID");
		case ShreacNAME:
			return (R) _shuntReacCSV.get("Name");
		case ShreacBUS:
			return (R) _busrb.revndx(_shuntReacCSV.get("Node"));
		case ShreacP:
			return (R) getFloatData(_reacCaseIdx, _shuntReacCaseCSV.get("MW"), 0f);
		case ShreacQ:
			return (R) getFloatData(_reacCaseIdx, _shuntReacCaseCSV.get("MVAr"), 0f);
		case ShreacINSVC:
			return (R) resolveOOS(_reacCaseIdx, _shuntReacCaseCSV.get("InService"));
		case ShreacB:
			return (R) _shuntReacCSV.getFloats("MVAr");
		case SvcID:
			return (R) _svcCSV.get("ID");
		case SvcNAME:
			return (R) _svcCSV.get("Name");
		case SvcBUS:
			return (R) _busrb.revndx(_svcCSV.get("Node"));
		case SvcP:
			return (R) getFloatData(_svcCaseIdx, _svcCaseCSV.get("MW"));
		case SvcQ:
			return (R) getFloatData(_svcCaseIdx, _svcCaseCSV.get("MVAr"));
		case SvcINSVC:
			return (R) resolveOOS(_svcCaseIdx, _svcCaseCSV.get("InService"));
		case SvcQS:
			return (R) getFloatData(_svcCaseIdx, _svcCaseCSV.get("MVArSetPoint"));
		case SvcQMIN:
			return (R) _svcCSV.getFloats("MinMVAr");
		case SvcQMAX:
			return (R) _svcCSV.getFloats("MaxMVAr");
		case SvcAVR:
			return (R) getSvcAVR();
		case SvcVS:
			return (R) getFloatData(_svcCaseIdx, _svcCaseCSV.get("VoltageSetpoint"));
		case SvcSLOPE:
			return (R) _svcCSV.getFloats("Slope");
		case SvcREGBUS:
			return (R) _busrb.revndx(_svcCSV.get("Node"));
		case SvcOMODE: return null;
		case SwshID:
		case SwshNAME:
		case SwshP:
		case SwshQ:
		case SwshOOS:
		case SwshB:
			return null;
		//Area
		case AreaID:
			return (R) _areaCSV.get("ID");
		case AreaNAME:
			return (R) _areaCSV.get("Name");
		//Owner
		case OwnerID:
			return (R) _orgCSV.get("ID");
		case OwnerNAME:
			return (R) _orgCSV.get("Name");
		//Island
		case IslandID:
		case IslandNAME:
			String[] ids = new String[_m.getElectricalIslands().size()];
			for(int i = 0; i < ids.length; ++i) { ids[i] = ""+i; }
			return (R) ids;
		case IslandFREQ:
			return (R) new float[_m.getElectricalIslands().size()];
		case IslandEGZSTATE:
			return null;
		//Station
		case StationID:
			return (R) _substationCSV.get("ID");
		case StationNAME:
			return (R) _substationCSV.get("Name");
		//Voltage Level
		case VlevID:
			return (R) returnAsString(_vlkv);
		case VlevNAME:
			return (R) returnAsString(_vlkv);
		case VlevBASKV:
			return (R) _vlkv.toArray();
		//Line
		case LineID:
			return (R) _lineCSV.get("ID");
		case LineNAME:
			return (R) _lineCSV.get("Name");
		case LineBUSFROM:
			return (R) _busrb.revndx(_lineCSV.get("Node1"));
		case LineBUSTO:
			return (R) _busrb.revndx(_lineCSV.get("Node2"));
		case LineINSVC:
			return (R) resolveOOS(_lineCaseIdx, _lineCaseCSV.get("InService"));
		case LinePFROM:
			return (R) getFloatData(_lineCaseIdx, _lineCaseCSV.get("FromMW"), 0f);
		case LineQFROM:
			return (R) getFloatData(_lineCaseIdx, _lineCaseCSV.get("FromMVAr"), 0f);
		case LinePTO:
			return (R) getFloatData(_lineCaseIdx, _lineCaseCSV.get("ToMW"), 0f);
		case LineQTO:
			return (R) getFloatData(_lineCaseIdx, _lineCaseCSV.get("ToMVAr"), 0f);
		case LineR:
			return (R) _lineCSV.getFloats("R");
		case LineX:
			return (R) _lineCSV.getFloats("X");
		case LineBFROM:
		case LineBTO:
			return (R) getLineB();
		case LineRATLT:
			return (R) _lineCSV.getFloats("NormalOperatingLimit");
		//Series Capacitor
		case SercapID:
			return (R) _seriesCapCSV.get("ID");
		case SercapNAME:
			return (R) _seriesCapCSV.get("Name");
		case SercapBUSFROM:
			return (R) _busrb.revndx(_seriesCapCSV.get("Node1"));
		case SercapBUSTO:
			return (R) _busrb.revndx(_seriesCapCSV.get("Node2"));
		case SercapINSVC:
			return (R) resolveOOS(_serCapIdx, _seriesCapCSV.get("InService"));
		case SercapPFROM:
			return (R) getFloatData(_serCapIdx, _seriesCapCaseCSV.get("FromMW"));
		case SercapPTO:
			return (R) getFloatData(_serCapIdx, _seriesCapCaseCSV.get("ToMW"));
		case SercapQFROM:
			return (R) getFloatData(_serCapIdx, _seriesCapCaseCSV.get("FromMVAr"));
		case SercapQTO:
			return (R) getFloatData(_serCapIdx, _seriesCapCaseCSV.get("ToMVAr"));
		case SercapR:
			return (R) _seriesCapCSV.getFloats("R");
		case SercapX:
			return (R) _seriesCapCSV.getFloats("X");
		case SercapRATLT:
			return (R) _seriesCapCSV.getFloats("NormalOperatingLimit");
		//Series Reactor
		case SerreacID:
			return (R) _seriesReacCSV.get("ID");
		case SerreacNAME:
			return (R) _seriesReacCSV.get("Name");
		case SerreacBUSFROM:
			return (R) _busrb.revndx(_seriesReacCSV.get("Node1"));
		case SerreacBUSTO:
			return (R) _busrb.revndx(_seriesReacCSV.get("Node2"));
		case SerreacINSVC:
			return (R) resolveOOS(_serReacIdx, _seriesReacCSV.get("InService"));
		case SerreacQTO:
			return (R) getFloatData(_serReacIdx, _seriesReacCaseCSV.get("ToMVAr"));
		case SerreacQFROM:
			return (R) getFloatData(_serReacIdx, _seriesReacCaseCSV.get("FromMVAr"));
		case SerreacPTO:
			return (R) getFloatData(_serReacIdx, _seriesReacCaseCSV.get("ToMW"));
		case SerreacPFROM:
			return (R) getFloatData(_serReacIdx, _seriesReacCaseCSV.get("FromMW"));
		case SerreacR:
			return (R) _seriesReacCSV.getFloats("R");
		case SerreacX:
			return (R) _seriesReacCSV.getFloats("X");
		case SerreacRATLT:
			return (R) _seriesReacCSV.getFloats("NormalOperatingLimit");
		//Phase Shifter
		case PhashID:
			return (R) getStringData(_ps.getTransformerIndex(), _transformerCSV.get("ID"));
		case PhashNAME:
			return (R) getStringData(_ps.getTransformerIndex(), _transformerCSV.get("Name"));
		case PhashBUSFROM:
			return (R) _busrb.revndx(getStringData(_ps.getWindingIndex(), _tfmrWindingCSV.get("Node1")));
		case PhashBUSTO:
			return (R) _busrb.revndx(getStringData(_ps.getWindingIndex(), _tfmrWindingCSV.get("Node2")));
		case PhashINSVC:
			return (R) resolveOOS(_ps.getWindingCaseIndex(), _windingCaseCSV.get("InService"));
		case PhashPFROM:
			return (R) getFloatData(_ps.getWindingCaseIndex(), _windingCaseCSV.get("FromMW"), 0f);
		case PhashQFROM:
			return (R) getFloatData(_ps.getWindingCaseIndex(), _windingCaseCSV.get("FromMVAr"), 0f);
		case PhashPTO:
			return (R) getFloatData(_ps.getWindingCaseIndex(), _windingCaseCSV.get("ToMW"), 0f);
		case PhashQTO:
			return (R) getFloatData(_ps.getWindingCaseIndex(), _windingCaseCSV.get("ToMVAr"), 0f);
		case PhashR:
			return (R) getFloatData(_ps.getWindingIndex(), _tfmrWindingCSV.get("R"));
		case PhashX:
			return (R) getFloatData(_ps.getWindingIndex(), _tfmrWindingCSV.get("X"));
		case PhashGMAG:
			return (R) getFloatData(_ps.getWindingIndex(), _tfmrWindingCSV.get("Gmag"));
		case PhashBMAG:
			return (R) getFloatData(_ps.getWindingIndex(), _tfmrWindingCSV.get("Bmag"));
		case PhashANG:
			return (R) _ps.calcShift();
		case PhashTAPFROM:
			return (R) _ps.getSide(DevSide.From).getTap();
		case PhashTAPTO:
			return (R) _ps.getSide(DevSide.To).getTap();
		case PhashCTRLMODE:
			return (R) getPhaseCtrlMode();
		case PhashRATLT:
			return (R) getFloatData(_ps.getWindingIndex(), _tfmrWindingCSV.get("NormalOperatingLimit"));
		case PhashHASREG: return (R) _ps.hasReg();
		case PhashMXANG: return (R) _ps.getMaxAng();
		case PhashMNANG: return (R) _ps.getMinAng();
		case PhashMXMW: return (R) _ps.getMaxMW();
		case PhashMNMW: return (R) _ps.getMinMW();
		//Transformer
		case TfmrID:
			return (R) getStringData(_xf.getTransformerIndex(), _transformerCSV.get("ID"));
		case TfmrNAME:
			return (R) getStringData(_xf.getTransformerIndex(), _transformerCSV.get("Name"));
		case TfmrBUSFROM:
			return (R) _busrb.revndx(getStringData(_xf.getWindingIndex(), _tfmrWindingCSV.get("Node1")));
		case TfmrBUSTO:
			return (R) _busrb.revndx(getStringData(_xf.getWindingIndex(), _tfmrWindingCSV.get("Node2")));
		case TfmrINSVC:
			return (R) resolveOOS(_xf.getWindingCaseIndex(), _windingCaseCSV.get("InService"));
		case TfmrPFROM:
			return (R) getFloatData(_xf.getWindingCaseIndex(), _windingCaseCSV.get("FromMW"), 0f);
		case TfmrQFROM:
			return (R) getFloatData(_xf.getWindingCaseIndex(), _windingCaseCSV.get("FromMVAr"), 0f);
		case TfmrPTO:
			return (R) getFloatData(_xf.getWindingCaseIndex(), _windingCaseCSV.get("ToMW"), 0f);
		case TfmrQTO:
			return (R) getFloatData(_xf.getWindingCaseIndex(), _windingCaseCSV.get("ToMVAr"), 0f);
		case TfmrR:
			return (R) getFloatData(_xf.getWindingIndex(), _tfmrWindingCSV.get("R"));
		case TfmrX:
			return (R) getFloatData(_xf.getWindingIndex(), _tfmrWindingCSV.get("X"));
		case TfmrGMAG:
			return (R) getFloatData(_xf.getWindingIndex(), _tfmrWindingCSV.get("Gmag"), 0f);
		case TfmrBMAG:
			return (R) getFloatData(_xf.getWindingIndex(), _tfmrWindingCSV.get("Bmag"), 0f);
		case TfmrANG:
			return (R)  _xf.calcShift();
		case TfmrTAPFROM:
			return (R) _xf.getSide(DevSide.From).getTap();
		case TfmrTAPTO:
			return (R) _xf.getSide(DevSide.To).getTap();
		case TfmrRATLT:
			return (R) getFloatData(_xf.getWindingIndex(), _tfmrWindingCSV.get("NormalOperatingLimit"));
		case TfmrREGBUS:
			return (R) _xf.getRegBus();
		case TfmrHASREG: return (R) _xf.hasReg();
		case TfmrTAPBUS: return (R) _xf.getTapBus();
		case TfmrREGENAB: return (R) _xf.isRegEnabl();
		case TfmrMINREGKV: return (R) _xf.getMinKV();
		case TfmrMAXREGKV: return (R) _xf.getMaxKV();
		case TfmrMNTPFROM: return (R) _xf.getFromMinTap();
		case TfmrMXTPFROM: return (R) _xf.getFromMaxTap();
		case TfmrMNTPTO: return (R) _xf.getToMinTap();
		case TfmrMXTPTO: return (R) _xf.getToMaxTap();
		case TfmrSTEPFROM: return (R) _xf.getFromStep();
		case TfmrSTEPTO: return (R) _xf.getToStep();
		case SwID:
			return (R) _switchCSV.get("ID");
		case SwNAME:
			return (R) _switchCSV.get("Name");
		case SwBUSFROM:
			return (R) _busrb.revndx(_switchCSV.get("Node1"));
		case SwBUSTO:
			return (R) _busrb.revndx(_switchCSV.get("Node2"));
		case SwINSVC:
			return (R) resolveOOS(_swCaseIdx, _switchCaseCSV.get("InService"));
		case SwPFROM: 
			return (R) getFloatData(_swCaseIdx, _switchCaseCSV.get("FromMW"));
		case SwQFROM:
			return (R) getFloatData(_swCaseIdx, _switchCaseCSV.get("FromMVAr"));
		case SwPTO:
			return (R) getFloatData(_swCaseIdx, _switchCaseCSV.get("ToMW"));
		case SwQTO:
			return (R) getFloatData(_swCaseIdx, _switchCaseCSV.get("ToMVAr"));
		case SwSTATE:
			return (R) Switch.State.fromString(getStringData(_swCaseIdx,_switchCaseCSV.get("SwitchPosition")));
		case SwOPLD:
			return (R) operableUnderLoad();
		case SwENAB:
			boolean[] switches = new boolean[_switchCSV.getRowCount()];
			Arrays.fill(switches, true);
			return (R) switches;
		//T2dc
		case T2dcID:
		case T2dcNAME:
		case T2dcBUSFROM:
		case T2dcBUSTO:
		case T2dcINSVC:
		case T2dcPFROM:
		case T2dcQFROM:
		case T2dcPTO:
		case T2dcQTO:
			return null;
		default:
			return null;
		}
	}
	
	private float[] getBusVM()
	{
		float[] rv;
		if (useFlatVoltage())
		{
			int n = _busCaseIdx.length;
			rv = new float[n];
			for(int i=0; i < n; ++i)
				rv[i] = _vlkv.get(_busvl[i]);
		}
		else
			rv = getFloatData(_busCaseIdx, _busCaseCSV.get("Mag"));
		return rv;
	}
	
	class MVArLimits
	{
		float[] _min, _max;
		MVArLimits()
		{
			int n = _smIdx.length;
			_min = new float[n];
			_max = new float[n];
			String[] sdn = _reacCapCurveCSV.get("MinMVAr");
			String[] sdx = _reacCapCurveCSV.get("MaxMVAr");
			for (int i = 0; i < n; ++i)
			{
				float min = Float.POSITIVE_INFINITY;
				float max = Float.NEGATIVE_INFINITY;
				for (int j : _smCapIdx.get(i))
				{
					float nv = Float.parseFloat(sdn[j]);
					if (nv < min) min = nv;
					float xv = Float.parseFloat(sdx[j]);
					if (xv > max) max = xv;
				}
				_min[i] = (min == Float.POSITIVE_INFINITY) ? 0f : min;
				_max[i]  = (max == Float.NEGATIVE_INFINITY) ? 0f : max;
			}
		}
		public float[] getMin()
		{
			return _min;
		}
		public float[] getMax() {return _max;}
	}	
	
	MVArLimits getMVArLimits()
	{
		MVArLimits rv = _mvarLimits.get();
		if (rv == null)
		{
			rv = new MVArLimits();
			_mvarLimits = new WeakReference<>(rv);
		}
		return rv;
	}
	
	private String[] returnAsString(TFloatList fs)
	{
		int n = fs.size();
		String[] asString = new String[n];
		for(int i=0; i < n; ++i)
			asString[i] = String.valueOf(fs.get(i));
		return asString;
	}
	
	
	boolean[] resolveOOS(int[] idx, String[] data)
	{
		if(data.length == 0)
		{
			boolean[] rv = new boolean[idx.length];
			Arrays.fill(rv, true);
			return rv;
		}
		else
		{
			return getBooleanData(idx, data); 
		}
			 
	}
	
	private float[] invertValues(float[] origData)
	{
		int size = origData.length;
		float[] data = new float[size];
		
		for(int i = 0; i < size; ++i)
		{
			data[i] = origData[i] * -1;
		}
		
		return data;
	}
	
	boolean[] getBooleanData(int[] idx, String[] data)
	{
		if (data.length == 0) return new boolean[0];
		int n = idx.length;
		boolean[] rv = new boolean[n];
		for(int i=0; i < n; ++i)
			rv[i] = Boolean.parseBoolean(data[idx[i]]);
		return rv;
	}
	
	boolean[] getBooleanData(int[] idx, String[] data, boolean deft)
	{
		if (data.length == 0) return new boolean[0];
		int n = idx.length;
		boolean[] rv = new boolean[n];
		for(int i=0; i < n; ++i)
		{
			int x = idx[i];
			rv[i] = (x == -1) ? deft: Boolean.parseBoolean(data[x]);
		}
		return rv;
	}
	
//	private void buildVlev() throws PAModelException
//	{
//		_vlevMap = new TFloatIntHashMap();
//		TIntFloatMap tempMap = new TIntFloatHashMap();
//		
//		float[] kv = _busCSV.getFloats("NominalKV");
//		int offset = 0;
//		
//		for(int i = 0; i < kv.length; ++i)
//		{
//			//Check to see if the voltage level exists in the map
//			if(!tempMap.containsValue(kv[i]))
//			{
//				//New level found, add it to the map
//				tempMap.put(offset, kv[i]);
//				offset++;
//			}
//		}
//		
//		//Now that we know how many voltage levels there are we can create proper maps & arrays.
//		_vlevFloat = new float[offset];
//		for(int i = 0; i < offset; ++i)
//		{
//			_vlevFloat[i] = tempMap.get(i);
//			_vlevMap.put(tempMap.get(i), i);
//		}
//	}
	
//	private void buildOwnerMap() throws PAModelException
//	{
//		if(_orgCSV == null) loadOwners();
//		String[] ownerIDs = _orgCSV.hasCol("ID") ? _orgCSV.get("ID") : new String[0];
//		_ownerMap = new TObjectIntHashMap<>(ownerIDs.length);
//		
//		for(int i = 0; i < ownerIDs.length; ++i)
//		{
//			_ownerMap.put(ownerIDs[i], i);
//		}
//			
//	}
//	
//	private void buildAreaMap() throws PAModelException
//	{
//		if(_areaCSV == null)loadAreas();
//		String[] areaIDs = _areaCSV.hasCol("ID") ? _areaCSV.get("ID") : new String[0];
//		_areaMap = new TObjectIntHashMap<>(areaIDs.length);
//		
//		for(int i = 0; i < areaIDs.length; ++i)
//		{
//			_areaMap.put(areaIDs[i], i);
//		}	
//	}
//	
//	private void buildBusIndexes() throws PAModelException
//	{
//		if(_areaMap == null) buildAreaMap();
//		if(_ownerMap == null) buildOwnerMap();
//		if(_stationOffsetMap == null) buildSubstationMap();
//		if(_busCSV == null) loadBuses();
//		int nbus = _busCSV.getRowCount();
//		
//		_busOwnerIndex = new int[nbus];
//		
//		if(_busCSV.hasCol("ControlArea"))
//		{
//			String[] busAreaID = _busCSV.get("ControlArea");
//			_busAreaIndex = new int[nbus];
//			for (int i = 0; i < nbus; ++i)
//				_busAreaIndex[i] = _areaMap.get(busAreaID[i]);
//		}
//		
//		if(_busCSV.hasCol("Organization"))
//		{
//			String[] busOrgID = _busCSV.get("Organization");
//			_busOwnerIndex = new int[nbus];
//			for (int i = 0; i < nbus; ++i)
//				_busOwnerIndex[i] = _ownerMap.get(busOrgID[i]);
//		}
//	}
//	
//	private void buildBusStationIndex() throws PAModelException
//	{
//		if(_stationOffsetMap == null) buildSubstationMap();
//		if(_busCSV == null) loadBuses();
//		String[] substationIDs = _busCSV.hasCol("Substation") ? _busCSV.get("Substation") : new String[0];
//		_busStationIndex = new int[substationIDs.length];
//		
//		for(int i = 0; i < substationIDs.length; ++i)
//		{
//			_busStationIndex[i] = _stationOffsetMap.get(substationIDs[i]);
//		}
//	}
	
//	private int[] getBusVlev() throws PAModelException
//	{
//		if(_vlevMap == null) buildVlev();
//		
//		float[] kv = _busCSV.getFloats("NominalKV");
//		int[] busVlev = new int[kv.length];
//		
//		for(int i = 0; i < busVlev.length; ++i)
//		{
//			busVlev[i] = _vlevMap.get(kv[i]);
//		}
//		
//		return busVlev;
//	}

	boolean[] operableUnderLoad()
	{
		boolean[] op = getBooleanData(_swTypeIdx, _switchTypeCSV.get("OpenUnderLoad"));
		boolean[] close = getBooleanData(_swTypeIdx, _switchTypeCSV.get("CloseUnderLoad"));
		int n = op.length;
		for(int i=0; i < n; ++i)
			op[i] |= close[i];
		return op;
		
	}
	
//	private boolean[] operableUnderLoad()
//	{	
//		if(_switchTypeMap == null) buildSwitchMaps();
//		boolean[] isOperable = new boolean[_switchCSV.getRowCount()];
//		String[] typeIDs = _switchCSV.get("SwitchType");
//		
//		if(typeIDs == null)
//		{
//			System.err.println("[PFlowPsmModelBldr] Could not load column \"SwitchType\" from Switch.csv");
//			Arrays.fill(isOperable, false);
//		}
//		else
//		{
//			for(int i = 0; i < typeIDs.length; ++i)
//			{
//				isOperable[i] = _typeIsOperable[_switchTypeMap.get(typeIDs[i])];
//			}
//		}
//		
//		return isOperable;
//	}
//	
	private Gen.Mode[] getGenMode() throws PAModelException
	{
		String[] genOpMode = _genCaseCSV.get("GeneratorOperatingMode");
		String[] smOpMode = _synchCaseCSV.get("SynchronousMachineOperatingMode");
		int n = _genCSV.getRowCount();
		Gen.Mode genModes[] = new Gen.Mode[n];
		int nm = smOpMode.length-1;
		for(int i = 0; i < n; ++i)
		{
			int scx = _smCaseIdx[_smIdx[i]];
			
			String smode = nm < scx ? "":smOpMode[scx];
			String tmode = smode.equalsIgnoreCase("gen") ? genOpMode[_guCaseIdx[i]] : smode;  
			genModes[i] = Gen.Mode.valueOf(tmode.isEmpty() ? "Unknown" : tmode.toUpperCase());

		}
		
		return genModes;
	}
	
	private boolean[] getGenAVRMode() throws PAModelException
	{
		String[] genAVR = getStringData(_smCaseIdx, _synchCaseCSV.get("AVRMode"));
		int n = genAVR.length;
		boolean[] avr = new boolean[n];
		
		for(int i = 0; i < n; ++i)
		{
			avr[i] = (genAVR[i].toLowerCase().equals("off"))?false:true;
		}
		
		return avr;
	}
	
	private boolean[] getSvcAVR()
	{
		String[] svcAVR = getStringData(_svcCaseIdx, _svcCaseCSV.get("Mode"));
		int n = svcAVR.length;
		boolean[] avr = new boolean[n];
		
		for(int i = 0; i < n; ++i)
		{
			avr[i] = svcAVR[i].toLowerCase().equals("volt")?true:false;
		}
		
		return avr;
	}
	
	private float[] getLineB()
	{
		float[] lineB = _lineCSV.getFloats("Bch");
		float[] data = new float[lineB.length];
		
		for(int i = 0; i < lineB.length; ++i)
		{
			data[i] = lineB[i] / 2f;
		}
		
		return data;
	}
	
	
	private int[] getGenRegBus() throws PAModelException
	{
		String[] unsortedBuses = _synchMachineCSV.get("RegulatedNode");
		if(unsortedBuses == null)
			unsortedBuses = _synchMachineCSV.get("Node");
		return _busrb.revndx(unsortedBuses, _smIdx);
	}
	
//	private float[] getSwitchData(String col)
//	{
//		if(_switchCaseMap == null) buildSwitchMaps();
//		
//		String[] switchIDs = _switchCSV.get("ID");
//		float[] unsortedData = _switchCaseCSV.getFloats(col);
//		float[] data = new float[switchIDs.length];
//
//		if (unsortedData.length > 0)
//		{
//			for (int i = 0; i < switchIDs.length; ++i)
//				data[i] = unsortedData[_switchCaseMap.get(switchIDs.length)];
//		}		
//		return data;
//	}
//	
	/** Convert raw data to an array of floats based on the given index
	 * The index must contain only valid offsets.  If it doesn't, use the 
	 * getFloatDAta(int[] idx, String[] data, float deft) version instead that
	 * allows you to define a value even when no valid index exists
	 * 
	 */

	float[] getFloatData(int[] idx, String[] data)
	{
		int n = idx.length;
		float[] rv = new float[n];
		for(int i=0; i < n; ++i)
			rv[i] = Float.parseFloat(data[idx[i]]);
		return rv;
	}
	
	float[] getFloatData(int[] idx, String[] data, float deft)
	{
		int nidx = idx.length;
		float[] rv = new float[nidx];
		Arrays.fill(rv, deft);
		int ndata = data.length;
		for(int i=0; i < nidx; ++i)
		{
			int x = idx[i];
			String d = (x == -1 || x >= ndata) ? "" : data[x];
			rv[i] = d.isEmpty()?deft:Float.parseFloat(d);
		}
		return rv;
	}
	
	int[] getIntData(int[] idx, String[] data, int deft)
	{
		int nidx = idx.length;
		int[] rv = new int[nidx];
		Arrays.fill(rv, deft);
		int ndata = data.length;
		for(int i=0; i < nidx; ++i)
		{
			int x = idx[i];
			String d = (x == -1 || x >= ndata) ? "" : data[x];
			rv[i] = d.isEmpty()?deft:Integer.parseInt(d);
		}
		return rv;
	}
	
	String[] getStringData(int[] idx, String[] data)
	{
		int n = idx.length;
		String[] rv = new String[n];
		for(int i=0; i < n; ++i)
			rv[i] = data[idx[i]];
		return rv;
	}
//	private float[] getGenDataFloat(String col, SimpleCSV src, int[] idx)
//	{
//		int ngen = _genCSV.getRowCount();
//		float[] rv = new float[ngen];
//		String[] sdata = src.get(col);
//		for(int i=0; i < ngen; ++i)
//			rv[i] = Float.parseFloat(sdata[idx[i]]);
//		
//		//Build maps if they are empty
//		if(_genCaseMap == null || _genToSynchMap == null) buildGeneratorMaps();
//		
//		//Figure out what csv we are dealing with
//		if(csv.toLowerCase().equals("synch"))
//		{
//			unsortedData = _synchMachineCSV.getFloats(col);
//			if(unsortedData == null) 
//			{
////				System.err.println("[PFlowPsmModelBldr] Error loading synchronous machine column \""+col+"\". Does it exist in the CSV?");
//				Arrays.fill(data, -999);
//			}
//			else
//			{
//				for (int i = 0; i < genIDs.length; ++i)
//				{
//					data[i] = unsortedData[_genToSynchMap.get(genIDs[i])];
//				}
//			}
//		}
//		else if(csv.toLowerCase().equals("synchcase"))
//		{
//			String[] synchIDs = _synchMachineCSV.get("ID");
//			unsortedData = _synchCaseCSV.getFloats(col);
//			if(unsortedData == null) 
//			{
////				System.err.println("[PFlowPsmModelBldr] Error loading synchronous machine case column \""+col+"\". Does it exist in the CSV?");
//				Arrays.fill(data, -999);
//			}
//			else
//			{
//				for(int i = 0; i < genIDs.length; ++i)
//				{
//					data[i] = unsortedData[_syncCasehMap.get(synchIDs[_genToSynchMap.get(genIDs[i])])];
//				}
//			}
//		}
//		else if(csv.toLowerCase().equals("gencase"))
//		{
//			unsortedData = _genCaseCSV.getFloats(col);
//			if(unsortedData == null) 
//			{
////				System.err.println("[PFlowPsmModelBldr] Error loading generator case column \""+col+"\". Does it exist in the CSV?");
//				Arrays.fill(data, -999);
//			}
//			else
//			{
//				for (int i = 0; i < genIDs.length; ++i)
//				{
//					data[i] = unsortedData[_genCaseMap.get(genIDs[i])];
//				}
//			}
//		}
//		else if(csv.toLowerCase().equals("curve"))
//		{
//			unsortedData = _reacCapCurveCSV.getFloats(col);
//			String[] synchIDs = _synchMachineCSV.get("ID");
//			
//			if(unsortedData == null) 
//			{
////				System.err.println("[PFlowPsmModelBldr] Error loading reactive capability curve column \""+col+"\". Does it exist in the CSV?");
//				Arrays.fill(data, -999);
//			}
//			else
//			{
//				
//				for (int i = 0; i < genIDs.length; ++i)
//				{
//					data[i] = unsortedData[_synchToCurveMap.get(synchIDs[_genToSynchMap.get(genIDs[i])])];
//				}
//			}
//		}
//		else
//		{
//			Arrays.fill(data, -999);
//			return data;
//		}
//		
//		return data;
//	}
	
//	private String[] getGenDataString(String col, String csv)
//	{
//		String[] genIDs = _genCSV.get("ID");
//		String[] unsortedData;
//		String[] data = new String[_genCaseCSV.getRowCount()];
//		
//		//Build maps if they are empty
//		if(_genCaseMap == null || _genToSynchMap == null) buildGeneratorMaps();
//		
//		//Figure out what csv we are dealing with
//		if(csv.toLowerCase().equals("synch"))
//		{
//			unsortedData = _synchMachineCSV.get(col);
//			if(unsortedData == null)
//			{
////				System.err.println("[PFlowPsmModelBldr] Error loading synchronous machine column \""+col+"\". Does it exist in the CSV?");
//				Arrays.fill(data, "null");
//			}
//			else
//			{
//				for (int i = 0; i < genIDs.length; ++i)
//				{
//					data[i] = unsortedData[_genToSynchMap.get(genIDs[i])];
//				}				
//			}
//		}
//		else if(csv.toLowerCase().equals("synchcase"))
//		{
//			String[] synchIDs = _synchMachineCSV.get("ID");
//			unsortedData = _synchCaseCSV.get(col);
//			if(unsortedData == null) 
//			{
////				System.err.println("[PFlowPsmModelBldr] Error loading synchronous machine case column \""+col+"\". Does it exist in the CSV?");
//				Arrays.fill(data, "null");
//			}
//			else
//			{				
//				for(int i = 0; i < genIDs.length; ++i)
//				{
//					data[i] = unsortedData[_syncCasehMap.get(synchIDs[_genToSynchMap.get(genIDs[i])])];
//				}
//			}
//		}
//		else if(csv.toLowerCase().equals("gencase"))
//		{
//			unsortedData = _genCaseCSV.get(col);
//			if(unsortedData == null) 
//			{
////				System.err.println("[PFlowPsmModelBldr] Error loading generator case column \""+col+"\". Does it exist in the CSV?");
//				Arrays.fill(data, "null");
//			}
//			else
//			{
//				for (int i = 0; i < genIDs.length; ++i)
//				{
//					data[i] = unsortedData[_genCaseMap.get(genIDs[i])];
//				}				
//			}
//		}
//		else return null;
//		
//		return data;
//	}
//	
//	private float[] getLineCaseData(String col)
//	{
//		if(_lineCaseMap == null) buildLineMap();
//	
//		String[] ids = _lineCSV.get("ID");
//		float[] unsortedData = _lineCaseCSV.getFloats(col);
//		float[] data = new float[ids.length];
//		if(unsortedData == null) 
//		{
////			System.err.println("[PFlowPsmModelBldr] Error loading line case column \""+col+"\". Does it exist in the CSV?");
//			Arrays.fill(data, -999);
//		}
//		else
//		{
//			for(int i = 0; i < ids.length; ++i)
//			{
//				if (_lineCaseMap.containsKey(ids[i]))
//					data[i] = unsortedData[_lineCaseMap.get(ids[i])];
//			}
//		}
//		
//		return data;
//	}
//	
//	private float[] getSeriesReacData(String col)
//	{
//		if(_seriesReacCaseMap == null) buildSeriesReactorMap();
//		String[] ids = _seriesReacCSV.get("ID");
//		float[] unsortedData = _seriesReacCaseCSV.getFloats(col);
//		float[] data = new float[ids.length];
//		
//		if(unsortedData == null)
//		{
////			System.err.println("[PFlowPsmModelBldr] Error loading series reactor case column \""+col+"\". Does it exist in the CSV?");
//			Arrays.fill(data, -999);
//		}
//		else
//		{
//			for(int i = 0; i < ids.length; ++i)
//			{
//				data[i] = unsortedData[_seriesReacCaseMap.get(ids[i])];
//			}
//		}
//		
//		return data;
//	}
	
//	private float[] getSeriesCapData(String col)
//	{
//		if(_seriesCapCaseMap == null) buildSeriesCapacitorMap();
//		String[] ids = _seriesCapCSV.get("ID");
//		float[] unsortedData = _seriesCapCaseCSV.getFloats(col);
//		float[] data = new float[ids.length];
//		
//		if(unsortedData == null)
//		{
//			System.err.println("[PFlowPsmModelBldr] Error loading series capacitor case column \""+col+"\". Does it exist in the CSV?");
//			Arrays.fill(data, -999);
//		}
//		else
//		{
//			for(int i = 0; i < ids.length; ++i)
//			{
//				data[i] = unsortedData[_seriesCapCaseMap.get(ids[i])];
//			}
//		}
//		
//		return data;
//	}
//	
//	private float[] getSVCDataFloats(String col)
//	{
//		//Build maps if they don't exist
//		if(_svcCaseMap == null) buildSVCMap();
//		String[] ids = _svcCaseCSV.get("ID");
//		float[] unsortedData = _svcCaseCSV.getFloats(col);
//		float[] data = new float[ids.length];
//		if(unsortedData == null) 
//		{
//			System.err.println("[PFlowPsmModelBldr] Error loading svc case column \""+col+"\". Does it exist in the CSV?");
//			Arrays.fill(data, -999);
//		}
//		else
//		{
//			for(int i = 0; i < ids.length; ++i)
//			{
//				data[i] = unsortedData[_svcCaseMap.get(ids[i])];
//			}
//		}
//		
//		return data;
//	}
	
//	private String[] getSVCDataStrings(String col)
//	{
//		//Build maps if they don't exist
//		if(_svcCaseMap == null) buildSVCMap();
//		String[] ids = _svcCaseCSV.get("ID");
//		String[] unsortedData = _svcCaseCSV.get(col);
//		String[] data = new String[ids.length];
//		if(unsortedData == null) 
//		{
//			System.err.println("[PFlowPsmModelBldr] Error loading line case column \""+col+"\". Does it exist in the CSV?");
//			Arrays.fill(data, -999);
//		}
//		else
//		{
//			for(int i = 0; i < ids.length; ++i)
//			{
//				data[i] = unsortedData[_svcCaseMap.get(ids[i])];
//			}
//		}
//		
//		return data;
//	}
//	
//	private float[] getTransformerRatios(boolean isTo)
//	{
//		float[] data = new float[_transformerIDs.size()];
//		float[] ratios = _ratioTapChgCaseCSV.getFloats("Ratio");
//		String[] ratioIDs = _ratioTapChgCSV.get("ID");
//		String[] wdgIDs = _ratioTapChgCSV.get("TransformerWinding");
//		String[] tfmrIDs = _tfmrWindingCSV.get("Transformer");
//		String[] tapNodeIDs = _ratioTapChgCSV.get("TapNode");
//		String nodeCol = isTo ? "Node2":"Node1";
//		String[] tfmrNode = _tfmrWindingCSV.get(nodeCol);
//		
//		for(int i = 0; i < ratios.length; ++i)
//		{
//			//check if the current tapNodeIDs is the to or from node
//			if(tfmrNode[_windingMap.get(wdgIDs[i])].equals(tapNodeIDs[i]))
//			{
//				data[_transformerMap.get(tfmrIDs[_windingMap.get(wdgIDs[i])])] = ratios[_ratioCaseMap.get(ratioIDs[i])];
//			}
//		}
//		
//		return data;
//	}
//	
//	private String[] getTransformerDataStrings(String col, String csv, boolean isTfmr)
//	{
//		//Build maps if they don't exist
//		if(_transformerMap == null) buildTransformerMaps();
//		List<String> ids = (isTfmr)?_transformerIDs:_phaseShifterIDs;
//		
//		
//		
//		String[] data = new String[ids.size()];
//		String[] unsortedData;
//		
//		if(csv.toLowerCase().equals("transformer"))
//		{
//			unsortedData = _transformerCSV.get(col);
//			if(unsortedData == null) 
//			{
//				System.err.println("[PFlowPsmModelBldr] Error loading transformer column \""+col+"\". Does it exist in the CSV?");
//				Arrays.fill(data, "null");
//			}
//			else
//			{
//				for(int i = 0; i < ids.size(); ++i)
//				{
//					data[i] = unsortedData[_transformerMap.get(ids.get(i))];
//				}
//			}
//		}
//		else if(csv.toLowerCase().equals("winding"))
//		{
//			unsortedData = _tfmrWindingCSV.get(col);
//			if(unsortedData == null)
//			{
//				System.err.println("[PFlowPsmModelBldr] Error loading winding column \""+col+"\". Does it exist in the CSV?");
//				Arrays.fill(data, "null");
//			}
//			else
//			{
//				for(int i = 0; i < ids.size(); ++i)
//				{
//					data[i] = unsortedData[_wdgToTfmrMap.get(ids.get(i))];
//				}
//			}
//		}
//		else if(csv.toLowerCase().equals("phasecase"))
//		{
//			unsortedData = _phaseCaseCSV.get(col);
//			String[] phaseCaseIDs = _phaseCaseCSV.get("ID");
//			if(unsortedData == null)
//			{
//				System.err.println("[PFlowPsmModelBldr] Error phase shifter case column \""+col+"\". Does it exist in the CSV?");
//				Arrays.fill(data, "null");
//			}
//			else
//			{
//				for(int i = 0; i < ids.size(); ++i)
//				{
//					data[_tfmrPhaseTapMap.get(phaseCaseIDs[i])] = unsortedData[i];
//				}
//			}
//		}
//		else if(csv.toLowerCase().equals("ratiocase"))
//		{
//			unsortedData = _ratioTapChgCaseCSV.get(col);
//			String[] ratioCaseIDs = _ratioTapChgCaseCSV.get("ID");
//			if(unsortedData == null)
//			{
//				System.err.println("[PFlowPsmModelBldr] Error ratio tap change case column \""+col+"\". Does it exist in the CSV?");
//				Arrays.fill(data, "null");
//			}
//			else
//			{
//				for(int i = 0; i < ids.size(); ++i)
//				{
//					data[_tfmrRatioTapMap.get(ratioCaseIDs[i])] = unsortedData[i];
//				}
//			}
//		}
//		else
//		{
//			System.err.println("[PFlowPsmModelBldr] No functionality setup for csv \""+csv+"\"");
//			Arrays.fill(data, "null");
//		}
//		
//		return data;
//	}
//	
//	private boolean[] getTransformerDataBools(String col, String csv, boolean isTfmr)
//	{	
//		String[] unsortedData = getTransformerDataStrings(col, csv, isTfmr);
//		boolean[] data = new boolean[unsortedData.length];
//		
//		for(int i = 0; i < data.length; ++i)
//		{
//			data[i] = (unsortedData[i].toLowerCase().equals("true"))?true:false;
//		}
//		
//		return data;
//	}
//	
//	private float[] getTransformerDataFloats(String col, String csv, boolean isTfmr)
//	{	
//		//Build maps if they don't exist
//		if(_transformerMap == null) buildTransformerMaps();
//		List<String> ids = (isTfmr)?_transformerIDs:_phaseShifterIDs;
//		
//		float[] data = new float[ids.size()];
//		float[] unsortedData;
//		
//		if(csv.toLowerCase().equals("transformer"))
//		{
//			unsortedData = _transformerCSV.getFloats(col);
//			if(unsortedData == null)
//			{
//				System.err.println("[PFlowPsmModelBldr] Error loading transformer column \""+col+"\". Does it exist in the CSV?");
//				Arrays.fill(data, -999);
//			}
//			else
//			{
//				for(int i = 0; i < ids.size(); ++i)
//				{
//					data[i] = unsortedData[_transformerMap.get(ids.get(i))];
//				}				
//			}
//		}
//		else if(csv.toLowerCase().equals("phasecase"))
//		{
//			unsortedData = _phaseCaseCSV.getFloats(col);
//			String[] phaseCaseIDs = _phaseCaseCSV.get("ID");
//			if(unsortedData == null)
//			{
//				System.err.println("[PFlowPsmModelBldr] Error phase shifter case column \""+col+"\". Does it exist in the CSV?");
//				Arrays.fill(data, -999);
//			}
//			else
//			{
//				for(int i = 0; i < ids.size(); ++i)
//				{
//					data[_tfmrPhaseTapMap.get(phaseCaseIDs[i])] = unsortedData[i];
//				}
//			}
//		}
//		else if(csv.toLowerCase().equals("ratiocase"))
//		{
//			unsortedData = _ratioTapChgCaseCSV.getFloats(col);
//			String[] ratioCaseIDs = _ratioTapChgCaseCSV.get("ID");
//			if(unsortedData == null)
//			{
//				System.err.println("[PFlowPsmModelBldr] Error ratio tap change case column \""+col+"\". Does it exist in the CSV?");
//				Arrays.fill(data, -999);
//			}
//			else
//			{
//				for(int i = 0; i < ids.size(); ++i)
//				{
//					data[_tfmrRatioTapMap.get(ratioCaseIDs[i])] = unsortedData[i];
//				}
//			}
//		}
//		else if(csv.toLowerCase().equals("winding"))
//		{
//			unsortedData = _tfmrWindingCSV.getFloats(col);
//			if(unsortedData == null) 
//			{
//				System.err.println("[PFlowPsmModelBldr] Error loading winding column \""+col+"\". Does it exist in the CSV?");
//				Arrays.fill(data, -999);
//			}
//			else
//			{
//				for(int i = 0; i < ids.size(); ++i)
//				{
//					data[i] = unsortedData[_wdgToTfmrMap.get(ids.get(i))];
//				}				
//			}
//		}
//		else
//		{
//			System.err.println("[PFlowPsmModelBldr] No functionality added for \""+csv+"\"");
//		}
//		return data;
//	}
//	
	private PhaseShifter.ControlMode[] getPhaseCtrlMode()
	{
		boolean[] baseData = getBooleanData(_ps.getSide(DevSide.From).casex, 
			_phaseTapChgCaseCSV.get("ControlStatus"));
		PhaseShifter.ControlMode[] data = new PhaseShifter.ControlMode[baseData.length];
		
		for(int i = 0; i < data.length; ++i)
		{
			data[i] = baseData[i] ? PhaseShifter.ControlMode.FixedMW:PhaseShifter.ControlMode.FixedAngle;
		}
		
		return data;
	}
	
//	private float[] getWindingCaseData(String col, boolean isTfmr)
//	{
//		float[] unsortedData = _windingCaseCSV.getFloats(col);
//		List<String> ids = (isTfmr)?_transformerIDs:_phaseShifterIDs;
//		String[] wdgIDs = _tfmrWindingCSV.get("ID");
//		float[] data = new float[ids.size()];
//		if(unsortedData == null) 
//		{
//			System.err.println("[PFlowPsmModelBldr] Error loading winding case column \""+col+"\". Does it exist in the CSV?");
//			Arrays.fill(data, -999);
//		}
//		else
//		{
//			for(int i = 0; i < ids.size(); ++i)
//			{
//				//Have tfmr/phase ID. Need to winding ID.
//				//Using the winding ID we'll get the case offset which will give us the float from unsorted date
////			System.out.println("\n============\n[getWindingCaseData] Col: \""+col+"\"\n[getWindingCaseData] IDs["+i+"/"+ids.size()+" | "+_ratioTapChgCSV.getRowCount()+"] = "+ids.get(i));
////			System.out.println("[getWindingCaseData] wdgIDs["+_wdgToTfmrMap.get(ids.get(i))+"] = "+wdgIDs[_wdgToTfmrMap.get(ids.get(i))]);
////			System.out.println("[getWindingCaseData] data["+_windingCaseMap.get(wdgIDs[_wdgToTfmrMap.get(ids.get(i))])+"] = "+unsortedData[_windingCaseMap.get(wdgIDs[_wdgToTfmrMap.get(ids.get(i))])]);
//				String wid = wdgIDs[_wdgToTfmrMap.get(ids.get(i))];
//				if (_windingCaseMap.containsKey(wid))
//					data[i] = unsortedData[_windingCaseMap.get(wid)];
//			}			
//		}
//		
//		return data;
//	}
	
//	private void buildTransformerMaps()
//	{	
//		String[] ratioCaseIDs 	= _ratioTapChgCaseCSV.get("ID");
//		String[] ratioTapIDs 	= _ratioTapChgCSV.get("ID");
//		String[] wdgInRatioIDs 	= _ratioTapChgCSV.get("TransformerWinding");
//		String[] tfmrInWdgIDs 	= _tfmrWindingCSV.get("Transformer");
//		String[] windingIDs 	= _tfmrWindingCSV.get("ID");
//		String[] phaseTapIDs	= _phaseTapChgCSV.get("ID");
//		String[] wdgInPhaseIDs  = _phaseTapChgCSV.get("TransformerWinding");
//		String[] allIDs 		= _transformerCSV.get("ID");
//		String[] wdgCaseIDs		= _windingCaseCSV.get("ID");
//
//		_transformerMap 	= new TObjectIntHashMap<>(allIDs.length);
//		_windingMap			= new TObjectIntHashMap<>(windingIDs.length);
//		_windingCaseMap		= new TObjectIntHashMap<>(windingIDs.length);
//		_tfmrRatioTapMap 	= new TObjectIntHashMap<>(wdgInRatioIDs.length); //Key = Transformer, Value = Ratio Tap Offset
//		_wdgInRatioMap		= new TObjectIntHashMap<>(wdgInRatioIDs.length);
//		_wdgInPhaseMap 		= new TObjectIntHashMap<>(phaseTapIDs.length);
//		_wdgToTfmrMap		= new TObjectIntHashMap<>(windingIDs.length); //Key = transformer, value = Winding Offset
//		_tfmrPhaseTapMap	= new TObjectIntHashMap<>(phaseTapIDs.length);
//		_ratioCaseMap 		= new TObjectIntHashMap<>(ratioTapIDs.length);
//		
//		if(_transformerIDs == null) _transformerIDs = new ArrayList<String>();
//		if(_phaseShifterIDs == null) _phaseShifterIDs = new ArrayList<String>();
//		
//		//Are transformer and winding csv's always the same length?
//		//Build maps based on transformer length
//		for(int i = 0; i < allIDs.length; ++i)
//		{
//			_transformerMap.put(allIDs[i], i);
//		}
//		//Build maps based on Winding length
//		for(int i = 0; i < windingIDs.length; ++i)
//		{
//			_windingMap.put(windingIDs[i], i);
//			_wdgToTfmrMap.put(tfmrInWdgIDs[i], i);
////			System.out.println("[buildTransformerMaps] _windingCaseMap.put("+wdgCaseIDs[i]+", "+i+")");
//			if(wdgCaseIDs.length > 0) _windingCaseMap.put(wdgCaseIDs[i], i);
//		}
//		
//		//Build maps based only on Phase Tap
//		for(int i = 0; i < phaseTapIDs.length; ++i)
//		{
////			System.out.println("[buildTransformerMaps] _wdgInPhaseMap.put("+wdgInPhaseIDs[i]+", "+i+")");
//			_wdgInPhaseMap.put(wdgInPhaseIDs[i], i);
//		}
//		
//		
//		//Build maps based only on Ratio Tap
//		for(int i = 0; i < ratioTapIDs.length; ++i)
//		{
////			System.out.println("[buildTransformerMaps] wdgInRationIDs["+i+"/"+ratioTapIDs.length+"] = "+wdgInRatioIDs[i]);
//			_wdgInRatioMap.put(wdgInRatioIDs[i], i);
//			_ratioCaseMap.put(ratioCaseIDs[i], i);
//		}
//		
//		//Array of transformers and phase shifters ?
//		for(int i = 0; i < allIDs.length; ++i)
//		{
//			//Have to use the transformer ID to get the winding ID
//			//Use the winding ID to see if it is a transformer or phase shifter
//			
//			//First figure out if the winding is for a transformer or phase shifter
//			//System.out.println("allIDs["+i+"/"+allIDs.length+"] = "+allIDs[i]);
//			if(_wdgInRatioMap.containsKey(windingIDs[i]))
//			{
//				//ID belongs to a transformer
//				//Place id in transformer array list
//				_transformerIDs.add(tfmrInWdgIDs[i]);
////				System.out.println("transformerIDs["+tfmrOff+"/"+ratioTapIDs.length+"] = "+tfmrInWdgIDs[i]);
//				//Figure out the transformer ID key to ratio tap offset map
//				_tfmrRatioTapMap.put(tfmrInWdgIDs[i], _wdgInRatioMap.get(windingIDs[i]));
//			}
//			else if(_wdgInPhaseMap.containsKey(windingIDs[i]))
//			{
//				_phaseShifterIDs.add(tfmrInWdgIDs[i]);
////				System.out.println("phaseShifterIDs["+phaseOff+"/"+phaseTapIDs.length+"] = "+tfmrInWdgIDs[i]);
//				_tfmrPhaseTapMap.put(tfmrInWdgIDs[i], _wdgInPhaseMap.get(windingIDs[i]));
//			}
//			else
//			{
//				System.err.println("[buildTransformerMaps] ID not found: "+windingIDs[i]);
//			}
//		}
//		
//	}
//	
//	private void buildSubstationMap() throws PAModelException
//	{
//		if(_substationCSV == null) loadStations();
//		String[] subIDs = _substationCSV.hasCol("ID") ? _substationCSV.get("ID") : new String[0];
//		_stationOffsetMap = new TObjectIntHashMap<>(subIDs.length);
//		
//		for(int i = 0; i < subIDs.length; ++i)
//		{
//			_stationOffsetMap.put(subIDs[i], i);
//		}
//	}
//	
//	private void buildSeriesReactorMap()
//	{
//		String[] caseIDs = _seriesReacCaseCSV.get("ID");
//		
//		_seriesReacCaseMap = new TObjectIntHashMap<>(caseIDs.length);
//		
//		for(int i = 0; i < caseIDs.length; ++i)
//		{
//			_seriesReacCaseMap.put(caseIDs[i], i);
//		}
//	}
//	
//	private void buildSeriesCapacitorMap()
//	{
//		String[] caseIDs = _seriesCapCaseCSV.get("ID");
//		
//		_seriesCapCaseMap = new TObjectIntHashMap<>(caseIDs.length);
//		
//		for(int i = 0; i < caseIDs.length; ++i)
//		{
//			_seriesCapCaseMap.put(caseIDs[i], i);
//		}
//	}
//	
//	private void buildGeneratorMaps()
//	{
//		String[] genCaseIDs = _genCaseCSV.get("ID");
//		String[] synchCaseIDs = _synchCaseCSV.get("ID");
//		String[] synchGenIDs = _synchMachineCSV.get("GeneratingUnit");
//		String[] reacSynchIDs = _reacCapCurveCSV.get("SynchronousMachine");
//		
//		_genCaseMap = new TObjectIntHashMap<>(genCaseIDs.length);
//		_syncCasehMap = new TObjectIntHashMap<>(synchCaseIDs.length);
//		_genToSynchMap = new TObjectIntHashMap<>(genCaseIDs.length);
//		_genToSynchCaseMap = new TObjectIntHashMap<>(genCaseIDs.length);
//		_synchToCurveMap = new TObjectIntHashMap<>(synchCaseIDs.length);
//		
//		for(int i = 0; i < genCaseIDs.length; ++i)
//		{
//			_genCaseMap.put(genCaseIDs[i], i); // Takes generating unit ID
//			_syncCasehMap.put(synchCaseIDs[i], i); // Takes synch machine ID
//			_genToSynchMap.put(synchGenIDs[i], i); //Takes generating ID
//			if(i < reacSynchIDs.length)
//				_synchToCurveMap.put(reacSynchIDs[i], i); //Takes synch machine ID
//		}
//	}
//	
//	private void buildSwitchMaps()
//	{
//		String[] caseIDs = _switchCaseCSV.get("ID");
//		String[] typeIDs = _switchTypeCSV.get("ID");
//		String[] open = _switchTypeCSV.get("OpenUnderLoad");
//		String[] close = _switchTypeCSV.get("CloseUnderLoad");
//		_typeIsOperable = new boolean[typeIDs.length];
//		_switchCaseMap = new TObjectIntHashMap<>(caseIDs.length);
//		_switchTypeMap = new TObjectIntHashMap<>(_switchTypeCSV.getRowCount());
//		
//		for(int i = 0; i < caseIDs.length; ++i)
//		{
//			_switchCaseMap.put(caseIDs[i], i);
//		}
//		
//		for(int i = 0; i < typeIDs.length; ++i)
//		{
//			_switchTypeMap.put(typeIDs[i], i);
//			//Build a boolean array at this time to figure out if a switch with this type is operable under load
//			_typeIsOperable[i] = (open[i].toLowerCase().equals("true") && close[i].toLowerCase().equals("true"))?true:false;
//		}
//	}
//	
//	private void buildSVCMap()
//	{
//		String[] caseIDs = _svcCaseCSV.get("ID");
//		_svcCaseMap = new TObjectIntHashMap<>(caseIDs.length);
//		
//		for(int i = 0; i < caseIDs.length; ++i)
//		{
//			_svcCaseMap.put(caseIDs[i], i);
//		}
//	}
//	
//	private Switch.State[] getSwitchState()
//	{
//		String[] unsortedData = _switchCaseCSV.get("SwitchPosition");
//		Switch.State[] state = new Switch.State[ids.length];
//		for(int i = 0; i < ids.length; ++i)
//		{
//			state[i] = (unsortedData[_switchCaseMap.get(ids[i])].equals("Open"))?Switch.State.Open:Switch.State.Closed;
//		}
//		
//		return state;
//	}
	
//	private void buildLoadMap()
//	{
//		String[] caseIDs = _loadCaseCSV.get("ID");
//		int ncase = caseIDs.length;
//		_loadCaseMap = new TObjectIntHashMap<>(ncase);
//		
//		for(int i = 0; i < ncase; ++i)
//		{
//			_loadCaseMap.put(caseIDs[i], i);
//		}
//
//	}
//	
//	private void buildShuntReacMap()
//	{
//		String[] caseIDs = _shuntReacCaseCSV.get("ID");
//		
//		_shuntReacCaseMap = new TObjectIntHashMap<>(caseIDs.length);
//		for(int i = 0; i < caseIDs.length; ++i)
//		{
//			_shuntReacCaseMap.put(caseIDs[i], i);
//		}
//	}
//	
//	private void buildShuntCapMap()
//	{
//		String[] caseIDs = _shuntCapCaseCSV.get("ID");
//		
//		_shuntCapCaseMap = new TObjectIntHashMap<>(caseIDs.length);
//		for(int i = 0; i < caseIDs.length; ++i)
//		{
//			_shuntCapCaseMap.put(caseIDs[i], i);
//		}
//	}
//	
//	private void buildLineMap()
//	{
//		String[] caseIDs = _lineCaseCSV.get("ID");
//		
//		_lineCaseMap = new TObjectIntHashMap<>(caseIDs.length);
//		for(int i = 0; i < caseIDs.length; ++i)
//		{
//			_lineCaseMap.put(caseIDs[i], i);
//		}
//	}
	
//	private int[] getBusesById(String[] ids) throws PAModelException 
//	{
//		if(_busCSV == null) loadBuses();
//		int[] indexes = _m.getBuses().getIndexesFromIDs(ids);
//		return indexes;
//	}
//	private int[] getBusesByIdx(String[] ids, int[] idx) throws PAModelException 
//	{
//		if(_busCSV == null) loadBuses();
//		BusList buses = _m.getBuses();
//		int n = idx.length;
//		int[] indexes = new int[n];
//		for(int i=0; i < n; ++i)
//			indexes[i] = buses.getByID(ids[idx[i]]).getIndex();
//		return indexes;
//	}
//
}
