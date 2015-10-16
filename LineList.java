package com.powerdata.openpa;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;
import com.powerdata.openpa.impl.EmptyLists;

public interface LineList extends ACBranchListIfc<Line>
{
	static LineList emptyList() {return EmptyLists.EMPTY_LINES;}
	
	float getFromBchg(int ndx) throws PAModelException;
	void setFromBchg(int ndx, float b) throws PAModelException;
	float[] getFromBchg() throws PAModelException;
	void setFromBchg(float[] b) throws PAModelException;
	float getToBchg(int ndx) throws PAModelException;
	void setToBchg(int ndx, float b) throws PAModelException;
	float[] getToBchg() throws PAModelException;
	void setToBchg(float[] b) throws PAModelException;
	static Set<ColumnMeta> _Cols = EnumSet.copyOf(Arrays
			.asList(new ColumnMeta[] { ColumnMeta.LineBFROM,
					ColumnMeta.LineBTO, ColumnMeta.LineBUSFROM,
					ColumnMeta.LineBUSTO, ColumnMeta.LineID,
					ColumnMeta.LineNAME, ColumnMeta.LineINSVC,
					ColumnMeta.LinePFROM, ColumnMeta.LinePTO,
					ColumnMeta.LineQFROM, ColumnMeta.LineQTO, ColumnMeta.LineR,
					ColumnMeta.LineRATLT, ColumnMeta.LineX }));
	@Override
	default Set<ColumnMeta> getColTypes()
	{
		return _Cols;
	}
	@Override
	default ListMetaType getListMeta()
	{
		return ListMetaType.Line;
	}
}
