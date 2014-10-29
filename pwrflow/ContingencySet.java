package com.powerdata.openpa.pwrflow;

import gnu.trove.list.array.TIntArrayList;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import com.powerdata.openpa.ACBranchList;
import com.powerdata.openpa.ListMetaType;
import com.powerdata.openpa.OutOfService;
import com.powerdata.openpa.OutOfServiceList;
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
		ListInfo _li;
		OutOfService _cobj;
		ContImpl(ListInfo li, OutOfService cobj)
		{
			_li = li;
			_cobj = cobj;
		}
		@Override
		public void execute(PAModel cmodel) throws PAModelException
		{
			_Accessors.get(_li.getType()).set(cmodel, _cobj.getIndex());
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
				rv = String.format("%s %s", _li.getType().toString(), _cobj.getID());
			}
			catch (PAModelException e)
			{
				// TODO Auto-generated catch block
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

	
	int _size = 0;
	class ListInfo
	{
		int[] _ofs;
		OutOfServiceList<? extends OutOfService> _list;
		
		ListInfo(int[] ofs, OutOfServiceList<? extends OutOfService> list)
		{
			_ofs = ofs;
			_list = list;
		}

		public ListMetaType getType()
		{
			return _list.getListMeta();
		}
		
		public int[] getOffsets() {return _ofs;}
		
		public OutOfServiceList<? extends OutOfService> getList() {return _list;}

		@Override
		public String toString()
		{
			return String.format("%s %s", getType().toString(), Arrays.toString(_ofs));
		}
		
		
	}
	Map<ListMetaType, ListInfo> _oos = 
			new EnumMap<>(ListMetaType.class);
	PAModel _m;
	
	public ContingencySet(PAModel m) throws PAModelException
	{
//		mapContingencies(m.getGenerators());
		//TODO:  Add in distributed slack to power flow before adding generators
		for(ACBranchList brlist : m.getACBranches())
			mapContingencies(brlist);
	}
	
	void mapContingencies(OutOfServiceList<? extends OutOfService> list) throws PAModelException
	{
		if (!list.isEmpty())
		{
			_oos.put(list.getListMeta(), new ListInfo(mapOOS(list), list));
		}
	}

	int[] mapOOS(OutOfServiceList<? extends OutOfService> list) throws PAModelException
	{
		TIntArrayList r = new TIntArrayList();
		for(OutOfService b : list)
		{
			if (!b.isOutOfSvc())
			{
				r.add(b.getIndex());
				++_size;
			}
		}
		return r.toArray();
	}
	
	@Override
	public Iterator<Contingency> iterator()
	{
		return new Iterator<Contingency>()
		{
			Iterator<Entry<ListMetaType,ListInfo>> _mapi = _oos.entrySet().iterator();
			ListInfo _info = _mapi.next().getValue();
			int _li = 0, _nli = _info.getOffsets().length;
			
			@Override
			public boolean hasNext()
			{
				return (_li < _nli || _mapi.hasNext()); 
			}

			@Override
			public Contingency next()
			{
				if (_li >= _nli)
				{
					_info = _mapi.next().getValue();
					_nli = _info.getOffsets().length;
					_li = 0;
				}
				return new ContImpl(_info, _info.getList().get(_li++));
			}
		};
	}

	@Override
	public int size()
	{
		return _size;
	}
}
