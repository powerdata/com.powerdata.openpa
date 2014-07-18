package com.powerdata.openpa;

/** 
 * Provide a view of attributes common to AC Branches.  
 * 
 * @author chris@powerdata.com
 *
 */

public class ACBranch extends TwoTermDev
{
	ACBranchListIfc<? extends ACBranch> _list;
	
	public ACBranch(ACBranchListIfc<? extends ACBranch> list, int ndx)
	{
		super(list, ndx);
		_list = list;
	}

	/** get resistance p.u. on 100 MVA and bus base kv */
	public float getR()
	{
		return _list.getR(_ndx);
	}
	/** set resistance p.u. on 100 MVA and bus base kv */
	public void setR(float r)
	{
		_list.setR(_ndx, r);
	}
	/** get reactance p.u. on 100MVA and bus base KV */
	public float getX()
	{
		return _list.getX(_ndx);
	}
	/** set reactance p.u. on 100MVA and bus base KV */
	public void setX(float x)
	{
		_list.setX(_ndx, x);
	}
	/** get from-side off-nominal tap ratio p.u. on 100MVA base and bus base KV */
	public float getFromTap()
	{
		return 1f;
	}
	/** set from-side off-nominal tap ratio p.u. on 100MVA base and bus base KV */
	public void setFromTap(float a)
	{
		// defaults to no operation
	}
	/** get to-side off-nominal tap ratio p.u on 100MVA base and bus base KV */
	public float getToTap()
	{
		return 1f;
	}
	/** set to-side off-nominal tap ratio p.u on 100MVA base and bus base KV */
	public void setToTap(float a)
	{
		// defaults to no operation
	}
	/** get transformer magnetizing conductance p.u. on 100MVA base */
	public float getGmag()
	{
		return 0f;
	}
	/** set transformer magnetizing conductance p.u. on 100MVA base */
	public void setGmag(float g)
	{
		// defaults to no operation
	}
	/** get transformer magnetizing susceptance p.u. on 100 MVA base */
	public float getBmag()
	{
		return 0f;
	}
	/** set transformer magnetizing susceptance p.u. on 100 MVA base */
	public void setBmag(float b)
	{
		// defaults to no operation
	}
	/** get from-side charging susceptance */
	public float getFromBchg()
	{
		return 0f;
	}
	/** set from-side charging susceptance */
	public void setFromBchg(float b)
	{
		// defaults to no operation
	}
	/** get to-side charging susceptance */
	public float getToBchg()
	{
		return 0f;
	}
	/** set to-side charging susceptance */
	public void setToBchg(float b)
	{
		// defaults to no operation
	}
	/** get phase shift through branch in Degrees */
	public float getShift()
	{
		return 0f;
	}
	/** set phase shift through branch in Degrees */
	public void setShift(float sdeg)
	{
		// defaults to no operation
	}
}
