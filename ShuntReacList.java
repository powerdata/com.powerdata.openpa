package com.powerdata.openpa;

import com.powerdata.openpa.impl.ShuntReacListI;

public interface ShuntReacList extends FixedShuntList<ShuntReactor> 
{

	static final ShuntReacList Empty = new ShuntReacListI();

}
