package com.powerdata.openpa.psse;

import com.powerdata.openpa.psse.conversions.XfrMagYTool;
import com.powerdata.openpa.psse.conversions.XfrWndTool;
import com.powerdata.openpa.psse.conversions.XfrZToolFactory;
import com.powerdata.openpa.tools.Complex;
import com.powerdata.openpa.tools.PAMath;

public abstract class PhaseShifterList extends PsseBaseList<PhaseShifter>
{
	protected XfrZToolFactory _ztool;
	
	public static final PhaseShifterList Empty = new PhaseShifterList()
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
	
	protected PhaseShifterList() {super();}
	public PhaseShifterList(PsseModel model) throws PsseModelException 
	{
		super(model);
		_ztool = XfrZToolFactory.Open(_model.getPsseVersion());
	}

	
	/** Get a Transformer by it's index. */
	@Override
	public PhaseShifter get(int ndx) { return new PhaseShifter(ndx,this); }
	/** Get a Transformer by it's ID. */
	@Override
	public PhaseShifter get(String id) { return super.get(id); }
	
	/* Convenience methods */
	
	public Bus getFromBus(int ndx) throws PsseModelException {return _model.getBus(getI(ndx));}
	public Bus getToBus(int ndx) throws PsseModelException {return _model.getBus(getJ(ndx));}
	public Complex getZ(int ndx) throws PsseModelException
	{
		return _ztool.get(getCZ(ndx)).convert2W(get(ndx));
	}
	public Complex getY(int ndx) throws PsseModelException {return getZ(ndx).inv();}

	public Complex getFromYmag(int ndx) throws PsseModelException {return XfrMagYTool.getYMag(get(ndx));}
	public Complex getToYmag(int ndx) throws PsseModelException {return XfrMagYTool.getYMag(get(ndx));}
	public float getFromTap(int ndx) throws PsseModelException {return XfrWndTool.get(getCW(ndx)).getRatio1(get(ndx));}
	public float getToTap(int ndx) throws PsseModelException {return 1f;}
	public float getPhaseShift(int ndx) throws PsseModelException {return PAMath.deg2rad(getANG1(ndx));}
	public boolean isInSvc(int ndx) throws PsseModelException {return getSTAT(ndx) == 1;}

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
	public float getNOMV1(int ndx) throws PsseModelException {return getFromBus(ndx).getBASKV();}
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
	public OwnershipList getOwnership(int ndx) throws PsseModelException
	{
		return OwnershipList.Empty;
	}

	/* realtime fields */

	public void setRTFromS(int ndx, Complex s) throws PsseModelException {/* do nothing */}
	public void setRTToS(int ndx, Complex s) throws PsseModelException {/* do nothing */}
	public Complex getRTFromS(int ndx) throws PsseModelException { return Complex.Zero;}
	public Complex getRTToS(int ndx) throws PsseModelException {return Complex.Zero;}
}	

