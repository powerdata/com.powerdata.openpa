package com.powerdata.openpa.psse;

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
	public PhaseShifter get(String id) { return super.get(id); }
	@Override
	public Bus getFromBus(int ndx) throws PsseModelException { return super.getFromBus(_ndxs[ndx]); }
	@Override
	public Bus getToBus(int ndx) throws PsseModelException { return super.getToBus(_ndxs[ndx]); }
	@Override
	public Complex getZ(int ndx) throws PsseModelException { return super.getZ(_ndxs[ndx]); }
	@Override
	public Complex getY(int ndx) throws PsseModelException { return super.getY(_ndxs[ndx]); }
	@Override
	public Complex getFromYmag(int ndx) throws PsseModelException { return super.getFromYmag(_ndxs[ndx]); }
	@Override
	public Complex getToYmag(int ndx) throws PsseModelException { return super.getToYmag(_ndxs[ndx]); }
	@Override
	public float getFromTap(int ndx) throws PsseModelException { return super.getFromTap(_ndxs[ndx]); }
	@Override
	public float getToTap(int ndx) throws PsseModelException { return super.getToTap(_ndxs[ndx]); }
	@Override
	public float getPhaseShift(int ndx) throws PsseModelException { return super.getPhaseShift(_ndxs[ndx]); }
	@Override
	public boolean isInSvc(int ndx) throws PsseModelException { return super.isInSvc(_ndxs[ndx]); }
	@Override
	public String getI(int ndx) throws PsseModelException { return _base.getI(_ndxs[ndx]);}
	@Override
	public String getJ(int ndx) throws PsseModelException {return _base.getJ(_ndxs[ndx]);}
	@Override
	public String getCKT(int ndx) throws PsseModelException { return super.getCKT(_ndxs[ndx]); }
	@Override
	public int getCW(int ndx) throws PsseModelException { return super.getCW(_ndxs[ndx]); }
	@Override
	public int getCZ(int ndx) throws PsseModelException { return super.getCZ(_ndxs[ndx]); }
	@Override
	public int getCM(int ndx) throws PsseModelException { return super.getCM(_ndxs[ndx]); }
	@Override
	public float getMAG1(int ndx) throws PsseModelException { return super.getMAG1(_ndxs[ndx]); }
	@Override
	public float getMAG2(int ndx) throws PsseModelException { return super.getMAG2(_ndxs[ndx]); }
	@Override
	public int getNMETR(int ndx) throws PsseModelException { return super.getNMETR(_ndxs[ndx]); }
	@Override
	public String getNAME(int ndx) throws PsseModelException { return super.getNAME(_ndxs[ndx]); }
	@Override
	public int getSTAT(int ndx) throws PsseModelException { return super.getSTAT(_ndxs[ndx]); }
	@Override
	public float getR1_2(int ndx) throws PsseModelException { return super.getR1_2(_ndxs[ndx]); }
	@Override
	public float getX1_2(int ndx) throws PsseModelException { return _base.getX1_2(_ndxs[ndx]);}
	@Override
	public float getSBASE1_2(int ndx) throws PsseModelException { return super.getSBASE1_2(_ndxs[ndx]); }
	@Override
	public float getWINDV1(int ndx) throws PsseModelException { return super.getWINDV1(_ndxs[ndx]); }
	@Override
	public float getNOMV1(int ndx) throws PsseModelException { return super.getNOMV1(_ndxs[ndx]); }
	@Override
	public float getANG1(int ndx) throws PsseModelException { return super.getANG1(_ndxs[ndx]); }
	@Override
	public float getRATA1(int ndx) throws PsseModelException { return super.getRATA1(_ndxs[ndx]); }
	@Override
	public float getRATB1(int ndx) throws PsseModelException { return super.getRATB1(_ndxs[ndx]); }
	@Override
	public float getRATC1(int ndx) throws PsseModelException { return super.getRATC1(_ndxs[ndx]); }
	@Override
	public int getCOD1(int ndx) throws PsseModelException { return super.getCOD1(_ndxs[ndx]); }
	@Override
	public float getRMA1(int ndx) throws PsseModelException { return super.getRMA1(_ndxs[ndx]); }
	@Override
	public float getRMI1(int ndx) throws PsseModelException { return super.getRMI1(_ndxs[ndx]); }
	@Override
	public float getVMA1(int ndx) throws PsseModelException { return super.getVMA1(_ndxs[ndx]); }
	@Override
	public float getVMI1(int ndx) throws PsseModelException { return super.getVMI1(_ndxs[ndx]); }
	@Override
	public int getNTP1(int ndx) throws PsseModelException { return super.getNTP1(_ndxs[ndx]); }
	@Override
	public int getTAB1(int ndx) throws PsseModelException { return super.getTAB1(_ndxs[ndx]); }
	@Override
	public float getCR1(int ndx) throws PsseModelException { return super.getCR1(_ndxs[ndx]); }
	@Override
	public float getCX1(int ndx) throws PsseModelException { return super.getCX1(_ndxs[ndx]); }
	@Override
	public OwnershipList getOwnership(int ndx) throws PsseModelException { return super.getOwnership(_ndxs[ndx]); }
	@Override
	public String getObjectID(int ndx) throws PsseModelException {return _base.getObjectID(_ndxs[ndx]);}
	@Override
	public String getObjectName(int ndx) throws PsseModelException { return super.getObjectName(_ndxs[ndx]); }
	@Override
	public int size() {return _ndxs.length;}
}
