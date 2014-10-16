package com.powerdata.openpa.tools.psmfmt;

import com.powerdata.openpa.BaseList;
import com.powerdata.openpa.BaseObject;
import com.powerdata.openpa.PAModelException;
import java.util.function.IntFunction;


abstract class ExportOpenPA<T extends BaseList<? extends BaseObject>> extends Export
{
	T _list;
	ExportOpenPA(T list, int ncol)
	{
		_list = list;
		_finfo = new FmtInfo[ncol];
	}

	interface ListAccess
	{
		String get(int i) throws PAModelException;
	}
	FmtInfo[] _finfo;

	class StringWrap implements ListAccess
	{
		ListAccess _base;
		StringWrap(ListAccess base) {_base = base;}
		
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
	
	void assign(Enum<?> id, ListAccess f)
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
//						System.out.println("[ExportOpenPA] rv: "+rv);
					}
					catch(PAModelException e)
					{
						e.printStackTrace();
					}
					return rv;
				}
			});
		_finfo[n] = fi;
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
