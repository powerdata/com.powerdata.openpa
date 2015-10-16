package com.powerdata.openpa;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;
import com.powerdata.openpa.impl.EmptyLists;

public interface LoadList extends OneTermDevListIfc<Load>
{
	static LoadList emptyList() {return EmptyLists.EMPTY_LOADS;}
	
	float getMaxP(int ndx) throws PAModelException;
	void setMaxP(int ndx, float mw) throws PAModelException;
	float[] getMaxP() throws PAModelException;
	void setMaxP(float[] mw) throws PAModelException;
	float getMaxQ(int ndx) throws PAModelException;
	void setMaxQ(int ndx, float mvar) throws PAModelException;
	float[] getMaxQ() throws PAModelException;
	void setMaxQ(float[] mvar) throws PAModelException;
	static Set<ColumnMeta> Cols = EnumSet
			.copyOf(Arrays.asList(new ColumnMeta[] { ColumnMeta.LoadBUS,
					ColumnMeta.LoadID, ColumnMeta.LoadNAME, ColumnMeta.LoadINSVC,
					ColumnMeta.LoadP, ColumnMeta.LoadPMAX, ColumnMeta.LoadQ,
					ColumnMeta.LoadQMAX }));
	@Override
	default Set<ColumnMeta> getColTypes()
	{
		return Cols;
	}
	@Override default ListMetaType getListMeta() {return ListMetaType.Line;}
}
