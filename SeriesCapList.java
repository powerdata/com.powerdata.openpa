package com.powerdata.openpa;

import com.powerdata.openpa.impl.SeriesCapListI;

public interface SeriesCapList extends ACBranchListIfc<SeriesCap>
{

	static final SeriesCapList Empty = new SeriesCapListI();

}
