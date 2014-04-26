package com.powerdata.openpa.psse;

import com.powerdata.openpa.psse.conversions.XfrMagYTool;
import com.powerdata.openpa.psse.conversions.XfrWndTool;
import com.powerdata.openpa.psse.conversions.XfrZToolFactory;
import com.powerdata.openpa.tools.Complex;
import com.powerdata.openpa.tools.PAMath;

public abstract class TransformerList extends PsseBaseList<Transformer>
{
	protected XfrZToolFactory _ztool;
	
	public static final TransformerList Empty = new TransformerList()
	{
		@Override
		public String getI(int ndx) {return null;}
		@Override
		public String getJ(int ndx) {return null;}
		@Override
		public float getX1_2(int ndx) {return 0f;}
		@Override
		public String getObjectID(int ndx) {return null;}
		@Override
		public int size() {return 0;}
		@Override
		public long getKey(int ndx) {return -1;}
	};
	
	protected TransformerList() {super();}
	public TransformerList(PsseModel model) throws PsseModelException 
	{
		super(model);
		_ztool = XfrZToolFactory.Open(_model.getPsseVersion());
	}
	
	/** Get a Transformer by it's index. */
	@Override
	public Transformer get(int ndx) { return new Transformer(ndx,this); }
	/** Get a Transformer by it's ID. */
	@Override
	public Transformer get(String id) { return super.get(id); }
	
	/* Convenience methods */
	
	public Bus getFromBus(int ndx) throws PsseModelException {return _model.getBus(getI(ndx));}
	public Bus getToBus(int ndx) throws PsseModelException {return _model.getBus(getJ(ndx));}

	public float getR(int ndx) throws PsseModelException {return getZ(ndx).re();}
	public float getX(int ndx) throws PsseModelException {return getZ(ndx).im();}
	public Complex getZ(int ndx) throws PsseModelException
	{
		return _ztool.get(getCZ(ndx)).convert2W(get(ndx));
	}
	public Complex getY(int ndx) throws PsseModelException {return getZ(ndx).inv();}
	public float getFromTap(int ndx) throws PsseModelException {return XfrWndTool.get(getCW(ndx)).getRatio1(get(ndx));}
	public float getToTap(int ndx) throws PsseModelException {return XfrWndTool.get(getCW(ndx)).getRatio2(get(ndx));}
	public float getPhaseShift(int ndx) throws PsseModelException {return PAMath.deg2rad(getANG1(ndx));}
	public boolean isInSvc(int ndx) throws PsseModelException {return getSTAT(ndx) == 1;}
	public void setInSvc(int ndx, boolean state) throws PsseModelException {}
	public TransformerCtrlMode getCtrlMode(int ndx) throws PsseModelException {return TransformerCtrlMode.fromCode(getCOD1(ndx));}
	public boolean getRegStat(int ndx) throws PsseModelException {return getCOD1(ndx)>0;}
	public void setRegStat(int ndx, boolean stat) throws PsseModelException {}

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
	public float getWINDV1(int ndx) throws PsseModelException {return (getCW(ndx)==2)?_model.getBus(getI(ndx)).getBASKV():1f;}
	/** nominal winding 1 voltage in kV */
	public float getNOMV1(int ndx) throws PsseModelException {return _model.getBus(getI(ndx)).getBASKV();}
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
		return (getCW(ndx) == 2) ? 1.1f * _model.getBus(getI(ndx)).getBASKV() : 1.1f; 
	}
	/** RMI lower limit (see PSS/e documentation) */
	public float getRMI1(int ndx) throws PsseModelException
	{
		return (getCW(ndx) == 2) ? 0.9f * _model.getBus(getI(ndx)).getBASKV() : 0.9f; 
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
	public OwnershipList getOwnership(int ndx) throws PsseModelException
	{
		return OwnershipList.Empty;
	}
	
	/** Winding 2 off-nominal turns ratio */
	public float getWINDV2(int ndx) throws PsseModelException {return ((getCW(ndx)==2)?_model.getBus(getJ(ndx)).getBASKV():1f);}
	/** Winding 2 nominal voltage */
	public float getNOMV2(int ndx) throws PsseModelException {return _model.getBus(getJ(ndx)).getBASKV();}
	/**
	 * Allow a tap range to be specified on winding 2.
	 */
	public float getRMA2(int ndx) throws PsseModelException
	{
		return (getCW(ndx) == 2) ? 1.1f * _model.getBus(getJ(ndx)).getBASKV() : 1.1f; 
	}

	/**
	 * Allow a tap range to be specified on winding 2.
	 */
	public float getRMI2(int ndx) throws PsseModelException
	{
		return (getCW(ndx) == 2) ? 0.9f * _model.getBus(getJ(ndx)).getBASKV() : 0.9f; 
	}
	/**
	 * Allow a number of positions to be specified on winding 2.
	 */
	public int getNTP2(int ndx) throws PsseModelException {return 33;}

	public float getGmag(int ndx) throws PsseModelException {return XfrMagYTool.getYMag(get(ndx)).re();}
	public float getBmag(int ndx) throws PsseModelException {return XfrMagYTool.getYMag(get(ndx)).im();}
	
	public float[] getRATA1() { return null;}
	public float[] getRATB1() { return null;}
	public float[] getRATC1() { return null;}

}	
