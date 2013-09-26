package com.powerdata.openpa.psse.powerflow;

public enum VoltageSource
{
	RealTime, Flat, LastSolved;
	
	public static VoltageSource fromConfig(String cfg)
	{
		switch(cfg.toLowerCase())
		{
			case "realtime": return RealTime;
			case "last":
			case "lostsolved": return LastSolved;
			default: return Flat;
		}
	}
}
