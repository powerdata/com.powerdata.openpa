package com.powerdata.openpa;

import com.powerdata.openpa.impl.SeriesReacListI;

public interface SeriesReacList extends ACBranchList<SeriesReac>
{

	static final SeriesReacList Empty = new SeriesReacListI(); 

}
