package com.powerdata.openpa.tools.psseparse;

import com.powerdata.pse.PseModelReaderException;

public abstract class PsseClassWriter
{
	public String processRecord(PsseClass pcclass, String[] tok, int linenumber)
			throws PseModelReaderException
	{
		return processRecord(pcclass, tok, linenumber, null, null);
	}

	public abstract String processRecord(PsseClass pcclass, String[] tok,
			int linenumber, String containerclass, String containerid)
			throws PseModelReaderException;

	public abstract void cleanup() throws PseModelReaderException;
}
