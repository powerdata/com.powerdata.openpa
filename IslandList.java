package com.powerdata.openpa;

import com.powerdata.openpa.Gen.Mode;
import com.powerdata.openpa.Switch.State;

public class IslandList extends EquipLists<Island>
{
	protected boolean[] _egzd = null;
	
	public static final IslandList Empty = new IslandList();
	
	IslandList(){};
	
	public IslandList(PALists model)
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
		}.addAll().getMap());
		
		_name = new String[_size];
		_id = new String[_size];
		for(int i=0; i < _size; ++i)
		{
			String s = String.valueOf(i);
			_name[i] = s;
			_id[i] = s;
		}
			
	}
	
	@Override
	public Island get(int index)
	{
		return new Island(this, index);
	}
	
	public boolean[] isEnergized()
	{
		if (_egzd == null)
			setupEgStatus();
		return _egzd;
	}
	
	void setupEgStatus()
	{
		int n = size();
		_egzd = new boolean[n];
		for(int i=0; i < n; ++i)
		{
			GenList gens = getGenerators(i);
			for(Gen g : gens)
			{
				Mode m = g.getMode();
				if (m != Mode.OFF && m != Mode.PMP)
				{
					_egzd[i] = true;
					break;
				}
			}
		}
	}
	
	public boolean isEnergized(int ndx)
	{
		return isEnergized()[ndx];
	}

	public static void main(String[] args) throws Exception
	{
		PAModel m = PflowModelBuilder.Create("pd2cim:sdb=/run/shm/config.pddb&db=/home/chris/Documents/testmodels/public/palco/exports/cim.pddb").load();
		for(Island i : m.getIslands())
		{
			System.out.println(i);
		}
	}
	
}
