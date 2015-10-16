package com.powerdata.openpa;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;
import com.powerdata.openpa.impl.EmptyLists;

public interface TwoTermDCLineList extends TwoTermDevListIfc<TwoTermDCLine>
{
	static TwoTermDCLineList emptyList() {return EmptyLists.EMPTY_TWOTERMDCLINES;}
	
	static Set<ColumnMeta> Cols = EnumSet.copyOf(Arrays
			.asList(new ColumnMeta[] { ColumnMeta.T2dcBUSFROM,
					ColumnMeta.T2dcBUSTO, ColumnMeta.T2dcID,
					ColumnMeta.T2dcNAME, ColumnMeta.T2dcINSVC,
					ColumnMeta.T2dcPFROM, ColumnMeta.T2dcPTO,
					ColumnMeta.T2dcQFROM, ColumnMeta.T2dcQTO }));
	@Override
	default Set<ColumnMeta> getColTypes()
	{
		return Cols;
	}
	@Override
	default ListMetaType getListMeta()
	{
		return ListMetaType.TwoTermDCLine;
	}
}
