package com.powerdata.openpa.psse;

public enum TransformerStatus
{
	Unknown, OutOfService, InService, Wnd1Out, Wnd2Out, Wnd3Out;
	private static final TransformerStatus[] _Codes = new TransformerStatus[]
	{
		OutOfService, InService, Wnd2Out, Wnd3Out, Wnd1Out
	};
	public static TransformerStatus fromCode(int code)
	{
		if (code < 0 || code >= _Codes.length)
			return Unknown;
		else
			return _Codes[code];
	}
}