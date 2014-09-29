package com.powerdata.openpa.tools.psmfmt;

public enum PsmCaseFmtObject implements VersionedDoc
{
	CaseParameter,ControlArea,LoadAreaCurve,Load,GeneratingUnit,
	SynchronousMachine,ShuntCapacitor,ShuntReactor,SVC,
	RatioTapChanger,PhaseTapChanger,Switch, Line, SeriesCapacitor,
	SeriesReactor, TransformerWinding;
	
	public String getVersion() {return "1.9";}
}
