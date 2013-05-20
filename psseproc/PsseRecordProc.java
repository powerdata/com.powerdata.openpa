package com.powerdata.openpa.psseproc;

/**
 * Process individual records.  Subclass to provide appropriate implementation.
 * 
 * @author chris@powerdata.com
 *
 */

public abstract class PsseRecordProc
{
	/**
	 * For PSS/e records contained on a single line, or can be multiline if all
	 * lines are attributes of the same class
	 * 
	 * @param pcclass Class to process
	 * @param tok tokenized PSS/e line
	 * @param linenumber line number in the PSS/e file
	 * @return id that can be used to associate multiline records that use more than a single class
	 * @throws PsseProcException
	 */
	public String processRecord(PsseClass pcclass, String[] tok, int linenumber)
			throws PsseProcException
	{
		return processRecord(pcclass, tok, linenumber, null, null);
	}

	/**
	 * For PSS/e records that span multiple lines which also correspond to different classes
	 * 
	 * @param pcclass Class to process
	 * @param tok tokenized PSS/e line
	 * @param linenumber current line number in PSS/e file
	 * @param containerclass Container class if this is a related class
	 * @param containerid Container id if this class is realted to a multiline parent
	 * @return id that can be used to associate multiline records that use more than a single class
	 * @throws PsseProcException
	 */
	public abstract String processRecord(PsseClass pcclass, String[] tok,
			int linenumber, String containerclass, String containerid)
			throws PsseProcException;

	public abstract void cleanup() throws PsseProcException;
}
