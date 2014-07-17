package com.powerdata.openpa;

import com.powerdata.openpa.impl.ShuntReacListI;

public interface ShuntReacList extends ShuntList<ShuntReactor> 
{

	static final ShuntReacList Empty = new ShuntReacListI();

}
