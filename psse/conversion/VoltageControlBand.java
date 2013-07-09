package com.powerdata.openpa.psse.conversion;

import com.powerdata.openpa.psse.Limits;
import com.powerdata.openpa.psse.LogSev;
import com.powerdata.openpa.psse.PsseModelException;

public abstract class VoltageControlBand
{
	private static final VoltLimByWnd[] _Tools = new VoltLimByWnd[]
	{
		new VLimW1(),
		new VLimW2(),
		new VLimW3()
	};
	
	public static Limits getLimits(TransformerRawList list, int ndx, int winding)
			throws PsseModelException
	{
		return _Tools[winding].getFactory(list, ndx).getLimits(list, ndx);
	}
	
	public abstract Limits getLimits(TransformerRawList list, int ndx)
			throws PsseModelException;

}

class LogMsg extends VoltageControlBand
{
	public static final VoltageControlBand	Default		= new LogMsg();
	public static final Limits				DeftLimit	= new Limits(0.9F, 1.1F);

	@Override
	public Limits getLimits(TransformerRawList list, int ndx)
			throws PsseModelException
	{
		list.getPsseModel().log(LogSev.Warn,list.get(ndx),
			"Attempting to retrieve MVAr voltage band limits when Transformer is controlling bus voltage");
		return DeftLimit;
	}
}


interface VoltLimByWnd
{
	public VoltageControlBand getFactory(TransformerRawList list, int ndx) throws PsseModelException;
}

class VLimW1 implements VoltLimByWnd
{
	private static final VoltageControlBand[] _Tools = new VoltageControlBand[] {
		VLW1PassThru.Default,
		VLW1PassThru.Default,
		LogMsg.Default, //
		null, //
		VLW1PassThru.Default
	};
	@Override
	public VoltageControlBand getFactory(TransformerRawList list, int ndx) throws PsseModelException
	{
		return _Tools[Math.abs(list.getCOD1(ndx))];
	}
	
}

class VLW1PassThru extends VoltageControlBand
{
	public static final VoltageControlBand	Default	= new VLW1PassThru();

	@Override
	public Limits getLimits(TransformerRawList list, int ndx)
			throws PsseModelException
	{
		return new Limits(list.getVMI1(ndx), list.getVMA1(ndx));
	}
}

class VLimW2 implements VoltLimByWnd
{
	private static final VoltageControlBand[]	_Tools	= new VoltageControlBand[] {
			VLW2PassThru.Default, VLW2PassThru.Default, LogMsg.Default, //
			null, //
			VLW2PassThru.Default						};

	@Override
	public VoltageControlBand getFactory(TransformerRawList list, int ndx) throws PsseModelException
	{
		return _Tools[Math.abs(list.getCOD2(ndx))];
	}

}

class VLW2PassThru extends VoltageControlBand
{
	public static final VoltageControlBand	Default	= new VLW2PassThru();

	@Override
	public Limits getLimits(TransformerRawList list, int ndx)
			throws PsseModelException
	{
		return new Limits(list.getVMI2(ndx), list.getVMA2(ndx));
	}
}

class VLimW3 implements VoltLimByWnd
{
	private static final VoltageControlBand[]	_Tools	= new VoltageControlBand[] {
			VLW3PassThru.Default, VLW3PassThru.Default, LogMsg.Default, //
			null, //
			VLW3PassThru.Default						};

	@Override
	public VoltageControlBand getFactory(TransformerRawList list, int ndx) throws PsseModelException
	{
		return _Tools[Math.abs(list.getCOD3(ndx))];
	}

}

class VLW3PassThru extends VoltageControlBand
{
	public static final VoltageControlBand	Default	= new VLW3PassThru();

	@Override
	public Limits getLimits(TransformerRawList list, int ndx)
			throws PsseModelException
	{
		return new Limits(list.getVMI3(ndx), list.getVMA3(ndx));
	}
}
