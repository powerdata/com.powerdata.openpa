package com.powerdata.openpa.impl;

import com.powerdata.openpa.ColumnMeta;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.TwoTermDCLine;
import com.powerdata.openpa.TwoTermDCLineList;

public class TwoTermDCLineListI extends TwoTermDevListI<TwoTermDCLine> implements TwoTermDCLineList
{
	static final TwoTermDevEnum _PFld = new TwoTermDevEnum()
	{
		@Override
		public ColumnMeta id() {return ColumnMeta.T2dcID;}
		@Override
		public ColumnMeta name() {return ColumnMeta.T2dcNAME;}
		@Override
		public ColumnMeta fbus() {return ColumnMeta.T2dcBUSFROM;}
		@Override
		public ColumnMeta tbus() {return ColumnMeta.T2dcBUSTO;}
		@Override
		public ColumnMeta insvc() {return ColumnMeta.T2dcINSVC;}
		@Override
		public ColumnMeta fp() {return ColumnMeta.T2dcPFROM;}
		@Override
		public ColumnMeta fq() {return ColumnMeta.T2dcQFROM;}
		@Override
		public ColumnMeta tp() {return ColumnMeta.T2dcPTO;}
		@Override
		public ColumnMeta tq() {return ColumnMeta.T2dcQTO;}
	};

	public TwoTermDCLineListI(PAModelI model, int[] keys) throws PAModelException
	{
		super(model, keys, _PFld);
	}
	public TwoTermDCLineListI(PAModelI model, int size) throws PAModelException
	{
		super(model, size, _PFld);
	}

	public TwoTermDCLineListI() {super();}

	@Override
	public TwoTermDCLine get(int index)
	{
		return new TwoTermDCLine(this, index);
	}
}
