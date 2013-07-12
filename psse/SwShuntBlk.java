package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.BaseObject;

public class SwShuntBlk extends BaseObject
{
	protected SwShuntBlkList _list;
	
	public SwShuntBlk(int ndx, SwShuntBlkList list)
	{
		super(ndx);
		_list = list;
	}

	@Override
	public String getObjectID() throws PsseModelException {return _list.getObjectID(_ndx);}
	
	/* convenience methods */
	
	/** per-unit susceptance of each step (B) */
	public float getShuntB() throws PsseModelException {return _list.getShuntB(_ndx);}
	
	
	/* raw PSS/e methods */
	
	/** number of steps in block */
	public int getN() throws PsseModelException {return _list.getN(_ndx);}
	/** admittance for each step MVAr at unity voltage */
	public float getB() throws PsseModelException {return _list.getB(_ndx);}
	
}
