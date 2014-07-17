package com.powerdata.openpa;

import com.powerdata.openpa.PAModel.ListMetaType;

public class SVCListImpl extends ShuntListImpl<SVC> implements SVCList
{
	static final ShuntEnum _PFld = new ShuntEnum()
	{
		@Override
		public ColumnMeta id() {return ColumnMeta.SvcID;}
		@Override
		public ColumnMeta name() {return ColumnMeta.SvcNAME;}
		@Override
		public ColumnMeta bus() {return ColumnMeta.SvcBUS;}
		@Override
		public ColumnMeta p() {return ColumnMeta.SvcP;}
		@Override
		public ColumnMeta q() {return ColumnMeta.SvcQ;}
		@Override
		public ColumnMeta insvc() {return ColumnMeta.SvcINSVC;}
		@Override
		public ColumnMeta b() {return ColumnMeta.SvcB;}
	};

	BusList _buses;

	FloatData _minb = new FloatData(ColumnMeta.SvcBMIN),
			_maxb = new FloatData(ColumnMeta.SvcBMAX),
			_vs = new FloatData(ColumnMeta.SvcVS);
	
	BoolData _avr = new BoolData(ColumnMeta.SvcAVR);
	IntData _rbus = new IntData(ColumnMeta.SvcREGBUS);

	public SVCListImpl(PAModel model, int[] keys)
	{
		super(model, keys, _PFld);
		_buses = model.getBuses();
	}
	public SVCListImpl(PAModel model, int size)
	{
		super(model, size, _PFld);
		_buses = model.getBuses();
	}

	public SVCListImpl() {super();}

	@Override
	public SVC get(int index)
	{
		return new SVC(this, index);
	}
	@Override
	public float getMinB(int ndx)
	{
		return _minb.get(ndx);
	}
	@Override
	public void setMinB(int ndx, float b)
	{
		_minb.set(ndx, b);
	}
	@Override
	public float[] getMinB()
	{
		return _minb.get();
	}
	@Override
	public void setMinB(float[] b)
	{
		_minb.set(b);
	}
	@Override
	public float getMaxB(int ndx)
	{
		return _maxb.get(ndx);
	}
	@Override
	public void setMaxB(int ndx, float b)
	{
		_maxb.set(ndx, b);
	}
	@Override
	public float[] getMaxB()
	{
		return _maxb.get();
	}
	@Override
	public void setMaxB(float[] b)
	{
		_maxb.set(b);
	}
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
		return ListMetaType.SVC;
	}

}
