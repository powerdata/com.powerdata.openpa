package com.powerdata.openpa.psseproc;

import java.io.IOException;
import java.io.LineNumberReader;


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
				loadTokens(rec, fields, l, 0);
				wrtr.writeRecord(this, rec);

				processInner(rdr, wrtr, cset.getMultiTermDC_ACConv(), rec[1]);
				processInner(rdr, wrtr, cset.getMultiTermDCBus(), rec[2]);
				processInner(rdr, wrtr, cset.getMultiTermDCLink(), rec[3]);
			}
		} catch (IOException ioe) { throw new PsseProcException(ioe); }
	}

	protected void processInner(LineNumberReader rdr, PsseRecWriter wrtr,
			PsseClass inner, String scount) throws IOException, PsseProcException
	{
		int count = Integer.parseInt(scount);
		PsseField[] fields = getLines().get(0);
		for (int i=0; i < count; ++i)
		{
			String[] rec = new String[fields.length];
			loadTokens(rec, fields, readLine(rdr), 0);
			wrtr.writeRecord(inner, rec);
		}
	}

	
}
