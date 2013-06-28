package com.powerdata.openpa.psse.conversion;

import com.powerdata.openpa.psse.Limits;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.psse.TransformerRawList;

public abstract class PhaseShiftTapLimits
{
	private static final WndToTapLim[]	_TapLim	= new WndToTapLim[] { null,
			new Wnd1TapLim(), new Wnd2TapLim(), new Wnd3TapLim() };

	/** Call here to get the appropriate limits */
	public static Limits getLimits(TransformerRawList list, int ndx, int winding)
			throws PsseModelException
	{
		return _TapLim[winding].getLimits(list, ndx);
	}

}

interface WndToTapLim
{
	public Limits getLimits(TransformerRawList list, int ndx)
			throws PsseModelException;
}

class Wnd1TapLim implements WndToTapLim
{
	@Override
	public Limits getLimits(TransformerRawList list, int ndx)
			throws PsseModelException
	{
		return new Limits(list.getRMI1(ndx), list.getRMA1(ndx));
	}
}

class Wnd2TapLim implements WndToTapLim
{
	@Override
	public Limits getLimits(TransformerRawList list, int ndx)
			throws PsseModelException
	{
		return new Limits(list.getRMI2(ndx), list.getRMA2(ndx));
	}
}

class Wnd3TapLim implements WndToTapLim
{
	@Override
	public Limits getLimits(TransformerRawList list, int ndx)
			throws PsseModelException
	{
		return new Limits(list.getRMI3(ndx), list.getRMA3(ndx));
	}
}
