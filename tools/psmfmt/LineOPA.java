package com.powerdata.openpa.tools.psmfmt;

import com.powerdata.openpa.BusRefIndex;
import com.powerdata.openpa.LineList;
import com.powerdata.openpa.PAModel;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.BusList;

public class LineOPA extends ExportOpenPA<LineList>
{
	public LineOPA(PAModel m, BusRefIndex bri) throws PAModelException
	{
		super(m.getLines(), Line.values().length);
		BusRefIndex.TwoTerm bx = bri.get2TBus(_list);
		BusList buses = bri.getBuses();
		assign(Line.ID, new StringWrap(i -> _list.getID(i)));
		assign(Line.Name, new StringWrap(i -> _list.getName(i)));
		assign(Line.Node1, new StringWrap(i -> buses.get(bx.getFromBus()[i]).getID()));
		assign(Line.Node2, new StringWrap(i -> buses.get(bx.getToBus()[i]).getID()));
		assign(Line.R, i -> String.valueOf(_list.getR(i)));
		assign(Line.X, i -> String.valueOf(_list.getX(i)));
		assign(Line.Bch, i -> String.valueOf(_list.getFromBchg(i)+_list.getToBchg(i)));
		assign(Line.NormalOperatingLimit, i -> String.valueOf(_list.getLTRating(i)));
	}
	
	@Override
	protected String getPsmFmtName()
	{
		return PsmMdlFmtObject.Line.toString();
	}
}
