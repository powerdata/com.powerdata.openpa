package com.powerdata.openpa.psseproc;

import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.powerdata.openpa.tools.StringParse;

/**
 * Tracks PSS/e fields for a given object type.  Allows a single record to be
 * specified over multiple lines.  Lines can contain a variable list of related
 * records.
 * 
 * @author chris
 *
 */
public class PsseClass
{
	protected static final String parseDelim = " ,";
	protected static final char quoteChar = '\'';
	
	protected String _classnm;
	protected ArrayList<PsseField[]> _lines = new ArrayList<>();

	
	public PsseClass(String classname)
	{
		_classnm = classname;
	}
	
	public void addLine(PsseField[] fields)
	{
		_lines.add(fields);
	}
	
	public String getClassName() {return _classnm;}
	public List<PsseField[]> getLines() {return _lines;}
	

	

	protected String readLine(LineNumberReader r) throws IOException {return r.readLine().trim();}
	
	public void processRecords(LineNumberReader rdr, PsseRecWriter wrtr,
			PsseClassSet cset) throws PsseProcException
	{
		List<PsseField[]> lines = getLines();
		int nfld = 0;
		for (PsseField[] line : lines)
			nfld += line.length;
		String[] rv = null;

		try
		{
			String l = readLine(rdr);
			while(isRecord(l))
			{
				rv = new String[nfld];
				int rvofs = 0;
				int iline = 0;
				while (l != null)
				{
					PsseField[] pl = lines.get(iline++);
					rvofs = loadTokens(rv, 0, pl, l, rvofs);
					l = hasLine(iline, rv) ? readLine(rdr) : null;
					Arrays.fill(rv, rvofs, rv.length, "");
				}
				wrtr.writeRecord(this, rv);
				l = readLine(rdr);
			}
		} catch (IOException ex)
		{
			throw new PsseProcException(ex);
		}
	}
	
	protected int loadTokens(String[] rec, int recstart, PsseField[] pl, String l, int rvofs)
	{
		rvofs += recstart;
		int endofs = rvofs + pl.length;
		StringParse sp = parseLine(l);
		while (sp.hasMoreTokens())
			rec[rvofs++] = sp.nextToken().trim();
		Arrays.fill(rec, rvofs, endofs, "");
		return endofs;
		
	}
	
	protected boolean hasLine(int lineno, String[] vals) {return lineno < _lines.size();}

	protected boolean isRecord(String l)
	{
		boolean rv = true;
		
		// look for comments, remove and re-trim;
		int ndx = l.indexOf('/');
		if (ndx != -1 && l.substring(0, ndx).trim().equals("0"))
			rv = false;
		return rv;
	}

	protected StringParse parseLine(String line)
	{
		StringParse sp = new StringParse(line, parseDelim);
		sp.setQuoteChar(quoteChar);
		return sp;
	}

	@Override
	public String toString() {return getClassName();}
	

}