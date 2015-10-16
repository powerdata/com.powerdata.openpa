package com.powerdata.openpa.impl;

import com.powerdata.openpa.BusGrpMapBldr;
import com.powerdata.openpa.BusList;
import com.powerdata.openpa.ColumnMeta;
import com.powerdata.openpa.Gen;
import com.powerdata.openpa.GroupListI;
import com.powerdata.openpa.ElectricalIsland;
import com.powerdata.openpa.ElectricalIslandList;
import com.powerdata.openpa.Line;
import com.powerdata.openpa.ListMetaType;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.PhaseShifter;
import com.powerdata.openpa.SeriesCap;
import com.powerdata.openpa.SeriesReac;
import com.powerdata.openpa.Switch;
import com.powerdata.openpa.Transformer;
import com.powerdata.openpa.TwoTermDCLine;
import com.powerdata.openpa.Gen.Mode;
import com.powerdata.openpa.Switch.State;

public class ElectricalIslandListI extends GroupListI<ElectricalIsland> implements ElectricalIslandList
{
	static final PAListEnum _PFld = new PAListEnum()
	{
		@Override
		public ColumnMeta id()
		{
			return ColumnMeta.IslandID;
		}
		@Override
		public ColumnMeta name()
		{
			return ColumnMeta.IslandNAME;
		}
	};
	BoolData _egzd;
	protected FloatData _freq = new FloatData(ColumnMeta.IslandFREQ);
	BusList _buses;
	
	public ElectricalIslandListI(PAModelI model) throws PAModelException
	{
		super(model, new BusGrpMapBldr(model)
		{
			@Override
			protected boolean incSW(Switch d) throws PAModelException
			{
				return d.getState() == State.Closed;
			}
			@Override
			protected boolean incLN(Line d) throws PAModelException
			{
				return d.isInService();
			}
			@Override
			protected boolean incSR(SeriesReac d) throws PAModelException
			{
				return d.isInService();
			}
			@Override
			protected boolean incSC(SeriesCap d) throws PAModelException
			{
				return d.isInService();
			}
			@Override
			protected boolean incTX(Transformer d) throws PAModelException
			{
				return d.isInService();
			}
			@Override
			protected boolean incPS(PhaseShifter d) throws PAModelException
			{
				return d.isInService();
			}
			@Override
			protected boolean incD2(TwoTermDCLine d) throws PAModelException
			{
				return d.isInService();
			}
		}.addAll().getMap(), _PFld);
		_buses = model.getBuses();
		setupEgStatus();
	}
	public ElectricalIslandListI() 
	{
		super();
	};
	@Override
	public ElectricalIsland get(int index)
	{
		return new ElectricalIsland(this, index);
	}
	void setupEgStatus() throws PAModelException
	{
		int n = size();
		boolean[] e = new boolean[_size];
		for (int i = 0; i < n; ++i)
		{
			for (Gen g : getGenerators(i))
			{
				Mode m = g.getMode();
				if (m != Mode.OFF && m != Mode.PMP)
				{
					e[i] = true;
					break;
				}
			}
		}
		_egzd = new BoolData(ColumnMeta.IslandEGZSTATE)
		{
			@Override
			boolean[] load() throws PAModelException
			{
				return e;
			}
		};
	}
	@Override
	public boolean isEnergized(int ndx) throws PAModelException
	{
		return _egzd.get(ndx);
	}
	@Override
	public boolean[] isEnergized() throws PAModelException
	{
		return _egzd.get();
	}
	@Override
	public float getFreq(int ndx) throws PAModelException
	{
		return _freq.get(ndx);
	}
	@Override
	public void setFreq(int ndx, float f) throws PAModelException
	{
		_freq.set(ndx, f);
	}
	@Override
	public float[] getFreq() throws PAModelException
	{
		return _freq.get();
	}
	@Override
	public void setFreq(float[] f) throws PAModelException
	{
		_freq.set(f);
	}
	@Override
	public ListMetaType getListMeta()
	{
		return ListMetaType.Island;
	}
}
