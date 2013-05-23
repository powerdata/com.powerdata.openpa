package com.powerdata.openpa.tools;

import java.util.AbstractList;

public abstract class BaseList<T extends BaseObject> extends AbstractList<T> 
{
	/* constants */
	
	/** Constant to convert degrees and radians */
	public static final float D2R = ((float)Math.PI)/180F;

	
	/** convert degrees to radians */
	public static float deg2rad(float deg) {return deg*D2R;}
	/** convert radians to degrees */
	public static float rad2deg(float rad) {return rad/D2R;}
	
	/** convert MW to per-unit */
	public static float mw2pu(float mw) {return mw/100F;}
	/** convert pu active power fo MW */
	public static float pu2mw(float pwr) {return pwr*100F;}
	/** convert MVAr to per-unit */
	public static float mvar2pu(float mvar) {return mvar/100F;}
	/** convert pu active power fo MVAr */
	public static float pu2mvar(float pwr) {return pwr*100F;}
	/** convert p.u. impedance to 100 MVA base */
	public static float rebaseZ100(float zval, float mvabase) {return (mvabase==100F)?zval:zval*100F/mvabase;}
	
	
	public abstract String getObjectID(int ndx);
	/** Get an object by it's ID */
	public abstract T get(String objectid);
	
	public abstract StringAttrib<T>  mapStringAttrib(String attribname);
	public abstract FloatAttrib<T>   mapFloatAttrib(String attribname);
	public abstract IntAttrib<T>     mapIntAttrib(String attribname);
	public abstract BooleanAttrib<T> mapBooleanAttrib(String attribname);
	
}
