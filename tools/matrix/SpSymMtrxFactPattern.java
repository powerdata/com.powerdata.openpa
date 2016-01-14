package com.powerdata.openpa.tools.matrix;

import java.util.AbstractList;
import java.util.Arrays;
import com.powerdata.openpa.tools.LinkNet;

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
	public class EliminatedNodeList extends AbstractList<EliminatedNode>
	{
		@Override
		public int size() {return _iord;}
		@Override
		public EliminatedNode get(int ndx) {return new EliminatedNode(ndx);}

	};
	
	public class EliminatedNode
	{
		/** Index of this eliminated node within the Eliminated Node List */
		int _ndx;
		
		/**
		 * Create a new Eliminated Node
		 * @param ndx Index of EliminatedNode within EliminatedNOdeList
		 */
		EliminatedNode(int ndx)
		{
			_ndx = ndx;
		}
		
		/**
		 * Get the index of the node within the original order used by the matrix
		 * @return Index of the node within the original order used by the matrix
		 */
		public int getElimNodeNdx() {return _elimndorder[_ndx];}
		/**
		 * 
		 * @return
		 */
		/** 
		 * Get the set of nodes connected to the eliminated node
		 * @return Set of nodes connected to the eliminated node
		 */
		public int[] getRemainingNodes() {return _n[_ndx];}
		/**
		 * Get the set of eliminated edges
		 * @return Set of eliminated edges same order as getRemainingNodes()
		 */
		public int[] getElimEdges() {return _bfr[_ndx];}
		/**
		 * Get the filled-in edges. The order of edges is formed by traversing
		 * the rows of the upper triangle in the "mutual" submatrix
		 * 
		 * @return filled-in edges
		 */
		public int[] getFilledInEdges() {return _btr[_ndx];}

		@Override
		public String toString()
		{
			return String.format("Index=%d, elimpos=%d, connected nd=%s br=%s, target br=%s",
				_elimndorder[_ndx], _ndx,
				Arrays.toString(getRemainingNodes()),
				Arrays.toString(getElimEdges()),
				Arrays.toString(getFilledInEdges()));
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

	public EliminatedNodeList getEliminatedBuses()
	{
		return new EliminatedNodeList();
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

