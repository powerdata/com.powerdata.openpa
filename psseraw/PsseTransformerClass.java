package com.powerdata.openpa.psseraw;

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
