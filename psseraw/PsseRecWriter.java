package com.powerdata.openpa.psseraw;

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
