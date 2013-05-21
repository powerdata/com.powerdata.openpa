package com.powerdata.openpa.psse;

public enum BusTypeCode
{
	Unknown(-1), Load(1), Gen(2), Slack(3), Isolated(4);
	private int _code;
	private static BusTypeCode[] _Codes = new BusTypeCode[]
	{
		null, Load, Gen, Slack, Isolated
	};
	BusTypeCode(int code) {_code = code;}
	public int getCode() {return _code;}
	public static BusTypeCode fromCode(int code)
	{
		if (code < 1 || code >= _Codes.length)
			return Unknown;
		else
			return _Codes[code];
	}
}