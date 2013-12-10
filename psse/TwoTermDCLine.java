package com.powerdata.openpa.psse;

public class TwoTermDCLine extends PsseBaseObject implements TwoTermDev
{
	public enum CtrlMode
	{
		Unknown, Blocked, Power, Current;
		private static final CtrlMode[] _Codes = new CtrlMode[]
			{Blocked, Power, Current};
		public static CtrlMode fromCode(int cod)
		{
			int code = Math.abs(cod);
			if (code >= _Codes.length)
				return Unknown;
			else
				return _Codes[code];
		}
		
	}
	protected TwoTermDCLineList _list;
	
	public TwoTermDCLine(TwoTermDCLineList list, int ndx)
	{
		super(list, ndx);
		_list = list;
	}

	/** dc line number, keep I to the AC bus for interface consistency */
	public int getDCLineNum() throws PsseModelException {return _list.getDCLineNum(_ndx);}
	
	@Override
	public String getI() throws PsseModelException {return _list.getI(_ndx);}
	@Override
	public String getJ() throws PsseModelException {return _list.getJ(_ndx);}
	@Override
	public Bus getFromBus() throws PsseModelException {return _list.getFromBus(_ndx);}
	@Override
	public Bus getToBus() throws PsseModelException {return _list.getToBus(_ndx);}
	
	/** get Control Mode */
	public int getMDC() throws PsseModelException {return _list.getMDC(_ndx);}
	/** set Control Mode */
	public void setMDC(int mdc) throws PsseModelException {_list.setMDC(_ndx, mdc);}
	/** get Control Mode */
	public CtrlMode getCtrlMode() throws PsseModelException {return _list.getCtrlMode(_ndx);}
	/** set control mode */
	public void setCtrlMode(CtrlMode cmode) throws PsseModelException {_list.setCtrlMode(_ndx, cmode);}
	/** get DC resistance */
	public float getRDC() throws PsseModelException {return _list.getRDC(_ndx);}
	/**
	 * get power or current demand (depending on control mode). If power control
	 * mode, a negative value specifies inverter power
	 */	
	public float getSETVL() throws PsseModelException {return _list.getSETVL(_ndx);}
	/**
	 * set power or current demand. If power control mode, a negative value
	 * specifies inverter power
	 */
	public void setSETVL(float svl) throws PsseModelException {_list.setSETVL(_ndx, svl);}
	/** get scheduled compounded DC voltage in kV */
	public float getVSCHD() throws PsseModelException {return _list.getVSCHD(_ndx);}
	/** set scheduled compounded DC voltage in kV */
	public void setVSCHD(float vdc) throws PsseModelException {_list.setVSCHD(_ndx, vdc);}
	/** get DC voltage for mode switch from power order to current order */
	public float getVCMOD() throws PsseModelException {return _list.getVCMOD(_ndx);}
	/** get compounding resistance */
	public float getRCOMP() throws PsseModelException {return _list.getRCOMP(_ndx);}
	/** Margin per unit of current or power order reduction in a mode switch */
	public float getDELTI() throws PsseModelException {return _list.getDELTI(_ndx);}
	/**
	 * get minimum compunded DC voltage (in kV) to be used in constant gamma and
	 * externally modeled transformer controls voltage through tap adjustments
	 */
	public float getDCVMIN() throws PsseModelException {return _list.getDCVMIN(_ndx);}
	/** Iteration limit for capacitor commutated solution */
	public int getCCCITMX() throws PsseModelException {return _list.getCCCITMX(_ndx);}
	/** Acceleration factor for capacitor commutated solution */
	public float getCCCACC() throws PsseModelException {return _list.getCCCACC(_ndx);}
	
	/* rectifier data methods */
	
	/** rectifier AC bus */
	public String getIPR() throws PsseModelException {return _list.getIPR(_ndx);}
	/** number of of rectifier bridges */
	public int getNBR() throws PsseModelException {return _list.getNBR(_ndx);}
	/** maximum rectifier angle */
	public float getALFMX() throws PsseModelException {return _list.getALFMX(_ndx);}
	/** minimum stead-state rectifier angle */
	public float getALFMN() throws PsseModelException {return _list.getALFMN(_ndx);}
	/** maximum rectifier angle (Rad)*/
	public float getALFMXrad() throws PsseModelException {return _list.getALFMXrad(_ndx);}
	/** minimum stead-state rectifier angle (rad) */
	public float getALFMNrad() throws PsseModelException {return _list.getALFMNrad(_ndx);}
	/** Rectifier commutating resistance per bridge in ohms */
	public float getRCR() throws PsseModelException {return _list.getRCR(_ndx);}
	/** Rectifier commutating reactance per bridge in ohms */
	public float getXCR() throws PsseModelException {return _list.getXCR(_ndx);}
	/** Rectifier transformer primary base voltage in KV */
	public float getEBASR() throws PsseModelException {return _list.getEBASR(_ndx);}
	/** rectifier transformer ratio */
	public float getTRR() throws PsseModelException {return _list.getTRR(_ndx);}
	/** rectifier transformer tap ratio */
	public float getTAPR() throws PsseModelException {return _list.getTAPR(_ndx);}
	/** rectifier transformer maximum tap ratio */
	public float getTMXR() throws PsseModelException {return _list.getTMXR(_ndx);}
	/** rectifier transformer minimum tap ratio */
	public float getTMNR() throws PsseModelException {return _list.getTMNR(_ndx);}
	/** rectifier transformer tap step size */
	public float getSTPR() throws PsseModelException {return _list.getSTPR(_ndx);}
	
	/** rectifier firing angle reference bus */
	public String getICR() throws PsseModelException {return _list.getICR(_ndx);}
	/** from-side rectifier transformer bus */
	public String getIFR() throws PsseModelException {return _list.getIFR(_ndx);}
	/** to-side rectifier transformer bus */
	public String getITR() throws PsseModelException {return _list.getITR(_ndx);}
	/** rectifier transformer */
	public Transformer getTransformerR() throws PsseModelException {return _list.getTransformerR(_ndx);}
	/** rectifier transformer circuit identifier */
	public String getIDR() throws PsseModelException {return _list.getIDR(_ndx);}
	/** rectifier commutating capacitor reactance in ohms */
	public float getXCAPR() throws PsseModelException {return _list.getXCAPR(_ndx);}
	
	/* inverter data methods */
	
	/** inverter AC bus */
	public String getIPI() throws PsseModelException {return _list.getIPI(_ndx);}
	/** number of of inverter bridges */
	public int getNBI() throws PsseModelException {return _list.getNBI(_ndx);}
	/** maximum inverter angle */
	public float getGAMMX() throws PsseModelException {return _list.getGAMMX(_ndx);}
	/** minimum stead-state inverter angle */
	public float getGAMMN() throws PsseModelException {return _list.getGAMMN(_ndx);}
	/** maximum inverter angle (Radians)*/
	public float getGAMMXrad() throws PsseModelException {return _list.getGAMMXrad(_ndx);}
	/** minimum stead-state inverter angle  (Radians)*/
	public float getGAMMNrad() throws PsseModelException {return _list.getGAMMNrad(_ndx);}
	/** inverter commutating resistance per bridge in ohms */
	public float getRCI() throws PsseModelException {return _list.getRCI(_ndx);}
	/** inverter commutating reactance per bridge in ohms */
	public float getXCI() throws PsseModelException {return _list.getXCI(_ndx);}
	/** Inverter transformer primary base voltage in KV */
	public float getEBASI() throws PsseModelException {return _list.getEBASI(_ndx);}
	/** inverter transformer ratio */
	public float getTRI() throws PsseModelException {return _list.getTRI(_ndx);}
	/** inverter transformer tap ratio */
	public float getTAPI() throws PsseModelException {return _list.getTAPI(_ndx);}
	/** inverter transformer maximum tap ratio */
	public float getTMXI() throws PsseModelException {return _list.getTMXI(_ndx);}
	/** inverter transformer minimum tap ratio */
	public float getTMNI() throws PsseModelException {return _list.getTMNI(_ndx);}
	/** inverter transformer tap step size */
	public float getSTPI() throws PsseModelException {return _list.getSTPI(_ndx);}
	/** inverter firing angle reference bus */
	public String getICI() throws PsseModelException {return _list.getICI(_ndx);}
	/** from-side inverter transformer bus */
	public String getIFI() throws PsseModelException {return _list.getIFI(_ndx);}
	/** to-side inverter transformer bus */
	public String getITI() throws PsseModelException {return _list.getITI(_ndx);}
	/** inverter transformer */
	public Transformer getTransformerI() throws PsseModelException {return _list.getTransformerI(_ndx);}
	/** inverter transformer circuit identifier */
	public String getIDI() throws PsseModelException {return _list.getIDI(_ndx);}
	/** inverter commutating capacitor reactance in ohms */
	public float getXCAPI() throws PsseModelException {return _list.getXCAPI(_ndx);}

	@Override
	public boolean isInSvc() throws PsseModelException {return _list.isInSvc(_ndx);}
}
