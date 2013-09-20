package com.powerdata.openpa.psse.powerflow;

public enum VoltageSource
{
	RealTime, Flat;
	
	public static VoltageSource fromConfig(String cfg)
	{
		switch(cfg.toLowerCase())
		{
			case "realtime": return RealTime;
			default: return Flat;
		}
	}
}
