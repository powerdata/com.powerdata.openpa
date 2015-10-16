package com.powerdata.openpa;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;
import com.powerdata.openpa.impl.EmptyLists;

public interface ShuntReacList extends FixedShuntListIfc<ShuntReactor>
{
	static ShuntReacList emptyList() {return EmptyLists.EMPTY_SHUNTREACS;}
	
	static Set<ColumnMeta> Cols = EnumSet.copyOf(Arrays
			.asList(new ColumnMeta[] { ColumnMeta.ShreacB,
					ColumnMeta.ShreacBUS, ColumnMeta.ShreacID,
					ColumnMeta.ShreacNAME, ColumnMeta.ShreacINSVC,
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
