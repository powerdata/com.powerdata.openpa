package com.powerdata.openpa.psse;

import com.powerdata.openpa.psse.TwoTermDCLine.CtrlMode;

public abstract class TwoTermDCLineList extends PsseBaseList<TwoTermDCLine>
{
//	public static final TwoTermDCLineList Empty = new TwoTermDCLineList() {};
	@Override
	public String getObjectID(int ndx) throws PsseModelException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TwoTermDCLine get(int index)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	public String getI(int ndx) throws PsseModelException {return getIPR(ndx);}
	public String getJ(int ndx) throws PsseModelException {return getIPI(ndx);}
	public Bus getFromBus(int ndx) throws PsseModelException {return _model.getBus(getIPR(ndx));}
	public Bus getToBus(int ndx) throws PsseModelException {return _model.getBus(getIPI(ndx));}
	public int getMDC(int ndx) throws PsseModelException {return 0;}
	public void setMDC(int ndx, int mdc) throws PsseModelException {}
	public CtrlMode getCtrlMode(int ndx) throws PsseModelException {return CtrlMode.fromCode(getMDC(ndx));}
	public void setCtrlMode(int ndx, CtrlMode cmode) throws PsseModelException {}
	public abstract float getRDC(int ndx) throws PsseModelException;
	public abstract float getSETVL(int ndx) throws PsseModelException;
	public void setSETVL(int ndx, float svl) throws PsseModelException {}
	public abstract float getVSCHD(int ndx) throws PsseModelException;
	public void setVSCHD(int ndx, float vdc) throws PsseModelException {}
	public float getVCMOD(int ndx) throws PsseModelException {return 0f;}
	public float getRCOMP(int ndx) throws PsseModelException {return 0f;}
	public float getDELTI(int ndx) throws PsseModelException {return 0f;}
	public float getDCVMIN(int ndx) throws PsseModelException {return 0f;}

	public abstract String getIPR(int ndx) throws PsseModelException;
	public abstract int getNBR(int ndx) throws PsseModelException;;
	public abstract float getALFMX(int ndx) throws PsseModelException;
	public abstract float getALFMN(int ndx) throws PsseModelException;
	public float getRCR(int ndx) throws PsseModelException {return 0f;}
	public abstract float getXCR(int ndx) throws PsseModelException;
	public String getICR(int ndx) throws PsseModelException {return "0";}
	public String getIFR(int ndx) throws PsseModelException {return "0";} 
	public String getITR(int ndx) throws PsseModelException {return "0";}
	public String getIDR(int ndx) throws PsseModelException {return "1";}
	public Transformer getTransformerR(int ndx) throws PsseModelException
	{
		// TODO Implement more reasonable default
		return null;
	}

	public float getXCAPR(int ndx) throws PsseModelException {return 0f;}
	public abstract String getIPI(int ndx) throws PsseModelException;
	public abstract int getNBI(int ndx) throws PsseModelException;
	public abstract float getGAMMX(int ndx) throws PsseModelException;
	public abstract float getGAMMN(int ndx) throws PsseModelException;
	public float getRCI(int ndx) throws PsseModelException {return 0f;}
	public abstract float getXCI(int ndx) throws PsseModelException;
	public String getICI(int ndx) throws PsseModelException {return "0";}
	public String getIFI(int ndx) throws PsseModelException {return "0";}
	public String getITI(int ndx) throws PsseModelException {return "0";}
	public Transformer getTransformerI(int ndx)
	{
		// TODO Implement more reasonable default
		return null;
	}

	public String getIDI(int ndx) throws PsseModelException {return "1";}
	public float getXCAPI(int ndx) throws PsseModelException {return 0f;}
	public abstract int getDCLineNum(int ndx) throws PsseModelException;

	public int getCCCITMX(int ndx) throws PsseModelException {return 20;}
	public float getCCCACC(int ndx) throws PsseModelException {return 1f;}

}
