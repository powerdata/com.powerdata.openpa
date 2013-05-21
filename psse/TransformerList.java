package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.BaseList;

public abstract class TransformerList<T extends Transformer> 
	extends BaseList<T>
{
	

	/* Convenience Methods */
	
	public Bus getBus1(int ndx) {return null;}
	public Bus getBus2(int ndx) {return null;}
	public Bus getBus3(int ndx) {return null;}
	public float getMagCondPerUnit(int ndx) {return 0;}
	public float getMagSuscPerUnit(int ndx) {return 0;}
	public float getNoLoadLoss(int ndx) {return 0;}
	public float getExcitingCurrent(int ndx) {return 0;}
	public TransformerStatus getInitTransformerStat(int ndx)
	{
		return TransformerStatus.fromCode(getSTAT(ndx));
	}
	public float getResistance1_2(int ndx) {return 0;}
	public float getReactance1_2(int ndx)  {return 0;}
	public float getResistance2_3(int ndx) {return 0;}
	public float getReactance2_3(int ndx) {return 0;}
	public float getResistance3_1(int ndx) {return 0;}
	public float getReactance3_1(int ndx) {return 0;}
	
	
	/* Raw methods */
	
	public abstract String getI(int ndx);
	public abstract String getJ(int ndx);
	public String getK(int ndx) {return "0";}
	public String getCKT(int ndx) {return "1";}
	public int getCW(int ndx) {return 1;}
	public int getCZ(int ndx) {return 1;}
	public int getCM(int ndx) {return 1;}
	public float getMAG1(int ndx) {return 0;}
	public float getMAG2(int ndx)  {return 0;}
	public int getNMETR(int ndx) {return 2;}
	public String getNAME(int ndx) {return "";}
	public int getSTAT(int ndx) {return 1;}
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
