package com.powerdata.openpa;

public interface ShuntCapList extends ShuntList<ShuntCapacitor> 
{

	static final ShuntCapList Empty = new ShuntCapListImpl();

}
