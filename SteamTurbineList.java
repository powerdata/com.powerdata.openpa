package com.powerdata.openpa;
/*
 * Copyright (c) 2016, PowerData Corporation, Incremental Systems Corporation
 * All rights reserved.
 * Licensed under the BSD-3 Clause License.
 * See full license at https://powerdata.github.io/openpa/LICENSE.md
 */


import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;
import com.powerdata.openpa.SteamTurbine.SteamSupply;

public interface SteamTurbineList extends BaseList<SteamTurbine>
{
	
	/**
	 * Get Steam Supply
	 * @param ndx
	 * @return
	 * @throws PAModelException
	 */
	SteamSupply getSteamSupply(int ndx) throws PAModelException;
	void setSteamSupply(int ndx, SteamSupply v) throws PAModelException;
	
	@Override
	default Set<ColumnMeta> getColTypes()
	{
		return Cols;
	}
	@Override default ListMetaType getListMeta() {return ListMetaType.SteamTurbine;}

	static Set<ColumnMeta> Cols = EnumSet
			.copyOf(Arrays.asList(new ColumnMeta[] { ColumnMeta.SteamTurbineID,
					ColumnMeta.SteamTurbineNAME, ColumnMeta.SteamTurbineSteamSupply }));

}
