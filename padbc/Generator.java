package com.powerdata.openpa.padbc;

import com.powerdata.openpa.padbc.GeneratorList.AVRMode;
import com.powerdata.openpa.padbc.GeneratorList.GenMode;
import com.powerdata.openpa.padbc.GeneratorList.SyncMachMode;
import com.powerdata.openpa.padbc.GeneratorList.UnitType;

public class Generator extends BaseObject
{
	private GeneratorList _list;
	
	public Generator(int ndx, GeneratorList list)
	{
		super(ndx);
		_list = list;
	}
	
	@Override
	public String getID() {return _list.getID(getIndex());}
	
	public AVRMode getAVRMode() {return _list.getAVRMode(getIndex());}
	public GenMode getGenMode() {return _list.getGenMode(getIndex());}
	public SyncMachMode getSyncMachMode() {return _list.getSyncMachMode(getIndex());}
	public UnitType getUnitType() {return _list.getUnitType(getIndex());}
	
	public float getActvPwr() {return _list.getActvPwr(getIndex());}
	public float getReacPwr() {return _list.getReacPwr(getIndex());}
	public float getMinOperActvPwr() {return _list.getMinOperActvPwr(getIndex());}
	public float getMaxOperActvPwr() {return _list.getMaxOperActvPwr(getIndex());}

	public float getMinVoltage() {return _list.getMinVoltage(getIndex());}
	public float getMaxVoltage() {return _list.getMaxVoltage(getIndex());}
	public float getRegVoltage() {return _list.getRegVoltage(getIndex());}
	public float getRegReacPwr() {return _list.getRegReacPwr(getIndex());}
	
/* These attributes would be added to support the M* and K* units */
//	public float getMW();
//	public float getMVAr();
//	public float getMinOperMW();
//	public float getMaxOperMW();
//	public float getMinRatedGrossMW();
//	public float getMaxRatedGrossMW();
//
//	public float getMinKV();
//	public float getMaxKV();
//	public float getRegKV();
//	public float getRegMVAr();
//	

	
	/* These commented out attributes would be used to support an OTS, but maybe not just the power flow */	
//	public UnitCtrlMode getUnitCtrlMode();
//	public float getMinRatedGrossActvPwr();
//	public float getMaxRatedGrossActvPwr();
	
//	public float getControlDeadband();
//	public float getControlResponseRate();
//	public float getStepChange();
//	public float getSpinReserveRamp();
//	public float getGovSCD();
//	public float getGovMPL();
	
	public int getRegNode() {return _list.getRegNode(getIndex());}
	public float getR() {return _list.getR(getIndex());}
	public float getX() {return _list.getX(getIndex());}
	public float getInertia() {return _list.getInertia(getIndex());}
	
	public int getCtrlSwitch() {return _list.getCtrlSwitch(getIndex());}

}
