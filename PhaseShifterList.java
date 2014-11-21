package com.powerdata.openpa;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;
import com.powerdata.openpa.PhaseShifter.ControlMode;
import com.powerdata.openpa.impl.EmptyLists;

public interface PhaseShifterList extends ACBranchListIfc<PhaseShifter>
{
	static PhaseShifterList emptyList() {return EmptyLists.EMPTY_PHASESHIFTERS;}
	
	ControlMode getControlMode(int ndx) throws PAModelException;
	ControlMode[] getControlMode() throws PAModelException;
	void setControlMode(int ndx, ControlMode m) throws PAModelException;
	void setControlMode(ControlMode[] m) throws PAModelException;
	static Set<ColumnMeta> Cols = EnumSet.copyOf(Arrays
			.asList(new ColumnMeta[] { ColumnMeta.PhashANG,
					ColumnMeta.PhashBMAG, ColumnMeta.PhashBUSFROM,
					ColumnMeta.PhashBUSTO, ColumnMeta.PhashCTRLMODE,
					ColumnMeta.PhashGMAG, ColumnMeta.PhashID,
					ColumnMeta.PhashNAME, ColumnMeta.PhashOOS,
					ColumnMeta.PhashPFROM, ColumnMeta.PhashPTO,
					ColumnMeta.PhashQFROM, ColumnMeta.PhashQTO,
					ColumnMeta.PhashR, ColumnMeta.PhashRATLT,
					ColumnMeta.PhashTAPFROM, ColumnMeta.PhashTAPTO,
					ColumnMeta.PhashX }));
	@Override
	default Set<ColumnMeta> getColTypes()
	{
		return Cols;
	}
	@Override
	default ListMetaType getListMeta()
	{
		return ListMetaType.PhaseShifter;
	}
}
