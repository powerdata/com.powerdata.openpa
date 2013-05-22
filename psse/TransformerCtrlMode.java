package com.powerdata.openpa.psse;

public enum TransformerCtrlMode
{
	Unknown, None, Voltage, ReactivePowerFlow,
	ActivePowerFlow, DCLine;
	private static TransformerCtrlMode[] _Codes = new TransformerCtrlMode[]
			{None, Voltage, ReactivePowerFlow, ActivePowerFlow, DCLine};
	public static TransformerCtrlMode fromCode(int code)
	{
		if (code < 0 || code >= _Codes.length)
			return Unknown;
		else
			return _Codes[code];
	}
	
}
