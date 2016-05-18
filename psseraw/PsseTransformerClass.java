package com.powerdata.openpa.psseraw;
/*
 * Copyright (c) 2016, PowerData Corporation, Incremental Systems Corporation
 * All rights reserved.
 * Licensed under the BSD-3 Clause License.
 * See full license at https://powerdata.github.io/openpa/LICENSE.md
 */


public class PsseTransformerClass extends PsseClass
{
	public PsseTransformerClass() {super("Transformer");}

	@Override
	protected boolean hasLine(int lineno, String[] vals)
	{
		if (lineno < 4) return true;
		if (lineno > 4) return false;
		String k = vals[2];
		return !k.isEmpty() && !k.equals("0");
	}

	
}
