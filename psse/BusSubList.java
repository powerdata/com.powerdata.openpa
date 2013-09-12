package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.Complex;
import com.powerdata.openpa.tools.PComplex;

public class BusSubList extends BusList
{
	BusList _base;
	int[] _ndxs;
	
	public BusSubList(BusList base, int[] ndxs)
	{
		super(base.getPsseModel());
		_base = base;
		_ndxs = ndxs;
		_idToNdx = base.idmap();
	}
	
	@Override
	public Bus get(String id) {return new Bus(_ndxs[_idToNdx.get(id)], this);}
	@Override
	public GenList getGenerators(int ndx) throws PsseModelException {return _base.getGenerators(_ndxs[ndx]);}
	@Override
	public LoadList getLoads(int ndx) throws PsseModelException {return _base.getLoads(_ndxs[ndx]);}
	@Override
	public LineList getLines(int ndx) throws PsseModelException {return _base.getLines(_ndxs[ndx]);}
	@Override
	public TransformerList getTransformers(int ndx) throws PsseModelException {return _base.getTransformers(_ndxs[ndx]);}
	@Override
	public ShuntList getShunts(int ndx) throws PsseModelException {return _base.getShunts(_ndxs[ndx]);}
	@Override
	public SvcList getSvcs(int ndx) throws PsseModelException {return _base.getSvcs(_ndxs[ndx]);}
	@Override
	public PhaseShifterList getPhaseShifters(int ndx) throws PsseModelException {return _base.getPhaseShifters(_ndxs[ndx]);}
	@Override
	public SwitchList getSwitches(int ndx) throws PsseModelException {return _base.getSwitches(_ndxs[ndx]);}
	@Override
	public SwitchList isolate(int ndx) throws PsseModelException {return _base.isolate(_ndxs[ndx]);}
	@Override
	public boolean isEnergized(int ndx) throws PsseModelException {return _base.isEnergized(_ndxs[ndx]);}
	@Override
	public int getIsland(int ndx) throws PsseModelException {return _base.getIsland(_ndxs[ndx]);}
	@Override
	public BusTypeCode getBusType(int ndx) throws PsseModelException {return _base.getBusType(_ndxs[ndx]);}
	@Override
	public Area getAreaObject(int ndx) throws PsseModelException {return _base.getAreaObject(_ndxs[ndx]);}
	@Override
	public Zone getZoneObject(int ndx) throws PsseModelException {return _base.getZoneObject(_ndxs[ndx]);}
	@Override
	public Owner getOwnerObject(int ndx) throws PsseModelException {return _base.getOwnerObject(_ndxs[ndx]);}
	@Override
	public Complex getShuntY(int ndx) throws PsseModelException {return _base.getShuntY(_ndxs[ndx]);}
	@Override
	public String getObjectName(int ndx) throws PsseModelException {return _base.getObjectName(_ndxs[ndx]);}
	@Override
	public int getI(int ndx) throws PsseModelException {return _base.getI(_ndxs[ndx]);}
	@Override
	public String getNAME(int ndx) throws PsseModelException {return _base.getNAME(_ndxs[ndx]);}
	@Override
	public float getBASKV(int ndx) throws PsseModelException {return _base.getBASKV(_ndxs[ndx]);}
	@Override
	public int getIDE(int ndx) throws PsseModelException {return _base.getIDE(_ndxs[ndx]);}
	@Override
	public float getGL(int ndx) throws PsseModelException {return _base.getGL(_ndxs[ndx]);}
	@Override
	public float getBL(int ndx) throws PsseModelException {return _base.getBL(_ndxs[ndx]);}
	@Override
	public int getAREA(int ndx) throws PsseModelException {return _base.getAREA(_ndxs[ndx]);}
	@Override
	public int getZONE(int ndx) throws PsseModelException {return _base.getZONE(_ndxs[ndx]);}
	@Override
	public float getVM(int ndx) throws PsseModelException {return _base.getVM(_ndxs[ndx]);}
	@Override
	public float getVA(int ndx) throws PsseModelException {return _base.getVA(_ndxs[ndx]);}
	@Override
	public int getOWNER(int ndx) throws PsseModelException {return _base.getOWNER(_ndxs[ndx]);}
	@Override
	public void setRTMismatch(int ndx, Complex mismatch) throws PsseModelException {_base.setRTMismatch(ndx, mismatch);}
	@Override
	public Complex getRTMismatch(int ndx) throws PsseModelException {return _base.getRTMismatch(_ndxs[ndx]);}
	@Override
	public String getObjectID(int ndx) throws PsseModelException {return _base.getObjectID(_ndxs[ndx]);}
	@Override
	public int size() {return _ndxs.length;}
	@Override
	public float getRTVMag(int ndx) throws PsseModelException {return _base.getRTVMag(_ndxs[ndx]);}
	@Override
	public float getRTVAng(int ndx) throws PsseModelException {return _base.getRTVAng(_ndxs[ndx]);}
	
	
	
}
