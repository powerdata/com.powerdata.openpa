package com.powerdata.openpa.padbc;

public abstract class Branch extends BaseObject 
{
	public Branch(int ndx)
	{
		super(ndx);
	}

	public abstract int getFromNode();
	public abstract int getToNode();
	public abstract float getR();
	public abstract float getX();

}
