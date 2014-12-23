package com.powerdata.openpa.pwrflow;

import java.util.List;
import com.powerdata.openpa.ACBranch;
import com.powerdata.openpa.Bus;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.TwoTermBaseList;
import com.powerdata.openpa.tools.Complex;

/**
 * Interface to extend AC Branch with some additional tweaks
 * <ul>
 * <li>Complex admittance</li>
 * <li>From- and To-side buses with possibly-adjusted topology. These can
 * potentially return Bus objects either with a full connectivity model, or a
 * reduced single-bus model depending on parameters at construction time. It is
 * expected that the user tracks its use. Asking for buses directly from the
 * ACBranch object will always yield connectvity nodes</li>
 * </ul>
 * 
 * @author chris@powerdata.com
 *
 * @param <T> ACBranchExt or subclass of list items
 */
public interface ACBranchExtList<T extends com.powerdata.openpa.pwrflow.ACBranchExtList.ACBranchExt>
		extends TwoTermBaseList<T>
{
	public static class ACBranchExt implements TwoTermBase
	{
		int _ndx;
		ACBranchExtList<? extends ACBranchExt> _list;
		public ACBranchExt(ACBranchExtList<? extends ACBranchExt> list, int index)
		{
			_list = list;
			_ndx = index;
		}
		public Complex getY() {return _list.getY(_ndx);}
		@Override
		public Bus getFromBus() throws PAModelException
		{
			return _list.getFromBus(_ndx);
		}
		@Override
		public Bus getToBus() throws PAModelException
		{
			return _list.getToBus(_ndx);
		}
		public ACBranch getBranch()
		{
			return _list.getBranch(_ndx);
		}
		@Override
		public int getIndex()
		{
			return _ndx;
		}
	}

	/** Return branch admittance on system (100MVA) base */
	Complex getY(int ndx);
	
	/** Return branch admittance on system (100MVA) base */
	List<Complex> getY();
	
	/**
	 * Return from-side bus. Can either be on connectivity- or single-bus
	 * topology depending on configuration specified at construction
	 * 
	 * @param ndx
	 *            Branch index
	 * @return from-side bus. Can either be on connectivity- or single-bus
	 *         topology depending on configuration specified at construction
	 * @throws PAModelException
	 */
	@Override
	Bus getToBus(int ndx) throws PAModelException;

	/**
	 * Return to-side bus. Can either be on connectivity- or single-bus
	 * topology depending on configuration specified at construction
	 * 
	 * @param ndx
	 *            Branch index
	 * @return to-side bus. Can either be on connectivity- or single-bus
	 *         topology depending on configuration specified at construction
	 * @throws PAModelException
	 */
	@Override
	Bus getFromBus(int ndx) throws PAModelException;
	
	ACBranch getBranch(int ndx);
}
