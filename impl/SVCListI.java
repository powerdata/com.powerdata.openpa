package com.powerdata.openpa.impl;

import com.powerdata.openpa.Bus;
import com.powerdata.openpa.ColumnMeta;
import com.powerdata.openpa.ListMetaType;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.SVC;
import com.powerdata.openpa.SVC.SVCState;
import com.powerdata.openpa.SVCList;

public class SVCListI extends OneTermDevListI<SVC> implements SVCList
{
	static final OneTermDevEnum _PFld = new OneTermDevEnum()
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
		public ColumnMeta insvc() {return ColumnMeta.SvcOOS;}
	};

	BusListI _buses;

	FloatData _minb = new FloatData(ColumnMeta.SvcBMIN),
			_maxb = new FloatData(ColumnMeta.SvcBMAX),
			_vs = new FloatData(ColumnMeta.SvcVS),
			_slope = new FloatData(ColumnMeta.SvcSLOPE);
	
	BoolData _avr = new BoolData(ColumnMeta.SvcAVR);
	IntData _rbus = new IntData(ColumnMeta.SvcREGBUS);
	EnumData<SVC.SVCState> _omode = new EnumData<>(ColumnMeta.SvcOMODE);

	public SVCListI(PAModelI model, int[] keys) throws PAModelException
	{
		super(model, keys, _PFld);
		_buses = model.getBuses();
	}
	public SVCListI(PAModelI model, int size) throws PAModelException
	{
		super(model, size, _PFld);
		_buses = model.getBuses();
	}

	public SVCListI() {super();}

	@Override
	public SVC get(int index)
	{
		return new SVC(this, index);
	}
	@Override
	public float getMinQ(int ndx) throws PAModelException
	{
		return _minb.get(ndx);
	}
	@Override
	public void setMinQ(int ndx, float b) throws PAModelException
	{
		_minb.set(ndx, b);
	}
	@Override
	public float[] getMinQ() throws PAModelException
	{
		return _minb.get();
	}
	@Override
	public void setMinQ(float[] b) throws PAModelException
	{
		_minb.set(b);
	}
	@Override
	public float getMaxQ(int ndx) throws PAModelException
	{
		return _maxb.get(ndx);
	}
	@Override
	public void setMaxQ(int ndx, float b) throws PAModelException
	{
		_maxb.set(ndx, b);
	}
	@Override
	public float[] getMaxQ() throws PAModelException
	{
		return _maxb.get();
	}
	@Override
	public void setMaxQ(float[] b) throws PAModelException
	{
		_maxb.set(b);
	}
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
	@Override
	public ListMetaType getListMeta()
	{
		return ListMetaType.SVC;
	}
	@Override
	public float getSlope(int ndx) throws PAModelException
	{
		return _slope.get(ndx);
	}
	@Override
	public void setSlope(int ndx, float slope) throws PAModelException
	{
		_slope.set(ndx, slope);
	}
	@Override
	public float[] getSlope() throws PAModelException
	{
		return _slope.get();
	}
	@Override
	public void setSlope(float[] slope) throws PAModelException
	{
		_slope.set(slope);
	}
	@Override
	public SVCState getOutputMode(int ndx) throws PAModelException
	{
		return _omode.get(ndx);
	}
	@Override
	public SVCState[] getOutputMode() throws PAModelException
	{
		return _omode.get();
	}
	@Override
	public void setOutputMode(int ndx, SVCState m) throws PAModelException
	{
		_omode.set(ndx, m);
	}
	@Override
	public void setOutputMode(SVCState[] m) throws PAModelException
	{
		_omode.set(m);
	}
	
}
