package com.powerdata.openpa;

import java.util.Collections;
import java.util.Set;



/**
 * Anonymous GroupList.  
 * 
 * @author chris@powerdata.com
 *
 */
public class GroupList extends GroupListI<Group>
{
	/**
	 * Create an anonymous GroupList
	 * @param lists Set of lists used to form groups and provide equipment sublists
	 * @param busgrp Map from bus to group
	 */
	public GroupList(PALists lists, GroupIndex busgrp)
	{
		super(lists, busgrp);
	}

	/**
	 * Create an anonymous GroupList with a set of known keys
	 * @param lists Set of lists used to form groups and provide equipment sublists
	 * @param keys Set of keys to assign each group
	 * @param busgrp Map from bus to group
	 */
	public GroupList(PALists model, int[] keys, GroupIndex busgrp)
	{
		super(model, keys, busgrp);
	}

	/**
	 * Return the group at the given index in the list
	 * @param index Index position in the list
	 * @return Group object at the index
	 */
	@Override
	public Group get(int index)
	{
		return new Group(this, index);
	}
	
	@Override
	public ListMetaType getListMeta()
	{
		return null;
	}

	@Override
	public boolean objEquals(int ndx, Object obj)
	{
		BaseObject o = (BaseObject) obj;
		return System.identityHashCode(this) == System.identityHashCode(o.getList()) && getKey(ndx) == o.getKey();
	}

	@Override
	public int objHash(int ndx)
	{
		return System.identityHashCode(this) + getKey(ndx);
	}

	@Override
	public Set<ColumnMeta> getColTypes()
	{
		return Collections.emptySet();
	}
	
}