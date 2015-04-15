package com.powerdata.openpa.psseraw;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;

/**
 * Provide a starting point to process a pss/e file.  Subclass and provide appropriate writers for specific applications
 * 
 * @author chris@powerdata.com
 * 
 */
public abstract class PsseProcessor
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
	
	public PsseClassSet getPsseClassSet() {return _cset;}
	
	public void process() throws PsseProcException, IOException
	{
		for (PsseClass pc : getPsseClassSet().getPsseClasses())
		{
			PsseRecWriter w = getWriter(pc.getClassName());
			if (!pc.getLines().isEmpty())
				pc.processRecords(_rdr, w, _cset);
		}
	}

	protected abstract PsseRecWriter getWriter(String psseClassName);

	
}
