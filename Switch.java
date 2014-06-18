package com.powerdata.openpa;

public class Switch extends TwoTermDev 
{
	public enum State
	{
		Closed(false), Open(true);
		State(boolean st) {_state = st;}
		boolean _state;
		public boolean getState() {return _state;}

		
		public static State fromBoolean(boolean b) {return b?State.Open:State.Closed;}
		
		public static State[] fromBoolean(boolean[] b)
		{
			int n = b.length;
			State[] rv = new State[n];
			for(int i=0; i < n; ++i)
				rv[i] = fromBoolean(b[i]);
			return rv;
		}
		
	}
	
	SwitchList _list;
	public Switch(SwitchList list, int ndx)
	{
		super(list, ndx);
		_list = list;
	}
	public State getState() {return _list.getState(_ndx);} 
	public boolean canOperateUnderLoad()	{	return _list.canOperateUnderLoad(_ndx);}
	public void setState(Switch.State state)	{	/* What to do?? */	}
	public void commit()	{	/* What to do */	}
}
