package com.powerdata.openpa.tools;

import com.powerdata.openpa.Bus;
import com.powerdata.openpa.BusList;
import com.powerdata.openpa.PAModelException;

/**
 * Power Applications conversions 
 * 
 * @author chris@powerdata.com
 *
 */
public class PAMath
{
	/* constants that get re-used */
	public static final float PI = (float) Math.PI;
	
	/** 3 over PI */
	public static final float THREEOVERPI = 3f / PI;
	
	/** 3 sqrt(2) over PI */
	public static final float THREESQRT2OVERPI = THREEOVERPI * ((float) Math.sqrt(2f));

	/** Constant to convert degrees and radians */
	public static final float D2R = PI/180f;

	
	/** convert degrees to radians */
	public static float deg2rad(float deg) {return deg*D2R;}
	public static float[] deg2rad(float[] deg)
	{
		int n = deg.length;
		float[] rv = new float[n];
		for(int i=0; i < n; ++i)
			rv[i] = deg[i]*D2R;
		return rv;
	}
	/** convert radians to degrees */
	public static float rad2deg(float rad) {return rad/D2R;}
	public static float[] rad2deg(float[] rad)
	{
		int n = rad.length;
		float[] rv = new float[n];
		for(int i=0; i < n; ++i)
			rv[i] = rad[i]/D2R;
		return rv;
	}

	/* methods to convert per-unit on 100MVA base */
	
	/** convert MVA to per-unit */
	public static float mva2pu(float mva, float mvabase) {return mva/mvabase;}
	public static float[] mva2pu(float[] mva, float mvabase)
	{
		int n = mva.length;
		float[] pu = new float[n];
		for(int i=0; i < n; ++i)
			pu[i] = mva[i] / mvabase;
		return pu;
	}
	/** convert per-unit to MVA */
	public static float pu2mva(float pu, float mvabase) {return pu*mvabase;}
	
	public static float[] pu2mva(float[] pu, float mvabase)
	{
		int n = pu.length;
		float[] rv = new float[n];
		for(int i=0; i < n; ++i)
			rv[i] = pu[i] * mvabase;
		return rv;
	}


	/** convert p.u. impedance to 100 MVA base */
	public static float rebaseZ100(float zval, float mvabase) {return (mvabase==100F)?zval:zval*100F/mvabase;}
	/** convert p.u. impedance to 100 MVA base */
	public static Complex rebaseZ100 (Complex zval, float mvabase)
	{
		return (mvabase==100F)?zval:zval.mult(100F/mvabase);
	}

	public static float calcMVA(float mw, float mvar)
	{
		return (float) Math.sqrt(mw * mw + mvar * mvar);
	}
	public static float[] vmpu(BusList buses) throws PAModelException
	{
		int n = buses.size();
		float[] rv = new float[n];
		float[] vm = buses.getVM();
		for(int i=0; i < n; ++i)
			rv[i] = vm[i]/buses.getVoltageLevel(i).getBaseKV();
		return rv;
	}
	
	public static float vmpu(Bus bus) throws PAModelException
	{
		return bus.getVM()/bus.getVoltageLevel().getBaseKV();
	}
	
}
