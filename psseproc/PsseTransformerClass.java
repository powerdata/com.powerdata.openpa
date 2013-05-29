package com.powerdata.openpa.psseproc;

public class PsseTransformerClass extends PsseClass
{
	public PsseTransformerClass() {super("Transformer");}

	@Override
	protected boolean hasMoreLines(int lineno, String[] vals)
	{
		String k = vals[2];
		return (lineno < 4 || (!k.isEmpty() && !k.equals("0")));
	}

	
}
