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
		
		public static State fromString(String s)
		{
			char c = Character.toLowerCase(s.charAt(0));
			return (c=='t'||c=='o')?State.Open:State.Closed;
		}
		
		public static State[] fromString(String[] s)
		{
			int n = s.length;
			State[] rv = new State[n];
			for(int i=0; i < n; ++i)
				rv[i] = fromString(s[i]);
			return rv;
		}
		
	}
	
	SwitchList _list;
	public Switch(SwitchList list, int ndx)
	{
		super(list, ndx);
		_list = list;
	}
	public State getState() throws PAModelException {return _list.getState(_ndx);} 
	public void setState(State state) throws PAModelException
	{
		_list.setState(_ndx, state);
	}
	public boolean isOperableUnderLoad() throws PAModelException
	{
		return _list.isOperableUnderLoad(_ndx);
	}
	public void setOperableUnderLoad(boolean op) throws PAModelException
	{
		_list.setOperableUnderLoad(_ndx, op);
	}
	public boolean isEnabled() throws PAModelException
	{
		return _list.isEnabled(_ndx);
	}
	public void setEnabled(boolean enable) throws PAModelException
	{
		_list.setEnabled(_ndx, enable);
	}
	public float getTransitTime() throws PAModelException
	{
		return _list.getTransitTime(_ndx);
	}
	public void setTransitTime(float t) throws PAModelException
	{
		_list.setTransitTime(_ndx, t);
	}
}
