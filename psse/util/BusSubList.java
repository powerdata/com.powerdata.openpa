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
	boolean _indexed = false;
	
	public BusSubList(BusList base, int[] ndxs) throws PsseModelException
	{
		super(base.getPsseModel());
		_base = base;
		_ndxs = ndxs;
	}
	
	@Override
	public Bus get(String id)
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
	public void commit() throws PsseModelException {_base.commit();}
	@Override
	public GenList getGenerators(int ndx) throws PsseModelException {return _base.getGenerators(map(ndx));}
	@Override
	public LoadList getLoads(int ndx) throws PsseModelException {return _base.getLoads(map(ndx));}
	@Override
	public LineList getLines(int ndx) throws PsseModelException {return _base.getLines(map(ndx));}
	@Override
	public TransformerList getTransformers(int ndx) throws PsseModelException {return _base.getTransformers(map(ndx));}
	@Override
	public ShuntList getShunts(int ndx) throws PsseModelException {return _base.getShunts(map(ndx));}
	@Override
	public SvcList getSvcs(int ndx) throws PsseModelException {return _base.getSvcs(map(ndx));}
	@Override
	public PhaseShifterList getPhaseShifters(int ndx) throws PsseModelException {return _base.getPhaseShifters(map(ndx));}
	@Override
	public SwitchList getSwitches(int ndx) throws PsseModelException {return _base.getSwitches(map(ndx));}
	@Override
	public SwitchList isolate(int ndx) throws PsseModelException {return _base.isolate(map(ndx));}
	@Override
	public boolean isEnergized(int ndx) throws PsseModelException {return _base.isEnergized(map(ndx));}
	@Override
	public int getIsland(int ndx) throws PsseModelException {return _base.getIsland(map(ndx));}
	@Override
	public BusTypeCode getBusType(int ndx) throws PsseModelException {return _base.getBusType(map(ndx));}
	@Override
	public Area getAreaObject(int ndx) throws PsseModelException {return _base.getAreaObject(map(ndx));}
	@Override
	public Zone getZoneObject(int ndx) throws PsseModelException {return _base.getZoneObject(map(ndx));}
	@Override
	public Owner getOwnerObject(int ndx) throws PsseModelException {return _base.getOwnerObject(map(ndx));}
	@Override
	public Complex getShuntY(int ndx) throws PsseModelException {return _base.getShuntY(map(ndx));}
	@Override
	public String getObjectName(int ndx) throws PsseModelException {return _base.getObjectName(map(ndx));}
	@Override
	public int getI(int ndx) throws PsseModelException {return _base.getI(map(ndx));}
	@Override
	public String getNAME(int ndx) throws PsseModelException {return _base.getNAME(map(ndx));}
	@Override
	public float getBASKV(int ndx) throws PsseModelException {return _base.getBASKV(map(ndx));}
	@Override
	public int getIDE(int ndx) throws PsseModelException {return _base.getIDE(map(ndx));}
	@Override
	public float getGL(int ndx) throws PsseModelException {return _base.getGL(map(ndx));}
	@Override
	public float getBL(int ndx) throws PsseModelException {return _base.getBL(map(ndx));}
	@Override
	public int getAREA(int ndx) throws PsseModelException {return _base.getAREA(map(ndx));}
	@Override
	public int getZONE(int ndx) throws PsseModelException {return _base.getZONE(map(ndx));}
	@Override
	public float getVM(int ndx) throws PsseModelException {return _base.getVM(map(ndx));}
	@Override
	public float getVA(int ndx) throws PsseModelException {return _base.getVA(map(ndx));}
	@Override
	public float getVMpu(int ndx) throws PsseModelException {return _base.getVMpu(map(ndx));}
	@Override
	public float getVArad(int ndx) throws PsseModelException {return _base.getVArad(map(ndx));}
	@Override
	public int getOWNER(int ndx) throws PsseModelException {return _base.getOWNER(map(ndx));}
	@Override
	public String getObjectID(int ndx) throws PsseModelException {return _base.getObjectID(map(ndx));}
	@Override
	public int size() {return _ndxs.length;}
	@Override
	public int getRootIndex(int ndx) {return _base.getRootIndex(map(ndx));}

	@Override
	public int getStation(int ndx) throws PsseModelException {return _base.getStation(map(ndx));}
	@Override
	public void setVM(int ndx, float kv) throws PsseModelException {_base.setVM(map(ndx), kv);}
	@Override
	public void setVMpu(int ndx, float v) throws PsseModelException {_base.setVMpu(map(ndx), v);}
	@Override
	public void setVA(int ndx, float va) throws PsseModelException {_base.setVA(map(ndx), va);}
	@Override
	public void setVArad(int ndx, float rad) throws PsseModelException {_base.setVArad(map(ndx), rad);}
	@Override
	public float getFrequency(int ndx) throws PsseModelException {return _base.getFrequency(map(ndx));}
	@Override
	public int getFrequencySourcePriority(int ndx) throws PsseModelException {return _base.getFrequencySourcePriority(map(ndx));}

	@Override
	public String getFullName(int ndx) throws PsseModelException {return _base.getFullName(map(ndx));}
	@Override
	public String getDebugName(int ndx) throws PsseModelException {return _base.getDebugName(map(ndx));}
}
