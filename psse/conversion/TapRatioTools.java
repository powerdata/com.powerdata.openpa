package com.powerdata.openpa.psse.conversion;

import com.powerdata.openpa.psse.TransformerRawList;

public class TapRatioTools
{
	private static final TapRatioTools[]	_ToolSet;

	static
	{
		_ToolSet = new TapRatioTools[]
				{
				
				};
	}
	
	public static TapRatioTools getToolsWnd1(TransformerRawList list, int ndx)
	{
		return _ToolSet[list.getCOD1(ndx)];
	}
}
