package com.powerdata.openpa.psse;

public abstract class PhaseShifterInList extends PsseBaseInputList<PhaseShifterIn>
{
	protected BusInList _buses;
	
	public static final PhaseShifterInList Empty = new PhaseShifterInList()
	{
		@Override
		public String getI(int ndx) throws PsseModelException {return null;}
		@Override
		public String getJ(int ndx) throws PsseModelException {return null;}
		@Override
		public float getX1_2(int ndx) throws PsseModelException {return 0f;}
		@Override
		public String getObjectID(int ndx) throws PsseModelException {return null;}
		@Override
		public int size() {return 0;}
	};
	
	protected PhaseShifterInList() {super();}
	public PhaseShifterInList(PsseModel model) throws PsseModelException 
	{
		super(model);
		_buses = model.getBuses();
	}

	
	/** Get a Transformer by it's index. */
	@Override
	public PhaseShifterIn get(int ndx) { return new PhaseShifterIn(ndx,this); }
	/** Get a Transformer by it's ID. */
	@Override
	public PhaseShifterIn get(String id) { return super.get(id); }
	
	/* Convenience methods */
	
	public BusIn getBus1(int ndx) throws PsseModelException {return _buses.get(getI(ndx));}
	public BusIn getBus2(int ndx) throws PsseModelException {return _buses.get(getJ(ndx));}

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
	public float getMAG1(int ndx) throws PsseModelException {return 0f;}
	/** Magnetizing susceptance */
	public float getMAG2(int ndx) throws PsseModelException {return 0f;}
	/** Nonmetered end code */
	public int getNMETR(int ndx) throws PsseModelException {return 2;}
	/** Name */
	public String getNAME(int ndx) throws PsseModelException {return "";}
	/** Initial Transformer status */
	public int getSTAT(int ndx) throws PsseModelException {return 1;}
	/** Measured resistance between winding 1 and winding 2 busses */
	public float getR1_2(int ndx) throws PsseModelException {return 0f;}
	/** Measured reactance between winding 1 and winding 2 busses */
	public abstract float getX1_2(int ndx) throws PsseModelException;
	/** get winding 1-2 base MVA */
	public float getSBASE1_2(int ndx) throws PsseModelException {return _model.getSBASE();}
	/** winding 1 off-nominal turns ratio */
	public float getWINDV1(int ndx) throws PsseModelException
	{
		return (getCW(ndx)==2)?
				_model.getBus(getI(ndx)).getBASKV() :
				1f;
	}
	/** nominal winding 1 voltage in kV */
	public float getNOMV1(int ndx) throws PsseModelException {return getBus1(ndx).getBASKV();}
	/** winding 1 phase shift (DEG) */
	public float getANG1(int ndx) throws PsseModelException {return 0f;}
	/** winding 1 rating A in MVA */
	public float getRATA1(int ndx) throws PsseModelException {return 0f;}
	/** winding 1 rating B in MVA */
	public float getRATB1(int ndx) throws PsseModelException {return 0f;}
	/** winding 1 rating C in MVA */
	public float getRATC1(int ndx) throws PsseModelException {return 0f;}
	/** Transformer control mode */
	public int getCOD1(int ndx) throws PsseModelException {return 0;}
	/** phase shift angle max in degrees */
	public float getRMA1(int ndx) throws PsseModelException {return 180;}
	/** phase shift angle min in degrees */
	public float getRMI1(int ndx) throws PsseModelException {return -180f;}
	/** VMA upper limit (see PSS/e documentation) */
	public float getVMA1(int ndx) throws PsseModelException {return 0f;}
	/** VMI lower limit (see PSS/e documentation) */
	public float getVMI1(int ndx) throws PsseModelException {return 0f;}
	/** number of taps positions available */
	public int getNTP1(int ndx) throws PsseModelException {return 33;}
	/** transformer impedance correction table */
	public int getTAB1(int ndx) throws PsseModelException {return 0;}
	/** load drop compensation resistance in pu on system base */
	public float getCR1(int ndx) throws PsseModelException {return 0f;}
	/** load drop compensation reactance in pu on system base */
	public float getCX1(int ndx) throws PsseModelException {return 0f;}
	/** return Ownership as a list */
	public OwnershipInList getOwnership(int ndx) throws PsseModelException
	{
		return OwnershipInList.Empty;
		//TODO: implement
	}

}	

