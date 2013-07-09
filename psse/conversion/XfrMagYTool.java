package com.powerdata.openpa.psse.conversion;

import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.tools.Complex;

public abstract class XfrMagYTool
{
	protected static final XfrMagYTool[] _ToolSet;
	
	static
	{
		XfrMagYTool cm1 = new XfrMagYcm1();
		_ToolSet = new XfrMagYTool[]
		{
			cm1,
			cm1,
			new XfrMagYcm2()
		};
		
	}
	
	public static Complex getYMag(TransformerRawList list, int ndx) throws PsseModelException
	{
		return _ToolSet[list.getCM(ndx)]._getYMag(list, ndx);
	}

	protected abstract Complex _getYMag(TransformerRawList list, int ndx) throws PsseModelException;
}
