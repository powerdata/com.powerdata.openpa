package com.powerdata.openpa.pwrflow;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;
import com.powerdata.openpa.ColumnMeta;
import com.powerdata.openpa.PAModel;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.pwrflow.CAWorker.Result;
import com.powerdata.openpa.pwrflow.ContingencySet.Contingency;

public abstract class ContingencyManager
{
	protected PAModel _model;
	
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
		ColumnMeta.SvcINSVC,
		
		ColumnMeta.LinePFROM,
		ColumnMeta.LinePTO,
		ColumnMeta.LineQFROM,
		ColumnMeta.LineQTO,
		ColumnMeta.LineINSVC,
		
		ColumnMeta.SercapPFROM,
		ColumnMeta.SercapPTO,
		ColumnMeta.SercapQFROM,
		ColumnMeta.SercapQTO,
		ColumnMeta.SercapINSVC,
			
		ColumnMeta.SerreacPFROM,
		ColumnMeta.SerreacPTO,
		ColumnMeta.SerreacQFROM,
		ColumnMeta.SerreacQTO,
		ColumnMeta.SerreacINSVC,
		
		ColumnMeta.PhashPFROM,
		ColumnMeta.PhashPTO,
		ColumnMeta.PhashQFROM,
		ColumnMeta.PhashQTO,
		ColumnMeta.PhashINSVC,
		
		ColumnMeta.TfmrPFROM,
		ColumnMeta.TfmrPTO,
		ColumnMeta.TfmrQFROM,
		ColumnMeta.TfmrQTO,
		ColumnMeta.TfmrINSVC,
		
		ColumnMeta.GenINSVC,
		
	}));
	
	
	public abstract void runSet(ContingencySet set);
	protected abstract void report(Contingency c, Set<Result> r, PAModel m) throws PAModelException;
	protected abstract void recordException(Contingency c, PAModelException e);

}

