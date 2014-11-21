package com.powerdata.openpa;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;
import com.powerdata.openpa.impl.EmptyLists;

public interface SeriesCapList extends ACBranchListIfc<SeriesCap>
{
	static final SeriesCapList EMPTY = EmptyLists.EMPTY_SERIESCAPS;
	
	static Set<ColumnMeta> Cols = EnumSet.copyOf(Arrays
			.asList(new ColumnMeta[] { ColumnMeta.SercapBUSFROM,
					ColumnMeta.SercapBUSTO, ColumnMeta.SercapID,
					ColumnMeta.SercapNAME, ColumnMeta.SercapOOS,
					ColumnMeta.SercapPFROM, ColumnMeta.SercapPTO,
					ColumnMeta.SercapQFROM, ColumnMeta.SercapQTO,
					ColumnMeta.SercapR, ColumnMeta.SercapRATLT,
					ColumnMeta.SercapX }));
	@Override
	default Set<ColumnMeta> getColTypes()
	{
		return Cols;
	}
	@Override
	default ListMetaType getListMeta()
	{
		return ListMetaType.SeriesCap;
	}
}
