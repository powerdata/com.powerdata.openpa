package com.powerdata.openpa.psse;

public enum TransformerStatus
{
	Unknown(-1), OutOfService(0), InService(1), Wnd1Out(4), Wnd2Out(2), Wnd3Out(3);
	private int _code;
	TransformerStatus(int code) {_code = code;}
	private static final TransformerStatus[] _Codes = new TransformerStatus[]
	{
		OutOfService, InService, Wnd2Out, Wnd3Out, Wnd1Out
	};
	public int getCode() {return _code;}
	public static TransformerStatus fromCode(int code)
	{
		if (code < 0 || code >= _Codes.length)
			return Unknown;
		else
			return _Codes[code];
	}
}