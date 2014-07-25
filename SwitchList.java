package com.powerdata.openpa;

import com.powerdata.openpa.Switch.State;

public interface SwitchList extends TwoTermDevListIfc<Switch>
{

	State getState(int ndx) throws PAModelException;

	void setState(int ndx, State state) throws PAModelException;
	
	State[] getState() throws PAModelException;
	
	void setState(State[] state) throws PAModelException;

	boolean isOperableUnderLoad(int ndx) throws PAModelException;

	void setOperableUnderLoad(int ndx, boolean op) throws PAModelException;
	
	boolean[] isOperableUnderLoad() throws PAModelException;
	
	void setOperableUnderLoad(boolean[] op) throws PAModelException;

	boolean isEnabled(int ndx) throws PAModelException;

	void setEnabled(int ndx, boolean enable) throws PAModelException;

	boolean[] isEnabled() throws PAModelException;
	
	void setEnabled(boolean[] enable) throws PAModelException;
}
