package com.powerdata.openpa;

/**
 * Interface for ModelBuilders, used to configure and create a PAModel object
 * 
 * @author chris@powerdata.com
 * 
 */
public abstract class ModelBuilder
{
	public PAModel load() throws PAModelException
	{
		PAModel m = new PAModel();
		loadStart(m);
		m._areas = loadAreas(m);
		m._owners = loadOwners(m);
		m._buslist = loadBuses(m);
		m._swlist = loadSwitches(m);
		m._linelist = loadLines(m);
		loadStop(m);
		return m;
	}

	protected void loadStop(PAModel m) throws PAModelException {}
	protected void loadStart(PAModel m) throws PAModelException {}

	protected abstract BusList loadBuses(PAModel m) throws PAModelException;
	protected abstract SwitchList loadSwitches(PAModel m) throws PAModelException;
	protected abstract LineList loadLines(PAModel m) throws PAModelException;
	protected abstract AreaList loadAreas(PAModel m) throws PAModelException;
	protected abstract OwnerList loadOwners(PAModel m) throws PAModelException;
}
