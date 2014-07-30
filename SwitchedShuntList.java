package com.powerdata.openpa;

import com.powerdata.openpa.impl.SwitchedShuntListI;

public interface SwitchedShuntList extends BaseList<SwitchedShunt>, BusRegulator
{

	static final SwitchedShuntList Empty = new SwitchedShuntListI();

}
