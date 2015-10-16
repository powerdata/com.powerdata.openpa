package com.powerdata.openpa;

import java.util.EnumSet;
import java.util.Set;

public class Gen extends OneTermDev
{
	GenList _list;
	
	public enum Mode
	{
		/** Unknown */ Unknown,
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

	static Set<Mode> GenModes = EnumSet.complementOf(EnumSet.of(Mode.OFF, Mode.PMP));
	
	public Gen(GenList list, int ndx)
	{
		super(list, ndx);
		_list = list;
	}

	public Type getType() throws PAModelException
	{
		return _list.getType(_ndx);
	}
	
	public void setType(Type t) throws PAModelException
	{
		_list.setType(_ndx, t);
	}
	
	public Mode getMode() throws PAModelException
	{
		return _list.getMode(_ndx);
	}
	
	public void setMode(Mode m) throws PAModelException
	{
		_list.setMode(_ndx, m);
	}
	
	public float getOpMinP() throws PAModelException
	{
		return _list.getOpMinP(_ndx);
	}
	
	public void setOpMinP(float mw) throws PAModelException
	{
		_list.setOpMinP(_ndx, mw);
	}
	
	/** max active power in MW */
	public float getOpMaxP() throws PAModelException
	{
		return _list.getOpMaxP(_ndx);
	}
	/** max active power in MW */
	public void setOpMaxP(float mw) throws PAModelException
	{
		_list.setOpMaxP(_ndx, mw);
	}
	
	/** Minimum generator reactive power output (MVAr) */
	public float getMinQ() throws PAModelException
	{
		return _list.getMinQ(_ndx);
	}
	/** Minimum generator reactive power output (MVAr) */
	public void setMinQ(float mvar) throws PAModelException
	{
		_list.setMinQ(_ndx, mvar);
	}
	/** Maximum generator reactive power output (MVAr) */
	public float getMaxQ() throws PAModelException
	{
		return _list.getMaxQ(_ndx);
	}
	/** Maximum generator reactive power output (MVAr) */
	public void setMaxQ(float mvar) throws PAModelException
	{
		_list.setMaxQ(_ndx, mvar);
	}

	/** get the active power setpoint in MW */
	public float getPS()  throws PAModelException
	{
		return _list.getPS(_ndx);
	}

	/** get the active power setpoint in MW */
	public void setPS(float mw) throws PAModelException
	{
		_list.setPS(_ndx, mw);
	}
	
	/** get the reactive power setpoint when not regulating voltage (MVAr)*/
	public float getQS() throws PAModelException
	{
		return _list.getQS(_ndx);
	}
	
	/** get the reactive power setpoint when not regulating voltage (MVAr)*/
	public void setQS(float mvar) throws PAModelException
	{
		_list.setQS(_ndx, mvar);
	}
	
	/** is unit regulating voltage */
	public boolean isRegKV() throws PAModelException
	{
		return _list.isRegKV(_ndx);
	}
	
	/** is unit regulating voltage */
	public void setRegKV(boolean reg) throws PAModelException
	{
		_list.setRegKV(_ndx, reg);
	}
	
	/** voltage setpoint in KV, used if regulating voltage */
	public float getVS() throws PAModelException
	{
		return _list.getVS(_ndx);
	}
	
	/** voltage setpoint in KV, used if regulating voltage */
	public void setVS(float kv) throws PAModelException
	{
		_list.setVS(_ndx, kv);
	}
	
	/** Bus where voltage is regulated */
	public Bus getRegBus() throws PAModelException
	{
		return _list.getRegBus(_ndx);
	}
	
	public void setRegBus(Bus b) throws PAModelException
	{
		_list.setRegBus(_ndx, b);
	}
	
	public boolean unitInAVR() throws PAModelException
	{
		return isGenerating() && isRegKV();
	}
	
	
	public boolean isGenerating() throws PAModelException
	{
		return isInService() && GenModes.contains(getMode());
	}

}
