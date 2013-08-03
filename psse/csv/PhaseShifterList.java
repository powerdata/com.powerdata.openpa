package com.powerdata.openpa.psse.csv;

import com.powerdata.openpa.psse.Bus;
import com.powerdata.openpa.psse.BusList;
import com.powerdata.openpa.psse.OwnershipList;
import com.powerdata.openpa.psse.PhaseShifter;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.tools.Complex;

public class PhaseShifterList extends com.powerdata.openpa.psse.PhaseShifterList
{
	BusList _buses;
	PhaseShifterRawList _base;
	
	public PhaseShifterList(BusList buses, PhaseShifterRawList pslist)
	{
		_buses = buses;
		_base = pslist;
	}

	@Override
	public PhaseShifter get(String id) { return _base.get(id); }
	@Override
	public Bus getFromBus(int ndx) throws PsseModelException { return _buses.get(getI(ndx)); }
	@Override
	public Bus getToBus(int ndx) throws PsseModelException { return _buses.get(getJ(ndx)); }
	@Override
	public Complex getZ(int ndx) throws PsseModelException { return _base.getZ(ndx); }
	@Override
	public Complex getY(int ndx) throws PsseModelException { return _base.getY(ndx); }
	@Override
	public Complex getFromYmag(int ndx) throws PsseModelException { return _base.getFromYmag(ndx); }
	@Override
	public Complex getToYmag(int ndx) throws PsseModelException { return _base.getToYmag(ndx); }
	@Override
	public float getFromTap(int ndx) throws PsseModelException { return _base.getFromTap(ndx); }
	@Override
	public float getToTap(int ndx) throws PsseModelException { return _base.getToTap(ndx); }
	@Override
	public float getPhaseShift(int ndx) throws PsseModelException { return _base.getPhaseShift(ndx); }
	@Override
	public boolean isInSvc(int ndx) throws PsseModelException { return _base.isInSvc(ndx); }
	@Override
	public String getI(int ndx) throws PsseModelException  {return _base.getI(ndx);}
	@Override
	public String getJ(int ndx) throws PsseModelException {return _base.getJ(ndx);}
	@Override
	public String getCKT(int ndx) throws PsseModelException { return _base.getCKT(ndx); }
	@Override
	public int getCW(int ndx) throws PsseModelException { return _base.getCW(ndx); }
	@Override
	public int getCZ(int ndx) throws PsseModelException { return _base.getCZ(ndx); }
	@Override
	public int getCM(int ndx) throws PsseModelException { return _base.getCM(ndx); }
	@Override
	public float getMAG1(int ndx) throws PsseModelException { return _base.getMAG1(ndx); }
	@Override
	public float getMAG2(int ndx) throws PsseModelException { return _base.getMAG2(ndx); }
	@Override
	public int getNMETR(int ndx) throws PsseModelException { return _base.getNMETR(ndx); }
	@Override
	public String getNAME(int ndx) throws PsseModelException { return _base.getNAME(ndx); }
	@Override
	public int getSTAT(int ndx) throws PsseModelException { return _base.getSTAT(ndx); }
	@Override
	public float getR1_2(int ndx) throws PsseModelException { return _base.getR1_2(ndx); }
	@Override
	public float getX1_2(int ndx) throws PsseModelException {return _base.getX1_2(ndx);}
	@Override
	public float getSBASE1_2(int ndx) throws PsseModelException { return _base.getSBASE1_2(ndx); }
	@Override
	public float getWINDV1(int ndx) throws PsseModelException { return _base.getWINDV1(ndx); }
	@Override
	public float getNOMV1(int ndx) throws PsseModelException { return _base.getNOMV1(ndx); }
	@Override
	public float getANG1(int ndx) throws PsseModelException { return _base.getANG1(ndx); }
	@Override
	public float getRATA1(int ndx) throws PsseModelException { return _base.getRATA1(ndx); }
	@Override
	public float getRATB1(int ndx) throws PsseModelException { return _base.getRATB1(ndx); }
	@Override
	public float getRATC1(int ndx) throws PsseModelException { return _base.getRATC1(ndx); }
	@Override
	public int getCOD1(int ndx) throws PsseModelException { return _base.getCOD1(ndx); }
	@Override
	public float getRMA1(int ndx) throws PsseModelException { return _base.getRMA1(ndx); }
	@Override
	public float getRMI1(int ndx) throws PsseModelException { return _base.getRMI1(ndx); }
	@Override
	public float getVMA1(int ndx) throws PsseModelException { return _base.getVMA1(ndx); }
	@Override
	public float getVMI1(int ndx) throws PsseModelException { return _base.getVMI1(ndx); }
	@Override
	public int getNTP1(int ndx) throws PsseModelException { return _base.getNTP1(ndx); }
	@Override
	public int getTAB1(int ndx) throws PsseModelException { return _base.getTAB1(ndx); }
	@Override
	public float getCR1(int ndx) throws PsseModelException { return _base.getCR1(ndx); }
	@Override
	public float getCX1(int ndx) throws PsseModelException { return _base.getCX1(ndx); }
	@Override
	public OwnershipList getOwnership(int ndx) throws PsseModelException { return _base.getOwnership(ndx); }
	@Override
	public void setRTFromS(int ndx, Complex s) throws PsseModelException { _base.setRTFromS(ndx, s); }
	@Override
	public void setRTToS(int ndx, Complex s) throws PsseModelException { _base.setRTToS(ndx, s); }
	@Override
	public Complex getRTFromS(int ndx) throws PsseModelException { return _base.getRTFromS(ndx); }
	@Override
	public Complex getRTToS(int ndx) throws PsseModelException { return _base.getRTToS(ndx); }
	@Override
	public String getObjectID(int ndx) throws PsseModelException {return _base.getObjectID(ndx);}
	@Override
	public String getObjectName(int ndx) throws PsseModelException { return _base.getObjectName(ndx); }

	@Override
	public int size() {return _base.size();}
}
