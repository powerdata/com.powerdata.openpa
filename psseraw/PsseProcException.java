package com.powerdata.openpa.psseraw;
/*
 * Copyright (c) 2016, PowerData Corporation, Incremental Systems Corporation
 * All rights reserved.
 * Licensed under the BSD-3 Clause License.
 * See full license at https://powerdata.github.io/openpa/LICENSE.md
 */


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
