package com.powerdata.openpa.impl;

import com.powerdata.openpa.ColumnMeta;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.Switch;
import com.powerdata.openpa.SwitchList;
import com.powerdata.openpa.Switch.State;

public class SwitchListI extends TwoTermDevListI<Switch> implements SwitchList
{
	static final TwoTermDevEnum _PFld = new TwoTermDevEnum()
	{
		@Override
		public ColumnMeta id() {return ColumnMeta.SwID;}
		@Override
		public ColumnMeta name() {return ColumnMeta.SwNAME;}
		@Override
		public ColumnMeta fbus() {return ColumnMeta.SwBUSFROM;}
		@Override
		public ColumnMeta tbus() {return ColumnMeta.SwBUSTO;}
		@Override
		public ColumnMeta insvc() {return ColumnMeta.SwINSVC;}
		@Override
		public ColumnMeta fp() {return ColumnMeta.SwPFROM;}
		@Override
		public ColumnMeta fq() {return ColumnMeta.SwQFROM;}
		@Override
		public ColumnMeta tp() {return ColumnMeta.SwPTO;}
		@Override
		public ColumnMeta tq() {return ColumnMeta.SwQTO;}
	};
	
	EnumData<State> _state =  new EnumData<State>(ColumnMeta.SwSTATE);
	BoolData _opld = new BoolData(ColumnMeta.SwOPLD),
			_enab = new BoolData(ColumnMeta.SwENAB);
	FloatData _ttrans = new FloatData(ColumnMeta.SwTRTIME);
	
	protected SwitchListI(){super();}
	
	public SwitchListI(PAModelI model, int[] keys) throws PAModelException
	{
		super(model, keys, _PFld);
	}
	public SwitchListI(PAModelI model, int size) throws PAModelException
	{
		super(model, size, _PFld);
	}

	@Override
	public State getState(int ndx) throws PAModelException
	{
		return _state.get(ndx);
	}

	@Override
	public void setState(int ndx, State state) throws PAModelException
	{
		_state.set(ndx, state);
	}

	@Override
	public State[] getState() throws PAModelException
	{
		return _state.get();
	}

	@Override
	public void setState(State[] state) throws PAModelException
	{
		_state.set(state);
	}

	@Override
	public boolean isOperableUnderLoad(int ndx) throws PAModelException
	{
		return _opld.get(ndx);
	}

	@Override
	public void setOperableUnderLoad(int ndx, boolean op) throws PAModelException
	{
		_opld.set(ndx, op);
	}

	@Override
	public boolean[] isOperableUnderLoad() throws PAModelException
	{
		return _opld.get();
	}
	
	@Override
	public void setOperableUnderLoad(boolean[] op) throws PAModelException
	{
		_opld.set(op);
	}

	@Override
	public boolean isEnabled(int ndx) throws PAModelException
	{
		return _enab.get(ndx);
	}

	@Override
	public void setEnabled(int ndx, boolean enable) throws PAModelException
	{
		_enab.set(ndx, enable);
	}

	@Override
	public boolean[] isEnabled() throws PAModelException
	{
		return _enab.get();
	}

	@Override
	public void setEnabled(boolean[] enable) throws PAModelException
	{
		_enab.set(enable);
	}

	@Override
	public Switch get(int index)
	{
		return new Switch(this, index);
	}

	@Override
	public float getTransitTime(int ndx) throws PAModelException
	{
		return _ttrans.get(ndx);
	}

	@Override
	public float[] getTransitTime() throws PAModelException
	{
		return _ttrans.get();
	}

	@Override
	public void setTransitTime(int ndx, float t) throws PAModelException
	{
		_ttrans.set(ndx, t);
	}

	@Override
	public void setTransitTime(float[] t) throws PAModelException
	{
		_ttrans.set(t);
	}
	
	
}
