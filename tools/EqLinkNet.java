package com.powerdata.openpa.tools;

import java.util.Arrays;
import java.util.HashSet;

import com.powerdata.openpa.psse.Bus;
import com.powerdata.openpa.psse.OneTermDev;
import com.powerdata.openpa.psse.PsseModel;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.psse.TwoTermDev;

public class EqLinkNet
{
	PsseModel _mdl;
	LinkNet _linknet = new LinkNet();
	long _branches[] = new long[1];
	int _islands[][] = null;
	public EqLinkNet(PsseModel mdl)
	{
		_mdl = mdl;
	}
	public int getBranchCount() { return _linknet.getBranchCount(); }
	public int getMaxBusNdx() { return _linknet.getMaxBusNdx(); }
	public int getConnectionCount(int busNdx) { return _linknet.getConnectionCount(busNdx); }
	public int add(TwoTermDev dev) throws PsseModelException
	{
		int br = _linknet.addBranch(dev.getFromBus().getIndex(), dev.getToBus().getIndex());
		if (br >= _branches.length) _branches = Arrays.copyOf(_branches, _branches.length * 2);
		_branches[br] = EqType.GetID(dev);
		return br;
	}
	public int add(OneTermDev dev) throws PsseModelException
	{
		int br = _linknet.addBranch(dev.getBus().getIndex(), dev.getBus().getIndex());
		if (br >= _branches.length) _branches = Arrays.copyOf(_branches, _branches.length * 2);
		_branches[br] = EqType.GetID(dev);
		return br;		
	}
	public BaseObject getBranch(int br) throws PsseModelException
	{
		BaseObject eq = EqType.getObject(_mdl, _branches[br]);
		return eq;
	}
	public BaseObject findBranch(Bus from, Bus to) throws PsseModelException
	{
		int br = _linknet.findBranch(from.getIndex(), to.getIndex());
		if (br >= 0) return getBranch(br);
		return null;
	}
	public BaseObject[] findBranches(Bus bus) throws PsseModelException
	{
		int br[] = _linknet.findBranches(bus.getIndex());
		BaseObject eq[] = new BaseObject[br.length];
		for(int i=0; i<br.length; i++) eq[i] = EqType.getObject(_mdl, _branches[br[i]]);
		return eq;
	}
	public Bus[] findBuses(Bus bus) throws PsseModelException
	{
		int buses[] = _linknet.findBuses(bus.getIndex());
		Bus eq[] = new Bus[buses.length];
		for(int i=0; i<buses.length; i++) eq[i] = _mdl.getBuses().get(buses[i]);
		return eq;
	}
	public Bus[] getAllBuses() throws PsseModelException
	{
		int buses[] = _linknet.getAllBuses();
		Bus eq[] = new Bus[buses.length];
		for(int i=0; i<buses.length; i++) eq[i] = _mdl.getBuses().get(buses[i]);
		return eq;		
	}
	public int[][] getIslands()
	{
		return _linknet.findGroups();
	}
	public int getIslandCount()
	{
		if (_islands == null) _islands = _linknet.findGroups();
		return _islands.length;
	}
	public Bus[] getIslandBuses(int island) throws PsseModelException
	{
		if (_islands == null) _islands = _linknet.findGroups();
		int buses[] = _islands[island];
		Bus eq[] = new Bus[buses.length];
		for(int i=0; i<buses.length; i++) eq[i] = _mdl.getBuses().get(buses[i]);
		return eq;				
	}
	public BaseObject[] getIslandEquipment(int island) throws PsseModelException
	{
		if (_islands == null) _islands = _linknet.findGroups();
		HashSet<Long> br = new HashSet<>();
		// get a single list of all branches
		for(int bus : _islands[island])
		{
			for(int b : _linknet.findBranches(bus))
			{
				br.add(_branches[b]);
			}
		}
		BaseObject eq[] = new BaseObject[br.size()];
		Long ids[] = br.toArray(new Long[0]);
		for(int i=0; i<eq.length; i++) eq[i] = EqType.getObject(_mdl, ids[i]);
		return eq;				
	}
}
