package com.powerdata.openpa.pwrflow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.TwoTermBaseList.TwoTermBase;
import com.powerdata.openpa.impl.GroupMap;
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
public class ACBranchAdjacencies<T extends TwoTermBase> extends LinkNet
{
	List<List<? extends T>> _lists = new ArrayList<>();
	GroupMap _gmap;
	
	public ACBranchAdjacencies(Collection<? extends List<? extends T>> branch_lists, int buscnt) throws PAModelException
	{
		int nbr = branch_lists.stream().mapToInt(i -> i.size()).sum();
		ensureCapacity(buscnt-1, nbr*2);
		
		int[] map = new int[nbr];
		int nm = 0;
		Arrays.fill(map, -1);
		
		for (List<? extends T> list : branch_lists)
		{
			if (!list.isEmpty())
			{
				_lists.add(list);
				for (TwoTermBase b : list)
				{
					int fn = b.getFromBus().getIndex(), tn = b.getToBus()
							.getIndex(), br = findBranch(fn, tn);
					if (br == Empty)
					{
						br = addBranch(fn, tn);
					}
					map[nm++] = br;
				}
			}
		}
		_gmap = new GroupMap(map, nm);
	}
	public ACBranchAdjacencies(ACBranchAdjacencies<T> src)
	{
		super(src);
		_lists.addAll(src._lists);
		_gmap = src._gmap;
	}
	
	public Collection<T> getBranches(int brndx)
	{
		int[] b = _gmap.get(brndx);
		int n = b.length;
		Arrays.sort(b);
		ArrayList<T> rv = new ArrayList<>(n);
		List<? extends T> l = _lists.get(0);
		for(int i=0, j=0, ofs=0; i < n; ++i)
		{
			int x = b[i]-ofs;
			while (x >= l.size())
			{
				int nx = l.size();
				x -= nx;
				ofs += nx;
				l = _lists.get(++j);
			}
			rv.add(l.get(x));
		}
		return rv;
	}

}
