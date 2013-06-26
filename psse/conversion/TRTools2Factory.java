package com.powerdata.openpa.psse.conversion;

import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.psse.TapRatioLimit;
import com.powerdata.openpa.psse.TransformerRawList;

public class TRTools2Factory
{
	private static final TRTools2[]	_ToolSet;
	
	static
	{ TRTools2 cw1 = new RatioCw1();
		_ToolSet = new TRTools2[]
			{
				cw1, cw1
			};
}

	public static TRTools2 Open(TransformerRawList list, int ndx)
	{
		return _ToolSet[list.getCW(ndx)];
	}
}

class RatioCw1 extends TRTools2
{
// ADD another test here for |COD|
	@Override
	public TapRatioLimit getRatioLim1(TransformerRawList list, int ndx) throws PsseModelException
	{
		return new TapRatioLimit(list.getRMI1(ndx), list.getRMA1(ndx));
	}

	@Override
	public TapRatioLimit getRatioLim2(TransformerRawList list, int ndx)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TapRatioLimit getRatioLim3(TransformerRawList list, int ndx)
	{
		// TODO Auto-generated method stub
		return null;
	}
}