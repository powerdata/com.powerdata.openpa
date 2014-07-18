package com.powerdata.openpa;

import com.powerdata.openpa.impl.SeriesReacListI;

public interface SeriesReacList extends ACBranchListIfc<SeriesReac>
{

	static final SeriesReacList Empty = new SeriesReacListI(); 

}
