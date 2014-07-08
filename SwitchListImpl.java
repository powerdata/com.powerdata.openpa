package com.powerdata.openpa;

import com.powerdata.openpa.Switch.State;

public class SwitchListImpl extends TwoTermDevListI<Switch> implements SwitchList
{
	public static final SwitchList	Empty	= new SwitchListImpl();

	protected State[][] _state = new State[2][];
	protected boolean[][] _opld = IBool(), _enbl = IBool();
	
	protected SwitchListImpl(){super();}
	
	public SwitchListImpl(PAModel model, int[] keys)
	{
		super(model, keys);
	}
	protected SwitchListImpl(PAModel model, int size)
	{
		super(model, size);
	}

	@Override
	public State getState(int ndx)
	{
		return getObj(_state, ndx);
	}

	@Override
	public void setState(int ndx, State state)
	{
		setObj(_state, ndx, state);
	}

	@Override
	public State[] getState()
	{
		return getObj(_state);
	}

	@Override
	public void setState(State[] state)
	{
		setObj(_state, state);
	}

	@Override
	public boolean isOperableUnderLoad(int ndx)
	{
		return getBool(_opld, ndx);
	}

	@Override
	public void setOperableUnderLoad(int ndx, boolean op)
	{
		setBool(_opld, ndx, op);
	}

	@Override
	public boolean[] isOperableUnderLoad()
	{
		return getBool(_opld);
	}
	
	@Override
	public void setOperableUnderLoad(boolean[] op)
	{
		setBool(_opld, op);
	}

	@Override
	public boolean isEnabled(int ndx)
	{
		return getBool(_enbl, ndx);
	}

	@Override
	public void setEnabled(int ndx, boolean enable)
	{
		setBool(_enbl, ndx, enable);
	}

	@Override
	public boolean[] isEnabled()
	{
		return getBool(_enbl);
	}

	@Override
	public void setEnabled(boolean[] enable)
	{
		setBool(_enbl, enable);
	}

	@Override
	public Switch get(int index)
	{
		return new Switch(this, index);
	}

}
