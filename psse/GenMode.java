package com.powerdata.openpa.psse;

public enum GenMode
{
	/** Off */ OFF, 
	/** On in unknown mode */ ON, 
	/** On with manual active power setpoint */ MAN, 
	/** Load Frequency Control */ LFC,
	/** Automatic Generation Control */ AGC, 
	/** Economic Dispatch */ EDC, 
	/** Pumping */ PMP, 
	/** Condense */ CON,
	/** Isochronous Restore Mode */ RES;
}
