package com.powerdata.openpa.psse.conversion;

import com.powerdata.openpa.psse.Limits;
import com.powerdata.openpa.psse.TransformerRawList;

public abstract class VoltageLimitTools
{
	private static final VoltLimByWnd[] _Tools = new VoltLimByWnd[]
	{
		new VLimW1(),
		new VLimW2(),
		new VLimW3()
	};
	
	public static Limits getLimits(TransformerRawList list, int ndx, int winding)
	{
		return _Tools[winding].getLimits(list, ndx);
	}
	
//	public VoltageLimitTools getTools(int winding)
}

interface VoltLimByWnd
{
	public VoltageLimitTools getFactory(TransformerRawList list, int ndx);
}

class VLimW1 implements VoltLimByWnd
{
	private static final VoltageLimitTools[] _Tools = new VoltageLimitTools[] {
		W1PassThru.Default,
		W1PassThru.Default,
		W1PassThru.Default,
		W1PassThru.Default,
		W1PassThru.Default
	};
	@Override
	public VoltageLimitTools getFactory(TransformerRawList list, int ndx)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
}



class W1PassThru extends VoltageLimitTools
{
	public static final VoltageLimitTools Default = new W1PassThru();
}