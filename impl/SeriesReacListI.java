package com.powerdata.openpa.impl;

import com.powerdata.openpa.ColumnMeta;
import com.powerdata.openpa.ListMetaType;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.SeriesReac;
import com.powerdata.openpa.SeriesReacList;

public class SeriesReacListI extends ACBranchListI<SeriesReac> implements SeriesReacList
{
	static final ACBranchEnum _PFld = new ACBranchEnum()
	{
		@Override
		public ColumnMeta fbus() {return ColumnMeta.SercapBUSFROM;}
		@Override
		public ColumnMeta tbus() {return ColumnMeta.SercapBUSTO;}
		@Override
		public ColumnMeta insvc() {return ColumnMeta.SercapOOS;}
		@Override
		public ColumnMeta fp() {return ColumnMeta.SercapPFROM;}
		@Override
		public ColumnMeta fq() {return ColumnMeta.SercapQFROM;}
		@Override
		public ColumnMeta tp() {return ColumnMeta.SercapPTO;}
		@Override
		public ColumnMeta tq() {return ColumnMeta.SercapQTO;}
		@Override
		public ColumnMeta id() {return ColumnMeta.SercapID;}
		@Override
		public ColumnMeta name() {return ColumnMeta.SercapNAME;}
		@Override
		public ColumnMeta r() {return ColumnMeta.SercapR;}
		@Override
		public ColumnMeta x() {return ColumnMeta.SercapX;}
	};


	public SeriesReacListI() {super();}
	
	public SeriesReacListI(PAModelI model, int[] keys) throws PAModelException
	{
		super(model, keys, _PFld);
	}
	public SeriesReacListI(PAModelI model, int size) throws PAModelException
	{
		super(model, size, _PFld);
	}

	@Override
	public SeriesReac get(int index)
	{
		return new SeriesReac(this, index);
	}

	@Override
	public ListMetaType getListMeta()
	{
		return ListMetaType.SeriesReac;
	}
}
