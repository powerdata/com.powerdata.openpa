package com.powerdata.openpa.psseproc;

public class PsseTransformerClass extends PsseClass
{
	public PsseTransformerClass() {super("Transformer");}

	@Override
	protected boolean hasMoreLines(int lineno, String[] vals)
	{
		if (lineno < 5) return true;
		if (lineno > 5) return false;
		String k = vals[2];
		boolean dammit = !k.isEmpty() && !k.equals("0");
		return !k.isEmpty() && !k.equals("0");
	}

	
}
