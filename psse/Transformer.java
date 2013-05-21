package com.powerdata.openpa.psse;

import com.powerdata.openpa.psse.TransformerList.TransformerStatus;
import com.powerdata.openpa.tools.BaseObject;

public class Transformer extends BaseObject
{
	protected TransformerList<?> _list;
	
	public Transformer(int ndx, TransformerList<?> list)
	{
		super(ndx);
		_list = list;
	}

	@Override
	public String getObjectID() {return _list.getObjectID(_ndx);}
	
	/* Convenience methods */

	/** Winding 1 bus */
	public Bus getBus1() {return _list.getBus1(_ndx);}
	/** Winding 2 bus */
	public Bus getBus2() {return _list.getBus2(_ndx);}
	/** Winding 3 bus for 3 winding transformers*/
	public Bus getBus3() {return _list.getBus3(_ndx);}

	//TODO:  How to convert to per-unit when CM == 2
	/** Magnetizing Conductance per-unit on 100 MVA Base */
	public float getMagCondPerUnit() {return _list.getMagCondPerUnit(_ndx);}
	/** Magnetizing Susceptance per-unit on 100 MVA Base */
	public float getMagSuscPerUnit() {return _list.getMagSuscPerUnit(_ndx);}
	/** No load loss in Watts (MAG1 when CM=2 */
	public float getNoLoadLoss() {return _list.getNoLoadLoss(_ndx);}
	/** Exciting current p.u. on 100 MVA base  and nominal voltage (NOMV1) when CM=2 */
	public float getExcitingCurrent() {return _list.getExcitingCurrent(_ndx);}
	/** Initial Transformer Status */
	public TransformerStatus getInitTransformerStat() {return _list.getInitTransformerStat(_ndx);}
	// TODO:  How to convert to per-unit impedance on 100MVA base when CZ =2 or 3
	/** get resistance between windings 1 and 2 on 100 MVA base */
	public float getResistance1_2() {return _list.getResistance1_2(_ndx);}
	/** get Reactance between windings 1 and 2 on 100 MVA base */
	public float getReactance1_2() {return _list.getReactance1_2(_ndx);}
	/** get resistance between windings 2 and 3 on 100 MVA base */
	public float getResistance2_3() {return _list.getResistance2_3(_ndx);}
	/** get Reactance between windings 2 and 3 on 100 MVA base */
	public float getReactance2_3() {return _list.getReactance2_3(_ndx);}
	/** get resistance between windings 3 and 1 on 100 MVA base */
	public float getResistance3_1() {return _list.getResistance3_1(_ndx);}
	/** get Reactance between windings 3 and 1 on 100 MVA base */
	public float getReactance3_1() {return _list.getReactance3_1(_ndx);}
	/** get star node angle in radians */
//	public float getAnStarRad() {return _list.getAnStarRad(_ndx);}
//	/** get winding 1 turns ratio p.u. on winding 1 bus base voltage or generate appropriate default */
//	public float getWnd1Ratio() {return _list.getWnd1Ratio(_ndx);}
//	/** get winding 1 nominal KV either directly or generate default value */
//	public float getWnd1NomKV() {return _list.getWnd1NomKV(_ndx);}
	
	
	
	
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
	/** Measured reactiance between winding 1 and winding 2 busses */
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
//	/** voltage magnitude at star node p.u. */
//	public float getVMSTAR() {return _list.getVMSTAR(_ndx);}
//	/** voltage angle at star node in degrees */
//	public float getANSTAR() {return _list.getANSTAR(_ndx);}
//	
//	/* raw PSS/e methods (line 3) */
//	
//	/** winding 1 off-nominal turns ratio */
//	public float getWINDV1() {return _list.getWINDV1(_ndx);}
//	/** nominal winding 1 voltage in kV */
//	public float getNOMV1() {return _list.getNOMV1(_ndx);}
//	/** winding 1 phase shift (DEG) */
//	public float getANG1() {return _list.getANG1(_ndx);}
	
	
	
	
	/** return Ownership as a list */
	public OwnershipList<?> getOwnership() {return _list.getOwnership(_ndx);}
	
	

}
