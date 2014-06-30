package com.powerdata.openpa;

/**
 * Interface for bus List values needed by the Bus class. Makes the Bus object
 * usable by multiple lists (i.e. connectivity and single-bus views)
 * 
 * @author chris@powerdata.com
 * 
 */
public abstract class BusListIfc extends GroupList<Bus> {

	protected BusListIfc(PALists model, int[] keys, BusGrpMap grp)
	{
		super(model, keys, grp);
	}

	protected BusListIfc(PALists model, BusGrpMap grp)
	{
		super(model, grp);
	}

	protected BusListIfc()
	{
		super();
	}

	public abstract float getVM(int ndx);

	public abstract void setVM(int ndx, float vm);

	public abstract float getVA(int ndx);

	public abstract void setVA(int ndx, float va);

	public abstract float[] getVM();

	public abstract void setVM(float[] vm);

	public abstract float[] getVA();

	public abstract void setVA(float[] va);

	public abstract Area getArea(int ndx);

	public abstract void setArea(int ndx, Area a);

	public abstract Island getIsland(int ndx);

	public abstract int getFrequencySourcePriority(int ndx);

	public abstract void setFrequencySourcePriority(int ndx, int fsp);

	public abstract Station getStation(int ndx);

	public abstract void setStation(int ndx, Station s);

	public abstract Owner getOwner(int ndx);

	public abstract void setOwner(int ndx, Owner o);

	public abstract VoltageLevel getVoltageLevel(int _ndx);

	public abstract void setVoltageLevel(int _ndx, VoltageLevel l);

}
