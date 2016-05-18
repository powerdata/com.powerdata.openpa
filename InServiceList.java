package com.powerdata.openpa;
/*
 * Copyright (c) 2016, PowerData Corporation, Incremental Systems Corporation
 * All rights reserved.
 * Licensed under the BSD-3 Clause License.
 * See full license at https://powerdata.github.io/openpa/LICENSE.md
 */


public interface InServiceList<T extends InService> extends BaseList<T>
{
	boolean isInService(int ndx) throws PAModelException;
	boolean[] isInService() throws PAModelException;
	void setInService(int ndx, boolean s) throws PAModelException;
	void setInService(boolean[] s) throws PAModelException;
}
