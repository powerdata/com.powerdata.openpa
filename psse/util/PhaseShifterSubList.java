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
	
	public PhaseShifterSubList(PhaseShifterList base, int[] ndxs) throws PsseModelException
	{
		super(base.getPsseModel());
		_base = base;
		_ndxs = ndxs;
		_idToNdx = base.idmap();
	}
	@Override
	public PhaseShifter get(String id) {return new PhaseShifter(_ndxs[_idToNdx.get(id)], this);}
	@Override
	public Bus getFromBus(int ndx) throws PsseModelException { return _base.getFromBus(_ndxs[ndx]); }
	@Override
	public Bus getToBus(int ndx) throws PsseModelException { return _base.getToBus(_ndxs[ndx]); }
	@Override
	public Complex getZ(int ndx) throws PsseModelException { return _base.getZ(_ndxs[ndx]); }
	@Override
	public Complex getY(int ndx) throws PsseModelException { return _base.getY(_ndxs[ndx]); }
	@Override
	public float getFromTap(int ndx) throws PsseModelException { return _base.getFromTap(_ndxs[ndx]); }
	@Override
	public float getToTap(int ndx) throws PsseModelException { return _base.getToTap(_ndxs[ndx]); }
	@Override
	public float getPhaseShift(int ndx) throws PsseModelException { return _base.getPhaseShift(_ndxs[ndx]); }
	@Override
	public boolean isInSvc(int ndx) throws PsseModelException { return _base.isInSvc(_ndxs[ndx]); }
	@Override
	public String getI(int ndx) throws PsseModelException { return _base.getI(_ndxs[ndx]);}
	@Override
	public String getJ(int ndx) throws PsseModelException {return _base.getJ(_ndxs[ndx]);}
	@Override
	public String getCKT(int ndx) throws PsseModelException { return _base.getCKT(_ndxs[ndx]); }
	@Override
	public int getCW(int ndx) throws PsseModelException { return _base.getCW(_ndxs[ndx]); }
	@Override
	public int getCZ(int ndx) throws PsseModelException { return _base.getCZ(_ndxs[ndx]); }
	@Override
	public int getCM(int ndx) throws PsseModelException { return _base.getCM(_ndxs[ndx]); }
	@Override
	public float getMAG1(int ndx) throws PsseModelException { return _base.getMAG1(_ndxs[ndx]); }
	@Override
	public float getMAG2(int ndx) throws PsseModelException { return _base.getMAG2(_ndxs[ndx]); }
	@Override
	public int getNMETR(int ndx) throws PsseModelException { return _base.getNMETR(_ndxs[ndx]); }
	@Override
	public String getNAME(int ndx) throws PsseModelException { return _base.getNAME(_ndxs[ndx]); }
	@Override
	public int getSTAT(int ndx) throws PsseModelException { return _base.getSTAT(_ndxs[ndx]); }
	@Override
	public float getR1_2(int ndx) throws PsseModelException { return _base.getR1_2(_ndxs[ndx]); }
	@Override
	public float getX1_2(int ndx) throws PsseModelException { return _base.getX1_2(_ndxs[ndx]);}
	@Override
	public float getSBASE1_2(int ndx) throws PsseModelException { return _base.getSBASE1_2(_ndxs[ndx]); }
	@Override
	public float getWINDV1(int ndx) throws PsseModelException { return _base.getWINDV1(_ndxs[ndx]); }
	@Override
	public float getNOMV1(int ndx) throws PsseModelException { return _base.getNOMV1(_ndxs[ndx]); }
	@Override
	public float getANG1(int ndx) throws PsseModelException { return _base.getANG1(_ndxs[ndx]); }
	@Override
	public float getRATA1(int ndx) throws PsseModelException { return _base.getRATA1(_ndxs[ndx]); }
	@Override
	public float getRATB1(int ndx) throws PsseModelException { return _base.getRATB1(_ndxs[ndx]); }
	@Override
	public float getRATC1(int ndx) throws PsseModelException { return _base.getRATC1(_ndxs[ndx]); }
	@Override
	public int getCOD1(int ndx) throws PsseModelException { return _base.getCOD1(_ndxs[ndx]); }
	@Override
	public float getRMA1(int ndx) throws PsseModelException { return _base.getRMA1(_ndxs[ndx]); }
	@Override
	public float getRMI1(int ndx) throws PsseModelException { return _base.getRMI1(_ndxs[ndx]); }
	@Override
	public float getVMA1(int ndx) throws PsseModelException { return _base.getVMA1(_ndxs[ndx]); }
	@Override
	public float getVMI1(int ndx) throws PsseModelException { return _base.getVMI1(_ndxs[ndx]); }
	@Override
	public int getNTP1(int ndx) throws PsseModelException { return _base.getNTP1(_ndxs[ndx]); }
	@Override
	public int getTAB1(int ndx) throws PsseModelException { return _base.getTAB1(_ndxs[ndx]); }
	@Override
	public float getCR1(int ndx) throws PsseModelException { return _base.getCR1(_ndxs[ndx]); }
	@Override
	public float getCX1(int ndx) throws PsseModelException { return _base.getCX1(_ndxs[ndx]); }
	@Override
	public OwnershipList getOwnership(int ndx) throws PsseModelException { return _base.getOwnership(_ndxs[ndx]); }
	@Override
	public String getObjectID(int ndx) throws PsseModelException {return _base.getObjectID(_ndxs[ndx]);}
	@Override
	public String getObjectName(int ndx) throws PsseModelException { return _base.getObjectName(_ndxs[ndx]); }
	@Override
	public int size() {return _ndxs.length;}
	@Override
	public float getR(int ndx) throws PsseModelException {return _base.getR(_ndxs[ndx]);}
	@Override
	public float getX(int ndx) throws PsseModelException {return _base.getX(_ndxs[ndx]);}
	@Override
	public float getGmag(int ndx) throws PsseModelException {return _base.getGmag(_ndxs[ndx]);}
	@Override
	public float getBmag(int ndx) throws PsseModelException {return _base.getBmag(_ndxs[ndx]);}
}
