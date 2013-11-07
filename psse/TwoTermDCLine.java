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
	/**
	 * get minimum compunded DC voltage (in kV) to be used in constant gamma and
	 * externally modeled transformer controls voltage through tap adjustments
	 */
	public float getDCVMIN() throws PsseModelException {return _list.getDCVMIN(_ndx);}
	
	/* rectifier data methods */
	
	/** 
}
