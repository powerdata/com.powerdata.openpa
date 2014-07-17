package com.powerdata.openpa;

import com.powerdata.openpa.PAModel.ListMetaType;

public class ShuntReacListI extends ShuntListImpl<ShuntReactor> implements ShuntReacList
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
	
	public ShuntReacListI(PAModel model, int[] keys)
	{
		super(model, keys, _PFld);
	}

	public ShuntReacListI(PAModel model, int size)
	{
		super(model, size, _PFld);
	}

	public ShuntReacListI() {super();}

	@Override
	public ShuntReactor get(int index)
	{
		return new ShuntReactor(this, index);
	}

	@Override
	protected ListMetaType getMetaType()
	{
		return ListMetaType.ShuntReac;
	}

}
