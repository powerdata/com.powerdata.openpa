package com.powerdata.openpa;

import com.powerdata.openpa.Gen.Mode;
import com.powerdata.openpa.Gen.Type;
import com.powerdata.openpa.PAModel.ListMetaType;

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
	
	EnumData<Gen.Type> _type = new EnumData<>(ColumnMeta.GenTYPE);
	EnumData<Gen.Mode> _mode = new EnumData<>(ColumnMeta.GenMODE);
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
	
	public GenListI(PAModel model, int[] keys)
	{
		super(model, keys, _PFld);
		_buses = model.getBuses();
	}
	public GenListI(PAModel model, int size)
	{
		super(model, size, _PFld);
		_buses = model.getBuses();
	}
	
	@Override
	public Type getType(int ndx)
	{
		return _type.get(ndx);
	}

	@Override
	public void setType(int ndx, Type t)
	{
		_type.set(ndx, t);
	}

	@Override
	public Type[] getType()
	{
		return _type.get();
	}

	@Override
	public void setType(Type[] t)
	{
		_type.set(t);
	}

	@Override
	public Mode getMode(int ndx)
	{
		return _mode.get(ndx);
	}

	@Override
	public void setMode(int ndx, Mode m)
	{
		_mode.set(ndx, m);
	}

	@Override
	public Mode[] getMode()
	{
		return _mode.get();
	}

	@Override
	public void setMode(Mode[] m)
	{
		_mode.set(m);
	}

	@Override
	public Gen get(int index)
	{
		return new Gen(this, index);
	}

	@Override
	public float getOpMinP(int ndx)
	{
		return _opminp.get(ndx);
	}

	@Override
	public void setOpMinP(int ndx, float mw)
	{
		_opminp.set(ndx, mw);
	}

	@Override
	public float[] getOpMinP()
	{
		return _opminp.get();
	}

	@Override
	public void setOpMinP(float[] mw)
	{
		_opminp.set(mw);
	}

	@Override
	public float getOpMaxP(int ndx)
	{
		return _opmaxp.get(ndx);
	}

	@Override
	public void setOpMaxP(int ndx, float mw)
	{
		_opmaxp.set(ndx, mw);
	}

	@Override
	public float[] getOpMaxP()
	{
		return _opmaxp.get();
	}

	@Override
	public void setOpMaxP(float[] mw)
	{
		_opmaxp.set(mw);
	}

	@Override
	public float getMinQ(int ndx)
	{
		return _minq.get(ndx);
	}

	@Override
	public void setMinQ(int ndx, float mvar)
	{
		_minq.set(ndx, mvar);
	}

	@Override
	public float[] getMinQ()
	{
		return _minq.get();
	}

	@Override
	public void setMinQ(float[] mvar)
	{
		_minq.set(mvar);
	}

	@Override
	public float getMaxQ(int ndx)
	{
		return _maxq.get(ndx);
	}

	@Override
	public void setMaxQ(int ndx, float mvar)
	{
		_maxq.set(ndx, mvar);
	}

	@Override
	public float[] getMaxQ()
	{
		return _maxq.get();
	}

	@Override
	public void setMaxQ(float[] mvar)
	{
		_maxq.set(mvar);
	}

	@Override
	public float getPS(int ndx)
	{
		return _ps.get(ndx);
	}

	@Override
	public void setPS(int ndx, float mw)
	{
		_ps.set(ndx, mw);
	}

	@Override
	public float[] getPS()
	{
		return _ps.get();
	}

	@Override
	public void setPS(float[] mw)
	{
		_ps.set(mw);
	}

	@Override
	public float getQS(int ndx)
	{
		return _qs.get(ndx);
	}

	@Override
	public void setQS(int ndx, float mvar)
	{
		_qs.set(ndx, mvar);
	}

	@Override
	public float[] getQS()
	{
		return _qs.get();
	}

	@Override
	public void setQS(float[] mvar)
	{
		_qs.set(mvar);
	}

	//TODO: change to AVR in the method name
	@Override
	public boolean isRegKV(int ndx)
	{
		return _avr.get(ndx);
	}

	@Override
	public void setRegKV(int ndx, boolean reg)
	{
		_avr.set(ndx, reg);
	}

	@Override
	public boolean[] isRegKV()
	{
		return _avr.get();
	}

	@Override
	public void setRegKV(boolean[] reg)
	{
		_avr.set(reg);
	}

	@Override
	public float getVS(int ndx)
	{
		return _vs.get(ndx);
	}

	@Override
	public void setVS(int ndx, float kv)
	{
		_vs.set(ndx, kv);
	}

	@Override
	public float[] getVS()
	{
		return _vs.get();
	}

	@Override
	public void setVS(float[] kv)
	{
		_vs.set(kv);
	}

	@Override
	public Bus getRegBus(int ndx)
	{
		return _buses.get(_rbus.get(ndx));
	}

	@Override
	public void setRegBus(int ndx, Bus b)
	{
		_rbus.set(ndx, b.getIndex());
	}

	@Override
	public Bus[] getRegBus()
	{
		return _buses.toArray(_rbus.get());
	}

	@Override
	public void setRegBus(Bus[] b)
	{
		_rbus.set(BaseList.ObjectNdx(b));
	}

	@Override
	protected ListMetaType getMetaType()
	{
		return ListMetaType.Gen;
	}

}
