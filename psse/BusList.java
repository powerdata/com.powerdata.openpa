package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.BaseList;

public abstract class BusList<T extends Bus> extends BaseList<T>
{
	public enum BusTypeCode
	{
		Load(1), Gen(2), Slack(3), Isolated(4);
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
				default: return null;
			}
		}
	}
	
	/* convenience methods */
	
	public abstract BusTypeCode getBusType(int ndx);
	public abstract float getShuntConductance(int ndx);
	public abstract float getShuntSusceptance(int ndx);
	public abstract AreaInterchange getAreaObject(int ndx);
	public abstract Zone getZoneObject(int ndx);
	public abstract Owner getOwnerObject(int ndx);
	public abstract float getVangRad(int ndx);

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
