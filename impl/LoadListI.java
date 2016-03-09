package com.powerdata.openpa.impl;

import com.powerdata.openpa.ColumnMeta;
import com.powerdata.openpa.ListMetaType;
import com.powerdata.openpa.Load;
import com.powerdata.openpa.LoadList;
import com.powerdata.openpa.PAModelException;

public class LoadListI extends OneTermDevListI<Load> implements LoadList
{
	static final OneTermDevEnum _PFld = new OneTermDevEnum()
	{
		@Override
		public ColumnMeta id()
		{
			return ColumnMeta.LoadID;
		}
		@Override
		public ColumnMeta name()
		{
			return ColumnMeta.LoadNAME;
		}
		@Override
		public ColumnMeta bus()
		{
			return ColumnMeta.LoadBUS;
		}
		@Override
		public ColumnMeta p()
		{
			return ColumnMeta.LoadP;
		}
		@Override
		public ColumnMeta q()
		{
			return ColumnMeta.LoadQ;
		}
		@Override
		public ColumnMeta insvc()
		{
			return ColumnMeta.LoadINSVC;
		}
	};
	public LoadListI()
	{
		super();
	}

	FloatData _pmx = new FloatData(ColumnMeta.LoadPMAX);
	FloatData _qmx = new FloatData(ColumnMeta.LoadQMAX);	
	
	public LoadListI(PAModelI model, int size) throws PAModelException
	{
		super(model, size, _PFld);
	}
	public LoadListI(PAModelI model, int[] keys) throws PAModelException
	{
		super(model, keys, _PFld);
	}
	@Override
	public Load get(int index)
	{
		return new Load(this, index);
	}
	@Override
	public float getMaxP(int ndx) throws PAModelException
	{
		return _pmx.get(ndx);
	}
	@Override
	public void setMaxP(int ndx, float mw) throws PAModelException
	{
		_pmx.set(ndx, mw);
	}
	@Override
	public float[] getMaxP() throws PAModelException
	{
		return _pmx.get();
	}
	@Override
	public void setMaxP(float[] mw) throws PAModelException
	{
		_pmx.set(mw);
	}
	@Override
	public float getMaxQ(int ndx) throws PAModelException
	{
		return _qmx.get(ndx);
	}
	@Override
	public void setMaxQ(int ndx, float mvar) throws PAModelException
	{
		_qmx.set(ndx, mvar);
	}
	@Override
	public float[] getMaxQ() throws PAModelException
	{
		return _qmx.get();
	}
	@Override
	public void setMaxQ(float[] mvar) throws PAModelException
	{
		_qmx.set(mvar);
	}
	@Override
	public ListMetaType getListMeta()
	{
		return ListMetaType.Load;
	}
	
}
