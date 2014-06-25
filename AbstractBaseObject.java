package com.powerdata.openpa;

/**
 * Starting point of the base object hierarchy
 * 
 * @author chris@powerdata.com
 *
 */
public class AbstractBaseObject implements BaseObject
{
	protected int _ndx;
	protected BaseList<? extends BaseObject> _list;
	
	public AbstractBaseObject(BaseList<? extends BaseObject> list, int ndx)
	{
		_list = list;
		_ndx = ndx;
	}

	@Override
	public int getIndex() {return _ndx;}

	@Override
	public int getKey()
	{
		return _list.getKey(_ndx);
	}

	@Override
	public String getID()
	{
		return _list.getID(_ndx);
	}

	@Override
	public void setID(String id)
	{
		_list.setID(_ndx, id);
	}

	// TODO: 
	// WHat is the different between name and fullname, does it matter?
	
	@Override
	public String getName()
	{
		return _list.getName(_ndx);
	}
	@Override
	public void setName(String name)
	{
		_list.setName(_ndx, name);
	}

	@Override
	public String toString()
	{
		try
		{
			return new StringBuilder(getClass().getSimpleName()).append(' ')
					.append(getName()).append(' ').append(getID()).toString();
		} catch (Exception e)
		{
			return "err";
		}
	}
}
