package com.powerdata.openpa;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;
import com.powerdata.openpa.PhaseShifter.ControlMode;
import com.powerdata.openpa.impl.EmptyLists;

public interface PhaseShifterList extends ACBranchListIfc<PhaseShifter>
{
	static PhaseShifterList emptyList() {return EmptyLists.EMPTY_PHASESHIFTERS;}
	
	/**
	 * return the phase shifter control mode
	 * @param ndx offset within the list
	 * @return current ControlMode
	 * @throws PAModelException
	 */
	ControlMode getControlMode(int ndx) throws PAModelException;
	/**
	 * return the control mode for all phase shifters
	 * @return current ControlMode for the entire list
	 * @throws PAModelException
	 */
	ControlMode[] getControlMode() throws PAModelException;
	/**
	 * set the phase shifter control mode
	 * @param ndx offset within the list
	 * @param m Desired control mode
	 * @throws PAModelException
	 */
	void setControlMode(int ndx, ControlMode m) throws PAModelException;
	/**
	 * set the phase shifter control modes
	 * @param m array of Desired control modes
	 * @throws PAModelException
	 */
	void setControlMode(ControlMode[] m) throws PAModelException;
	/** 
	 * query the presence of active power regulation
	 * @param ndx offset within the list
	 * @return true if the device is capable of regulating MW, false otherwise
	 * @throws PAModelException
	 */
	boolean hasReg(int ndx) throws PAModelException;
	/** 
	 * query the presence of active power regulation
	 * @return true if the device is capable of regulating MW, false otherwise
	 * @throws PAModelException
	 */
	boolean[] hasReg() throws PAModelException;
	/** 
	 * set the presence of active power regulation
	 * @param ndx offset within the list
	 * @param v true if the device is capable of regulating MW, false otherwise
	 * @throws PAModelException
	 */
	void setReg(int ndx, boolean v) throws PAModelException;
	/**
	 * set the presence of active power regulation
	 * 
	 * @param v
	 *            array of booleans true if the device is capable of regulating
	 *            MW, false otherwise
	 * @throws PAModelException
	 */
	void setReg(boolean[] v) throws PAModelException;
	/** 
	 * Get the maximum phase shift angle
	 * @param ndx offset within the list
	 * @return maximum phase shift angle (DEG)
	 * @throws PAModelException
	 */
	float getMaxAng(int ndx) throws PAModelException;
	float[] getMaxAng() throws PAModelException;
	void setMaxAng(int ndx, float v) throws PAModelException;
	void setMaxAng(float[] v) throws PAModelException;
	float getMinAng(int ndx) throws PAModelException;
	float[] getMinAng() throws PAModelException;
	void setMinAng(int ndx, float v) throws PAModelException;
	void setMinAng(float[] v) throws PAModelException;
	float getRegMaxMW(int ndx) throws PAModelException;
	float[] getRegMaxMW() throws PAModelException;
	void setRegMaxMW(int ndx, float mw) throws PAModelException;
	void setRegMaxMW(float[] mw) throws PAModelException;
	float getRegMinMW(int ndx) throws PAModelException;
	void setRegMinMW(int ndx, float mw) throws PAModelException;
	float[] getRegMinMW() throws PAModelException;
	void setRegMinMW(float[] mw) throws PAModelException;

	
	static Set<ColumnMeta> Cols = EnumSet.copyOf(Arrays
			.asList(new ColumnMeta[] {
					ColumnMeta.PhashID,
					ColumnMeta.PhashNAME,
					ColumnMeta.PhashBUSFROM,
					ColumnMeta.PhashBUSTO,
					ColumnMeta.PhashINSVC,
					ColumnMeta.PhashPFROM,
					ColumnMeta.PhashQFROM,
					ColumnMeta.PhashPTO,
					ColumnMeta.PhashQTO,
					ColumnMeta.PhashR,
					ColumnMeta.PhashX,
					ColumnMeta.PhashGMAG,
					ColumnMeta.PhashBMAG,
					ColumnMeta.PhashANG,
					ColumnMeta.PhashTAPFROM,
					ColumnMeta.PhashTAPTO,
					ColumnMeta.PhashCTRLMODE,
					ColumnMeta.PhashRATLT,
					ColumnMeta.PhashHASREG,
					ColumnMeta.PhashMXANG,
					ColumnMeta.PhashMNANG,
					ColumnMeta.PhashMXMW,
					ColumnMeta.PhashMNMW
					}));
	@Override
	default Set<ColumnMeta> getColTypes()
	{
		return Cols;
	}
	@Override
	default ListMetaType getListMeta()
	{
		return ListMetaType.PhaseShifter;
	}

}
