package com.powerdata.openpa.psse.csv;

import com.powerdata.openpa.psse.ACBranch;
import com.powerdata.openpa.psse.ACBranchList;
import com.powerdata.openpa.psse.BusList;
import com.powerdata.openpa.psse.Gen;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.psse.PsseModel;
import com.powerdata.openpa.tools.LinkNet;

public class TP
{
	int[] _bus2island;
	boolean[] _energized;
	int[][] _groups;
	
	public TP(PsseModel model) throws PsseModelException
	{
		LinkNet net = configureNetwork(model);
		_groups = net.findGroups();
		_energized = new boolean[_groups.length];
		for(Gen g : model.getGenerators())
		{
			if (g.isInSvc() && g.getPG() > 1)
			{
				int busndx = g.getBus().getIndex();
				int island = _bus2island[busndx];
				if (!_energized[island]) _energized[island] = true;
			}
		}
	}

	LinkNet configureNetwork(PsseModel model) throws PsseModelException
	{
		BusList buses = model.getBuses();
		ACBranchList branches = model.getBranches();
		LinkNet rv = new LinkNet();
		int nbr = branches.size();
		rv.ensureCapacity(buses.getI(buses.size()-1), nbr);
		for(int i=0; i < nbr; ++i)
		{
			ACBranch b = branches.get(i);
			if (b.isInSvc())
			{
				rv.addBranch(b.getFromBus().getIndex(), b.getToBus().getIndex());
			}
		}

		return rv;
	}
	
	/**
	 * Return the island number of a node (connectivity node or topological node)
	 * @param node
	 * @return
	 */
	public int getIsland(int bus)
	{
		return _bus2island[bus];
	}
	
	/**
	 * Return the number of islands.
	 * @return
	 */
	public int getIslandCount() {return _groups.length;}

	/**
	 * Return the energization status of an island.
	 * @param island
	 * @return
	 * @throws PsseModelException
	 */
	public boolean isIslandEnergized(int island) throws PsseModelException
	{
		return _energized[island];
	}

	public int[] getIslandNodes(int island)
	{
		return _groups[island];
	}
	
}
