package com.powerdata.openpa;

import com.powerdata.openpa.impl.TwoTermDCLineListI;

public interface TwoTermDCLineList extends TwoTermDevListIfc<TwoTermDCLine>
{

	static final TwoTermDCLineList Empty = new TwoTermDCLineListI();

}
