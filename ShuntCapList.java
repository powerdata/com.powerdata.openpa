package com.powerdata.openpa;

import com.powerdata.openpa.impl.ShuntCapListI;

public interface ShuntCapList extends FixedShuntList<ShuntCapacitor> 
{

	static final ShuntCapList Empty = new ShuntCapListI();

}
