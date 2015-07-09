package com.powerdata.openpa.pwrflow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import com.powerdata.openpa.BusList;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.pwrflow.ACBranchExtList.ACBranchExt;
import com.powerdata.openpa.tools.LinkNet;

/**
 * Associate the network adjacency with PAModel / ACBranches.
 * 
 * This is a specialized subclass  of LinkNet that adds ACBranches from a list. Parallel
 * branches are merged, and the merge operations are tracked in order to map
 * both directions
 * 
 * @author chris@powerdata.com
 *
 */
public class ACBranchAdjacencies extends LinkNet
{
	/**
	 * Provide a hook to build the matrix elements in the same processing loop as 
	 * building the adjacencies
	 * 
	 * @author chris@powerdata.com
	 *
	 */
	public static abstract class MatrixElementBuilder
	{
		protected float[] _bdiag, _boffdiag;
		
		public MatrixElementBuilder(int nbus, int nbranch)
		{
			_bdiag = new float[nbus];
			_boffdiag = new float[nbranch];
		}
		
		public abstract void build(ACBranchExt branch, int branchIndex, int frombus, int tobus)
			throws PAModelException;
		
		public float[] getBDiag() {return _bdiag;}
		public float[] getBOffDiag() {return _boffdiag;}
	}
	
	static class Info
	{
		List<? extends ACBranchExt> _list;
		int _startOffset;
		public Info(List<? extends ACBranchExt> list, int startOffset)
		{
			_list = list;
			_startOffset = startOffset;
		}
	}

	/** track the branch order */
	List<Info> _lists = new ArrayList<>();
	
	public ACBranchAdjacencies(
			Collection<? extends List<? extends ACBranchExt>> branch_lists,
			BusList buses, MatrixElementBuilder...elementBuilders) throws PAModelException
	{
		configure(branch_lists, buses, elementBuilders);
	}

	void configure(Collection<? extends List<? extends ACBranchExt>> branch_lists, BusList buses,
			MatrixElementBuilder... elementBuilders) throws PAModelException
	{
		int nbr = branch_lists.stream().mapToInt(i -> i.size()).sum();
		int buscnt = buses.size();
		ensureCapacity(buscnt - 1, nbr * 2);
		int nm = 0;
		for (List<? extends ACBranchExt> list : branch_lists)
		{
			if (!list.isEmpty())
			{
				_lists.add(new Info(list, nm));
				for (ACBranchExt b : list)
				{
					/*
					 * Buses on objects are always connectivity.  For other topologies
					 * use the passed-in buslist to resolve buses.
					 */
//					int fn = buses.getByBus(b.getFromBus()).getIndex();
//					int tn = buses.getByBus(b.getToBus()).getIndex();
					int fn = b.getFromBus().getIndex();
					int tn = b.getToBus().getIndex();
					int br = findBranch(fn, tn);
					if (br == Empty)
						br = addBranch(fn, tn);
					for(MatrixElementBuilder bldr : elementBuilders)
						bldr.build(b, br, fn, tn);
					++nm;
				}
			}
		}
	}
	
//	public ACBranchAdjacencies(PALists model, BusList buses,
//			MatrixElementBuilder... elementBuilders) throws PAModelException
//	{
//		configure(SubLists.getBranchInsvc(model.getACBranches()), buses, elementBuilders);
//	}
//	
	
	
//	public ACBranchAdjacencies(ACBranchAdjacencies<T> src)
//	{
//		super(src);
//		_lists.addAll(src._lists);
//		_gmap = src._gmap;
//	}
//	
//	public Collection<T> getBranches(int brndx)
//	{
//		int[] b = _gmap.get(brndx);
//		int n = b.length;
//		Arrays.sort(b);
//		ArrayList<T> rv = new ArrayList<>(n);
//		List<? extends T> l = _lists.get(0);
//		for(int i=0, j=0, ofs=0; i < n; ++i)
//		{
//			int x = b[i]-ofs;
//			while (x >= l.size())
//			{
//				int nx = l.size();
//				x -= nx;
//				ofs += nx;
//				l = _lists.get(++j);
//			}
//			rv.add(l.get(x));
//		}
//		return rv;
//	}

}
