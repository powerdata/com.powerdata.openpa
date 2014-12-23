package com.powerdata.openpa;

import java.util.List;

public interface OneTermBaseList<T extends com.powerdata.openpa.OneTermBaseList.OneTermBase> extends List<T>
{
	interface OneTermBase extends BaseObjectCore
	{
		/** Device terminal Bus */
		public Bus getBus() throws PAModelException;
	}
	
	Bus getBus(int ndx) throws PAModelException;
}
