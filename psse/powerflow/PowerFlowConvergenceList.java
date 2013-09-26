package com.powerdata.openpa.psse.powerflow;

import java.util.AbstractList;
import java.util.Arrays;

public class PowerFlowConvergenceList extends AbstractList<PowerFlowConvergence>
{
	int[] _worstpbus, _worstqbus, _niter;
	float[] _worstpmm, _worstqmm;
	boolean[] _conv;
	int _size;

	public PowerFlowConvergenceList(int size)
	{
		/* setup results arrays */
		_worstpbus = new int[size];
		_worstqbus = new int[size];
		_niter = new int[size];
		_worstpmm = new float[size];
		_worstqmm = new float[size];
		_conv = new boolean[size];
		_size = size;
		
		Arrays.fill(_worstpbus, -1);
		Arrays.fill(_worstqbus, -1);
	}
	
	@Override
	public PowerFlowConvergence get(int index)
	{
		return new PowerFlowConvergence(this, index);
	}

	@Override
	public int size()
	{
		return _size;
	}

	public void setWorstPbus(int ndx, int worstp) {_worstpbus[ndx] = worstp;}
	public void setWorstQbus(int ndx, int worstq) {_worstqbus[ndx] = worstq;}
	public void setWorstPmm(int ndx, float mm) {_worstpmm[ndx] = mm;}
	public void setWorstQmm(int ndx, float mm) {_worstqmm[ndx] = mm;}
	public void setConverged(int ndx, boolean conv) {_conv[ndx] = conv;}
	public boolean getConverged(int ndx) {return _conv[ndx];}
	public int getWorstPbus(int ndx) {return _worstpbus[ndx];} 
	public float getWorstPmm(int ndx) {return _worstpmm[ndx];}
	public int getWorstQbus(int ndx) {return _worstqbus[ndx];}
	public float getWorstQmm(int ndx) {return _worstqmm[ndx];}
	public int getIterationCount(int ndx) {return _niter[ndx];}
	public void setIterationCount(int ndx, int itercnt) {_niter[ndx] = itercnt;}
}