package com.powerdata.openpa;

import com.powerdata.openpa.PhaseShifter.ControlMode;
import com.powerdata.openpa.impl.PhaseShifterListI;

public interface PhaseShifterList extends TransformerBaseList<PhaseShifter>
{

	static final PhaseShifterList Empty = new PhaseShifterListI();

	ControlMode getControlMode(int ndx) throws PAModelException;
	
	ControlMode[] getControlMode() throws PAModelException;

	void setControlMode(int ndx, ControlMode m) throws PAModelException;
	
	void setControlMode(ControlMode[] m) throws PAModelException;

}
