package com.powerdata.openpa.pwrflow;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.powerdata.openpa.ACBranchList;
import com.powerdata.openpa.ListMetaType;
import com.powerdata.openpa.InService;
import com.powerdata.openpa.PAModel;
import com.powerdata.openpa.PAModelException;

public class ContingencySet extends AbstractSet<com.powerdata.openpa.pwrflow.ContingencySet.Contingency>
{
	public interface Contingency
	{
		/** execute the contingency against the given model */
		void execute(PAModel cmodel) throws PAModelException;
		String getName();
		InService getContObj();
	}
	
	@FunctionalInterface 
	interface EquipOOS
	{
		void set(PAModel m, int idx) throws PAModelException;
	}
	
	static protected Map<ListMetaType,EquipOOS> _Accessors = new EnumMap<>(ListMetaType.class);
	static
	{
		_Accessors.put(ListMetaType.Gen, (m,i) -> m.getGenerators().setInService(i, true));
		_Accessors.put(ListMetaType.SVC, (m,i) -> m.getSVCs().setInService(i, true));
		_Accessors.put(ListMetaType.TwoTermDCLine, (m,i) -> m.getTwoTermDCLines().setInService(i, true));
		_Accessors.put(ListMetaType.Line, (m,i) -> m.getLines().setInService(i, true));
		_Accessors.put(ListMetaType.SeriesCap, (m,i) -> m.getSeriesCapacitors().setInService(i, true));
		_Accessors.put(ListMetaType.SeriesReac, (m,i) -> m.getSeriesReactors().setInService(i, true));
		_Accessors.put(ListMetaType.PhaseShifter, (m,i) -> m.getPhaseShifters().setInService(i, true));
		_Accessors.put(ListMetaType.Transformer, (m,i) -> m.getTransformers().setInService(i, true));
	}
	
	static class ContImpl implements Contingency
	{
		InService _cobj;
		ContImpl(InService cobj)
		{
			_cobj = cobj;
		}
		@Override
		public void execute(PAModel cmodel) throws PAModelException
		{
			_Accessors.get(_cobj.getList().getListMeta()).set(cmodel, _cobj.getIndex());
		}
		
		@Override
		public String toString()
		{
			return getName();
		}
		@Override
		public String getName()
		{
			String rv=null;
			try
			{
				ListMetaType lt = _cobj.getList().getListMeta();
				rv = String.format("%s %s", lt.toString(), _cobj.getID());
			}
			catch (PAModelException e)
			{
				e.printStackTrace();
			}
			return rv;
		}
		@Override
		public InService getContObj()
		{
			return _cobj;
		}
	}

	
	Set<InService> _oos = new HashSet<>();
	
	public ContingencySet(PAModel m) throws PAModelException
	{
		for(ACBranchList br : m.getACBranches())
			addInService(br);
		addInService(m.getGenerators());
	}

	private void addInService(List<? extends InService> ooslist) throws PAModelException
	{
		for (InService o : ooslist)
		{
			if (o.isInService()) _oos.add(o);
		}
	}
	
	/**
	 * Create a set of contingencies from a collection of equipment lists
	 * @param m OpenPAModel  
	 * @param list
	 * @throws PAModelException
	 */
	public ContingencySet(Collection<? extends InService> oos)
			throws PAModelException
	{
			for (InService o : oos)
			{
				if (o.isInService()) _oos.add(o);
			}
	}
	
	@Override
	public Iterator<Contingency> iterator()
	{
		return new Iterator<Contingency>()
		{
			Iterator<InService> _i = _oos.iterator();
			@Override
			public boolean hasNext()
			{
				return _i.hasNext();
			}
			@Override
			public Contingency next()
			{
				return new ContImpl(_i.next());
			}
		};
	}
	@Override
	public int size()
	{
		return _oos.size();
	}
}
