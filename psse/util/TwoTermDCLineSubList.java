package com.powerdata.openpa.psse.util;

import com.powerdata.openpa.psse.Bus;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.psse.Transformer;
import com.powerdata.openpa.psse.TwoTermDCLine;
import com.powerdata.openpa.psse.TwoTermDCLine.CtrlMode;
import com.powerdata.openpa.psse.TwoTermDCLineList;

public class TwoTermDCLineSubList extends TwoTermDCLineList
{
	TwoTermDCLineList _base;
	int[] _ndxs;
	boolean _indexed = false;
	
	public TwoTermDCLineSubList() {super();}
	public TwoTermDCLineSubList(TwoTermDCLineList lines, int[] ndxs) throws PsseModelException
	{
		super(lines.getPsseModel());
		_base = lines;
		_ndxs = ndxs;
	}

	@Override
	public TwoTermDCLine get(String id)
	{
		if (!_indexed)
		{
			_indexed = true;
			try
			{
				reindex();
			} catch (PsseModelException e)
			{
				e.printStackTrace();
			}
		}
		return super.get(id);
	}

	protected int map(int ndx) {return _ndxs[ndx];}
	@Override
	public String getI(int ndx) throws PsseModelException {return _base.getI(map(ndx));}
	@Override
	public String getJ(int ndx) throws PsseModelException {return _base.getJ(map(ndx));}
	@Override
	public Bus getFromBus(int ndx) throws PsseModelException {return _base.getFromBus(map(ndx));}
	@Override
	public Bus getToBus(int ndx) throws PsseModelException {return _base.getToBus(map(ndx));}
	@Override
	public int getMDC(int ndx) throws PsseModelException {return _base.getMDC(map(ndx));}
	@Override
	public void setMDC(int ndx, int mdc) throws PsseModelException {_base.setMDC(map(ndx), mdc);}
	@Override
	public CtrlMode getCtrlMode(int ndx) throws PsseModelException
	{
		return _base.getCtrlMode(map(ndx));
	}
	@Override
	public void setCtrlMode(int ndx, CtrlMode cmode) throws PsseModelException
	{
		_base.setCtrlMode(map(ndx), cmode);
	}
	@Override
	public float getRDC(int ndx) throws PsseModelException {return _base.getRDC(map(ndx));}
	@Override
	public float getSETVL(int ndx) throws PsseModelException {return _base.getSETVL(map(ndx));}
	@Override
	public void setSETVL(int ndx, float svl) throws PsseModelException {_base.setSETVL(map(ndx), svl);}
	@Override
	public float getVSCHD(int ndx) throws PsseModelException {return _base.getVSCHD(map(ndx));}
	@Override
	public void setVSCHD(int ndx, float vdc) throws PsseModelException {_base.setVSCHD(map(ndx), vdc);}
	@Override
	public float getVCMOD(int ndx) throws PsseModelException {return _base.getVCMOD(map(ndx));}
	@Override
	public float getRCOMP(int ndx) throws PsseModelException {return _base.getRCOMP(map(ndx));}
	@Override
	public float getDELTI(int ndx) throws PsseModelException {return _base.getDELTI(map(ndx));}
	@Override
	public float getDCVMIN(int ndx) throws PsseModelException {return _base.getDCVMIN(map(ndx));}
	@Override
	public String getIPR(int ndx) throws PsseModelException {return _base.getIPR(map(ndx));}
	@Override
	public int getNBR(int ndx) throws PsseModelException {return _base.getNBR(map(ndx));}
	@Override
	public float getALFMX(int ndx) throws PsseModelException {return _base.getALFMX(map(ndx));}
	@Override
	public float getALFMN(int ndx) throws PsseModelException {return _base.getALFMN(map(ndx));}
	@Override
	public float getRCR(int ndx) throws PsseModelException {return _base.getRCR(map(ndx));}
	@Override
	public float getXCR(int ndx) throws PsseModelException {return _base.getXCR(map(ndx));}
	@Override
	public String getICR(int ndx) throws PsseModelException {return _base.getICR(map(ndx));}
	@Override
	public String getIFR(int ndx) throws PsseModelException {return _base.getIFR(map(ndx));}
	@Override
	public String getITR(int ndx) throws PsseModelException {return _base.getITR(map(ndx));}
	@Override
	public String getIDR(int ndx) throws PsseModelException {return _base.getIDR(map(ndx));}
	@Override
	public Transformer getTransformerR(int ndx) throws PsseModelException
	{
		return _base.getTransformerR(map(ndx));
	}
	@Override
	public float getXCAPR(int ndx) throws PsseModelException {return _base.getXCAPR(map(ndx));}
	@Override
	public String getIPI(int ndx) throws PsseModelException {return _base.getIPI(map(ndx));}
	@Override
	public int getNBI(int ndx) throws PsseModelException {return _base.getNBI(map(ndx));}
	@Override
	public float getGAMMX(int ndx) throws PsseModelException {return _base.getGAMMX(map(ndx));}
	@Override
	public float getGAMMN(int ndx) throws PsseModelException {return _base.getGAMMN(map(ndx));}
	@Override
	public float getRCI(int ndx) throws PsseModelException {return _base.getRCI(map(ndx));}
	@Override
	public float getXCI(int ndx) throws PsseModelException {return _base.getXCI(map(ndx));}
	@Override
	public String getICI(int ndx) throws PsseModelException {return _base.getICI(map(ndx));}
	@Override
	public String getIFI(int ndx) throws PsseModelException {return _base.getIFI(map(ndx));}
	@Override
	public String getITI(int ndx) throws PsseModelException {return _base.getITI(map(ndx));}
	@Override
	public Transformer getTransformerI(int ndx)
	{
		return _base.getTransformerI(map(ndx));
	}
	@Override
	public String getIDI(int ndx) throws PsseModelException {return _base.getIDI(map(ndx));}
	@Override
	public float getXCAPI(int ndx) throws PsseModelException {return _base.getXCAPI(map(ndx));}
	@Override
	public int getDCLineNum(int ndx) throws PsseModelException {return _base.getDCLineNum(map(ndx));}
	@Override
	public int getCCCITMX(int ndx) throws PsseModelException {return _base.getCCCITMX(map(ndx));}
	@Override
	public float getCCCACC(int ndx) throws PsseModelException {return _base.getCCCACC(map(ndx));}
	@Override
	public float getTRR(int ndx) throws PsseModelException {return _base.getTRR(map(ndx));}
	@Override
	public float getTAPR(int ndx) throws PsseModelException {return _base.getTAPR(map(ndx));}
	@Override
	public float getTMXR(int ndx) throws PsseModelException {return _base.getTMXR(map(ndx));}
	@Override
	public float getTMNR(int ndx) throws PsseModelException {return _base.getTMNR(map(ndx));}
	@Override
	public float getSTPR(int ndx) throws PsseModelException {return _base.getSTPR(map(ndx));}
	@Override
	public float getTRI(int ndx) throws PsseModelException {return _base.getTRI(map(ndx));}
	@Override
	public float getTAPI(int ndx) throws PsseModelException {return _base.getTAPI(map(ndx));}
	@Override
	public float getTMXI(int ndx) throws PsseModelException {return _base.getTMXI(map(ndx));}
	@Override
	public float getTMNI(int ndx) throws PsseModelException {return _base.getTMNI(map(ndx));}
	@Override
	public float getSTPI(int ndx) throws PsseModelException {return _base.getSTPI(map(ndx));}
	@Override
	public float getEBASR(int ndx) throws PsseModelException {return _base.getEBASR(map(ndx));}
	@Override
	public float getEBASI(int ndx) throws PsseModelException {return _base.getEBASI(map(ndx));}
	@Override
	public float getALFMXrad(int ndx) throws PsseModelException {return _base.getALFMXrad(map(ndx));}
	@Override
	public float getALFMNrad(int ndx) throws PsseModelException {return _base.getALFMNrad(map(ndx));}
	@Override
	public float getGAMMXrad(int ndx) throws PsseModelException {return _base.getGAMMXrad(map(ndx));}
	@Override
	public float getGAMMNrad(int ndx) throws PsseModelException {return _base.getGAMMNrad(map(ndx));}
	@Override
	public boolean isInSvc(int ndx) throws PsseModelException {return _base.isInSvc(map(ndx));}
	@Override
	public void commit() throws PsseModelException {_base.commit();}
	@Override
	public String getObjectID(int ndx) throws PsseModelException {return _base.getObjectID(map(ndx));}
	@Override
	public String getObjectName(int ndx) throws PsseModelException {return _base.getObjectName(map(ndx));}
	@Override
	public String getFullName(int ndx) throws PsseModelException {return _base.getFullName(map(ndx));}
	@Override
	public String getDebugName(int ndx) throws PsseModelException {return _base.getDebugName(map(ndx));}
	@Override
	public int getRootIndex(int ndx) {return _base.getRootIndex(map(ndx));}
	@Override
	public int size() {return _ndxs.length;}
}
