package com.powerdata.openpa;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import com.powerdata.openpa.tools.SNdxKeyOfs;

public abstract class SuperListMgr<T extends BaseObject, U extends BaseList<T>>
		extends AbstractBaseList<T>
{
	protected class Member
	{
		int maxidx;
		U list;
		Member(int maxidx, U list)
		{
			this.maxidx = maxidx;
			this.list = list;
		}
	}
	List<Member> _members = new ArrayList<>();

	protected class ListInfo
	{
		U list;
		int ofs;
		public ListInfo(int rawofs)
		{
			int ix = rawofs;
			for (Member m : _members)
			{
				int ms = m.maxidx;
				if (ix < ms)
				{
					list = m.list;
					ofs = ix;
					break;
				}
				else
					ix -= ms;
			}
			throw new IndexOutOfBoundsException(String.valueOf(rawofs));
		}
	}
	@SuppressWarnings("unchecked")
	protected SuperListMgr(PALists lists, Class<?> clz)
			throws ReflectiveOperationException, RuntimeException
	{
		for (Method m : lists.getClass().getDeclaredMethods())
		{
			if (testMethod(clz, m.getReturnType()))
			{
				U list = (U) m.invoke(lists, new Object[] {});
				_size += list.size();
				_members.add(new Member(_size, list));
			}
		}
		int[] keys = new int[_size];
		for (Member m : _members)
		{
			U list = m.list;
			System.arraycopy(list.getKeys(), 0, keys, m.maxidx, list.size());
		}
		_keyndx = SNdxKeyOfs.Create(keys);
	}
	protected <V> V getHelper(IntFunction<V> alloc, Function<U, V> p)
	{
		V rv = alloc.apply(_size);
		for (Member m : _members)
		{
			U list = m.list;
			System.arraycopy(p.apply(list), 0, rv, m.maxidx, list.size());
		}
		return rv;
	}
	protected <V> void setHelper(V upd, IntFunction<V> alloc, BiConsumer<U, V> set)
	{
		for (Member m : _members)
		{
			U list = m.list;
			int n = list.size();
			V lu = alloc.apply(n);
			System.arraycopy(upd, m.maxidx, lu, 0, n);
			set.accept(list, lu);
		}
	}
	private boolean testMethod(Class<?> clz, Class<?> returnType)
	{
		if (returnType.equals(clz))
			return true;
		else
		{
			for (Class<?> pifc : returnType.getInterfaces())
				if (testMethod(clz, pifc)) return true;
		}
		return false;
	}
	protected ListInfo getLI(int rawidx)
	{
		return new ListInfo(rawidx);
	}
	@Override
	public int size()
	{
		return _size;
	}
	@Override
	public String getID(int ndx)
	{
		ListInfo li = getLI(ndx);
		return li.list.getID(li.ofs);
	}
	@Override
	public String[] getID()
	{
		return getHelper(String[]::new, l -> l.getID());
	}
	@Override
	public void setID(String[] id)
	{
		setHelper(id, String[]::new, (l,v) -> l.setID(v));
	}
	@Override
	public void setID(int ndx, String id)
	{
		ListInfo li = getLI(ndx);
		li.list.setID(li.ofs, id);
	}
	@Override
	public String getName(int ndx)
	{
		ListInfo li = getLI(ndx);
		return li.list.getName(li.ofs);
	}
	@Override
	public void setName(int ndx, String name)
	{
		ListInfo li = getLI(ndx);
		li.list.setName(li.ofs, name);
	}
	@Override
	public String[] getName()
	{
		return getHelper(String[]::new, l -> l.getName());
	}
	@Override
	public void setName(String[] name)
	{
		setHelper(name, String[]::new, (l,v) -> l.setName(v));
	}
}