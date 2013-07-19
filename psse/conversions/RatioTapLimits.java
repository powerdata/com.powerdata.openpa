package com.powerdata.openpa.psse.conversions;

import com.powerdata.openpa.psse.Bus;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.psse.Limits;

public abstract class RatioTapLimits
{
	/** CW  x 3 (windings) */
	static private final CwWndToFact[][] _ctf;
	static
	{
		CwWndToFact cw1w1 = new Cw1Wnd1ToFact();
		CwWndToFact cw1w2 = new Cw1Wnd2ToFact();
		CwWndToFact cw1w3 = new Cw1Wnd3ToFact();
		_ctf = new CwWndToFact[][] {
				{null, cw1w1, cw1w2, cw1w3}, 											// CW = 0(default to CW 1) for each of 3 windings
				{null, cw1w1, cw1w2, cw1w3}, 											// CW = 1 for each of 3 windings
				{null, new Cw2Wnd1ToFact(), new Cw2Wnd2ToFact(), new Cw2Wnd3ToFact()}, 	// CW = 2 for each of 3 windings
				{null, new Cw3Wnd1ToFact(), new Cw3Wnd2ToFact(), new Cw3Wnd3ToFact()}, 	// CW = 3 for each of 3 windings
		};
	}
	
	/** Call here to get the appropriate limits */
	public static Limits getLimits(TransformerRaw xf, int winding) throws PsseModelException
	{
		return _ctf[xf.getCW()][winding].getFactory(xf).getLimits(xf);
	}

	public abstract Limits getLimits(TransformerRaw xf)
			throws PsseModelException;
	
}

interface CwWndToFact
{
	public RatioTapLimits getFactory(TransformerRaw xf) throws PsseModelException;
}

/* Converters for Code CW 1 */

class Cw1Wnd1ToFact implements CwWndToFact
{
	private static final RatioTapLimits[] _Facts;
	static
	{
		_Facts = new RatioTapLimits[]
		{
			W1PassThru.Default,
			W1PassThru.Default,
			W1PassThru.Default,
			null, // should never happen
			W1PassThru.Default,
		};
	}
	@Override
	public RatioTapLimits getFactory(TransformerRaw xf)
			throws PsseModelException
	{
		return _Facts[Math.abs(xf.getCOD1())];
	}
}

class Cw1Wnd2ToFact implements CwWndToFact
{
	private static final RatioTapLimits[] _Facts;
	static
	{
		_Facts = new RatioTapLimits[]
		{
			W2PassThru.Default,
			W2PassThru.Default,
			W2PassThru.Default,
			null, // should never happen
			W2PassThru.Default,
		};
	}

	@Override
	public RatioTapLimits getFactory(TransformerRaw xf)
			throws PsseModelException
	{
		return _Facts[Math.abs(xf.getCOD2())];
	}
}

class Cw1Wnd3ToFact implements CwWndToFact
{
	private static final RatioTapLimits[] _Facts;
	static
	{
		_Facts = new RatioTapLimits[]
		{
			W3PassThru.Default,
			W3PassThru.Default,
			W3PassThru.Default,
			null, // should never happen
			W3PassThru.Default,
		};
	}

	@Override
	public RatioTapLimits getFactory(TransformerRaw xf)
			throws PsseModelException
	{
		return _Facts[Math.abs(xf.getCOD3())];
	}
}

class W1PassThru extends RatioTapLimits
{
	static public final RatioTapLimits	Default	= new W1PassThru();

	@Override
	public Limits getLimits(TransformerRaw xf)
			throws PsseModelException
	{
		return new Limits(xf.getRMI1(), xf.getRMA1());
	}
}

class W2PassThru extends RatioTapLimits
{
	static public final RatioTapLimits	Default	= new W2PassThru();

	@Override
	public Limits getLimits(TransformerRaw xf)
			throws PsseModelException
	{
		return new Limits(xf.getRMI2(), xf.getRMA2());
	}
}

class W3PassThru extends RatioTapLimits
{
	static public final RatioTapLimits	Default	= new W3PassThru();

	@Override
	public Limits getLimits(TransformerRaw xf)
			throws PsseModelException
	{
		return new Limits(xf.getRMI3(), xf.getRMA3());
	}
}


/* Converters for Code CW 2 */

class Cw2Wnd1ToFact implements CwWndToFact
{
	private static final RatioTapLimits[] _Facts;
	static
	{
		_Facts = new RatioTapLimits[]
		{
			W1PassThru.Default,
			Cw2W1.Default,
			Cw2W1.Default,
			null, // should never happen
			W1PassThru.Default,
		};
	}
	@Override
	public RatioTapLimits getFactory(TransformerRaw xf)
			throws PsseModelException
	{
		return _Facts[Math.abs(xf.getCOD1())];
	}
}

class Cw2Wnd2ToFact implements CwWndToFact
{
	private static final RatioTapLimits[] _Facts;
	static
	{
		_Facts = new RatioTapLimits[]
		{
			W2PassThru.Default,
			Cw2W2.Default,
			Cw2W2.Default,
			null, // should never happen
			W2PassThru.Default,
		};
	}

	@Override
	public RatioTapLimits getFactory(TransformerRaw xf)
			throws PsseModelException
	{
		return _Facts[Math.abs(xf.getCOD2())];
	}
}

class Cw2Wnd3ToFact implements CwWndToFact
{
	private static final RatioTapLimits[] _Facts;
	static
	{
		_Facts = new RatioTapLimits[]
		{
			W3PassThru.Default,
			Cw2W3.Default,
			Cw2W3.Default,
			null, // should never happen
			W3PassThru.Default,
		};
	}

	@Override
	public RatioTapLimits getFactory(TransformerRaw xf)
			throws PsseModelException
	{
		return _Facts[Math.abs(xf.getCOD3())];
	}
}

class CwCvt
{
	static Limits cw2(float rmi, float rma, Bus bus) throws PsseModelException
	{
		float kv = bus.getBASKV();
		return new Limits(rmi/kv, rma/kv);
	}

	static Limits cw3(float rmi, float rma, float nomv, Bus bus) throws PsseModelException
	{
		float cwratio = nomv / bus.getBASKV();
		return new Limits(rmi/cwratio, rma/cwratio);
	}
}

class Cw2W1 extends RatioTapLimits
{
	static public final RatioTapLimits	Default	= new Cw2W1();

	@Override
	public Limits getLimits(TransformerRaw xf)
			throws PsseModelException
	{
		
		return CwCvt.cw2(xf.getRMI1(), xf.getRMA1(), xf.getBusI());
	}
}

class Cw2W2 extends RatioTapLimits
{
	static public final RatioTapLimits	Default	= new Cw2W2();

	@Override
	public Limits getLimits(TransformerRaw xf)
			throws PsseModelException
	{
		return CwCvt.cw2(xf.getRMI2(), xf.getRMA2(), xf.getBusJ());
	}
}

class Cw2W3 extends RatioTapLimits
{
	static public final RatioTapLimits	Default	= new Cw2W3();

	@Override
	public Limits getLimits(TransformerRaw xf)
			throws PsseModelException
	{
		return CwCvt.cw2(xf.getRMI3(), xf.getRMA3(), xf.getBusK());
	}
}

/* Converters for Code CW 3 */

class Cw3
{
	
}

class Cw3Wnd1ToFact implements CwWndToFact
{
	private static final RatioTapLimits[] _Facts;
	static
	{
		_Facts = new RatioTapLimits[]
		{
			W1PassThru.Default,
			Cw3W1.Default,
			Cw3W1.Default,
			null, // should never happen
			W1PassThru.Default,
		};
	}
	@Override
	public RatioTapLimits getFactory(TransformerRaw xf)
			throws PsseModelException
	{
		return _Facts[Math.abs(xf.getCOD1())];
	}
}

class Cw3Wnd2ToFact implements CwWndToFact
{
	private static final RatioTapLimits[] _Facts;
	static
	{
		_Facts = new RatioTapLimits[]
		{
			W2PassThru.Default,
			Cw3W2.Default,
			Cw3W2.Default,
			null, // should never happen
			W2PassThru.Default,
		};
	}

	@Override
	public RatioTapLimits getFactory(TransformerRaw xf)
			throws PsseModelException
	{
		return _Facts[Math.abs(xf.getCOD2())];
	}
}

class Cw3Wnd3ToFact implements CwWndToFact
{
	private static final RatioTapLimits[] _Facts;
	static
	{
		_Facts = new RatioTapLimits[]
		{
			W3PassThru.Default,
			Cw3W3.Default,
			Cw3W3.Default,
			null, // should never happen
			W3PassThru.Default,
		};
	}

	@Override
	public RatioTapLimits getFactory(TransformerRaw xf)
			throws PsseModelException
	{
		return _Facts[Math.abs(xf.getCOD3())];
	}
}

class Cw3W1 extends RatioTapLimits
{
	static public final RatioTapLimits	Default	= new Cw3W1();

	@Override
	public Limits getLimits(TransformerRaw xf)
			throws PsseModelException
	{
		return CwCvt.cw3(xf.getRMI1(), xf.getRMA1(),
				xf.getNOMV1(), xf.getBusI());
	}
}

class Cw3W2 extends RatioTapLimits
{
	static public final RatioTapLimits	Default	= new Cw3W2();

	@Override
	public Limits getLimits(TransformerRaw xf)
			throws PsseModelException
	{
		return CwCvt.cw3(xf.getRMI2(), xf.getRMA2(),
				xf.getNOMV2(), xf.getBusJ());
	}
}

class Cw3W3 extends RatioTapLimits
{
	static public final RatioTapLimits	Default	= new Cw3W3();

	@Override
	public Limits getLimits(TransformerRaw xf)
			throws PsseModelException
	{
		return CwCvt.cw3(xf.getRMI3(), xf.getRMA3(),
				xf.getNOMV3(), xf.getBusK());
	}
}

