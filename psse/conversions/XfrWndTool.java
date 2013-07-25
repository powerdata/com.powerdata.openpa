package com.powerdata.openpa.psse.conversions;

import com.powerdata.openpa.psse.PhaseShifter;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.psse.Transformer;
import com.powerdata.openpa.psse.TransformerRaw;

public abstract class XfrWndTool
{

	private static final XfrWndTool[]	_ToolSet;
	static
	{
		XfrWndTool cw1 = new XfrWndcw1();
		_ToolSet = new XfrWndTool[]
		{
			cw1,
			cw1,
			new XfrWndcw2(),
			new XfrWndcw3()
		};
	}
	
	public static XfrWndTool get(int cw) {return _ToolSet[cw];}

	public abstract float getRatio1(TransformerRaw xf) throws PsseModelException;
	public abstract float getRatio2(TransformerRaw xf) throws PsseModelException;
	public abstract float getRatio3(TransformerRaw xf) throws PsseModelException;
	public abstract float getRatio1(Transformer xf) throws PsseModelException;
	public abstract float getRatio2(Transformer xf) throws PsseModelException;
	public abstract float getRatio1(PhaseShifter ps) throws PsseModelException;
	
}
