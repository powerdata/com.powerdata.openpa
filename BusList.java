package com.powerdata.openpa;

import com.powerdata.openpa.impl.BusListI;


/**
 * List of Buses.
 * 
 * @author chris@powerdata.com
 * 
 */
public interface BusList extends GroupListIfc<Bus>
{

	static BusList	Empty	= new BusListI();

	float getVM(int ndx) throws PAModelException;

	void setVM(int ndx, float vm) throws PAModelException;

	float[] getVM() throws PAModelException;

	void setVM(float[] vm) throws PAModelException;

	float getVA(int ndx) throws PAModelException;

	void setVA(int ndx, float va) throws PAModelException;

	float[] getVA() throws PAModelException;

	void setVA(float[] va) throws PAModelException;

	int getFreqSrcPri(int ndx) throws PAModelException;

	void setFreqSrcPri(int ndx, int fsp) throws PAModelException;
	
	int[] getFreqSrcPri() throws PAModelException;
	
	void setFreqSrcPri(int[] fsp) throws PAModelException;

	Island getIsland(int ndx) throws PAModelException;

	Area getArea(int ndx) throws PAModelException;

	void setArea(int ndx, Area a) throws PAModelException;
	
	Area[] getArea() throws PAModelException;
	
	void setArea(Area[] a) throws PAModelException;

	Station getStation(int ndx) throws PAModelException;

	void setStation(int ndx, Station s) throws PAModelException;

	Station[] getStation() throws PAModelException;
	
	void setStation(Station[] s) throws PAModelException;
	
	Owner getOwner(int ndx) throws PAModelException;

	void setOwner(int ndx, Owner o) throws PAModelException;

	Owner[] getOwner() throws PAModelException;
	
	void setOwner(Owner[] o) throws PAModelException;
	
	VoltageLevel getVoltageLevel(int ndx) throws PAModelException;

	void setVoltageLevel(int ndx, VoltageLevel l) throws PAModelException;
	
	VoltageLevel[] getVoltageLevel() throws PAModelException;
	
	void setVoltageLevel(VoltageLevel[] l) throws PAModelException;

}
