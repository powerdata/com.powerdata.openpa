package com.powerdata.openpa.psse;

public class GroupType
{
	protected static final String[] GrpType = new String[]
			{"AREA", "ZONE", "OWNER", "STATION", "VOLTLEV"};

	public static final String	Area			= GrpType[0];
	public static final String	Zone			= GrpType[1];
	public static final String	Owner			= GrpType[2];
	public static final String	Station			= GrpType[3];
	public static final String	VoltageLevel	= GrpType[4];
	
	public static String[] values() {return GrpType;}
	
	
}
