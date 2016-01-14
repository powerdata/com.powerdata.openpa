package com.powerdata.openpa.impl;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import com.powerdata.openpa.ACBranch;
import com.powerdata.openpa.ACBranchList;
import com.powerdata.openpa.ACBranchListIfc;
import com.powerdata.openpa.BaseList;
import com.powerdata.openpa.BaseObject;
import com.powerdata.openpa.FixedShunt;
import com.powerdata.openpa.FixedShuntList;
import com.powerdata.openpa.FixedShuntListIfc;
import com.powerdata.openpa.OneTermDev;
import com.powerdata.openpa.OneTermDevList;
import com.powerdata.openpa.OneTermDevListIfc;
import com.powerdata.openpa.PALists;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.TwoTermDev;
import com.powerdata.openpa.TwoTermDevList;
import com.powerdata.openpa.TwoTermDevListIfc;

public class SuperList
{
	PALists _lists;

	class Member<T extends BaseList<? extends BaseObject>>
	{
		Set<Method> _m;

		Member(Set<Method> m)
		{
			_m = m;
		}
		
		@SuppressWarnings("unchecked")
		List<T> getList() throws PAModelException
		{
			List<T> rv = new ArrayList<>();
			try
			{
				for (Method m : _m)
				{
					rv.add((T) m.invoke(_lists, new Object[] {}));
				}
			}
			catch (ReflectiveOperationException | IllegalArgumentException e)
			{
				throw new PAModelException(e);
			}			
			return rv;
		}
	}
	
	WeakReference<List<TwoTermDevList>> _l2   = new WeakReference<>(null);
	WeakReference<List<OneTermDevList>> _l1   = new WeakReference<>(null);
	WeakReference<List<ACBranchList>>   _lac  = new WeakReference<>(null);
	WeakReference<List<FixedShuntList>> _lfsh = new WeakReference<>(null);
	
	public SuperList(PALists lists)
	{
		_lists = lists;
	}
	
	<T extends BaseList<? extends BaseObject>> Member<T> loadMethods(Class<?> clz) 
			throws PAModelException
	{
		Member<T> m = null;
		Set<Method> ms = new HashSet<>();
		for(Method mt : _lists.getClass().getMethods())
		{
			Class<?> rt = mt.getReturnType();
			if (testInterfaces(clz, rt))
			{
				ms.add(mt);
				m = new Member<T>(ms);
			}
		}
		return m;
	}
	
	<T extends BaseList<? extends BaseObject>, U extends BaseList<? extends BaseObject>> 
	List<U> wrap(List<T> slist, Function<T, U> alloc)
{
	List<U> rv = new ArrayList<>(slist.size());
	for (T l : slist)
		rv.add(alloc.apply(l));
	return rv;
}


	public List<OneTermDevList> getOneTermDevs() throws PAModelException
	{
		List<OneTermDevList> rv = _l1.get();
		if (rv == null)
		{
			Member<OneTermDevListIfc<? extends OneTermDev>> m =
				loadMethods(OneTermDevListIfc.class);
			rv = wrap(m.getList(), OneTermDevList::new);
			_l1 = new WeakReference<>(rv);
		}
		return rv;
	}
	
	public List<TwoTermDevList> getTwoTermDevs() throws PAModelException
	{
		List<TwoTermDevList> rv = _l2.get();
		if (rv == null)
		{
			Member<TwoTermDevListIfc<? extends TwoTermDev>> m = loadMethods(TwoTermDevListIfc.class);
			rv = wrap(m.getList(), TwoTermDevList::new);
			_l2 = new WeakReference<>(rv);
		}
		return rv;
	}
	
	public List<ACBranchList> getACBranches() throws PAModelException
	{
		List<ACBranchList> rv = _lac.get();
		if (rv == null)
		{
			Member<ACBranchListIfc<? extends ACBranch>> m = loadMethods(ACBranchListIfc.class);
			rv = wrap(m.getList(), ACBranchList::new);
			_lac = new WeakReference<>(rv);
		}
		return rv;
	}
	
	public List<FixedShuntList> getFixedShunts() throws PAModelException
	{
		List<FixedShuntList> rv = _lfsh.get();
		if (rv == null)
		{
			Member<FixedShuntListIfc<? extends FixedShunt>> m =
				loadMethods(FixedShuntListIfc.class);
			rv = wrap(m.getList(), FixedShuntList::new);
			_lfsh = new WeakReference<>(rv);
		}
		return rv;
	}
	
	boolean testInterfaces(Class<?> clz, Class<?> returnType)
	{
		if (returnType.equals(clz))
			return true;
		else if (returnType.equals(BaseList.class))
			return false;
		else
		{
			for (Class<?> pifc : returnType.getInterfaces())
				if (testInterfaces(clz, pifc)) return true;
		}
		return false;
	}

}
