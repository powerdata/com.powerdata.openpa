package com.powerdata.openpa.psse;

public enum TransformerCtrlMode
{
	Unknown, None, Voltage, ReactivePowerFlow,
	ActivePowerFlow, DCLine;
	private static final TransformerCtrlMode[] _Codes = new TransformerCtrlMode[]
			{None, Voltage, ReactivePowerFlow, ActivePowerFlow, DCLine};
	public static TransformerCtrlMode fromCode(int cod)
	{
		int code = Math.abs(cod);
		if (code >= _Codes.length)
			return Unknown;
		else
			return _Codes[code];
	}
	
}
