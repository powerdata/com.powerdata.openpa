package com.powerdata.openpa.impl;

import com.powerdata.openpa.ColumnMeta;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.ShuntReacList;
import com.powerdata.openpa.ShuntReactor;

public class ShuntReacListI extends FixedShuntListI<ShuntReactor> implements ShuntReacList
{
	static final ShuntEnum _PFld = new ShuntEnum()
	{
		@Override
		public ColumnMeta id() {return ColumnMeta.ShreacID;}
		@Override
		public ColumnMeta name() {return ColumnMeta.ShreacNAME;}
		@Override
		public ColumnMeta bus() {return ColumnMeta.ShreacBUS;}
		@Override
		public ColumnMeta p() {return ColumnMeta.ShreacP;}
		@Override
		public ColumnMeta q() {return ColumnMeta.ShreacQ;}
		@Override
		public ColumnMeta insvc() {return ColumnMeta.ShreacINSVC;}
		@Override
		public ColumnMeta b() {return ColumnMeta.ShreacB;}
	};
	
	public ShuntReacListI(PAModelI model, int[] keys) throws PAModelException
	{
		super(model, keys, _PFld);
	}

	public ShuntReacListI(PAModelI model, int size) throws PAModelException
	{
		super(model, size, _PFld);
	}

	public ShuntReacListI() {super();}

	@Override
	public ShuntReactor get(int index)
	{
		return new ShuntReactor(this, index);
	}

}
