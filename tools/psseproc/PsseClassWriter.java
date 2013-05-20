package com.powerdata.openpa.tools.psseproc;

public abstract class PsseClassWriter
{
	public String processRecord(PsseClass pcclass, String[] tok, int linenumber)
			throws PsseProcException
	{
		return processRecord(pcclass, tok, linenumber, null, null);
	}

	public abstract String processRecord(PsseClass pcclass, String[] tok,
			int linenumber, String containerclass, String containerid)
			throws PsseProcException;

	public abstract void cleanup() throws PsseProcException;
}
