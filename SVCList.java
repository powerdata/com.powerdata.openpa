package com.powerdata.openpa;

import com.powerdata.openpa.SVC.SVCState;
import com.powerdata.openpa.impl.SVCListI;

public interface SVCList extends OneTermDevListIfc<SVC>
{

	static final SVCList Empty = new SVCListI();

	float getMinQ(int ndx) throws PAModelException;

	void setMinQ(int ndx, float mvar) throws PAModelException;
	
	float[] getMinQ() throws PAModelException;
	
	void setMinQ(float[] mvar) throws PAModelException;

	float getMaxQ(int ndx) throws PAModelException;

	void setMaxQ(int ndx, float mvar) throws PAModelException;
	
	float[] getMaxQ() throws PAModelException;
	
	void setMaxQ(float[] mvar) throws PAModelException;

	boolean isRegKV(int ndx) throws PAModelException;

	void setRegKV(int ndx, boolean reg) throws PAModelException;
	
	boolean[] isRegKV() throws PAModelException;
	
	void setRegKV(boolean[] reg) throws PAModelException;

	float getVS(int ndx) throws PAModelException;

	void setVS(int ndx, float kv) throws PAModelException;
	
	float[] getVS() throws PAModelException;
	
	void setVS(float[] kv) throws PAModelException;

	void setRegBus(int ndx, Bus b) throws PAModelException;
	
	Bus getRegBus(int ndx) throws PAModelException;
	
	Bus[] getRegBus() throws PAModelException;
	
	void setRegBus(Bus[] b) throws PAModelException;

	/** get slope (kV/MVAr per-cent on largest magnitude admittance limit) */
	float getSlope(int ndx) throws PAModelException;
	
	/** get slope (kV/MVAr per-cent on largest magnitude admittance limit) */
	float[] getSlope() throws PAModelException;

	/** set slope (kV/MVAr per-cent on largest magnitude admittance limit) */
	void setSlope(int ndx, float slope) throws PAModelException;
	
	/** set slope (kV/MVAr per-cent on largest magnitude admittance limit) */
	void setSlope(float[] slope) throws PAModelException;

	/** get SVC output operating mode */
	SVCState getOutputMode(int ndx)throws PAModelException;

	/** get SVC output operating mode */
	SVCState[] getOutputMode()throws PAModelException;
	
	/** set SVC output operating mode */
	void setOutputMode(int ndx, SVCState m)throws PAModelException;

	/** set SVC output operating mode */
	void setOutputMode(SVCState[] m)throws PAModelException;

	/** get MVAr setpoint used if AVR is off */
	float getQS(int ndx)throws PAModelException;

	/** get MVAr setpoint used if AVR is off */
	float[] getQS()throws PAModelException;
	
	/** set MVAr setpoint used if AVR is off */
	void setQS(int ndx, float mvar)throws PAModelException;

	/** set MVAr setpoint used if AVR is off */
	void setQS(float[] mvar)throws PAModelException;
}
