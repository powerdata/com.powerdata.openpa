package com.powerdata.openpa;

import com.powerdata.openpa.impl.SeriesCapListI;

public interface SeriesCapList extends ACBranchList<SeriesCap>
{

	static final SeriesCapList Empty = new SeriesCapListI();

}
