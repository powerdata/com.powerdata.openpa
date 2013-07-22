package com.powerdata.openpa.psse.conversions;

import com.powerdata.openpa.psse.Limits;
import com.powerdata.openpa.psse.PhaseShifter;
import com.powerdata.openpa.psse.PsseModelException;

public abstract class PhaseShiftTapLimits
{
	private static final WndToTapLim[]	_TapLim	= new WndToTapLim[] { null,
			new Wnd1TapLim(), new Wnd2TapLim(), new Wnd3TapLim() };

	/** Call here to get the appropriate limits */
	public static Limits getLimits(TransformerRaw xf, int winding)
			throws PsseModelException
	{
		return _TapLim[winding].getLimits(xf);
	}

	public static Limits getLimits(PhaseShifter xf, int winding)
			throws PsseModelException
	{
		return _TapLim[winding].getLimits(xf);
	}

}

interface WndToTapLim
{
	public Limits getLimits(TransformerRaw xf) throws PsseModelException;
	public Limits getLimits(PhaseShifter xf) throws PsseModelException;
}

class Wnd1TapLim implements WndToTapLim
{
	@Override
	public Limits getLimits(TransformerRaw xf) throws PsseModelException
	{
		return new Limits(xf.getRMI1(), xf.getRMA1());
	}

	@Override
	public Limits getLimits(PhaseShifter xf) throws PsseModelException
	{
		return new Limits(xf.getRMI1(), xf.getRMA1());
	}
}

class Wnd2TapLim implements WndToTapLim
{
	@Override
	public Limits getLimits(TransformerRaw xf)
			throws PsseModelException
	{
		return new Limits(xf.getRMI2(), xf.getRMA2());
	}
	@Override
	public Limits getLimits(PhaseShifter xf)
			throws PsseModelException
	{
		return new Limits(-180f, 180f);
	}
}

class Wnd3TapLim implements WndToTapLim
{
	@Override
	public Limits getLimits(TransformerRaw xf)
			throws PsseModelException
	{
		return new Limits(xf.getRMI3(), xf.getRMA3());
	}

	@Override
	public Limits getLimits(PhaseShifter xf) throws PsseModelException
	{
		return new Limits(180f, 180);
	}
}
