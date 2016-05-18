package com.powerdata.openpa.psseraw;
/*
 * Copyright (c) 2016, PowerData Corporation, Incremental Systems Corporation
 * All rights reserved.
 * Licensed under the BSD-3 Clause License.
 * See full license at https://powerdata.github.io/openpa/LICENSE.md
 */


/**
 * Can implement writers based on destination format (CSV for example), or for
 * more complex handling of certain record types, or both
 * 
 * @author chris@powerdata.com
 */
public interface PsseRecWriter
{
	public void writeRecord(PsseClass pclass, String[] record)
			throws PsseProcException;
}
