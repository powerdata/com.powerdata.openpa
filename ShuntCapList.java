package com.powerdata.openpa;

import com.powerdata.openpa.impl.ShuntCapListI;

public interface ShuntCapList extends ShuntList<ShuntCapacitor> 
{

	static final ShuntCapList Empty = new ShuntCapListI();

}
