package com.powerdata.openpa.tools;

import java.util.AbstractList;

public abstract class BaseList<T extends BaseObject> extends AbstractList<T> 
{
	public static final float D2R = ((float)Math.PI)/180F;
	public static float deg2rad(float deg) {return deg*D2R;}
	public static float rad2deg(float rad) {return rad/D2R;}
	
	public abstract String getObjectID(int ndx);
	/** Get an object by it's ID */
	public abstract T get(String objectid);
	
	public abstract StringAttrib<T>  mapStringAttrib(String attribname);
	public abstract FloatAttrib<T>   mapFloatAttrib(String attribname);
	public abstract IntAttrib<T>     mapIntAttrib(String attribname);
	public abstract BooleanAttrib<T> mapBooleanAttrib(String attribname);
	
}
