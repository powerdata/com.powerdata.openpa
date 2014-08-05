package com.powerdata.openpa.pwrflow;

import com.powerdata.openpa.OutOfService;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.impl.GroupMap;

public class CalcBase
{
	int[] _oosndx;
	
	int[] getInSvc(OutOfService list) throws PAModelException
	{
		if (_oosndx == null)
		{
			boolean[] oos = list.isOutOfSvc();
			int n = oos.length;
			int[] map = new int[n];
			for(int i=0; i < n; ++i)
				map[i] = oos[i]?1:0;
			_oosndx = new GroupMap(map,2).get(0);
		}
		return _oosndx;
	}
}
