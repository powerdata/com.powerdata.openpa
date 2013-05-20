package com.powerdata.openpa.tools.psseproc;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;

/**
 * Provide a starting point to process a pss/e file.
 * 
 * @author chris@powerdata.com
 * 
 */
public class PsseProcessor
{
	protected PsseHeader		_hdr;
	protected LineNumberReader	_rdr;
	protected String			_ver;
	protected PsseClassSet		_cset;

	public PsseProcessor(Reader rawpsse, String specversion)
			throws IOException, PsseProcException
	{
		_rdr = new LineNumberReader(rawpsse);
		_hdr = new PsseHeader(_rdr);
		String hver = _hdr.getVersion();
		_ver = (hver == null) ? specversion : hver;

		if (_ver == null)
		{
			throw new PsseProcException(
				"Unable to detect version from PSS/e file."
				+ "  Version must be manually specified");
		}
		
		_cset = PsseClassSet.GetClassSetForVersion(_ver);
	}

	public PsseHeader getHeader() {return _hdr;}
	
	public PsseClassSet getClassSet() {return _cset;}
	
	public void process(PsseRecordProc p) throws PsseProcException, IOException
	{
		for (PsseClass pc : _cset.getClasses())
		{
			pc.processRecords(_rdr, p, null, null);
		}
	}
}
