package com.powerdata.openpa.psse.util;

import com.powerdata.openpa.psse.Bus;
import com.powerdata.openpa.psse.OwnershipList;
import com.powerdata.openpa.psse.PhaseShifter;
import com.powerdata.openpa.psse.PhaseShifterList;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.tools.Complex;

public class PhaseShifterSubList extends PhaseShifterList
{
	PhaseShifterList _base;
	int[] _ndxs;
	boolean _indexed = false;
		
	public PhaseShifterSubList(PhaseShifterList base, int[] ndxs) throws PsseModelException
	{
		super(base.getPsseModel());
		_base = base;
		_ndxs = ndxs;
	}
	
	protected int map(int ndx) {return _ndxs[ndx];}

	@Override
	public PhaseShifter get(String id)
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

	@Override
	public Bus getFromBus(int ndx) throws PsseModelException {return _base.getFromBus(map(ndx));}
	@Override
	public Bus getToBus(int ndx) throws PsseModelException {return _base.getToBus(map(ndx));}
	@Override
	public float getR(int ndx) throws PsseModelException {return _base.getR(map(ndx));}
	@Override
	public float getX(int ndx) throws PsseModelException {return _base.getX(map(ndx));}
	@Override
	public Complex getZ(int ndx) throws PsseModelException {return _base.getZ(map(ndx));}
	@Override
	public Complex getY(int ndx) throws PsseModelException {return _base.getY(map(ndx));}
	@Override
	public float getFromTap(int ndx) throws PsseModelException {return _base.getFromTap(map(ndx));}
	@Override
	public float getToTap(int ndx) throws PsseModelException {return _base.getToTap(map(ndx));}
	@Override
	public float getPhaseShift(int ndx) throws PsseModelException {return _base.getPhaseShift(map(ndx));}
	@Override
	public boolean isInSvc(int ndx) throws PsseModelException {return _base.isInSvc(map(ndx));}
	@Override
	public float getGmag(int ndx) throws PsseModelException {return _base.getGmag(map(ndx));}
	@Override
	public float getBmag(int ndx) throws PsseModelException {return _base.getBmag(map(ndx));}
	@Override
	public String getI(int ndx) throws PsseModelException {return _base.getI(map(ndx));}
	@Override
	public String getJ(int ndx) throws PsseModelException {return _base.getJ(map(ndx));}
	@Override
	public String getCKT(int ndx) throws PsseModelException {return _base.getCKT(map(ndx));}
	@Override
	public int getCW(int ndx) throws PsseModelException {return _base.getCW(map(ndx));}
	@Override
	public int getCZ(int ndx) throws PsseModelException {return _base.getCZ(map(ndx));}
	@Override
	public int getCM(int ndx) throws PsseModelException {return _base.getCM(map(ndx));}
	@Override
	public float getMAG1(int ndx) throws PsseModelException {return _base.getMAG1(map(ndx));}
	@Override
	public float getMAG2(int ndx) throws PsseModelException {return _base.getMAG2(map(ndx));}
	@Override
	public int getNMETR(int ndx) throws PsseModelException {return _base.getNMETR(map(ndx));}
	@Override
	public String getNAME(int ndx) throws PsseModelException {return _base.getNAME(map(ndx));}
	@Override
	public int getSTAT(int ndx) throws PsseModelException {return _base.getSTAT(map(ndx));}
	@Override
	public float getR1_2(int ndx) throws PsseModelException {return _base.getR1_2(map(ndx));}
	@Override
	public float getX1_2(int ndx) throws PsseModelException {return _base.getX1_2(map(ndx));}
	@Override
	public float getSBASE1_2(int ndx) throws PsseModelException {return _base.getSBASE1_2(map(ndx));}
	@Override
	public float getWINDV1(int ndx) throws PsseModelException {return _base.getWINDV1(map(ndx));}
	@Override
	public float getNOMV1(int ndx) throws PsseModelException {return _base.getNOMV1(map(ndx));}
	@Override
	public float getANG1(int ndx) throws PsseModelException {return _base.getANG1(map(ndx));}
	@Override
	public float getRATA1(int ndx) throws PsseModelException {return _base.getRATA1(map(ndx));}
	@Override
	public float getRATB1(int ndx) throws PsseModelException {return _base.getRATB1(map(ndx));}
	@Override
	public float getRATC1(int ndx) throws PsseModelException {return _base.getRATC1(map(ndx));}
	@Override
	public int getCOD1(int ndx) throws PsseModelException {return _base.getCOD1(map(ndx));}
	@Override
	public float getRMA1(int ndx) throws PsseModelException {return _base.getRMA1(map(ndx));}
	@Override
	public float getRMI1(int ndx) throws PsseModelException {return _base.getRMI1(map(ndx));}
	@Override
	public float getVMA1(int ndx) throws PsseModelException {return _base.getVMA1(map(ndx));}
	@Override
	public float getVMI1(int ndx) throws PsseModelException {return _base.getVMI1(map(ndx));}
	@Override
	public int getNTP1(int ndx) throws PsseModelException {return _base.getNTP1(map(ndx));}
	@Override
	public int getTAB1(int ndx) throws PsseModelException {return _base.getTAB1(map(ndx));}
	@Override
	public float getCR1(int ndx) throws PsseModelException {return _base.getCR1(map(ndx));}
	@Override
	public float getCX1(int ndx) throws PsseModelException {return _base.getCX1(map(ndx));}
	@Override
	public OwnershipList getOwnership(int ndx) throws PsseModelException
	{
		return _base.getOwnership(map(ndx));
	}
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
	@Override
	public void setInSvc(int ndx, boolean status) throws PsseModelException {_base.setInSvc(map(ndx), status);}
	@Override
	public long getKey(int ndx) throws PsseModelException {return _base.getKey(ndx);}
}
