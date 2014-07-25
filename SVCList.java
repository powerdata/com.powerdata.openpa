package com.powerdata.openpa;

import com.powerdata.openpa.impl.SVCListI;

public interface SVCList extends OneTermDevListIfc<SVC>
{

	static final SVCList Empty = new SVCListI();

	float getMinB(int ndx) throws PAModelException;

	void setMinB(int ndx, float b) throws PAModelException;
	
	float[] getMinB() throws PAModelException;
	
	void setMinB(float[] b) throws PAModelException;

	float getMaxB(int ndx) throws PAModelException;

	void setMaxB(int ndx, float b) throws PAModelException;
	
	float[] getMaxB() throws PAModelException;
	
	void setMaxB(float[] b) throws PAModelException;

	boolean isRegKV(int ndx) throws PAModelException;

	void setRegKV(int ndx, boolean reg) throws PAModelException;
	
	boolean[] isRegKV() throws PAModelException;
	
	void setRegKV(boolean[] reg) throws PAModelException;

	float getVS(int ndx) throws PAModelException;

	void setVS(int ndx, float kv) throws PAModelException;
	
	float[] getVS() throws PAModelException;
	
	void setVS(float[] kv) throws PAModelException;

	Bus getRegBus(int ndx) throws PAModelException;

	void setRegBus(int ndx, Bus b) throws PAModelException;
	
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

}
