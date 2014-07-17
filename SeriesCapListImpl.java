package com.powerdata.openpa;

import com.powerdata.openpa.PAModel.ListMetaType;

public class SeriesCapListImpl extends ACBranchListImpl<SeriesCap> implements SeriesCapList
{
	static final ACBranchEnum _PFld = new ACBranchEnum()
	{
		@Override
		public ColumnMeta fbus() {return ColumnMeta.SercapBUSFROM;}
		@Override
		public ColumnMeta tbus() {return ColumnMeta.SercapBUSTO;}
		@Override
		public ColumnMeta insvc() {return ColumnMeta.SercapINSVC;}
		@Override
		public ColumnMeta fp() {return ColumnMeta.SercapPFROM;}
		@Override
		public ColumnMeta fq() {return ColumnMeta.SercapQFROM;}
		@Override
		public ColumnMeta tp() {return ColumnMeta.SercapPTO;}
		@Override
		public ColumnMeta tq() {return ColumnMeta.SercapQTO;}
		@Override
		public ColumnMeta id() {return ColumnMeta.SercapID;}
		@Override
		public ColumnMeta name() {return ColumnMeta.SercapNAME;}
		@Override
		public ColumnMeta r() {return ColumnMeta.SercapR;}
		@Override
		public ColumnMeta x() {return ColumnMeta.SercapX;}
	};

	public SeriesCapListImpl(PAModel model, int[] keys)
	{
		super(model, keys, _PFld);
	}

	public SeriesCapListImpl(PAModel model, int size)
	{
		super(model, size, _PFld);
	}

	public SeriesCapListImpl() {super();}

	@Override
	public SeriesCap get(int index)
	{
		return new SeriesCap(this, index);
	}

	@Override
	protected ListMetaType getMetaType()
	{
		return ListMetaType.SeriesCap;
	}
}
