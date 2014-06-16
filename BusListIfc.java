package com.powerdata.openpa;


/**
 * Interface for bus List values needed by the Bus class. Makes the Bus object
 * usable by multiple lists (i.e. connectivity and single-bus views)
 * 
 * @author chris@powerdata.com
 * 
 */
public abstract class BusListIfc extends EquipLists<Bus>
{

	protected BusListIfc(PALists model, int[] keys, BusGrpMap grp)
	{
		super(model, keys, grp);
	}

	protected BusListIfc(PALists model, BusGrpMap grp)
	{
		super(model, grp);
	}

	protected BusListIfc() {super();}

	public abstract float getBaseKV(int ndx);

	public abstract void setBaseKV(int ndx, float kv);

	public abstract float getVM(int ndx);

	public abstract void setVM(int ndx, float vm);

	public abstract float getVA(int ndx);

	public abstract void setVA(int ndx, float va);

	public abstract float[] getBaseKV();

	public abstract void setBaseKV(float[] kv);

	public abstract float[] getVM();

	public abstract void setVM(float[] vm);

	public abstract float[] getVA();

	public abstract void setVA(float[] va);

}


