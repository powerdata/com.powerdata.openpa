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
	public float getR() throws PAModelException
	{
		return _list.getR(_ndx);
	}
	/** set resistance p.u. on 100 MVA and bus base kv */
	public void setR(float r) throws PAModelException
	{
		_list.setR(_ndx, r);
	}
	/** get reactance p.u. on 100MVA and bus base KV */
	public float getX() throws PAModelException
	{
		return _list.getX(_ndx);
	}
	/** set reactance p.u. on 100MVA and bus base KV */
	public void setX(float x) throws PAModelException
	{
		_list.setX(_ndx, x);
	}
	/** get from-side off-nominal tap ratio p.u. on 100MVA base and bus base KV */
	public float getFromTap() throws PAModelException
	{
		return _list.getFromTap(_ndx);
	}
	/** set from-side off-nominal tap ratio p.u. on 100MVA base and bus base KV */
	public void setFromTap(float a) throws PAModelException
	{
		_list.setFromTap(_ndx, a);
	}
	/** get to-side off-nominal tap ratio p.u on 100MVA base and bus base KV */
	public float getToTap() throws PAModelException
	{
		return _list.getToTap(_ndx);
	}
	/** set to-side off-nominal tap ratio p.u on 100MVA base and bus base KV */
	public void setToTap(float a) throws PAModelException
	{
		_list.setToTap(_ndx, a);
	}
	/** get transformer magnetizing conductance p.u. on 100MVA base */
	public float getGmag() throws PAModelException
	{
		return _list.getGmag(_ndx);
	}
	/** set transformer magnetizing conductance p.u. on 100MVA base */
	public void setGmag(float g) throws PAModelException
	{
		_list.setGmag(_ndx, g);
	}
	/** get transformer magnetizing susceptance p.u. on 100 MVA base */
	public float getBmag() throws PAModelException
	{
		return _list.getBmag(_ndx);
	}
	/** set transformer magnetizing susceptance p.u. on 100 MVA base */
	public void setBmag(float b) throws PAModelException
	{
		_list.setBmag(_ndx, b);
	}
	/** get from-side charging susceptance */
	public float getFromBchg() throws PAModelException
	{
		return _list.getFromBchg(_ndx);
	}
	/** set from-side charging susceptance */
	public void setFromBchg(float b) throws PAModelException
	{
		_list.setFromBchg(_ndx, b);
	}
	/** get to-side charging susceptance */
	public float getToBchg() throws PAModelException
	{
		return _list.getToBchg(_ndx);
	}
	/** set to-side charging susceptance */
	public void setToBchg(float b) throws PAModelException
	{
		_list.setToBchg(_ndx, b);
	}
	/** get phase shift through branch in Degrees */
	public float getShift() throws PAModelException
	{
		return _list.getShift(_ndx);
	}
	/** set phase shift through branch in Degrees */
	public void setShift(float sdeg) throws PAModelException
	{
		_list.setShift(_ndx, sdeg);
	}
	
	/** get long-term branch rating MVA */
	public float getLTRating() throws PAModelException
	{
		return _list.getLTRating(_ndx);
	}
	
	/** set long-term branch rating MVA */
	public void setLTRating(float mva) throws PAModelException
	{
		_list.setLTRating(_ndx, mva);
	}
	
}
