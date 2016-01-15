package com.powerdata.openpa.se;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.powerdata.openpa.*;
import com.powerdata.openpa.impl.BasicGroupIndex;
import com.powerdata.openpa.pwrflow.ListDumper;
import com.powerdata.openpa.tools.BusConnectionsPriQ;
import com.powerdata.openpa.tools.BusConnectionsPriQ.BusFunction;
import com.powerdata.openpa.tools.ConcurrentGroupLinkNet;
import com.powerdata.openpa.tools.LinkNet;
import gnu.trove.map.hash.TIntObjectHashMap;

/**
 * <p>
 * List of observable islands based on a USDOE paper,
 * "Contribution to Power State Estimation and Transient Stability Analysis" ,
 * Feb 1984.
 * </p>
 * <p>
 * Measurement Telemetered status is not yet available directly in OpenPA or the
 * PSIM CSV formats. As such, we look for non-zero measurements on devices
 * connected to known-energized buses.
 * </p>
 * <p>
 * This class provides a list of observable islands built using
 * {@link ObsIslandBldrDOE} (inner static class for now). This class has been
 * tested only against a 24-bus model with fabricated data. Test procedures are
 * under development for larger models
 * </p>
 * 
 * 
 * <p>
 * There is a 2nd "build", @{link ObsIslandBldrNew} algorith based on the DOE
 * algorithm, with some potential optimizations in place. This is a work in
 * progress, and does not currently work.
 * </p>
 * 
 * @author chris@powerdata.com
 *
 */

public class ObservableIslandList extends 
	GroupListI<com.powerdata.openpa.se.ObservableIslandList.ObservableIsland>
{

	/**
	 * Single Observable Island.
	 */
	public class ObservableIsland extends Group
	{
		public ObservableIsland(int ndx)
		{
			super(ObservableIslandList.this, ndx);
		}
		
		public boolean hasVoltage() throws PAModelException
		{
			return ObservableIslandList.this.hasVoltage(_ndx);
		}
	}

	enum BranchState {NotFound, Known, Unknown;}

	/** Provide in interface to trace debug information in different implementations */
	public interface Debug
	{
		/** During initialization, track which buses have avalable injection measurements */
		void initInjection(int bus, boolean valid);
		/** During initialization, track which branches have telemetry */
		void initMeasuredBranch(int branch, int destisl, int srcisl);
		/**
		 * During initialization, track branches without telemetry, but an
		 * available injection on either side
		 */
		void initUnknownBranch(int br, int bus);
		/**
		 * The DOE paper step 1, where unknown branches are evaluated to
		 * determine if both buses reside in the same island
		 */
		void step1KnownBranch(int branch, int objisle);
		/** DOE psper step 2, where a merge occurs */ 
		void step2Merge(int bus, int destisl, int srcisl);
		/** track branch offset to actual Branch */
		void trackBranch(int brofs, ACBranch branch) throws PAModelException;
	}
	
	/** No debugging enabled */
	static final Debug _NDbg = new Debug()
	{
		@Override
		public void initInjection(int bus, boolean valid) {}
		@Override
		public void initMeasuredBranch(int branch, int destisl, int srcisl) {}
		@Override
		public void step1KnownBranch(int branch, int objisle) {}
		@Override
		public void step2Merge(int bus, int srcisl, int destisl) {}
		@Override
		public void initUnknownBranch(int br, int bus) {}
		@Override
		public void trackBranch(int br, ACBranch branch) {}
	};
	
	/** Electrical model and state */
	PAModel _model;
	/** Optional debug container */
	Debug _dbg;
	/**
	 * Used for parent list support that requires names and ID's for list
	 * elements.  This would not normally be used in production, only debugging
	 */
	String[] _id = null;
	
	/**
	 * Create a new observable island list
	 * @param model
	 * @throws PAModelException
	 */
	public ObservableIslandList(PAModel model) throws PAModelException
	{
//		super(model, new ObsIslandBldrNew(model, _NDbg).constructIndex());
		super(model, new ObsIslandBldrDOE(model, _NDbg).constructIndex());
		_model = model;
		_dbg = _NDbg;
	}
	
	/**
	 * Create a new observable island list with debugging enabled
	 * @param model
	 * @throws PAModelException
	 */
	public ObservableIslandList(PAModel model, Debug debug) throws PAModelException
	{
//		super(model, new ObsIslandBldrNew(model, debug).constructIndex());
		super(model, new ObsIslandBldrDOE(model, debug).constructIndex());
		_model = model;
		_dbg = debug;
	}
	
	@Override
	public ListMetaType getListMeta() {return null;}
 
	@Override
	public Set<ColumnMeta> getColTypes() {return null;}
	
	@Override
	public ObservableIsland get(int index)
	{
		return new ObservableIsland(index);
	}
	
	/**
	 * Try to find a bus with voltage telemetry
	 * 
	 * TODO: Since OpenPA doesn't yet have explicity telemetry status, we assume
	 * that if a bus has nonzero voltage but we know that the topological island
	 * is energized, then we have good telemetry
	 * 
	 * @param ndx
	 * @return
	 * @throws PAModelException
	 */
	public boolean hasVoltage(int ndx) throws PAModelException
	{
		/*
		 * Test a bus in the electrical island to make sure that we're energized
		 */
		BusList buses = get(ndx).getBuses();
		
		if(!_model.getElectricalIslands().getByBus(buses.get(0)).isEnergized()) return false;
		
		/*
		 * If electrically energized, look for any single nonzero voltage
		 */
		for(Bus b : buses)
		{
			if(b.getVM() != 0f) return true;
		}
		
		/*
		 * Nothing available, no voltage measurement exists so we're not really observable.
		 */
		return false;
	}
	
	
	protected void makeID()
	{
		int n = size();
		_id = new String[n];
		for(int i=0; i < n; ++i)
			_id[i] = "ObsIsland-"+i;
	}
	
	@Override
	public String getID(int ndx)
	{
		if (_id == null) makeID();
		return _id[ndx];
	}

	@Override
	public String[] getID() throws PAModelException
	{
		if (_id == null) makeID();
		return _id;
	}

	@Override
	public String getName(int ndx) throws PAModelException
	{
		if (_id == null) makeID();
		return _id[ndx];
	}

	@Override
	public String[] getName() throws PAModelException
	{
		if (_id == null) makeID();
		return _id;
	}

	/**
	 * <p>
	 * Build observable islands based on a USDOE paper,
	 * "Contribution to Power State Estimation and Transient Stability Analysis"
	 * , Feb 1984
	 * </p>
	 * <p>
	 * OpenPA does not yet support telemetry state (metered versus non-metered
	 * measurements), so we use the following for now:
	 * 
	 * <ul>
	 * <li>out-of-service injections count as known</li>
	 * <li>in-service units with an "OFF" mode count as known</li>
	 * <li>synchronous condensers with MVAr != 0 are known</li>
	 * <li>all others require MW != 0 and MVAr != 0</li>
	 * <li>fixed shunts are known if there is a known voltage</li>
	 * </ul>
	 * </p>
	 * <p>
	 * Items that we don't handle:
	 * 
	 * <ul>
	 * <li>We don't worry yet about "preferred solution" variables (either taps
	 * or buses)</li>
	 * <li>We don't yet handle tap branches</li>
	 * </ul>
	 * </p>
	 * 
	 * @author chris@powerdata.com
	 *
	 */
	static abstract class ObsIslandBldr
	{
		/** Use to convert equipment buses into the desired topology */
		BusRefIndex _bri;
		/** Representation of model network and state */
		PAModel _model;
		/** Optional debug container */
		Debug _dbg;
		
		ObsIslandBldr(PAModel model, Debug dbg) throws PAModelException
		{
			_model = model;
			_dbg = dbg;
			_bri = BusRefIndex.CreateFromSingleBuses(_model);
		}
		
		/**
		 * Test if a branch is metered.
		 * 
		 * TODO:  OpenPA/PSIM needs to be extended to handle a telemetered state directly.
		 * 
		 * @param br Branch to test for telemetry
		 * @return true if branch has telemetry, false otherwise
		 * @throws PAModelException
		 */
		boolean isBranchMetered(ACBranch br) throws PAModelException
		{
			boolean hot = br.getFromBus().getIsland().isEnergized();
			return  hot && (br.getFromP() != 0f || 
					br.getToP() != 0f ||
					br.getFromQ() != 0f ||
					br.getToQ() != 0f);
			
		}
		
		/**
		 * Return an array, in bus order, of which buses have available
		 * injection measurements.
		 * 
		 * @return
		 * @throws PAModelException
		 */
		boolean[] findAvailableInjections() throws PAModelException
		{
			BusList buses = _bri.getBuses();
			int nbus = buses.size();
			boolean[] bh = new boolean[nbus];
			for(Bus b : buses)
			{
				int bx = b.getIndex();
				if (testInjections(b))
					bh[bx] = true;
			}
			return bh;
		}
		
		/** 
		 * Currently, we don't have a measurement state in OpenPA.  So, we are going to say
		 * that a 0 value for an energized device is not metered.
		 * @param b
		 * @return
		 * @throws PAModelException
		 */
		boolean testInjections(Bus b) throws PAModelException
		{
			boolean rv = false;
			/* a de-energized bus is 0-injection, so include it */
			if(!b.getIsland().isEnergized())
				rv = true;
			else
				rv = testGenInj(b) && testSvcInj(b) && testLoadInj(b);
			
			_dbg.initInjection(b.getIndex(), rv);
			return rv;
			
		}

		/**
		 * Test if the bus has injections from Loads 
		 * @param b Bus to check for load injections
		 * @return
		 * @throws PAModelException
		 */
		private boolean testLoadInj(Bus b) throws PAModelException
		{
			for(Load l : SubLists.getLoadInsvc(b.getLoads()))
				if (l.getP() == 0f || l.getQ() == 0f) return false;

			return true;
		}

		/**
		 * Test if the bus has injections from SVC's
		 * @param b Bus to check for load injections
		 * @return
		 * @throws PAModelException
		 */
		private boolean testSvcInj(Bus b) throws PAModelException
		{
			for(SVC s : SubLists.getSVCInsvc(b.getSVCs()))
				if (s.getQ() == 0f) return false;
			return true;
		}

		/**
		 * Test if the bus has injections from Generators
		 * @param b Bus to check for load injections
		 * @return
		 * @throws PAModelException
		 */
		private boolean testGenInj(Bus b) throws PAModelException
		{
			for(Gen g : SubLists.getGenInsvc(b.getGenerators()))
			{
				boolean r = true;
				switch(g.getMode())
				{
					case OFF: continue;
					case CON: r &= (g.getQ() != 0f);
					break;
					default: 
						r &= (g.getP() != 0f) || (g.getQ() != 0f);

				}
				if(!r) return false;
			}
			return true;
		}

		/**
		 * Construct the index (ties buses to observable island). This is the
		 * actual point at which observability will be determined. The returned
		 * index maps bus offsets to an observable island.
		 * 
		 * @return
		 * @throws PAModelException
		 */
		abstract GroupIndex constructIndex() throws PAModelException;
	}

	/**
	 * Completes the implementation of the DOE algorithm {@link ObsIslandBldr}
	 * @author chris@powerdata.com
	 *
	 */
	static class ObsIslandBldrDOE extends ObsIslandBldr
	{
		
		ObsIslandBldrDOE(PAModel model, Debug dbg) throws PAModelException
		{
			super(model, dbg);
		}

		/** ObsIsle object in bus-order */
		ObsIsle[] _bus2Island;
		/** remaining available injections */
		Set<Bus> _availableInj = new HashSet<>();
		/** remaining unknown branches */
		Set<ACBranch> _unknownBranch = new HashSet<>();
		/** Current set of ObjIsle's */
		Set<ObsIsle> _obsIslands = new HashSet<>();
		
		
		/**
		 * represetation of an ObservableIsland during construction of the list.
		 * 
		 * @author chris@powerdata.com
		 *
		 */
		class ObsIsle extends HashSet<Bus>
		{
			private static final long serialVersionUID = 1L;

			/** Pick a token bus that can be used for identification */
			Bus _token;
			
			/** Create a new Observable Island with a single bus */
			public ObsIsle(Bus b) throws PAModelException
			{
				add(b);
				_token = b;
			}

			/** Merge another obsverable island into ours */
			public ObsIsle merge(ObsIsle src)
			{
				addAll(src);
				for(Bus b : src)
					_bus2Island[b.getIndex()] = this;
				_obsIslands.remove(src);
				return this;
			}

			public String getID() throws PAModelException
			{
				return _token.getID();
			}
			
			public int getIndex() {return _token.getIndex();}
			
			public String toString() {try
			{
				return _token.getID();
			}
			catch (PAModelException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}}

			@Override
			public boolean equals(Object o)
			{
				return o.hashCode() == hashCode();
			}

			@Override
			public int hashCode()
			{
				return _token.hashCode();
			}
		}

		/** Run the DOE observability algorithm and map each bus to it's island */
		@Override
		GroupIndex constructIndex() throws PAModelException
		{
			/** List of buses in single-bus view */
			BusList buses = _bri.getBuses();
			/** Number of "single" buses */
			int nbus = buses.size();
			_bus2Island = new ObsIsle[nbus];

			for(int i=0; i < nbus; ++i)
			{
				Bus b = buses.get(i);
				/* create a new ObjIsle for each bus */
				ObsIsle oi = new ObsIsle(b);
				/* track each island by bus order */
				_bus2Island[i] = oi;
				/* track the new island */
				_obsIslands.add(oi);
				/*
				 * test for injection measurements and if present, add to the
				 * available injections set
				 */
				if(testInjections(b)) _availableInj.add(b);
			}
			
			initializeBranches();
			
			extendMeasurements();
			
			/*
			 * Observability is now complete. Create the map used to construct
			 * the full Observable Island list. For each bus, indicate which
			 * objservable island it belongs to. A value of -1 in the map array
			 * means that a bus is not in any island.
			 */
			int[] map = new int[nbus];
			Arrays.fill(map,  -1);
			int ibus = 0;
			for(ObsIsle objisle : _obsIslands)
			{
				for(Bus b : objisle)
					map[b.getIndex()] = ibus;
				++ibus;
			}
			
			/*
			 * BasicGroupIndex is the means by which we communicate the bus ->
			 * island mapping to the group list parent
			 */
			return new BasicGroupIndex(map, ibus);
		}


		/**
		 * Run the measurement extension step of the DOE algorithm 
		 *
		 * @throws PAModelException
		 */
		void extendMeasurements() throws PAModelException
		{
			BusList sbus = _bri.getBuses();
			int nUnkBr = -1;
			int nInj = -1;
			while (nUnkBr != _unknownBranch.size()
					|| nInj != _availableInj.size())
			{
				nUnkBr = _unknownBranch.size();
				nInj = _availableInj.size();
				/*
				 * DOE step 1 loop through the unknown branches and check for the
				 * same island on each side
				 */
				Iterator<ACBranch> bri = _unknownBranch.iterator();
				while (bri.hasNext())
				{
					ACBranch b = bri.next();
					/** from-side bus in the single-bus topology */
					Bus f = sbus.getByBus(b.getFromBus());
					/** to-side bus in sngle-bus topology */
					Bus t = sbus.getByBus(b.getToBus());
					ObsIsle fi = _bus2Island[f.getIndex()];
					if (fi == _bus2Island[t.getIndex()])
					{
						bri.remove();
						_dbg.step1KnownBranch(b.hashCode(), fi.getIndex());
					}
				}
				
				/* step 2 loop through available injections and try to apply */
				Iterator<Bus> busi = _availableInj.iterator();
				while (busi.hasNext())
				{
					Bus bus = busi.next();
					ObsIsle rmt = testUnknownBranches(bus);
					if (rmt != null)
					{
						ObsIsle di = getObsIsle(bus);
						if (rmt != di)
						{
							rmt.merge(di);
							_dbg.step2Merge(bus.getIndex(), rmt.getIndex(), di.getIndex());
							busi.remove();
						}
					}
				}
				
			}
		}	
		
		/**
		 * As part of step 2, we need to know if all the branches incident to a
		 * bus have their remote side in the same island.
		 * 
		 * @param bus
		 *            Bus to evaluate
		 * @return the ObsIsle of the remote sides of the branches, or else null
		 *         if the test failed.
		 * @throws PAModelException
		 */
		ObsIsle testUnknownBranches(Bus bus) throws PAModelException
		{
			/** remote side of first branch */
			ObsIsle rmti = null;
			for (ACBranchList brlist : bus.getACBranches())
			{
				for (ACBranch b : brlist)
				{
					if (_unknownBranch.contains(b))
					{
						/*
						 * Model elements have references to connectivity buses.
						 * We need to find the appropriate "single" bus
						 */
						Bus f = _bri.getByBus(b.getFromBus());
						Bus t = _bri.getByBus(b.getToBus());
						/** remote or "other" bus for this branch */
						Bus rb = (f.equals(bus)) ? t : f;
						/** observable island of remote bus for this branch */
						ObsIsle rbi = _bus2Island[rb.getIndex()];
						/*
						 * Set the remote side of the first branch so we can
						 * compare the remaining branches
						 */
						if (rmti == null)
							rmti = rbi;
						/*
						 * if any one of the branch far sides does not match the
						 * first one, then the test failed.
						 */
						else if (!rmti.equals(rbi)) return null;
					}
				}
			}
			return rmti;
		}		
		/**
		 * Performs the "initialize branches" phase of the DOE algorithm.
		 * 
		 * @throws PAModelException
		 */
		void initializeBranches() throws PAModelException
		{
			/** restrict branches to those in-service */
			Collection<ACBranchList> l = SubLists.getBranchInsvc(_model.getACBranches());
			for(ACBranchList blist : l)
			{
				for(ACBranch br : blist)
				{
					if (isBranchMetered(br))
					{
						ObsIsle dest = getObsIsle(br.getFromBus());
						ObsIsle src = getObsIsle(br.getToBus());
						if (!dest.equals(src))
						{
							dest.merge(src);
							_dbg.trackBranch(br.hashCode(), br);
							_dbg.initMeasuredBranch(br.hashCode(), dest.getIndex(),
								src.getIndex());
						}
					}
					else
					{
						Bus fbus = _bri.getByBus(br.getFromBus());
						Bus tbus = _bri.getByBus(br.getToBus());
						Bus inj = null;
						if(_availableInj.contains(fbus)) inj = fbus;
						else if (_availableInj.contains(tbus)) inj = tbus;
						_dbg.trackBranch(br.hashCode(), br);
						_dbg.initUnknownBranch(br.hashCode(), inj.getIndex());
						if(inj != null)
							_unknownBranch.add(br);
					}
				}
			}
		}

		ObsIsle getObsIsle(Bus bus) throws PAModelException
		{
			return _bus2Island[_bri.getByBus(bus).getIndex()];
		}
		
	}
	
	/**
	 * <ul>
	 * This is a work in progress, and attempts to make use of some existing OpenPA tools as
	 * possible optimizations:
	 * <li>superimpose both the known branches and unknown branches within the
	 * same {@link LinkNet} object, utilizing LinkNet's ability to eliminate
	 * branches</li>
	 * <li>Use the minimum order utility {@link BusConnectionsPriQ} to
	 * (hopefully) make better use of injection measurements and operate on
	 * smaller numbers of branches</li>
	 * </ul>
	 * 
	 * @author chris@powerdata.com
	 *
	 */
	static class ObsIslandBldrNew extends ObsIslandBldr
	{
		
		ObsIslandBldrNew(PAModel model, Debug dbg) throws PAModelException
		{
			super(model, dbg);
		}

		@Override
		GroupIndex constructIndex() throws PAModelException
		{
			boolean[] availInj = findAvailableInjections();
			ConcurrentGroupLinkNet onet = new ConcurrentGroupLinkNet();
			BusConnectionsPriQ que = initBranches(onet, availInj);
			extendMeasurements(onet, que);
			LinkNet.GroupMap gm = onet.findGroupMap();
			return new BasicGroupIndex(gm.getBusIslandMap(), gm.getIslandCount());
		}
		
		void extendMeasurements(ConcurrentGroupLinkNet onet, BusConnectionsPriQ q)
		{
			BusFunction bf = b ->
			{
				boolean rv = false;
				/*
				 * This method is called for each bus in the que. If any incident unknown
				 * branch has the other end in our same island, then they can be
				 * immediately made "known" by rule 1. If the rest of the
				 * branches are in the same island (but not ours), then a merge
				 * occurs
				 */				
				
				/** our island */
				int ourisland = onet.getGroup(b);
				int adjisl = -1;
				int[][] conn = onet.findElimConnections(b);
				 /** connected nodes */
				int[] cnd = conn[0];
				/** connected branches */
				int[] cbr = conn[1];
				int nconn = cnd.length;
				/** number of branches already in our island */
				int nFalseUnknown = 0;
				
				/** a small simple stack */
				int[] stack = new int[nconn];
				int sp = 0;
				
				for(int i=0; i < nconn; ++i)
				{
					/** connected (far side) node */
					int c = cnd[i];
					/** island of connected node */
					int ci = onet.getGroup(c);
					if (ci == ourisland)
					{
						/*
						 * In this case, it's already in our island, so
						 * mark the branch as known (no merge is triggered)
						 */
						int br = cbr[i];
						onet.restoreBranch(br);
						_dbg.step1KnownBranch(br, ci);
						++nFalseUnknown;
					}
					else
					{
						if (adjisl == -1)
							adjisl = ci;
						if (adjisl == ci)
							stack[sp++] = i;
					}
				}

				/**
				 * if all the unknown branches have far-side nodes in the same
				 * island, then merge
				 */
				if ((nconn - nFalseUnknown) == sp)
				{
					for(int i=0; i < sp; ++i)
					{
						int sv = stack[i];
						_dbg.step2Merge(b, ourisland, onet.getGroup(cnd[sv]));
						onet.restoreBranch(cbr[sv]);
						q.dec(b);
						q.dec(cnd[sv]);
					}
					rv = true;
				}
				
				return rv;
			};
			
			/*
			 * the forEach call applies the function to each bus in priority
			 * order. When forEach returns true, it means we merged an island,
			 * and we should do it again. When it returns false, that means we
			 * looked at each available injection bus and could not apply any of
			 * them.
			 * 
			 * We should consider for performance in this loop. For example, if
			 * we merge, we kick out of forEach. Instead, maybe we should
			 * try to operate on the rest of the list similar to DOE. This should be
			 * tested, but for now we think that:
			 *
			 * 1. We have a higher chance of getting more buses with single
			 * unknown branches when we go back to the head of the queue.  
			 * 
			 * 2. Further research & testing is probably required, but it
			 * appears as if there are three cases to consider about subsequent
			 * injections in the same pass:
			 * 
			 * 2a) available injection that is already in the same island. There
			 * is no work required, so no performance difference.
			 * 
			 * 2b) available injection in an unrelated island. Order should not
			 * present any issues for unrelated islands.
			 * 
			 * 2c) avaliable injection in an "adjacent" island where our island
			 * size would grow. Because the ConcurrentGroupLinkNet picks the
			 * smaller island to roll into the larger, there is nothing to gain
			 * by trying to look for adjacencies in a single pass
			 * 
			 */
			while(q.forEach(bf));
			
		}
		
		
		BusConnectionsPriQ initBranches(ConcurrentGroupLinkNet onet, boolean[] availInj) throws PAModelException
		{
			List<ACBranchList> brset = SubLists.getBranchInsvc(_model.getACBranches());
			int nbr = brset.stream().mapToInt(i -> i.size()).sum();
			int nbus = _bri.getBuses().size();
			onet.ensureCapacity(nbus-1, nbr);
			onet.addBuses(0,  nbus);
			
			for(ACBranchList list : brset)
			{
				BusRefIndex.TwoTerm tt = _bri.get2TBus(list);
				int[] fn = tt.getFromBus(), tn = tt.getToBus();
				int n = list.size();
				for(int i=0; i < n; ++i)
				{
					ACBranch br = list.get(i);
					boolean ismetered = isBranchMetered(br);
					int f = fn[i], t = tn[i];
					int brid = onet.findBranchElim(f, t);
					int fg = onet.getGroup(f), tg = onet.getGroup(t);
					Bus injbus = (availInj[f]) ? br.getFromBus() : (availInj[t]?br.getToBus():null);
					/*
					 * If the branch id is less than -1, then it is an "unknown"
					 * branch.  See if the new one is telemetered and if so upgrade it.  If
					 * not, just ignore it.
					 */
					switch(calcState(brid))
					{
						case NotFound:
							if (ismetered)
							{
								//TODO:  
								brid = onet.addBranch(f, t);
								_dbg.trackBranch(brid, br);
								_dbg.initMeasuredBranch(brid, fg, tg);
							}
							else if (availInj[f] || availInj[t])
							{
								brid = onet.addBranchEliminated(f, t);
								_dbg.trackBranch(brid, br);
								_dbg.initUnknownBranch(brid, injbus.getIndex());
							}
							break;
						case Unknown:
							if(ismetered)
							{
								onet.restoreBranch(brid);
								throw new UnsupportedOperationException("Not yet tested");
							}
							break;
						default:
					}
				}
			}
			
			return new BusConnectionsPriQ(onet.getElimConnectionCounts());
			
		}
		
		BranchState calcState(int brid)
		{
			if (brid == -1) return BranchState.NotFound;
			if(brid < -1) return BranchState.Unknown;
			return BranchState.Known;
		}
		
	}
	
	public static void main(String...args) throws Exception
	{
		String uri = null;
		File poutdir = new File(System.getProperty("user.dir"));
		for(int i=0; i < args.length;)
		{
			String s = args[i++].toLowerCase();
			int ssx = 1;
			if (s.startsWith("--")) ++ssx;
			switch(s.substring(ssx))
			{
				case "uri":
					uri = args[i++];
					break;
				case "outdir":
					poutdir = new File(args[i++]);
					break;
			}
		}
		if (uri == null)
		{
			System.err.format("Usage: -uri model_uri "
					+ "[ --outdir output_directory (deft to $CWD ]\n");
			System.exit(1);
		}
		final File outdir = poutdir;
		if (!outdir.exists()) outdir.mkdirs();
		PAModel model = PflowModelBuilder.Create(uri).load();

		/*
		 * Print out a list of observable islands
		 */
		ObservabilityDebugReport report = new ObservabilityDebugReport(outdir, model);
		ObservableIslandList olist = new ObservableIslandList(model, report);
		ListDumper ld = new ListDumper(); 
		ld.dumpList(new File(outdir, "ObservableIsland.csv"), olist);
		
		/*
		 * Print out the buses under each observable island
		 */
		printBusDetail(olist, outdir);
		
		report.close();
	}

	/**
	 * Use this class to perform detailed tracing and debugging of the algorithms.
	 * 
	 * @author chris@powerdata.com
	 *
	 */
	static class ObservabilityDebugReport implements Debug
	{
		enum Report {Injection, InitMeasBranch, InitUnknownBranch, Log;}
		static Map<Report,String> _Hdr = new EnumMap<>(Report.class);
		Map<Report,PrintWriter> _map = new EnumMap<>(Report.class);
		PAModel _model;
		BusList _sbus;
		TIntObjectHashMap<ACBranch> _brmap = new TIntObjectHashMap<>();
		
		static
		{
			_Hdr.put(Report.Injection, "Bus,Valid");
			_Hdr.put(Report.InitMeasBranch, "ID");
			_Hdr.put(Report.InitUnknownBranch, "Branch,InjectionBus");
			_Hdr.put(Report.Log, "Message");
		}
		
		public ObservabilityDebugReport(File dir, PAModel m) throws IOException, PAModelException
		{
			_model = m;
			_sbus = m.getSingleBus();
			for(Report r : Report.values())
			{
				PrintWriter w = new PrintWriter(new BufferedWriter(
					new FileWriter(new File(dir, r.toString()+".csv"))));
				w.println(_Hdr.get(r));
				_map.put(r, w);
			}
		}

		@Override
		public void initInjection(int busndx, boolean valid)
		{
			PrintWriter pw = _map.get(Report.Injection);
			try
			{
				pw.format("%s,%s\n",
					_sbus.get(busndx).getID(), String.valueOf(valid));
			}
			catch (PAModelException e)
			{
				e.printStackTrace(pw);
			}
		}

		@Override
		public void initMeasuredBranch(int branch, int destisl, int srcisl)
		{
			String bid;
			PrintWriter im = _map.get(Report.InitMeasBranch);
			try
			{
				bid = _brmap.get(branch).getID();
				im.format("%s\n", bid);
				_map.get(Report.Log).format("Merge island %s into %s for measured branch %s\n",
					_sbus.get(srcisl).getID(), _sbus.get(destisl).getID(), bid);
			}
			catch (PAModelException e)
			{
				e.printStackTrace(im);
			}
		}

		@Override
		public void initUnknownBranch(int brcode, int bus)
		{
			PrintWriter pw = _map.get(Report.InitUnknownBranch);
			try
			{
				String brid;
				brid = _brmap.get(brcode).getID();
				pw.format("%s,%s\n",
					brid, (bus == -1) ? "No Injection" : _sbus.get(bus).getID());
			}
			catch (PAModelException e)
			{
				e.printStackTrace(pw);
			}
		}

		@Override
		public void step1KnownBranch(int branch, int obsisle)
		{
			PrintWriter pw = _map.get(Report.Log); 
			try
			{
				pw.format("Recognize branch %s in island %s by step 1\n",
					_brmap.get(branch).getID(), _sbus.get(obsisle).getID());
			}
			catch (PAModelException e)
			{
				e.printStackTrace(pw);
			}
		}

		@Override
		public void step2Merge(int b, int destisl, int srcisl)
		{
			PrintWriter pw = _map.get(Report.Log); 
			try
			{
				pw.format(
					"Merge island %s into %s by step 2 at bus %s\n",
					_sbus.get(srcisl).getID(), _sbus.get(destisl).getID(), _sbus.get(b).getID());
			}
			catch (PAModelException e)
			{
				e.printStackTrace(pw);
			}
		}
		
		public void close()
		{
			_map.values().forEach(i -> i.close());
		}

		@Override
		public void trackBranch(int brkey, ACBranch branch) throws PAModelException
		{
			_brmap.put(brkey, branch);
		}

	}
	
	private static void printBusDetail(ObservableIslandList olist, File outdir)
			throws IOException, PAModelException
	{
		PrintWriter bdw = new PrintWriter(new BufferedWriter(
				new FileWriter(new File(outdir, "oibusdetail.csv"))));
		bdw.println("IslandID,BusID,BusName");
		for (ObservableIsland i : olist)
		{
			/*
			 * Find out if the island has 
			 */
			for (Bus b : i.getBuses())
			{
				bdw.format("%s,%s,%s\n",
					i.getID(), b.getID(), b.getName());
			}
		}
		bdw.close();
	}
}

