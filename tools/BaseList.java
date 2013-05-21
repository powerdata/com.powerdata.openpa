package com.powerdata.openpa.tools;

import java.util.AbstractList;

public abstract class BaseList<T extends BaseObject> extends AbstractList<T> 
{
	public abstract String getObjectID(int ndx);
	public abstract int getIndex(String id);
	
	public abstract StringAttrib<T>  mapStringAttrib(String attribname);
	public abstract FloatAttrib<T>   mapFloatAttrib(String attribname);
	public abstract IntAttrib<T>     mapIntAttrib(String attribname);
	public abstract BooleanAttrib<T> mapBooleanAttrib(String attribname);
	
}
