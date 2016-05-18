package com.powerdata.openpa;
/*
 * Copyright (c) 2016, PowerData Corporation, Incremental Systems Corporation
 * All rights reserved.
 * Licensed under the BSD-3 Clause License.
 * See full license at https://powerdata.github.io/openpa/LICENSE.md
 */


public interface FixedShuntListIfc<T extends FixedShunt> extends OneTermDevListIfc<T>
{

	float getB(int ndx) throws PAModelException;

	void setB(int ndx, float b) throws PAModelException;
	
	float[] getB() throws PAModelException;
	
	void setB(float[] b) throws PAModelException;

}
