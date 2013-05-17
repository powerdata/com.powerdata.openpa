package com.powerdata.openpa.padbc;

public abstract class GeneratorList extends BaseList<Generator>
{
	public enum AVRMode {OFF, ON;}
	public enum GenMode {OFF, MAN, AGC, EDC, LFC;}
	public enum SyncMachMode {GEN, CON, PUMP;}
	public enum UnitType {Thermal, Hydro;} 
//	public enum UnitCtrlMode {Setpoint, Pulsed;}
	
	@Override
	public Generator get(int ndx) {return new Generator(ndx, this);}
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
	
/* These attributes would be added to support the M* and K* units */
//	public abstract float getMW(int ndx);
//	public abstract float getMVAr(int ndx);
//	public abstract float getMinOperMW(int ndx);
//	public abstract float getMaxOperMW(int ndx);
//	public abstract float getMinRatedGrossMW(int ndx);
//	public abstract float getMaxRatedGrossMW(int ndx);
//
//	public abstract float getMinKV(int ndx);
//	public abstract float getMaxKV(int ndx);
//	public abstract float getRegKV(int ndx);
//	public abstract float getRegMVAr(int ndx);
//	

	
	/* These commented out attributes would be used to support an OTS, but maybe not just the power flow */	
//	public abstract UnitCtrlMode getUnitCtrlMode(int ndx);
//	public abstract float getMinRatedGrossActvPwr(int ndx);
//	public abstract float getMaxRatedGrossActvPwr(int ndx);
	
//	public abstract float getControlDeadband(int ndx);
//	public abstract float getControlResponseRate(int ndx);
//	public abstract float getStepChange(int ndx);
//	public abstract float getSpinReserveRamp(int ndx);
//	public abstract float getGovSCD(int ndx);
//	public abstract float getGovMPL(int ndx);
	
	public abstract int getRegNode(int ndx);
	public abstract float getR(int ndx);
	public abstract float getX(int ndx);
	public abstract float getInertia(int ndx);
	
	public abstract int getCtrlSwitch(int ndx);
	
}
