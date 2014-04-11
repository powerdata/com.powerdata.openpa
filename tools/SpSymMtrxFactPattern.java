package com.powerdata.openpa.tools;

import java.util.AbstractList;
import java.util.Arrays;

/**
 * Symmetric (Sparse) Matrix factorization pattern. Perform elimination and keep
 * the pattern around for re-use.
 * 
 * @author chris@powerdata.com
 * 
 * TODO:  Assess memory footprint of save elimination info and resolve if needed
 * 
 */

public class SpSymMtrxFactPattern extends SpSymMtrxFactorizer
		
{
	public class EliminatedBusList extends AbstractList<EliminatedBus>
	{
		@Override
		public int size() {return _iord;}
		@Override
		public EliminatedBus get(int ndx) {return new EliminatedBus(ndx);}

	};
	
	public class EliminatedBus
	{
		int _ndx;
		EliminatedBus(int ndx)
		{
			_ndx = ndx;
		}
		
		public int getOrder() {return _ndx;}
		public int getElimBusNdx() {return _elimndorder[_ndx];}
		public int[] getRemainingNodes() {return _n[_ndx];}
		public int[] getElimBranches() {return _bfr[_ndx];}
		public int[] getRemainingBranches() {return _btr[_ndx];}

		@Override
		public String toString()
		{
			return String
					.format("Index=%d, elimpos=%d, connected nd=%s br=%s, target br=%s",
							_elimndorder[_ndx], _ndx,
							Arrays.toString(getRemainingNodes()),
							Arrays.toString(getElimBranches()),
							Arrays.toString(getRemainingBranches()));
		}
	}

	int[][] _n;
	int[][] _bfr,_btr;
	int[] _tbr;
	int _itbr;

	@Override
	protected void setup(LinkNet matrix)
	{
		int nbus = matrix.getMaxBusNdx();
		_n = new int[nbus][];
		_btr = new int[nbus][];
		_bfr = new int[nbus][];
	}

	@Override
	protected void finish()
	{
		_n = Arrays.copyOf(_n, _iord);
	}

	public EliminatedBusList getEliminatedBuses()
	{
		return new EliminatedBusList();
	}
	
	@Override
	protected void elimStart(int elimbus, int[] cbus, int[] cbr)
	{
		_n[_iord] = cbus;
		_bfr[_iord] = cbr;
		_itbr = 0;
		int nbus = cbus.length;
		_tbr = new int[nbus*(nbus-1)/2];
	}
	
	@Override
	protected void elimStop()
	{
		_btr[_iord] = (_tbr.length == _itbr) ? _tbr :
			Arrays.copyOf(_tbr, _itbr);
	}

	@Override
	protected void mutual(int imut, int adjbr, int targbr)
	{
		_tbr[_itbr++] = targbr;
	}

	@Override
	protected int ensureCapacity(int newsize)
	{
		return super.ensureCapacity(newsize);
	}

}

