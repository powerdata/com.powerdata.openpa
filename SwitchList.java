package com.powerdata.openpa;

import com.powerdata.openpa.Switch.State;

public class SwitchList extends TwoTermDevList<Switch>
{
	State[] _state, _stateo;

	public static final SwitchList	Empty	= new SwitchList();

	protected SwitchList(){super();}
	
	public SwitchList(PALists model, int[] keys, int[] fbuskey, int[] tbuskey)
	{
		super(model, keys, fbuskey, tbuskey);
	}
	protected SwitchList(PALists model, int size, int[] fbuskey, int[] tbuskey)
	{
		super(model, size, fbuskey, tbuskey);
	}

	@Override
	public Switch get(int index)
	{
		return new Switch(this, index);
	}

	public State getState(int ndx)
	{
		return _state[ndx];
	}

	public void setState(int ndx, State state)
	{
		if (_stateo == null && _state != null)
			_stateo = _state.clone();
		_state[ndx] = state;
	}
	
	public State[] getState()
	{
		return _state;
	}
	
	public void setState(State[] state)
	{
		if (_state != state)
		{
			if (_stateo == null)
				_stateo = _state;
			_state = state;
		}
	}
	public boolean isOperable(int ndx)
	{
		//TODO:
		return true;
	}
	public void setOperable(int ndx, boolean op)
	{
		// TODO Auto-generated method stub
	}

	@Override
	protected Switch[] newarray(int size)
	{
		// TODO Auto-generated method stub
		return null;
	}
}
