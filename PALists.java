package com.powerdata.openpa;

public interface PALists
{
	/** return list of areas */
	AreaList getAreas();
	
	/** return list of buses */
	BusList getBuses();
	
	/** return list of switches */
	SwitchList getSwitches();

	/** return list of lines */
	LineList getLines();

	/** return list of series reactors */
	SeriesReacList getSeriesReactors();
	
	/** return list of series capacitors */
	SeriesCapList getSeriesCapacitors();
	
	/** return list of transformers */
	TransformerList getTransformers();
	
	/** return list of phase shifters */
	PhaseShifterList getPhaseShifters();
	
	/** return list of two-terminal DC Lines */
	TwoTermDCLineList getTwoTermDCLines();
	
	/** return list of generators */
	GenList getGenerators();
	
	/** return list of loads */
	LoadList getLoads();
	
	/** return list of shunt reactors */
	ShuntReacList getShuntReactors();
	
	/** return list of shunt capacitors */
	ShuntCapList getShuntCapacitors();

	/** return list of switched shunts */
	SwitchedShuntList getSwitchedShunts();
	
	/** return list of SVC's */
	SVCList getSVCs();
}
