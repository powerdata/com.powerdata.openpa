package com.powerdata.openpa.impl;

import com.powerdata.openpa.ColumnMeta;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.SeriesCap;
import com.powerdata.openpa.SeriesCapList;

public class SeriesCapListI extends ACBranchListI<SeriesCap> implements SeriesCapList
{
	static final ACBranchEnum _PFld = new ACBranchEnum()
	{
		@Override
		public ColumnMeta fbus() {return ColumnMeta.SercapBUSFROM;}
		@Override
		public ColumnMeta tbus() {return ColumnMeta.SercapBUSTO;}
		@Override
		public ColumnMeta insvc() {return ColumnMeta.SercapINSVC;}
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
		@Override
		public ColumnMeta ratLT() {return ColumnMeta.SercapRATLT;}
	};

	public SeriesCapListI(PAModelI model, int[] keys) throws PAModelException
	{
		super(model, keys, _PFld);
	}

	public SeriesCapListI(PAModelI model, int size) throws PAModelException
	{
		super(model, size, _PFld);
	}

	public SeriesCapListI() {super();}

	@Override
	public SeriesCap get(int index)
	{
		return new SeriesCap(this, index);
	}
}
