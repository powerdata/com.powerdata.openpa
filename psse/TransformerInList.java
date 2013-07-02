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
	
	/** Winding 1 bus number or name */
	public abstract String getI(int ndx) throws PsseModelException;
	/** Winding 2 bus number or name */
	public abstract String getJ(int ndx) throws PsseModelException;
	/** circuit identifier */
	public String getCKT(int ndx) throws PsseModelException {return "1";}
	/** Winding data I/O code */
	public int getCW(int ndx) throws PsseModelException {return 1;}
	/** Impedance data I/O code */
	public int getCZ(int ndx) throws PsseModelException {return 1;}
	/** Magnetizing admittance I/O code */
	public int getCM(int ndx) throws PsseModelException {return 1;}
	/** Magnetizing conductance */
	public float getMAG1(int ndx) throws PsseModelException {return 0F;}
	/** Magnetizing susceptance */
	public float getMAG2(int ndx) throws PsseModelException {return 0F;}
	/** Nonmetered end code */
	public int getNMETR(int ndx) throws PsseModelException {return 2;}
	/** Name */
	public String getNAME(int ndx) throws PsseModelException {return "";}
	/** Initial Transformer status */
	public int getSTAT(int ndx) throws PsseModelException {return 1;}
	/** Measured resistance between winding 1 and winding 2 busses */
	public float getR1_2(int ndx) throws PsseModelException {return 0F;}
	/** Measured reactance between winding 1 and winding 2 busses */
	public abstract float getX1_2(int ndx) throws PsseModelException;
	/** get winding 1-2 base MVA */
	public float getSBASE1_2(int ndx) throws PsseModelException {return _model.getSBASE();}
	/** winding 1 off-nominal turns ratio */ 
	public float getWINDV1(int ndx) throws PsseModelException
	{
		return (getCW(ndx)==2)?
				_model.getBus(getI(ndx)).getBASKV() :
				1F;
	}
	public abstract float getNOMV1(int ndx) throws PsseModelException;
	public abstract float getANG1(int ndx) throws PsseModelException;
	public abstract float getRATA1(int ndx) throws PsseModelException;
	public abstract float getRATB1(int ndx) throws PsseModelException;
	public abstract float getRATC1(int ndx) throws PsseModelException;
	public abstract int getCOD1(int ndx) throws PsseModelException;
	public abstract String getCONT1(int ndx) throws PsseModelException;
	public abstract float getRMA1(int ndx) throws PsseModelException;
	public abstract float getRMI1(int ndx) throws PsseModelException;
	public abstract float getVMA1(int ndx) throws PsseModelException;
	public abstract float getVMI1(int ndx) throws PsseModelException;
	public abstract int getNTP1(int ndx) throws PsseModelException;
	public abstract int getTAB1(int ndx) throws PsseModelException;
	public abstract float getCR1(int ndx) throws PsseModelException;
	public abstract float getCX1(int ndx) throws PsseModelException;
	public abstract OwnershipInList getOwnership(int ndx) throws PsseModelException;

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
