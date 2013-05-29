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
	

//	public void processRecords(LineNumberReader rdr, PsseRecordProc wrtr) throws IOException,
//			PsseProcException
//	{
//		int lno = rdr.getLineNumber()+1;
//		String[] tokens = readRecord(rdr);
//		if (tokens != null)
//		{
//			String cn = getClassName();
//			if (cn.equals("VoltageSourceConverterDCLine") ||
//				cn.equals("FACTSDevice"))
//			{
//				throw new PsseProcException(cn+" not validated");
//			}
//		}
//		while (tokens != null)
//		{
//			wrtr.processRecord(this, tokens, lno);
//			lno = rdr.getLineNumber()+1;
//			tokens = readRecord(rdr);
//		}
//	}
	
//	protected void processRecords(LineNumberReader rdr, PsseRecordProc wrtr,
//			int count, String containerclass, String containerid)
//			throws IOException, PsseProcException
//	{
//		for(int i=0; i < count; ++i)
//		{
//			int lno = rdr.getLineNumber()+1;
//			wrtr.processRecord(this, readRecord(rdr), lno, containerclass, containerid);
//		}
//	}
//	
//	protected String[] readRecord(LineNumberReader rdr) throws IOException
//	{
//		List<String> rv = correctLength(_readRecord(rdr), _cols.length); 
//		return (rv == null) ? null : rv.toArray(new String[rv.size()]);
//	}
//	
//	protected String[] _readRecord(LineNumberReader rdr) throws IOException
//	{
//		String l = rdr.readLine().trim();
//
//		if (hasNext(l))
//		{
//			ArrayList<String> rvl = new ArrayList<>();
//			StringParse sp = new StringParse(l, " ,");
//			sp.setQuoteChar('\'');
//			
//			while (sp.hasMoreTokens())
//			{
//				rvl.add(sp.nextToken().trim());
//			}
//			rv = rvl.toArray(new String[rvl.size()]);
//		}
//		else
//		{
//			return null;
//		}
//		return rv; 
//	}

	public String[] readRecord(LineNumberReader rdr) throws PsseProcException
	{
		List<PsseField[]> lines = getLines();
		int nfld = 0;
		for (PsseField[] line : lines)
			nfld += line.length;
		String[] rv = null;

		try
		{
			String l = rdr.readLine().trim();
			if (isRecord(l))
			{
				rv = new String[nfld];
				int rvofs = 0;
				int iline = 0;
				while (hasMoreLines(iline, rv))
				{
					PsseField[] pl = lines.get(iline++);
					int endofs = rvofs + pl.length;
					StringParse sp = parseLine(l);
					while (sp.hasMoreTokens())
						rv[rvofs++] = sp.nextToken().trim();
					Arrays.fill(rv, rvofs, endofs, "");
					rvofs = endofs;
					l = rdr.readLine().trim();
				}
			}
		} catch (IOException ex)
		{
			throw new PsseProcException(ex);
		}
		return rv;
	}
	
	protected boolean hasMoreLines(int lineno, String[] vals) {return lineno < _lines.size();}

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
	
//	@SuppressWarnings("uncheced")
//	protected static <T> T[] mergeSet(T[][] set)
//	{
//		int sum = 0;
//		for(T[] s : set)
//		{
//			sum += s.length;
//		}
//		T[] rv = (T[]) Array.newInstance(set[0][0].getClass(), sum);
//		int dx = 0;
//		for(T[] s : set)
//		{
//			int len = s.length;
//			System.arraycopy(s, 0, rv, dx, len);
//			dx += len;
//		}
//		return rv;
//	}
//
//	protected List<String> correctLength(String[] tokens, int length)
//	{
//		return (tokens == null) ? null : correctLength(Arrays.asList(tokens), length);
//	}
	
//	protected List<String> correctLength(List<String> tokens, int length)
//	{
//		if (tokens == null) return null;
//		int tlen = tokens.size();
//		List<String> rv = tokens; 
//		if (tlen < length)
//		{
//			rv = new ArrayList<>(rv);
//			while (tlen++ < length) rv.add("");
//		}
//		return rv;
//	}
//	

}