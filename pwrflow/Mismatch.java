package com.powerdata.openpa.pwrflow;

import java.util.AbstractCollection;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import com.powerdata.openpa.Bus;
import com.powerdata.openpa.Island;
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
		OK, BlowsUp;
	}

	public static class WorstMM
	{
		Status _s;
		float _wval;
		Bus _wbus;
		WorstMM(float wval, Bus wbus)
		{
			_s = Status.OK;
			_wval = wval;
			_wbus = wbus;
		}
		WorstMM(Status s, float wval, Bus wbus)
		{
			_s = s;
			_wval = wval;
			_wbus = wbus;
		}
		public Status getStatus() {return Status.OK;}
		public float getValue() {return _wval;}
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
	public WorstMM test(Island i)
	{
		float wval = Float.MIN_VALUE;
		int wndx = -1;
		for(BusType btype : _bustypes)
		{
			for(int b : _btu.getBuses(btype, i))
			{
				float v = _mm[b];
				if (!Float.isFinite(v))
				{
					return new WorstMM(Status.BlowsUp, 0, _bri.getBuses().get(b));
				}
				
				if (Math.abs(wval) < Math.abs(v))
				{
					wval = v;
					wndx = b;
				}
			}
		}
		return new WorstMM(wval, _bri.getBuses().get(wndx));
	}
	
	/**
	 * Get the bus types configured for testing 
	 */
	public Collection<int[]> getTestBuses(Island i)
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
