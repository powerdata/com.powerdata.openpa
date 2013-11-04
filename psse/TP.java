package com.powerdata.openpa.psse;

import java.util.Arrays;

import com.powerdata.openpa.psse.ACBranch;
import com.powerdata.openpa.psse.ACBranchList;
import com.powerdata.openpa.psse.BusList;
import com.powerdata.openpa.psse.Gen;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.psse.PsseModel;
import com.powerdata.openpa.tools.LinkNet;

@Deprecated
public class TP
{
	int[] _bus2island;
	boolean[] _energized;
	int[][] _groups;
	BusTypeCode[] _bustype;
	int[] _arefbyisland;
	int[] _loadbus, _genbus, _arefbus; 
	int[][] _pqbyisland, _pvbyisland;
	
	public TP(PsseModel model) throws PsseModelException
	{
		BusList buses = model.getBuses();
		int nbus = buses.size();
		LinkNet net = configureNetwork(model);
		_groups = net.findGroups();
		int nisland = _groups.length;
		
		_energized = new boolean[nisland];
		_arefbyisland = new int[nisland];
		Arrays.fill(_arefbyisland, -1);
		_bustype = new BusTypeCode[nbus];
		Arrays.fill(_bustype, BusTypeCode.Unknown);
		float[] maxgen = new float[nbus];

		_bus2island = new int[nbus];
		Arrays.fill(_bus2island, -1);
		for (int igrp=0; igrp < _groups.length; ++igrp)
		{
			for(int gbus : _groups[igrp])
			{
				_bus2island[gbus] = igrp;
			}
		}
		
		for(int i=0; i < nbus; ++i)
		{
			if (net.getConnectionCount(i)==0)
			{
				_bustype[i] = BusTypeCode.Isolated;
			}
		}

		int egycnt = 0;
		for(Gen g : model.getGenerators())
		{
			if (g.isInSvc())
			{
				int busndx = g.getBus().getIndex();
				int island = _bus2island[busndx];
				//TODO:  should islands be able to be -1?
				if (island != -1 && !_energized[island])
				{
					_energized[island] = true;
					++egycnt;
				}
				if ((g.getQT() - g.getQB()) > 1f
						&& _bustype[busndx] == BusTypeCode.Unknown)
				{
					_bustype[busndx] = BusTypeCode.Gen;
					maxgen[busndx] += g.getPT();
					if (_arefbyisland[island] == -1 || maxgen[busndx] > maxgen[_arefbyisland[island]])
							_arefbyisland[island] = busndx;
				}
			}
		}
//		_arefbyisland[0] = 43;
		
		int[] genbus = new int[nbus];
		int[] loadbus = new int[nbus];
		int ngen=0, nload=0, ihot=0;
		_arefbus = new int[egycnt];
		for (int i=0; i < nbus; ++i)
		{
			int island = _bus2island[i];
			
			if (island > -1 && _energized[island])
			{
				if (_bustype[i] == BusTypeCode.Gen)
				{
					if (_arefbyisland[island] != i)
					{
						genbus[ngen++] = i;
					}
					else
					{
						_arefbus[ihot++] = i;
						_bustype[i] = BusTypeCode.Slack;
					}
				}
				else
				{
					_bustype[i] = BusTypeCode.Load;
					loadbus[nload++] = i;
				}
			}
		}
		_genbus = Arrays.copyOf(genbus, ngen);
		_loadbus = Arrays.copyOf(loadbus, nload);
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

	public BusTypeCode getBusType(int bus)
	{
		return _bustype[bus];
	}

	public int[] getBusNdxsForType(BusTypeCode bustype)
	{
		switch(bustype)
		{
			case Load: return _loadbus;
			case Gen: return _genbus;
			case Slack: return _arefbus;
			default: return new int[0];
		}
	}
	
	public int[] getBusNdxsForType(int islandndx, BusTypeCode bustype)
	{
		if (_energized[islandndx])
		{
			if (_pqbyisland == null)
			{
				analyzeIslandBustypes();
			}
			switch(bustype)
			{
				case Load: return _pqbyisland[islandndx];
				case Gen: return _pvbyisland[islandndx];
				case Slack: return new int[] {_arefbyisland[islandndx]};
				default: return new int[0];
			}
		}
		else
		{
			return new int[0];
		}
	}

	void analyzeIslandBustypes()
	{
		int nisland = _groups.length;
		
		_pqbyisland = new int[nisland][];
		_pvbyisland = new int[nisland][];
		int[] nld = new int[nisland], ngen = new int[nisland];
		
		for(int ig=0; ig < nisland; ++ig)
		{
			int[] grp = _groups[ig];
			int ng = grp.length;
			_pqbyisland[ig] = new int[ng];
			_pvbyisland[ig] = new int[ng];
		}

		for(int gb : _genbus)
		{
			int ix = _bus2island[gb];
			_pvbyisland[ix][ngen[ix]++] = gb;
		}
		
		for(int lb : _loadbus)
		{
			int ix = _bus2island[lb];
			_pqbyisland[ix][nld[ix]++] = lb;
		}
		
		for(int ig=0; ig < nisland; ++ig)
		{
			_pvbyisland[ig] = Arrays.copyOf(_pvbyisland[ig], ngen[ig]);
			_pqbyisland[ig] = Arrays.copyOf(_pqbyisland[ig], nld[ig]);
		}
	}

	public int getAngleRefBusNdx(int ndx)
	{
		return _arefbyisland[ndx];
	}
	
}
