package com.powerdata.openpa.psseraw;

import java.io.IOException;
import java.io.LineNumberReader;

/**
 * PsseClass that can parse and process raw PSS/e MultiTerminal DC Line records
 * 
 * @author chris@powerdata.com
 * 
 */
public class MultiTerminalDCLine extends PsseClass
{
	public MultiTerminalDCLine() {super("MultiTerminalDCLine");}

	@Override
	public void processRecords(LineNumberReader rdr, PsseRecWriter wrtr,
			PsseClassSet cset) throws PsseProcException
	{
		PsseField[] fields = getLines().get(0);
		String[] rec = new String[fields.length];
		try
		{
			String l = readLine(rdr);
			while (isRecord(l))
			{
				loadTokens(rec, 0, fields, l, 0);
				wrtr.writeRecord(this, rec);

				processInner(rdr, wrtr, cset.getMultiTermDC_ACConv(), rec[1], rec[0]);
				processInner(rdr, wrtr, cset.getMultiTermDCBus(), rec[2], rec[0]);
				processInner(rdr, wrtr, cset.getMultiTermDCLink(), rec[3], rec[0]);

				l = readLine(rdr);
			}
		} catch (IOException ioe) { throw new PsseProcException(ioe); }
	}

	protected void processInner(LineNumberReader rdr, PsseRecWriter wrtr,
			PsseClass inner, String scount, String dcline) throws IOException, PsseProcException
	{
		int count = Integer.parseInt(scount);
		PsseField[] fields = inner.getLines().get(0);
		for (int i=0; i < count; ++i)
		{
			String[] rec = new String[fields.length+1];
			loadTokens(rec, 1, fields, readLine(rdr), 0);
			rec[0] = dcline;
			wrtr.writeRecord(inner, rec);
		}
	}

	
}
