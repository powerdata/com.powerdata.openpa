package com.powerdata.openpa.tools.psmfmt;

import com.powerdata.openpa.BaseList;
import com.powerdata.openpa.BaseObject;
import com.powerdata.openpa.PAModelException;
import java.util.function.IntFunction;


public abstract class ExportOpenPA<T extends BaseList<? extends BaseObject>> extends Export
{
	protected T _list;
	protected ExportOpenPA(T list, int ncol)
	{
		_list = list;
		_finfo = new FmtInfo[ncol];
	}

	public interface ListAccess
	{
		String get(int i) throws PAModelException;
	}
	protected FmtInfo[] _finfo;

	public class StringWrap implements ListAccess
	{
		ListAccess _base;
		public StringWrap(ListAccess base) {_base = base;}
		
		@Override
		public String get(int i) throws PAModelException
		{
			String rv = "";
			try
			{rv = String.format("\"%s\"", _base.get(i));}
			catch(ArrayIndexOutOfBoundsException x)
			{
				rv = "";
			}
			return rv;
		}
	}
	
	protected void assign(Enum<?> id, ListAccess f)
	{
		int n = id.ordinal();
		FmtInfo fi = new FmtInfo(id.toString(), 
			new IntFunction<String>()
			{
				@Override
				public String apply(int value)
				{
					String rv = null;
					try
					{
						rv = f.get(value);
					}
					catch(PAModelException e)
					{
						e.printStackTrace();
					}
					catch(ArrayIndexOutOfBoundsException bex)
					{
						rv = "";
					}
					return rv;
				}
			});
		_finfo[n] = fi;
	}
	
	protected void assign(Enum<?> id, ListAccess f, FmtInfo[] finfo)
	{
		int n = id.ordinal();
		FmtInfo fi = new FmtInfo(id.toString(), 
			new IntFunction<String>()
			{
				@Override
				public String apply(int value)
				{
					String rv = null;
					try
					{
						rv = f.get(value);
					}
					catch(PAModelException e)
					{
						e.printStackTrace();
					}
					return rv;
				}
			});
		finfo[n] = fi;
	}

	@Override
	protected int getCount()
	{
		return _list.size();
	}

	@Override
	protected FmtInfo[] getFmtInfo()
	{
		return _finfo;
	}
}
