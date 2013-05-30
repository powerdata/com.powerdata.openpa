package com.powerdata.openpa.pssecvt;

/**
 * Can implement writers based on destination format (CSV for example), or for
 * more complex handling of certain record types, or both
 */
public interface PsseRecWriter
{
	public void writeRecord(PsseClass pclass, String[] record)
			throws PsseProcException;
	
	public abstract void cleanup();
}
