package com.powerdata.openpa.impl;

import com.powerdata.openpa.Bus;
import com.powerdata.openpa.BusList;
import com.powerdata.openpa.ColumnMeta;
import com.powerdata.openpa.Gen;
import com.powerdata.openpa.GenList;
import com.powerdata.openpa.Gen.Mode;
import com.powerdata.openpa.Gen.Type;
import com.powerdata.openpa.PAModelException;

public class GenListI extends OneTermDevListI<Gen> implements GenList
{
	static final OneTermDevEnum _PFld = new OneTermDevEnum()
	{
		@Override
		public ColumnMeta id() {return ColumnMeta.GenID;}
		@Override
		public ColumnMeta name() {return ColumnMeta.GenNAME;}
		@Override
		public ColumnMeta bus() {return ColumnMeta.GenBUS;}
		@Override
		public ColumnMeta p() {return ColumnMeta.GenP;}
		@Override
		public ColumnMeta q() {return ColumnMeta.GenQ;}
		@Override
		public ColumnMeta insvc() {return ColumnMeta.GenINSVC;}
	};
	
	EnumData<Gen.Type> _type = new EnumData<Gen.Type>(ColumnMeta.GenTYPE);
	EnumData<Gen.Mode> _mode = new EnumData<Gen.Mode>(ColumnMeta.GenMODE);
	FloatData _opminp = new FloatData(ColumnMeta.GenOPMINP), 
			_opmaxp = new FloatData(ColumnMeta.GenOPMAXP),
			_vs = new FloatData(ColumnMeta.GenVS),
			_minq = new FloatData(ColumnMeta.GenMINQ),
			_maxq = new FloatData(ColumnMeta.GenMAXQ),
			_ps = new FloatData(ColumnMeta.GenPS),
			_qs = new FloatData(ColumnMeta.GenQS);
	BoolData _avr = new BoolData(ColumnMeta.GenAVR);
	IntData _rbus = new IntData(ColumnMeta.GenREGBUS);
	
	BusList _buses;
	
	protected GenListI() {super();}
	
	public GenListI(PAModelI model, int[] keys) throws PAModelException
	{
		super(model, keys, _PFld);
		_buses = model.getBuses();
	}
	public GenListI(PAModelI model, int size) throws PAModelException
	{
		super(model, size, _PFld);
		_buses = model.getBuses();
	}
	
	@Override
	public Type getType(int ndx) throws PAModelException
	{
		return _type.get(ndx);
	}

	@Override
	public void setType(int ndx, Type t) throws PAModelException
	{
		_type.set(ndx, t);
	}

	@Override
	public Type[] getType() throws PAModelException
	{
		return _type.get();
	}

	@Override
	public void setType(Type[] t) throws PAModelException
	{
		_type.set(t);
	}

	@Override
	public Mode getMode(int ndx) throws PAModelException
	{
		return _mode.get(ndx);
	}

	@Override
	public void setMode(int ndx, Mode m) throws PAModelException
	{
		_mode.set(ndx, m);
	}

	@Override
	public Mode[] getMode() throws PAModelException
	{
		return _mode.get();
	}

	@Override
	public void setMode(Mode[] m) throws PAModelException
	{
		_mode.set(m);
	}

	@Override
	public Gen get(int index)
	{
		return new Gen(this, index);
	}

	@Override
	public float getOpMinP(int ndx) throws PAModelException
	{
		return _opminp.get(ndx);
	}

	@Override
	public void setOpMinP(int ndx, float mw) throws PAModelException
	{
		_opminp.set(ndx, mw);
	}

	@Override
	public float[] getOpMinP() throws PAModelException
	{
		return _opminp.get();
	}

	@Override
	public void setOpMinP(float[] mw) throws PAModelException
	{
		_opminp.set(mw);
	}

	@Override
	public float getOpMaxP(int ndx) throws PAModelException
	{
		return _opmaxp.get(ndx);
	}

	@Override
	public void setOpMaxP(int ndx, float mw) throws PAModelException
	{
		_opmaxp.set(ndx, mw);
	}

	@Override
	public float[] getOpMaxP() throws PAModelException
	{
		return _opmaxp.get();
	}

	@Override
	public void setOpMaxP(float[] mw) throws PAModelException
	{
		_opmaxp.set(mw);
	}

	@Override
	public float getMinQ(int ndx) throws PAModelException
	{
		return _minq.get(ndx);
	}

	@Override
	public void setMinQ(int ndx, float mvar) throws PAModelException
	{
		_minq.set(ndx, mvar);
	}

	@Override
	public float[] getMinQ() throws PAModelException
	{
		return _minq.get();
	}

	@Override
	public void setMinQ(float[] mvar) throws PAModelException
	{
		_minq.set(mvar);
	}

	@Override
	public float getMaxQ(int ndx) throws PAModelException
	{
		return _maxq.get(ndx);
	}

	@Override
	public void setMaxQ(int ndx, float mvar) throws PAModelException
	{
		_maxq.set(ndx, mvar);
	}

	@Override
	public float[] getMaxQ() throws PAModelException
	{
		return _maxq.get();
	}

	@Override
	public void setMaxQ(float[] mvar) throws PAModelException
	{
		_maxq.set(mvar);
	}

	@Override
	public float getPS(int ndx) throws PAModelException
	{
		return _ps.get(ndx);
	}

	@Override
	public void setPS(int ndx, float mw) throws PAModelException
	{
		_ps.set(ndx, mw);
	}

	@Override
	public float[] getPS() throws PAModelException
	{
		return _ps.get();
	}

	@Override
	public void setPS(float[] mw) throws PAModelException
	{
		_ps.set(mw);
	}

	@Override
	public float getQS(int ndx) throws PAModelException
	{
		return _qs.get(ndx);
	}

	@Override
	public void setQS(int ndx, float mvar) throws PAModelException
	{
		_qs.set(ndx, mvar);
	}

	@Override
	public float[] getQS() throws PAModelException
	{
		return _qs.get();
	}

	@Override
	public void setQS(float[] mvar) throws PAModelException
	{
		_qs.set(mvar);
	}

	//TODO: change to AVR in the method name
	@Override
	public boolean isRegKV(int ndx) throws PAModelException
	{
		return _avr.get(ndx);
	}

	@Override
	public void setRegKV(int ndx, boolean reg) throws PAModelException
	{
		_avr.set(ndx, reg);
	}

	@Override
	public boolean[] isRegKV() throws PAModelException
	{
		return _avr.get();
	}

	@Override
	public void setRegKV(boolean[] reg) throws PAModelException
	{
		_avr.set(reg);
	}

	@Override
	public float getVS(int ndx) throws PAModelException
	{
		return _vs.get(ndx);
	}

	@Override
	public void setVS(int ndx, float kv) throws PAModelException
	{
		_vs.set(ndx, kv);
	}

	@Override
	public float[] getVS() throws PAModelException
	{
		return _vs.get();
	}

	@Override
	public void setVS(float[] kv) throws PAModelException
	{
		_vs.set(kv);
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

}
