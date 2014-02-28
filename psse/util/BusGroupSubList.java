package com.powerdata.openpa.psse.util;

import com.powerdata.openpa.psse.Bus;
import com.powerdata.openpa.psse.BusList;
import com.powerdata.openpa.psse.BusTypeCode;
import com.powerdata.openpa.psse.GenList;
import com.powerdata.openpa.psse.Island;
import com.powerdata.openpa.psse.LineList;
import com.powerdata.openpa.psse.LoadList;
import com.powerdata.openpa.psse.PhaseShifterList;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.psse.ShuntList;
import com.powerdata.openpa.psse.SvcList;
import com.powerdata.openpa.psse.SwitchList;
import com.powerdata.openpa.psse.SwitchedShuntList;
import com.powerdata.openpa.psse.TransformerList;
import com.powerdata.openpa.psse.TwoTermDCLineList;

public class BusGroupSubList extends BusGroup2TDevList
{
	BusGroupList _base;
	int[] _ndxs;
	boolean _indexed = false;
	
	public BusGroupSubList(BusGroupList base, int[] ndxs) throws PsseModelException
	{
		super(base._model);
		_base = base;
		_ndxs = ndxs;
	}
	
	protected int map(int ndx) {return _ndxs[ndx];}

	@Override
	public BusGroup get(String id)
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
	public int size() {return _ndxs.length;}

	@Override
	public String getObjectID(int ndx) throws PsseModelException {return _base.getObjectID(map(ndx));}
	@Override
	public int findGrpNdx(Bus bus) throws PsseModelException {return -1;}
	@Override
	public int findGrpNdx(int busndx) throws PsseModelException {return -1;}
	@Override
	public BusGroup findGroup(Bus bus) throws PsseModelException {return null;}
	@Override
	public BusGroup findGroup(int busndx) throws PsseModelException {return null;}
	@Override
	public int[] getBusNdxs(int ndx) {return _base.getBusNdxs(map(ndx));}
	@Override
	public BusList getBuses(int ndx) throws PsseModelException {return _base.getBuses(map(ndx));}
	@Override
	public GenList getGenerators(int ndx) throws PsseModelException {return _base.getGenerators(map(ndx));}
	@Override
	public LoadList getLoads(int ndx) throws PsseModelException {return _base.getLoads(map(ndx));}
	@Override
	public LineList getLines(int ndx) throws PsseModelException {return _base.getLines(map(ndx));}
	@Override
	public TransformerList getTransformers(int ndx) throws PsseModelException {return _base.getTransformers(map(ndx));}
	@Override
	public PhaseShifterList getPhaseShifters(int ndx) throws PsseModelException
	{
		return _base.getPhaseShifters(map(ndx));
	}

	@Override
	public SwitchList getSwitches(int ndx) throws PsseModelException {return _base.getSwitches(map(ndx));}
	@Override
	public ShuntList getShunts(int ndx) throws PsseModelException {return _base.getShunts(map(ndx));}

	@Override
	public SvcList getSvcs(int ndx) throws PsseModelException {return _base.getSvcs(map(ndx));}
	@Override
	public SwitchedShuntList getSwitchedShunts(int ndx)
			throws PsseModelException
	{
		return _base.getSwitchedShunts(map(ndx));
	}

	@Override
	public TwoTermDCLineList getTwoTermDCLines(int ndx)
			throws PsseModelException
	{
		return _base.getTwoTermDCLines(map(ndx));
	}

	@Override
	public String getObjectName(int ndx) throws PsseModelException {return _base.getObjectName(map(ndx));}
	@Override
	public String getFullName(int ndx) throws PsseModelException {return _base.getFullName(map(ndx));}
	@Override
	public String getDebugName(int ndx) throws PsseModelException {return _base.getDebugName(map(ndx));}
	@Override
	@Deprecated /*CMM - not all groups have meaningful voltage or energization status */
	public Island getIsland(int ndx) throws PsseModelException {return _base.getIsland(map(ndx));}
	@Override
	@Deprecated /*CMM - not all groups have meaningful voltage or energization status */
	public BusTypeCode getBusType(int ndx) throws PsseModelException {return _base.getBusType(map(ndx));}
	@Override
	@Deprecated /*CMM - not all groups have meaningful voltage or energization status */
	public float getVArad(int ndx) throws PsseModelException {return _base.getVArad(map(ndx));}
	@Override
	@Deprecated /*CMM - not all groups have meaningful voltage or energization status */
	public float getVMpu(int ndx) throws PsseModelException {return _base.getVMpu(map(ndx));}
	@Override
	@Deprecated /*CMM - not all groups have meaningful voltage or energization status */
	public boolean isEnergized(int ndx) throws PsseModelException {return _base.isEnergized(map(ndx));}
	@Override
	@Deprecated
	public int getRootIndex(int ndx) {return _base.getRootIndex(map(ndx));}
	@Override
	public long getKey(int ndx) {return _base.getKey(ndx);}
}
