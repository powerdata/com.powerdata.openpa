package com.powerdata.openpa.psse.csv;

import java.lang.reflect.Array;
import java.lang.reflect.Method;

import com.powerdata.openpa.psse.PsseModelException;

class WndLoader
{
	static final Class<TransformerRawList> _class = TransformerRawList.class;
	
	Method[] _methods;
	public WndLoader(String prop) throws PsseModelException
	{
		try
		{
			String pn = "get"+prop;
			_methods = new Method[] {null,
					_class.getMethod(pn+"1", int.class),
					_class.getMethod(pn+"2", int.class),
					_class.getMethod(pn+"3", int.class)};
		} catch (ReflectiveOperationException | SecurityException e)
		{
			throw new PsseModelException(e);
		}
	}
	
	public Object load(TransformerRawList rlist, int[] ndx,
			int[] wnd, Class<?> type) throws PsseModelException
	{
		int n = ndx.length;
		Object rv = Array.newInstance(type, n);
		try
		{
			for (int i = 0; i < n; ++i)
			{
				Array.set(rv, i, _methods[wnd[i]].invoke(rlist, ndx[i]));

			}
		} catch (ArrayIndexOutOfBoundsException | ReflectiveOperationException e)
		{
			throw new PsseModelException(e);
		}
		return rv;
	}
}