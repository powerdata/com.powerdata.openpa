package com.powerdata.openpa;

import com.powerdata.openpa.impl.PhaseShifterListI;
import com.powerdata.openpa.impl.TransformerBaseList;

public interface PhaseShifterList extends TransformerBaseList<PhaseShifter>
{

	static final PhaseShifterList Empty = new PhaseShifterListI();

}
