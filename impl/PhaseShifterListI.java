package com.powerdata.openpa.impl;

import com.powerdata.openpa.ColumnMeta;
import com.powerdata.openpa.ListMetaType;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.PhaseShifter;
import com.powerdata.openpa.PhaseShifter.ControlMode;
import com.powerdata.openpa.PhaseShifterList;

public class PhaseShifterListI extends TransformerBaseListI<PhaseShifter> implements PhaseShifterList
{
	static final TransBaseEnum _PFld = new TransBaseEnum()
	{
		@Override
		public ColumnMeta fbus() {return ColumnMeta.PhashBUSFROM;}
		@Override
		public ColumnMeta tbus() {return ColumnMeta.PhashBUSTO;}
		@Override
		public ColumnMeta insvc() {return ColumnMeta.PhashOOS;}
		@Override
		public ColumnMeta fp() {return ColumnMeta.PhashPFROM;}
		@Override
		public ColumnMeta fq() {return ColumnMeta.PhashQFROM;}
		@Override
		public ColumnMeta tp() {return ColumnMeta.PhashPTO;}
		@Override
		public ColumnMeta tq() {return ColumnMeta.PhashQTO;}
		@Override
		public ColumnMeta id() {return ColumnMeta.PhashID;}
		@Override
		public ColumnMeta name() {return ColumnMeta.PhashNAME;}
		@Override
		public ColumnMeta r() {return ColumnMeta.PhashR;}
		@Override
		public ColumnMeta x() {return ColumnMeta.PhashX;}
		@Override
		public ColumnMeta gmag() {return ColumnMeta.PhashGMAG;}
		@Override
		public ColumnMeta bmag() {return ColumnMeta.PhashBMAG;}
		@Override
		public ColumnMeta shift() {return ColumnMeta.PhashANG;}
		@Override
		public ColumnMeta ftap() {return ColumnMeta.PhashTAPFROM;}
		@Override
		public ColumnMeta ttap() {return ColumnMeta.PhashTAPTO;}
		@Override
		public ColumnMeta ratLT() {return ColumnMeta.PhashRATLT;}
	};

	EnumData<ControlMode> _cmode = new EnumData<ControlMode>(ColumnMeta.PhashCTRLMODE);
	
	public PhaseShifterListI(PAModelI model, int[] keys) throws PAModelException
	{
		super(model, keys, _PFld);
	}
	public PhaseShifterListI(PAModelI model, int size) throws PAModelException
	{
		super(model, size, _PFld);
	}
	public PhaseShifterListI()
	{
		super();
	}
	@Override
	public PhaseShifter get(int index)
	{
		return new PhaseShifter(this, index);
	}
	@Override
	public ListMetaType getListMeta()
	{
		return ListMetaType.PhaseShifter;
	}
	@Override
	public ControlMode getControlMode(int ndx) throws PAModelException
	{
		return _cmode.get(ndx);
	}
	@Override
	public ControlMode[] getControlMode() throws PAModelException
	{
		return _cmode.get();
	}
	@Override
	public void setControlMode(int ndx, ControlMode m) throws PAModelException
	{
		_cmode.set(ndx, m);
	}
	@Override
	public void setControlMode(ControlMode[] m) throws PAModelException
	{
		_cmode.set(m);
	}
}
