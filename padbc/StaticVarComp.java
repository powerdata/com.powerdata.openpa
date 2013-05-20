package com.powerdata.openpa.padbc;

import com.powerdata.openpa.padbc.StaticVarCompList.SVCMode;
import com.powerdata.openpa.tools.BaseObject;

public class StaticVarComp extends BaseObject
{
	private StaticVarCompList<?> _list;
	
	public StaticVarComp(int ndx, StaticVarCompList<?> list)
	{
		super(ndx);
		_list = list;
	}
	
	@Override
	public String getObjectID() {return _list.getObjectID(getIndex());}
	
	/** return minimum susceptance limit in PU for 100MVA base */
	public float getMinB() {return _list.getMinB(getIndex());}
	/** return maximum susceptance limit in PU for 100MVA base */
	public float getMaxB() {return _list.getMaxB(getIndex());}
	/** return SVC slope */
	public float getSlope() {return _list.getSlope(getIndex());}
	/** get mode, either regulating voltage or fixed var */
	public SVCMode getMode() {return _list.getMode(getIndex());}
	/** get voltaget setpoint if regulating voltage */
	public float getVoltageSetpt() {return _list.getVoltageSetpt(getIndex());}
	/** get fixed var setpoint */
	public float getBSetpt() {return _list.getBSetpt(getIndex());}
	
	/* MVAr case */
//	public float getMinMVAr() {return _list.getMinMVAr(getIndex());}
//	public float getMaxMVAr() {return _list.getMaxMVAr(getIndex());}
//  public float getKVSetpt() {return _list.getKVSetpt(getIndex());}
//  public float getMVArSett() {return _list.getMVArSetpt(getIndex());}
	
	public float getReacPwr() {return _list.getReacPwr(getIndex());}
	public void updateReacPwr(float b) {_list.updateReacPwr(getIndex(), b);}
	// public void updateMVAr(float mvar) {_list.updateMVAr(getIndex(), mvar);}

}
