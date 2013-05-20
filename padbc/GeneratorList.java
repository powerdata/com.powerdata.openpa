package com.powerdata.openpa.padbc;

import com.powerdata.openpa.tools.BaseList;

public abstract class GeneratorList<T extends Generator> extends BaseList<T>
{
	public enum AVRMode {OFF, ON;}
	public enum GenMode {OFF, MAN, AGC, EDC, LFC;}
	public enum SyncMachMode {GEN, CON, PUMP;}
	public enum UnitType {Thermal, Hydro;} 
	
	public abstract int getNode(int ndx);
	public abstract AVRMode getAVRMode(int ndx);
	public abstract GenMode getGenMode(int ndx);
	public abstract SyncMachMode getSyncMachMode(int ndx);
	public abstract UnitType getUnitType(int ndx);
	
	public abstract float getActvPwr(int ndx);
	public abstract float getReacPwr(int ndx);
	public abstract float getMinOperActvPwr(int ndx);
	public abstract float getMaxOperActvPwr(int ndx);

	public abstract float getMinVoltage(int ndx);
	public abstract float getMaxVoltage(int ndx);
	public abstract float getRegVoltage(int ndx);
	public abstract float getRegReacPwr(int ndx);
	
	
	public abstract int getRegNode(int ndx);
	public abstract float getR(int ndx);
	public abstract float getX(int ndx);
	public abstract float getInertia(int ndx);
	
	public abstract int getCtrlSwitch(int ndx);
	
}
