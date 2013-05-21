package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.BaseList;

public abstract class TransformerList<T extends Transformer> 
	extends BaseList<T>
{
	public enum TransformerStatus
	{
		Unknown(-1), OutOfService(0), InService(1), Wnd1Out(4), Wnd2Out(2), Wnd3Out(3);
		private int _code;
		TransformerStatus(int code)
		{
			_code = code;
		}
		public int getCode() {return _code;}
		public static TransformerStatus fromCode(int code)
		{
			switch(code)
			{
				case 0: return OutOfService;
				case 1: return InService;
				case 2: return Wnd2Out;
				case 3: return Wnd3Out;
				case 4: return Wnd1Out;
				default: return Unknown;
			}
		}
	}

	/* Convenience Methods */
	
	public abstract Bus getBus1(int ndx);
	public abstract Bus getBus2(int ndx);
	public abstract Bus getBus3(int ndx);
	public abstract float getMagCondPerUnit(int ndx);
	public abstract float getMagSuscPerUnit(int ndx);
	public abstract float getNoLoadLoss(int ndx);
	public abstract float getExcitingCurrent(int ndx);
	public abstract TransformerStatus getInitTransformerStat(int ndx);
	public abstract float getResistance1_2(int ndx);
	public abstract float getReactance1_2(int ndx);
	public abstract float getResistance2_3(int ndx);
	public abstract float getReactance2_3(int ndx);
	public abstract float getResistance3_1(int ndx);
	public abstract float getReactance3_1(int ndx);
	
	
	/* Raw methods */
	
	public abstract String getI(int ndx);
	public abstract String getJ(int ndx);
	public abstract String getK(int ndx);
	public abstract String getCKT(int ndx);
	public abstract int getCW(int ndx);
	public abstract int getCZ(int ndx);
	public abstract int getCM(int ndx);
	public abstract float getMAG1(int ndx);
	public abstract float getMAG2(int ndx);
	public abstract int getNMETR(int ndx);
	public abstract String getNAME(int ndx);
	public abstract int getSTAT(int ndx);
	public abstract float getR1_2(int ndx);
	public abstract float getX1_2(int ndx);
	public abstract float getSBASE1_2(int ndx);
	public abstract float getR2_3(int ndx);
	public abstract float getX2_3(int ndx);
	public abstract float getSBASE2_3(int ndx);
	public abstract float getR3_1(int ndx);
	public abstract float getX3_1(int ndx);
	public abstract float getSBASE3_1(int ndx);
	public abstract OwnershipList<?> getOwnership(int ndx);
}	
