package com.powerdata.openpa;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;
import com.powerdata.openpa.impl.EmptyLists;

public interface SwitchedShuntList extends OneTermDevListIfc<SwitchedShunt>
{
	static SwitchedShuntList emptyList() {return EmptyLists.EMPTY_SWITCHEDSHUNTS;}
	
	static Set<ColumnMeta> Cols = EnumSet.copyOf(Arrays
			.asList(new ColumnMeta[] { ColumnMeta.SwshB, ColumnMeta.SwshID,
					ColumnMeta.SwshNAME, ColumnMeta.SwshOOS, ColumnMeta.SwshP,
					ColumnMeta.SwshQ }));
	@Override
	default Set<ColumnMeta> getColTypes()
	{
		return Cols;
	}
	@Override
	default ListMetaType getListMeta()
	{
		return ListMetaType.SwitchedShunt;
	}

}
