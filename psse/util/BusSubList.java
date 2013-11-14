package com.powerdata.openpa.psse.util;

import com.powerdata.openpa.psse.Area;
import com.powerdata.openpa.psse.Bus;
import com.powerdata.openpa.psse.BusList;
import com.powerdata.openpa.psse.BusTypeCode;
import com.powerdata.openpa.psse.GenList;
import com.powerdata.openpa.psse.LineList;
import com.powerdata.openpa.psse.LoadList;
import com.powerdata.openpa.psse.Owner;
import com.powerdata.openpa.psse.PhaseShifterList;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.psse.ShuntList;
import com.powerdata.openpa.psse.SvcList;
import com.powerdata.openpa.psse.SwitchList;
import com.powerdata.openpa.psse.TransformerList;
import com.powerdata.openpa.psse.Zone;
import com.powerdata.openpa.tools.Complex;

public class BusSubList extends BusList
{
	BusList _base;
	int[] _ndxs;
	
	public BusSubList(BusList base, int[] ndxs) throws PsseModelException
	{
		super(base.getPsseModel());
		_base = base;
		_ndxs = ndxs;
		reindex();
	}
	
	@Override
	public Bus get(String id) {return new Bus(_idToNdx.get(id), this);}
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
	public float getVMpu(int ndx) throws PsseModelException {return _base.getVMpu(_ndxs[ndx]);}
	@Override
	public float getVArad(int ndx) throws PsseModelException {return _base.getVArad(_ndxs[ndx]);}
	@Override
	public int getOWNER(int ndx) throws PsseModelException {return _base.getOWNER(_ndxs[ndx]);}
	@Override
	public String getObjectID(int ndx) throws PsseModelException {return _base.getObjectID(_ndxs[ndx]);}
	@Override
	public int size() {return _ndxs.length;}
	@Override
	public int getRootIndex(int ndx) {return _base.getRootIndex(_ndxs[ndx]);}
}
