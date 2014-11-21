package com.powerdata.openpa;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;

public interface ShuntCapList extends FixedShuntListIfc<ShuntCapacitor>
{
	static Set<ColumnMeta> _Cols = EnumSet
			.copyOf(Arrays.asList(new ColumnMeta[] { ColumnMeta.ShcapB,
					ColumnMeta.ShcapBUS, ColumnMeta.ShcapID,
					ColumnMeta.ShcapNAME, ColumnMeta.ShcapOOS,
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
