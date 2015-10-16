package com.powerdata.openpa.pwrflow;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import gnu.trove.list.array.TIntArrayList;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.map.hash.TObjectIntHashMap;
import com.powerdata.openpa.Bus;
import com.powerdata.openpa.GroupIndex;
import com.powerdata.openpa.BusList;
import com.powerdata.openpa.Gen;
import com.powerdata.openpa.GenList;
import com.powerdata.openpa.ElectricalIsland;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.SVC;
import com.powerdata.openpa.impl.BasicGroupIndex;
import com.powerdata.openpa.pwrflow.ConvergenceList.ConvergenceInfo;


/**
 * Monitor "remote" bus voltage and take action
 * 
 * @author chris@powerdata.com
 *
 */
public class VoltageSetPoint
{
	/** All system buses */
	BusList _sysbuses;
	/** just the buses originally configured as PV buses */
	BusList _pvbuses;
	/** voltage setpoints in order parallel with _pvbuses */
	float[] _vsp;
	
	/** remote regulated buses */
	int[] _rmtregbus;
	/** map _rmtregbus to set of local PV buses */
	GroupIndex _rmtregmap;
	/** map islands to _rmtregbus */
	GroupIndex _rmtregbyisl;
	/** remote setpoints */
	float[] _rmtvsp;
	
	/** only monitor remote buses if the var mismatch is under this limit */
	float _qtol = 0.05f;

	public VoltageSetPoint(BusList pvbuses, BusList netbuses, int nIslands) throws PAModelException
	{
		_sysbuses = netbuses;
		_pvbuses = pvbuses;
		int npv = pvbuses.size();
		_vsp = new float[npv];
		Set<Gen> rgens = new HashSet<>();
//		TIntArrayList genpvndx = new TIntArrayList();
		
		for(int i=0; i < npv; ++i)
		{
			Bus b = pvbuses.get(i);
			float vsp = 0f;//, vsr = 0f;
			int ngen = 0;//, ngr = 0;
			for(Gen g : b.getGenerators())
			{
				if(g.unitInAVR())
				{
					Bus regbus = g.getRegBus();
					boolean rmt = !regbus.equals(b);
					float v = 1f;
					if (rmt)
						rgens.add(g);
					else
						v = g.getVS() / regbus.getVoltageLevel().getBaseKV();
					
					vsp += v;
					++ngen;
				}
			}
			for(SVC c : b.getSVCs())
			{
				if (c.isInService() && c.isRegKV() && c.getSlope() == 0f)
				{
					Bus regbus = c.getRegBus();
					float v = c.getVS() / regbus.getVoltageLevel().getBaseKV();
					vsp += v;
					++ngen;
				}
			}
			_vsp[i] = vsp/((float) ngen);
		}
		
		//TODO: restore this, buggy when no regulated bus specified
//		processRemote(rgens, nIslands);
	}
	
	static class RmtVsp
	{
		int n = 0;
		float vs = 0;
//		RmtVsp(float vs)
//		{
//			this.vs = vs;
//			n = 1;
//		}
		void add(float vs)
		{
			this.vs += vs;
			++n;
		}
		float avg() {return vs / ((float) n);}
	}
	
	void processRemote(Set<Gen> rgens, int nIslands) throws PAModelException
	{
		int npv = _pvbuses.size();
		TIntArrayList rmtregbus = new TIntArrayList();
		TIntArrayList rmtregbyisl = new TIntArrayList();
		TIntObjectMap<RmtVsp> rmtvsp = new TIntObjectHashMap<>();
		TObjectIntMap<Bus> rbmap = new TObjectIntHashMap<>(100, 0.49f, -1);
		int[] rmtregmap = new int[npv];
		
		for(int i=0; i < npv; ++i)
		{
			Bus pv = _pvbuses.get(i);
			boolean added = false;
			GenList gens = pv.getGenerators();
			for(Gen g : gens)
			{
				if (rgens.contains(g))
				{
					Bus rb = _sysbuses.getByBus(g.getRegBus());
					int ix = rbmap.putIfAbsent(rb, rmtregbus.size());
					if (ix == -1)
					{
						ix = rmtregbus.size();
						rmtregbus.add(rb.getIndex());
						rmtregbyisl.add(rb.getIsland().getIndex());
					}
					if(!added)
					{
						added = true;
						rmtregmap[i] = ix;
					}
					RmtVsp rvsp = rmtvsp.get(ix);
					if (rvsp == null)
					{
						rvsp = new RmtVsp();
						rmtvsp.put(ix, rvsp);
					}
					rvsp.add(g.getVS()/rb.getVoltageLevel().getBaseKV());
				}
			}
			
		}
		_rmtregbus = rmtregbus.toArray();
		int nrmt = rmtregbus.size();
		_rmtregmap = new BasicGroupIndex(rmtregmap, nrmt);
		_rmtregbyisl = new BasicGroupIndex(rmtregbyisl.toArray(), nIslands);
		_rmtvsp = new float[nrmt];
		for(int i=0; i < nrmt; ++i)
		{
			_rmtvsp[i] = rmtvsp.get(i).avg();
		}
		
	}
	
	public interface SetPoint
	{
		Bus getBus();
		float getVS();
	}
	
	Collection<SetPoint> _spres = new AbstractCollection<SetPoint>()
	{
		@Override
		public Iterator<SetPoint> iterator()
		{
			return new Iterator<SetPoint>()
			{
				private int i = 0, n = _vsp.length;
				@Override
				public boolean hasNext()
				{
					return i < n;
				}

				@Override
				public SetPoint next()
				{
					return new SetPoint()
					{
						int ndx = i++;
						@Override
						public Bus getBus() {return _pvbuses.get(ndx);}
						@Override
						public float getVS() {return _vsp[ndx];}
					};
				}
			};
		}

		@Override
		public int size()
		{
			return _vsp.length;
		}
	};

	/**
	 * Apply full set of setpoints to voltage working array
	 * @param vm Array to apply setpoints
	 */
	public void applyToVMag(float[] vm)
	{
		int nrmt = _vsp.length;
		for(int i=0; i < nrmt; ++i)
			vm[_pvbuses.get(i).getIndex()] = _vsp[i];
	}

	/**
	 * Monitor remote buses, and adjust PV bus setpoints accordingly.
	 * 
	 * Only perform adjustments if the worst mismatch has settled down
	 * 
	 * @param vm Working voltages
	 */
	public void applyRemotes(float[] vm, ConvergenceList clist)
	{
		for(ConvergenceInfo ci : clist)
		{
			if (!ci.completed() &&  Math.abs(ci.getWorstQ().getValue()) < _qtol)
				applyIslandRemote(vm, ci.getIsland());
		}
	}

	void applyIslandRemote(float[] vm, ElectricalIsland island)
	{
		int[] ibus = _rmtregbyisl.map().get(island.getIndex());
		for(int rb : ibus)
			applyRemote(rb, vm);
	}

	void applyRemote(int rbx, float[] vm)
	{
		int[] lbus = _rmtregmap.map().get(rbx);
		int rb = _rmtregbus[rbx];
		float diff = _rmtvsp[rbx] - vm[rb];
		for(int b : lbus)
		{
			_vsp[b] += diff;
			vm[_pvbuses.get(b).getIndex()] = _vsp[b];
		}
	}

}
