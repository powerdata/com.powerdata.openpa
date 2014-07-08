package com.powerdata.openpa;

public class Gen extends OneTermDev
{
	GenList _list;
	
	public enum Mode
	{
		/** Off */ OFF, 
		/** On in unknown mode */ ON, 
		/** On with manual active power setpoint */ MAN, 
		/** Load Frequency Control */ LFC,
		/** Automatic Generation Control */ AGC, 
		/** Economic Dispatch */ EDC, 
		/** Pumping */ PMP, 
		/** Condense */ CON,
		/** Isochronous Restore Mode */ RES;
	}

	public enum Type {Unknown, Thermal, Hydro};
	
	public Gen(GenList list, int ndx)
	{
		super(list, ndx);
		_list = list;
	}

	public Type getType()
	{
		return _list.getType(_ndx);
	}
	
	public void setType(Type t)
	{
		_list.setType(_ndx, t);
	}
	
	public Mode getMode()
	{
		return _list.getMode(_ndx);
	}
	
	public void setMode(Mode m)
	{
		_list.setMode(_ndx, m);
	}
	
	public float getOpMinMW() throws PAModelException // {return _list.getPB(_ndx);}
	{
		return 0;
	}
	public void setPB(float mw) throws PAModelException
	{
	}
	public void setPS(float mw) throws PAModelException //{_list.setPS(_ndx, mw);}
	{
	}
	public float getPS() throws PAModelException 
	{
		return 0;
	}
	/** max active power in MW */
	public float getPT() throws PAModelException // {return _list.getPT(_ndx);}
	{
		return 0;
	}
	/** max active power in MW */
	public void setPT(float mw) throws PAModelException // {_list.setPT(_ndx, mw);}
	{
		
	}
	/** Maximum generator reactive power output (MVAr) */
	public float getQT() throws PAModelException // {return _list.getQT(_ndx);}
	{
		return 0;
	}
	/** Maximum generator reactive power output (MVAr) */
	public void setQT(float mvar) throws PAModelException // {_list.setQT(_ndx, mvar);}
	{
		
	}
	/** Minimum generator reactive power output (MVAr) */
	public float getQB() throws PAModelException // {return _list.getQB(_ndx);}
	{
		return 0;
	}
	/** Minimum generator reactive power output (MVAr) */
	public void setQB(float mvar) throws PAModelException // {_list.setQB(_ndx, mvar);}
	{
		
	}


}
