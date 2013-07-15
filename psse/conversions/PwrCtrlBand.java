package com.powerdata.openpa.psse.conversions;

import com.powerdata.openpa.psse.Limits;
import com.powerdata.openpa.psse.PsseModelException;

public class PwrCtrlBand
{
	private static final WndToPwrLim[]	_WndLim	= new WndToPwrLim[] { null,
			new Wnd1PwrLim(), new Wnd2PwrLim(), new Wnd3PwrLim() };

	public static final Limits getLimits(TransformerRawList list, int ndx,
			int winding) throws PsseModelException
	{
		return _WndLim[winding].getLimits(list, ndx);
	}
}

interface WndToPwrLim
{
	Limits getLimits(TransformerRawList list, int ndx)
			throws PsseModelException;
}

class Wnd1PwrLim implements WndToPwrLim
{
	@Override
	public Limits getLimits(TransformerRawList list, int ndx)
			throws PsseModelException
	{
		return new Limits(list.getVMI1(ndx), list.getVMA1(ndx));
	}
}

class Wnd2PwrLim implements WndToPwrLim
{
	@Override
	public Limits getLimits(TransformerRawList list, int ndx)
			throws PsseModelException
	{
		return new Limits(list.getVMI2(ndx), list.getVMA2(ndx));
	}
}

class Wnd3PwrLim implements WndToPwrLim
{
	@Override
	public Limits getLimits(TransformerRawList list, int ndx)
			throws PsseModelException
	{
		return new Limits(list.getVMI3(ndx), list.getVMA3(ndx));
	}
}
