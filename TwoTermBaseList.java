package com.powerdata.openpa;

import java.util.List;

public interface TwoTermBaseList<T extends com.powerdata.openpa.TwoTermBaseList.TwoTermBase> extends List<T>
{
	interface TwoTermBase extends BaseObjectCore
	{
		/** get from-side Bus */
		public Bus getFromBus() throws PAModelException;
		
		/** get to-side bus */
		public Bus getToBus() throws PAModelException;
		
	}

	Bus getFromBus(int ndx) throws PAModelException;
	
	Bus getToBus(int ndx) throws PAModelException;
	
}
