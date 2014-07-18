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
	String[] stringAccess();
	/** access changes as an array of floats */
	float[] floatAccess();
	/** access changes as an array of ints */
	int[] intAccess();
	/** access changes as an array of boolean */
	boolean[] booleanAccess();
	/** clear the cahnges */ 
	void clear();
}
