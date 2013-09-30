package com.powerdata.openpa.psse.powerflow;

/**
 * 
 * @author chris@powerdata.com
 *
 */
public class PowerFlowConvergence
{
	int _ndx;
	PowerFlowConvergenceList _list;
	
	public PowerFlowConvergence(PowerFlowConvergenceList list, int ndx)
	{
		_ndx = ndx;
		_list = list;
	}

	public void setWorstPbus(int worstp) {_list.setWorstPbus(_ndx, worstp);}
	public void setWorstQbus(int worstq) {_list.setWorstQbus(_ndx, worstq);}
	public void setWorstPmm(float mm) {_list.setWorstPmm(_ndx, mm);}
	public void setWorstQmm(float mm) {_list.setWorstQmm(_ndx, mm);}
	public void setConverged(boolean conv) {_list.setConverged(_ndx, conv);}
	public void setIterationCount(int itercnt) {_list.setIterationCount(_ndx, itercnt);}
	public int getIslandNdx() {return _ndx;}
	public boolean getConverged() {return _list.getConverged(_ndx);}

	public int getWorstPbus() {return _list.getWorstPbus(_ndx);}
	public float getWorstPmm() {return _list.getWorstPmm(_ndx);}
	public int getWorstQbus() {return _list.getWorstQbus(_ndx);}
	public float getWorstQmm() {return _list.getWorstQmm(_ndx);}
	public int getIterationCount() {return _list.getIterationCount(_ndx);}
}
