package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.BaseList;

public abstract class BusList<T extends Bus> extends BaseList<T>
{
	public enum BusTypeCode
	{
		Unknown(-1), Load(1), Gen(2), Slack(3), Isolated(4);
		private int _code;
		BusTypeCode(int code) {_code = code;}
		public int getCode() {return _code;}
		public static BusTypeCode fromCode(int code)
		{
			switch(code)
			{
				case 1: return Load;
				case 2: return Gen;
				case 3: return Slack;
				case 4: return Isolated;
				default: return Unknown;
			}
		}
	}
	
	/* convenience methods */
	
	public BusTypeCode getBusType(int ndx) {return BusTypeCode.fromCode(getIDE(ndx));}
	public float getShuntConductance(int ndx) {return 0;}
	public float getShuntSusceptance(int ndx) {return 0;}
	public AreaInterchange getAreaObject(int ndx) {return null;}
	public Zone getZoneObject(int ndx) {return null;}
	public Owner getOwnerObject(int ndx)  {return null;}
	public float getVangRad(int ndx) {return 0;}

	/* raw methods */

	public abstract int getI(int ndx);
	public abstract String getNAME(int ndx);
	public abstract float getBASKV(int ndx);
	public abstract int getIDE(int ndx);
	public abstract int getGL(int ndx);
	public abstract int getBL(int ndx);
	public abstract int getAREA(int ndx);
	public abstract int getZONE(int ndx);
	public abstract float getVM(int ndx);
	public abstract float getVA(int ndx);
	public abstract int getOWNER(int ndx);
}
