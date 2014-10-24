package com.powerdata.openpa.pwrflow;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;
import com.powerdata.openpa.ColumnMeta;
import com.powerdata.openpa.PAModel;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.pwrflow.CAWorker.Results;
import com.powerdata.openpa.pwrflow.ContingencySet.Contingency;

public abstract class ContingencyManager
{
	PAModel _model;
	
	public ContingencyManager(PAModel m)
	{
		_model = m;
	}
	
	protected static Set<ColumnMeta> _localcols = EnumSet.copyOf(Arrays.asList(new ColumnMeta[]
	{
		ColumnMeta.BusVM,
		ColumnMeta.BusVA,
		
		ColumnMeta.ShcapQ,
		ColumnMeta.ShreacQ,
		
		ColumnMeta.SvcQ,
		ColumnMeta.SvcOOS,
		
		ColumnMeta.LinePFROM,
		ColumnMeta.LinePTO,
		ColumnMeta.LineQFROM,
		ColumnMeta.LineQTO,
		ColumnMeta.LineOOS,
		
		ColumnMeta.SercapPFROM,
		ColumnMeta.SercapPTO,
		ColumnMeta.SercapQFROM,
		ColumnMeta.SercapQTO,
		ColumnMeta.SercapOOS,
			
		ColumnMeta.SerreacPFROM,
		ColumnMeta.SerreacPTO,
		ColumnMeta.SerreacQFROM,
		ColumnMeta.SerreacQTO,
		ColumnMeta.SerreacOOS,
		
		ColumnMeta.PhashPFROM,
		ColumnMeta.PhashPTO,
		ColumnMeta.PhashQFROM,
		ColumnMeta.PhashQTO,
		ColumnMeta.PhashOOS,
		
		ColumnMeta.TfmrPFROM,
		ColumnMeta.TfmrPTO,
		ColumnMeta.TfmrQFROM,
		ColumnMeta.TfmrQTO,
		ColumnMeta.TfmrOOS,
		
		ColumnMeta.GenOOS,
		
	}));
	
	
	public abstract void runSet(ContingencySet set);
	protected abstract void report(Contingency c, Results r, PAModel m) throws PAModelException;
	protected abstract void recordException(Contingency c, PAModelException e);

}

