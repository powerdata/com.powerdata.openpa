package com.powerdata.openpa.psse.conversions;

import com.powerdata.openpa.psse.BusIn;
import com.powerdata.openpa.psse.BusInList;
import com.powerdata.openpa.psse.OwnershipInList;
import com.powerdata.openpa.psse.PsseBaseInputList;
import com.powerdata.openpa.psse.PsseModel;
import com.powerdata.openpa.psse.PsseModelException;

public abstract class TransformerRawList extends PsseBaseInputList<TransformerRaw>
{
	protected BusInList _busses;
	
	public TransformerRawList(PsseModel model) throws PsseModelException
	{
		super(model);
		_busses = model.getBuses();
	}
	
	public BusIn getBus1(int ndx) throws PsseModelException {return _busses.get(getI(ndx));}
	public BusIn getBus2(int ndx) throws PsseModelException {return _busses.get(getJ(ndx));}
	public BusIn getBus3(int ndx) throws PsseModelException {return _busses.get(getK(ndx));}

	

	/* Line 1 */
	
	/** Get number or name of bus connected to first winding */
	public abstract String getI(int ndx) throws PsseModelException;
	/** Get number or name of bus connected to second winding */
	public abstract String getJ(int ndx) throws PsseModelException;
	/** If 3-winding, get number or name of bus connected to third winding */
	public String getK(int ndx) throws PsseModelException {return "0";}
	/** Get circuit ID (used to differentiate parallel branches) */
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
	/** Non-metered end code */
	public int getNMETR(int ndx) throws PsseModelException {return 2;}
	/** Transformer Name */
	public String getNAME(int ndx) throws PsseModelException {return "";}
	/** Initial transformer status */
	public int getSTAT(int ndx) throws PsseModelException {return 1;}
	/** return Ownership as a list */
	public abstract OwnershipInList getOwnership(int ndx) throws PsseModelException;
	
	/* line 2 */
	
	/** resistance between first and second windings */
	public float getR1_2(int ndx) throws PsseModelException {return 0f;}
	/** reactance between first and second windings */
	public abstract float getX1_2(int ndx) throws PsseModelException;
	/** Winding 1 to 2 base MVA */
	public float getSBASE1_2(int ndx) throws PsseModelException {return _model.getSBASE();}
	/** resistance between second and third windings */
	public float getR2_3(int ndx) throws PsseModelException {return 0f;}
	/** reactance between second and third windings */
	public abstract float getX2_3(int ndx) throws PsseModelException;
	/** Winding 2 to 3 base MVA */
	public float getSBASE2_3(int ndx) throws PsseModelException {return _model.getSBASE();}
	/** resistance between third and first windings */
	public float getR3_1(int ndx) throws PsseModelException {return 0f;}
	/** reactance between third and first windings */
	public abstract float getX3_1(int ndx) throws PsseModelException;
	/** Winding 3 to 1 base MVA */
	public float getSBASE3_1(int ndx) throws PsseModelException {return _model.getSBASE();}
	/** Star node voltage magnitude */
	public float getVMSTAR(int ndx) throws PsseModelException {return 1f;}
	/** Star node voltage angle */
	public float getANSTAR(int ndx) throws PsseModelException {return 0f;}

	/* line 3 */
	
	/** Winding 1 off-nominal turns ratio */
	public float getWINDV1(int ndx) throws PsseModelException {return ((getCW(ndx)==2)?_busses.get(getI(ndx)).getBASKV():1f);}
	/** Winding 1 nominal voltage */
	public float getNOMV1(int ndx) throws PsseModelException {return _busses.get(getI(ndx)).getBASKV();}
	/** Winding 1 phase shift angle (degrees) */
	public float getANG1(int ndx) throws PsseModelException {return 0f;}
	/** Winding 1 first rating */
	public float getRATA1(int ndx) throws PsseModelException {return 0f;}
	/** Winding 1 second rating */
	public float getRATB1(int ndx) throws PsseModelException {return 0f;}
	/** Winding 1 third rating */
	public float getRATC1(int ndx) throws PsseModelException {return 0f;}
	/** Transformer control mode of winding 1 tap*/
	public int getCOD1(int ndx) throws PsseModelException {return 0;}
	/** Number or name of winding 1 voltage controlled bus */
	public String getCONT1(int ndx) throws PsseModelException {return "0";}
	/** Winding 1 tap maximum limit */
	public float getRMA1(int ndx) throws PsseModelException
	{
		int cod = Math.abs(getCOD1(ndx));
		int cw = getCW(ndx);
		if (cod == 3)
		{
			return 180f;
		}
		else if (cod < 3 && cw == 2)
		{
			return 1.1f * _busses.get(getI(ndx)).getBASKV();
		}
		return 1.1f;
	}
	/** Winding 1 tap minimum limit */
	public float getRMI1(int ndx) throws PsseModelException
	{
		int cod = Math.abs(getCOD1(ndx));
		int cw = getCW(ndx);
		if (cod == 3)
		{
			return -180f;
		}
		else if (cod < 3 && cw == 2)
		{
			return 0.9f * _busses.get(getI(ndx)).getBASKV();
		}
		return 0.9f;
	}
	/** Winding 1 band control maximum limit */
	public float getVMA1(int ndx) throws PsseModelException
	{
		int cod = Math.abs(getCOD1(ndx));
		if (cod == 2 || cod == 3)
			return 99999f;
		return 1.1f;
	}
	/** Winding 1 band control minimum limit */
	public float getVMI1(int ndx) throws PsseModelException
	{
		int cod = Math.abs(getCOD1(ndx));
		if (cod == 2 || cod == 3)
			return -99999f;
		return 0.9f;
	}
	/** Winding 1 tap available positions */
	public int getNTP1(int ndx) throws PsseModelException {return 33;}
	/** Winding 1 impedance correction table index */
	public int getTAB1(int ndx) throws PsseModelException {return 0;}
	/** Load drop compensation resistance */
	public float getCR1(int ndx) throws PsseModelException {return 0f;}
	/** Load drop compensation reactance */
	public float getCX1(int ndx) throws PsseModelException {return 0f;}
	
	/* line 4 */
	
	/** Winding 2 off-nominal turns ratio */
	public float getWINDV2(int ndx) throws PsseModelException {return ((getCW(ndx)==2)?_busses.get(getJ(ndx)).getBASKV():1f);}
	/** Winding 2 nominal voltage */
	public float getNOMV2(int ndx) throws PsseModelException {return _busses.get(getJ(ndx)).getBASKV();}
	/** Winding 2 phase shift angle (degrees) */
	public float getANG2(int ndx) throws PsseModelException {return 0f;}
	/** Winding 2 first rating */
	public float getRATA2(int ndx) throws PsseModelException {return 0f;}
	/** Winding 2 second rating */
	public float getRATB2(int ndx) throws PsseModelException {return 0f;}
	/** Winding 2 third rating */
	public float getRATC2(int ndx) throws PsseModelException {return 0f;}
	/** Transformer control mode of Winding 2 tap*/
	public int getCOD2(int ndx) throws PsseModelException {return 0;}
	/** Number or name of Winding 2 voltage controlled bus */
	public String getCONT2(int ndx) throws PsseModelException {return "0";}
	/** Winding 2 tap maximum limit */
	public float getRMA2(int ndx) throws PsseModelException
	{
		int cod = Math.abs(getCOD2(ndx));
		int cw = getCW(ndx);
		if (cod == 3)
		{
			return 180f;
		}
		else if (cod < 3 && cw == 2)
		{
			return 1.1f * _busses.get(getJ(ndx)).getBASKV();
		}
		return 1.1f;
	}
	/** Winding 2 tap minimum limit */
	public float getRMI2(int ndx) throws PsseModelException
	{
		int cod = Math.abs(getCOD2(ndx));
		int cw = getCW(ndx);
		if (cod == 3)
		{
			return -180f;
		}
		else if (cod < 3 && cw == 2)
		{
			return 0.9f * _busses.get(getJ(ndx)).getBASKV();
		}
		return 0.9f;
	}
	/** Winding 2 band control maximum limit */
	public float getVMA2(int ndx) throws PsseModelException
	{
		int cod = Math.abs(getCOD2(ndx));
		if (cod == 2 || cod == 3)
			return 99999f;
		return 1.1f;
	}
	/** Winding 2 band control minimum limit */
	public float getVMI2(int ndx) throws PsseModelException
	{
		int cod = Math.abs(getCOD2(ndx));
		if (cod == 2 || cod == 3)
			return -99999f;
		return 0.9f;
	}
	/** Winding 2 tap available positions */
	public int getNTP2(int ndx) throws PsseModelException {return 33;}
	/** Winding 2 impedance correction table index */
	public int getTAB2(int ndx) throws PsseModelException {return 0;}
	/** Load drop compensation resistance */
	public float getCR2(int ndx) throws PsseModelException {return 0f;}
	/** Load drop compensation reactance */
	public float getCX2(int ndx) throws PsseModelException {return 0f;}


	/* line 5 */
	
	/** Winding 3 off-nominal turns ratio */
	public float getWINDV3(int ndx) throws PsseModelException {return ((getCW(ndx)==2)?_busses.get(getK(ndx)).getBASKV():1f);}
	/** Winding 3 nominal voltage */
	public float getNOMV3(int ndx) throws PsseModelException {return _busses.get(getK(ndx)).getBASKV();}
	/** Winding 3 phase shift angle (degrees) */
	public float getANG3(int ndx) throws PsseModelException {return 0f;}
	/** Winding 3 first rating */
	public float getRATA3(int ndx) throws PsseModelException {return 0f;}
	/** Winding 3 second rating */
	public float getRATB3(int ndx) throws PsseModelException {return 0f;}
	/** Winding 3 third rating */
	public float getRATC3(int ndx) throws PsseModelException {return 0f;}
	/** Transformer control mode of Winding 3 tap*/
	public int getCOD3(int ndx) throws PsseModelException {return 0;}
	/** Number or name of Winding 3 voltage controlled bus */
	public String getCONT3(int ndx) throws PsseModelException {return "0";}
	/** Winding 3 tap maximum limit */
	public float getRMA3(int ndx) throws PsseModelException
	{
		int cod = Math.abs(getCOD3(ndx));
		int cw = getCW(ndx);
		if (cod == 3)
		{
			return 180f;
		}
		else if (cod < 3 && cw == 2)
		{
			return 1.1f * _busses.get(getK(ndx)).getBASKV();
		}
		return 1.1f;
	}
	/** Winding 3 tap minimum limit */
	public float getRMI3(int ndx) throws PsseModelException
	{
		int cod = Math.abs(getCOD3(ndx));
		int cw = getCW(ndx);
		if (cod == 3)
		{
			return -180f;
		}
		else if (cod < 3 && cw == 2)
		{
			return 0.9f * _busses.get(getK(ndx)).getBASKV();
		}
		return 0.9f;
	}
	/** Winding 3 band control maximum limit */
	public float getVMA3(int ndx) throws PsseModelException
	{
		int cod = Math.abs(getCOD3(ndx));
		if (cod == 2 || cod == 3)
			return 99999f;
		return 1.1f;
	}
	/** Winding 3 band control minimum limit */
	public float getVMI3(int ndx) throws PsseModelException
	{
		int cod = Math.abs(getCOD3(ndx));
		if (cod == 2 || cod == 3)
			return -99999f;
		return 0.9f;
	}
	/** Winding 3 tap available positions */
	public int getNTP3(int ndx) throws PsseModelException {return 33;}
	/** Winding 3 impedance correction table index */
	public int getTAB3(int ndx) throws PsseModelException {return 0;}
	/** Load drop compensation resistance */
	public float getCR3(int ndx) throws PsseModelException {return 0f;}
	/** Load drop compensation reactance */
	public float getCX3(int ndx) throws PsseModelException {return 0f;}

}
