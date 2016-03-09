package com.powerdata.openpa.impl;

import com.powerdata.openpa.ColumnMeta;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.SteamTurbine;
import com.powerdata.openpa.SteamTurbine.SteamSupply;
import com.powerdata.openpa.SteamTurbineList;

public class SteamTurbineListI extends AbstractPAList<SteamTurbine> implements SteamTurbineList
{
	static final PAListEnum _PFld = new PAListEnum()
	{
		@Override
		public ColumnMeta id()
		{
			return ColumnMeta.SteamTurbineID;
		}
		@Override
		public ColumnMeta name()
		{
			return ColumnMeta.SteamTurbineNAME;
		}
	};
	
	protected EnumData<SteamSupply> _ssupply = new EnumData<SteamSupply>(ColumnMeta.SteamTurbineSteamSupply);

	
	public SteamTurbineListI() {super();}
	public SteamTurbineListI(PAModelI model, int size) throws PAModelException
	{
		super(model, size, _PFld);
	}
	public SteamTurbineListI(PAModelI model, int[] keys) throws PAModelException
	{
		super(model, keys, _PFld);
	}

	@Override
	public SteamSupply getSteamSupply(int ndx) throws PAModelException
	{
		return _ssupply.get(ndx);
	}

	@Override
	public void setSteamSupply(int ndx, SteamSupply v) throws PAModelException
	{
		_ssupply.set(ndx, v);
	}

	@Override
	public SteamTurbine get(int index)
	{
		return new SteamTurbine(this, index);
	}
	
}
