package com.powerdata.openpa.impl;

import com.powerdata.openpa.BaseList;
import com.powerdata.openpa.Bus;
import com.powerdata.openpa.BusGrpMapBldr;
import com.powerdata.openpa.ColumnMeta;
import com.powerdata.openpa.Gen;
import com.powerdata.openpa.GroupListI;
import com.powerdata.openpa.Island;
import com.powerdata.openpa.IslandList;
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
	BoolData _egzd;
	protected FloatData _freq = new FloatData(ColumnMeta.IslandFREQ);
	protected IntData _fsrc = new IntData(ColumnMeta.IslandFRQSRC);
	IslandListI() {};
	
	class IStringData extends StringData
	{
		String[] id;
		IStringData(ColumnMeta coltype, String[] id) {super(coltype);this.id = id;}

		@Override
		String[] load() throws PAModelException
		{
			return id;
		}

	}
	
	public IslandListI(PAModelI model) throws PAModelException
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
				return d.isOutOfSvc();
			}
			@Override
			protected boolean incSR(SeriesReac d) throws PAModelException
			{
				return d.isOutOfSvc();
			}
			@Override
			protected boolean incSC(SeriesCap d) throws PAModelException
			{
				return d.isOutOfSvc();
			}
			@Override
			protected boolean incTX(Transformer d) throws PAModelException
			{
				return d.isOutOfSvc();
			}
			@Override
			protected boolean incPS(PhaseShifter d) throws PAModelException
			{
				return d.isOutOfSvc();
			}
			@Override
			protected boolean incD2(TwoTermDCLine d) throws PAModelException
			{
				return d.isOutOfSvc();
			}
		}.addAll().getMap(), _PFld);
		String[] id = new String[_size];
		for (int i = 0; i < _size; ++i)
			id[i] = String.valueOf(i + 1);
		
		_id = new IStringData(ColumnMeta.IslandID, id);
		_name = new IStringData(ColumnMeta.IslandNAME, id);
		setupEgStatus();
	}
	@Override
	public Island get(int index)
	{
		return new Island(this, index);
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
	public Bus getFreqSrc(int ndx) throws PAModelException
	{
		return _model.getBuses().get(_fsrc.get(ndx));
	}
	@Override
	public void setFreqSrc(int ndx, Bus fsrc) throws PAModelException
	{
		_fsrc.set(ndx, fsrc.getIndex());
	}
	@Override
	public Bus[] getFreqSrc() throws PAModelException
	{
		return _model.getBuses().toArray(_fsrc.get());
	}
	@Override
	public void setFreqSrc(Bus[] fsrc) throws PAModelException
	{
		_fsrc.set(BaseList.ObjectNdx(fsrc));
	}
	@Override
	public ListMetaType getListMeta()
	{
		return ListMetaType.Island;
	}
}
