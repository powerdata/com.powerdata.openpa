package com.powerdata.openpa.psse;

public abstract class TransformerRawList extends PsseBaseInputList<TransformerRaw>
{
	
	public TransformerRawList(PsseInputModel model) 
	{
		super(model);
	}

	/** Get a Transformer by it's index. */
	@Override
	public TransformerRaw get(int ndx) { return new TransformerRaw(ndx,this); }
	/** Get a Transformer by it's ID. */
	@Override
	public TransformerRaw get(String id) { return super.get(id); }
	public abstract BusIn getBus1(int ndx) throws PsseModelException;
	public abstract BusIn getBus2(int ndx) throws PsseModelException;
	public abstract BusIn getBus3(int ndx) throws PsseModelException;
	public abstract BusIn getStarBus(int ndx);

	public BusIn getDeftBus1(int ndx) throws PsseModelException {return _model.getBus(getI(ndx));}
	public BusIn getDeftBus2(int ndx) throws PsseModelException {return _model.getBus(getI(ndx));}
	public BusIn getDeftBus3(int ndx) throws PsseModelException {return _model.getBus(getI(ndx));}

	
	/** retrieve impedance corrections and transforms
	
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
	public abstract float getVMSTAR(int ndx);
	public abstract float getANSTAR(int ndx);
	public abstract float getWINDV1(int ndx) throws PsseModelException;
	public abstract float getNOMV1(int ndx);
	public abstract float getANG1(int ndx);
	public abstract float getRATA1(int ndx);
	public abstract float getRATB1(int ndx);
	public abstract float getRATC1(int ndx);
	public abstract int getCOD1(int ndx);
	public abstract String getCONT1(int ndx);
	public abstract float getRMA1(int ndx) throws PsseModelException;
	public abstract float getRMI1(int ndx) throws PsseModelException;
	public abstract float getVMA1(int ndx) throws PsseModelException;
	public abstract float getVMI1(int ndx) throws PsseModelException;
	public abstract int getNTP1(int ndx);
	public abstract int getTAB1(int ndx);
	public abstract float getCR1(int ndx);
	public abstract float getCX1(int ndx);

	public abstract float getWINDV2(int ndx) throws PsseModelException;
	public abstract float getNOMV2(int ndx);
	public abstract float getANG2(int ndx);
	public abstract float getRATA2(int ndx);
	public abstract float getRATB2(int ndx);
	public abstract float getRATC2(int ndx);
	public abstract int getCOD2(int ndx);
	public abstract String getCONT2(int ndx);
	public abstract float getRMA2(int ndx) throws PsseModelException;
	public abstract float getRMI2(int ndx) throws PsseModelException;
	public abstract float getVMA2(int ndx) throws PsseModelException;
	public abstract float getVMI2(int ndx) throws PsseModelException;
	public abstract int getNTP2(int ndx);
	public abstract int getTAB2(int ndx);
	public abstract float getCR2(int ndx);
	public abstract float getCX2(int ndx);

	public abstract float getWINDV3(int ndx) throws PsseModelException;
	public abstract float getNOMV3(int ndx);
	public abstract float getANG3(int ndx);
	public abstract float getRATA3(int ndx);
	public abstract float getRATB3(int ndx);
	public abstract float getRATC3(int ndx);
	public abstract int getCOD3(int ndx);
	public abstract String getCONT3(int ndx);
	public abstract float getRMA3(int ndx) throws PsseModelException;
	public abstract float getRMI3(int ndx) throws PsseModelException;
	public abstract float getVMA3(int ndx) throws PsseModelException;
	public abstract float getVMI3(int ndx) throws PsseModelException;
	public abstract int getNTP3(int ndx);
	public abstract int getTAB3(int ndx);
	public abstract float getCR3(int ndx);
	public abstract float getCX3(int ndx);

	public abstract OwnershipInList getOwnership(int ndx);


	/* raw method defaults */
	
	public String getDeftK(int ndx) {return "0";}
	public String getDeftCKT(int ndx) {return "1";}
	public int getDeftCW(int ndx) {return 1;}
	public int getDeftCZ(int ndx) {return 1;}
	public int getDeftCM(int ndx) {return 1;}
	public float getDeftMAG1(int ndx) {return 0;}
	public float getDeftMAG2(int ndx)  {return 0;}
	public int getDeftNMETR(int ndx) {return 2;}
	public String getDeftNAME(int ndx) {return "";}
	public int getDeftSTAT(int ndx) {return 1;}
	public float getDeftR1_2(int ndx) {return 0F;}
	public float getDeftSBASE1_2(int ndx) {return _model.getSBASE();}
	public float getDeftR2_3(int ndx) {return 0F;}
	public float getDeftSBASE2_3(int ndx) {return _model.getSBASE();}
	public float getDeftR3_1(int ndx) {return 0F;}
	public float getDeftSBASE3_1(int ndx) {return _model.getSBASE();}
	public float getDeftVMSTAR(int ndx) {return 1F;}
	public float getDeftANSTAR(int ndx) {return 0F;}
	public float getDeftWINDV1(int ndx) throws PsseModelException
	{
		return (getCW(ndx)==2)?
				_model.getBus(getI(ndx)).getBASKV() :
				1F;
	}
	public float getDeftNOMV1(int ndx)  {return 0F;}
	public float getDeftANG1(int ndx)   {return 0F;}
	public float getDeftRATA1(int ndx) {return 0F;}
	public float getDeftRATB1(int ndx) {return 0F;}
	public float getDeftRATC1(int ndx) {return 0F;}
	public int getDeftCOD1(int ndx) {return 0;}
	public String getDeftCONT1(int ndx) {return "0";}
	public int getDeftNTP1(int ndx) {return 33;}
	public int getDeftTAB1(int ndx) {return 0;}
	public float getDeftCR1(int ndx) {return 0F;}
	public float getDeftCX1(int ndx) {return 0F;}
	public float getDeftWINDV2(int ndx) throws PsseModelException
	{
		return (getCW(ndx)==2)?
				_model.getBus(getJ(ndx)).getBASKV() :
				1F;
	}
	public float getDeftNOMV2(int ndx)  {return 0F;}
	public float getDeftANG2(int ndx)   {return 0F;}
	public float getDeftRATA2(int ndx) {return 0F;}
	public float getDeftRATB2(int ndx) {return 0F;}
	public float getDeftRATC2(int ndx) {return 0F;}
	public int getDeftCOD2(int ndx) {return 0;}
	public String getDeftCONT2(int ndx) {return "0";}
	public int getDeftNTP2(int ndx) {return 33;}
	public int getDeftTAB2(int ndx) {return 0;}
	public float getDeftCR2(int ndx) {return 0F;}
	public float getDeftCX2(int ndx) {return 0F;}
	public float getDeftWINDV3(int ndx) throws PsseModelException
	{
		float rv = 1F;
		if (getCW(ndx)==2)
		{
			String k = getK(ndx);
			if (k == null || k.isEmpty() || k.equals("0"))
			{
				throw new PsseModelException("Not a 3-winding transformer");
			}
			rv = _model.getBus(k).getBASKV();
		}
		return rv;
	}
	public float getDeftNOMV3(int ndx)  {return 0F;}
	public float getDeftANG3(int ndx)   {return 0F;}
	public float getDeftRATA3(int ndx) {return 0F;}
	public float getDeftRATB3(int ndx) {return 0F;}
	public float getDeftRATC3(int ndx) {return 0F;}
	public int getDeftCOD3(int ndx) {return 0;}
	public String getDeftCONT3(int ndx) {return "0";}
	public int getDeftNTP3(int ndx) {return 33;}
	public int getDeftTAB3(int ndx) {return 0;}
	public float getDeftCR3(int ndx) {return 0F;}
	public float getDeftCX3(int ndx) {return 0F;}

}	
