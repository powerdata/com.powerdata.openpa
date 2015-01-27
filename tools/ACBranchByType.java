package com.powerdata.openpa.tools;

import java.util.EnumMap;
import com.powerdata.openpa.ACBranch;
import com.powerdata.openpa.ACBranchListIfc;
import com.powerdata.openpa.ListMetaType;
import com.powerdata.openpa.PALists;
import com.powerdata.openpa.PAModelException;

public class ACBranchByType
		extends
		EnumMap<ListMetaType, com.powerdata.openpa.tools.ACBranchByType.BranchListSupplier>
{
	@FunctionalInterface
	interface BranchListSupplier
	{
		ACBranchListIfc<? extends ACBranch> get(PALists lists) throws PAModelException;
	}
	private static final long serialVersionUID = 1L;
	public ACBranchByType()
	{
		super(ListMetaType.class);
		put(ListMetaType.Line, i -> i.getLines());
		put(ListMetaType.SeriesCap, i -> i.getSeriesCapacitors());
		put(ListMetaType.SeriesReac, i -> i.getSeriesReactors());
		put(ListMetaType.PhaseShifter, i -> i.getPhaseShifters());
		put(ListMetaType.Transformer, i -> i.getTransformers());
	}
	private static ACBranchByType _BranchByType = new ACBranchByType();
	public static ACBranchListIfc<? extends ACBranch> get(ListMetaType type,
		PALists lists) throws PAModelException
	{
		return _BranchByType.get(type).get(lists);
	}
}
