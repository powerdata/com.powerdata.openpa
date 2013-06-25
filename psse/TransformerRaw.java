package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.BaseObject;

public class TransformerRaw extends BaseObject
{
	protected TransformerRawList _list;
	
	public TransformerRaw(int ndx, TransformerRawList list)
	{
		super(ndx);
		_list = list;
	}

	@Override
	public String getDebugName() throws PsseModelException
	{
		String nm = getNAME().trim();
		return nm.isEmpty() ? 
			String.format("%s %s %s %s", getBus1().getNAME(), getBus2().getNAME(),
				(getBus2() == null) ? "" : getBus2().getNAME(), getCKT()) : nm;
		
	}
	@Override
	public String getObjectID() {return _list.getObjectID(_ndx);}
	
	/* Convenience methods */

	/** Winding 1 bus */ 
	public BusIn getBus1() throws PsseModelException {return _list.getBus1(_ndx);}
	/** Winding 2 bus */
	public BusIn getBus2() throws PsseModelException {return _list.getBus2(_ndx);}
	/** Winding 3 bus for 3 winding transformers*/
	public BusIn getBus3() throws PsseModelException {return _list.getBus3(_ndx);}

	/* Raw PSS/e methods (line 1) */
	
	/** Winding 1 bus number or name */
	public String getI() {return _list.getI(_ndx);}
	/** Winding 2 bus number or name */
	public String getJ() {return _list.getJ(_ndx);}
	/** Winding 3 bus number or name for 3 winding transformers*/
	public String getK() {return _list.getK(_ndx);}
	/** circuit identifier */
	public String getCKT() {return _list.getCKT(_ndx);}
	/** Winding data I/O code */
	public int getCW() {return _list.getCW(_ndx);}
	/** Impedance data I/O code */
	public int getCZ() {return _list.getCZ(_ndx);}
	/** Magnetizing admittance I/O code */
	public int getCM() {return _list.getCM(_ndx);}
	/** Magnetizing conductance */
	public float getMAG1() {return _list.getMAG1(_ndx);}
	/** Magnetizing susceptance */
	public float getMAG2() {return _list.getMAG2(_ndx);}
	/** Nonmetered end code */
	public int getNMETR() {return _list.getNMETR(_ndx);}
	/** Name */
	public String getNAME() {return _list.getNAME(_ndx);}
	/** Initial Transformer status */
	public int getSTAT() {return _list.getSTAT(_ndx);}
	
	/* raw PSS/e methods (line 2) */
	
	/** Measured resistance between winding 1 and winding 2 busses */
	public float getR1_2() {return _list.getR1_2(_ndx);}
	/** Measured reactance between winding 1 and winding 2 busses */
	public float getX1_2() {return _list.getX1_2(_ndx);}
	/** get winding 1-2 base MVA */
	public float getSBASE1_2() {return _list.getSBASE1_2(_ndx);}
	/** Measured resistance between winding 2 and winding 3 busses */
	public float getR2_3() {return _list.getR2_3(_ndx);}
	/** Measured reactiance between winding 2 and winding 3 busses */
	public float getX2_3() {return _list.getX2_3(_ndx);}
	/** get winding 2-3 base MVA */
	public float getSBASE2_3() {return _list.getSBASE2_3(_ndx);}
	/** Measured resistance between winding 2 and winding 3 busses */
	public float getR3_1() {return _list.getR3_1(_ndx);}
	/** Measured reactiance between winding 2 and winding 3 busses */
	public float getX3_1() {return _list.getX3_1(_ndx);}
	/** get winding 2-3 base MVA */
	public float getSBASE3_1() {return _list.getSBASE3_1(_ndx);}
	/** voltage magnitude at star node p.u. */
	public float getVMSTAR() {return _list.getVMSTAR(_ndx);}
	/** voltage angle at star node in degrees */
	public float getANSTAR() {return _list.getANSTAR(_ndx);}
	
	/* raw PSS/e methods (line 3) */
	
	/** winding 1 off-nominal turns ratio */
	public float getWINDV1() throws PsseModelException {return _list.getWINDV1(_ndx);}
	/** nominal winding 1 voltage in kV */
	public float getNOMV1() {return _list.getNOMV1(_ndx);}
	/** winding 1 phase shift (DEG) */
	public float getANG1() {return _list.getANG1(_ndx);}
	/** winding 1 rating A in MVA */
	public float getRATA1() {return _list.getRATA1(_ndx);}
	/** winding 1 rating B in MVA */
	public float getRATB1() {return _list.getRATB1(_ndx);}
	/** winding 1 rating C in MVA */
	public float getRATC1() {return _list.getRATC1(_ndx);}
	/** Transformer control mode */
	public int getCOD1() {return _list.getCOD1(_ndx);}
	/** controlled bus */
	public String getCONT1() {return _list.getCONT1(_ndx);}
	/** RMA upper limit (see PSS/e documentation) */
	public float getRMA1() throws PsseModelException {return _list.getRMA1(_ndx);}
	/** RMI lower limit (see PSS/e documentation) */
	public float getRMI1() throws PsseModelException {return _list.getRMI1(_ndx);}
	/** VMA upper limit (see PSS/e documentation) */
	public float getVMA1() throws PsseModelException {return _list.getVMA1(_ndx);}
	/** VMI lower limit (see PSS/e documentation) */
	public float getVMI1() throws PsseModelException {return _list.getVMI1(_ndx);}
	/** number of taps positions available */
	public int getNTP1() throws PsseModelException {return _list.getNTP1(_ndx);}
	/** transformer impedance correction table */
	public int getTAB1() throws PsseModelException {return _list.getTAB1(_ndx);}
	/** load drop compensation resistance in pu on system base */
	public float getCR1() throws PsseModelException {return _list.getCR1(_ndx);}
	/** load drop compensation reactance in pu on system base */
	public float getCX1() throws PsseModelException {return _list.getCX1(_ndx);}
	
	/* raw PSS/e methods (line 4) */
	
	/** winding 2 off-nominal turns ratio */
	public float getWINDV2() throws PsseModelException {return _list.getWINDV2(_ndx);}
	/** nominal winding 2 voltage in kV */
	public float getNOMV2() {return _list.getNOMV2(_ndx);}
	/** winding 2 phase shift (DEG) */
	public float getANG2() {return _list.getANG2(_ndx);}
	/** winding 2 rating A in MVA */
	public float getRATA2() {return _list.getRATA2(_ndx);}
	/** winding 2 rating B in MVA */
	public float getRATB2() {return _list.getRATB2(_ndx);}
	/** winding 2 rating C in MVA */
	public float getRATC2() {return _list.getRATC2(_ndx);}
	/** Transformer control mode */
	public int getCOD2() {return _list.getCOD2(_ndx);}
	/** controlled bus */
	public String getCONT2() {return _list.getCONT2(_ndx);}
	/** RMA upper limit (see PSS/e documentation) */
	public float getRMA2() throws PsseModelException {return _list.getRMA2(_ndx);}
	/** RMI lower limit (see PSS/e documentation) */
	public float getRMI2() throws PsseModelException {return _list.getRMI2(_ndx);}
	/** VMA upper limit (see PSS/e documentation) */
	public float getVMA2() throws PsseModelException {return _list.getVMA2(_ndx);}
	/** VMI lower limit (see PSS/e documentation) */
	public float getVMI2() throws PsseModelException {return _list.getVMI2(_ndx);}
	/** number of taps positions available */
	public int getNTP2() throws PsseModelException {return _list.getNTP2(_ndx);}
	/** transformer impedance correction table */
	public int getTAB2() throws PsseModelException {return _list.getTAB2(_ndx);}
	/** load drop compensation resistance in pu on system base */
	public float getCR2() throws PsseModelException {return _list.getCR2(_ndx);}
	/** load drop compensation reactance in pu on system base */
	public float getCX2() throws PsseModelException {return _list.getCX2(_ndx);}

	/* raw PSS/e methods (line 5) */
	
	/** winding 3 off-nominal turns ratio */
	public float getWINDV3() throws PsseModelException {return _list.getWINDV3(_ndx);}
	/** nominal winding 3 voltage in kV */
	public float getNOMV3() {return _list.getNOMV3(_ndx);}
	/** winding 3 phase shift (DEG) */
	public float getANG3() {return _list.getANG3(_ndx);}
	/** winding 3 rating A in MVA */
	public float getRATA3() {return _list.getRATA3(_ndx);}
	/** winding 3 rating B in MVA */
	public float getRATB3() {return _list.getRATB3(_ndx);}
	/** winding 3 rating C in MVA */
	public float getRATC3() {return _list.getRATC3(_ndx);}
	/** Transformer control mode */
	public int getCOD3() {return _list.getCOD3(_ndx);}
	/** controlled bus */
	public String getCONT3() {return _list.getCONT3(_ndx);}
	/** RMA upper limit (see PSS/e documentation) */
	public float getRMA3() throws PsseModelException {return _list.getRMA3(_ndx);}
	/** RMI lower limit (see PSS/e documentation) */
	public float getRMI3() throws PsseModelException {return _list.getRMI3(_ndx);}
	/** VMA upper limit (see PSS/e documentation) */
	public float getVMA3() throws PsseModelException {return _list.getVMA3(_ndx);}
	/** VMI lower limit (see PSS/e documentation) */
	public float getVMI3() throws PsseModelException {return _list.getVMI3(_ndx);}
	/** number of taps positions available */
	public int getNTP3() throws PsseModelException {return _list.getNTP3(_ndx);}
	/** transformer impedance correction table */
	public int getTAB3() throws PsseModelException {return _list.getTAB3(_ndx);}
	/** load drop compensation resistance in pu on system base */
	public float getCR3() throws PsseModelException {return _list.getCR3(_ndx);}
	/** load drop compensation reactance in pu on system base */
	public float getCX3() throws PsseModelException {return _list.getCX3(_ndx);}

	/** return Ownership as a list */
	public OwnershipInList getOwnership() {return _list.getOwnership(_ndx);}

}
