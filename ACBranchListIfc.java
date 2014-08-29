package com.powerdata.openpa;

public interface ACBranchListIfc<T extends ACBranch> extends TwoTermDevListIfc<T>
{

	float getR(int ndx) throws PAModelException;

	void setR(int ndx, float r) throws PAModelException;
	
	float[] getR() throws PAModelException;
	
	void setR(float[] r) throws PAModelException;

	float getX(int ndx) throws PAModelException;
	
	void setX(int ndx, float x) throws PAModelException;

	float[] getX() throws PAModelException;
	
	void setX(float[] x) throws PAModelException;

	float getFromTap(int ndx) throws PAModelException;

	void setFromTap(int ndx, float a) throws PAModelException;
	
	float[] getFromTap() throws PAModelException;
	
	void setFromTap(float[] a) throws PAModelException;

	float getToTap(int ndx) throws PAModelException;

	void setToTap(int ndx, float a) throws PAModelException;
	
	float[] getToTap() throws PAModelException;
	
	void setToTap(float[] a) throws PAModelException;

	float getGmag(int ndx) throws PAModelException;

	void setGmag(int ndx, float g) throws PAModelException;
	
	float[] getGmag() throws PAModelException;
	
	void setGmag(float[] g) throws PAModelException;

	float getBmag(int ndx) throws PAModelException;

	void setBmag(int ndx, float b) throws PAModelException;
	
	float[] getBmag() throws PAModelException;
	
	void setBmag(float[] b) throws PAModelException;

	float getFromBchg(int ndx) throws PAModelException;

	void setFromBchg(int ndx, float b) throws PAModelException;
	
	float[] getFromBchg() throws PAModelException;
	
	void setFromBchg(float[] b) throws PAModelException;

	float getToBchg(int ndx) throws PAModelException;

	void setToBchg(int ndx, float b) throws PAModelException;
	
	float[] getToBchg() throws PAModelException;
	
	void setToBchg(float[] b) throws PAModelException;

	float getShift(int ndx) throws PAModelException;

	void setShift(int ndx, float sdeg) throws PAModelException;
	
	float[] getShift() throws PAModelException;
	
	void setShift(float[] sdeg) throws PAModelException;

	float getLTRating(int ndx) throws PAModelException;
	
	float[] getLTRating() throws PAModelException;

	void setLTRating(int ndx, float mva) throws PAModelException;
	
	void setLTRating(float[] mva) throws PAModelException;
}
