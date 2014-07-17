package com.powerdata.openpa;

import com.powerdata.openpa.impl.SwitchedShuntListI;

public interface SwitchedShuntList extends ShuntList<SwitchedShunt>
{

	static final SwitchedShuntList Empty = new SwitchedShuntListI();

}
