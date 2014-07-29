package com.powerdata.openpa;

import com.powerdata.openpa.impl.SuperListMgr;


public class ACBranchList extends SuperListMgr<ACBranch, ACBranchListIfc<ACBranch>> implements
		ACBranchListIfc<ACBranch>
{

	public ACBranchList(PALists lists) throws PAModelException
	{
		super(lists, ACBranchListIfc.class);
	}

	@Override
	public Bus getFromBus(int ndx) throws PAModelException
	{
		ListInfo li = getLI(ndx);
		return li.list.getFromBus(li.ofs);
	}

	@Override
	public void setFromBus(int ndx, Bus b) throws PAModelException
	{
		ListInfo li = getLI(ndx);
		li.list.setFromBus(li.ofs, b);
	}

	@Override
	public Bus[] getFromBus() throws PAModelException
	{
		return getHelper(Bus[]::new, l -> l.getFromBus());
	}

	@Override
	public void setFromBus(Bus[] b) throws PAModelException
	{
		setHelper(b, Bus[]::new, (l,v) -> l.setToBus(v));
	}

	@Override
	public Bus getToBus(int ndx) throws PAModelException
	{
		ListInfo li = getLI(ndx);
		return li.list.getToBus(li.ofs);
	}

	@Override
	public void setToBus(int ndx, Bus b) throws PAModelException
	{
		ListInfo li = getLI(ndx);
		li.list.setToBus(li.ofs, b);
	}

	@Override
	public Bus[] getToBus() throws PAModelException
	{
		return getHelper(Bus[]::new, l -> l.getToBus());
	}

	@Override
	public void setToBus(Bus[] b) throws PAModelException
	{
		setHelper(b, Bus[]::new, (l,v) -> l.setToBus(v));
	}

	@Override
	public boolean isOutOfSvc(int ndx) throws PAModelException
	{
		ListInfo li = getLI(ndx);
		return li.list.isOutOfSvc(li.ofs);
	}

	@Override
	public void setOutOfSvc(int ndx, boolean state) throws PAModelException
	{
		ListInfo li = getLI(ndx);
		li.list.setOutOfSvc(li.ofs, state);
	}

	@Override
	public boolean[] isOutOfSvc() throws PAModelException
	{
		return getHelper(boolean[]::new, l -> l.isOutOfSvc());
	}

	@Override
	public void setOutOfSvc(boolean[] state) throws PAModelException
	{
		setHelper(state, boolean[]::new, (l,v) -> l.setOutOfSvc(v));
	}

	@Override
	public float getR(int ndx) throws PAModelException
	{
		ListInfo li = getLI(ndx);
		return li.list.getR(li.ofs);
	}

	@Override
	public void setR(int ndx, float r) throws PAModelException
	{
		ListInfo li = getLI(ndx);
		li.list.setR(li.ofs, r);
	}

	@Override
	public float[] getR() throws PAModelException
	{
		return getHelper(float[]::new, l -> l.getR());
	}

	@Override
	public void setR(float[] r) throws PAModelException
	{
		setHelper(r, float[]::new, (l,v) -> l.setR(v));
	}

	@Override
	public float getX(int ndx) throws PAModelException
	{
		ListInfo li = getLI(ndx);
		return li.list.getX(li.ofs);
	}

	@Override
	public void setX(int ndx, float x) throws PAModelException
	{
		ListInfo li = getLI(ndx);
		li.list.setX(li.ofs, x);
	}

	@Override
	public float[] getX() throws PAModelException
	{
		return getHelper(float[]::new, l -> l.getX());
	}

	@Override
	public void setX(float[] x) throws PAModelException
	{
		setHelper(x, float[]::new, (l,v) -> l.setX(v));
	}

	@Override
	public ACBranch get(int index)
	{
		return new ACBranch(this, index);
	}

	@Override
	public float getFromTap(int ndx) throws PAModelException
	{
		ListInfo li = getLI(ndx);
		return li.list.getFromTap(li.ofs);
	}

	@Override
	public void setFromTap(int ndx, float a) throws PAModelException
	{
		ListInfo li = getLI(ndx);
		li.list.setFromTap(li.ofs, a);
	}

	@Override
	public float[] getFromTap() throws PAModelException
	{
		return getHelper(float[]::new, l -> l.getFromTap());
	}

	@Override
	public void setFromTap(float[] a) throws PAModelException
	{
		setHelper(a, float[]::new, (l,v) -> l.setFromTap(v));
	}

	@Override
	public float getToTap(int ndx) throws PAModelException
	{
		ListInfo li = getLI(ndx);
		return li.list.getToTap(li.ofs);
	}

	@Override
	public void setToTap(int ndx, float a) throws PAModelException
	{
		ListInfo li = getLI(ndx);
		li.list.setToTap(li.ofs, a);
	}

	@Override
	public float[] getToTap() throws PAModelException
	{
		return getHelper(float[]::new, l -> l.getToTap());		
	}

	@Override
	public void setToTap(float[] a) throws PAModelException
	{
		setHelper(a, float[]::new, (l,v) -> l.setFromTap(v));
	}

	@Override
	public float getGmag(int ndx) throws PAModelException
	{
		ListInfo li = getLI(ndx);
		return li.list.getGmag(li.ofs);
	}

	@Override
	public void setGmag(int ndx, float g) throws PAModelException
	{
		ListInfo li = getLI(ndx);
		li.list.setGmag(li.ofs, g);		
	}

	@Override
	public float[] getGmag() throws PAModelException
	{
		return getHelper(float[]::new, l -> l.getGmag());		
	}

	@Override
	public void setGmag(float[] g) throws PAModelException
	{
		setHelper(g, float[]::new, (l,v) -> l.setFromTap(v));
	}

	@Override
	public float getBmag(int ndx) throws PAModelException
	{
		ListInfo li = getLI(ndx);
		return li.list.getBmag(li.ofs);
	}

	@Override
	public void setBmag(int ndx, float b) throws PAModelException
	{
		ListInfo li = getLI(ndx);
		li.list.setBmag(li.ofs, b);		
	}

	@Override
	public float[] getBmag() throws PAModelException
	{
		return getHelper(float[]::new, l -> l.getBmag());		
	}

	@Override
	public void setBmag(float[] b) throws PAModelException
	{
		setHelper(b, float[]::new, (l,v) -> l.setFromTap(v));
	}

	@Override
	public float getFromBchg(int ndx) throws PAModelException
	{
		ListInfo li = getLI(ndx);
		return li.list.getFromBchg(li.ofs);	
	}

	@Override
	public void setFromBchg(int ndx, float b) throws PAModelException
	{
		ListInfo li = getLI(ndx);
		li.list.setFromBchg(li.ofs, b);		
	}

	@Override
	public float[] getFromBchg() throws PAModelException
	{
		return getHelper(float[]::new, l -> l.getFromBchg());		
	}

	@Override
	public void setFromBchg(float[] b) throws PAModelException
	{
		setHelper(b, float[]::new, (l,v) -> l.setFromTap(v));
	}

	@Override
	public float getToBchg(int ndx) throws PAModelException
	{
		ListInfo li = getLI(ndx);
		return li.list.getToBchg(li.ofs);
	}

	@Override
	public void setToBchg(int ndx, float b) throws PAModelException
	{
		ListInfo li = getLI(ndx);
		li.list.setToBchg(li.ofs, b);		
	}

	@Override
	public float[] getToBchg() throws PAModelException
	{
		return getHelper(float[]::new, l -> l.getToBchg());		
	}

	@Override
	public void setToBchg(float[] b) throws PAModelException
	{
		setHelper(b, float[]::new, (l,v) -> l.setFromTap(v));
	}

	@Override
	public float getShift(int ndx) throws PAModelException
	{
		ListInfo li = getLI(ndx);
		return li.list.getShift(li.ofs);
	}

	@Override
	public void setShift(int ndx, float sdeg) throws PAModelException
	{
		ListInfo li = getLI(ndx);
		li.list.setShift(li.ofs, sdeg);		
	}

	@Override
	public float[] getShift() throws PAModelException
	{
		return getHelper(float[]::new, l -> l.getShift());		
	}

	@Override
	public void setShift(float[] sdeg) throws PAModelException
	{
		setHelper(sdeg, float[]::new, (l,v) -> l.setFromTap(v));
	}

	@Override
	public float getFromP(int ndx) throws PAModelException
	{
		ListInfo li = getLI(ndx);
		return li.list.getFromP(li.ofs);
	}

	@Override
	public void setFromP(int ndx, float mw) throws PAModelException
	{
		ListInfo li = getLI(ndx);
		li.list.setFromP(li.ofs, mw);
	}

	@Override
	public float getFromQ(int ndx) throws PAModelException
	{
		ListInfo li = getLI(ndx);
		return li.list.getFromQ(li.ofs);
	}

	@Override
	public void setFromQ(int ndx, float mvar) throws PAModelException
	{
		ListInfo li = getLI(ndx);
		li.list.setFromQ(li.ofs, mvar);
	}

	@Override
	public float getToP(int ndx) throws PAModelException
	{
		ListInfo li = getLI(ndx);
		return li.list.getToP(li.ofs);
	}

	@Override
	public void setToP(int ndx, float mw) throws PAModelException
	{
		ListInfo li = getLI(ndx);
		li.list.setToP(li.ofs, mw);
	}

	@Override
	public float getToQ(int ndx) throws PAModelException
	{
		ListInfo li = getLI(ndx);
		return li.list.getToQ(li.ofs);
	}

	@Override
	public void setToQ(int ndx, float mvar) throws PAModelException
	{
		ListInfo li = getLI(ndx);
		li.list.setToQ(li.ofs, mvar);
	}

	@Override
	public float[] getFromP() throws PAModelException
	{
		return getHelper(float[]::new, l -> l.getFromP());
	}

	@Override
	public void setFromP(float[] mw) throws PAModelException
	{
		setHelper(mw, float[]::new, (l,v) -> l.setFromP(v));
	}

	@Override
	public float[] getFromQ() throws PAModelException
	{
		return getHelper(float[]::new, l -> l.getFromQ());
	}

	@Override
	public void setFromQ(float[] mvar) throws PAModelException
	{
		setHelper(mvar, float[]::new, (l,v) -> l.setFromQ(v));
	}

	@Override
	public float[] getToP() throws PAModelException
	{
		return getHelper(float[]::new, l -> l.getToP());
	}

	@Override
	public void setToP(float[] mw) throws PAModelException
	{
		setHelper(mw, float[]::new, (l,v) -> l.setToP(v));
	}

	@Override
	public float[] getToQ() throws PAModelException
	{
		return getHelper(float[]::new, l -> l.getToQ());
	}

	@Override
	public void setToQ(float[] mvar) throws PAModelException
	{
		setHelper(mvar, float[]::new, (l,v) -> l.setToQ(v));
	}

}
