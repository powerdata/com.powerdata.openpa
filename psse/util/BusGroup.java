package com.powerdata.openpa.psse.util;

import com.powerdata.openpa.psse.BusList;
import com.powerdata.openpa.psse.BusTypeCode;
import com.powerdata.openpa.psse.GenList;
import com.powerdata.openpa.psse.Island;
import com.powerdata.openpa.psse.LineList;
import com.powerdata.openpa.psse.LoadList;
import com.powerdata.openpa.psse.PhaseShifterList;
import com.powerdata.openpa.psse.PsseLists;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.psse.ShuntList;
import com.powerdata.openpa.psse.SvcList;
import com.powerdata.openpa.psse.SwitchList;
import com.powerdata.openpa.psse.SwitchedShuntList;
import com.powerdata.openpa.psse.TransformerList;
import com.powerdata.openpa.psse.TwoTermDCLineList;
import com.powerdata.openpa.tools.AbstractBaseObject;

public class BusGroup extends AbstractBaseObject implements PsseLists
{
	
	BusGroupList _bglist;
	
	public BusGroup(BusGroupList list, int ndx)
	{
		super(list, ndx);
		_bglist = list;
	}

	@Override
	public BusList getBuses() throws PsseModelException {return _bglist.getBuses(_ndx);}
	@Override
	public GenList getGenerators() throws PsseModelException {return _bglist.getGenerators(_ndx);}
	@Override
	public LoadList getLoads() throws PsseModelException {return _bglist.getLoads(_ndx);}
	@Override
	public LineList getLines() throws PsseModelException {return _bglist.getLines(_ndx);}

	@Override
	public TransformerList getTransformers() throws PsseModelException
	{
		return _bglist.getTransformers(_ndx);
	}

	@Override
	public PhaseShifterList getPhaseShifters() throws PsseModelException
	{
		return _bglist.getPhaseShifters(_ndx);
	}

	@Override
	public SwitchList getSwitches() throws PsseModelException {return _bglist.getSwitches(_ndx);}
	@Override
	public ShuntList getShunts() throws PsseModelException {return _bglist.getShunts(_ndx);}
	@Override
	public SvcList getSvcs() throws PsseModelException {return _bglist.getSvcs(_ndx);}
	@Override
	public SwitchedShuntList getSwitchedShunts() throws PsseModelException 
	{
		return _bglist.getSwitchedShunts(_ndx);
	}

	@Override
	public TwoTermDCLineList getTwoTermDCLines() throws PsseModelException
	{
		return _bglist.getTwoTermDCLines(_ndx);
	}

	/** return the island this group is in */
	@Deprecated /*CMM - not all groups have meaningful voltage or energization status */
	public Island getIsland() throws PsseModelException {return _bglist.getIsland(_ndx);}

	@Deprecated /*CMM - not all groups have meaningful voltage or energization status */
	public BusTypeCode getBusType() throws PsseModelException
	{
		return _bglist.getBusType(_ndx);
	}
	
	@Deprecated /*CMM - not all groups have meaningful voltage or energization status */
	public float getVArad() throws PsseModelException {return _bglist.getVArad(_ndx);}
	@Deprecated /*CMM - not all groups have meaningful voltage or energization status */
	public float getVMpu() throws PsseModelException {return _bglist.getVMpu(_ndx);}
	@Deprecated /*CMM - not all groups have meaningful voltage or energization status */
	public boolean isEnergized() throws PsseModelException {return _bglist.isEnergized(_ndx);}
}
