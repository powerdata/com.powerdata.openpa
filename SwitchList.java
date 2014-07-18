package com.powerdata.openpa;

import com.powerdata.openpa.Switch.State;

public interface SwitchList extends TwoTermDevListIfc<Switch>
{

	State getState(int ndx);

	void setState(int ndx, State state);
	
	State[] getState();
	
	void setState(State[] state);

	boolean isOperableUnderLoad(int ndx);

	void setOperableUnderLoad(int ndx, boolean op);
	
	boolean[] isOperableUnderLoad();
	
	void setOperableUnderLoad(boolean[] op);

	boolean isEnabled(int ndx);

	void setEnabled(int ndx, boolean enable);

	boolean[] isEnabled();
	
	void setEnabled(boolean[] enable);
}
