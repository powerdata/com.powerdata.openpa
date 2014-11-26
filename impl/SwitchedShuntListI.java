package com.powerdata.openpa.impl;

import com.powerdata.openpa.ColumnMeta;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.SwitchedShunt;
import com.powerdata.openpa.SwitchedShuntList;

public class SwitchedShuntListI extends OneTermDevListI<SwitchedShunt> implements SwitchedShuntList
{
	static final OneTermDevEnum _PFld = new OneTermDevEnum()
	{
		@Override
		public ColumnMeta id() {return ColumnMeta.SwshID;}
		@Override
		public ColumnMeta name() {return ColumnMeta.SwshNAME;}
		@Override
		public ColumnMeta insvc()
		{
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public ColumnMeta bus()
		{
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public ColumnMeta p()
		{
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public ColumnMeta q()
		{
			// TODO Auto-generated method stub
			return null;
		}
	};

	public SwitchedShuntListI(PAModelI model, int[] keys) throws PAModelException
	{
		super(model, keys, _PFld);
	}

	public SwitchedShuntListI(PAModelI model, int size) throws PAModelException
	{
		super(model, size, _PFld);
	}

	public SwitchedShuntListI() {super();}

	@Override
	public SwitchedShunt get(int index)
	{
		return new SwitchedShunt(this, index);
	}
}
