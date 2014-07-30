package com.powerdata.openpa.util;

import com.powerdata.openpa.BusList;

interface BusRefIndex
{
	int[] getIndex();
	BusList getBuses();
	static BusRefIndex CreateBusIndex() {return null;}
	static BusRefIndex CreateSingleBusIndex() {return null;}
}

