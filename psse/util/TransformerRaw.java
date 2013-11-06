package com.powerdata.openpa.psse.util;

import com.powerdata.openpa.psse.Bus;
import com.powerdata.openpa.psse.OwnershipList;
import com.powerdata.openpa.psse.PsseBaseObject;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.psse.TransformerCtrlMode;


public class TransformerRaw extends PsseBaseObject 
{
	protected TransformerRawList _list;
	
	public TransformerRaw(int ndx, TransformerRawList list)
	{
		super(list,ndx);
		_list = list;
	}

	@Override
	public String getObjectID() throws PsseModelException {return _list.getObjectID(_ndx);}

	public Bus getBusI() throws PsseModelException {return _list.getBusI(_ndx);}
	public Bus getBusJ() throws PsseModelException {return _list.getBusJ(_ndx);}
	public Bus getBusK() throws PsseModelException {return _list.getBusK(_ndx);}

	public TransformerCtrlMode getCtrlMode1()  throws PsseModelException {return _list.getCtrlMode1(_ndx);}
	public TransformerCtrlMode getCtrlMode2()  throws PsseModelException {return _list.getCtrlMode2(_ndx);}
	public TransformerCtrlMode getCtrlMode3()  throws PsseModelException {return _list.getCtrlMode3(_ndx);}
	
	/** Get number or name of bus connected to first winding */
	public String getI() throws PsseModelException {return _list.getI(_ndx);}
	/** Get number or name of bus connected to second winding */
	public String getJ() throws PsseModelException {return _list.getJ(_ndx);}
	/** If 3-winding, get number or name of bus connected to third winding */
	public String getK() throws PsseModelException {return _list.getK(_ndx);}
	/** Get circuit ID (used to differentiate parallel branches) */
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
	/** Non-metered end code */
	public int getNMETR() throws PsseModelException {return _list.getNMETR(_ndx);}
	/** Transformer Name */
	public String getNAME() throws PsseModelException {return _list.getNAME(_ndx);}
	/** Initial transformer status */
	public int getSTAT() throws PsseModelException {return _list.getSTAT(_ndx);}
	/** return Ownership as a list */
	public OwnershipList getOwnership() throws PsseModelException {return _list.getOwnership(_ndx);}
	
	/* line 2 */
	
	/** resistance between first and second windings */
	public float getR1_2() throws PsseModelException {return _list.getR1_2(_ndx);}
	/** reactance between first and second windings */
	public float getX1_2() throws PsseModelException {return _list.getX1_2(_ndx);}
	/** Winding 1 to 2 base MVA */
	public float getSBASE1_2() throws PsseModelException {return _list.getSBASE1_2(_ndx);}
	/** resistance between second and third windings */
	public float getR2_3() throws PsseModelException {return _list.getR2_3(_ndx);}
	/** reactance between second and third windings */
	public float getX2_3() throws PsseModelException {return _list.getX2_3(_ndx);}
	/** Winding 2 to 3 base MVA */
	public float getSBASE2_3() throws PsseModelException {return _list.getSBASE2_3(_ndx);}
	/** resistance between third and first windings */
	public float getR3_1() throws PsseModelException {return _list.getR3_1(_ndx);}
	/** reactance between third and first windings */
	public float getX3_1() throws PsseModelException {return _list.getX3_1(_ndx);}
	/** Winding 3 to 1 base MVA */
	public float getSBASE3_1() throws PsseModelException {return _list.getSBASE3_1(_ndx);}
	/** Star node voltage magnitude */
	public float getVMSTAR() throws PsseModelException {return _list.getVMSTAR(_ndx);}
	/** Star node voltage angle */
	public float getANSTAR() throws PsseModelException {return _list.getANSTAR(_ndx);}
	
	/* line 3 */
	
	/** Winding 1 off-nominal turns ratio */
	public float getWINDV1() throws PsseModelException {return _list.getWINDV1(_ndx);}
	/** Winding 1 nominal voltage */
	public float getNOMV1() throws PsseModelException {return _list.getNOMV1(_ndx);}
	/** Winding 1 phase shift angle (degrees) */
	public float getANG1() throws PsseModelException {return _list.getANG1(_ndx);}
	/** Winding 1 first rating */
	public float getRATA1() throws PsseModelException {return _list.getRATA1(_ndx);}
	/** Winding 1 second rating */
	public float getRATB1() throws PsseModelException {return _list.getRATB1(_ndx);}
	/** Winding 1 third rating */
	public float getRATC1() throws PsseModelException {return _list.getRATC1(_ndx);}
	/** Transformer control mode of winding 1 tap*/
	public int getCOD1() throws PsseModelException {return _list.getCOD1(_ndx);}
	/** Number or name of winding 1 voltage controlled bus */
	public String getCONT1() throws PsseModelException {return _list.getCONT1(_ndx);}
	/** Winding 1 tap maximum limit */
	public float getRMA1() throws PsseModelException {return _list.getRMA1(_ndx);}
	/** Winding 1 tap minimum limit */
	public float getRMI1() throws PsseModelException {return _list.getRMI1(_ndx);}
	/** Winding 1 band control maximum limit */
	public float getVMA1() throws PsseModelException {return _list.getVMA1(_ndx);}
	/** Winding 1 band control minimum limit */
	public float getVMI1() throws PsseModelException {return _list.getVMI1(_ndx);}
	/** Winding 1 tap available positions */
	public int getNTP1() throws PsseModelException {return _list.getNTP1(_ndx);}
	/** Winding 1 impedance correction table index */
	public int getTAB1() throws PsseModelException {return _list.getTAB1(_ndx);}
	/** Load drop compensation resistance */
	public float getCR1() throws PsseModelException {return _list.getCR1(_ndx);}
	/** Load drop compensation reactance */
	public float getCX1() throws PsseModelException {return _list.getCX1(_ndx);}
	
	/* line 4 */

	/** Winding 2 off-nominal turns ratio */
	public float getWINDV2() throws PsseModelException {return _list.getWINDV2(_ndx);}
	/** Winding 2 nominal voltage */
	public float getNOMV2() throws PsseModelException {return _list.getNOMV2(_ndx);}
	/** Winding 2 phase shift angle (degrees) */
	public float getANG2() throws PsseModelException {return _list.getANG2(_ndx);}
	/** Winding 2 first rating */
	public float getRATA2() throws PsseModelException {return _list.getRATA2(_ndx);}
	/** Winding 2 second rating */
	public float getRATB2() throws PsseModelException {return _list.getRATB2(_ndx);}
	/** Winding 2 third rating */
	public float getRATC2() throws PsseModelException {return _list.getRATC2(_ndx);}
	/** Transformer control mode of Winding 2 tap*/
	public int getCOD2() throws PsseModelException {return _list.getCOD2(_ndx);}
	/** Number or name of Winding 2 voltage controlled bus */
	public String getCONT2() throws PsseModelException {return _list.getCONT2(_ndx);}
	/** Winding 2 tap maximum limit */
	public float getRMA2() throws PsseModelException {return _list.getRMA2(_ndx);}
	/** Winding 2 tap minimum limit */
	public float getRMI2() throws PsseModelException {return _list.getRMI2(_ndx);}
	/** Winding 2 band control maximum limit */
	public float getVMA2() throws PsseModelException {return _list.getVMA2(_ndx);}
	/** Winding 2 band control minimum limit */
	public float getVMI2() throws PsseModelException {return _list.getVMI2(_ndx);}
	/** Winding 2 tap available positions */
	public int getNTP2() throws PsseModelException {return _list.getNTP2(_ndx);}
	/** Winding 2 impedance correction table index */
	public int getTAB2() throws PsseModelException {return _list.getTAB2(_ndx);}
	/** Load drop compensation resistance */
	public float getCR2() throws PsseModelException {return _list.getCR2(_ndx);}
	/** Load drop compensation reactance */
	public float getCX2() throws PsseModelException {return _list.getCX2(_ndx);}
	
	/* line 5 */

	/** Winding 3 off-nominal turns ratio */
	public float getWINDV3() throws PsseModelException {return _list.getWINDV3(_ndx);}
	/** Winding 3 nominal voltage */
	public float getNOMV3() throws PsseModelException {return _list.getNOMV3(_ndx);}
	/** Winding 3 phase shift angle (degrees) */
	public float getANG3() throws PsseModelException {return _list.getANG3(_ndx);}
	/** Winding 3 first rating */
	public float getRATA3() throws PsseModelException {return _list.getRATA3(_ndx);}
	/** Winding 3 second rating */
	public float getRATB3() throws PsseModelException {return _list.getRATB3(_ndx);}
	/** Winding 3 third rating */
	public float getRATC3() throws PsseModelException {return _list.getRATC3(_ndx);}
	/** Transformer control mode of Winding 3 tap*/
	public int getCOD3() throws PsseModelException {return _list.getCOD3(_ndx);}
	/** Number or name of Winding 3 voltage controlled bus */
	public String getCONT3() throws PsseModelException {return _list.getCONT3(_ndx);}
	/** Winding 3 tap maximum limit */
	public float getRMA3() throws PsseModelException {return _list.getRMA3(_ndx);}
	/** Winding 3 tap minimum limit */
	public float getRMI3() throws PsseModelException {return _list.getRMI3(_ndx);}
	/** Winding 3 band control maximum limit */
	public float getVMA3() throws PsseModelException {return _list.getVMA3(_ndx);}
	/** Winding 3 band control minimum limit */
	public float getVMI3() throws PsseModelException {return _list.getVMI3(_ndx);}
	/** Winding 3 tap available positions */
	public int getNTP3() throws PsseModelException {return _list.getNTP3(_ndx);}
	/** Winding 3 impedance correction table index */
	public int getTAB3() throws PsseModelException {return _list.getTAB3(_ndx);}
	/** Load drop compensation resistance */
	public float getCR3() throws PsseModelException {return _list.getCR3(_ndx);}
	/** Load drop compensation reactance */
	public float getCX3() throws PsseModelException {return _list.getCX3(_ndx);}

}
