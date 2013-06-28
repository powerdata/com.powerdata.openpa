package com.powerdata.openpa.psse;

public abstract class TransformerInList extends PsseBaseInputList<TransformerIn>
{
	
	public TransformerInList(PsseInputModel model) 
	{
		super(model);
	}

	
	/** Get a Transformer by it's index. */
	@Override
	public TransformerIn get(int ndx) { return new TransformerIn(ndx,this); }
	/** Get a Transformer by it's ID. */
	@Override
	public TransformerIn get(String id) { return super.get(id); }
	
	/* Convenience methods */
	
	public abstract BusIn getBus1(int ndx) throws PsseModelException;
	public abstract BusIn getBus2(int ndx) throws PsseModelException;

	/* Raw methods */
	
	public abstract String getI(int ndx);
	public abstract String getJ(int ndx);
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
	public abstract float getWINDV1(int ndx);
	public abstract float getNOMV1(int ndx);
	public abstract float getANG1(int ndx);
	public abstract float getRATA1(int ndx);
	public abstract float getRATB1(int ndx);
	public abstract float getRATC1(int ndx);
	public abstract int getCOD1(int ndx);
	public abstract String getCONT1(int ndx);
	public abstract float getRMA1(int ndx);
	public abstract float getRMI1(int ndx);
	public abstract float getVMA1(int ndx);
	public abstract float getVMI1(int ndx);
	public abstract int getNTP1(int ndx);
	public abstract int getTAB1(int ndx);
	public abstract float getCR1(int ndx);
	public abstract float getCX1(int ndx);
	public abstract OwnershipInList getOwnership(int ndx);

	public String getDeftCKT(int ndx) {return "1";}
	public int getDeftCW(int ndx) {return 1;}
	public int getDeftCZ(int ndx) {return 1;}
	public int getDeftCM(int ndx) {return 1;}
	public float getDeftMAG1(int ndx) {return 0F;}
	public float getDeftMAG2(int ndx) {return 0F;}
	public int getDeftNMETR(int ndx) {return 2;}
	public String getDeftNAME(int ndx) {return "";}
	public int getDeftSTAT(int ndx) {return 1;}
	public float getDeftR1_2(int ndx) {return 0F;}
	public float getDeftSBASE1_2(int ndx) {return _model.getSBASE();}
	public float getDeftWINDV1(int ndx) throws PsseModelException
	{
		return (getCW(ndx)==2)?
				_model.getBus(getI(ndx)).getBASKV() :
				1F;
	}
	public float getDeftNOMV1(int ndx) {return 0F;}
	public float getDeftANG1(int ndx) {return 0F;}
	public float getDeftRATA1(int ndx) {return 0F;}
	public float getDeftRATB1(int ndx) {return 0F;}
	public float getDeftRATC1(int ndx) {return 0F;}
	public int getDeftCOD1(int ndx) {return 0;}
	public String getDeftCONT1(int ndx) {return "0";}
	public float getDeftRMA1(int ndx) {return 0.9F;}
	public float getDeftRMI1(int ndx) {return 1.1F;}
	public int getDeftNTP1(int ndx) {return 33;}
	public int getDeftTAB1(int ndx) {return 0;}
	public float getDeftCR1(int ndx) {return 0F;}
	public float getDeftCX1(int ndx) {return 0F;}
}	
