package com.powerdata.openpa.psse;

public abstract class TransformerInList extends PsseBaseInputList<TransformerIn>
{
	protected BusInList _busses;
	
	public static final TransformerInList Empty = new TransformerInList()
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
	
	protected TransformerInList() {super();}
	public TransformerInList(PsseModel model) throws PsseModelException 
	{
		super(model);
		_busses = model.getBuses();
	}
	
	/** Get a Transformer by it's index. */
	@Override
	public TransformerIn get(int ndx) { return new TransformerIn(ndx,this); }
	/** Get a Transformer by it's ID. */
	@Override
	public TransformerIn get(String id) { return super.get(id); }
	
	/* Convenience methods */
	
	public BusIn getBus1(int ndx) throws PsseModelException {return _busses.get(getI(ndx));}
	public BusIn getBus2(int ndx) throws PsseModelException {return _busses.get(getJ(ndx));}

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
	public float getWINDV1(int ndx) throws PsseModelException {return (getCW(ndx)==2)?_busses.get(getI(ndx)).getBASKV():1f;}
	/** nominal winding 1 voltage in kV */
	public float getNOMV1(int ndx) throws PsseModelException {return _busses.get(getI(ndx)).getBASKV();}
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
	/** controlled bus */
	public String getCONT1(int ndx) throws PsseModelException {return "0";}
	/** RMA upper limit (see PSS/e documentation) */
	public float getRMA1(int ndx) throws PsseModelException
	{
		if (Math.abs(getCOD1(ndx)) < 3 && getCW(ndx) == 2)
		{
			return 1.1f * _busses.get(getI(ndx)).getBASKV();
		}
		return 1.1f;
	}
	/** RMI lower limit (see PSS/e documentation) */
	public float getRMI1(int ndx) throws PsseModelException
	{
		if (Math.abs(getCOD1(ndx)) < 3 && getCW(ndx) == 2)
		{
			return 0.9f * _busses.get(getI(ndx)).getBASKV();
		}
		return 0.9f;
	}
	/** VMA upper limit (see PSS/e documentation) */
	public float getVMA1(int ndx) throws PsseModelException
	{
		return (Math.abs(getCOD1(ndx)) == 2) ? 99999f : 1.1f;
	}
	/** VMI lower limit (see PSS/e documentation) */
	public float getVMI1(int ndx) throws PsseModelException
	{
		return (Math.abs(getCOD1(ndx)) == 2) ? -99999f : 0.9f;
	}
	/** number of taps positions available */
	public int getNTP1(int ndx) throws PsseModelException {return 33;}
	/** transformer impedance correction table */
	public  int getTAB1(int ndx) throws PsseModelException {return 0;}
	/** load drop compensation resistance in pu on system base */
	public float getCR1(int ndx) throws PsseModelException {return 0f;}
	/** load drop compensation reactance in pu on system base */
	public float getCX1(int ndx) throws PsseModelException {return 0f;}
	/** return Ownership as a list */
	public OwnershipInList getOwnership(int ndx) throws PsseModelException
	{
		return OwnershipInList.Empty;
	}
	
	/** Winding 2 off-nominal turns ratio */
	public float getWINDV2(int ndx) throws PsseModelException {return ((getCW(ndx)==2)?_busses.get(getJ(ndx)).getBASKV():1f);}
	/** Winding 2 nominal voltage */
	public float getNOMV2(int ndx) throws PsseModelException {return _busses.get(getJ(ndx)).getBASKV();}
	/** Allow control mode to be specified on winding 2 in order to understand tap limits. */
	public int getCOD2(int ndx) throws PsseModelException {return 0;}
	/**
	 * Allow a tap range to be specified on winding 2.
	 */
	public float getRMA2(int ndx) throws PsseModelException
	{
		if (Math.abs(getCOD2(ndx)) < 3 && getCW(ndx) == 2)
		{
			return 1.1f * _busses.get(getJ(ndx)).getBASKV();
		}
		return 1.1f;
	}

	/**
	 * Allow a tap range to be specified on winding 2.
	 */
	public float getRMI2(int ndx) throws PsseModelException
	{
		if (Math.abs(getCOD2(ndx)) < 3 && getCW(ndx) == 2)
		{
			return 0.9f * _busses.get(getJ(ndx)).getBASKV();
		}
		return 0.9f;
	}
	/**
	 * Allow a number of positions to be specified on winding 2.
	 */
	public float getNTP2(int ndx) throws PsseModelException {return 33;}
}	
