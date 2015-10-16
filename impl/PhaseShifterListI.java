package com.powerdata.openpa.impl;

import com.powerdata.openpa.ColumnMeta;
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
		public ColumnMeta insvc() {return ColumnMeta.PhashINSVC;}
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
	BoolData _hasreg = new BoolData(ColumnMeta.PhashHASREG);
	FloatData _maxang = new FloatData(ColumnMeta.PhashMXANG);
	FloatData _minang = new FloatData(ColumnMeta.PhashMNANG);
	FloatData _maxmw = new FloatData(ColumnMeta.PhashMXMW);
	FloatData _minmw = new FloatData(ColumnMeta.PhashMNMW);

	
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
	@Override
	public boolean hasReg(int ndx) throws PAModelException
	{
		return _hasreg.get(ndx);
	}
	@Override
	public boolean[] hasReg() throws PAModelException
	{
		return _hasreg.get();
	}
	@Override
	public void setReg(int ndx, boolean v) throws PAModelException
	{
		_hasreg.set(ndx, v);
	}
	@Override
	public void setReg(boolean[] v) throws PAModelException
	{
		_hasreg.set(v);
	}
	@Override
	public float getMaxAng(int ndx) throws PAModelException
	{
		return _maxang.get(ndx);
	}
	@Override
	public float[] getMaxAng() throws PAModelException
	{
		return _maxang.get();
	}
	@Override
	public void setMaxAng(int ndx, float v) throws PAModelException
	{
		_maxang.set(ndx, v);
	}
	@Override
	public void setMaxAng(float[] v) throws PAModelException
	{
		_maxang.set(v);
	}
	@Override
	public float getMinAng(int ndx) throws PAModelException
	{
		return _minang.get(ndx);
	}
	@Override
	public void setMinAng(int ndx, float v) throws PAModelException
	{
		_minang.set(ndx, v);
	}
	@Override
	public float[] getMinAng() throws PAModelException
	{
		return _minang.get();
	}
	@Override
	public void setMinAng(float[] v) throws PAModelException
	{
		_minang.set(v);
	}
	@Override
	public float getRegMaxMW(int ndx) throws PAModelException
	{
		return _maxmw.get(ndx);
	}
	@Override
	public float[] getRegMaxMW() throws PAModelException
	{
		return _maxmw.get();
	}
	@Override
	public void setRegMaxMW(int ndx, float mw) throws PAModelException
	{
		_maxmw.set(ndx, mw);
	}
	@Override
	public void setRegMaxMW(float[] mw) throws PAModelException
	{
		_maxmw.set(mw);
	}
	@Override
	public float getRegMinMW(int ndx) throws PAModelException
	{
		return _minmw.get(ndx);
	}
	@Override
	public void setRegMinMW(int ndx, float mw) throws PAModelException
	{
		_minmw.set(ndx, mw);
	}
	@Override
	public float[] getRegMinMW() throws PAModelException
	{
		return _minmw.get();
	}
	@Override
	public void setRegMinMW(float[] mw) throws PAModelException
	{
		_minmw.set(mw);
	}
}
