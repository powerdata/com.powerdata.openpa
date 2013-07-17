package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.PAMath;

public abstract class SwShuntBlkList extends PsseBaseList<SwShuntBlk>
{
	protected SwitchedShunt _sh;

	public static final SwShuntBlkList Empty = new SwShuntBlkList()
	{
		@Override
		public int size() {return 0;}
	};
	
	protected SwShuntBlkList() {super();}
	public SwShuntBlkList(PsseModel model, SwitchedShunt sh)
	{
		super(model);
		_sh = sh;
	}

	/* Standard object retrieval */

	/** Get a SwitchedShuntBlock by it's index. */
	@Override
	public SwShuntBlk get(int ndx) { return new SwShuntBlk(ndx,this); }
	/** Get a SwitchedShuntBlock by it's ID. */
	@Override
	public SwShuntBlk get(String id) { return super.get(id); }

	/* convenience methods */

	public float getShuntB(int ndx) throws PsseModelException {return PAMath.mvar2pu(getB(ndx));}
	
	/* raw methods */

	public int getN(int ndx) throws PsseModelException {return 0;}
	public float getB(int ndx) throws PsseModelException {return 0F;}

	@Override
	public String getObjectID(int ndx) throws PsseModelException
	{
		StringBuilder sb = new StringBuilder(_sh.getObjectID());
		sb.append('-');
		sb.append(ndx);
		return sb.toString();
	}

}
