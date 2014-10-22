package com.powerdata.openpa.tools.psmfmt;

import com.powerdata.openpa.BusList;
import com.powerdata.openpa.PAModel;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.SeriesReacList;
import com.powerdata.openpa.pwrflow.BusRefIndex;

public class SeriesReactorOPA extends ExportOpenPA<SeriesReacList>
{
	public SeriesReactorOPA(PAModel m, BusRefIndex bri) throws PAModelException
	{
		super(m.getSeriesReactors(), SeriesReactor.values().length);
		int[][] bx = bri.get2TBus(_list);
		BusList buses = bri.getBuses();
		assign(SeriesCapacitor.ID, new StringWrap(i -> _list.getID(i)));
		assign(SeriesCapacitor.Name, new StringWrap(i -> _list.getName(i)));
		assign(SeriesCapacitor.Node1, new StringWrap(i -> buses.get(bx[0][i])
				.getID()));
		assign(SeriesCapacitor.Node2, new StringWrap(i -> buses.get(bx[1][i])
				.getID()));
		assign(SeriesCapacitor.R, i -> String.valueOf(_list.getR(i)));
		assign(SeriesCapacitor.X, i -> String.valueOf(_list.getX(i)));
		assign(SeriesCapacitor.NormalOperatingLimit,
			i -> String.valueOf(_list.getLTRating(i)));
	}

	
	@Override
	protected String getPsmFmtName()
	{
		return PsmMdlFmtObject.SeriesReactor.toString();
	}
}
