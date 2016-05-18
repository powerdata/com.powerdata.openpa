package com.powerdata.openpa;
/*
 * Copyright (c) 2016, PowerData Corporation, Incremental Systems Corporation
 * All rights reserved.
 * Licensed under the BSD-3 Clause License.
 * See full license at https://powerdata.github.io/openpa/LICENSE.md
 */


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
