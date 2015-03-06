package com.powerdata.openpa.pwrflow;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import com.powerdata.openpa.ListMetaType;
import com.powerdata.openpa.OutOfService;
import com.powerdata.openpa.PAModel;
import com.powerdata.openpa.PAModelException;

public class ContingencySet extends AbstractSet<com.powerdata.openpa.pwrflow.ContingencySet.Contingency>
{
	public interface Contingency
	{
		/** execute the contingency against the given model */
		void execute(PAModel cmodel) throws PAModelException;
		String getName();
		OutOfService getContObj();
	}
	
	@FunctionalInterface 
	interface EquipOOS
	{
		void set(PAModel m, int idx) throws PAModelException;
	}
	
	static protected Map<ListMetaType,EquipOOS> _Accessors = new EnumMap<>(ListMetaType.class);
	static
	{
		_Accessors.put(ListMetaType.Gen, (m,i) -> m.getGenerators().setOutOfSvc(i, true));
		_Accessors.put(ListMetaType.SVC, (m,i) -> m.getSVCs().setOutOfSvc(i, true));
		_Accessors.put(ListMetaType.TwoTermDCLine, (m,i) -> m.getTwoTermDCLines().setOutOfSvc(i, true));
		_Accessors.put(ListMetaType.Line, (m,i) -> m.getLines().setOutOfSvc(i, true));
		_Accessors.put(ListMetaType.SeriesCap, (m,i) -> m.getSeriesCapacitors().setOutOfSvc(i, true));
		_Accessors.put(ListMetaType.SeriesReac, (m,i) -> m.getSeriesReactors().setOutOfSvc(i, true));
		_Accessors.put(ListMetaType.PhaseShifter, (m,i) -> m.getPhaseShifters().setOutOfSvc(i, true));
		_Accessors.put(ListMetaType.Transformer, (m,i) -> m.getTransformers().setOutOfSvc(i, true));
	}
	
	static class ContImpl implements Contingency
	{
		OutOfService _cobj;
		ContImpl(OutOfService cobj)
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
		public OutOfService getContObj()
		{
			return _cobj;
		}
	}

	
	Set<OutOfService> _oos = new HashSet<>();
	
	public ContingencySet(PAModel m) throws PAModelException
	{
//		mapContingencies(m.getGenerators());
		//TODO:  Add in distributed slack to power flow before adding generators
//		for(ACBranchList brlist : m.getACBranches())
//			mapContingencies(brlist);
		for(OutOfService o : m.getLines())
		{
			if (!o.isOutOfSvc()) _oos.add(o);
		}
	}

	/**
	 * Create a set of contingencies from a collection of equipment lists
	 * @param m OpenPAModel  
	 * @param list
	 * @throws PAModelException
	 */
	public ContingencySet(Collection<? extends OutOfService> oos)
			throws PAModelException
	{
		for(OutOfService o : oos)
		{
			if (!o.isOutOfSvc()) _oos.add(o);
		}
	}
	
	@Override
	public Iterator<Contingency> iterator()
	{
		return new Iterator<Contingency>()
		{
			Iterator<OutOfService> _i = _oos.iterator();
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
