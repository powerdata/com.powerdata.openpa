package com.powerdata.openpa.psse.util;

import java.util.AbstractList;
import java.util.List;

import com.powerdata.openpa.psse.ACBranch;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.tools.Complex;

/** Allow manipulation of impedance values */
public class ImpedanceFilter extends AbstractList<Complex>
{
	List<? extends ACBranch> _branches;
	
	public ImpedanceFilter(List<? extends ACBranch> branches)
	{
		_branches = branches;
	}


	@Override
	public int size() {return _branches.size();}
	@Override
	public Complex get(int ndx)
	{
		Complex rv = null;
		try
		{
			rv = getZ(ndx);
		} catch (PsseModelException e)
		{
			e.printStackTrace();
		}
		return rv;
	}
	
	public float getR(int ndx) throws PsseModelException {return _branches.get(ndx).getR();}
	public float getX(int ndx) throws PsseModelException {return _branches.get(ndx).getX();}
	public Complex getZ(int ndx) throws PsseModelException {return _branches.get(ndx).getZ();}
	public Complex getY(int ndx) throws PsseModelException {return _branches.get(ndx).getY();}
	
	
}
