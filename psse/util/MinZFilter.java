package com.powerdata.openpa.psse.util;

import java.util.List;

import com.powerdata.openpa.psse.ACBranch;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.tools.Complex;

/**
 * Force Reactance a minimum distance away from zero.

 * @author chris@powerdata.com
 *
 */
public class MinZFilter extends ImpedanceFilter
{
	protected float[] _x, _g, _b;
	
	/**
	 * Create a new minimum impedance filter.
	 * @param branches List of branches on which to manipulate reactance
	 * @param minX smallest value of X, such that |X| >= minX
	 * @throws PsseModelException 
	 */
	public MinZFilter(List<? extends ACBranch> branches, float minX) throws PsseModelException
	{
		super(branches);
		int nbr = branches.size();
		_x = new float[nbr];
		_g = new float[nbr];
		_b = new float[nbr];
		for(int i=0; i < nbr; ++i)
		{
			ACBranch branch = branches.get(i);
			float r = branch.getR();
			float x = branch.getX();
			if (Math.abs(x) < minX)
			{
				x = Math.signum(x) * minX;
			}
			Complex y = new Complex(r, x).inv();
			_x[i] = x;
			_g[i] = y.re();
			_b[i] = y.im();
		}
	}

	@Override
	public float getX(int ndx) throws PsseModelException {return _x[ndx];}
	@Override
	public Complex getZ(int ndx) throws PsseModelException {return new Complex(getR(ndx), _x[ndx]);}
	@Override
	public Complex getY(int ndx) throws PsseModelException {return new Complex(_g[ndx], _b[ndx]);}
}
