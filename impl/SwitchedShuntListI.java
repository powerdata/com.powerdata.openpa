package com.powerdata.openpa.impl;

import com.powerdata.openpa.ColumnMeta;
import com.powerdata.openpa.ListMetaType;
import com.powerdata.openpa.SwitchedShunt;
import com.powerdata.openpa.SwitchedShuntList;

public class SwitchedShuntListI extends AbstractPAList<SwitchedShunt> implements SwitchedShuntList
{
	public final static SwitchedShuntListI Empty = new SwitchedShuntListI();
	static final PAListEnum _PFld = new PAListEnum()
	{
		@Override
		public ColumnMeta id() {return ColumnMeta.SwshID;}
		@Override
		public ColumnMeta name() {return ColumnMeta.SwshNAME;}
	};

	public SwitchedShuntListI(PAModelI model, int[] keys)
	{
		super(model, keys, _PFld);
	}

	public SwitchedShuntListI(PAModelI model, int size)
	{
		super(model, size, _PFld);
	}

	public SwitchedShuntListI() {super();}

	@Override
	public SwitchedShunt get(int index)
	{
		return new SwitchedShunt(this, index);
	}

	@Override
	public ListMetaType getListMeta()
	{
		return ListMetaType.SwitchedShunt;
	}

}
