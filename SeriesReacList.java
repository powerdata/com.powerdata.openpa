package com.powerdata.openpa;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;
import com.powerdata.openpa.impl.EmptyLists;

public interface SeriesReacList extends ACBranchListIfc<SeriesReac>
{
	static SeriesReacList emptyList() {return EmptyLists.EMPTY_SERIESREACS;}
	
	static Set<ColumnMeta> Cols = EnumSet.copyOf(Arrays
			.asList(new ColumnMeta[] { ColumnMeta.SerreacBUSFROM,
					ColumnMeta.SerreacBUSTO, ColumnMeta.SerreacID,
					ColumnMeta.SerreacNAME, ColumnMeta.SerreacINSVC,
					ColumnMeta.SerreacPFROM, ColumnMeta.SerreacPTO,
					ColumnMeta.SerreacQFROM, ColumnMeta.SerreacQTO,
					ColumnMeta.SerreacR, ColumnMeta.SerreacRATLT,
					ColumnMeta.SerreacX }));
	@Override
	default Set<ColumnMeta> getColTypes()
	{
		return Cols;
	}
	@Override
	default ListMetaType getListMeta()
	{
		return ListMetaType.SeriesReac;
	}
}
