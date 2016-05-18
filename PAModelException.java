package com.powerdata.openpa;
/*
 * Copyright (c) 2016, PowerData Corporation, Incremental Systems Corporation
 * All rights reserved.
 * Licensed under the BSD-3 Clause License.
 * See full license at https://powerdata.github.io/openpa/LICENSE.md
 */


public class PAModelException extends Exception 
{
	private static final long	serialVersionUID	= 1L;

	public PAModelException() {}
	
	public PAModelException(String message)
	{
		super(message);
	}

	public PAModelException(Throwable cause)
	{
		super(cause);
	}

	public PAModelException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public PAModelException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
