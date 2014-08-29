package com.powerdata.openpa.tools.psmfmt;

public enum PsmMdlFmtObject implements VersionedDoc
{
	ControlArea, CurrentRelay, FrequencyRelay, GeneratingUnit, Line, Load, LoadArea, 
	ModelParameters, Node, Organization, PhaseTapChanger, PrimeMover, RatioTapChanger, 
	ReactiveCapabilityCurve, RelayOperate, SeriesCapacitor, SeriesReactor, 
	ShuntCapacitor, ShuntReactor, Substation, SynchronousMachine, SVC, Switch, 
	SwitchType, Transformer, TransformerWinding, VoltageRelay;

	static final String _version = "1.10";
	
	public String getVersion() {return _version;}
	static public String GetVersion() {return _version;}
}
