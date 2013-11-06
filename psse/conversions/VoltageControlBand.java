package com.powerdata.openpa.psse.conversions;

import com.powerdata.openpa.psse.Limits;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.psse.Transformer;
import com.powerdata.openpa.psse.util.LogSev;

public abstract class VoltageControlBand
{
	private static final VoltLimByWnd[] _Tools = new VoltLimByWnd[]
	{
		new VLimW1(),
		new VLimW2(),
		new VLimW3()
	};
	
	public static Limits getLimits(Transformer xf, int winding)
			throws PsseModelException
	{
		return _Tools[winding].getFactory(xf).getLimits(xf);
	}
	
	public abstract Limits getLimits(Transformer xf)
			throws PsseModelException;

}

class LogMsg extends VoltageControlBand
{
	public static final VoltageControlBand	Default		= new LogMsg();
	public static final Limits				DeftLimit	= new Limits(0.9F, 1.1F);

	@Override
	public Limits getLimits(Transformer xf)
			throws PsseModelException
	{
		xf.getPsseModel().log(LogSev.Warn,xf,
			"Attempting to retrieve MVAr voltage band limits when Transformer is controlling bus voltage");
		return DeftLimit;
	}
}


interface VoltLimByWnd
{
	public VoltageControlBand getFactory(Transformer xf) throws PsseModelException;
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
	public VoltageControlBand getFactory(Transformer xf) throws PsseModelException
	{
		return _Tools[Math.abs(xf.getCOD1())];
	}
}

class VLW1PassThru extends VoltageControlBand
{
	public static final VoltageControlBand	Default	= new VLW1PassThru();
	@Override
	public Limits getLimits(Transformer xf)
			throws PsseModelException
	{
		return new Limits(xf.getVMI1(), xf.getVMA1());
	}
}

class VLimW2 implements VoltLimByWnd
{
	private static final VoltageControlBand[]	_Tools	= new VoltageControlBand[] {
			VLW2PassThru.Default, VLW2PassThru.Default, LogMsg.Default, //
			null, //
			VLW2PassThru.Default						};

	@Override
	public VoltageControlBand getFactory(Transformer xf) throws PsseModelException
	{
		return _Tools[0];
	}

}

class VLW2PassThru extends VoltageControlBand
{
	public static final VoltageControlBand	Default	= new VLW2PassThru();

	@Override
	public Limits getLimits(Transformer xf)
			throws PsseModelException
	{
		return new Limits(0.9f, 1.1f);
	}
}

class VLimW3 implements VoltLimByWnd
{
	private static final VoltageControlBand[]	_Tools	= new VoltageControlBand[] {
			VLW3PassThru.Default, VLW3PassThru.Default, LogMsg.Default, //
			null, //
			VLW3PassThru.Default						};

	@Override
	public VoltageControlBand getFactory(Transformer xf) throws PsseModelException
	{
		return _Tools[0];
	}

}

class VLW3PassThru extends VoltageControlBand
{
	public static final VoltageControlBand	Default	= new VLW3PassThru();

	@Override
	public Limits getLimits(Transformer xf)
			throws PsseModelException
	{
		return new Limits(0.9f, 1.1f);
	}
}
