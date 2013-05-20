package com.powerdata.openpa.tools.psseproc;

import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PsseClassTransformer extends PsseMultiLineClass
{
	
	public PsseClassTransformer(PsseField[][] colsByLine, String vtable, PsseField[] vcols )
	{
		super("Transformer", colsByLine, vtable, vcols);
	}

	@Override
	protected String[] readRecord(LineNumberReader rdr) throws IOException
	{
		/*
		 * process line 1. Look at the "K" field which is set valid only if this
		 * is a 3-winding transformer. Would be very unlikely for future
		 * versions to change position of this column
		 */
		String[] l1 = _readRecord(rdr);
		ArrayList<String> rv = new ArrayList<>(_cols.length);
		if (l1 != null && l1.length > 0)
		{
			String k = l1[2];
			boolean is3w = !(k == null || k.length() == 0 || k.equals("0"));
			List<String> l1list = Arrays.asList(l1);
			rv.addAll(l1list.subList(0, _lineLengths[0]));

			/* handle remaining lines */
			for (int i = 0; i < 3; ++i)
			{
				String[] l = _readRecord(rdr);
				rv.addAll(correctLength(l, _lineLengths[i + 1]));
			}

			/* if 3-winding, do 5th line */
			String[] l5 = (is3w) ? _readRecord(rdr) : new String[0];
			rv.addAll(correctLength(l5, _lineLengths[4]));

			/*
			 * put the variable columns at the end so that the writer processes
			 * the relationship
			 */
			rv.addAll(l1list.subList(_lineLengths[0], l1list.size()));
		}

		return (rv.isEmpty()) ? null : rv.toArray(new String[rv.size()]);
	}

}
