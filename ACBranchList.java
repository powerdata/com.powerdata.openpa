package com.powerdata.openpa;

import java.util.AbstractList;
import java.util.Set;

public class ACBranchList extends AbstractList<ACBranch> implements ACBranchListIfc<ACBranch>
{
	ACBranchListIfc<? extends ACBranch> _src;
	
	public ACBranchList(ACBranchListIfc<? extends ACBranch> src)
	{
		_src = src;
	}

	@Override
	public Bus getFromBus(int ndx) throws PAModelException
	{
		return _src.getFromBus(ndx);
	}

	@Override
	public void setFromBus(int ndx, Bus b) throws PAModelException
	{
		_src.setFromBus(ndx, b);
	}

	@Override
	public Bus[] getFromBus() throws PAModelException
	{
		return _src.getFromBus();
	}

	@Override
	public void setFromBus(Bus[] b) throws PAModelException
	{
		_src.setFromBus(b);
	}

	@Override
	public Bus getToBus(int ndx) throws PAModelException
	{
		return _src.getToBus(ndx);
	}

	@Override
	public void setToBus(int ndx, Bus b) throws PAModelException
	{
		_src.setToBus(ndx, b);
	}

	@Override
	public Bus[] getToBus() throws PAModelException
	{
		return _src.getToBus();
	}

	@Override
	public void setToBus(Bus[] b) throws PAModelException
	{
		_src.setToBus(b);
	}

	@Override
	public boolean isInService(int ndx) throws PAModelException
	{
		return _src.isInService(ndx);
	}

	@Override
	public void setInService(int ndx, boolean state) throws PAModelException
	{
		_src.setInService(ndx, state);
	}

	@Override
	public boolean[] isInService() throws PAModelException
	{
		return _src.isInService();
	}

	@Override
	public void setInService(boolean[] state) throws PAModelException
	{
		_src.setInService(state);
	}

	@Override
	public float getFromP(int ndx) throws PAModelException
	{
		return _src.getFromP(ndx);
	}

	@Override
	public void setFromP(int ndx, float mw) throws PAModelException
	{
		_src.setFromP(ndx, mw);
	}

	@Override
	public float[] getFromP() throws PAModelException
	{
		return _src.getFromP();
	}

	@Override
	public void setFromP(float[] mw) throws PAModelException
	{
		_src.setFromP(mw);
	}

	@Override
	public float getFromQ(int ndx) throws PAModelException
	{
		return _src.getFromQ(ndx);
	}

	@Override
	public void setFromQ(int ndx, float mvar) throws PAModelException
	{
		_src.setFromQ(ndx, mvar);
	}

	@Override
	public float[] getFromQ() throws PAModelException
	{
		return _src.getFromQ();
	}

	@Override
	public void setFromQ(float[] mvar) throws PAModelException
	{
		_src.setFromQ(mvar);
	}

	@Override
	public float getToP(int ndx) throws PAModelException
	{
		return _src.getToP(ndx);
	}

	@Override
	public void setToP(int ndx, float mw) throws PAModelException
	{
		_src.setToP(ndx, mw);
	}

	@Override
	public float[] getToP() throws PAModelException
	{
		return _src.getToP();
	}

	@Override
	public void setToP(float[] mw) throws PAModelException
	{
		_src.setToP(mw);
	}

	@Override
	public float getToQ(int ndx) throws PAModelException
	{
		return _src.getToQ(ndx);
	}

	@Override
	public void setToQ(int ndx, float mvar) throws PAModelException
	{
		_src.setToQ(ndx, mvar);
	}

	@Override
	public float[] getToQ() throws PAModelException
	{
		return _src.getToQ();
	}

	@Override
	public void setToQ(float[] mvar) throws PAModelException
	{
		_src.setToQ(mvar);
	}

	@Override
	public int getKey(int ndx)
	{
		return _src.getKey(ndx);
	}

	@Override
	public int[] getKeys()
	{
		return _src.getKeys();
	}

	@Override
	public String getID(int ndx) throws PAModelException
	{
		return _src.getID(ndx);
	}

	@Override
	public String[] getID() throws PAModelException
	{
		return _src.getID();
	}

	@Override
	public void setID(String[] id) throws PAModelException
	{
		_src.setID(id);
	}

	@Override
	public void setID(int ndx, String id) throws PAModelException
	{
		_src.setID(ndx, id);
	}

	@Override
	public String getName(int ndx) throws PAModelException
	{
		return _src.getName(ndx);
	}

	@Override
	public void setName(int ndx, String name) throws PAModelException
	{
		_src.setName(ndx, name);
	}

	@Override
	public String[] getName() throws PAModelException
	{
		return _src.getName();
	}

	@Override
	public void setName(String[] name) throws PAModelException
	{
		_src.setName(name);
	}

	@Override
	public ACBranch getByKey(int key)
	{
		return _src.getByKey(key);
	}

	@Override
	public ListMetaType getListMeta()
	{
		return _src.getListMeta();
	}

	@Override
	public int getIndex(int ndx)
	{
		return _src.getIndex(ndx);
	}

	@Override
	public float getR(int ndx) throws PAModelException
	{
		return _src.getR(ndx);
	}

	@Override
	public void setR(int ndx, float r) throws PAModelException
	{
		_src.setR(ndx, r);
	}

	@Override
	public float[] getR() throws PAModelException
	{
		return _src.getR();
	}

	@Override
	public void setR(float[] r) throws PAModelException
	{
		_src.setR(r);
	}

	@Override
	public float getX(int ndx) throws PAModelException
	{
		return _src.getX(ndx);
	}

	@Override
	public void setX(int ndx, float x) throws PAModelException
	{
		_src.setX(ndx, x);
	}

	@Override
	public float[] getX() throws PAModelException
	{
		return _src.getX();
	}

	@Override
	public void setX(float[] x) throws PAModelException
	{
		_src.setX(x);
	}

	@Override
	public float getFromTap(int ndx) throws PAModelException
	{
		return _src.getFromTap(ndx);
	}

	@Override
	public void setFromTap(int ndx, float a) throws PAModelException
	{
		_src.setFromTap(ndx, a);
	}

	@Override
	public float[] getFromTap() throws PAModelException
	{
		return _src.getFromTap();
	}

	@Override
	public void setFromTap(float[] a) throws PAModelException
	{
		_src.setFromTap(a);
	}

	@Override
	public float getToTap(int ndx) throws PAModelException
	{
		return _src.getToTap(ndx);
	}

	@Override
	public void setToTap(int ndx, float a) throws PAModelException
	{
		_src.setToTap(ndx, a);
	}

	@Override
	public float[] getToTap() throws PAModelException
	{
		return _src.getToTap();
	}

	@Override
	public void setToTap(float[] a) throws PAModelException
	{
		_src.setToTap(a);
	}

	@Override
	public float getGmag(int ndx) throws PAModelException
	{
		return _src.getGmag(ndx);
	}

	@Override
	public void setGmag(int ndx, float g) throws PAModelException
	{
		_src.setGmag(ndx, g);
	}

	@Override
	public float[] getGmag() throws PAModelException
	{
		return _src.getGmag();
	}

	@Override
	public void setGmag(float[] g) throws PAModelException
	{
		_src.setGmag(g);
	}

	@Override
	public float getBmag(int ndx) throws PAModelException
	{
		return _src.getBmag(ndx);
	}

	@Override
	public void setBmag(int ndx, float b) throws PAModelException
	{
		_src.setBmag(ndx, b);
	}

	@Override
	public float[] getBmag() throws PAModelException
	{
		return _src.getBmag();
	}

	@Override
	public void setBmag(float[] b) throws PAModelException
	{
		_src.setBmag(b);
	}

	@Override
	public float getFromBchg(int ndx) throws PAModelException
	{
		return _src.getFromBchg(ndx);
	}

	@Override
	public void setFromBchg(int ndx, float b) throws PAModelException
	{
		_src.setFromBchg(ndx, b);
	}

	@Override
	public float[] getFromBchg() throws PAModelException
	{
		return _src.getFromBchg();
	}

	@Override
	public void setFromBchg(float[] b) throws PAModelException
	{
		_src.setFromBchg(b);
	}

	@Override
	public float getToBchg(int ndx) throws PAModelException
	{
		return _src.getToBchg(ndx);
	}

	@Override
	public void setToBchg(int ndx, float b) throws PAModelException
	{
		_src.setToBchg(ndx, b);
	}

	@Override
	public float[] getToBchg() throws PAModelException
	{
		return _src.getToBchg();
	}

	@Override
	public void setToBchg(float[] b) throws PAModelException
	{
		_src.setToBchg(b);
	}
	
	@Override
	public float getShift(int ndx) throws PAModelException
	{
		return _src.getShift(ndx);
	}

	@Override
	public void setShift(int ndx, float sdeg) throws PAModelException
	{
		_src.setShift(ndx, sdeg);
	}

	@Override
	public float[] getShift() throws PAModelException
	{
		return _src.getShift();
	}

	@Override
	public void setShift(float[] sdeg) throws PAModelException
	{
		_src.setShift(sdeg);
	}

	@Override
	public ACBranch get(int index)
	{
		return _src.get(index);
	}

	@Override
	public int size()
	{
		return _src.size();
	}

	@Override
	public float getLTRating(int ndx) throws PAModelException
	{
		return _src.getLTRating(ndx);
	}

	@Override
	public float[] getLTRating() throws PAModelException
	{
		return _src.getLTRating();
	}

	@Override
	public void setLTRating(int ndx, float mva) throws PAModelException
	{
		_src.setLTRating(ndx, mva);
	}

	@Override
	public void setLTRating(float[] mva) throws PAModelException
	{
		_src.setLTRating(mva);
	}

	@Override
	public ACBranch getByID(String id) throws PAModelException
	{
		return _src.getByID(id);
	}

	@Override
	public boolean objEquals(int ndx, Object obj)
	{
		return _src.objEquals(ndx, obj);
	}

	@Override
	public int objHash(int ndx)
	{
		return _src.objHash(ndx);
	}

	@Override
	public Set<ColumnMeta> getColTypes()
	{
		return _src.getColTypes();
	}
	
	/**
	 * return the unwrapped version of the list
	 * @return
	 */
	public 	ACBranchListIfc<? extends ACBranch> getBaseList() {return _src;}
}
