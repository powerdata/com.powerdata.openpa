package com.powerdata.openpa.tools.psmfmt;
/*
 * Copyright (c) 2016, PowerData Corporation, Incremental Systems Corporation
 * All rights reserved.
 * Licensed under the BSD-3 Clause License.
 * See full license at https://powerdata.github.io/openpa/LICENSE.md
 */


import com.powerdata.openpa.BusList;
import com.powerdata.openpa.BusRefIndex;
import com.powerdata.openpa.PAModelException;

public class NodeOPA extends ExportOpenPA<BusList>
{
	public NodeOPA(BusRefIndex bri) throws PAModelException
	{
		super(bri.getBuses(), Node.values().length);
		assign(Node.ID, new StringWrap(i -> _list.getID(i)));
		assign(Node.Name, new StringWrap(i -> _list.getName(i)));
		assign(Node.NominalKV, i -> String.valueOf(
			_list.getVoltageLevel(i).getBaseKV()));
		assign(Node.Substation, new StringWrap(i -> _list.getStation(i).getID()));
		assign(Node.FrequencySourcePriority, i -> String.valueOf(_list.getFreqSrcPri(i)));
	}
	
	@Override
	protected String getPsmFmtName()
	{
		return PsmMdlFmtObject.Node.toString();
	}
}
