package com.powerdata.openpa;

import com.powerdata.openpa.impl.ShuntCapListI;

public interface ShuntCapList extends FixedShuntListIfc<ShuntCapacitor> 
{

	static final ShuntCapList Empty = new ShuntCapListI();

}
