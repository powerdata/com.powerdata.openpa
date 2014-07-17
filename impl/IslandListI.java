package com.powerdata.openpa.impl;

import com.powerdata.openpa.BaseList;
import com.powerdata.openpa.Bus;
import com.powerdata.openpa.BusGrpMapBldr;
import com.powerdata.openpa.ColumnMeta;
import com.powerdata.openpa.Gen;
import com.powerdata.openpa.Island;
import com.powerdata.openpa.IslandList;
import com.powerdata.openpa.Line;
import com.powerdata.openpa.ListMetaType;
import com.powerdata.openpa.PhaseShifter;
import com.powerdata.openpa.SeriesCap;
import com.powerdata.openpa.SeriesReac;
import com.powerdata.openpa.Switch;
import com.powerdata.openpa.Transformer;
import com.powerdata.openpa.TwoTermDCLine;
import com.powerdata.openpa.Gen.Mode;
import com.powerdata.openpa.Switch.State;

public class IslandListI extends GroupListI<Island> implements IslandList
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
	BoolData _egzd = new BoolData(ColumnMeta.IslandEGZSTATE);
	FloatData _freq = new FloatData(ColumnMeta.IslandFREQ);
	IntData _fsrc = new IntData(ColumnMeta.IslandFRQSRC);
	IslandListI()
	{
	};
	public IslandListI(PAModelI model)
	{
		super(model, new BusGrpMapBldr(model)
		{
			@Override
			protected boolean incSW(Switch d)
			{
				return d.getState() == State.Closed;
			}
			@Override
			protected boolean incLN(Line d)
			{
				return d.isInSvc();
			}
			@Override
			protected boolean incSR(SeriesReac d)
			{
				return d.isInSvc();
			}
			@Override
			protected boolean incSC(SeriesCap d)
			{
				return d.isInSvc();
			}
			@Override
			protected boolean incTX(Transformer d)
			{
				return d.isInSvc();
			}
			@Override
			protected boolean incPS(PhaseShifter d)
			{
				return d.isInSvc();
			}
			@Override
			protected boolean incD2(TwoTermDCLine d)
			{
				return d.isInSvc();
			}
		}.addAll().getMap(), _PFld);
		String[] id = new String[_size];
		for (int i = 0; i < _size; ++i)
			id[i] = String.valueOf(i + 1);
		setName(id);
		setID(id);
		setupEgStatus();
	}
	@Override
	public Island get(int index)
	{
		return new Island(this, index);
	}
	void setupEgStatus()
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
			_egzd.set(e);
		}
	}
	@Override
	public boolean isEnergized(int ndx)
	{
		return _egzd.get(ndx);
	}
	@Override
	public boolean[] isEnergized()
	{
		return _egzd.get();
	}
	@Override
	public float getFreq(int ndx)
	{
		return _freq.get(ndx);
	}
	@Override
	public void setFreq(int ndx, float f)
	{
		_freq.set(ndx, f);
	}
	@Override
	public float[] getFreq()
	{
		return _freq.get();
	}
	@Override
	public void setFreq(float[] f)
	{
		_freq.set(f);
	}
	@Override
	public Bus getFreqSrc(int ndx)
	{
		return _model.getBuses().get(_fsrc.get(ndx));
	}
	@Override
	public void setFreqSrc(int ndx, Bus fsrc)
	{
		_fsrc.set(ndx, fsrc.getIndex());
	}
	@Override
	public Bus[] getFreqSrc()
	{
		return _model.getBuses().toArray(_fsrc.get());
	}
	@Override
	public void setFreqSrc(Bus[] fsrc)
	{
		_fsrc.set(BaseList.ObjectNdx(fsrc));
	}
	@Override
	protected ListMetaType getMetaType()
	{
		return ListMetaType.Island;
	}
}
