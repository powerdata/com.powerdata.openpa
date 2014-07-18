package com.powerdata.openpa;

public interface ColChange
{
	/** get column meta type */
	ColumnMeta getColMeta();
	/** get list meta type */
	ListMetaType getListMeta();
	/** get changed list indexes */
	int[] getNdxs();
	/** get changed keys */
	int[] getKeys();
	/** get number of changes */
	int size();
	/** access changes as an array of Strings */
	String[] stringValues();
	/** access changes as an array of floats */
	float[] floatValues();
	/** access changes as an array of ints */
	int[] intValues();
	/** access changes as an array of boolean */
	boolean[] booleanValues();
	/** clear the cahnges */ 
	void clear();
}
