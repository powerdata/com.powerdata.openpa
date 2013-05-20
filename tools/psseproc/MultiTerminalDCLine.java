package com.powerdata.openpa.tools.psseproc;

import java.io.IOException;
import java.io.LineNumberReader;

public class MultiTerminalDCLine extends PsseClass
{
	protected int _nconvIdx;
	protected int _ndcbsIdx;
	protected int _ndclnIdx;
	private PsseClass _acconv;
	private PsseClass _dcbus;
	private PsseClass _dclink;
	

	public MultiTerminalDCLine(PsseField[] cols, PsseClass acconv,
			PsseClass dcbus, PsseClass dclink)
	{
		super("MultiTerminalDCLine", cols);
		
		int ncol = cols.length;
		for(int i=0; i < ncol; ++i)
		{
			PsseField f = cols[i];
			switch(f.getName())
			{
				case "NCONV":
					_nconvIdx = i;
					break;
				case "NDCBS":
					_ndcbsIdx = i;
					break;
				case "NDCLN":
					_ndclnIdx = i;
			}
		}
		_acconv = acconv;
		_dcbus = dcbus;
		_dclink = dclink;
	}

	@Override
	public void processRecords(LineNumberReader rdr, PsseRecordProc wrtr,
			String containerclass, String containerid) throws IOException,
			PsseProcException
	{
		String[] tok = readRecord(rdr);
		while (tok != null)
		{
			String lineid = wrtr.processRecord(this, tok,
					rdr.getLineNumber());
			_acconv.processRecords(rdr, wrtr, Integer.parseInt(tok[_nconvIdx]),
					getClassName(), lineid);
			_dcbus.processRecords(rdr, wrtr, Integer.parseInt(tok[_ndcbsIdx]),
					getClassName(), lineid);
			_dclink.processRecords(rdr, wrtr, Integer.parseInt(tok[_ndclnIdx]),
					getClassName(), lineid);
			tok = readRecord(rdr);
		}

	}
}
