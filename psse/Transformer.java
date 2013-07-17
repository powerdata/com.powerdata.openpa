package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.BaseObject;

/**
 * View all transformers as 2-winding.
 * 
 * @author chris@powerdata.com
 *
 */
public class Transformer extends BaseObject
{
	protected TransformerList _list;
	
	public Transformer(int ndx, TransformerList list)
	{
		super(ndx);
		_list = list;
	}

	@Override
	public String getObjectID() throws PsseModelException {return _list.getObjectID(_ndx);}
	
	/* Convenience methods */

	/** Winding 1 bus */ 
	public Bus getBus1() throws PsseModelException {return _list.getBus1(_ndx);}
	/** Winding 2 bus */
	public Bus getBus2() throws PsseModelException {return _list.getBus2(_ndx);}

	
	/* RAW methods */
	/** Winding 1 bus number or name */ 
	public String getI() throws PsseModelException {return _list.getI(_ndx);}
	/** Winding 2 bus number or name */ 
	public String getJ() throws PsseModelException {return _list.getJ(_ndx);}
	/** circuit identifier */ 
	public String getCKT() throws PsseModelException {return _list.getCKT(_ndx);}
	/** Winding data I/O code */ 
	public int getCW() throws PsseModelException {return _list.getCW(_ndx);}
	/** Impedance data I/O code */
	public int getCZ() throws PsseModelException {return _list.getCZ(_ndx);}
	/** Magnetizing admittance I/O code */
	public int getCM() throws PsseModelException {return _list.getCM(_ndx);}
	/** Magnetizing conductance */
	public float getMAG1() throws PsseModelException {return _list.getMAG1(_ndx);}
	/** Magnetizing susceptance */
	public float getMAG2() throws PsseModelException {return _list.getMAG2(_ndx);}
	/** Nonmetered end code */
	public int getNMETR() throws PsseModelException {return _list.getNMETR(_ndx);}
	/** Name */
	public String getNAME() throws PsseModelException {return _list.getNAME(_ndx);}
	/** Initial Transformer status */
	public int getSTAT() throws PsseModelException {return _list.getSTAT(_ndx);}
	/** Measured resistance between winding 1 and winding 2 busses */
	public float getR1_2() throws PsseModelException {return _list.getR1_2(_ndx);}
	/** Measured reactance between winding 1 and winding 2 busses */
	public float getX1_2() throws PsseModelException {return _list.getX1_2(_ndx);}
	/** get winding 1-2 base MVA */
	public float getSBASE1_2() throws PsseModelException {return _list.getSBASE1_2(_ndx);}
	/** winding 1 off-nominal turns ratio */
	public float getWINDV1() throws PsseModelException {return _list.getWINDV1(_ndx);}
	/** nominal winding 1 voltage in kV */
	public float getNOMV1() throws PsseModelException {return _list.getNOMV1(_ndx);}
	/** winding 1 phase shift (DEG) */
	public float getANG1() throws PsseModelException {return _list.getANG1(_ndx);}
	/** winding 1 rating A in MVA */
	public float getRATA1() throws PsseModelException {return _list.getRATA1(_ndx);}
	/** winding 1 rating B in MVA */
	public float getRATB1() throws PsseModelException {return _list.getRATB1(_ndx);}
	/** winding 1 rating C in MVA */
	public float getRATC1() throws PsseModelException {return _list.getRATC1(_ndx);}
	/** Transformer control mode */
	public int getCOD1() throws PsseModelException {return _list.getCOD1(_ndx);}
	/** controlled bus */
	public String getCONT1() throws PsseModelException {return _list.getCONT1(_ndx);}
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
	/** return Ownership as a list */
	public OwnershipList getOwnership() throws PsseModelException {return _list.getOwnership(_ndx);}

	
}
