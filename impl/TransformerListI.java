package com.powerdata.openpa.impl;

import com.powerdata.openpa.Bus;
import com.powerdata.openpa.ColumnMeta;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.Transformer;
import com.powerdata.openpa.TransformerList;

public class TransformerListI extends TransformerBaseListI<Transformer> implements TransformerList
{
	static final TransBaseEnum _PFld = new TransBaseEnum()
	{
		@Override
		public ColumnMeta fbus() {return ColumnMeta.TfmrBUSFROM;}
		@Override
		public ColumnMeta tbus() {return ColumnMeta.TfmrBUSTO;}
		@Override
		public ColumnMeta insvc() {return ColumnMeta.TfmrINSVC;}
		@Override
		public ColumnMeta fp() {return ColumnMeta.TfmrPFROM;}
		@Override
		public ColumnMeta fq() {return ColumnMeta.TfmrQFROM;}
		@Override
		public ColumnMeta tp() {return ColumnMeta.TfmrPTO;}
		@Override
		public ColumnMeta tq() {return ColumnMeta.TfmrQTO;}
		@Override
		public ColumnMeta id() {return ColumnMeta.TfmrID;}
		@Override
		public ColumnMeta name() {return ColumnMeta.TfmrNAME;}
		@Override
		public ColumnMeta r() {return ColumnMeta.TfmrR;}
		@Override
		public ColumnMeta x() {return ColumnMeta.TfmrX;}
		@Override
		public ColumnMeta gmag() {return ColumnMeta.TfmrGMAG;}
		@Override
		public ColumnMeta bmag() {return ColumnMeta.TfmrBMAG;}
		@Override
		public ColumnMeta shift() {return ColumnMeta.TfmrANG;}
		@Override
		public ColumnMeta ftap() {return ColumnMeta.TfmrTAPFROM;}
		@Override
		public ColumnMeta ttap() {return ColumnMeta.TfmrTAPTO;}
		@Override
		public ColumnMeta ratLT() {return ColumnMeta.TfmrRATLT;}
	};
	
	BoolData _isreg = new BoolData(ColumnMeta.TfmrREGENAB),
			_hasreg = new BoolData(ColumnMeta.TfmrHASREG);
	
	IntData _tapbus = new IntData(ColumnMeta.TfmrTAPBUS);
	FloatData _minkv = new FloatData(ColumnMeta.TfmrMINREGKV),
			_maxkv = new FloatData(ColumnMeta.TfmrMAXREGKV),
			_fmntap = new FloatData(ColumnMeta.TfmrMNTPFROM),
			_fmxtap = new FloatData(ColumnMeta.TfmrMXTPFROM),
			_tmntap = new FloatData(ColumnMeta.TfmrMNTPTO),
			_tmxtap = new FloatData(ColumnMeta.TfmrMXTPTO),
			_fstep = new FloatData(ColumnMeta.TfmrSTEPFROM),
			_tstep = new FloatData(ColumnMeta.TfmrSTEPTO);
	IntData _rbus = new IntData(ColumnMeta.TfmrREGBUS);

	public TransformerListI() {super();}
	
	public TransformerListI(PAModelI model, int[] keys) throws PAModelException
	{
		super(model, keys, _PFld);
	}
	public TransformerListI(PAModelI model, int size) throws PAModelException
	{
		super(model, size, _PFld);
	}

	@Override
	public Transformer get(int index)
	{
		return new Transformer(this, index);
	}

	@Override
	public boolean isRegEnabled(int ndx) throws PAModelException
	{
		return _isreg.get(ndx);
	}

	@Override
	public void setRegEnabled(int ndx, boolean enabl) throws PAModelException
	{
		_isreg.set(ndx, enabl);
	}

	@Override
	public boolean[] isRegEnabled() throws PAModelException
	{
		return _isreg.get();
	}

	@Override
	public void setRegEnabled(boolean[] enabl) throws PAModelException
	{
		_isreg.set(enabl);
	}

	@Override
	public Bus getTapBus(int ndx) throws PAModelException
	{
		return _buses.get(_tapbus.get(ndx));
	}

	@Override
	public Bus[] getTapBus() throws PAModelException
	{
		return _buses.toArray(_tapbus.get());
	}

	@Override
	public void setTapBus(int ndx, Bus s) throws PAModelException
	{
		_tapbus.set(ndx, s.getIndex());
	}

	@Override
	public void setTapBus(Bus[] s) throws PAModelException
	{
		_tapbus.set(_buses.getIndexes(s));
	}

	@Override
	public float getMinKV(int ndx) throws PAModelException
	{
		return _minkv.get(ndx);
	}

	@Override
	public void setMinKV(int ndx, float kv) throws PAModelException
	{
		_minkv.set(ndx, kv);
	}

	@Override
	public float[] getMinKV() throws PAModelException
	{
		return _minkv.get();
	}

	@Override
	public void setMinKV(float[] kv) throws PAModelException
	{
		_minkv.set(kv);
	}

	@Override
	public float getMaxKV(int ndx) throws PAModelException
	{
		return _maxkv.get(ndx);
	}

	@Override
	public void setMaxKV(int ndx, float kv) throws PAModelException
	{
		_maxkv.set(ndx, kv);
	}

	@Override
	public float[] getMaxKV() throws PAModelException
	{
		return _maxkv.get();
	}

	@Override
	public void setMaxKV(float[] kv) throws PAModelException
	{
		_maxkv.set(kv);
	}

	@Override
	public Bus getRegBus(int ndx) throws PAModelException
	{
		return _buses.get(_rbus.get(ndx));
	}

	@Override
	public void setRegBus(int ndx, Bus b) throws PAModelException
	{
		_rbus.set(ndx, b.getIndex());
	}

	@Override
	public Bus[] getRegBus() throws PAModelException
	{
		return _buses.toArray(_rbus.get());
	}

	@Override
	public void setRegBus(Bus[] b) throws PAModelException
	{
		_rbus.set(_buses.getIndexes(b));
	}

	@Override
	public boolean hasLTC(int ndx) throws PAModelException
	{
		return _hasreg.get(ndx);
	}

	@Override
	public boolean[] hasLTC() throws PAModelException
	{
		return _hasreg.get();
	}

	@Override
	public void setHasLTC(int ndx, boolean b) throws PAModelException
	{
		_hasreg.set(ndx, b);
	}

	@Override
	public void setHasLTC(boolean[] b) throws PAModelException
	{
		_hasreg.set(b);
	}

	@Override
	public float getFromMinTap(int ndx) throws PAModelException
	{
		return _fmntap.get(ndx);
	}

	@Override
	public void setFromMinTap(int ndx, float a) throws PAModelException
	{
		_fmntap.set(ndx, a);
	}

	@Override
	public float[] getFromMinTap() throws PAModelException
	{
		return _fmntap.get();
	}

	@Override
	public void setFromMinTap(float[] a) throws PAModelException
	{
		_fmntap.set(a);
	}

	@Override
	public float getToMinTap(int ndx) throws PAModelException
	{
		return _tmntap.get(ndx);
	}

	@Override
	public float[] getToMinTap() throws PAModelException
	{
		return _tmntap.get();
	}

	@Override
	public void setToMinTap(int ndx, float a) throws PAModelException
	{
		_tmntap.set(ndx, a);
	}

	@Override
	public void setToMinTap(float[] a) throws PAModelException
	{
		_tmntap.set(a);
	}

	@Override
	public float getFromMaxTap(int ndx) throws PAModelException
	{
		return _fmxtap.get(ndx);
	}

	@Override
	public void setFromMaxTap(int ndx, float a) throws PAModelException
	{
		_fmxtap.set(ndx, a);
		
	}

	@Override
	public float getToMaxTap(int ndx) throws PAModelException
	{
		return _tmxtap.get(ndx);
	}

	@Override
	public void setToMaxTap(int ndx, float a) throws PAModelException
	{
		_tmxtap.set(ndx, a);
	}

	@Override
	public float[] getFromMaxTap() throws PAModelException
	{
		return _fmxtap.get();
	}

	@Override
	public void setFromMaxTap(float[] a) throws PAModelException
	{
		_fmxtap.set(a);
	}

	@Override
	public float[] getToMaxTap() throws PAModelException
	{
		return _tmxtap.get();
	}

	@Override
	public void setToMaxTap(float[] a) throws PAModelException
	{
		_tmxtap.set(a);
	}

	@Override
	public float getFromStepSize(int ndx) throws PAModelException
	{
		return _fstep.get(ndx);
	}

	@Override
	public float[] getFromStepSize() throws PAModelException
	{
		return _fstep.get();
	}

	@Override
	public void setFromStepSize(int ndx, float step) throws PAModelException
	{
		_fstep.set(ndx, step);
	}

	@Override
	public void setFromStepSize(float[] step) throws PAModelException
	{
		_fstep.set(step);
	}

	@Override
	public float getToStepSize(int ndx) throws PAModelException
	{
		return _tstep.get(ndx);
	}

	@Override
	public float[] getToStepSize() throws PAModelException
	{
		return _tstep.get();
	}

	@Override
	public void setToStepSize(int ndx, float step) throws PAModelException
	{
		_tstep.set(ndx, step);
	}

	@Override
	public void setToStepSize(float[] step) throws PAModelException
	{
		_tstep.set(step);
	}
}
