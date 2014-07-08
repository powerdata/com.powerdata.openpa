package com.powerdata.openpa;

/**
 * Interface for bus List values needed by the Bus class. Makes the Bus object
 * usable by multiple lists (i.e. connectivity and single-bus views)
 * 
 * @author chris@powerdata.com
 * 
 */
public interface BusList extends GroupList<Bus>
{

	float getVM(int ndx);

	void setVM(int ndx, float vm);

	float[] getVM();

	void setVM(float[] vm);

	float getVA(int ndx);

	void setVA(int ndx, float va);

	float[] getVA();

	void setVA(float[] va);

	int getFrequencySourcePriority(int ndx);

	void setFrequencySourcePriority(int ndx, int fsp);
	
	int[] getFrequencySourcePriority();
	
	void setFrequencySourcePriority(int[] fsp);

	Island getIsland(int ndx);

	Area getArea(int ndx);

	void setArea(int ndx, Area a);
	
	Area[] getArea();
	
	void setArea(Area[] a);

	Station getStation(int ndx);

	void setStation(int ndx, Station s);

	Station[] getStation();
	
	void setStation(Station[] s);
	
	Owner getOwner(int ndx);

	void setOwner(int ndx, Owner o);

	Owner[] getOwner();
	
	void setOwner(Owner[] o);
	
	VoltageLevel getVoltageLevel(int ndx);

	void setVoltageLevel(int ndx, VoltageLevel l);
	
	VoltageLevel[] getVoltageLevel();
	
	void setVoltageLevel(VoltageLevel[] l);

}
