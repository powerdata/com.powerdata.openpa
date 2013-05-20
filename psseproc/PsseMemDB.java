package com.powerdata.openpa.psseproc;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PsseMemDB extends PsseRecordProc
{
	protected PsseHeader _hdr;
	protected List<List<String[]>> _db = new ArrayList<>();
	protected Map<String,List<String[]>> _clmap = new HashMap<>();
	protected PsseClassSet _cset;
	
	public PsseMemDB(Reader rawpsse, String specver) throws IOException, PsseProcException
	{
		PsseProcessor p = new PsseProcessor(rawpsse, specver);
		_hdr = p.getHeader();
		
		_cset = p.getClassSet();
		for(PsseClass c : _cset.getClasses())
		{
			String cn = c.getClassName();
			List<String[]> clist = new ArrayList<>();
			_db.add(clist);
			_clmap.put(cn, clist);
		}
		
		p.process(this);
	}

	@Override
	public String processRecord(PsseClass pcclass, String[] tok,
			int linenumber, String containerclass, String containerid)
			throws PsseProcException
	{
//		String clname = pcclass.getClassName();
//		int ncols = pcclass.getColumns().length;
//		List<String[]> clist = _clmap.get(clname);
//		int nrec = clist.size();
//		clist.add(Arrays.copyOf(tok, ncols));
//		PsseField[] vcols = pcclass.getVarCols();
//		if (vcols != null)
//		{
//			String rtname = pcclass.getVTable();
//			int nvc = vcols.length;
//			List<String[]> vlist = _clmap.get(rtname);
//		
//
//		
//		return String.valueOf(nrec);
		return null;
	}

	@Override
	public void cleanup() throws PsseProcException {}

	public PsseHeader getHeader() {return _hdr;}
}
