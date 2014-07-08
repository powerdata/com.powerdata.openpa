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
	
	public float getOpMinP()
	{
		return _list.getOpMinP(_ndx);
	}
	public void setOpMinP(float mw)
	{
		_list.setOpMinP(_ndx, mw);
	}
	/** max active power in MW */
	public float getOpMaxP()
	{
		return _list.getOpMaxP(_ndx);
	}
	/** max active power in MW */
	public void setOpMaxP(float mw)
	{
		_list.setOpMaxP(_ndx, mw);
	}
	
	/** Minimum generator reactive power output (MVAr) */
	public float getMinQ()
	{
		return _list.getMinQ(_ndx);
	}
	/** Minimum generator reactive power output (MVAr) */
	public void setMinQ(float mvar)
	{
		_list.setMinQ(_ndx, mvar);
	}
	/** Maximum generator reactive power output (MVAr) */
	public float getMaxQ()
	{
		return _list.getMaxQ(_ndx);
	}
	/** Maximum generator reactive power output (MVAr) */
	public void setMaxQ(float mvar)
	{
		_list.setMaxQ(_ndx, mvar);
	}

	/** get the active power setpoint in MW */
	public float getPS() 
	{
		return _list.getPS(_ndx);
	}

	/** get the active power setpoint in MW */
	public void setPS(float mw)
	{
		_list.setPS(_ndx, mw);
	}
	
	/** get the reactive power setpoint when not regulating voltage (MVAr)*/
	public float getQS()
	{
		return _list.getQS(_ndx);
	}
	
	/** get the reactive power setpoint when not regulating voltage (MVAr)*/
	public void setQS(float mvar)
	{
		_list.setQS(_ndx, mvar);
	}
}
