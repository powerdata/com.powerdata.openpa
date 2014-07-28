package com.powerdata.openpa;

import com.powerdata.openpa.impl.ShuntReacListI;

public interface ShuntReacList extends FixedShuntListIfc<ShuntReactor> 
{

	static final ShuntReacList Empty = new ShuntReacListI();

}
