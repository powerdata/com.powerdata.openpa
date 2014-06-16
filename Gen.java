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
}
