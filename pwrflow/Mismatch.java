package com.powerdata.openpa.pwrflow;

import java.util.AbstractCollection;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import com.powerdata.openpa.Bus;
import com.powerdata.openpa.BusList;
import com.powerdata.openpa.BusRefIndex;
import com.powerdata.openpa.ElectricalIsland;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.tools.PAMath;

/**
 * Manage mismatches
 * 
 * 
 * @author chris@powerdata.com
 *
 */
public class Mismatch
{
	public enum Status
	{
		OK, BlowsUp, RefOnly, Null;
	}

	public interface WorstMM
	{
		Status getStatus();
		float getValue();
		Bus getBus();
		String toString(String unit);
	}
	
	static class BasicWorstMM implements WorstMM
	{
		Status _s;
		float _wval;
		Bus _wbus;
		BasicWorstMM(float wval, Bus wbus)
		{
			_s = Status.OK;
			_wval = wval;
			_wbus = wbus;
		}
		BasicWorstMM(Status s, float wval, Bus wbus)
		{
			_s = s;
			_wval = wval;
			_wbus = wbus;
		}
		@Override
		public Status getStatus() {return Status.OK;}
		@Override
		public float getValue() {return _wval;}
		@Override
		public Bus getBus() {return _wbus;}

		public String toString(String unit)
		{
			try
			{
				return String.format("%s %f %s", _wbus.getName(), 
					PAMath.pu2mva(_wval, 100f), unit);
			}
			catch (PAModelException e)
			{
				e.printStackTrace();
			}
			return "";
		}
	}
	
	static class NoBusMM implements WorstMM
	{
		@Override
		public Status getStatus() {return Status.RefOnly;}
		@Override
		public float getValue() {return 0f;}
		@Override
		public Bus getBus() {return null;}
		@Override
		public String toString(String unit)
		{
			return "No Comparable Buses [reference only]";
		}
		
	}
	
	public static final WorstMM NullMM = new WorstMM(){

		@Override
		public Status getStatus() {
			return Status.Null;
		}

		@Override
		public float getValue() {
			return 0;
		}

		@Override
		public Bus getBus() {
			return null;
		}

		@Override
		public String toString(String unit) {
			return "No mismatch";
		}};
	
	float[] _mm;
	BusRefIndex _bri;
	BusTypeUtil _btu;
	Collection<BusType> _bustypes;
	
	
	/**
	 * Create new mismatch.
	 * @param nbus number of buses to manage
	 */
	public Mismatch(BusRefIndex bri, BusTypeUtil btu,
			Collection<BusType> bustypes)
	{
		_bri = bri;
		_mm = new float[bri.getBuses().size()];
		_bustypes = bustypes;
		_btu = btu;
	}	
	/**
	 * Return the mismatch at a bus (index)
	 * @param ndx Bus index for mismatch value
	 * @return Mismatch value
	 */
	public float get(int ndx) {return _mm[ndx];}
	/**
	 * Access the entire mismatch array
	 * 
	 * @return mismatch array
	 */
	public float[] get() {return _mm;}
	
	public void add(int ndx, float flow) {_mm[ndx] += flow;}
	
	/**
	 * Find the worst mismatch for the set of buses configured
	 * @return worst mismatch value
	 */
	public WorstMM test(ElectricalIsland i)
	{
		float wval = 0f;
		int wndx = -1;
		BusList buses = _bri.getBuses();
		for(BusType btype : _bustypes)
		{
			for(int b : _btu.getBuses(btype, i))
			{
				float v = _mm[b];
				if (!Float.isFinite(v))
				{
					return new BasicWorstMM(Status.BlowsUp, 0, buses.get(b));
				}
				
				if (Math.abs(wval) < Math.abs(v) || wndx == -1)
				{
					wval = v;
					wndx = b;
				}
			}
		}
		if (wndx == -1)
		{
			return new NoBusMM();
		}
		return new BasicWorstMM(wval, _bri.getBuses().get(wndx));
	}
	
	/**
	 * Get the bus types configured for testing 
	 */
	public Collection<int[]> getTestBuses(ElectricalIsland i)
	{
		return new AbstractCollection<int[]>()
		{
			@Override
			public Iterator<int[]> iterator()
			{
				return new Iterator<int[]>()
				{
					Iterator<BusType> bti = _bustypes.iterator();
					
					@Override
					public boolean hasNext()
					{
						return bti.hasNext();
					}

					@Override
					public int[] next()
					{
						return _btu.getBuses(bti.next(), i);
					}};
			}

			@Override
			public int size()
			{
				return _bustypes.size();
			}
		};
	}
	
	public BusRefIndex getBusRefIndex() {return _bri;}
	@Override
	public String toString()
	{
		return Arrays.toString(_mm);
	}
	
	/**
	 * Reset (set to 0) the mismatches
	 */
	public void reset()
	{
		Arrays.fill(_mm, 0f);
	}
	
	
}
