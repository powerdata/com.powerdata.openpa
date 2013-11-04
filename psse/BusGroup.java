package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.LinkNet;

/**
 * Create connectivity-based groups of buses
 * 
 * @author chris@powerdata.com
 *
 */
public abstract class BusGroup
{
	/** from bus index to group index */
	int[]	_busgrp;
	/** from group index to representative Bus */
	int[]	_grpbus;
	
	public BusGroup(PsseModel model, GroupBuilder gbld) throws PsseModelException
	{
		LinkNet gnet = gbld.build(model);
		int[][] groups = gnet.findGroups();
		int ngrp = groups.length, nbus = model.getBuses().size();
		_busgrp = new int[nbus];
		_grpbus = new int[ngrp];
		for(int igrp=0; igrp < ngrp; ++igrp)
		{
			int[] grp = groups[igrp];
			for(int b : grp) _busgrp[b] = igrp;
			_grpbus[igrp] = grp[0];
		}
	}
	
	public int size() {return _busgrp.length;}
	
	/** return the offset of the group of which this bus is a member */
	public int getGroupOfs(int busndx)
	{
		return _busgrp[busndx];
	}
	
	public int getGroupBusNdx(int busndx)
	{
		return _grpbus[_busgrp[busndx]];
	}
}
