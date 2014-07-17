package com.powerdata.openpa;

import com.powerdata.openpa.PAModel.ListMetaType;

public class ShuntCapListI extends ShuntListImpl<ShuntCapacitor> implements ShuntCapList
{
	static final ShuntEnum _PFld = new ShuntEnum()
	{
		@Override
		public ColumnMeta id() {return ColumnMeta.ShcapID;}
		@Override
		public ColumnMeta name() {return ColumnMeta.ShcapNAME;}
		@Override
		public ColumnMeta bus() {return ColumnMeta.ShcapBUS;}
		@Override
		public ColumnMeta p() {return ColumnMeta.ShcapP;}
		@Override
		public ColumnMeta q() {return ColumnMeta.ShcapQ;}
		@Override
		public ColumnMeta insvc() {return ColumnMeta.ShcapINSVC;}
		@Override
		public ColumnMeta b() {return ColumnMeta.ShcapB;}
	};
	
	public ShuntCapListI(PAModel model, int[] keys)
	{
		super(model, keys, _PFld);
	}
	public ShuntCapListI(PAModel model, int size)
	{
		super(model, size, _PFld);
	}
	public ShuntCapListI() {super();}

	@Override
	public ShuntCapacitor get(int index)
	{
		return new ShuntCapacitor(this, index);
	}
	@Override
	protected ListMetaType getMetaType()
	{
		return ListMetaType.ShuntCap;
	}

}
