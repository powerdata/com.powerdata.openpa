package com.powerdata.openpa.psseproc;

import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

public class PsseMultiLineClass extends PsseClass
{
	protected int[] _lineLengths;

	public PsseMultiLineClass(String classname, PsseField[][] cols)
	{
		super(classname, mergeSet(cols));
		setupLengths(cols);
	}

	public PsseMultiLineClass(String classname, PsseField[][] cols, String vtable,
			PsseField[] varcols)
	{
		super(classname, mergeSet(cols), vtable, varcols);
		setupLengths(cols);
	}

	private void setupLengths(PsseField[][] cols)
	{
		int nline = cols.length;
		_lineLengths = new int[nline];
		for(int i=0; i < nline; ++i)
			_lineLengths[i] = cols[i].length;
	}

	@Override
	protected String[] readRecord(LineNumberReader rdr) throws IOException
	{
		int nlines = _lineLengths.length;
		ArrayList<String> rv = new ArrayList<>();
		List<String> rec = correctLength(_readRecord(rdr), _lineLengths[0]);
		if (rec == null) return null;
		rv.addAll(rec);
		for (int i=1; i < nlines; ++i)
		{
			rec = correctLength(_readRecord(rdr), _lineLengths[i]);
			rv.addAll(rec);
		}
		return rv.toArray(new String[rv.size()]);
	}
	
	
}
