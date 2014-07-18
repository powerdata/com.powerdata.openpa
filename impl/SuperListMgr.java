package com.powerdata.openpa.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import com.powerdata.openpa.BaseList;
import com.powerdata.openpa.BaseObject;
import com.powerdata.openpa.ListMetaType;
import com.powerdata.openpa.PALists;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.tools.SNdxKeyOfs;

public abstract class SuperListMgr<T extends BaseObject, U extends BaseList<T>>
		extends AbstractBaseList<T>
{
	protected class Member
	{
		protected int startidx, endidx;
		protected U list;
		Member(int startidx, int endidx, U list)
		{
			this.startidx = startidx;
			this.endidx = endidx;
			this.list = list;
		}
		@Override
		public String toString()
		{
			return String.format("[%s %d %d]", list.getMetaType().toString(),
				startidx, endidx);
		}
		
	}
	List<Member> _members = new ArrayList<>();

	protected class ListInfo
	{
		public U list;
		public int ofs;
		public ListInfo(int rawofs)
		{
			for (Member m : _members)
			{
				int ms = m.endidx;
				if (rawofs < ms)
				{
					list = m.list;
					ofs = rawofs - m.startidx;
					return;
				}
			}
			throw new IndexOutOfBoundsException(String.valueOf(rawofs));
		}
		@Override
		public String toString()
		{
			return String.format("[%s %d]", list.getMetaType().toString(), ofs);
		}
		
	}
	@SuppressWarnings("unchecked")
	protected SuperListMgr(PALists lists, Class<?> clz) throws PAModelException
	{
		try
		{
			for (Method m : lists.getClass().getDeclaredMethods())
			{
				Class<?> rt = m.getReturnType();
				if (!rt.equals(this.getClass()) && testMethod(clz, rt))
				{
					U list;
					list = (U) m.invoke(lists, new Object[] {});
					if (!list.isEmpty())
					{
						int os = _size;
						_size += list.size();
						_members.add(new Member(os, _size, list));
					}
				}
			}
		}
		catch (ReflectiveOperationException | IllegalArgumentException e)
		{
			throw new PAModelException(e);
		}
		int[] keys = new int[_size];
		for (Member m : _members)
		{
			U list = m.list;
			System.arraycopy(list.getKeys(), 0, keys, m.startidx, list.size());
		}
		_keyndx = SNdxKeyOfs.Create(keys);
	}
	protected <V> V getHelper(IntFunction<V> alloc, Function<U, V> p)
	{
		V rv = alloc.apply(_size);
		for (Member m : _members)
		{
			U list = m.list;
			System.arraycopy(p.apply(list), 0, rv, m.startidx, list.size());
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
			System.arraycopy(upd, m.startidx, lu, 0, n);
			set.accept(list, lu);
		}
	}
	private boolean testMethod(Class<?> clz, Class<?> returnType)
	{
		if (returnType.equals(clz))
			return true;
		else if (returnType.equals(BaseList.class))
			return false;
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
	@Override
	public ListMetaType getMetaType()
	{
		return ListMetaType.SuperList;
	}
	
}