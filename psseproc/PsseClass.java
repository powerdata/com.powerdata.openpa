package com.powerdata.openpa.psseproc;

import java.io.IOException;
import java.io.LineNumberReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.powerdata.openpa.tools.StringParse;

public class PsseClass
{
	protected String _classnm;
	protected PsseField[] _cols;
	protected PsseField[] _varcols;
	protected String _vtable;
	
	public PsseClass(String classname, PsseField[] cols)
	{
		_classnm = classname;
		_cols = cols;
	}

	public PsseClass(String classname, PsseField[] cols, String vtable,
			PsseField[] varcols)
	{
		_classnm = classname;
		_cols = cols;
		_varcols = varcols;
		_vtable = vtable;
	}
	
	public String getClassName() {return _classnm;}
	public PsseField[] getColumns() {return _cols;}
	public PsseField[] getVarCols() {return _varcols;}
	public String getVTable() {return _vtable;}
	

	public void processRecords(LineNumberReader rdr, PsseRecordProc wrtr,
			String containerclass, String containerid) throws IOException,
			PsseProcException
	{
		int lno = rdr.getLineNumber()+1;
		String[] tokens = readRecord(rdr);
		if (tokens != null)
		{
			String cn = getClassName();
			if (cn.equals("VoltageSourceConverterDCLine") ||
				cn.equals("FACTSDevice"))
			{
				throw new PsseProcException(cn+" not validated");
			}
		}
		while (tokens != null)
		{
			wrtr.processRecord(this, tokens, lno);
			lno = rdr.getLineNumber()+1;
			tokens = readRecord(rdr);
		}
	}
	
	public void processRecords(LineNumberReader rdr, PsseRecordProc wrtr,
			int count, String containerclass, String containerid)
			throws IOException, PsseProcException
	{
		for(int i=0; i < count; ++i)
		{
			int lno = rdr.getLineNumber()+1;
			wrtr.processRecord(this, readRecord(rdr), lno, containerclass, containerid);
		}
	}
	
	protected String[] readRecord(LineNumberReader rdr) throws IOException
	{
		List<String> rv = correctLength(_readRecord(rdr), _cols.length); 
		return (rv == null) ? null : rv.toArray(new String[rv.size()]);
	}
	
	protected String[] _readRecord(LineNumberReader rdr) throws IOException
	{
		String l = rdr.readLine().trim();
		String[] rv;
		if (hasNext(l))
		{
			ArrayList<String> rvl = new ArrayList<>();
			StringParse sp = new StringParse(l, " ,");
			sp.setQuoteChar('\'');
			
			while (sp.hasMoreTokens())
			{
				rvl.add(sp.nextToken().trim());
			}
			rv = rvl.toArray(new String[rvl.size()]);
		}
		else
		{
			return null;
		}
		return rv; 
	}

	protected boolean hasNext(String l)
	{
		boolean rv = true;
		
		// look for comments, remove and re-trim;
		StringParse sp = new StringParse(l, "/");
		sp.setQuoteChar('\'');
		String vl = sp.nextToken();
		if (vl.trim().equals("0"))
			rv = false;
		return rv;
	}

	@SuppressWarnings("unchecked")
	protected static <T> T[] mergeSet(T[][] set)
	{
		int sum = 0;
		for(T[] s : set)
		{
			sum += s.length;
		}
		T[] rv = (T[]) Array.newInstance(set[0][0].getClass(), sum);
		int dx = 0;
		for(T[] s : set)
		{
			int len = s.length;
			System.arraycopy(s, 0, rv, dx, len);
			dx += len;
		}
		return rv;
	}

	protected List<String> correctLength(String[] tokens, int length)
	{
		return (tokens == null) ? null : correctLength(Arrays.asList(tokens), length);
	}
	
	protected List<String> correctLength(List<String> tokens, int length)
	{
		if (tokens == null) return null;
		int tlen = tokens.size();
		List<String> rv = tokens; 
		if (tlen < length)
		{
			rv = new ArrayList<>(rv);
			while (tlen++ < length) rv.add("");
		}
		return rv;
	}
	

}