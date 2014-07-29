package com.powerdata.openpa.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import com.powerdata.openpa.BaseList;
import com.powerdata.openpa.BaseObject;
import com.powerdata.openpa.ListMetaType;
import com.powerdata.openpa.PALists;
import com.powerdata.openpa.PAModelException;

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
			return String.format("[%s %d %d]", list.getListMeta().toString(),
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
			return String.format("[%s %d]", list.getListMeta().toString(), ofs);
		}
		
	}
	
	@FunctionalInterface
	protected interface IntFunction<V>
	{
		V apply(int ival) throws PAModelException;
	}
	@FunctionalInterface
	protected interface Function<U,V>
	{
		V apply(U u) throws PAModelException;
	}
	@FunctionalInterface
	protected interface BiConsumer<U,V>
	{
		void accept(U u, V v) throws PAModelException;
	}
	
	@SuppressWarnings("unchecked")
	protected SuperListMgr(PALists lists, Class<?> clz) throws PAModelException
	{
		try
		{
			for (Method m : lists.getClass().getMethods())
			{
				Class<?> rt = m.getReturnType();
				if (!rt.equals(this.getClass()) && testInterfaces(clz, rt) && testNotSuper(rt))
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
	}
	
	boolean testNotSuper(Class<?> rt)
	{
		if (rt == null)
			return true;
		else if (rt.equals(SuperListMgr.class))
			return false;
		else if (rt.equals(BaseList.class))
			return true;
		else return testNotSuper(rt.getSuperclass());
	}
	
	protected <V> V getHelper(IntFunction<V> alloc, Function<U, V> p) throws PAModelException
	{
		V rv = alloc.apply(_size);
		for (Member m : _members)
		{
			U list = m.list;
			System.arraycopy(p.apply(list), 0, rv, m.startidx, list.size());
		}
		return rv;
	}
	protected <V> void setHelper(V upd, IntFunction<V> alloc, BiConsumer<U, V> set) throws PAModelException
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
	public String getID(int ndx) throws PAModelException
	{
		ListInfo li = getLI(ndx);
		return li.list.getID(li.ofs);
	}
	@Override
	public String[] getID() throws PAModelException
	{
		return getHelper(String[]::new, l -> l.getID());
	}
	@Override
	public void setID(String[] id) throws PAModelException
	{
		setHelper(id, String[]::new, (l,v) -> l.setID(v));
	}
	@Override
	public void setID(int ndx, String id) throws PAModelException
	{
		ListInfo li = getLI(ndx);
		li.list.setID(li.ofs, id);
	}
	@Override
	public String getName(int ndx) throws PAModelException
	{
		ListInfo li = getLI(ndx);
		return li.list.getName(li.ofs);
	}
	@Override
	public void setName(int ndx, String name) throws PAModelException
	{
		ListInfo li = getLI(ndx);
		li.list.setName(li.ofs, name);
	}
	@Override
	public String[] getName() throws PAModelException
	{
		return getHelper(String[]::new, l -> l.getName());
	}
	@Override
	public void setName(String[] name) throws PAModelException
	{
		setHelper(name, String[]::new, (l,v) -> l.setName(v));
	}
	@Override
	public ListMetaType getListMeta()
	{
		return ListMetaType.SuperList;
	}

	public ListMetaType getListMeta(int ndx)
	{
		return getLI(ndx).list.getListMeta();
	}
	
	@Override
	public int getKey(int ndx)
	{
		ListInfo li = getLI(ndx);
		return li.list.getKey(li.ofs);
	}

	@Override
	public int[] getKeys()
	{
		int[] rv = new int[_size];
		for(Member m : _members)
		{
			U list = m.list;
			System.arraycopy(list.getKeys(), 0, rv, m.startidx, list.size());
		}
		return rv;
	}

	@Override
	@Nodump
	public T getByKey(int key)
	{
		for(Member m : _members)
		{
			T obj = m.list.getByKey(key);
			if (obj != null) return obj;
		}
		return null;
	}

	@Override
	public int getIndex(int ndx)
	{
		ListInfo li = getLI(ndx);
		return li.list.getIndex(li.ofs);
	}
	
	
	
}