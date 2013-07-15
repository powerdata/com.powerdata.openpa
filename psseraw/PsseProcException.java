package com.powerdata.openpa.psseraw;

public class PsseProcException extends Exception
{

	private static final long	serialVersionUID	= 1L;

	public PsseProcException() {}
	public PsseProcException(String message) {super(message);}
	public PsseProcException(Throwable cause) {super(cause);}
	public PsseProcException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public PsseProcException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
