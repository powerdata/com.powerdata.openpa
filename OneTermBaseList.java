package com.powerdata.openpa;
/*
 * Copyright (c) 2016, PowerData Corporation, Incremental Systems Corporation
 * All rights reserved.
 * Licensed under the BSD-3 Clause License.
 * See full license at https://powerdata.github.io/openpa/LICENSE.md
 */


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
