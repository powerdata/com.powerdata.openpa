package com.powerdata.openpa;

public class SVC extends OneTermDev
{
	SVCList _list;
	
	public SVC(SVCList list, int ndx)
	{
		super(list, ndx);
		_list = list;
	}
	
	/** get minimum susceptance (MVAr @ unity voltage) */
	public float getMinB() throws PAModelException
	{
		return _list.getMinB(_ndx);
	}
	
	/** set minimum susceptance (MVAr @ unity voltage) */
	public void setMinB(float b) throws PAModelException
	{
		_list.setMinB(_ndx, b);
	}

	/** get maximum susceptance (MVAr @ unity voltage) */
	public float getMaxB() throws PAModelException
	{
		return _list.getMaxB(_ndx);
	}

	/** set maximum susceptance (MVAr @ unity voltage) */
	public void setMaxB(float b) throws PAModelException
	{
		_list.setMaxB(_ndx, b);
	}
	
	/** is regulating voltage */
	public boolean isRegKV() throws PAModelException
	{
		return _list.isRegKV(_ndx);
	}
	
	/** is regulating voltage */
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
	
	/** get slope (kV/MVAr per-cent on largest magnitude admittance limit) */
	public float getSlope() throws PAModelException
	{
		return _list.getSlope(_ndx);
	}
	
	/** set slope (kV/MVAr per-cent on largest magnitude admittance limit) */
	public void setSlope(float slope) throws PAModelException
	{
		_list.setSlope(_ndx, slope);
	}

}
