package com.powerdata.openpa.psse;

public enum SwShuntCtrlMode
{
	Unknown, Fixed, DiscreteByVoltage, ContinuousByVoltage, DiscreteByReactive, DiscreteByReactiveVSC, DiscreteShuntY;
	private static final SwShuntCtrlMode[] _Codes = new SwShuntCtrlMode[]
		{Fixed, DiscreteByVoltage, ContinuousByVoltage, DiscreteByReactive, DiscreteByReactiveVSC, DiscreteShuntY};
	public static SwShuntCtrlMode fromCode(int code)
	{
		if (code < 0 || code >= _Codes.length)
			return Unknown;
		else
			return _Codes[code];
	}
}
