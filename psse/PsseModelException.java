package com.powerdata.openpa.psse;

public class PsseModelException extends Exception
{
	private static final long serialVersionUID = 1L;

	public PsseModelException() { super(); }
	public PsseModelException(String descr) { super(descr);}
	public PsseModelException(Throwable cause) { super(cause); }
	public PsseModelException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PsseModelException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
