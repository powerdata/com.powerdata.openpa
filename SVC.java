package com.powerdata.openpa;

public class SVC extends OneTermDev
{
	SVCList _list;
	
	public enum SVCState {Off,CapacitorLimit,ReactorLimit,Normal,FixedMVAr;}
	
	public SVC(SVCList list, int ndx)
	{
		super(list, ndx);
		_list = list;
	}
	
	/** get minimum MVAr */
	public float getMinQ() throws PAModelException
	{
		return _list.getMinQ(_ndx);
	}
	
	/** set minimum MVAr */
	public void setMinQ(float mvar) throws PAModelException
	{
		_list.setMinQ(_ndx, mvar);
	}

	/** get maximum MVAr */
	public float getMaxQ() throws PAModelException
	{
		return _list.getMaxQ(_ndx);
	}

	/** set maximum MVAr */
	public void setMaxQ(float mvar) throws PAModelException
	{
		_list.setMaxQ(_ndx, mvar);
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
	
	/** get slope (kV/MVAr per-unit on largest magnitude admittance limit) */
	public float getSlope() throws PAModelException
	{
		return _list.getSlope(_ndx);
	}
	
	/** set slope (kV/MVAr per-unit on largest magnitude admittance limit) */
	public void setSlope(float slope) throws PAModelException
	{
		_list.setSlope(_ndx, slope);
	}

	/** get SVC output operating mode */
	public SVCState getOutputMode() throws PAModelException
	{
		return _list.getOutputMode(_ndx);
	}
	
	/** set SVC output operating mode */
	public void setOutputMode(SVCState m) throws PAModelException
	{
		_list.setOutputMode(_ndx, m);
	}
	
	/** get MVAr setpoint used if AVR is off */
	public float getQS() throws PAModelException
	{
		return _list.getQS(_ndx);
	}

	/** set MVAr setpoint used if AVR is off */
	public void setQS(float mvar) throws PAModelException
	{
		_list.setQS(_ndx, mvar);
	}
}
