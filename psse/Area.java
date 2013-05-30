package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.BaseObject;

public class Area extends BaseObject implements Group
{
	protected AreaList _list;
	
	public Area(int ndx, AreaList list)
	{
		super(ndx);
		_list = list;
	}

	@Override
	public String getDebugName() throws PsseModelException {return getARNAME();}


	@Override
	public String getObjectID() {return _list.getObjectID(_ndx);}


	
	/* convenience methods */
	
	/** Area slack bus for area interchange control */ 
	public Bus getSlackBus() throws PsseModelException {return _list.getSlackBus(_ndx);}
	/** Desired net interchange (PDES) leaving the area entered p.u. */
	public float getIntExport()  throws PsseModelException {return _list.getIntExport(_ndx);}
	/** Interchange tolerance bandwidth (PTOL) in p.u. */
	public float getIntTol() throws PsseModelException {return _list.getIntTol(_ndx);}


	/* raw PSS/e methods */
	
	/** Area number */
	public int getI() throws PsseModelException {return _list.getI(_ndx);}
	/** Area slack bus for area interchange control */
	public String getISW() throws PsseModelException {return _list.getISW(_ndx);}
	/** Desired net interchange leaving the area entered in MW */
	public float getPDES() throws PsseModelException {return _list.getPDES(_ndx);}
	/** Interchange tolerance bandwidth entered in MW */
	public float getPTOL() throws PsseModelException {return _list.getPTOL(_ndx);}
	/** Alphanumeric identifier assigned to area */
	public String getARNAME() throws PsseModelException {return _list.getARNAME(_ndx);}

	/* Lists relevant to group */
	
	@Override
	public String getGroupName() throws PsseModelException {return getARNAME();}
	@Override
	public BusList getBuses() throws PsseModelException {return _list.getBuses();}
	@Override
	public GeneratorList getGenerators() throws PsseModelException {return _list.getGenerators();}
	@Override
	public LineList getNontransformerBranches() throws PsseModelException {return _list.getLines();}
	@Override
	public TransformerList getTransformers() throws PsseModelException {return _list.getTransformers();}
	@Override
	public OwnerList getOwners() throws PsseModelException {return _list.getOwners();}
	@Override
	public AreaList getAreas() throws PsseModelException {return _list.getAreas();}
	@Override
	public ZoneList getZones() throws PsseModelException {return _list.getZones();}
	@Override
	public ImpCorrTblList getImpCorrTables() throws PsseModelException {return _list.getImpCorrTables();}
	@Override
	public LoadList getLoads() throws PsseModelException {return _list.getLoads();}
	@Override
	public SwitchedShuntList getSwitchedShunts() throws PsseModelException {return _list.getSwitchedShunts();}
}
