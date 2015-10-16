package com.powerdata.openpa.se;

import gnu.trove.set.TIntSet;
import gnu.trove.set.TLongSet;
import gnu.trove.set.hash.TIntHashSet;
import gnu.trove.set.hash.TLongHashSet;
import java.util.Set;
import com.powerdata.openpa.*;
import com.powerdata.openpa.tools.LinkNet;
import com.powerdata.openpa.tools.EqType;

public class ObservableIslandList extends GroupListI<com.powerdata.openpa.se.ObservableIslandList.ObservableIsland>
{
	
	public ObservableIslandList(PAModel model) throws PAModelException
	{
		super(model, new ObsIslandBldr(model).constructIndex());
	}
	
	public class ObservableIsland extends Group
	{
		public ObservableIsland(int ndx)
		{
			super(ObservableIslandList.this, ndx);
		}
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
	 * <p>
	 * Build observable islands based on the DOE algorithm, based on
	 * "Contribution to Power State Estimation and Transient Stability Analysis"
	 * , Feb 1984
	 * </p><p>
	 * OpenPA does not yet support telemetry state (metered versus non-metered measurements), so we use the following for now:
	 * 
	 * <ul><li>out-of-service injections count as known</li>
	 * <li>in-service units with an "OFF" mode count as known</li>
	 * <li>synchronous condensers with MVAr != 0 are known</li>
	 * <li>all others require MW != 0 and MVAr != 0</li>
	 * <li>fixed shunts are known if there is a known voltage</li>
	 * </ul></p>
	 * <p>
	 * Items that we don't handle:
	 * 
	 * <ul><li>We don't worry yet about "preferred solution" variables (either taps or buses)</li>
	 * <li> We don't yet handle tap branches</li>
	 * </ul>
	 * </p>
	 * @author chris@powerdata.com
	 *
	 */
	static class ObsIslandBldr
	{
		PAModel _m;
		BusRefIndex _bri;
		ObsIslandBldr(PAModel model) throws PAModelException
		{
			_m = model;
			_bri = BusRefIndex.CreateFromSingleBuses(model);
		}
		
		GroupIndex constructIndex() throws PAModelException
		{
			TIntSet availInj = findAvailableInjections();
			LinkNet onet = new LinkNet();
			TLongSet unkBr = initBranches(onet); 
			
			
			return null;
		}
		
		TLongSet initBranches(LinkNet onet) throws PAModelException
		{
			Set<ACBranchList> brset = SubLists.getBranchInsvc(_m.getACBranches());
			int nbr = brset.stream().mapToInt(i -> i.size()).sum();
			int nbus = _bri.getBuses().size();
			TLongHashSet rv = new TLongHashSet(nbr);
			onet.ensureCapacity(nbus-1, nbr);
			onet.addBuses(0,  nbus);
			
			for(ACBranchList list : brset)
			{
				BusRefIndex.TwoTerm tt = _bri.get2TBus(list);
				int[] f = tt.getFromBus(), t = tt.getToBus();
				int n = list.size();
				for(int i=0; i < n; ++i)
				{
					ACBranch br = list.get(i);
					if ((br.getFromP() != 0f && br.getFromQ() != 0f)
						|| (br.getToP() != 0f && br.getToQ() != 0f))
					{
						if (onet.findBranch(f[i], t[i]) == -1)
							onet.addBranch(f[i], t[i]);
					}
					else
					{
						rv.add(EqType.GetID(br));
					}
				}
			}
			
			return rv;
		}
		
		TIntSet findAvailableInjections() throws PAModelException
		{
			BusList buses = _m.getSingleBus();
			TIntHashSet bh = new TIntHashSet(buses.size());
			for(Bus b : buses)
			{
				if (testInjections(b))
					bh.add(b.getIndex());
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
			/* a de-energized bus is 0-injection, so include it */
			if(!b.getIsland().isEnergized()) return true;
			/* if there are no devices that can inject, then it's 0-injection as well */
			return testGenInj(b) && testSvcInj(b) && testLoadInj(b);
			
		}

		private boolean testLoadInj(Bus b) throws PAModelException
		{
			for(Load l : SubLists.getLoadInsvc(b.getLoads()))
				if (l.getP() == 0f || l.getQ() == 0f) return false;

			return true;
		}

		private boolean testSvcInj(Bus b) throws PAModelException
		{
			for(SVC s : SubLists.getSVCInsvc(b.getSVCs()))
				if (s.getQ() == 0f) return false;
			return true;
		}

		private boolean testGenInj(Bus b) throws PAModelException
		{
			for(Gen g : SubLists.getGenInsvc(b.getGenerators()))
			{
				boolean r = true;
				switch(g.getMode())
				{
					case OFF: continue;
					default: r &= (g.getP() != 0f);
					case CON: r &= (g.getQ() != 0f);
				}
				if(!r) return false;
			}
			return true;
		}
	}
	
}
