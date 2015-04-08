package com.powerdata.openpa.psse;

public enum TransformerCtrlMode
{
	NotUsed, Fixed, Voltage, ReactivePowerFlow;
	private static final TransformerCtrlMode[] _Codes = new TransformerCtrlMode[]
			{Fixed, Voltage, ReactivePowerFlow};
	public static TransformerCtrlMode fromCode(int cod)
	{
		int code = Math.abs(cod);
		if (code >= _Codes.length)
			return NotUsed;
		else
			return _Codes[code];
	}
}
