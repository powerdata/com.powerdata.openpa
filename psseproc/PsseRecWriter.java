package com.powerdata.openpa.psseproc;

/**
 * Can implement writers based on destination format (CSV for example), or for
 * more complex handling of certain record types, or both
 */
public interface PsseRecWriter
{
	public void writeRecord(PsseClass pclass, String[] record, PsseClassSet cset)
			throws PsseProcException;
}
