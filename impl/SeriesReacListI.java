package com.powerdata.openpa.impl;

import com.powerdata.openpa.ColumnMeta;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.SeriesReac;
import com.powerdata.openpa.SeriesReacList;

public class SeriesReacListI extends ACBranchListI<SeriesReac> implements SeriesReacList
{
	static final ACBranchEnum _PFld = new ACBranchEnum()
	{
		@Override
		public ColumnMeta fbus() {return ColumnMeta.SerreacBUSFROM;}
		@Override
		public ColumnMeta tbus() {return ColumnMeta.SerreacBUSTO;}
		@Override
		public ColumnMeta insvc() {return ColumnMeta.SerreacINSVC;}
		@Override
		public ColumnMeta fp() {return ColumnMeta.SerreacPFROM;}
		@Override
		public ColumnMeta fq() {return ColumnMeta.SerreacQFROM;}
		@Override
		public ColumnMeta tp() {return ColumnMeta.SerreacPTO;}
		@Override
		public ColumnMeta tq() {return ColumnMeta.SerreacQTO;}
		@Override
		public ColumnMeta id() {return ColumnMeta.SerreacID;}
		@Override
		public ColumnMeta name() {return ColumnMeta.SerreacNAME;}
		@Override
		public ColumnMeta r() {return ColumnMeta.SerreacR;}
		@Override
		public ColumnMeta x() {return ColumnMeta.SerreacX;}
		@Override
		public ColumnMeta ratLT() {return ColumnMeta.SerreacRATLT;}
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
}
