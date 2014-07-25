package com.powerdata.openpa;

import com.powerdata.openpa.impl.TransformerBase;

public interface TransformerBaseList<T extends TransformerBase> extends ACBranchListIfc<T>
{

	float getGmag(int ndx) throws PAModelException;

	void setGmag(int ndx, float g) throws PAModelException;
	
	float[] getGmag() throws PAModelException;
	
	void setGmag(float[] g) throws PAModelException;

	float getBmag(int ndx) throws PAModelException;

	void setBmag(int ndx, float b) throws PAModelException;
	
	float[] getBmag() throws PAModelException;
	
	void setBmag(float[] b) throws PAModelException;

	float getShift(int ndx) throws PAModelException;

	void setShift(int ndx, float sdeg) throws PAModelException;
	
	float[] getShift() throws PAModelException;
	
	void setShift(float[] sdeg) throws PAModelException;

	float getFromTap(int ndx) throws PAModelException;

	void setFromTap(int ndx, float a) throws PAModelException;
	
	float[] getFromTap() throws PAModelException;
	
	void setFromTap(float[] a) throws PAModelException;

	float getToTap(int ndx) throws PAModelException;

	void setToTap(int ndx, float a) throws PAModelException;
	
	float[] getToTap() throws PAModelException;
	
	void setToTap(float[] a) throws PAModelException;

}
