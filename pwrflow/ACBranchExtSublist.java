package com.powerdata.openpa.pwrflow;

import java.util.AbstractList;
import java.util.List;
import com.powerdata.openpa.ACBranch;
import com.powerdata.openpa.ACBranchListIfc;
import com.powerdata.openpa.Bus;
import com.powerdata.openpa.BusRefIndex;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.pwrflow.ACBranchExtList.ACBranchExt;
import com.powerdata.openpa.tools.Complex;
import com.powerdata.openpa.tools.ComplexSublist;

public class ACBranchExtSublist<T extends ACBranchExt> extends AbstractList<T> implements ACBranchExtList<T>
{
	ACBranchExtList<T> _src;
	int[] _ndx;
	
	public ACBranchExtSublist(ACBranchExtList<T> src, int[] ndx)
	{
		_src = src;
		_ndx = ndx;
	}
	@Override
	public Complex getY(int ndx)
	{
		return _src.getY(_ndx[ndx]);
	}

	@Override
	public List<Complex> getY()
	{
		return new ComplexSublist(_src.getY(), _ndx);
	}

	@Override
	public Bus getToBus(int ndx) throws PAModelException
	{
		return _src.getToBus(_ndx[ndx]);
	}

	@Override
	public Bus getFromBus(int ndx) throws PAModelException
	{
		return _src.getFromBus(_ndx[ndx]);
	}

	@Override
	public T get(int index)
	{
		return _src.get(_ndx[index]);
	}

	@Override
	public int size()
	{
		return _ndx.length;
	}

	@Override
	public ACBranch getBranch(int ndx)
	{
		return _src.getBranch(_ndx[ndx]);
	}
	@Override
	public ACBranchListIfc<? extends ACBranch> getList()
	{
		throw new UnsupportedOperationException("Method not implemented");
	}
	@Override
	public BusRefIndex getBusRefIndex()
	{
		throw new UnsupportedOperationException("Method not implemented");
	}
}
