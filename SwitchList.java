package com.powerdata.openpa;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;
import com.powerdata.openpa.Switch.State;
import com.powerdata.openpa.impl.EmptyLists;

public interface SwitchList extends TwoTermDevListIfc<Switch>
{
	static final SwitchList EMPTY = EmptyLists.EMPTY_SWITCHES;
	
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
	static Set<ColumnMeta> Cols = EnumSet.copyOf(Arrays
			.asList(new ColumnMeta[] { ColumnMeta.SwBUSFROM,
					ColumnMeta.SwBUSTO, ColumnMeta.SwENAB, ColumnMeta.SwID,
					ColumnMeta.SwNAME, ColumnMeta.SwOOS, ColumnMeta.SwOPLD,
					ColumnMeta.SwPFROM, ColumnMeta.SwPTO, ColumnMeta.SwQFROM,
					ColumnMeta.SwQTO }));
	@Override
	default Set<ColumnMeta> getColTypes()
	{
		return Cols;
	}
	@Override
	default ListMetaType getListMeta() {return ListMetaType.Switch;}
}
