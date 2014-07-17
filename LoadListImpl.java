package com.powerdata.openpa;

import com.powerdata.openpa.PAModel.ListMetaType;

public class LoadListImpl extends OneTermDevListI<Load> implements LoadList 
{
	static final OneTermDevEnum _PFld = new OneTermDevEnum()
	{
		@Override
		public ColumnMeta id() {return ColumnMeta.LoadID;}
		@Override
		public ColumnMeta name() {return ColumnMeta.LoadNAME;}
		@Override
		public ColumnMeta bus() {return ColumnMeta.LoadBUS;}
		@Override
		public ColumnMeta p() {return ColumnMeta.LoadP;}
		@Override
		public ColumnMeta q() {return ColumnMeta.LoadQ;}
		@Override
		public ColumnMeta insvc() {return ColumnMeta.LoadINSVC;}
	};
	
	public LoadListImpl() {super();}
	
	FloatData _pmx = new FloatData(ColumnMeta.LoadPMAX),
			_qmx = new FloatData(ColumnMeta.LoadQMAX);

	public LoadListImpl(PAModel model, int size)
	{
		super(model, size, _PFld);
	}

	public LoadListImpl(PAModel model, int[] keys)
	{
		super(model, keys, _PFld);
	}

	@Override
	public Load get(int index)
	{
		return new Load(this, index);
	}

	@Override
	public float getMaxP(int ndx)
	{
		return _pmx.get(ndx);
	}

	@Override
	public void setMaxP(int ndx, float mw)
	{
		_pmx.set(ndx, mw);
	}

	@Override
	public float[] getMaxP()
	{
		return _pmx.get();
	}

	@Override
	public void setMaxP(float[] mw)
	{
		_pmx.set(mw);
	}

	@Override
	public float getMaxQ(int ndx)
	{
		return _qmx.get(ndx);
	}

	@Override
	public void setMaxQ(int ndx, float mvar)
	{
		_qmx.set(ndx, mvar);
	}

	@Override
	public float[] getMaxQ()
	{
		return _qmx.get();
	}

	@Override
	public void setMaxQ(float[] mvar)
	{
		_qmx.set(mvar);
	}

	@Override
	protected ListMetaType getMetaType()
	{
		return ListMetaType.Load;
	}

}
