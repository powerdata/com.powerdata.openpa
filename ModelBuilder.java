package com.powerdata.openpa;

/**
 * Interface for ModelBuilders, used to configure and create a PAModel object
 * 
 * @author chris@powerdata.com
 * 
 */
public abstract class  ModelBuilder
{
	protected PAModel _m;
	
	public abstract PAModel load() throws PAModelException;

	protected abstract BusList getBuses() throws PAModelException;
	
	protected abstract SwitchList getSwitches() throws PAModelException;
	
	protected abstract LineList getLines() throws PAModelException;

	protected abstract AreaList getAreas() throws PAModelException;
	
	protected BusList createBusList(int[] keys) {return new BusList(_m, keys);}
	protected BusList createBusList(int size) {return new BusList(_m, size);}
	
	protected SwitchList createSwitchList(int[] keys, int[] fbuskeys,
			int[] tbuskeys)
	{
		return new SwitchList(_m, keys, fbuskeys, tbuskeys);
	}
	protected LineList createLineList(int[] keys, int[] fbuskeys,
			int[] tbuskeys)
	{
		return new LineList(_m, keys, fbuskeys, tbuskeys);
	}
	
	protected AreaList createAreaList(int[] keys) {

	protected void createModel() throws PAModelException
	{
		_m = new PAModel();
		_m.load(this);
	}

}
