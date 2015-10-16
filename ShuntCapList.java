package com.powerdata.openpa;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;
import com.powerdata.openpa.impl.EmptyLists;

public interface ShuntCapList extends FixedShuntListIfc<ShuntCapacitor>
{
	static ShuntCapList emptyList() {return EmptyLists.EMPTY_SHUNTCAPS;}
	
	static Set<ColumnMeta> _Cols = EnumSet
			.copyOf(Arrays.asList(new ColumnMeta[] { ColumnMeta.ShcapB,
					ColumnMeta.ShcapBUS, ColumnMeta.ShcapID,
					ColumnMeta.ShcapNAME, ColumnMeta.ShcapINSVC,
					ColumnMeta.ShcapP, ColumnMeta.ShcapQ }));
	@Override
	default Set<ColumnMeta> getColTypes()
	{
		return _Cols;
	}
	@Override
	default ListMetaType getListMeta()
	{
		return ListMetaType.ShuntCap;
	}
}
