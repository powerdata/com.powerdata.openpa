package com.powerdata.openpa;

import com.powerdata.openpa.Gen.Mode;
import com.powerdata.openpa.Gen.Type;
import com.powerdata.openpa.PAModel.ListMetaType;

public class GenListI extends OneTermDevListI<Gen> implements GenList
{
	Type[][] _type = new Type[2][];
	Mode[][] _mode = new Mode[2][];
	float[][] _opminp=IFlt(), _opmaxp=IFlt(), _vs=IFlt();
	float[][] _minq=IFlt(), _maxq=IFlt(), _ps=IFlt(), _qs=IFlt();
	boolean[][] _isreg=IBool();
	int[][] _rbus = IInt();
	
	BusList _buses;
	
	protected GenListI() {super();}
	
	public GenListI(PAModel model, int[] keys)
	{
		super(model, keys);
		_buses = model.getBuses();
	}
	public GenListI(PAModel model, int size)
	{
		super(model, size);
		_buses = model.getBuses();
	}
	
	
	@Override
	public Type getType(int ndx)
	{
		return getObj(_type, ndx);
	}

	@Override
	public void setType(int ndx, Type t)
	{
		setObj(_type, ndx, t);
	}

	@Override
	public Type[] getType()
	{
		return getObj(_type);
	}

	@Override
	public void setType(Type[] t)
	{
		setObj(_type, t);
	}

	@Override
	public Mode getMode(int ndx)
	{
		return getObj(_mode, ndx);
	}

	@Override
	public void setMode(int ndx, Mode m)
	{
		setObj(_mode, ndx, m);
	}

	@Override
	public Mode[] getMode()
	{
		return getObj(_mode);
	}

	@Override
	public void setMode(Mode[] m)
	{
		setObj(_mode, m);
	}

	@Override
	public Gen get(int index)
	{
		return new Gen(this, index);
	}

	@Override
	public float getOpMinP(int ndx)
	{
		return getFloat(_opminp, ndx);
	}

	@Override
	public void setOpMinP(int ndx, float mw)
	{
		setFloat(_opminp, ndx, mw);
	}

	@Override
	public float[] getOpMinP()
	{
		return getFloat(_opminp);
	}

	@Override
	public void setOpMinP(float[] mw)
	{
		setFloat(_opminp, mw);
	}

	@Override
	public float getOpMaxP(int ndx)
	{
		return getFloat(_opmaxp, ndx);
	}

	@Override
	public void setOpMaxP(int ndx, float mw)
	{
		setFloat(_opmaxp, ndx, mw);
	}

	@Override
	public float[] getOpMaxP()
	{
		return getFloat(_opmaxp);
	}

	@Override
	public void setOpMaxP(float[] mw)
	{
		setFloat(_opmaxp, mw);
	}

	@Override
	public float getMinQ(int ndx)
	{
		return getFloat(_minq, ndx);
	}

	@Override
	public void setMinQ(int ndx, float mvar)
	{
		setFloat(_minq, ndx, mvar);
	}

	@Override
	public float[] getMinQ()
	{
		return getFloat(_minq);
	}

	@Override
	public void setMinQ(float[] mvar)
	{
		setFloat(_minq, mvar);
	}

	@Override
	public float getMaxQ(int ndx)
	{
		return getFloat(_maxq, ndx);
	}

	@Override
	public void setMaxQ(int ndx, float mvar)
	{
		setFloat(_maxq, ndx, mvar);
	}

	@Override
	public float[] getMaxQ()
	{
		return getFloat(_maxq);
	}

	@Override
	public void setMaxQ(float[] mvar)
	{
		setFloat(_maxq, mvar);
	}

	@Override
	public float getPS(int ndx)
	{
		return getFloat(_ps, ndx);
	}

	@Override
	public void setPS(int ndx, float mw)
	{
		setFloat(_ps, ndx, mw);
	}

	@Override
	public float[] getPS()
	{
		return getFloat(_ps);
	}

	@Override
	public void setPS(float[] mw)
	{
		setFloat(_ps, mw);
	}

	@Override
	public float getQS(int ndx)
	{
		return getFloat(_qs, ndx);
	}

	@Override
	public void setQS(int ndx, float mvar)
	{
		setFloat(_qs, ndx, mvar);
	}

	@Override
	public float[] getQS()
	{
		return getFloat(_qs);
	}

	@Override
	public void setQS(float[] mvar)
	{
		setFloat(_qs, mvar);
	}

	@Override
	public boolean isRegKV(int ndx)
	{
		return getBool(_isreg, ndx);
	}

	@Override
	public void setRegKV(int ndx, boolean reg)
	{
		setBool(_isreg, ndx, reg);
	}

	@Override
	public boolean[] isRegKV()
	{
		return getBool(_isreg);
	}

	@Override
	public void setRegKV(boolean[] reg)
	{
		setBool(_isreg, reg);
	}

	@Override
	public float getVS(int ndx)
	{
		return getFloat(_vs, ndx);
	}

	@Override
	public void setVS(int ndx, float kv)
	{
		setFloat(_vs, ndx, kv);
	}

	@Override
	public float[] getVS()
	{
		return getFloat(_vs);
	}

	@Override
	public void setVS(float[] kv)
	{
		setFloat(_vs, kv);
	}

	@Override
	public Bus getRegBus(int ndx)
	{
		return _buses.get(getInt(_rbus, ndx));
	}

	@Override
	public void setRegBus(int ndx, Bus b)
	{
		setInt(_rbus, ndx, b.getIndex());
	}

	@Override
	public Bus[] getRegBus()
	{
		return _buses.toArray(getInt(_rbus));
	}

	@Override
	public void setRegBus(Bus[] b)
	{
		setInt(_rbus, BaseList.ObjectNdx(b));
	}

	@Override
	protected ListMetaType getMetaType()
	{
		return ListMetaType.Gen;
	}

}
