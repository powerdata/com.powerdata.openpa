package com.powerdata.openpa;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;
import com.powerdata.openpa.impl.EmptyLists;


public interface StationList extends GroupListIfc<Station>
{
	static StationList emptyList() {return EmptyLists.EMPTY_STATIONS;}
	
    static Set<ColumnMeta> _Cols = EnumSet.copyOf(Arrays.asList(new ColumnMeta[]
    {
    	ColumnMeta.StationID,
    	ColumnMeta.StationNAME
    }));
	@Override
	default Set<ColumnMeta> getColTypes()
	{
		return _Cols;
	}
	@Override
	default ListMetaType getListMeta()
	{
		return ListMetaType.Station;
	}
}
