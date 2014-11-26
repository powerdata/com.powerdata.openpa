package com.powerdata.openpa;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;
import com.powerdata.openpa.impl.EmptyLists;


public interface OwnerList extends GroupListIfc<Owner>
{
	static OwnerList emptyList() {return EmptyLists.EMPTY_OWNERS;}
	
	static Set<ColumnMeta> Cols = EnumSet
			.copyOf(Arrays.asList(new ColumnMeta[] { ColumnMeta.OwnerID,
					ColumnMeta.OwnerNAME }));
	@Override
	default Set<ColumnMeta> getColTypes()
	{
		return Cols;
	}
	@Override
	default ListMetaType getListMeta()
	{
		return ListMetaType.Owner;
	}
}
