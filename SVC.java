package com.powerdata.openpa;

public class SVC extends Shunt
{
	SVCList _list;
	
	public SVC(SVCList list, int ndx)
	{
		super(list, ndx);
		_list = list;
	}
	
	/** get minimum susceptance (MVAr @ unity voltage) */
	public float getMinB()
	{
		return _list.getMinB(_ndx);
	}
	
	/** set minimum susceptance (MVAr @ unity voltage) */
	public void setMinB(float b)
	{
		_list.setMinB(_ndx, b);
	}

	/** get maximum susceptance (MVAr @ unity voltage) */
	public float getMaxB()
	{
		return _list.getMaxB(_ndx);
	}

	/** set maximum susceptance (MVAr @ unity voltage) */
	public void setMaxB(float b)
	{
		_list.setMaxB(_ndx, b);
	}
	
	/** is regulating voltage */
	public boolean isRegKV()
	{
		return _list.isRegKV(_ndx);
	}
	
	/** is regulating voltage */
	public void setRegKV(boolean reg)
	{
		_list.setRegKV(_ndx, reg);
	}
	
	/** voltage setpoint in KV, used if regulating voltage */
	public float getVS()
	{
		return _list.getVS(_ndx);
	}
	
	/** voltage setpoint in KV, used if regulating voltage */
	public void setVS(float kv)
	{
		_list.setVS(_ndx, kv);
	}
	
	/** Bus where voltage is regulated */
	public Bus getRegBus()
	{
		return _list.getRegBus(_ndx);
	}
	
	public void setRegBus(Bus b)
	{
		_list.setRegBus(_ndx, b);
	}

}
