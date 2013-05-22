package com.powerdata.openpa.psse;

public enum BusTypeCode
{
	Unknown, Load, Gen, Slack, Isolated;
	private static BusTypeCode[] _Codes = new BusTypeCode[]
	{
		null, Load, Gen, Slack, Isolated
	};
	public static BusTypeCode fromCode(int code)
	{
		if (code < 1 || code >= _Codes.length)
			return Unknown;
		else
			return _Codes[code];
	}
}