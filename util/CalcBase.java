package com.powerdata.openpa.util;

import java.lang.ref.WeakReference;
import com.powerdata.openpa.OutOfService;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.impl.GroupMap;

public class CalcBase
{
	WeakReference<int[]> _oosndx = new WeakReference<>(null);
	
	int[] getInSvc(OutOfService list) throws PAModelException
	{
		int[] rv = _oosndx.get();
		if (rv == null)
		{
			boolean[] oos = list.isOutOfSvc();
			int n = oos.length;
			int[] map = new int[n];
			for(int i=0; i < n; ++i)
				map[i] = oos[i]?1:0;
			GroupMap gm = new GroupMap(map,2);
			rv = gm.get(1);
			_oosndx = new WeakReference<>(rv);
		}
		return rv;
	}
}
