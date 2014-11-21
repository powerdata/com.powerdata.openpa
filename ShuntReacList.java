package com.powerdata.openpa;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;

public interface ShuntReacList extends FixedShuntListIfc<ShuntReactor>
{
	static Set<ColumnMeta> Cols = EnumSet.copyOf(Arrays
			.asList(new ColumnMeta[] { ColumnMeta.ShreacB,
					ColumnMeta.ShreacBUS, ColumnMeta.ShreacID,
					ColumnMeta.ShreacNAME, ColumnMeta.ShreacOOS,
					ColumnMeta.ShreacP, ColumnMeta.ShreacQ }));
	@Override
	default Set<ColumnMeta> getColTypes()
	{
		return Cols;
	}
	@Override
	default ListMetaType getListMeta()
	{
		return ListMetaType.ShuntReac;
	}
}
