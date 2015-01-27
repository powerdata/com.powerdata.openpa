package com.powerdata.openpa.pwrflow;

import java.util.AbstractList;
import com.powerdata.openpa.ACBranch;
import com.powerdata.openpa.ACBranchListIfc;
import com.powerdata.openpa.Bus;
import com.powerdata.openpa.BusRefIndex;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.pwrflow.ACBranchExtList.ACBranchExt;
import com.powerdata.openpa.tools.Complex;
import com.powerdata.openpa.tools.ComplexList;

public class ACBranchExtListI<T extends ACBranchExt> extends
		AbstractList<T> implements ACBranchExtList<T>
{
	ACBranchListIfc<? extends ACBranch> _list;
	ComplexList _y;
	BusRefIndex _bri;
	
	public ACBranchExtListI(ACBranchListIfc<? extends ACBranch> list, 
			BusRefIndex bri) throws PAModelException
	{
		_list = list;
		_bri = bri;
		_y = new ComplexList()
		{
			{
				_v1 = list.getR();
				_v2 = list.getX();
				_size = _v1.length;
			}
		}.inv();
	}
	

	@Override
	public int size() {return _list.size();}
	
	@Override
	public Complex getY(int ndx)
	{
		return _y.get(ndx);
	}
	
	@Override
	public ComplexList getY() {return _y;}

	@Override
	public Bus getFromBus(int ndx) throws PAModelException
	{
		return _bri.getBuses().getByBus(_list.getFromBus(ndx));
	}
	@Override
	public Bus getToBus(int ndx) throws PAModelException
	{
		return _bri.getBuses().getByBus(_list.getToBus(ndx));
	}

	@SuppressWarnings("unchecked")
	@Override
	public T get(int index)
	{
		return (T) new ACBranchExt(this, index);
	}


	@Override
	public ACBranch getBranch(int ndx)
	{
		return _list.get(ndx);
	}	
}
