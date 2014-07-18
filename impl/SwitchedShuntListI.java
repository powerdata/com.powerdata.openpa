package com.powerdata.openpa.impl;

import com.powerdata.openpa.ColumnMeta;
import com.powerdata.openpa.ListMetaType;
import com.powerdata.openpa.SwitchedShunt;
import com.powerdata.openpa.SwitchedShuntList;

public class SwitchedShuntListI extends ShuntListI<SwitchedShunt> implements SwitchedShuntList
{
	static final ShuntEnum _PFld = new ShuntEnum()
	{
		@Override
		public ColumnMeta id() {return ColumnMeta.SwshID;}
		@Override
		public ColumnMeta name() {return ColumnMeta.SwshNAME;}
		@Override
		public ColumnMeta bus() {return ColumnMeta.SwshBUS;}
		@Override
		public ColumnMeta p() {return ColumnMeta.SwshP;}
		@Override
		public ColumnMeta q() {return ColumnMeta.SwshQ;}
		@Override
		public ColumnMeta insvc() {return ColumnMeta.SwshINSVC;}
		@Override
		public ColumnMeta b() {return ColumnMeta.SwshB;}
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
	public ListMetaType getMetaType()
	{
		return ListMetaType.SwitchedShunt;
	}
	
	
}
