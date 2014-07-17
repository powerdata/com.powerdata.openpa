package com.powerdata.openpa;

import com.powerdata.openpa.PAModel.ListMetaType;
import com.powerdata.openpa.Switch.State;

public class SwitchListImpl extends TwoTermDevListI<Switch> implements SwitchList
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
	
	EnumData<State> _state =  new EnumData<>(ColumnMeta.SwSTATE);
	BoolData _opld = new BoolData(ColumnMeta.SwOPLD),
			_enab = new BoolData(ColumnMeta.SwENAB);
	
	protected SwitchListImpl(){super();}
	
	public SwitchListImpl(PAModel model, int[] keys)
	{
		super(model, keys, _PFld);
	}
	protected SwitchListImpl(PAModel model, int size)
	{
		super(model, size, _PFld);
	}

	@Override
	public State getState(int ndx)
	{
		return _state.get(ndx);
	}

	@Override
	public void setState(int ndx, State state)
	{
		_state.set(ndx, state);
	}

	@Override
	public State[] getState()
	{
		return _state.get();
	}

	@Override
	public void setState(State[] state)
	{
		_state.set(state);
	}

	@Override
	public boolean isOperableUnderLoad(int ndx)
	{
		return _opld.get(ndx);
	}

	@Override
	public void setOperableUnderLoad(int ndx, boolean op)
	{
		_opld.set(ndx, op);
	}

	@Override
	public boolean[] isOperableUnderLoad()
	{
		return _opld.get();
	}
	
	@Override
	public void setOperableUnderLoad(boolean[] op)
	{
		_opld.set(op);
	}

	@Override
	public boolean isEnabled(int ndx)
	{
		return _enab.get(ndx);
	}

	@Override
	public void setEnabled(int ndx, boolean enable)
	{
		_enab.set(ndx, enable);
	}

	@Override
	public boolean[] isEnabled()
	{
		return _enab.get();
	}

	@Override
	public void setEnabled(boolean[] enable)
	{
		_enab.set(enable);
	}

	@Override
	public Switch get(int index)
	{
		return new Switch(this, index);
	}

	@Override
	protected ListMetaType getMetaType()
	{
		return ListMetaType.Switch;
	}

}
